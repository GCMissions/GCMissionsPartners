package com.hengtiansoft.business.shopStock.platformStock.dto;

import java.io.Serializable;

public class AdminStockDto implements Serializable{

    private static final long serialVersionUID = 6705957295114225992L;
    
    private Long  goodsId;
    //物料编码
    private String goodsCode;
    //物料名称
    private String goodsName;
    //销售价
    private String price;
    //库存总量
    private Long stockNum;
    
    public Long getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
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
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public Long getStockNum() {
        return stockNum;
    }
    public void setStockNum(Long stockNum) {
        this.stockNum = stockNum;
    }
}
