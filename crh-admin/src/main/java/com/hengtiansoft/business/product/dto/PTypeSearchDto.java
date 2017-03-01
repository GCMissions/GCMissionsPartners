package com.hengtiansoft.business.product.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class PTypeSearchDto extends PagingDto<PTypeDto> {

    private static final long serialVersionUID = 7813576261600409427L;

    private String            msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
