package com.hengtiansoft.business.activity.dto;

import java.util.Date;

public class ActivityConstranintDto {
    
    private Long id;
    
    private Long productId;
    
    // 必填字段
    private String requireFields;
    
    // 必填字段是否必填：0-选填，1-必填
    private String requireMask;
    
    // 限购数量
    private Integer limitNum;
    
    // 开售类型：0-立即开售；1-定时开售
    private String saleType;
    
    // 开售开始时间
    private Date startTime;
    
    // 开售结束时间
    private Date endTime;
    
    // 开售开始时间(接收前台日期字符串)
    private String startDate;
    
    // 开售结束时间(接收前台日期字符串)
    private String endDate;
    
    // 截止下单时间（提前天数）
    private Integer buyDeadline;

    // 活动参与人数
    private String partakeInfo;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getRequireFields() {
        return requireFields;
    }

    public void setRequireFields(String requireFields) {
        this.requireFields = requireFields;
    }

    public String getRequireMask() {
        return requireMask;
    }

    public void setRequireMask(String requireMask) {
        this.requireMask = requireMask;
    }

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public Integer getBuyDeadline() {
        return buyDeadline;
    }

    public void setBuyDeadline(Integer buyDeadline) {
        this.buyDeadline = buyDeadline;
    }

    public String getPartakeInfo() {
        return partakeInfo;
    }

    public void setPartakeInfo(String partakeInfo) {
        this.partakeInfo = partakeInfo;
    }

}
