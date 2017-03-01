package com.hengtiansoft.business.product.dto;

import java.io.Serializable;
import java.util.List;

public class PTypeDto implements Serializable{
	
	private static final long serialVersionUID = -659547563617028497L;

	private String typeName;
	
	private Long  typeId;
	
	private List<Long> attrIds;
	
	private List<String> attrNames;
	
	private List<Long>  brandIds;
	
	private List<String> brandNames;
	
	private Long sort;
	
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public List<Long> getAttrIds() {
		return attrIds;
	}

	public void setAttrIds(List<Long> attrIds) {
		this.attrIds = attrIds;
	}

	public List<String> getAttrNames() {
		return attrNames;
	}

	public void setAttrNames(List<String> attrNames) {
		this.attrNames = attrNames;
	}

	public List<Long> getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(List<Long> brandIds) {
		this.brandIds = brandIds;
	}

	public List<String> getBrandNames() {
		return brandNames;
	}

	public void setBrandNames(List<String> brandNames) {
		this.brandNames = brandNames;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	
	

}
