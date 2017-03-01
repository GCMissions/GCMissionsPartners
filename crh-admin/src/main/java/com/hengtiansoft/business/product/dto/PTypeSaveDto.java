package com.hengtiansoft.business.product.dto;

import java.util.List;

import com.hengtiansoft.common.dto.RequestDto;

public class PTypeSaveDto extends RequestDto{

	private static final long serialVersionUID = 6582918133084046688L;
	
	private Long typeId;
	
	private String typeName;
	
	private Long sort;
	
	private String remark;
	
	private List<Long> brandIds;
	
	private List<Long> attrIds;


	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName.trim();
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public List<Long> getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(List<Long> brandIds) {
		this.brandIds = brandIds;
	}

	public List<Long> getAttrIds() {
		return attrIds;
	}

	public void setAttrIds(List<Long> attrIds) {
		this.attrIds = attrIds;
	}

	

	

}
