package com.hengtiansoft.crh.dto;

import java.io.Serializable;
import java.util.List;

public class GroupDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    
    private String color;
    
    private List<CountryDto> countryList;
    
    private List<PartnerDto> partnerList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<CountryDto> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<CountryDto> countryList) {
        this.countryList = countryList;
    }

    public List<PartnerDto> getPartnerList() {
        return partnerList;
    }

    public void setPartnerList(List<PartnerDto> partnerList) {
        this.partnerList = partnerList;
    }
}
