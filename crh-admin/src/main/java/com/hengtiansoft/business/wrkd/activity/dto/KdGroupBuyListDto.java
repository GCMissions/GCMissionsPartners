package com.hengtiansoft.business.wrkd.activity.dto;

import java.io.Serializable;

public class KdGroupBuyListDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long groupBuyId;
    
    private String groupBuyName;
    
    private String productName;
    
    private String createDate;
    
    private String startDate;
    
    private String endDate;
    
    private String status;
    
    private String productCode;

    public Long getGroupBuyId() {
        return groupBuyId;
    }

    public void setGroupBuyId(Long groupBuyId) {
        this.groupBuyId = groupBuyId;
    }

    public String getGroupBuyName() {
        return groupBuyName;
    }

    public void setGroupBuyName(String groupBuyName) {
        this.groupBuyName = groupBuyName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
