package com.hengtiansoft.business.member.dto;

import java.io.Serializable;

public class MemberCouponListDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String couponName;
    
    private String couponPrice;
    
    /**
     * 获得时间
     */
    private String containDate;
    
    /**
     * 有效时间
     */
    private String effectiveDate;
    
    /**
     * 优惠券状态
     */
    private String status;
    
    /**
     * 使用状态
     */
    private String useStatus;

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(String couponPrice) {
        this.couponPrice = couponPrice;
    }

    public String getContainDate() {
        return containDate;
    }

    public void setContainDate(String containDate) {
        this.containDate = containDate;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }
}
