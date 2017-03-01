package com.hengtiansoft.business.context.dto;

import java.io.Serializable;

public class StockImageDto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long actImageRecordId;
    
    private String imageUrl;

    public Long getActImageRecordId() {
        return actImageRecordId;
    }

    public void setActImageRecordId(Long actImageRecordId) {
        this.actImageRecordId = actImageRecordId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
