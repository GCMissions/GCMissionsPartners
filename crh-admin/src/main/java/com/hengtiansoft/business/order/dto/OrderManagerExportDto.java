package com.hengtiansoft.business.order.dto;

import java.io.Serializable;

public class OrderManagerExportDto implements Serializable{

    /**
    * Variables Name: serialVersionUID
    * Description: TODO
    * Value Description: TODO
    */
    private static final long serialVersionUID = 1L;
    
    private String orderId;
    
    private String createDate;
    
    private String status;
    
    private String menberName;
    //客户昵称
    private String custName;
    
    private String memberRegion;
    
    private String menberPhone;
 
    private String totalAmunt;
    
    private String couponAmunt;
    
    private String actualAmount;
    
    private String paymentType;
    
    private String payDate;
    
    private String TSN;
    
    private String returnAmunt;
    
    private String productName;
    
    private String orgName;
    
    private String specInfo;
    
    private String productPrice;
    
    private String num;
    
    private String requireField;
    
    
    private String remark;
    
    private String actDate;
    
    /**
     * 退款时间
     */
    private String returnDate;
    
    /**
     * 是否购买VIP
     */
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

    public String getMenberName() {
        return menberName;
    }

    public void setMenberName(String menberName) {
        this.menberName = menberName;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    
    public String getMemberRegion() {
        return memberRegion;
    }

    public void setMemberRegion(String memberRegion) {
        this.memberRegion = memberRegion;
    }

    public String getMenberPhone() {
        return menberPhone;
    }

    public void setMenberPhone(String menberPhone) {
        this.menberPhone = menberPhone;
    }


    public String getTotalAmunt() {
        return totalAmunt;
    }

    public void setTotalAmunt(String totalAmunt) {
        this.totalAmunt = totalAmunt;
    }

    public String getCouponAmunt() {
        return couponAmunt;
    }

    public void setCouponAmunt(String couponAmunt) {
        this.couponAmunt = couponAmunt;
    }

    public String getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(String actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getTSN() {
        return TSN;
    }

    public void setTSN(String tSN) {
        TSN = tSN;
    }

    public String getReturnAmunt() {
        return returnAmunt;
    }

    public void setReturnAmunt(String returnAmunt) {
        this.returnAmunt = returnAmunt;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getSpecInfo() {
        return specInfo;
    }

    public void setSpecInfo(String specInfo) {
        this.specInfo = specInfo;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getRequireField() {
        return requireField;
    }

    public void setRequireField(String requireField) {
        this.requireField = requireField;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getActDate() {
        return actDate;
    }

    public void setActDate(String actDate) {
        this.actDate = actDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getBuyVip() {
        return buyVip;
    }

    public void setBuyVip(String buyVip) {
        this.buyVip = buyVip;
    }
    
}
