package com.hengtiansoft.business.wrkd.activity.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class KdGroupBuySearchDto extends PagingDto<KdGroupBuyListDto>{

    private static final long serialVersionUID = 1L;

    private String groupBuyName;
    
    private String startTime;
    
    private String endTime;
    
    private String productName;
    
    private String productCode;
    
    private String status;

    public String getGroupBuyName() {
        return groupBuyName;
    }

    public void setGroupBuyName(String groupBuyName) {
        this.groupBuyName = groupBuyName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
