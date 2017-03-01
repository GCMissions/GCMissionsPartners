package com.hengtiansoft.business.marketing.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengtiansoft.common.serialize.CustomMoneySerializer;

/**
 * 
* Class Name: BargainChartDto
* Description: 砍价活动详情信息
* @author chenghongtu
*
 */
public class BargainChartDto {
    
    //排名
    private Integer rownum;
    //用户昵称
    private String bargainName;
    //手机号
    private String phone;
    //原价
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Long price;
    //现价
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Long currentPrice;
    //底价
    @JsonSerialize(using = CustomMoneySerializer.class)
    private Long basePrice;
    //用户自身转发次数
    private Integer shareTotal;
    //帮砍人数
    private Integer bargainNum;

    public Integer getRownum() {
        return rownum;
    }

    public void setRownum(Integer rownum) {
        this.rownum = rownum;
    }

    public String getBargainName() {
        return bargainName;
    }

    public void setBargainName(String bargainName) {
        this.bargainName = bargainName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Long currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Long getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Long basePrice) {
        this.basePrice = basePrice;
    }

    public Integer getShareTotal() {
        return shareTotal;
    }

    public void setShareTotal(Integer shareTotal) {
        this.shareTotal = shareTotal;
    }

    public Integer getBargainNum() {
        return bargainNum;
    }

    public void setBargainNum(Integer bargainNum) {
        this.bargainNum = bargainNum;
    }

}
