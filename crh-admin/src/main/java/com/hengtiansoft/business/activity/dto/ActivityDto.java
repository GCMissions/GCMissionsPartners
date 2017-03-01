package com.hengtiansoft.business.activity.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Class Name: ActivityDto Description: 商品信息界面DTO
 * 
 * @author jiafengchen
 *
 */
public class ActivityDto implements Serializable{
    
    /**
    * Variables Name: serialVersionUID
    * Description: TODO
    * Value Description: TODO
    */
    private static final long serialVersionUID = 1L;

    private Long productId;

    private Long cateId;
    
    private Long orgId;
    
    private String firstCateName;
    
    private String secondCateName;
    
    private String cateName;
    
    private String productCode;

    private String productName;
    
    private String price;
    
    private Long originalPrice;

    
    private Date createDate;

    private String orgName;
    
    private String saleFlag;
    
    public Long getProductId() {
        return productId;
    }


    public void setProductId(Long productId) {
        this.productId = productId;
    }


    public Long getCateId() {
        return cateId;
    }


    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }


    public Long getOrgId() {
        return orgId;
    }


    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }


    public String getCateName() {
        return cateName;
    }


    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    
    public String getFirstCateName() {
        return firstCateName;
    }


    public void setFirstCateName(String firstCateName) {
        this.firstCateName = firstCateName;
    }


    public String getSecondCateName() {
        return secondCateName;
    }


    public void setSecondCateName(String secondCateName) {
        this.secondCateName = secondCateName;
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


    public void setProductName(String productName) {
        this.productName = productName;
    }


    public String getPrice() {
        return price;
    }


    public void setPrice(String price) {
        this.price = price;
    }


    public Long getOriginalPrice() {
        return originalPrice;
    }


    public void setOriginalPrice(Long originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Date getCreateDate() {
        return createDate;
    }


    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    public String getOrgName() {
        return orgName;
    }


    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }


    public String getSaleFlag() {
        return saleFlag;
    }


    public void setSaleFlag(String saleFlag) {
        this.saleFlag = saleFlag;
    }

    
}
