package com.hengtiansoft.business.activity.dto;

import java.io.Serializable;

public class ActivityTypeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long type;
    
    private String typeName;

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
