package com.hengtiansoft.business.wrkd.order.dto;

import java.io.Serializable;

public class KdOrderManagerExportDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderId;
    
    private String createDate;
    
    private String status;
    
    private String phone;
    
    private String productNames;
    
    private String productPrices;
    
    private String productCounts;
    
    private String productSpecs;
    
    private String orderType;
    
    private String actualAmount;
    
    private String returnAmount;
    
    private String addressInfo;
    
    private String contact;
    
    private String buyVip;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProductNames() {
        return productNames;
    }

    public void setProductNames(String productNames) {
        this.productNames = productNames;
    }

    public String getProductPrices() {
        return productPrices;
    }

    public void setProductPrices(String productPrices) {
        this.productPrices = productPrices;
    }

    public String getProductCounts() {
        return productCounts;
    }

    public void setProductCounts(String productCounts) {
        this.productCounts = productCounts;
    }

    public String getProductSpecs() {
        return productSpecs;
    }

    public void setProductSpecs(String productSpecs) {
        this.productSpecs = productSpecs;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(String actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(String returnAmount) {
        this.returnAmount = returnAmount;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getBuyVip() {
        return buyVip;
    }

    public void setBuyVip(String buyVip) {
        this.buyVip = buyVip;
    }
}
