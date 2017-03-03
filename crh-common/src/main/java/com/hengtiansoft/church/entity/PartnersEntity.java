package com.hengtiansoft.church.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hengtiansoft.common.domain.BaseEntity;

@Entity
@Table(name = "partners")
public class PartnersEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "PARTNER_NAME")
    private String partnerName;
    
    @Column(name = "IMAGE")
    private String image;
    
    @Column(name = "MISSION")
    private String mission;
    
    @Column(name = "C_R_REF_ID")
    private Long cRRefId;
    
    @Column(name = "INTRODUCE")
    private String introduce;
    
    @Column(name = "DEL_FLAG")
    private String delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public Long getcRRefId() {
        return cRRefId;
    }

    public void setcRRefId(Long cRRefId) {
        this.cRRefId = cRRefId;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
