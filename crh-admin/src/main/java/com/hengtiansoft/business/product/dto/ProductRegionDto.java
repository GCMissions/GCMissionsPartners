package com.hengtiansoft.business.product.dto;

import java.io.Serializable;

public class ProductRegionDto implements Serializable {

    private static final long serialVersionUID = -334564418123863150L;

    private Integer              regionId;

    private String              price;
    
    private String              morePrice;

    private String              costPrice;
    
    private String              operCosts;

    private Long              priceId;

    private String            priceLevel;

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public String getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(String priceLevel) {
        this.priceLevel = priceLevel;
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
