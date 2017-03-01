package com.hengtiansoft.business.order.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class DistributionOrdeSearchDto extends PagingDto<DistributionOrdeDto> {

    private static final long serialVersionUID = -3181915833650541288L;

    private String              orderId;

    private Long              numLow;

    private Long              numHigh;

    private String            status;

    private String            startDate;

    private String            endDate;

    private String            consignee;

    private String            phone;
    
    private String            newOrder;

    private String            source;
    
    public Long getNumLow() {
        return numLow;
    }

    public void setNumLow(Long numLow) {
        this.numLow = numLow;
    }

    public Long getNumHigh() {
        return numHigh;
    }

    public void setNumHigh(Long numHigh) {
        this.numHigh = numHigh;
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

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getNewOrder() {
        return newOrder;
    }

    public void setNewOrder(String newOrder) {
        this.newOrder = newOrder;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
