package com.hengtiansoft.business.marketing.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengtiansoft.common.serialize.CustomDateTimeDeserialize;
import com.hengtiansoft.common.serialize.CustomDateTimeSerializer;
import com.hengtiansoft.common.serialize.CustomMoneyDeserializer;
import com.hengtiansoft.common.serialize.CustomMoneySerializer;

/**
 * 
* Class Name: BargainSaveAndEditDto
* Description: 砍价新增编辑DTO
* @author chenghongtu
*
 */
public class BargainSaveAndEditDto {
    
    private Long id;
    
    private String productName;
    
    @JsonSerialize(using = CustomMoneySerializer.class)
    @JsonDeserialize(using = CustomMoneyDeserializer.class)
    private Long price;
    
    @JsonSerialize(using = CustomMoneySerializer.class)
    @JsonDeserialize(using = CustomMoneyDeserializer.class)
    private Long basePrice;
    
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserialize.class)
    private Date effectiveStartDate;
    
    @JsonDeserialize(using = CustomDateTimeDeserialize.class)
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private Date effectiveEndDate;
    
    private String bargainType;
    
    @JsonSerialize(using = CustomMoneySerializer.class)
    @JsonDeserialize(using = CustomMoneyDeserializer.class)
    private Long            bargainMinAmount;
    
    @JsonSerialize(using = CustomMoneySerializer.class)
    @JsonDeserialize(using = CustomMoneyDeserializer.class)
    private Long            bargainMaxAmount;
    
    @JsonSerialize(using = CustomMoneySerializer.class)
    @JsonDeserialize(using = CustomMoneyDeserializer.class)
    private Long            bargainAmount;
    
    private String            bargainImage;
    
    private String            description;
    
    private String            status;
    
    private Integer             playerTotal;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Long basePrice) {
        this.basePrice = basePrice;
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

    public String getBargainType() {
        return bargainType;
    }

    public void setBargainType(String bargainType) {
        this.bargainType = bargainType;
    }

    public Long getBargainMinAmount() {
        return bargainMinAmount;
    }

    public void setBargainMinAmount(Long bargainMinAmount) {
        this.bargainMinAmount = bargainMinAmount;
    }

    public Long getBargainMaxAmount() {
        return bargainMaxAmount;
    }

    public void setBargainMaxAmount(Long bargainMaxAmount) {
        this.bargainMaxAmount = bargainMaxAmount;
    }

    public Long getBargainAmount() {
        return bargainAmount;
    }

    public void setBargainAmount(Long bargainAmount) {
        this.bargainAmount = bargainAmount;
    }

    public String getBargainImage() {
        return bargainImage;
    }

    public void setBargainImage(String bargainImage) {
        this.bargainImage = bargainImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPlayerTotal() {
        return playerTotal;
    }

    public void setPlayerTotal(Integer playerTotal) {
        this.playerTotal = playerTotal;
    }
    
}
