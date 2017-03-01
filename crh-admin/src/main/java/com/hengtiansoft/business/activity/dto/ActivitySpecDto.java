package com.hengtiansoft.business.activity.dto;

import net.sf.json.JSONObject;

/**
 * Class Name: ActivitySpecDto
 * Description: 活动规格Dto
 * @author zhongyidong
 *
 */
public class ActivitySpecDto {
    
    private Long id;
    
    private Long actStockId;
    
    /**
     * 子规格
     */
    private String subSpec;
    
    /**
     * 父规格
     */
    private String mainSpec;
    
    /**
     * 最小单位量
     */
    private Integer unitNum;
    
    /**
     * 组数（份数）
     */
    private Integer groupNum;
    
    /**
     * 总和
     */
    private Integer total;
    
    /**
     * 价格map（用于快速查询价格）
     */
    private JSONObject prices;
    
    /**
     * 限购map（用于快速查询限购数量）
     */
    private JSONObject limits;
    
    /**
     * vip价格map（用于快速查询vip价格）
     */
    private JSONObject vipPrices;
    
    /**
     * 价格描述
     */
    private String priceDesc;
    
    /**
     * 价格描述
     */
    private JSONObject priceDescJson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActStockId() {
        return actStockId;
    }

    public void setActStockId(Long actStockId) {
        this.actStockId = actStockId;
    }

    public String getSubSpec() {
        return subSpec;
    }

    public void setSubSpec(String subSpec) {
        this.subSpec = subSpec;
    }

    public String getMainSpec() {
        return mainSpec;
    }

    public void setMainSpec(String mainSpec) {
        this.mainSpec = mainSpec;
    }

    public Integer getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(Integer unitNum) {
        this.unitNum = unitNum;
    }

    public Integer getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(Integer groupNum) {
        this.groupNum = groupNum;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public JSONObject getPrices() {
        return prices;
    }

    public void setPrices(JSONObject prices) {
        this.prices = prices;
    }

    public JSONObject getLimits() {
        return limits;
    }

    public void setLimits(JSONObject limits) {
        this.limits = limits;
    }

    public JSONObject getVipPrices() {
        return vipPrices;
    }

    public void setVipPrices(JSONObject vipPrices) {
        this.vipPrices = vipPrices;
    }

    public String getPriceDesc() {
        return priceDesc;
    }

    public void setPriceDesc(String priceDesc) {
        this.priceDesc = priceDesc;
    }

    public JSONObject getPriceDescJson() {
        return priceDescJson;
    }

    public void setPriceDescJson(JSONObject priceDescJson) {
        this.priceDescJson = priceDescJson;
    }
}
