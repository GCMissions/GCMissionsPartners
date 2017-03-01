package com.hengtiansoft.business.product.dto;

import java.io.Serializable;

/**
 * 
* Class Name: ProductFloorSearchDto
* Description: 商品楼层搜索DTO
* @author zhisongliu
*
 */
public class ProductFloorSearchDto implements Serializable{

	private static final long serialVersionUID = -4036418502701016692L;
	
	private Integer regionId;
	
	
	private String  floorType;
	

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public String getFloorType() {
		return floorType;
	}

	public void setFloorType(String floorType) {
		this.floorType = floorType;
	}

}
