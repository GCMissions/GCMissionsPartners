package com.hengtiansoft.church.region.dto;

import java.io.Serializable;
import java.util.List;

import com.hengtiansoft.church.entity.CountryEntity;

public class RegionDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String regionName;

    private String color;

    private List<CountryEntity> haveCountryList;

    private List<CountryEntity> notHaveCountryList;

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

    public List<CountryEntity> getHaveCountryList() {
        return haveCountryList;
    }

    public void setHaveCountryList(List<CountryEntity> haveCountryList) {
        this.haveCountryList = haveCountryList;
    }

    public List<CountryEntity> getNotHaveCountryList() {
        return notHaveCountryList;
    }

    public void setNotHaveCountryList(List<CountryEntity> notHaveCountryList) {
        this.notHaveCountryList = notHaveCountryList;
    }
}
