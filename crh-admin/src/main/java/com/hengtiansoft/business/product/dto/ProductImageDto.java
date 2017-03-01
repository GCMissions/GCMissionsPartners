package com.hengtiansoft.business.product.dto;

import java.io.Serializable;

public class ProductImageDto implements Serializable{

	private static final long serialVersionUID = -3480016971561834336L;
	
	private Long sort;
	
	private String title;
	
	private String imageUrl;
	
	private Long  imageId;

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }
	
	
	
}
