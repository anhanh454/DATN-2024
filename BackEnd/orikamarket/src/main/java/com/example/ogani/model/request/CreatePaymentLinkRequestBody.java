package com.example.ogani.model.request;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import com.example.ogani.model.classes.ItemData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentLinkRequestBody {
  @NotNull(message="Họ khách hàng rỗng")
  @NotEmpty(message="Họ khách hàng rỗng")
  @Size(min=3,max=50,message="Họ khách hàng từ 3-50 ký tự")
  private String firstname;
  
  @NotNull(message="Tên khách hàng rỗng")
  @NotEmpty(message="Tên khách hàng rỗng")
  @Size(min=3,max=50,message="Tên khách hàng từ 3-50 ký tự")
  private String lastname;

  @NotNull(message="Tên quốc gia rỗng")
  @NotEmpty(message="Tên quốc gia rỗng")
  private String country;

  @NotNull(message="Tên địa chỉ rỗng")
  @NotEmpty(message="Tên địa chỉ rỗng")
  private String address;

  @NotNull(message="Tên quốc gia rỗng")
  @NotEmpty(message="Tên quốc gia rỗng")
  private String town;

  @NotNull(message="Tên khu vực rỗng")
  @NotEmpty(message="Tên khu vực rỗng")
  private String state;

  @NotNull(message ="Mã bưu điện rỗng")
  @NotEmpty(message ="Mã bưu điện rỗng")
  private long postCode;

  @NotNull(message = "Email rỗng")
  @NotEmpty(message = "Email rỗng")
  @Email(message = "Email không đúng định dạng")
  private String email;
   
  @NotNull(message="Số điện thoại rỗng")
  @NotEmpty(message="Số điện thoại rỗng")
  private String phone;

  private String note;

  private long totalPrice;
  
  private List<ItemData> productList;
  private String description;
  private String returnUrl;
  private String cancelUrl;
}