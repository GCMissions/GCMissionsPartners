package com.hengtiansoft.business.shopStock.regionalPlatform.dto;

import java.io.Serializable;

public class PReceivingDetailDto implements Serializable {

    /**
     * Variables Name: serialVersionUID Description: TODO Value Description: TODO
     */
    private static final long serialVersionUID = 6960121348288632687L;

    private String goodsId;

    private String goodsCode;

    private String name;

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

    public String getGoodsCode() {
        return goodsCode;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
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

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

}
