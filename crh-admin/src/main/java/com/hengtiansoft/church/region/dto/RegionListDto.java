package com.hengtiansoft.church.region.dto;

import java.io.Serializable;

public class RegionListDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;

    private String regionName;
    
    private String createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
