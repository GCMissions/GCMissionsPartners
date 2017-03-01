package com.hengtiansoft.business.product.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengtiansoft.common.util.CustomDoubleSerialize;
import com.hengtiansoft.common.util.WRWUtil;

/**
 * Class Name: PGoodsDto
 * 
 * Description: 物料表Dto
 * 
 * @author zhishenghong
 */
public class PGoodsDto implements Serializable {

    private static final long serialVersionUID = -373621593695140877L;

    private Long goodsId;

    private String goodCode;

    private String goodName;

    private Long price;

    private BigDecimal priceYuan;

    private Date createDate;

    private Long createId;

    private Date modifyDate;

    private Long modifyId;

    private String specs;

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
        this.priceYuan = WRWUtil.transFenToYuan(price);
    }

    @JsonSerialize(using = CustomDoubleSerialize.class)
    public BigDecimal getPriceYuan() {
        return priceYuan;
    }

    public void setPriceYuan(BigDecimal priceYuan) {
        this.priceYuan = priceYuan;
        this.price = WRWUtil.transYuanToFen(priceYuan);
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Long getModifyId() {
        return modifyId;
    }

    public void setModifyId(Long modifyId) {
        this.modifyId = modifyId;
    }

}
