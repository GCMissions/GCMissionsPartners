package com.hengtiansoft.business.barcode.dto;

import java.io.Serializable;
import java.util.List;

public class BarcodePageDto implements Serializable{

    private static final long serialVersionUID = 2097374650540432854L;
    
    private Long goodCountNum;
    
    private List<BarcodeGoodDto> goodDtos;

    public Long getGoodCountNum() {
        return goodCountNum;
    }

    public void setGoodCountNum(Long goodCountNum) {
        this.goodCountNum = goodCountNum;
    }

    public List<BarcodeGoodDto> getGoodDtos() {
        return goodDtos;
    }

    public void setGoodDtos(List<BarcodeGoodDto> goodDtos) {
        this.goodDtos = goodDtos;
    }

    
    
    
}
