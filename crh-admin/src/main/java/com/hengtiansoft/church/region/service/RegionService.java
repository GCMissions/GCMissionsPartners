package com.hengtiansoft.church.region.service;

import com.hengtiansoft.church.region.dto.RegionSaveDto;
import com.hengtiansoft.church.region.dto.RegionSearchDto;
import com.hengtiansoft.common.dto.ResultDto;

public interface RegionService {

    void searchRegion(RegionSearchDto dto);
    
    ResultDto<?> deleteRegion(Long id);

    RegionSaveDto regionDetail(Long id);

    ResultDto<?> saveRegion(RegionSaveDto dto);
}
