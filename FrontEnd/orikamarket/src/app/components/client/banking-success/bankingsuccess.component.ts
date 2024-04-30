import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { MessageService } from "primeng/api";
import { CartService } from "src/app/_service/cart.service";
import { SseService } from "src/app/_service/sse.service";

@Component({
  selector: 'app-bankingsuccess',
  templateUrl: './bankingsuccess.component.html',
  styleUrls: ['./bankingsucess.component.css'],
  providers: [MessageService]
})

export class BankingSuccessComponent implements OnInit {
  orderId: string = '';
  totalPrice: string = '';
  paymentTime: string = '';
  transactionId: string = '';
  formattedPaymentTime: string = '';

  constructor(private route: ActivatedRoute, private cartService: CartService) { }

  ngOnInit(): void {
    this.cartService.clearCart();
    this.route.queryParams.subscribe(params => {
      this.orderId = params['orderId'];
      // Nhân totalPrice với 0.01 trước khi gán vào biến
      this.totalPrice = (parseFloat(params['totalPrice']) * 0.01).toFixed(2);
      this.paymentTime = params['paymentTime'];
      this.transactionId = params['transactionId'];
      this.formatPaymentTime(); // Gọi hàm để format paymentTime
    });
  }

  // Phương thức để định dạng lại thời gian thanh toán
  formatPaymentTime(): void {
    // Chuyển đổi số thành chuỗi và cắt chuỗi để lấy ngày, tháng, năm
    let paymentTimeString: string = this.paymentTime;
    let day: string = paymentTimeString.substring(6, 8);
    let month: string = paymentTimeString.substring(4, 6);
    let year: string = paymentTimeString.substring(0, 4);

    // Định dạng lại thành 'DD-MM-YYYY'
    this.formattedPaymentTime = `${day}/${month}/${year}`;
  }
}