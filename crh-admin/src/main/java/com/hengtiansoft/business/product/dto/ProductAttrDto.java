package com.hengtiansoft.business.product.dto;

import java.io.Serializable;

/**
 * 
* Class Name: ProductAttrDto
* Description: 商品属性DTO
* @author zhisongliu
*
 */
public class ProductAttrDto implements Serializable{

	private static final long serialVersionUID = -7654433852007144799L;
	
	private Long attrId;
	
	private String attrName;
	
	private Long attrValueId;
	
	private String attrValue;

	public Long getAttrId() {
		return attrId;
	}

	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public Long getAttrValueId() {
		return attrValueId;
	}

	public void setAttrValueId(Long attrValueId) {
		this.attrValueId = attrValueId;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}
	
	
	
	
}
