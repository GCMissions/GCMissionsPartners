package com.hengtiansoft.crh.dto;

import java.io.Serializable;

public class ResourceDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
    
    private String imgSrc;
    
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
