package com.hengtiansoft.wrw.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hengtiansoft.common.domain.BaseEntity;

/**
 * The persistent class for the app_feature database table.
 * 
 */
@Entity
@Table(name = "coolbag_feature")
public class CoolbagFeatureEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 818801063388116688L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tag;

    private String name;

    private String description;

    private String details;

    private String image;

    @Column(name = "BUY_URL")
    private String buyUrl;

    private Integer sort;
    
    private String status;
    
    @Column(name = "SHEIF_STATUS")
    private String sheifStatus;
    
    @Column(name = "IF_HOMESHOW")
    private String ifHomeshow;
    
    @Column(name ="SHEIF_DATE")
    private Date   sheifDate;
   

    public CoolbagFeatureEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return this.details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return this.sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBuyUrl() {
        return buyUrl;
    }

    public void setBuyUrl(String buyUrl) {
        this.buyUrl = buyUrl;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSheifStatus() {
		return sheifStatus;
	}

	public void setSheifStatus(String sheifStatus) {
		this.sheifStatus = sheifStatus;
	}

	public String getIfHomeshow() {
		return ifHomeshow;
	}

	public void setIfHomeshow(String ifHomeshow) {
		this.ifHomeshow = ifHomeshow;
	}

    public Date getSheifDate() {
        return sheifDate;
    }

    public void setSheifDate(Date sheifDate) {
        this.sheifDate = sheifDate;
    }
    
}
