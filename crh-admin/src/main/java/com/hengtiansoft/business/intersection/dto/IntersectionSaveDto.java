package com.hengtiansoft.business.intersection.dto;

import java.io.Serializable;

public class IntersectionSaveDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;

    private String name;
    
    private String description;
    
    private String opration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOpration() {
        return opration;
    }

    public void setOpration(String opration) {
        this.opration = opration;
    }
}
