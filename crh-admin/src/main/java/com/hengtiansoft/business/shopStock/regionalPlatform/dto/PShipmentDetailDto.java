package com.hengtiansoft.business.shopStock.regionalPlatform.dto;

import java.io.Serializable;

public class PShipmentDetailDto implements Serializable {

    /**
     * Variables Name: serialVersionUID Description: TODO Value Description: TODO
     */
    private static final long serialVersionUID = -8807865786202294606L;

    private String goodsId;

    private String goodName;

    private String goodCode;

    private String price;

    private String createDate;

    private Long num;

    private String priceYuan;

    public String getPriceYuan() {
        return priceYuan;
    }

    public void setPriceYuan(String priceYuan) {
        this.priceYuan = priceYuan;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
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

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

}
