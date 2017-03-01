package com.hengtiansoft.wrw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hengtiansoft.common.domain.BaseEntity;

/**
 * Class Name: ActivityStock
 * Description: 活动库存
 * @author zhongyidong
 *
 */
@Entity
@Table(name = "act_stock")
public class ActivityStock extends BaseEntity{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "PRODUCT_ID")
    private Long productId;
    
    /**
    * 库存类型：0-按规格;1-按总库存;2-不需要库存.
    */
    @Column(name = "STOCK_TYPE")
    private String stockType;
    
    /**
     * 库存数量(原始库存量)
     */
    @Column(name = "ORIGINAL_COUNT")
    private Integer originalCount;
    
    /**
     * 库存数量(剩余库存量)
     */
    @Column(name = "TOTAL_COUNT")
    private Integer totalCount;
    
    /**
     * 活动时间
     */
    @Column(name = "ACT_DATE")
    private Date actDate;

    /**
     * 前台是否显示库存：0-显示；1-不显示
     */
    @Column(name = "SHOW_STOCK")
    private String showStock;
    
    /**
     * 规格信息
     */
    @Column(name = "SPEC_INFO")
    private String specInfo;
    
    /**
     * 最高价
     */
    @Column(name = "HIGH_PRICE")
    private Integer highPrice;
    
    /**
     * 最低价
     */
    @Column(name = "LOW_PRICE")
    private Integer lowPrice;
    
    /**
     * vip最高价
     */
    @Column(name = "HIGH_VIP_PRICE")
    private Integer highVipPrice;
    
    /**
     * vip最低价
     */
    @Column(name = "LOW_VIP_PRICE")
    private Integer lowVipPrice;
    
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
    
}
