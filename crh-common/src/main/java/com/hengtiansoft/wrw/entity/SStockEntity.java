package com.hengtiansoft.wrw.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "S_STOCK")
public class SStockEntity implements Serializable {

    private static final long serialVersionUID = 5393278491114591904L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STOCK_ID")
    private Long              stockId;

    @Column(name = "ORG_ID")
    private Long              orgId;

    @Column(name = "ORG_NAME")
    private String              orgName;

    @Column(name = "PRODUCT_ID")
    private Long              productId;
    
    @Column(name = "GOODS_ID")
    private Long              goodsId;

    @Column(name = "SAFE_NUM")
    private Long              safeNum;

    @Column(name = "STANDARD_NUM")
    private Long              standardNum;

    @Column(name = "STOCK_NUM")
    private Long              stockNum;

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getSafeNum() {
        return safeNum;
    }

    public void setSafeNum(Long safeNum) {
        this.safeNum = safeNum;
    }

    public Long getStandardNum() {
        return standardNum;
    }

    public void setStandardNum(Long standardNum) {
        this.standardNum = standardNum;
    }

    public Long getStockNum() {
        return stockNum;
    }

    public void setStockNum(Long stockNum) {
        this.stockNum = stockNum;
    }

    @Override
    public String toString() {
        return "SStockEntity [stockId=" + stockId + ", orgId=" + orgId + ", productId=" + productId + ", safeNum=" + safeNum + ", standardNum="
                + standardNum + ", stockNum=" + stockNum + "]";
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
