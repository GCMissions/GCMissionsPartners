package com.hengtiansoft.business.product.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
* Class Name: ProductFloorSaveDto
* Description: 商品楼层保存DTO
* @author zhisongliu
*
 */
public class ProductFloorSaveDto implements Serializable{

	private static final long serialVersionUID = -8388442316609330083L;
	
	
	private Integer regionId;
	
	private String floorType;
	
	private List<ProductPFloorDto> list;

	public List<ProductPFloorDto> getList() {
		return list;
	}

	public void setList(List<ProductPFloorDto> list) {
		this.list = list;
	}

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
