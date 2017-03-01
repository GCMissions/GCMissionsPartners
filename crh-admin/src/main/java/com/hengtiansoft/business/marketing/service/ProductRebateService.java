package com.hengtiansoft.business.marketing.service;

import java.util.List;

import com.hengtiansoft.business.marketing.dto.ProductRebatePageDto;
import com.hengtiansoft.business.marketing.dto.RebateProvinceDto;

public interface ProductRebateService {

    void search(ProductRebatePageDto dto);

    /**
     * 
    * Description: 查询某个商品的返利详情
    *
    * @param productId
    * @return
     */
    List<RebateProvinceDto> findOne(Long productId);

    void save(RebateProvinceDto rebateProvinceDto);

}
