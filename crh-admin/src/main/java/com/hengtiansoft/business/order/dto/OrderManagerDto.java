package com.hengtiansoft.business.order.dto;

import java.text.DecimalFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hengtiansoft.wrw.enums.OrderStatus;
import com.hengtiansoft.wrw.enums.PaymentStatus;
import com.hengtiansoft.wrw.enums.PaymentType;
import com.hengtiansoft.wrw.enums.ShipmentType;

public class OrderManagerDto {
    @JsonIgnore
    private static DecimalFormat decimalFormat = new DecimalFormat("##,##0.00");

    // 订单编号
    private String orderId;

    private Long orgId;

    // 订单金额
    private Long totalAmount;

    // 实付金额
    private Long actualAmount;

    // 优惠券抵扣金额
    private Long couponAmount;

    // 配送费
    private Long shipAmount;

    // 客户姓名
    private String memberName;

    // 联系方式
    private String phone;

    // 快递名称
    private String expressName;

    // 快递单号
    private String expressNum;

    // 发货时间
    private Date shipmentDate;

    // 客户要求配送时间
    private String recevingTime;

    // 收货时间
    private Date receivingDate;

    // 订单时间
    private Date createDate;

    // 订单状态
    private String status;

    // 订单状态 描述
    private String statusStr;

    // 支付状态
    private String paymentStatus;

    // 支付状态 描述
    private String paymentStatusStr;

    // 支付方式
    private String paymentType;

    // 支付方式 描述
    private String paymentTypeStr;

    // 配送状态
    private String shipmentType;

    // 配送状态 描述
    private String shipmentTypeStr;

    // 终端配送商
    private String orgName;

    // 收货地址
    private String address;

    // 联系人
    private String contact;

    // 是否 已催单 0：未催单 1：已催单
    private String pressedFlag;

    // 是否为异常订单 0:正常订单 ,1:异常订单
    private String ifException;

    // 是否 超时 0 未超时 1 已超时
    private String delay;

    // 是否再次派单（0：否，1：是）
    private String resendFlag;

    // 支付时间
    private Date payDate;

    // 订单金额
    private String totalAmountDisplay;

    // 实付金额
    private String actualAmountDisplay;

    // 优惠券抵扣金额
    private String couponAmountDisplay;

    // 配送费显示
    private String shipAmountDisplay;

    // 退款账号
    private String returnAmountNo;

    // 退款金额
    private String returnAmount;

    // 退款运费
    private String returnShipAmount;

    // 优惠券退款
    private String returnCounponAmount;
    
    private String source;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusStr() {
        statusStr = OrderStatus.getValue(this.getStatus());
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentStatusStr() {
        paymentStatusStr = PaymentStatus.getValue(this.getPaymentStatus());
        return paymentStatusStr;
    }

    public void setPaymentStatusStr(String paymentStatusStr) {
        this.paymentStatusStr = paymentStatusStr;
    }

    public String getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(String shipmentType) {
        this.shipmentType = shipmentType;
    }

    public String getShipmentTypeStr() {
        shipmentTypeStr = ShipmentType.getValue(this.getShipmentType());
        return shipmentTypeStr;
    }

    public void setShipmentTypeStr(String shipmentTypeStr) {
        this.shipmentTypeStr = shipmentTypeStr;
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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentTypeStr() {
        paymentTypeStr = PaymentType.getValue(this.getPaymentType());
        return paymentTypeStr;
    }

    public void setPaymentTypeStr(String paymentTypeStr) {
        this.paymentTypeStr = paymentTypeStr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getIfException() {
        return ifException;
    }

    public void setIfException(String ifException) {
        this.ifException = ifException;
    }

    public String getPressedFlag() {
        return pressedFlag;
    }

    public void setPressedFlag(String pressedFlag) {
        this.pressedFlag = pressedFlag;
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

    public String getTotalAmountDisplay() {
        if (totalAmount == null) {
            totalAmount = 0L;
        }
        totalAmountDisplay = decimalFormat.format(totalAmount.doubleValue() / 100);
        return totalAmountDisplay;
    }

    public void setTotalAmountDisplay(String totalAmountDisplay) {
        this.totalAmountDisplay = totalAmountDisplay;
    }

    public String getActualAmountDisplay() {
        if (actualAmount == null) {
            actualAmount = 0L;
        }
        actualAmountDisplay = decimalFormat.format(actualAmount.doubleValue() / 100);
        return actualAmountDisplay;
    }

    public void setActualAmountDisplay(String actualAmountDisplay) {
        this.actualAmountDisplay = actualAmountDisplay;
    }

    public String getCouponAmountDisplay() {
        if (couponAmount == null) {
            couponAmount = 0L;
        }
        couponAmountDisplay = decimalFormat.format(couponAmount.doubleValue() / 100);
        return couponAmountDisplay;
    }

    public void setCouponAmountDisplay(String couponAmountDisplay) {
        this.couponAmountDisplay = couponAmountDisplay;
    }

    public Long getShipAmount() {
        return shipAmount;
    }

    public void setShipAmount(Long shipAmount) {
        this.shipAmount = shipAmount;
    }

    public String getShipAmountDisplay() {
        if (shipAmount == null) {
            shipAmount = 0L;
        }
        shipAmountDisplay = decimalFormat.format(shipAmount.doubleValue() / 100);
        return shipAmountDisplay;
    }

    public void setShipAmountDisplay(String shipAmountDisplay) {
        this.shipAmountDisplay = shipAmountDisplay;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRecevingTime() {
        return recevingTime;
    }

    public void setRecevingTime(String recevingTime) {
        this.recevingTime = recevingTime;
    }

    public String getReturnAmountNo() {
        return returnAmountNo;
    }

    public void setReturnAmountNo(String returnAmountNo) {
        this.returnAmountNo = returnAmountNo;
    }

    public String getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(String returnAmount) {
        this.returnAmount = returnAmount;
    }

    public String getReturnShipAmount() {
        return returnShipAmount;
    }

    public void setReturnShipAmount(String returnShipAmount) {
        this.returnShipAmount = returnShipAmount;
    }

    public String getReturnCounponAmount() {
        return returnCounponAmount;
    }

    public void setReturnCounponAmount(String returnCounponAmount) {
        this.returnCounponAmount = returnCounponAmount;
    }

    public static DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }

    public static void setDecimalFormat(DecimalFormat decimalFormat) {
        OrderManagerDto.decimalFormat = decimalFormat;
    }

    public String getResendFlag() {
        return resendFlag;
    }

    public void setResendFlag(String resendFlag) {
        this.resendFlag = resendFlag;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
