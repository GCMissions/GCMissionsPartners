package com.hengtiansoft.business.marketing.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengtiansoft.common.util.CustomDoubleSerialize;
import com.hengtiansoft.common.util.WRWUtil;

/**
 * Class Name: CouponDto
 * Description: 平台优惠券（模板）Dto
 * 
 * @author chengminmiao
 */
public class CouponDto implements Serializable {

    private static final long   serialVersionUID = -2148166589594562151L;

    private Long                couponId;

    private String              couponName;

    private String              remark;

    private String              type;

    private String              productFlag;

    private String              status;

    private Long                value;

    private BigDecimal              valueYuan;

    private Long                fetchValueLimit;
    
    private BigDecimal              fetchValueLimitYuan;
    
    private Long                userValueLimit;
    
    private BigDecimal              userValueLimitYuan;


    private String              webUse;

    private String              mobileUse;

    private Long                totalNum;

    private Long                perNum;

    private Long                sendNum;

    private Date                beginDate;

    private Date                endDate;

    private Date                effectDate;

    private Date                invalidDate;

    private Date                createDate;

    private Long                createId;

    private Date                modifyDate;

    private Long                modifyId;

    private List<CouponProdDto> prodList;
    
    private String fetchType;
    
    private String useType;
    
    private String fetchTypeDetail;
    
    private String fetchTypeDetailNames;
    
    private String useTypeDetail;
    
    private String useTypeDetailNames;
    
    private String userIds;
    
    private String couponIds;
    
    private String sendType;

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

    public String getProductFlag() {
        return productFlag;
    }

    public void setProductFlag(String productFlag) {
        this.productFlag = productFlag;
    }

    public void setType(String type) {
        this.type = type;
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
        this.valueYuan = WRWUtil.transFenToYuan(value);
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

    public Long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Long totalNum) {
        this.totalNum = totalNum;
    }

    public Long getPerNum() {
        return perNum;
    }

    public void setPerNum(Long perNum) {
        this.perNum = perNum;
    }

    public Long getSendNum() {
        return sendNum;
    }

    public void setSendNum(Long sendNum) {
        this.sendNum = sendNum;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public List<CouponProdDto> getProdList() {
        return prodList;
    }

    public void setProdList(List<CouponProdDto> prodList) {
        this.prodList = prodList;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Long getModifyId() {
        return modifyId;
    }

    public void setModifyId(Long modifyId) {
        this.modifyId = modifyId;
    }

    @JsonSerialize(using = CustomDoubleSerialize.class)
    public BigDecimal getValueYuan() {
        return valueYuan;
    }

    public void setValueYuan(BigDecimal valueYuan) {
        this.valueYuan = valueYuan;
        this.value = WRWUtil.transYuanToFen(valueYuan);
    }

    public Long getFetchValueLimit() {
        return fetchValueLimit;
    }

    public void setFetchValueLimit(Long getValueLimit) {
        this.fetchValueLimit = getValueLimit;
        this.fetchValueLimitYuan = WRWUtil.transFenToYuan(getValueLimit);
    }

    @JsonSerialize(using = CustomDoubleSerialize.class)
    public BigDecimal getFetchValueLimitYuan() {
        return fetchValueLimitYuan;
    }

    public void setFetchValueLimitYuan(BigDecimal getValueLimitYuan) {
        this.fetchValueLimitYuan = getValueLimitYuan;
        this.fetchValueLimit = WRWUtil.transYuanToFen(getValueLimitYuan);
    }

    public Long getUserValueLimit() {
        return userValueLimit;
    }

    public void setUserValueLimit(Long userValueLimit) {
        this.userValueLimit = userValueLimit;
        this.userValueLimitYuan = WRWUtil.transFenToYuan(userValueLimit);
    }

    @JsonSerialize(using = CustomDoubleSerialize.class)
    public BigDecimal getUserValueLimitYuan() {
        return userValueLimitYuan;
    }

    public void setUserValueLimitYuan(BigDecimal userValueLimitYuan) {
        this.userValueLimitYuan = userValueLimitYuan;
        this.userValueLimit = WRWUtil.transYuanToFen(userValueLimitYuan);
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

    public String getFetchTypeDetailNames() {
        return fetchTypeDetailNames;
    }

    public void setFetchTypeDetailNames(String fetchTypeDetailNames) {
        this.fetchTypeDetailNames = fetchTypeDetailNames;
    }

    public String getUseTypeDetailNames() {
        return useTypeDetailNames;
    }

    public void setUseTypeDetailNames(String useTypeDetailNames) {
        this.useTypeDetailNames = useTypeDetailNames;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public String getCouponIds() {
        return couponIds;
    }

    public void setCouponIds(String couponIds) {
        this.couponIds = couponIds;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }
    
}
