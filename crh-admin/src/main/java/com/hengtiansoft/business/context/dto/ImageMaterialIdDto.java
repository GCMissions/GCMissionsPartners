package com.hengtiansoft.business.context.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class ImageMaterialIdDto extends PagingDto<MaterialListDto> {

    private static final long serialVersionUID = 1L;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
