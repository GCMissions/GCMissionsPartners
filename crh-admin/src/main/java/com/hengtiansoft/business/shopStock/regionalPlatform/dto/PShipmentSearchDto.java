package com.hengtiansoft.business.shopStock.regionalPlatform.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class PShipmentSearchDto extends PagingDto<PShipmentDto> {

    /**
    * Variables Name: serialVersionUID
    * Description: TODO
    * Value Description: TODO
    */
    private static final long serialVersionUID = -3635925849590720128L;
    
    private String orderId;

    private String totalNoFrom;
    
    private String totalNoTo;
    
    private String status;
    
    private String startDate;

    private String endDate;
    
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTotalNoFrom() {
        return totalNoFrom;
    }

    public void setTotalNoFrom(String totalNoFrom) {
        this.totalNoFrom = totalNoFrom;
    }

    public String getTotalNoTo() {
        return totalNoTo;
    }

    public void setTotalNoTo(String totalNoTo) {
        this.totalNoTo = totalNoTo;
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

}
