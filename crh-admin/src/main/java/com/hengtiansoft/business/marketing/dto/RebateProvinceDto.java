package com.hengtiansoft.business.marketing.dto;

import java.io.Serializable;
import java.util.List;

public class RebateProvinceDto implements Serializable {

    private static final long serialVersionUID = 4861671744482409137L;

    private Long              regionId;

    private String            regionName;

    private List<RebateDto>   detail;

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public List<RebateDto> getDetail() {
        return detail;
    }

    public void setDetail(List<RebateDto> detail) {
        this.detail = detail;
    }

}
