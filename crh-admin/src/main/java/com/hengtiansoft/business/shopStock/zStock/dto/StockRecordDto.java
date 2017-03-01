package com.hengtiansoft.business.shopStock.zStock.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class StockRecordDto extends PagingDto<StockRecordDto> {

    private static final long serialVersionUID = 1587169340811403201L;

    private Long              recordId;

    private Long              stockId;

    private String            operType;

    private Integer           changeNum;

    private String            operDate;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public Integer getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(Integer changeNum) {
        this.changeNum = changeNum;
    }

    public String getOperDate() {
        return operDate;
    }

    public void setOperDate(String operDate) {
        this.operDate = operDate;
    }

}
