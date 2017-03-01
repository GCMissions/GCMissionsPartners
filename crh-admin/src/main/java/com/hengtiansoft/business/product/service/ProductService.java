package com.hengtiansoft.business.product.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hengtiansoft.business.order.dto.LineOrderDto;
import com.hengtiansoft.business.product.dto.ProductSaveDto;
import com.hengtiansoft.business.product.dto.ProductSearchDto;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.wrw.entity.PProductEntity;

public interface ProductService {

    void search(ProductSearchDto dto);

    ResultDto<ProductSaveDto> save(ProductSaveDto dto);

    ProductSaveDto findById(Long id);

    ResultDto<String> update(ProductSaveDto dto);

    ResultDto<String> delete(Long id);

    Object findCateByCateId(Long cateId, HttpServletRequest request, HttpServletResponse response);

    Object findCateById(Long id, HttpServletRequest request, HttpServletResponse response);

    PProductEntity findByActStockId(Long actStockId);
    
    /**
     * 
    * Description: 校验线下订单中商品的有效性
    *
    * @param lineOrderDto
    * @return
    * @author chengchaoyin
     */
    ResultDto<String> checkStatusForLineOrder(LineOrderDto lineOrderDto);
    
    List<PProductEntity> findProductsByOrderId(String orderId);

}
