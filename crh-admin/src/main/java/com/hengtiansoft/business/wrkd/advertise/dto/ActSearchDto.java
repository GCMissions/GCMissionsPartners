package com.hengtiansoft.business.wrkd.advertise.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class ActSearchDto extends PagingDto<ActListDto> {

    private static final long serialVersionUID = 1L;

    private String actName;
    
    private String actType;

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
