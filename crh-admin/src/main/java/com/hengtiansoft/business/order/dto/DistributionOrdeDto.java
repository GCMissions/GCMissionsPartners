package com.hengtiansoft.business.order.dto;

import java.util.List;
public class DistributionOrdeDto {
    // 订单编号
    private String orderId;

    // 订单金额
    private String totalAmount;

    // 客户姓名
    private String memberName;

    // 联系方式
    private String phone;

    // 订单时间
    private String createDate;

    // 订单状态
    private String status;

    // 订单状态 描述
    private String statusStr;

    // 支付状态
    private String paymentStatus;

    // 支付状态 描述
    private String paymentStatusStr;

    // 配送状态
    private String shipmentType;

    //******订单详情*********
    //客户收货时间
    private String recevingTime;
    
    //完成时间
    private String finishDate;
    
    //收货地址
    private String address;
    
    //发货时间
    private String shipmentDate;
    
    //商品信息
    private List<DistributionOrdeProductDto> distributionOrdeProductDto;
    
    //支付信息
    private List<DistributionOrdePayDto> distributionOrdePayDtos;
    
    // 服务态度
    private Integer attitudeLevel;
    
    // 配送速度
    private Integer shipmentLevle;
    
    // 服务体验
    private Integer serviceLevel;
    
    // 是否显示扫码标识
    private Boolean scanCodeFlag=new Boolean(false);
    
    private String source;

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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusStr() {
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


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public List<DistributionOrdeProductDto> getDistributionOrdeProductDto() {
        return distributionOrdeProductDto;
    }

    public void setDistributionOrdeProductDto(List<DistributionOrdeProductDto> distributionOrdeProductDto) {
        this.distributionOrdeProductDto = distributionOrdeProductDto;
    }

    public List<DistributionOrdePayDto> getDistributionOrdePayDtos() {
        return distributionOrdePayDtos;
    }

    public void setDistributionOrdePayDtos(List<DistributionOrdePayDto> distributionOrdePayDtos) {
        this.distributionOrdePayDtos = distributionOrdePayDtos;
    }

    public Integer getAttitudeLevel() {
        return attitudeLevel;
    }

    public void setAttitudeLevel(Integer attitudeLevel) {
        this.attitudeLevel = attitudeLevel;
    }

    public Integer getShipmentLevle() {
        return shipmentLevle;
    }

    public void setShipmentLevle(Integer shipmentLevle) {
        this.shipmentLevle = shipmentLevle;
    }

    public Integer getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(Integer serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public String getRecevingTime() {
        return recevingTime;
    }

    public void setRecevingTime(String recevingTime) {
        this.recevingTime = recevingTime;
    }

    public String getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(String shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean getScanCodeFlag() {
        return scanCodeFlag;
    }

    public void setScanCodeFlag(Boolean scanCodeFlag) {
        this.scanCodeFlag = scanCodeFlag;
    }


    
}
