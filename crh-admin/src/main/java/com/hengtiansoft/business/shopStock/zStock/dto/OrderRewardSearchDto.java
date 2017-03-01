package com.hengtiansoft.business.shopStock.zStock.dto;

import java.util.Date;

import com.hengtiansoft.common.dto.PagingDto;

public class OrderRewardSearchDto extends PagingDto<OrderRewardDto> {

    private static final long serialVersionUID = -5687969314455319825L;

    private String            orderId;

    private Date              startDate;

    private Date              endDate;

    private String            type;                                     // 惩罚类型 2-超时接单 3-超时派送
    
    private String            orgName;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
