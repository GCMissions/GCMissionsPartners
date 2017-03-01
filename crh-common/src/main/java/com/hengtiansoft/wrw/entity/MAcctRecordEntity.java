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
@Table(name = "M_ACCT_RECORD")
public class MAcctRecordEntity implements Serializable {

    private static final long serialVersionUID = 8744355945237742107L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RECORD_ID")
    private Long   recordId;

    @Column(name = "ACCT_ID")
    private Long   acctId;

    @Column(name = "ORDER_ID")
    private String orderId;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "CHANGE_VALUE")
    private Long   changeValue;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "CREATE_DATE")
    private Date   createDate;

    @Column(name = "CREATE_ID")
    private Long   createId;

    @Column(name = "MODIFY_DATE")
    private Date   modifyDate;

    @Column(name = "MODIFY_ID")
    private Long   modifyId;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getAcctId() {
        return acctId;
    }

    public void setAcctId(Long acctId) {
        this.acctId = acctId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getChangeValue() {
        return changeValue;
    }

    public void setChangeValue(Long changeValue) {
        this.changeValue = changeValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Long getModifyId() {
        return modifyId;
    }

    public void setModifyId(Long modifyId) {
        this.modifyId = modifyId;
    }

    @Override
    public String toString() {
        return "AcctRecordEntity [recordId=" + recordId + ", acctId=" + acctId + ", orderId=" + orderId + ", type=" + type + ", changeValue="
                + changeValue + ", remark=" + remark + ", createDate=" + createDate + ", createId=" + createId + ", modifyDate=" + modifyDate
                + ", modifyId=" + modifyId + "]";
    }
}
