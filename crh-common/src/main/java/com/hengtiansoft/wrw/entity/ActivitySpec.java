package com.hengtiansoft.wrw.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hengtiansoft.common.domain.BaseEntity;

/**
 * Class Name: ActivitySpec
 * Description: 活动规格信息
 * @author zhongyidong
 *
 */
@Entity
@Table(name = "act_spec")
public class ActivitySpec extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name  = "ACT_STOCK_ID")
    private Long actStockId;
    
    /**
     * 子规格
     */
    @Column(name  = "SUB_SPEC")
    private String subSpec;
    
    /**
     * 父规格
     */
    @Column(name  = "MAIN_SPEC")
    private String mainSpec;
    
    /**
     * 最小单位量
     */
    @Column(name  = "UNIT_NUM")
    private Integer unitNum;
    
    /**
     * 组数（份数）
     */
    @Column(name  = "GROUP_NUM")
    private Integer groupNum;
    
    /**
     * 总和
     */
    @Column(name  = "TOTAL")
    private Integer total;
    
    /**
     * 价格组合
     */
    @Column(name  = "PRICES")
    private String prices;
    
    /**
     * 限购数量组合
     */
    @Column(name  = "LIMITS")
    private String limits;
    
    /**
     * vip价格组合
     */
    @Column(name = "VIP_PRICES")
    private String vipPrices;
    
    /**
     * 价格描述
     */
    @Column(name  = "PRICE_DESC")
    private String priceDesc;

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

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public String getLimits() {
        return limits;
    }

    public void setLimits(String limits) {
        this.limits = limits;
    }
    
    public String getVipPrices() {
        return vipPrices;
    }

    public void setVipPrices(String vipPrices) {
        this.vipPrices = vipPrices;
    }

    public String getPriceDesc() {
        return priceDesc;
    }

    public void setPriceDesc(String priceDesc) {
        this.priceDesc = priceDesc;
    }

}
