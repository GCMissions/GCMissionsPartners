package com.hengtiansoft.common.dto;

import java.io.Serializable;
import java.util.List;

public class RegionResponseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer parentId;
    private String name;
    private String longitude;
    private String latitude;
    private String cityCode;
    private List<RegionResponseDto> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<RegionResponseDto> getChildren() {
        return children;
    }

    public void setChildren(List<RegionResponseDto> children) {
        this.children = children;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

}
