package com.hengtiansoft.church.resource.dto;

import java.io.Serializable;

public class ResourceListDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;

    private String index;
    
    private String image;
    
    private String link;
    
    private String title;

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
