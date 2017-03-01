package com.hengtiansoft.business.product.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 商品品牌Dto
 * @author jiekaihu
 *
 */
public class ProductBrandDto implements Serializable{

	private static final long serialVersionUID = -5423380612391477631L;

	private Long brandId;

    private String brandName;

    private String logo;

    private String status;

    private String description;
    
    private Long sort;
    
    private Date createDate;
    
    public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
