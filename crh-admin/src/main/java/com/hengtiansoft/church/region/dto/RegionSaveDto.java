package com.hengtiansoft.church.region.dto;

import java.io.Serializable;

public class RegionSaveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    
    private String regionName;
    
    private String color;
    
    private Long[] countryIdList;
    
    private String type;

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

    public Long[] getCountryIdList() {
        return countryIdList;
    }

    public void setCountryIdList(Long[] countryIdList) {
        this.countryIdList = countryIdList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
