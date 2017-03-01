package com.hengtiansoft.wrw.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hengtiansoft.common.domain.BaseEntity;

@Entity
@Table(name = "M_ORDER_BILLING")
public class MOrderBillingEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1702506672303650203L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="BILLING_ID")
    private Long              billingId;

    @Column(name = "ORDER_ID")
    private String            orderId;

    @Column(name = "BILL_TYPE")
    private String            billType;

    @Column(name = "STATUS")
    private String            status;

    @Column(name = "PAY_TIME")
    private Date              payTime;

    @Column(name = "AMOUNT")
    private Long              amount;

    @Column(name = "ACTUAL_AMOUNT")
    private Long              actualAmount;
    
    @Column(name = "TSN")
    private String            tsn;

    public Long getBillingId() {
        return billingId;
    }

    public void setBillingId(Long billingId) {
        this.billingId = billingId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Long actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getTsn() {
        return tsn;
    }

    public void setTsn(String tsn) {
        this.tsn = tsn;
    }

    @Override
    public String toString() {
        return "MOrderBillingEntity [billingId=" + billingId + ", orderId=" + orderId + ", billType=" + billType + ", status=" + status + ", payTime="
                + payTime + ", amount=" + amount + ", actualAmount=" + actualAmount + ", tsn=" + tsn + "]";
    }

}
