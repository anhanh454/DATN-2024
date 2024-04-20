import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { faBars, faHeart, faPhone, faShoppingBag } from '@fortawesome/free-solid-svg-icons';
import { MessageService } from 'primeng/api';
import { Order } from 'src/app/_class/order';
import { OrderDetail } from 'src/app/_class/order-detail';

import { CartService } from 'src/app/_service/cart.service';
import { OrderService } from 'src/app/_service/order.service';
import { StorageService } from 'src/app/_service/storage.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
  providers: [MessageService]

})
export class CheckoutComponent implements OnInit {
  heart = faHeart;
  bag = faShoppingBag;
  phone = faPhone;
  bars = faBars;
  showDepartment = false;
  order = new Order();
  listOrderDetail: any[] = [];
  username !: string;
  invalidForm: boolean = false;

  // Trường dữ liệu để lưu trạng thái của phương thức thanh toán đã chọn
  selectedPaymentMethod: string = '';


  orderForm: any = {
    firstname: null,
    lastname: null,
    country: null,
    addrest: null,
    town: null,
    state: null,
    postCode: null,
    email: null,
    phone: null,
    note: null
  }

  constructor(public cartService: CartService, private orderService: OrderService, private storageService: StorageService, private messageService: MessageService) {

  }
  ngOnInit(): void {
    this.selectedPaymentMethod = 'cash'; // Mặc định chọn tiền mặt
    this.username = this.storageService.getUser().username;
    this.cartService.getItems();
    console.log(this.username);
  }

  showDepartmentClick() {
    this.showDepartment = !this.showDepartment;
  }

  togglePaymentMethod(method: string) {
    // Kiểm tra xem phương thức thanh toán nào được chọn
    if (this.selectedPaymentMethod != '') {
      // Nếu đã chọn rồi thì gán giá trị của selectedPaymentMethod về null
      this.selectedPaymentMethod = method;
      console.log(this.selectedPaymentMethod);
    }
  }

  placeOrder() {
    // Kiểm tra trường hợp null
    if (!this.orderForm.lastname || !this.orderForm.firstname || !this.orderForm.country || !this.orderForm.address || !this.orderForm.town || !this.orderForm.state || !this.orderForm.postCode || !this.orderForm.phone || !this.orderForm.email) {
      this.invalidForm = true; // Đánh dấu form là không hợp lệ
      this.showWarn("Vui lòng nhập đầy đủ thông tin giao hàng!");
      return; // Dừng hàm nếu form không hợp lệ
    }

    //  Người dùng sẽ trả tiền mặt hoặc là thanh toán bằng internet banking
    switch (this.selectedPaymentMethod) {
      case 'cash':
        this.placeOrderByCash();
        break;
      case 'banking':
        this.placeOrderPayOs();
        break;
    }
  }

  placeOrderByCash() {
    this.cartService.items.forEach(res => {
      let orderDetail: OrderDetail = new OrderDetail;
      orderDetail.name = res.name;
      orderDetail.price = res.price;
      orderDetail.quantity = res.quantity;
      orderDetail.subTotal = res.subTotal;
      this.listOrderDetail.push(orderDetail);
    })

    const { firstname, lastname, country, address, town, state, postCcode, phone, email, note } = this.orderForm;

    this.orderService.placeOrderByCash(firstname, lastname, country, address, town, state, postCcode, phone, email, note, this.listOrderDetail, this.username).subscribe({
      next: res => {
        //  Clear giỏ hàng
        this.cartService.clearCart();
        this.showSuccess("Thanh toán thành công!");
      }, error: err => {
        console.log(err);
        this.showError("Có lỗi xảy ra!");
      }
    })
  }

  placeOrderPayOs() {

  }

  showSuccess(text: string) {
    this.messageService.add({ severity: 'success', summary: 'Thành công', detail: text });
  }

  showError(text: string) {
    this.messageService.add({ severity: 'error', summary: 'Thất bại', detail: text });
  }

  showWarn(text: string) {
    this.messageService.add({ severity: 'warn', summary: 'Cảnh báo', detail: text });
  }


}
