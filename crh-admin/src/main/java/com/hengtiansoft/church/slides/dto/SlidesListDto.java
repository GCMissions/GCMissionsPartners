package com.hengtiansoft.church.slides.dto;

import java.io.Serializable;

public class SlidesListDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    
    private String index;
    
    private String image;
    
    private String description;
    
    private String displayed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayed() {
        return displayed;
    }

    public void setDisplayed(String displayed) {
        this.displayed = displayed;
    }
}
