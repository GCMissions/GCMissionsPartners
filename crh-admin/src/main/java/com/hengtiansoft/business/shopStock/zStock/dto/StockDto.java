package com.hengtiansoft.business.shopStock.zStock.dto;

import java.io.Serializable;

public class StockDto implements Serializable {

    private static final long serialVersionUID = -7116996219608372311L;

    private Integer id;

    private String name;

    private String price;

    private String priceYuan;

    private String createDate;

    private Integer safeNum;

    private Integer stockNum;

    private String status;

    private String warningCount;

    private String goodCode;

    public String getPriceYuan() {
        return priceYuan;
    }

    public void setPriceYuan(String priceYuan) {
        this.priceYuan = priceYuan;
    }

    public Integer getSafeNum() {
        return safeNum;
    }

    public void setSafeNum(Integer safeNum) {
        this.safeNum = safeNum;
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWarningCount() {
        return warningCount;
    }

    public void setWarningCount(String warningCount) {
        this.warningCount = warningCount;
    }

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

}
