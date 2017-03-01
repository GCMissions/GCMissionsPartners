package com.hengtiansoft.business.advertise.dto;

import java.io.Serializable;

public class DescriptionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long adId;
    
    private String description;
    
    private String url;

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
