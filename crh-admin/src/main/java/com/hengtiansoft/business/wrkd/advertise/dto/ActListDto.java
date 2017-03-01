package com.hengtiansoft.business.wrkd.advertise.dto;

import java.io.Serializable;

public class ActListDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer index;
    
    private Long actId;
    
    private String actName;
    
    private String actType;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getActType() {
        return actType;
    }

    public void setActType(String actType) {
        this.actType = actType;
    }
}
