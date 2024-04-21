package com.example.ogani.controller;

import com.example.ogani.model.request.CreatePaymentLinkRequestBody;
import com.fasterxml.jackson.databind.JsonNode;
import com.lib.payos.PayOS;
import com.lib.payos.type.ItemData;
import com.lib.payos.type.PaymentData;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
public class CheckoutController {

    @RequestMapping(value = "/")
    public String Index() {
        return "index";
    }

    @RequestMapping(value = "/success")
    public String Success() {
        return "success";
    }

    @RequestMapping(value = "/cancel")
    public String Cancel() {
        return "cancel";
    }

    @PostMapping("/create-payment-link")
    public void checkout(@RequestBody CreatePaymentLinkRequestBody paymentLinkRequestBody,
            HttpServletResponse httpServletResponse) {
                System.out.println("Chạy vào đây");
        // try {
        //     List<ItemData> productList = paymentLinkRequestBody.getProductList();
        //     String description = paymentLinkRequestBody.getDescription();
        //     String returnUrl = paymentLinkRequestBody.getReturnUrl();
        //     String cancelUrl = paymentLinkRequestBody.getCancelUrl();
        //     int totalPrice = 0;
        //     // Gen order code
        //     String currentTimeString = String.valueOf(new Date().getTime());

        //     int orderCode = Integer.parseInt(currentTimeString.substring(currentTimeString.length() - 6));

        //     //  Tạo ra list sản phẩm
        //     List<ItemData> itemList = new ArrayList<>();

        //     //  Chạy vòng lặp để add item có được từ FE vào list sản phẩm
        //     for (ItemData product : productList) {
        //         ItemData item = new ItemData(product.getName(), product.getQuantity(), product.getPrice());
        //         totalPrice += product.getPrice();
        //         itemList.add(item);
        //     }
            
        //     //  Tạo ra các thông tin thanh toán
        //     PaymentData paymentData = new PaymentData(orderCode, totalPrice, description,
        //             itemList, cancelUrl, returnUrl);

        //     //  Tạo json thanh toán để trả về phía FE
        //     JsonNode data = payOS.createPaymentLink(paymentData);

        //     //  Trong json đã có check out url, lấy ra để set cho header
        //     String checkoutUrl = data.get("checkoutUrl").asText();

        //     httpServletResponse.setHeader("Location", checkoutUrl);
        //     httpServletResponse.setStatus(302);
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
    }
}