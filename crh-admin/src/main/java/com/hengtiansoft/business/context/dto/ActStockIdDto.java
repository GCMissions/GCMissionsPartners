package com.hengtiansoft.business.context.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class ActStockIdDto extends PagingDto<ImageListDto> {

    private static final long serialVersionUID = 1L;
    
    private Long actStockId;

    public Long getActStockId() {
        return actStockId;
    }

    public void setActStockId(Long actStockId) {
        this.actStockId = actStockId;
    }
}
