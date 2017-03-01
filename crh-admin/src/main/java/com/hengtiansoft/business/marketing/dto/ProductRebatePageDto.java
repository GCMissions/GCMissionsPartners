package com.hengtiansoft.business.marketing.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class ProductRebatePageDto extends PagingDto<ProductRebateDto> {

    private static final long serialVersionUID = -3114959221844698622L;

    private String            productName;

    private String            productCode;

    private Long              brandId;

    private Long              cateId;

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

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

}
