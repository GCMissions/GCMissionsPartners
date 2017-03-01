package com.hengtiansoft.business.activity.dto;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hengtiansoft.common.serialize.CustomMoneySerializer;
/**
 * 
* Class Name: ActivityCopyDto
* Description:  复制已有活动（已有活动列表）
* @author chenghongtu
*
 */
public class ActivityCopyDto {

    private Long productId;

    private Long cateId;
    
    private String cateName;
    // 父-子
    private String psCate;
    
    private Long orgId;
    
    private String orgName;

    private String productName;

    @JsonSerialize(using = CustomMoneySerializer.class)
    private Long price;

    @JsonSerialize(using = CustomMoneySerializer.class)
    private Long originalPrice;

    private String note;
    
    private String rebackNote;

    private String description;
    
    private String isCaptcha;
    
    private String vip;

    private List<ActivityImageDto> listImages;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Long originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRebackNote() {
        return rebackNote;
    }

    public void setRebackNote(String rebackNote) {
        this.rebackNote = rebackNote;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ActivityImageDto> getListImages() {
        return listImages;
    }

    public void setListImages(List<ActivityImageDto> listImages) {
        this.listImages = listImages;
    }

    public String getPsCate() {
        return psCate;
    }

    public void setPsCate(String psCate) {
        this.psCate = psCate;
    }

    public String getIsCaptcha() {
        return isCaptcha;
    }

    public void setIsCaptcha(String isCaptcha) {
        this.isCaptcha = isCaptcha;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }
    
}
