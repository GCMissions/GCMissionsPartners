package com.hengtiansoft.business.marketing.dto;

import java.util.Date;

import com.hengtiansoft.common.dto.PagingDto;

public class CouponPageDto extends PagingDto<CouponDto> {

    private static final long serialVersionUID = 1L;

    private Long              couponId;

    private String            couponName;

    private String            type;

    private Double              value;

    private String            status;

    private Date              createDateStrat;

    private Date              createDateEnd;

    private Date              effectDateStart;

    private Date              effectDateEnd;
    
    private String              useTypeDetail;
    
    private String              useType;
    
    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    // 前台传入的是元，需要转换成分
    public void setValue(Double value) {
        if (null != value) {
            this.value = value * 100;
        }
    }

    public Date getCreateDateStrat() {
        return createDateStrat;
    }

    public void setCreateDateStrat(Date createDateStrat) {
        this.createDateStrat = createDateStrat;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    public Date getEffectDateStart() {
        return effectDateStart;
    }

    public void setEffectDateStart(Date effectDateStart) {
        this.effectDateStart = effectDateStart;
    }

    public Date getEffectDateEnd() {
        return effectDateEnd;
    }

    public void setEffectDateEnd(Date effectDateEnd) {
        this.effectDateEnd = effectDateEnd;
    }

    public String getUseTypeDetail() {
        return useTypeDetail;
    }

    public void setUseTypeDetail(String useTypeDetail) {
        this.useTypeDetail = useTypeDetail;
    }

    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }

}
