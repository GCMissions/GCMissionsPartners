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
@Table(name = "P_Shief")
public class PShiefEntity implements Serializable {

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

    @Column(name = "SHIEF_DATE")
    private Date            shiefDate;

    @Column(name = "UNSHIEF_DATE")
    private Date            unShiefDate;

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

    public Date getShiefDate() {
        return shiefDate;
    }

    public void setShiefDate(Date shiefDate) {
        this.shiefDate = shiefDate;
    }

    public Date getUnShiefDate() {
        return unShiefDate;
    }

    public void setUnShiefDate(Date unShiefDate) {
        this.unShiefDate = unShiefDate;
    }
}
