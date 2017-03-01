package com.hengtiansoft.business.advertise.dto;

import java.io.Serializable;

import com.hengtiansoft.common.dto.PagingDto;

public class ActCategoryDto extends PagingDto<SearchAdvertiseDto> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long firstCategoryId;
    
    private Long secondCategoryId;
    
    private Long orgId;
    
    private String actName;
    
    private String status;

    public Long getFirstCategoryId() {
        return firstCategoryId;
    }

    public void setFirstCategoryId(Long firstCategoryId) {
        this.firstCategoryId = firstCategoryId;
    }

    public Long getSecondCategoryId() {
        return secondCategoryId;
    }

    public void setSecondCategoryId(Long secondCategoryId) {
        this.secondCategoryId = secondCategoryId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
