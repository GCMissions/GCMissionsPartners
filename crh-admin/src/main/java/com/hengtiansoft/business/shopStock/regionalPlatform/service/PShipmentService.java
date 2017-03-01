package com.hengtiansoft.business.shopStock.regionalPlatform.service;

import java.util.List;

import com.hengtiansoft.business.provider.dto.SearchOrgDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.GoodsStockSearchDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.dto.PShipmentDetailPageDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.dto.PShipmentDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.dto.PShipmentSearchDto;

/**
 * Class Name: PShipmentService
 * Description: TODO
 * 
 * @author chengminmiao
 */
public interface PShipmentService {

    void searchDto(PShipmentSearchDto searchDto);

    Integer getShipmentCount();

    PShipmentDto findOrder(String orderId);

    PShipmentDetailPageDto findDetailOrder(String orderId);

    String changeStatus(String id);

    List<List<String>> findAllOrder(String orderId);

    SearchOrgDto getZorg();

    GoodsStockSearchDto searchProductStock(GoodsStockSearchDto dto);
}
