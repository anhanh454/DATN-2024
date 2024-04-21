package com.example.ogani.model.classes;

import java.util.List;

public class PaymentData {
    private int orderCode;
    private int totalPrice;
    private String description;
    private List<ItemData> items;
    private String cancelUrl;
    private String returnUrl;
    private String signature = null;
    private String buyerName = null;
    private String buyerEmail = null;
    private String buyerPhone = null;
    private String buyerAddress = null;
    private Integer expiredAt = null;

    public PaymentData(){

    }

    public PaymentData(int orderCode, int totalPrice, String description, List<ItemData> items, String cancelUrl,
            String returnUrl) {
        this.orderCode = orderCode;
        this.description = description;
        this.items = items;
        this.cancelUrl = cancelUrl;
        this.returnUrl = returnUrl;
        this.totalPrice = totalPrice;
    }

    public void setOrderCode(int orderCode) {
        this.orderCode = orderCode;
    }

    public void setAmount(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setItems(List<ItemData> items) {
        this.items = items;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public void setExpiredAt(Integer expiredAt) {
        this.expiredAt = expiredAt;
    }

    public int getOrderCode() {
        return this.orderCode;
    }

    public String getDescription() {
        return this.description;
    }

    public List<ItemData> getItems() {
        return this.items;
    }

    public String getCancelUrl() {
        return this.cancelUrl;
    }

    public String getReturnUrl() {
        return this.returnUrl;
    }

    public String getSignature() {
        return this.signature;
    }

    public String getBuyerName() {
        return this.buyerName;
    }

    public String getBuyerEmail() {
        return this.buyerEmail;
    }

    public String getBuyerPhone() {
        return this.buyerPhone;
    }

    public String getBuyerAddress() {
        return this.buyerAddress;
    }

    public Integer getExpiredAt() {
        return this.expiredAt;
    }

    public int getTotalPrice() {
        return this.totalPrice;
    }
}
