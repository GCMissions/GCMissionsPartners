package com.hengtiansoft.business.product.dto;

import java.io.Serializable;
import java.util.List;

import com.hengtiansoft.wrw.entity.PTypeEntity;

/**
 * Description: 分类编辑数据dto
 * @author jiekaihu
 *
 */
public class ProductCategoryEditDto implements Serializable{

	private static final long serialVersionUID = 5704579783680405706L;
	
	private ProductCategoryDto cateDto;
	private List<PTypeEntity> typeList;
	private String parentName;
	private List<ProductCategoryDto> cateList;//上一级同级List
	
	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return parentName;
	}
	/**
	 * @param parentName the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	/**
	 * @return the cateDto
	 */
	public ProductCategoryDto getCateDto() {
		return cateDto;
	}
	/**
	 * @param cateDto the cateDto to set
	 */
	public void setCateDto(ProductCategoryDto cateDto) {
		this.cateDto = cateDto;
	}
	/**
	 * @return the typeList
	 */
	public List<PTypeEntity> getTypeList() {
		return typeList;
	}
	/**
	 * @param typeList the typeList to set
	 */
	public void setTypeList(List<PTypeEntity> typeList) {
		this.typeList = typeList;
	}
	public List<ProductCategoryDto> getCateList() {
		return cateList;
	}
	public void setCateList(List<ProductCategoryDto> cateList) {
		this.cateList = cateList;
	}
	
}
