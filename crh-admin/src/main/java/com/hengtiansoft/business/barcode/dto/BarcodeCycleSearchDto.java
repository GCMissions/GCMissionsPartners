package com.hengtiansoft.business.barcode.dto;

import com.hengtiansoft.common.dto.PagingDto;

/**
 * Class Name: BarcodeCycleSearchDto
 * Description: TODO
 * 
 * @author hongqi
 */
public class BarcodeCycleSearchDto extends PagingDto<BarcodeCycleDto> {

    /**
     * 
     */
    private static final long serialVersionUID = 8212090100830224847L;

    private String            prefixCode;

    public String getPrefixCode() {
        return prefixCode;
    }

    public void setPrefixCode(String prefixCode) {
        this.prefixCode = prefixCode;
    }

}
