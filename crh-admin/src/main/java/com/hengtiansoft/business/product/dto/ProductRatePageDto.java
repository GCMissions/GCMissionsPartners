package com.hengtiansoft.business.product.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class ProductRatePageDto extends PagingDto<ProductRateDto> {

    /**
    * Variables Name: serialVersionUID
    * Description: TODO
    * Value Description: TODO
    */
    private static final long serialVersionUID = 7530011787994958775L;
    
    private Long productNo;

    public Long getProductNo() {
        return productNo;
    }

    public void setProductNo(Long productNo) {
        this.productNo = productNo;
    }
    
}
