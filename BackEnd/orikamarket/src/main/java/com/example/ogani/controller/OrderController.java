package com.example.ogani.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ogani.entity.Order;
import com.example.ogani.model.classes.ItemData;
import com.example.ogani.model.classes.PayOs;
import com.example.ogani.model.classes.PaymentData;
import com.example.ogani.model.request.CreateOrderRequest;
import com.example.ogani.model.request.CreatePaymentLinkRequestBody;
import com.example.ogani.model.response.MessageResponse;
import com.example.ogani.service.OrderService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*",maxAge = 3600)
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private PayOs payOS;

    @GetMapping("/")
    @Operation(summary="Lấy ra danh sách đặt hàng")
    public ResponseEntity<List<Order>> getList(){
        List<Order> list = orderService.getList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/user")
    @Operation(summary="Lấy ra danh sách đặt hàng của người dùng bằng username")
    public ResponseEntity<List<Order>> getListByUser(@RequestParam("username") String username){
        List<Order> list = orderService.getOrderByUser(username);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/create")
    @Operation(summary="Đặt hàng sản phẩm")
    public ResponseEntity<?> placeOrder(@RequestBody CreateOrderRequest request){
        orderService.placeOrder(request);
        return ResponseEntity.ok(new MessageResponse("Order Placed Successfully!"));
    }

    @PostMapping("/create-payos")
    public ResponseEntity<?> createPaymentLink(@RequestBody CreatePaymentLinkRequestBody requestBody) {
        try {
            // Call the service method to perform the logic
            orderService.placeOrderPayos(requestBody); // Giả sử phương thức placeOrderPayos trả về đối tượng Order
            
            // Return a ResponseEntity with HTTP status 200 OK
            return ResponseEntity.ok(new MessageResponse("Banking thành công!"));
        } catch (Exception e) {
            e.printStackTrace();
            // If an exception occurs, return ResponseEntity with HTTP status 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/create-payment-link")
    public void checkout(@RequestBody CreatePaymentLinkRequestBody paymentLinkRequestBody,
            HttpServletResponse httpServletResponse) {
        try {
            List<ItemData> productList = paymentLinkRequestBody.getProductList();
            String description = paymentLinkRequestBody.getDescription();
            String returnUrl = paymentLinkRequestBody.getReturnUrl();
            String cancelUrl = paymentLinkRequestBody.getCancelUrl();
            String buyerName = paymentLinkRequestBody.getLastname();
            String buyerEmail = paymentLinkRequestBody.getEmail();
            String buyerPhone = paymentLinkRequestBody.getPhone();
            String buyerAddress = paymentLinkRequestBody.getAddress();
            int totalPrice = 2000;
            // Gen order code
            String currentTimeString = String.valueOf(new Date().getTime());

            int orderCode = Integer.parseInt(currentTimeString.substring(currentTimeString.length() - 6));

            //  Tạo ra list sản phẩm
            List<ItemData> itemList = new ArrayList<>();

            //  Chạy vòng lặp để add item có được từ FE vào list sản phẩm
            for (ItemData product : productList) {
                ItemData item = new ItemData(product.getName(), product.getQuantity(), product.getPrice());
                itemList.add(item);
            }
            
            //  Tạo ra các thông tin thanh toán
            PaymentData paymentData = new PaymentData(orderCode, totalPrice, description,
                    itemList, cancelUrl, returnUrl);
            paymentData.setBuyerName(buyerName);
            paymentData.setBuyerAddress(buyerAddress);
            paymentData.setBuyerEmail(buyerEmail);
            paymentData.setBuyerPhone(buyerPhone);

            //  Tạo json thanh toán để trả về phía FE
            JsonNode data = payOS.createPaymentLink(paymentData);
            System.out.println("data: " + data.toPrettyString());
            //  Trong json đã có check out url, lấy ra để set cho header
            String checkoutUrl = data.get("checkoutUrl").asText();

            httpServletResponse.setHeader("Location", checkoutUrl);
            httpServletResponse.setStatus(302);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/{orderId}")
    public ObjectNode getOrderById(@PathVariable("orderId") int orderId) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode respon = objectMapper.createObjectNode();

        try {
            JsonNode order = payOS.getPaymentLinkInfomation(orderId);
            respon.set("data", order);
            respon.put("error", 0);
            respon.put("message", "ok");
            return respon;
        } catch (Exception e) {
            e.printStackTrace();
            respon.put("error", -1);
            respon.put("message", e.getMessage());
            respon.set("data", null);
            return respon;
        }
    }

    @PutMapping("/{orderId}")
    public ObjectNode cancelOrder(@PathVariable("orderId") int orderId) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode respon = objectMapper.createObjectNode();
        try {
            JsonNode order = payOS.cancelPaymentLink(orderId, null);
            respon.set("data", order);
            respon.put("error", 0);
            respon.put("message", "ok");
            return respon;
        } catch (Exception e) {
            e.printStackTrace();
            respon.put("error", -1);
            respon.put("message", e.getMessage());
            respon.set("data", null);
            return respon;
        }
    }
    
    @PostMapping(path = "/confirm-webhook")
    public ObjectNode confirmWebhook(@RequestBody Map<String, String> requestBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode respon = objectMapper.createObjectNode();
        try {
            String str = payOS.confirmWebhook(requestBody.get("webhookUrl"));
            respon.set("data", null);
            respon.put("error", 0);
            respon.put("message", "ok");
            return respon;
        } catch (Exception e) {
            e.printStackTrace();
            respon.put("error", -1);
            respon.put("message", e.getMessage());
            respon.set("data", null);
            return respon;
        }
    }
}
