package com.hengtiansoft.business.product.service;

import org.springframework.cache.annotation.CacheEvict;

import com.hengtiansoft.business.product.dto.ProductDeliveryShiefDto;
import com.hengtiansoft.business.product.dto.ProductPriceDto;
import com.hengtiansoft.business.product.dto.ProductShiefSearchDto;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.xmemcached.constant.CacheType;

/**
 * 
* Class Name: ProductShiefService
* Description: 上下架业务类
* @author zhisongliu
*
 */
public interface ProductShiefService {

    /**
     * 
    * Description: 搜索
    *
    * @param dto
     */
    void search(ProductShiefSearchDto dto);

    /**
     * 根据productId查询该商品的上架状态
     * 
     * @param productId
     */
    ProductPriceDto findShiefByProductId(Long productId);


    /**
     * 
    * Description: 商品上下架操作
    *
    * @param dto
    * @return
     */
    ResultDto<String> productShief(ProductDeliveryShiefDto dto);

    ResultDto<String> checkProduct(Long id);
    
    /**
     * 
    * Description: 清除乐园首页商品缓存数据
    *
    * @author chengchaoyin
     */
    @CacheEvict(value = CacheType.WELY_HOMEPAGE_CACHE,allEntries = true)
    void clearHomePageDataCache();

}
