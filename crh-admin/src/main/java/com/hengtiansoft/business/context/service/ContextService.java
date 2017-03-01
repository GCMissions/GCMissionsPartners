package com.hengtiansoft.business.context.service;

import java.util.List;

import com.hengtiansoft.business.context.dto.ActStockIdDto;
import com.hengtiansoft.business.context.dto.SearchActImaDto;
import com.hengtiansoft.common.dto.ResultDto;

public interface ContextService {

    void findList(SearchActImaDto dto);
    
    ResultDto<?> uploadActImag(Long actStockId, String imageUrl);
    
    ResultDto<?> uploadActImags(Long actStockId, List<String> imageUrls);
    
    SearchActImaDto getInfoByStockId(Long stockId);
    
    void findImages(ActStockIdDto dto);
    
    ResultDto<?> deleteImages(List<Long> actImageRecordId);
}
