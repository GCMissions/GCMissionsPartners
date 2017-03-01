package com.hengtiansoft.business.activity.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hengtiansoft.common.serialize.CustomMoneyDeserializer;
/**
 * 
* Class Name: ActivitySaveDto
* Description: 新增商品（基本信息）
* @author chenghongtu
*
 */
public class ActivitySaveDto implements Serializable {

    private static final long serialVersionUID = 7840381962811648846L;

    private Long cateId;
    
    private Long orgId;

    private String productName;

    @JsonDeserialize(using = CustomMoneyDeserializer.class)
    private Long price;

    @JsonDeserialize(using = CustomMoneyDeserializer.class)
    private Long originalPrice;
    
    private String isCaptcha;
    
    private String vip;

    private String note;
    
    private String rebackNote;

    private List<ActivityImageDto> listImages;

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getActivityName() {
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

    public List<ActivityImageDto> getListImages() {
        return listImages;
    }

    public void setListImages(List<ActivityImageDto> listImages) {
        this.listImages = listImages;
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

    public String getProductName() {
        return productName;
    }
    
}
