package com.hengtiansoft.business.product.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class ProductPageSearchDto extends PagingDto<ProductDto> {

    private static final long serialVersionUID = -5912668703922531473L;

    private String            productName;

    private String            productCode;

    private Integer           regionId;

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

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

}
