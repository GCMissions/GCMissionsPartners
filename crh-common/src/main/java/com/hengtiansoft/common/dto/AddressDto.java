package com.hengtiansoft.common.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Pattern;

public class AddressDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    @Pattern(regexp = "\\w+")
    private String address;
    @Pattern(regexp = "\\d{7,14}")
    private String addressPhone;
    @Pattern(regexp = "\\w+")
    private String addressContact;
    @Pattern(regexp = "\\d+")
    private Integer regionId;
    private String defFlag;
    private String status;
    private Date createDate;
    private Date modifyDate;
    private String lng;
    private String lat;
    private String areaName;

    public String getDefFlag() {
        return defFlag;
    }

    public void setDefFlag(String defFlag) {
        this.defFlag = defFlag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressPhone() {
        return addressPhone;
    }

    public void setAddressPhone(String addressPhone) {
        this.addressPhone = addressPhone;
    }

    public String getAddressContact() {
        return addressContact;
    }

    public void setAddressContact(String addressContact) {
        this.addressContact = addressContact;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
