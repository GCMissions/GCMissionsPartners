package com.hengtiansoft.business.product.dto;

import java.util.Date;

import com.hengtiansoft.common.dto.PagingDto;

public class PGoodsSearchDto extends PagingDto<PGoodsDto> {

    private static final long serialVersionUID = 3649632743371721239L;

    private String goodCode;

    private String goodName;

    private Date beginDate;

    private Date endDate;

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
