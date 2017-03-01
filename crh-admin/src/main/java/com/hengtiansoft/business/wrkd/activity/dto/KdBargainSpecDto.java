package com.hengtiansoft.business.wrkd.activity.dto;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengtiansoft.common.serialize.CustomMoneyDeserializer;
import com.hengtiansoft.common.serialize.CustomMoneySerializer;


/**
 * 
* Class Name: BargainDto
* Description: 24小时活动
* @author chengchaoyin
*
 */
public class KdBargainSpecDto {
    
    /**
     * 规格信息
     */
    private List<KdBargainSubSpecDetailDto> subPecDetailDtos;
    
    /**
     * 后台存储规格商品的价格
     */
    @JsonSerialize(using = CustomMoneySerializer.class)
    @JsonDeserialize(using = CustomMoneyDeserializer.class)
    private Long        productPrice;
    
    
    /**
     * 后台存储规格商品的底价
     */
    @JsonSerialize(using = CustomMoneySerializer.class)
    @JsonDeserialize(using = CustomMoneyDeserializer.class)
    private Long        productBasePrice;
    
    /**
     * 子规格组合key
     */
    private String      specGroup;
    
    /**
     * 规格组合。每个主规格的子规格的相互组合
     */
    private String      specInfo;
    
    /**
     * 砍价方式（BargainTypeEnum）
     */
    private String bargainType;
    
    /**
     * 砍价最小金额
     */
    @JsonSerialize(using = CustomMoneySerializer.class)
    @JsonDeserialize(using = CustomMoneyDeserializer.class)
    private Long            bargainMinAmount;
    
    /**
     * 砍价最大金额
     */
    @JsonSerialize(using = CustomMoneySerializer.class)
    @JsonDeserialize(using = CustomMoneyDeserializer.class)
    private Long            bargainMaxAmount;
    
    /**
     * 砍价固定金额
     */
    @JsonSerialize(using = CustomMoneySerializer.class)
    @JsonDeserialize(using = CustomMoneyDeserializer.class)
    private Long            bargainAmount;


    public String getSpecInfo() {
        return specInfo;
    }

    public void setSpecInfo(String specInfo) {
        this.specInfo = specInfo;
    }

    public String getSpecGroup() {
        return specGroup;
    }

    public void setSpecGroup(String specGroup) {
        this.specGroup = specGroup;
    }

    public List<KdBargainSubSpecDetailDto> getSubPecDetailDtos() {
        return subPecDetailDtos;
    }

    public void setSubPecDetailDtos(List<KdBargainSubSpecDetailDto> subPecDetailDtos) {
        this.subPecDetailDtos = subPecDetailDtos;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Long productPrice) {
        this.productPrice = productPrice;
    }

    public Long getProductBasePrice() {
        return productBasePrice;
    }

    public void setProductBasePrice(Long productBasePrice) {
        this.productBasePrice = productBasePrice;
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
    
}
