package com.hengtiansoft.business.wrkd.activity.dto;

import java.io.Serializable;
import java.util.List;

import com.hengtiansoft.business.wrkd.image.dto.KdPImageDto;

public class KdGroupBuySaveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long groupBuyId;
    
    private String groupBuyName;
    
    private String startDate;
    
    private String endDate;
    
    private String groupBuyImage;
    
    private String specialDesc;
    
    private Long productId;
    
    private List<String> specInfoArry;
    
    private List<String> prices;
    
    private String totalPrice;
    
    private String startCount;
    
    private String limitCount;
    
    private List<KdPImageDto> imageDtos;
    
    private String description;
    
    private String productCode;
    
    private String productName;
    
    private List<String> limits;
    
    // 操作类型（0：添加；1：编辑）
    private String operType;

    public Long getGroupBuyId() {
        return groupBuyId;
    }

    public void setGroupBuyId(Long groupBuyId) {
        this.groupBuyId = groupBuyId;
    }

    public String getGroupBuyName() {
        return groupBuyName;
    }

    public void setGroupBuyName(String groupBuyName) {
        this.groupBuyName = groupBuyName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getGroupBuyImage() {
        return groupBuyImage;
    }

    public void setGroupBuyImage(String groupBuyImage) {
        this.groupBuyImage = groupBuyImage;
    }

    public String getSpecialDesc() {
        return specialDesc;
    }

    public void setSpecialDesc(String specialDesc) {
        this.specialDesc = specialDesc;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<String> getSpecInfoArry() {
        return specInfoArry;
    }

    public void setSpecInfoArry(List<String> specInfoArry) {
        this.specInfoArry = specInfoArry;
    }

    public List<String> getPrices() {
        return prices;
    }

    public void setPrices(List<String> prices) {
        this.prices = prices;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStartCount() {
        return startCount;
    }

    public void setStartCount(String startCount) {
        this.startCount = startCount;
    }

    public String getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(String limitCount) {
        this.limitCount = limitCount;
    }

    public List<KdPImageDto> getImageDtos() {
        return imageDtos;
    }

    public void setImageDtos(List<KdPImageDto> imageDtos) {
        this.imageDtos = imageDtos;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public List<String> getLimits() {
        return limits;
    }

    public void setLimits(List<String> limits) {
        this.limits = limits;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
