package com.hengtiansoft.wrw.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "P_PRICE")
public class PPriceEntity implements Serializable {

    private static final long serialVersionUID = -5593740445776428431L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRICE_ID")
    private Long              priceId;

    @Column(name = "GOOD_ID")
    private Long              goodId;

    @Column(name = "REGION_ID")
    private Integer           regionId;

    @Column(name = "PRODUCT_ID")
    private Long              productId;

    @Column(name = "SALE_FLAG")
    private String            saleFlag;

    @Column(name = "PRICE")
    private Long              price;
    
    @Column(name = "MORE_PRICE")
    private Long              morePrice;

    @Column(name = "COST_PRICE")
    private Long              costPrice;

    @Column(name = "OPER_COSTS")
    private Long              operCosts;

    @Column(name = "PRICE_LEVEL")
    private String            priceLevel;

    @Column(name = "P_REBATE")
    private Double            pRebate;

    @Column(name = "Z_REBATE")
    private Double            zRebate;

    @Column(name = "CREATE_DATE")
    private Date              createDate;

    @Column(name = "CREATE_ID")
    private Long              createId;

    @Column(name = "MODIFY_DATE")
    private Date              modifyDate;

    @Column(name = "MODIFY_ID")
    private Long              modifyId;

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSaleFlag() {
        return saleFlag;
    }

    public void setSaleFlag(String saleFlag) {
        this.saleFlag = saleFlag;
    }

    public Long getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Long costPrice) {
        this.costPrice = costPrice;
    }

    public Long getOperCosts() {
        return operCosts;
    }

    public void setOperCosts(Long operCosts) {
        this.operCosts = operCosts;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
    
    public Long getMorePrice() {
        return morePrice;
    }

    public void setMorePrice(Long morePrice) {
        this.morePrice = morePrice;
    }

    public String getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(String priceLevel) {
        this.priceLevel = priceLevel;
    }

    public Double getpRebate() {
        return pRebate;
    }

    public void setpRebate(Double pRebate) {
        this.pRebate = pRebate;
    }

    public Double getzRebate() {
        return zRebate;
    }

    public void setzRebate(Double zRebate) {
        this.zRebate = zRebate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Long getModifyId() {
        return modifyId;
    }

    public void setModifyId(Long modifyId) {
        this.modifyId = modifyId;
    }

    @Override
    public String toString() {
        return "PPriceEntity [priceId=" + priceId + ", goodId=" + goodId + ", regionId=" + regionId + ", productId="
                + productId + ", saleFlag=" + saleFlag + ", price=" + price + ", morePrice=" + morePrice
                + ", costPrice=" + costPrice + ", operCosts=" + operCosts + ", priceLevel=" + priceLevel + ", pRebate="
                + pRebate + ", zRebate=" + zRebate + ", createDate=" + createDate + ", createId=" + createId
                + ", modifyDate=" + modifyDate + ", modifyId=" + modifyId + "]";
    }
}
