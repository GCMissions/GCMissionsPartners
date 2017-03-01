package com.hengtiansoft.wrw.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hengtiansoft.common.domain.BaseEntity;

/**
 * 
 * Class Name: KdTwentyFourHoursEntity Description: 酷袋24小时活动商品底价详情实体类
 * 
 * @author chengchaoyin
 *
 */
@Entity
@Table(name = "kd_tf_price_detail")
public class KdTFPriceDetailEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "TF_ID")
    private Long tfId;
    
    /**
     * 子规格组合
     */
    @Column(name = "SPEC_GROUP")
    private String specGroup;
    
    @Column(name = "PRICE")
    private Long price;
    
    @Column(name = "BARGAIN_AMOUNT")
    private Long bargainAmount;

    @Column(name = "BARGAIN_MAX_AMOUNT")
    private Long bargainMaxAmount;

    @Column(name = "BARGAIN_MIN_AMOUNT")
    private Long bargainMinAmount;

    @Column(name = "BARGAIN_TYPE")
    private String bargainType;
    
    /**
     * 是否删除。0-未删除，1-已删除
     */
    @Column(name = "IS_DELETED")
    private String isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTfId() {
        return tfId;
    }

    public void setTfId(Long tfId) {
        this.tfId = tfId;
    }

    public String getSpecGroup() {
        return specGroup;
    }

    public void setSpecGroup(String specGroup) {
        this.specGroup = specGroup;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getBargainAmount() {
        return bargainAmount;
    }

    public void setBargainAmount(Long bargainAmount) {
        this.bargainAmount = bargainAmount;
    }

    public Long getBargainMaxAmount() {
        return bargainMaxAmount;
    }

    public void setBargainMaxAmount(Long bargainMaxAmount) {
        this.bargainMaxAmount = bargainMaxAmount;
    }

    public Long getBargainMinAmount() {
        return bargainMinAmount;
    }

    public void setBargainMinAmount(Long bargainMinAmount) {
        this.bargainMinAmount = bargainMinAmount;
    }

    public String getBargainType() {
        return bargainType;
    }

    public void setBargainType(String bargainType) {
        this.bargainType = bargainType;
    }

}
