package com.hengtiansoft.business.context.dto;

import java.io.Serializable;

public class ImageAndIdDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long imageMaterialId;
    
    private String url;

    public Long getImageMaterialId() {
        return imageMaterialId;
    }

    public void setImageMaterialId(Long imageMaterialId) {
        this.imageMaterialId = imageMaterialId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
