package com.hengtiansoft.business.region.service;

import java.util.List;

import com.hengtiansoft.business.region.dto.RegionAddDto;
import com.hengtiansoft.business.region.dto.RegionCityDto;

public interface RegionCityService {

	List<RegionCityDto> findRegionByLevelType();

	void update(RegionAddDto dto);

}
