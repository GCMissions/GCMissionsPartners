package com.hengtiansoft.business.marketing.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengtiansoft.common.serialize.CustomDateTimeSerializer;
import com.hengtiansoft.common.serialize.CustomMoneySerializer;

/**
 * 
* Class Name: BargainDto
* Description: 砍价商品列表
* @author chenghongtu
*
 */
public class BargainDto {
    
    private Long id;
    
    private String productName;
    
    private Date createDate;
    
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private Date effectiveStartDate;
    
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private Date effectiveEndDate;
    
    private Integer playerTotal;
    
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Long price;
    
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Long basePrice;
    
    private String status;

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public Integer getPlayerTotal() {
        return playerTotal;
    }

    public void setPlayerTotal(Integer playerTotal) {
        this.playerTotal = playerTotal;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
