package com.hengtiansoft.business.intersection.service;

import java.util.List;

import com.hengtiansoft.business.intersection.dto.IntersectionSaveDto;
import com.hengtiansoft.business.intersection.dto.IntersectionSearchDto;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.wrw.entity.SIntersectionEntity;

public interface IntersectionService {

    void searchIntersection(IntersectionSearchDto dto);
    
    ResultDto<?> save(IntersectionSaveDto dto);
    
    SIntersectionEntity findIntersection(Long id);
    
    ResultDto<?> delete(List<Long> ids);
    
    /**
     * 
     * Description: 热门详情
     *
     * @param id
     * @return
     */
    ResultDto<?> getIntersectionDetail(Long id);
}
