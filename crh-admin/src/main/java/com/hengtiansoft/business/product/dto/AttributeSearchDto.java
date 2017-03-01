package com.hengtiansoft.business.product.dto;

import com.hengtiansoft.common.dto.PagingDto;

/**
 * Class Name: AttributeSearchDto
 * Description: 属性搜索DTO
 * 
 * @author zhisongliu
 */
public class AttributeSearchDto extends PagingDto<AttributeDto> {

    private static final long serialVersionUID = 2394814862008780121L;

    private String            keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}
