package com.hengtiansoft.wrw.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "M_PAYMENT_LOG")
public class MPaymentLogEntity implements Serializable {

    private static final long serialVersionUID = -8023929309247094021L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long              id;

    @Column(name = "ORDER_ID")
    private String            orderId;

    @Column(name = "CONTENT")
    private String            content;

    @Column(name = "PAYMENT_TYPE")
    private String            paymentType;

    @Column(name = "CREATE_DATE")
    private Date              createDate;
    
    @Column(name = "PAY_ACCOUNT")
    private String              payAccount;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    @Override
    public String toString() {
        return "MPaymentLogEntity [id=" + id + ", orderId=" + orderId + ", content=" + content + ", paymentType=" + paymentType + ", createDate="
                + createDate + "]";
    }
}
