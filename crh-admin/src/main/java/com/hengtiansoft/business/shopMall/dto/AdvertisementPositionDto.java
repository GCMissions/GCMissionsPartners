package com.hengtiansoft.business.shopMall.dto;

import java.io.Serializable;

public class AdvertisementPositionDto implements Serializable{

	private static final long serialVersionUID = -7677021845992378070L;

	/**
     * 对应广告的位子，数据库中的local字段
     */
    private String position;

    /**
     * 广告位名称
     */
    private String name;
    /**
     * 最多上传几张图片
     */
    private Integer maxImageCount;
	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the maxImageCount
	 */
	public Integer getMaxImageCount() {
		return maxImageCount;
	}
	/**
	 * @param maxImageCount the maxImageCount to set
	 */
	public void setMaxImageCount(Integer maxImageCount) {
		this.maxImageCount = maxImageCount;
	}
    
}
