package com.hengtiansoft.business.activity.dto;

import java.io.Serializable;

public class ActivityImageDto implements Serializable{

	private static final long serialVersionUID = -3480016971561834336L;
	
	private Long sort;
	
	private String title;
	
	private String imageUrl;
	
	private Long  imageId;
	
	private String imageKey;

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

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }
	
}
