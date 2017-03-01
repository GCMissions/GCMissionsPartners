package com.hengtiansoft.business.finance.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class ProductPageDto extends PagingDto<ProductDto> {
    /**
     * Variables Name: serialVersionUID
     * Description: TODO
     * Value Description: TODO
     */
    private static final long serialVersionUID = 3211699324358890341L;

    private String            productName;

    private String            brandId;

    private String            cateId;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

}
