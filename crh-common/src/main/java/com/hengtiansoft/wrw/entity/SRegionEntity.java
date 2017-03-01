package com.hengtiansoft.wrw.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "S_REGION")
public class SRegionEntity implements Serializable {

    private static final long serialVersionUID = 8284338562052945658L;

    @Id
    @Column(name = "ID")
    private Integer           id;

    @Column(name = "NAME")
    private String            name;

    @Column(name = "PARENT_ID")
    private Integer           parentId;

    @Column(name = "SHORT_NAME")
    private String            shortName;

    @Column(name = "LEVEL_TYPE")
    private Integer           levelType;

    @Column(name = "CITY_CODE")
    private String            cityCode;

    @Column(name = "ZIP_CODE")
    private String            zipCode;

    @Column(name = "MERGER_NAME")
    private String            mergerName;

    @Column(name = "PINYIN")
    private String            pinyin;

    @Column(name = "LNG")
    private String            lng;

    @Column(name = "LAT")
    private String            lat;

    @Column(name = "OPEN_FLAG")
    private String            openFlag;

    @Column(name = "TREE_PATH")
    private String            treePath;

    @Column(name = "LOGIN_FLAG")
    private String            loginFlag;

    @Column(name = "HOT_FLAG")
    private String            hotFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Integer getLevelType() {
        return levelType;
    }

    public void setLevelType(Integer levelType) {
        this.levelType = levelType;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getMergerName() {
        return mergerName;
    }

    public void setMergerName(String mergerName) {
        this.mergerName = mergerName;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(String openFlag) {
        this.openFlag = openFlag;
    }

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }

    public String getLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag;
    }

    public String getHotFlag() {
        return hotFlag;
    }

    public void setHotFlag(String hotFlag) {
        this.hotFlag = hotFlag;
    }

}
