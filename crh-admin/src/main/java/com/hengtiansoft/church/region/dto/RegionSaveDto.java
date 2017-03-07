package com.hengtiansoft.church.region.dto;

import java.io.Serializable;
import java.util.List;

public class RegionSaveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    
    private String regionName;
    
    private String color;
    
    private List<Long> countryIdList;

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Long> getCountryIdList() {
        return countryIdList;
    }

    public void setCountryIdList(List<Long> countryIdList) {
        this.countryIdList = countryIdList;
    }
}
