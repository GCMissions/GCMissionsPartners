package com.hengtiansoft.business.shopMall.dto;

import java.io.Serializable;

public class ProductImagesDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6925303850583843815L;
	private String image;
	private Long order;
	
	public ProductImagesDto(String image, Long order) {
		super();
		this.image = image;
		this.order = order;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Long getOrder() {
		return order;
	}
	public void setOrder(Long order) {
		this.order = order;
	}
	
	
}
