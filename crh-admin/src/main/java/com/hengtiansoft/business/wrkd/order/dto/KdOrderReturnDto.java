package com.hengtiansoft.business.wrkd.order.dto;

import java.io.Serializable;
import java.util.List;

public class KdOrderReturnDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String returnType;
    
    private String orderId;
    
    // 订单金额
    private String totalAmount;
    
    // 实付金额
    private String actualAmount;
    
    // 订单状态
    private String status;
    
    private String phone;
    
    // 运费
    private String shipAmount;
    
    private String createDate;
    
    // 订单类型
    private String orderType;
    
    // 快递信息
    private String expressInfo;
    
    private String addressInfo;
    
    private String returnInfo;
    
    private String showReturnInfo;
    
    private String orderTypeCode;
    
    // 备注信息
    private List<KdOrderRemarkDto> remarks;
    
    private String buyVip;

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(String actualAmount) {
        this.actualAmount = actualAmount;
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

    public String getShipAmount() {
        return shipAmount;
    }

    public void setShipAmount(String shipAmount) {
        this.shipAmount = shipAmount;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getExpressInfo() {
        return expressInfo;
    }

    public void setExpressInfo(String expressInfo) {
        this.expressInfo = expressInfo;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    public String getReturnInfo() {
        return returnInfo;
    }

    public void setReturnInfo(String returnInfo) {
        this.returnInfo = returnInfo;
    }

    public List<KdOrderRemarkDto> getRemarks() {
        return remarks;
    }

    public void setRemarks(List<KdOrderRemarkDto> remarks) {
        this.remarks = remarks;
    }

    public String getShowReturnInfo() {
        return showReturnInfo;
    }

    public void setShowReturnInfo(String showReturnInfo) {
        this.showReturnInfo = showReturnInfo;
    }

    public String getOrderTypeCode() {
        return orderTypeCode;
    }

    public void setOrderTypeCode(String orderTypeCode) {
        this.orderTypeCode = orderTypeCode;
    }

    public String getBuyVip() {
        return buyVip;
    }

    public void setBuyVip(String buyVip) {
        this.buyVip = buyVip;
    }
}
