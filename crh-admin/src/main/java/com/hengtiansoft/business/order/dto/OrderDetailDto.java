package com.hengtiansoft.business.order.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengtiansoft.common.serialize.CustomDateSerializer;
import com.hengtiansoft.common.serialize.CustomMoneySerializer;

public class OrderDetailDto implements Serializable {

    private static final long serialVersionUID = -4406847105189940698L;

    // 商品编号
    private String proudctCode;

    // 商品名称
    private String proudctName;

    // 服务商名称
    private String orgName;

    // 商品价格
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Long price;

    // 活动时间
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date actDate;

    // 商品规格（用户选择的）
    private String specInfo;

    // 必填信息
    private String personalInfo;
    
    private String remark;
    
    private Long productId;
    
    private String orderStatus;
    
    // 是否能退款
    private String isCanReturn;
    
    // 退款金额
    private String returnPrice;
    
    // 商品总数量
    private Integer productCount;
    
    private String returnCount;
    
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Long shipAmount;
    
    private String singlePrice;
    
    // 剩余可退价格
    private String restPrice;

    public OrderDetailDto() {
    }

    public String getProudctCode() {
        return proudctCode;
    }

    public void setProudctCode(String proudctCode) {
        this.proudctCode = proudctCode;
    }

    public String getProudctName() {
        return proudctName;
    }

    public void setProudctName(String proudctName) {
        this.proudctName = proudctName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Date getActDate() {
        return actDate;
    }

    public void setActDate(Date actDate) {
        this.actDate = actDate;
    }

    public String getSpecInfo() {
        return specInfo;
    }

    public void setSpecInfo(String specInfo) {
        this.specInfo = specInfo;
    }

    public String getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(String personalInfo) {
        this.personalInfo = personalInfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getIsCanReturn() {
        return isCanReturn;
    }

    public void setIsCanReturn(String isCanReturn) {
        this.isCanReturn = isCanReturn;
    }

    public String getReturnPrice() {
        return returnPrice;
    }

    public void setReturnPrice(String returnPrice) {
        this.returnPrice = returnPrice;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public Long getShipAmount() {
        return shipAmount;
    }

    public void setShipAmount(Long shipAmount) {
        this.shipAmount = shipAmount;
    }

    public String getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(String returnCount) {
        this.returnCount = returnCount;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(String singlePrice) {
        this.singlePrice = singlePrice;
    }

    public String getRestPrice() {
        return restPrice;
    }

    public void setRestPrice(String restPrice) {
        this.restPrice = restPrice;
    }
}
