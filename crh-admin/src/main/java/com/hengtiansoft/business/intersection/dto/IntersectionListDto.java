package com.hengtiansoft.business.intersection.dto;

import java.io.Serializable;

public class IntersectionListDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    
    private Integer index;
    
    private String name;
    
    private String createDate;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
