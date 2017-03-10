package com.hengtiansoft.church.partner.dto;

import java.io.Serializable;
import java.util.List;

import com.hengtiansoft.church.entity.CountryEntity;
import com.hengtiansoft.church.entity.RegionEntity;

public class PartnerSaveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    
    private String partnerName;
    
    private String mission;
    
    private Long regionId;
    
    private Long countryId;
    
    private String regionName;
    
    private String countryName;
    
    private String address;
    
    private String image;
    
    private String introduce;
    
    private String type;
    
    private List<RegionEntity> regionList;
    
    private List<CountryEntity> countryList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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
