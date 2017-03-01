package com.hengtiansoft.business.activity.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class OrgOrderDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderId;
    
    private String phone;
    
    private String productName;
    
    private String productType;
    
    private String spec;
    
    private String totalPrice;
    
    private String count;
    
    // 运费
    private String shipAmount;
    
    // 优惠金额
    private String couponAmount;
    
    // 实付金额
    private String actualAmount;
    
    private String orderStatus;
    
    // 是否退款
    private String isReturn;
    
    // 退款金额
    private String returnAmount;
    
    // 备注
    private String remark;
    
    private List<Map<String, String>> infoMap;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getShipAmount() {
        return shipAmount;
    }

    public void setShipAmount(String shipAmount) {
        this.shipAmount = shipAmount;
    }

    public String getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(String actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(String isReturn) {
        this.isReturn = isReturn;
    }

    public String getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(String returnAmount) {
        this.returnAmount = returnAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Map<String, String>> getInfoMap() {
        return infoMap;
    }

    public void setInfoMap(List<Map<String, String>> infoMap) {
        this.infoMap = infoMap;
    }
}
