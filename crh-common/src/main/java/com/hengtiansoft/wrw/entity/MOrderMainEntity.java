package com.hengtiansoft.wrw.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "M_ORDER_MAIN")
public class MOrderMainEntity implements Serializable {

    private static final long serialVersionUID = 7642131076931959691L;

    @Id
    @Column(name = "ORDER_ID")
    private String orderId;

    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "ORG_ID")
    private Long orgId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "IS_DELETE", insertable = false)
    private String isDelete;

    @Column(name = "MEMBER_NAME")
    private String memberName;

    @Column(name = "ORG_NAME")
    private String orgName;

    @Column(name = "FINISH_DATE")
    private Date finishDate;

    @Column(name = "SHIPMENT_TYPE")
    private String shipmentType;

    @Column(name = "PAYMENT_STATUS")
    private String paymentStatus;

    @Column(name = "PAYMENT_TYPE")
    private String paymentType;

    @Column(name = "PAY_DATE")
    private Date payDate;

    @Column(name = "TOTAL_AMOUNT")
    private Long totalAmount;

    @Column(name = "ACTUAL_AMOUNT")
    private Long actualAmount;

    @Column(name = "COUPON_AMOUNT")
    private Long couponAmount;

    @Column(name = "ACCT_AMOUNT")
    private Long acctAmount;

    @Column(name = "SHIP_AMOUNT")
    private Long shipAmount;

    @Column(name = "TOTAL_NUM")
    private Integer totalNum;

    @Column(name = "IF_EXCEPTION")
    private String ifException;

    @Column(name = "ADDRESS_ID")
    private Long addressId;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "CONTACT")
    private String contact;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "REGION_ID")
    private Integer regionId;

    @Column(name = "EXPRESS_NAME")
    private String expressName;

    @Column(name = "EXPRESS_NUM")
    private String expressNum;

    @Column(name = "RECEVING_TIME")
    private String recevingTime;

    @Column(name = "ASSIGN_DATE")
    private Date assignDate;

    @Column(name = "SHIPMENT_DATE")
    private Date shipmentDate;

    @Column(name = "RECEIVING_DATE")
    private Date receivingDate;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "CANCEL_REASON")
    private String cancelReason;

    @Column(name = "PRESSED_FLAG")
    private String pressedFlag;

    @Column(name = "SOURCE")
    private String source;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "CREATE_ID")
    private Long createId;

    @Column(name = "MODIFY_DATE")
    private Date modifyDate;

    @Column(name = "MODIFY_ID")
    private Long modifyId;

    @Column(name = "TSN")
    private String tsn;

    @Column(name = "RESEND_FLAG")
    private String resendFlag;
    
    @Column(name="QUICKPAY_REQ_ID")
    private Long quickpayReqId;

    @Column(name="IS_PREPAY")
    private String isPrepay;
    

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(String shipmentType) {
        this.shipmentType = shipmentType;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
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

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Long actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Long getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Long couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Long getAcctAmount() {
        return acctAmount;
    }

    public void setAcctAmount(Long acctAmount) {
        this.acctAmount = acctAmount;
    }

    public Long getShipAmount() {
        return shipAmount;
    }

    public void setShipAmount(Long shipAmount) {
        this.shipAmount = shipAmount;
    }

    public String getIfException() {
        return ifException;
    }

    public void setIfException(String ifException) {
        this.ifException = ifException;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public Date getReceivingDate() {
        return receivingDate;
    }

    public void setReceivingDate(Date receivingDate) {
        this.receivingDate = receivingDate;
    }

    public String getTsn() {
        return tsn;
    }

    public void setTsn(String tsn) {
        this.tsn = tsn;
    }

    public String getPressedFlag() {
        return pressedFlag;
    }

    public void setPressedFlag(String pressedFlag) {
        this.pressedFlag = pressedFlag;
    }

    public String getRecevingTime() {
        return recevingTime;
    }

    public void setRecevingTime(String recevingTime) {
        this.recevingTime = recevingTime;
    }

    public String getResendFlag() {
        return resendFlag;
    }

    public void setResendFlag(String resendFlag) {
        this.resendFlag = resendFlag;
    }

    public Date getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
    }
    

    public Long getQuickpayReqId() {
        return quickpayReqId;
    }

    public void setQuickpayReqId(Long quickpayReqId) {
        this.quickpayReqId = quickpayReqId;
    }

    @Override
    public String toString() {
        return "MOrderMainEntity [orderId=" + orderId + ", memberId=" + memberId + ", orgId=" + orgId + ", status="
                + status + ", memberName=" + memberName + ", orgName=" + orgName + ", finishDate=" + finishDate
                + ", shipmentType=" + shipmentType + ", paymentStatus=" + paymentStatus + ", paymentType="
                + paymentType + ", payDate=" + payDate + ", totalAmount=" + totalAmount + ", actualAmount="
                + actualAmount + ", couponAmount=" + couponAmount + ", acctAmount=" + acctAmount + ", shipAmount="
                + shipAmount + ", totalNum=" + totalNum + ", ifException=" + ifException + ", addressId=" + addressId
                + ", address=" + address + ", contact=" + contact + ", phone=" + phone + ", regionId=" + regionId
                + ", expressName=" + expressName + ", expressNum=" + expressNum + ", recevingTime=" + recevingTime
                + ", assignDate=" + assignDate + ", shipmentDate=" + shipmentDate + ", receivingDate=" + receivingDate
                + ", remark=" + remark + ", cancelReason=" + cancelReason + ", pressedFlag=" + pressedFlag
                + ", source=" + source + ", createDate=" + createDate + ", createId=" + createId + ", modifyDate="
                + modifyDate + ", modifyId=" + modifyId + ", tsn=" + tsn + ", resendFlag=" + resendFlag + "]";
    }

    public String getIsPrepay() {
        return isPrepay;
    }

    public void setIsPrepay(String isPrepay) {
        this.isPrepay = isPrepay;
    }
}
