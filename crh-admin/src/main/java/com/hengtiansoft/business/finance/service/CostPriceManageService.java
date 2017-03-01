package com.hengtiansoft.business.finance.service;

import java.util.List;

import com.hengtiansoft.business.finance.dto.ProductDto;
import com.hengtiansoft.business.finance.dto.ProductPageDto;
import com.hengtiansoft.common.dto.ResultDto;

public interface CostPriceManageService {
    
    /**
     * 
    * Description: 获取商品的成本价列表
    *
    * @param goodsPageDto
     */
    void searchProduct(ProductPageDto goodsPageDto);

    /**
     * 
    * Description: 修改商品成本价格
    *
    * @param goodsDtoList
    * @return
     */
    ResultDto<String> updateCostPrice(List<ProductDto> goodsDtoList);

}
