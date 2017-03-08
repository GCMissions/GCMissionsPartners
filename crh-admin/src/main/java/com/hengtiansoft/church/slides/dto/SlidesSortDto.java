package com.hengtiansoft.church.slides.dto;

import java.io.Serializable;

public class SlidesSortDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
