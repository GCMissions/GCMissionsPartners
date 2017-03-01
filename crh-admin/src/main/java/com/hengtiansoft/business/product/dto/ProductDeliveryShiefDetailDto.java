package com.hengtiansoft.business.product.dto;

import java.io.Serializable;

/**
 * 
* Class Name: ProductDeliveryShiefDetailDto
* Description: 商品上下架详情DTO
* @author zhisongliu
*
 */
public class ProductDeliveryShiefDetailDto implements Serializable{
	
	private static final long serialVersionUID = 4673143488805053060L;
	
	private Integer regionId;
	
	private String saleFlag;

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public String getSaleFlag() {
		return saleFlag;
	}

	public void setSaleFlag(String saleFlag) {
		this.saleFlag = saleFlag;
	}

}
