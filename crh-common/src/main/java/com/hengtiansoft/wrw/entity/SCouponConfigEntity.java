package com.hengtiansoft.wrw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hengtiansoft.common.domain.BaseEntity;

/**
 * 
* Class Name: SCouponConfigEntity
* Description:  优惠券模板
* @author chengchaoyin
*
 */
@Entity
@Table(name = "S_COUPON_CONFIG")
public class SCouponConfigEntity extends BaseEntity {

    private static final long serialVersionUID = 949279260100599080L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COUPON_ID")
    private Long couponId;

    /**
     * 优惠券名称
     */
    @Column(name = "COUPON_NAME")
    private String couponName;

    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 优惠券获取类型 1后台发放 2用户获取  3充值赠送
     */
    @Column(name = "TYPE")
    private String type;

    /**
     * 获取品类限制 0匹配 1全品类 
     */
    @Column(name = "FETCH_TYPE")
    private String fetchType;
    
    /**
     * 使用品类限制 0匹配 1全品类 
     */
    @Column(name = "USE_TYPE")
    private String useType;
    
    /**
     * 获取匹配品类类型,逗号分隔，存品类最低品类id
     */
    @Column(name = "FETCH_TYPE_DETAIL")
    private String fetchTypeDetail;
    
    /**
     * 使用匹配品类类型,逗号分隔，存品类最低品类id
     */
    @Column(name = "USE_TYPE_DETAIL")
    private String useTypeDetail;

    /**
     * 状态 0:失效 1:正常
     */
    @Column(name = "STATUS")
    private String status;

    /**
     * 面额
     */
    @Column(name = "VALUE")
    private Long value;

    /**
     * 优惠券获取最低金额
     */
    @Column(name = "FETCH_VALUE_LIMIT")
    private Long fetchValueLimit;
    
    /**
     * 优惠券使用最低金额
     */
    @Column(name = "USER_VALUE_LIMIT")
    private Long userValueLimit;

    /**
     * 商城可用 0 不可 1可以
     */
    @Column(name = "WEB_USE")
    private String webUse;

    /**
     * 移动可用 0 不可 1可以
     */
    @Column(name = "MOBILE_USE")
    private String mobileUse;

    /**
     * 发放数量
     */
    @Column(name = "SEND_NUM", insertable = false)
    private Long sendNum;

    /**
     * 生效时间
     */
    @Column(name = "EFFECT_DATE")
    private Date effectDate;

    /**
     * 失效时间
     */
    @Column(name = "INVALID_DATE")
    private Date invalidDate;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFetchType() {
        return fetchType;
    }

    public void setFetchType(String fetchType) {
        this.fetchType = fetchType;
    }

    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }


    public String getFetchTypeDetail() {
        return fetchTypeDetail;
    }

    public void setFetchTypeDetail(String fetchTypeDetail) {
        this.fetchTypeDetail = fetchTypeDetail;
    }

    public String getUseTypeDetail() {
        return useTypeDetail;
    }

    public void setUseTypeDetail(String useTypeDetail) {
        this.useTypeDetail = useTypeDetail;
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

    public Long getFetchValueLimit() {
        return fetchValueLimit;
    }

    public void setFetchValueLimit(Long fetchValueLimit) {
        this.fetchValueLimit = fetchValueLimit;
    }

    public Long getUserValueLimit() {
        return userValueLimit;
    }

    public void setUserValueLimit(Long userValueLimit) {
        this.userValueLimit = userValueLimit;
    }

    public String getWebUse() {
        return webUse;
    }

    public void setWebUse(String webUse) {
        this.webUse = webUse;
    }

    public String getMobileUse() {
        return mobileUse;
    }

    public void setMobileUse(String mobileUse) {
        this.mobileUse = mobileUse;
    }

    public Long getSendNum() {
        return sendNum;
    }

    public void setSendNum(Long sendNum) {
        this.sendNum = sendNum;
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

}
