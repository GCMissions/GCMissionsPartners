package com.hengtiansoft.crh.dto;

import java.io.Serializable;

public class PartnerDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    
    private String imgSrc;
    
    private String title;
    
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
