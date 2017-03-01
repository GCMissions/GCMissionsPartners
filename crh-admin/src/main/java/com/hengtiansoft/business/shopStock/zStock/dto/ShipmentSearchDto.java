package com.hengtiansoft.business.shopStock.zStock.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class ShipmentSearchDto extends PagingDto<ShipmentDto> {

    private static final long serialVersionUID = -8744790963944539812L;

    private String            numLow;

    private String            numHign;

    private String            orderCode;

    private String            status;

    private String            startDate;

    private String            endDate;

    private String            isReceipt;

    public String getNumLow() {
        return numLow;
    }

    public void setNumLow(String numLow) {
        this.numLow = numLow;
    }

    public String getNumHign() {
        return numHign;
    }

    public void setNumHign(String numHign) {
        this.numHign = numHign;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getIsReceipt() {
        return isReceipt;
    }

    public void setIsReceipt(String isReceipt) {
        this.isReceipt = isReceipt;
    }

}
