package com.hengtiansoft.business.product.dto;

import java.io.Serializable;
import java.util.List;

public class ProductPriceShiefDto implements Serializable{

	private static final long serialVersionUID = 6053632665987320312L;
	
	private Long productId;
	
	private List<ProductRegionDto> list;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public List<ProductRegionDto> getList() {
		return list;
	}

	public void setList(List<ProductRegionDto> list) {
		this.list = list;
	}
	
	
}
