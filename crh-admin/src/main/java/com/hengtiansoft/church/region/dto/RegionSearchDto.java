package com.hengtiansoft.church.region.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class RegionSearchDto extends PagingDto<RegionListDto> {

    private static final long serialVersionUID = 1L;

    private String regionName;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}
