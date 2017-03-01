package com.hengtiansoft.wrw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hengtiansoft.common.domain.BaseEntity;

@Entity
@Table(name = "kd_order_main")
public class KdOrderMainEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "ORDER_ID")
    private String orderId;
    
    @Column(name = "STATUS")
    private String status;
    
    @Column(name = "TOTAL_AMOUNT")
    private Long totalAmount;
    
    @Column(name = "ACTUAL_AMOUNT")
    private Long actualAmount;
    
    @Column(name = "DISCOUNT_AMOUNT")
    private Long discountAmount;
    
    @Column(name = "TYPE")
    private String type;
    
    @Column(name = "ADDRESS_INFO")
    private String addressInfo;
    
    @Column(name = "PHONE")
    private String phone;
    
    @Column(name = "CONTACT")
    private String contact;
    
    @Column(name = "EXPRESS_INFO")
    private String expressInfo;
    
    @Column(name = "SHIP_AMOUNT")
    private Long shipAmount;
    
    @Column(name = "PAYMENT_TYPE")
    private String paymentType;
    
    @Column(name = "PAY_DATE")
    private Date payDate;
    
    @Column(name = "TSN")
    private String tsn;
    
    @Column(name = "comment")
    private String comment;
    
    @Column(name = "MEMBER_ID")
    private Long memberId;
    
    @Column(name = "IS_PREPAY")
    private String isPrepay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Long actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getExpressInfo() {
        return expressInfo;
    }

    public void setExpressInfo(String expressInfo) {
        this.expressInfo = expressInfo;
    }

    public Long getShipAmount() {
        return shipAmount;
    }

    public void setShipAmount(Long shipAmount) {
        this.shipAmount = shipAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getTsn() {
        return tsn;
    }

    public void setTsn(String tsn) {
        this.tsn = tsn;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getIsPrepay() {
        return isPrepay;
    }

    public void setIsPrepay(String isPrepay) {
        this.isPrepay = isPrepay;
    }
}
