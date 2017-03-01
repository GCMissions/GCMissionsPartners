package com.hengtiansoft.business.product.dto;

import java.io.Serializable;
import java.util.List;

public class ProductPriceDto implements Serializable {

    private static final long    serialVersionUID = -5984881489381903099L;

    private Long                 productId;

    private String               productName;

    private String               productCode;

    private Long                 costPrice;

    private Long                 price;

    private Long                 operCosts;
    
    private Long                 morePrice;

    private String saleTime;
    private String unSaleTime;
    
    
    private List<RegionPriceDto> list;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Long costPrice) {
        this.costPrice = costPrice;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public List<RegionPriceDto> getList() {
        return list;
    }

    public void setList(List<RegionPriceDto> list) {
        this.list = list;
    }

    public Long getOperCosts() {
        return operCosts;
    }

    public void setOperCosts(Long operCosts) {
        this.operCosts = operCosts;
    }

    public Long getMorePrice() {
      return morePrice;
    }

    public void setMorePrice(Long morePrice) {
      this.morePrice = morePrice;
    }

    public String getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(String saleTime) {
        this.saleTime = saleTime;
    }

    public String getUnSaleTime() {
        return unSaleTime;
    }

    public void setUnSaleTime(String unSaleTime) {
        this.unSaleTime = unSaleTime;
    }
    
    
}
