import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {
  email: string = 'lananh2k3@gmail.com';
  usefulLinks: string[] = [
    'Về chúng tôi',
    'Bảo mật thông tin mua hàng',
    'Thông tin giao hàng',
    'Chính sách bảo mật',
    'Dịch vụ',
    'Liên hệ',
  ];
  newsletterTitle: string = 'Tham gia cùng chúng tôi';
  newsletterDescription: string = 'Thông báo về thông tin khuyến mãi và mặt hàng mới';
  subscribeButton: string = 'Đăng ký';

  constructor() { }

  ngOnInit(): void {
  }
}
