package com.hengtiansoft.business.order.dto;

import com.hengtiansoft.common.dto.PagingDto;

/**
 * 
 * Class Name: OrderManagerPageDto Description: 订单管理分页dto
 * 
 * @author kangruan
 *
 */
public class OrderManagerPageDto extends PagingDto<OrderManagerDto> {

    private static final long serialVersionUID = -5979220384663023510L;

    // 订单编号
    private String orderId;

    // 开始时间
    private String startDate;

    // 结束时间
    private String endDate;

    // 配送方式
    private String shipmentType;

    // 联系方式
    private String phone;

    // 订单状态
    private String status;

    // 支付状态
    private String paymentStatus;

    // 是否为异常订单（ 0:非异常, 1:异常）
    private String exception;

    // 查询超时订单 为1时查询超时订单
    private String daleyOrder;
    
    // 是否再次派单 (0:否, 1:是)
    private String resendFlag;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(String shipmentType) {
        this.shipmentType = shipmentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getDaleyOrder() {
        return daleyOrder;
    }

    public void setDaleyOrder(String daleyOrder) {
        this.daleyOrder = daleyOrder;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getResendFlag() {
        return resendFlag;
    }

    public void setResendFlag(String resendFlag) {
        this.resendFlag = resendFlag;
    }

    @Override
    public String toString() {
        return "OrderManagerPageDto [orderId=" + orderId + ", startDate=" + startDate + ", endDate=" + endDate
                + ", shipmentType=" + shipmentType + ", phone=" + phone + ", status=" + status + ", paymentStatus="
                + paymentStatus + ", exception=" + exception + ", daleyOrder=" + daleyOrder + ", resendFlag="
                + resendFlag + "]";
    }
}
