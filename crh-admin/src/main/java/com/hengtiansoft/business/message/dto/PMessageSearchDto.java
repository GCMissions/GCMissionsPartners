package com.hengtiansoft.business.message.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class PMessageSearchDto extends PagingDto<PMessageDto> {

    private static final long serialVersionUID = 4845370635147536218L;

    private String            title;

    private String            type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
