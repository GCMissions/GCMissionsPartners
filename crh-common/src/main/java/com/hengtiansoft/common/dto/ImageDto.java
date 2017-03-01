package com.hengtiansoft.common.dto;

public class ImageDto {
	private String sourcePath;
	
	private Integer weiht;
	
	private Integer height;

	public ImageDto(String sourcePath, Integer weiht, Integer height) {
		this.sourcePath=sourcePath;
		this.height=height;
		this.weiht=weiht;
	}

	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public Integer getWeiht() {
		return weiht;
	}

	public void setWeiht(Integer weiht) {
		this.weiht = weiht;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}
}
