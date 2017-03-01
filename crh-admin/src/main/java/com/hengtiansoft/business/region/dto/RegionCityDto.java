package com.hengtiansoft.business.region.dto;

import java.io.Serializable;

public class RegionCityDto implements Serializable {

    private static final long serialVersionUID = -177333078505453396L;

    private Integer           regionId;

    private String            regionName;

    private Integer           parentId;

    private Integer           cityNum          = 0;

    private Integer           openNum          = 0;

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getCityNum() {
        return cityNum;
    }

    public void setCityNum(Integer cityNum) {
        this.cityNum = cityNum;
    }

    public Integer getOpenNum() {
        return openNum;
    }

    public void setOpenNum(Integer openNum) {
        this.openNum = openNum;
    }

}
