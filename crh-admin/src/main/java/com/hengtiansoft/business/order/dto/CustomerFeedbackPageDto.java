package com.hengtiansoft.business.order.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class CustomerFeedbackPageDto extends PagingDto<CustomerFeedbackDto> {

    /**
     * Variables Name: serialVersionUID
     * Description: TODO
     * Value Description: TODO
     */
    private static final long serialVersionUID = 7165503563885377410L;

    private String            status;

    private String              startDate;

    private String              endDate;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
