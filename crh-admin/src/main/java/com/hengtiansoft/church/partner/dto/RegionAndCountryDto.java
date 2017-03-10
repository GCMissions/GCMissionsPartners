package com.hengtiansoft.church.partner.dto;

import java.io.Serializable;
import java.util.List;

import com.hengtiansoft.church.entity.CountryEntity;
import com.hengtiansoft.church.entity.RegionEntity;

public class RegionAndCountryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<RegionEntity> regionList;
    
    private List<CountryEntity> countryList;

    public List<RegionEntity> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<RegionEntity> regionList) {
        this.regionList = regionList;
    }

    public List<CountryEntity> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<CountryEntity> countryList) {
        this.countryList = countryList;
    }
}
