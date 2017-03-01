package com.hengtiansoft.wrw.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hengtiansoft.common.domain.BaseEntity;

/**
 * The persistent class for the app_image database table.
 * 
 */
@Entity
@Table(name = "coolbag_image")
public class CoolbagImageEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -374544994579331440L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FEATURE_ID")
    private Long featureId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SORT")
    private Integer sort;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "URL")
    private String url;
    
    @Column(name = "ACT_ID")
    private Long actId;
    

    @Column(name = "ACT_NAME")
    private String actName;
   

    @Column(name = "PRODUCT_ID")
    private Long  productId;
    

    @Column(name = "PRODUCT_NAME")
    private String productName;
    

    @Column(name = "RELF_URL")
    private String relfUrl;
    
    private String status;

    public CoolbagImageEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFeatureId() {
        return this.featureId;
    }

    public void setFeatureId(Long featureId) {
        this.featureId = featureId;
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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

	public Long getActId() {
		return actId;
	}

	public void setActId(Long actId) {
		this.actId = actId;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getRelfUrl() {
		return relfUrl;
	}

	public void setRelfUrl(String relfUrl) {
		this.relfUrl = relfUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    
}
