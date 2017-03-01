package com.hengtiansoft.business.product.dto;

import java.io.Serializable;
import java.util.List;

import com.hengtiansoft.wrw.entity.PTypeEntity;

/**
 * Description:分类添加 数据dto
 * @author jiekaihu
 *
 */
public class ProductCategoryAddDto implements Serializable{

	private static final long serialVersionUID = -476363253993047415L;
	
	private List<PTypeEntity> typeList;

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
	
}
