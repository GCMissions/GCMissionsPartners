package com.hengtiansoft.business.advertise.dto;

import java.io.Serializable;

/**
 * 
* Class Name: AdvertiseDto
* Description: TODO
* @author yiminli
*
 */
public class AdvertiseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long adId;
    
    private Long acId;
    
    private String url;
    
    private String activeName;
    
    private Integer sort;
    
    private Integer index;
    
    private String shortName;

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public Long getAcId() {
        return acId;
    }

    public void setAcId(Long acId) {
        this.acId = acId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getActiveName() {
        return activeName;
    }

    public void setActiveName(String activeName) {
        this.activeName = activeName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
