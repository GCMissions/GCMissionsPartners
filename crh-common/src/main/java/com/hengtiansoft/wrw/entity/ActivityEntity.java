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
* Class Name: ActivityEntity
* Description: 活动表（基本信息、详情）
* @author chenghongtu
*
 */
@Entity
@Table(name = "activity")
public class ActivityEntity extends BaseEntity{

    private static final long serialVersionUID = -2775597090599807619L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long              id;

    @Column(name = "ORG_ID")
    private Long              orgId;

    @Column(name = "CATE_ID")
    private Long              cateId;

    @Column(name = "ACTIVITY_NAME")
    private String            activityName;

    @Column(name = "PRICE")
    private Integer              price;

    @Column(name = "ORIGINAL_PRICE")
    private Integer              originalPrice;

    @Column(name = "DESCRIPTION")
    private String            description;

    @Column(name = "NOTE")
    private String            note;

    @Column(name = "REBACK_NOTE")
    private String            rebackNote;

    @Column(name = "DEL_FLAG")
    private String delFlag;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

}
