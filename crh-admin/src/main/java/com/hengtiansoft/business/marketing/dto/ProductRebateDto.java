package com.hengtiansoft.business.marketing.dto;

import java.io.Serializable;
import java.util.List;

public class ProductRebateDto implements Serializable {

    private static final long       serialVersionUID = 7864530792593778840L;

    private Long                    productId;

    private String                  productCode;

    private String                  productName;

    private String                  cateName;

    private String                  brandName;

    private List<RebateProvinceDto> detail;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public List<RebateProvinceDto> getDetail() {
        return detail;
    }

    public void setDetail(List<RebateProvinceDto> detail) {
        this.detail = detail;
    }

}
