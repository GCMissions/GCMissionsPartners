package com.hengtiansoft.business.shopStock.regionalPlatform.service;

import java.util.List;

import com.hengtiansoft.business.shopStock.regionalPlatform.dto.PReceivingDetailPageDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.dto.PReceivingDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.dto.PReceivingSearchDto;

/**
 * Class Name: PReceivingService
 * Description: TODO
 * 
 * @author chengminmiao
 */
public interface PReceivingService {

    void searchDto(PReceivingSearchDto searchDto);

    Integer getReceivingCount();

    PReceivingDetailPageDto findDetailOrder(String orderId);

    PReceivingDto findOrder(String orderId);

    String changeStatus(String id);

    List<List<String>> findAllOrder(String orderId);
}
