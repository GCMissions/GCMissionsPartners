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
 * Class Name: KdTwentyFourHoursEntity Description: 酷袋24小时实体类
 * 
 * @author chengchaoyin
 *
 */
@Entity
@Table(name = "kd_twenty_four_hours")
public class KdTwentyFourHoursEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "BARGAIN_AMOUNT")
    private Long bargainAmount;

    @Column(name = "BARGAIN_MAX_AMOUNT")
    private Long bargainMaxAmount;

    @Column(name = "BARGAIN_MIN_AMOUNT")
    private Long bargainMinAmount;

    @Column(name = "BARGAIN_TYPE")
    private String bargainType;

    @Column(name = "BASE_PRICE")
    private Long basePrice;
    
    @Column(name = "CHECK_TOTAL")
    private Long checkTotal;

    @Column(name = "DEL_FLAG")
    private String delFlag;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "EFFECTIVE_START_DATE")
    private Date effectiveStartDate;

    @Column(name = "EFFECTIVE_END_DATE")
    private Date effectiveEndDate;

    @Column(name = "EXPLAIN_INFO")
    private String explainInfo;
    
    @Column(name = "SPECIAL_DESC")
    private String specialDesc;
    
    @Column(name = "SUPPORT_DESC")
    private String supportDesc;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PLAYER_TOTAL")
    private Long playerTotal;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "SHARE_TOTAL")
    private Long shareTotal;

    @Column(name = "SPEC_TYPE")
    private String specType;

    @Column(name = "STATUS")
    private String status;

    public KdTwentyFourHoursEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBargainAmount() {
        return bargainAmount;
    }

    public void setBargainAmount(Long bargainAmount) {
        this.bargainAmount = bargainAmount;
    }

    public Long getBargainMaxAmount() {
        return bargainMaxAmount;
    }

    public void setBargainMaxAmount(Long bargainMaxAmount) {
        this.bargainMaxAmount = bargainMaxAmount;
    }

    public Long getBargainMinAmount() {
        return bargainMinAmount;
    }

    public void setBargainMinAmount(Long bargainMinAmount) {
        this.bargainMinAmount = bargainMinAmount;
    }

    public String getBargainType() {
        return bargainType;
    }

    public void setBargainType(String bargainType) {
        this.bargainType = bargainType;
    }

    public Long getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Long basePrice) {
        this.basePrice = basePrice;
    }

    public Long getCheckTotal() {
        return checkTotal;
    }

    public void setCheckTotal(Long checkTotal) {
        this.checkTotal = checkTotal;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEffectiveStartDate() {
        return effectiveStartDate;
    }

    public void setEffectiveStartDate(Date effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    public Date getEffectiveEndDate() {
        return effectiveEndDate;
    }

    public void setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }

    public String getExplainInfo() {
        return explainInfo;
    }

    public void setExplainInfo(String explainInfo) {
        this.explainInfo = explainInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPlayerTotal() {
        return playerTotal;
    }

    public void setPlayerTotal(Long playerTotal) {
        this.playerTotal = playerTotal;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getShareTotal() {
        return shareTotal;
    }

    public void setShareTotal(Long shareTotal) {
        this.shareTotal = shareTotal;
    }

    public String getSpecType() {
        return specType;
    }

    public void setSpecType(String specType) {
        this.specType = specType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecialDesc() {
        return specialDesc;
    }

    public void setSpecialDesc(String specialDesc) {
        this.specialDesc = specialDesc;
    }

    public String getSupportDesc() {
        return supportDesc;
    }

    public void setSupportDesc(String supportDesc) {
        this.supportDesc = supportDesc;
    }

}
