package com.hengtiansoft.business.product.service;

import com.hengtiansoft.business.product.dto.ProductRatePageDto;

public interface ProductRateService {

    String getProductName(Long id);

    Double getAvgStar(Long id);

    Long getAllRateCount(Long id);

    void search(ProductRatePageDto dto);

}
