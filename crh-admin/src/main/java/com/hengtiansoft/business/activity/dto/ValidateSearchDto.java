package com.hengtiansoft.business.activity.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class ValidateSearchDto extends PagingDto<ProductValidateDto> {

    private static final long serialVersionUID = 1L;

    private Long firstCategory;
    
    private Long secondCategory;
    
    private String productType;
    
    private String productName;
    
    private String minPrice;
    
    private String maxPrice;

    public Long getFirstCategory() {
        return firstCategory;
    }

    public void setFirstCategory(Long firstCategory) {
        this.firstCategory = firstCategory;
    }

    public Long getSecondCategory() {
        return secondCategory;
    }

    public void setSecondCategory(Long secondCategory) {
        this.secondCategory = secondCategory;
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

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }
}
