package com.hengtiansoft.business.product.dto;

import java.io.Serializable;

/**
 * Description: 品牌编辑数据dto
 * @author jiekaihu
 *
 */
public class ProductBrandEditDto implements Serializable{
	
	private static final long serialVersionUID = -8275214334571109035L;
	
	private ProductBrandDto brandDto;

	/**
	 * @return the brandDto
	 */
	public ProductBrandDto getBrandDto() {
		return brandDto;
	}

	/**
	 * @param brandDto
	 *            the brandDto to set
	 */
	public void setBrandDto(ProductBrandDto brandDto) {
		this.brandDto = brandDto;
	}

}
