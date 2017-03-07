package com.hengtiansoft.church.region.service;

import java.util.List;

import com.hengtiansoft.church.entity.CountryEntity;
import com.hengtiansoft.church.region.dto.RegionDetailDto;
import com.hengtiansoft.church.region.dto.RegionSaveDto;
import com.hengtiansoft.church.region.dto.RegionSearchDto;
import com.hengtiansoft.common.dto.ResultDto;

public interface RegionAdminService {

    void searchRegion(RegionSearchDto dto);
    
    ResultDto<?> deleteRegion(Long id);

    RegionDetailDto regionDetail(Long id);

    ResultDto<?> saveRegion(RegionSaveDto dto);
    
    List<CountryEntity> findNoRegionCountries();
}
