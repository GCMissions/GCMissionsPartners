package com.hengtiansoft.business.shopStock.platformStock.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class AdminSearchDto extends PagingDto<AdminStockDto> {

    private static final long serialVersionUID = 306974883969428149L;

    // 物料编码
    private String            goodsCode;

    // 物料名称
    private String            goodsName;

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

}
