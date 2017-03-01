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
 * 
* Class Name: BargainProductEntity
* Description: 砍价商品表
* @author chenghongtu
*
 */
@Entity
@Table(name = "bargain_product")
public class BargainProductEntity extends BaseEntity{

    
    private static final long serialVersionUID = 1968775801701724882L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long              id;
    
    @Column(name = "PRODUCT_NAME")
    private String            productName;
    
    @Column(name = "IMAGE")
    private String            image;
    
    @Column(name = "BARGAIN_IMAGE")
    private String            bargainImage;

    @Column(name = "PRICE")
    private Long              price;

    @Column(name = "BASE_PRICE")
    private Long              basePrice;
    
    @Column(name = "DESCRIPTION")
    private String            description;
    
    @Column(name = "BARGAIN_TYPE")
    private String            bargainType;
    
    @Column(name = "BARGAIN_AMOUNT")
    private Long            bargainAmount;
    
    @Column(name = "BARGAIN_MIN_AMOUNT")
    private Long            bargainMinAmount;
    
    @Column(name = "BARGAIN_MAX_AMOUNT")
    private Long            bargainMaxAmount;
    
    @Column(name = "STATUS")
    private String            status;
    
    @Column(name = "EFFECTIVE_START_DATE")
    private Date              effectiveStartDate;
    
    @Column(name = "EFFECTIVE_END_DATE")
    private Date              effectiveEndDate;
    
    @Column(name = "DEL_FLAG")
    private String          delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBargainImage() {
        return bargainImage;
    }

    public void setBargainImage(String bargainImage) {
        this.bargainImage = bargainImage;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Long basePrice) {
        this.basePrice = basePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBargainType() {
        return bargainType;
    }

    public void setBargainType(String bargainType) {
        this.bargainType = bargainType;
    }

    public Long getBargainAmount() {
        return bargainAmount;
    }

    public void setBargainAmount(Long bargainAmount) {
        this.bargainAmount = bargainAmount;
    }

    public Long getBargainMinAmount() {
        return bargainMinAmount;
    }

    public void setBargainMinAmount(Long bargainMinAmount) {
        this.bargainMinAmount = bargainMinAmount;
    }

    public Long getBargainMaxAmount() {
        return bargainMaxAmount;
    }

    public void setBargainMaxAmount(Long bargainMaxAmount) {
        this.bargainMaxAmount = bargainMaxAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getEffectiveStartDate() {
        return effectiveStartDate;
    }

    public void setEffectiveStartDate(Date effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    public Date getEffectiveEndDate() {
        return effectiveEndDate;
    }

    public void setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
    
}
