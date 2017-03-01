package com.hengtiansoft.business.product.dto;

import java.io.Serializable;

/**
 * 
* Class Name: AttributeValueDto
* Description: 属性值DTO
* @author zhisongliu
*
 */
public class AttributeValueDto implements Serializable{

	private static final long serialVersionUID = -1184441171614739361L;
	
	private Long attrValueId;
	
	private String valueInfo;
	
	private String status;
	
	private String image;
	
	private Long sort;

	public Long getAttrValueId() {
		return attrValueId;
	}

	public void setAttrValueId(Long attrValueId) {
		this.attrValueId = attrValueId;
	}

	public String getValueInfo() {
		return valueInfo;
	}

	public void setValueInfo(String valueInfo) {
		this.valueInfo = valueInfo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	

}
