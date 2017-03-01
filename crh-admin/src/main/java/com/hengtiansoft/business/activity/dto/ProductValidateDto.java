package com.hengtiansoft.business.activity.dto;

import java.io.Serializable;

public class ProductValidateDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String productCode;
    
    private String cateName;
    
    private String orgName;
    
    private String productType;
    
    private String productName;
    
    private String price;
    
    private String createDate;
    
    private Long productId;
    
    private String verificationCode;
    
    private String isCaptcha;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getIsCaptcha() {
        return isCaptcha;
    }

    public void setIsCaptcha(String isCaptcha) {
        this.isCaptcha = isCaptcha;
    }
}
