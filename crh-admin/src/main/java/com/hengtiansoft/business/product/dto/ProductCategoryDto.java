package com.hengtiansoft.business.product.dto;

import java.io.Serializable;

/**
 * Description: 商品分类dto
 * 
 * @author jiekaihu
 *
 */
public class ProductCategoryDto implements Serializable {

	private static final long serialVersionUID = -3360223201011878617L;

	private Long cateId;

	private Long typeId;

	private Long parentId;

	private String cateName;

	private String status;

	private String path;

	private Long sort;

	private String image;
	
	private String imageKey;

	private Boolean hasSon;

	public Boolean getHasSon() {
		return hasSon;
	}

	public void setHasSon(Boolean hasSon) {
		this.hasSon = hasSon;
	}

	public Long getCateId() {
		return cateId;
	}

	public void setCateId(Long cateId) {
		this.cateId = cateId;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

}
