package com.hengtiansoft.business.region.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RegionOpenDto implements Serializable {

    private static final long   serialVersionUID = -8770976277675289487L;

    private Integer             regionId;

    private String              regionName;

    private Integer             parentId;

    private List<RegionOpenDto> childrenList     = new ArrayList<RegionOpenDto>();

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

    public List<RegionOpenDto> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<RegionOpenDto> childrenList) {
        this.childrenList = childrenList;
    }

}
