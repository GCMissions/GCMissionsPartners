package com.hengtiansoft.business.order.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengtiansoft.common.serialize.CustomDateSerializer;
import com.hengtiansoft.common.serialize.CustomMoneySerializer;
import com.hengtiansoft.wrw.enums.CouponState;

/**
 * Class Name: CouponDto Description: 平台优惠券（模板）Dto
 * 
 * @author chengminmiao
 */
public class CouponDto implements Serializable {

    private static final long serialVersionUID = -2148166589594562151L;

    // 优惠券id
    private Long couponId;

    // 优惠券名称
    private String couponName;

    // 状态
    private String status;

    private String statusStr;

    // 面额
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Long value;

    // 生效时间
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date effectDate;

    // 失效时间
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date invalidDate;

    // 品类名称
    private String cateNames;

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

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Date getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
    }

    public Date getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }

    public String getCateNames() {
        return cateNames;
    }

    public void setCateNames(String cateNames) {
        this.cateNames = cateNames;
    }

    public String getStatusStr() {
        statusStr = CouponState.getValue(status);
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

}
