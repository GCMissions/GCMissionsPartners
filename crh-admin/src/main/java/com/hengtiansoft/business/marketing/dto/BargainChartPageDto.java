package com.hengtiansoft.business.marketing.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class BargainChartPageDto extends PagingDto<BargainChartDto>{
    
    private static final long serialVersionUID = -7472101980776814192L;
    
    private Long bargainId;
    
    private String productName;

    public Long getBargainId() {
        return bargainId;
    }

    public void setBargainId(Long bargainId) {
        this.bargainId = bargainId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    
}
