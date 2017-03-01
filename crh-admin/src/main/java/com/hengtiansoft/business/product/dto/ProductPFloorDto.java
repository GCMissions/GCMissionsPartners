package com.hengtiansoft.business.product.dto;

import java.io.Serializable;

public class ProductPFloorDto implements Serializable{

	private static final long serialVersionUID = -8866946099931293281L;
	
	private String productName;
	
	private String productCode;
	
	private Long productId;
	
	private String brandName;
	
	private Long brandId;
	
	private String cateName;
	
	private Long cateId;
	
	private Long sort;
	
	private String image;
	
	private String shiefStatus;//上下架状态
	

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public Long getCateId() {
		return cateId;
	}

	public void setCateId(Long cateId) {
		this.cateId = cateId;
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

    public String getShiefStatus() {
        return shiefStatus;
    }

    public void setShiefStatus(String shiefStatus) {
        this.shiefStatus = shiefStatus;
    }
	
	
}
