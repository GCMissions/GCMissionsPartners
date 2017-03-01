package com.hengtiansoft.business.finance.dto;

/**
 * 
* Class Name: SOrderBalanceDetailDto
* Description:财务报表-商品优惠券详情
* @author chenghongtu
*
 */
public class SOrderBalanceDetailDto {

    private String productCode;
    
    private String productName;
    
    private String brandName;
    
    private String cateName;
    
    private Long price;
    
    private Long num;
    
    private Long couponAmount;
    
    private Long bottles;//瓶数
    
    private Long value;//金额
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Long getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Long couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Long getBottles() {
        return bottles;
    }

    public void setBottles(Long bottles) {
        this.bottles = bottles;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
    
    
}
