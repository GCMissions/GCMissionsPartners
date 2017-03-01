package com.hengtiansoft.business.activity.dto;

import java.util.Date;
import java.util.List;

/**
 * Class Name: ActivityStockDto
 * Description: 活动库存
 * @author zhongyidong
 *
 */
public class ActivityStockDto {
    
    private Long id;
    
    private Long productId;
    
    /**
    * 库存类型：0-按规格;1-按总库存;2-不需要库存.
    */
    private String stockType;
    
    /**
     * 库存数量(原始库存量)
     */
    private Integer originalCount;

    /**
     * 库存数量(剩余库存量)
     */
    private Integer totalCount;
    
    /**
     * 活动时间
     */
    private Date actDate;

    /**
     * 前台是否显示库存：0-显示；1-不显示
     */
    private String showStock;
    
    /**
     * 规格库存
     */
    private String specInfo;
    
    /**
     * 最高价
     */
    private Integer highPrice;
    
    /**
     * 最低价
     */
    private Integer lowPrice;
    
    /**
     * 所有价格
     */
    private List<Integer> priceList;
    
    /**
     * vip最高价
     */
    private Integer highVipPrice;
    
    /**
     * vip最低价
     */
    private Integer lowVipPrice;
    
    /**
     * 所有价格
     */
    private List<Integer> vipPriceList;
    
    /**
     * 规格（主规格-子规格）
     */
    private List<SpecDto> specDtoList;
    
    /**
     * 规格库存
     */
    private List<ActivitySpecDto> actSpecList;
    
    /**
     * 活动日期list(包含每日库存量)
     */
    private List<ActivityDateDto> actDateList;
    
    /**
     * 购买信息
     */
    private ActivityConstranintDto actConstranintDto;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public Integer getOriginalCount() {
        return originalCount;
    }

    public void setOriginalCount(Integer originalCount) {
        this.originalCount = originalCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Date getActDate() {
        return actDate;
    }

    public void setActDate(Date actDate) {
        this.actDate = actDate;
    }

    public String getShowStock() {
        return showStock;
    }

    public void setShowStock(String showStock) {
        this.showStock = showStock;
    }

    public String getSpecInfo() {
        return specInfo;
    }

    public void setSpecInfo(String specInfo) {
        this.specInfo = specInfo;
    }

    public Integer getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(Integer highPrice) {
        this.highPrice = highPrice;
    }

    public Integer getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(Integer lowPrice) {
        this.lowPrice = lowPrice;
    }
    
    public List<Integer> getPriceList() {
        return priceList;
    }
    
    public void setPriceList(List<Integer> priceList) {
        this.priceList = priceList;
    }

    public Integer getHighVipPrice() {
        return highVipPrice;
    }

    public void setHighVipPrice(Integer highVipPrice) {
        this.highVipPrice = highVipPrice;
    }

    public Integer getLowVipPrice() {
        return lowVipPrice;
    }

    public void setLowVipPrice(Integer lowVipPrice) {
        this.lowVipPrice = lowVipPrice;
    }

    public List<Integer> getVipPriceList() {
        return vipPriceList;
    }

    public void setVipPriceList(List<Integer> vipPriceList) {
        this.vipPriceList = vipPriceList;
    }

    public List<SpecDto> getSpecDtoList() {
        return specDtoList;
    }

    public void setSpecDtoList(List<SpecDto> specDtoList) {
        this.specDtoList = specDtoList;
    }

    public List<ActivitySpecDto> getActSpecList() {
        return actSpecList;
    }

    public void setActSpecList(List<ActivitySpecDto> actSpecList) {
        this.actSpecList = actSpecList;
    }

    public List<ActivityDateDto> getActDateList() {
        return actDateList;
    }
    
    public void setActDateList(List<ActivityDateDto> actDateList) {
        this.actDateList = actDateList;
    }

    public ActivityConstranintDto getActConstranintDto() {
        return actConstranintDto;
    }
    
    public void setActConstranintDto(ActivityConstranintDto actConstranintDto) {
        this.actConstranintDto = actConstranintDto;
    }
    
}
