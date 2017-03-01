package com.hengtiansoft.business.wrkd.activity.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengtiansoft.business.wrkd.image.dto.KdPImageDto;
import com.hengtiansoft.common.serialize.CustomMoneyDeserializer;
import com.hengtiansoft.common.serialize.CustomMoneySerializer;

/**
 * 
* Class Name: BargainSaveAndEditDto
* Description: 24小时活动新增编辑DTO
* @author chengchaoyin
*
 */
public class KdBargainSaveAndEditDto implements Serializable{
    
    private static final long serialVersionUID = 8353717569948473175L;

    private Long id;
    
    /**
     * 活动名称
     */
    private String name;
    
    /**
     * 活动说明
     */
    private String explainInfo;
    
    /**
     * 活动说明图片
     */
    private KdPImageDto explainInfoPics;
    
    /**
     * 活动特别说明
     */
    private String specialDesc;
    
    /**
     * 资助说明
     */
    private String supportDesc;
    
    /**
     * 商品id
     */
    private Long productId;
    
    /**
     * 商品编码
     */
    private String pCode;
    
    /**
     * 商品名称
     */
    private String pName;
    
    /**
     * 活动底价
     */
    @JsonSerialize(using = CustomMoneySerializer.class)
    @JsonDeserialize(using = CustomMoneyDeserializer.class)
    private Long basePrice;
    
    /**
     * 活动开始开始时间
     */
    private Date effectiveStartDate;
    
    /**
     * 活动结束时间
     */
    private Date effectiveEndDate;
    
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
    
    /**
     * 活动商品信息
     */
    private List<KdPImageDto> listImages;
    
    /**
     * 活动详情
     */
    private String            description;
    
    /**
     * 活动状态（KdBargainStatusEnum）
     */
    private String            status;
    
    /**
     * 选中商品规格类型（0:全规格;1:选择规格）
     */
    private String specType;
    
    /**
     * 规格信息
     */
    private List<KdBargainProSpecPriceDto> specInfoList;
    
    /**
     * 价格（可能是区间）
     */
    private String priceRange;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExplainInfo() {
        return explainInfo;
    }

    public void setExplainInfo(String explainInfo) {
        this.explainInfo = explainInfo;
    }
    
    public KdPImageDto getExplainInfoPics() {
        return explainInfoPics;
    }

    public void setExplainInfoPics(KdPImageDto explainInfoPics) {
        this.explainInfoPics = explainInfoPics;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public String getSpecType() {
        return specType;
    }

    public void setSpecType(String specType) {
        this.specType = specType;
    }

    public List<KdPImageDto> getListImages() {
        return listImages;
    }

    public void setListImages(List<KdPImageDto> listImages) {
        this.listImages = listImages;
    }

    public List<KdBargainProSpecPriceDto> getSpecInfoList() {
        return specInfoList;
    }

    public void setSpecInfoList(List<KdBargainProSpecPriceDto> specInfoList) {
        this.specInfoList = specInfoList;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
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

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

}
