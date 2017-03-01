package com.hengtiansoft.business.shopStock.zStock.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class StockSearchDto extends PagingDto<StockDto> {

    private static final long serialVersionUID = -3581819379325514848L;

    private String            name;

    private String            sn;

    private String            status;

    private String            stockLow;

    private String            stockHigh;

    private String            stock;

    private String            orgCode;
    
    private String            orgType;

    private String            isWarning;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStockLow() {
        return stockLow;
    }

    public void setStockLow(String stockLow) {
        this.stockLow = stockLow;
    }

    public String getStockHigh() {
        return stockHigh;
    }

    public void setStockHigh(String stockHigh) {
        this.stockHigh = stockHigh;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getIsWarning() {
        return isWarning;
    }

    public void setIsWarning(String isWarning) {
        this.isWarning = isWarning;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

}
