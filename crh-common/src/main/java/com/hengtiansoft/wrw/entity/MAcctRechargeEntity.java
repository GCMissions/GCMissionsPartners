package com.hengtiansoft.wrw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hengtiansoft.common.domain.BaseEntity;

@Entity
@Table(name = "M_ACCT_RECHARGE")
public class MAcctRechargeEntity extends BaseEntity {

    private static final long serialVersionUID = -5825434003949240827L;

    @Id
    @Column(name = "RECHARGE_ID")
    private String            rechargeId;

    @Column(name = "ACCT_ID")
    private Long              acctId;

    @Column(name = "MEMBER_ID")
    private Long              memberId;

    @Column(name = "CONFIG_ID")
    private Long              configId;

    @Column(name = "AMOUNT")
    private Long              amount;

    @Column(name = "ACTUAL_AMOUNT")
    private Long              actualAmount;

    @Column(name = "DISCOUNT")
    private Long              discount;

    @Column(name = "PAYMENT_TYPE")
    private String            paymentType;

    @Column(name = "PAY_STATUS")
    private String            payStatus;

    @Column(name = "PAY_DATE")
    private Date              payDate;

    @Column(name = "TSN")
    private String            tsn;

    public String getRechargeId() {
        return rechargeId;
    }

    public void setRechargeId(String rechargeId) {
        this.rechargeId = rechargeId;
    }

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
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

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
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

    @Override
    public String toString() {
        return "MAcctRechargeEntity [rechargeId=" + rechargeId + ", acctId=" + acctId + ", memberId=" + memberId + ", configId=" + configId
                + ", amount=" + amount + ", actualAmount=" + actualAmount + ", discount=" + discount + ", paymentType=" + paymentType
                + ", payStatus=" + payStatus + ", payDate=" + payDate + ", tsn=" + tsn + "]";
    }
}
