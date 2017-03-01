package com.hengtiansoft.business.product.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RegionPriceDto implements Serializable {

    private static final long    serialVersionUID = 6624281125170057040L;

    private Integer              regionId;

    private String               regionName;

    private Integer              parentId;

    private Integer              levelType;

    private String               priceLevel;

    private Long                 priceId;

    private String                 price;

    private String                 costPrice;

    private String               saleFlag;
    
    private String               operCosts;
    
    private String               morePrice;

    private List<RegionPriceDto> childrenList     = new ArrayList<RegionPriceDto>();

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getLevelType() {
        return levelType;
    }

    public void setLevelType(Integer levelType) {
        this.levelType = levelType;
    }

    public List<RegionPriceDto> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<RegionPriceDto> childrenList) {
        this.childrenList = childrenList;
    }

    public String getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(String priceLevel) {
        this.priceLevel = priceLevel;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSaleFlag() {
		return saleFlag;
	}

	public void setSaleFlag(String saleFlag) {
		this.saleFlag = saleFlag;
	}

	public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getOperCosts() {
        return operCosts;
    }

    public void setOperCosts(String operCosts) {
        this.operCosts = operCosts;
    }

    public String getMorePrice() {
      return morePrice;
    }

    public void setMorePrice(String morePrice) {
      this.morePrice = morePrice;
    }
    
    
}
