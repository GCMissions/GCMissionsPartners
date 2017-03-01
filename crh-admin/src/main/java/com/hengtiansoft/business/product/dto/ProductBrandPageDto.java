package com.hengtiansoft.business.product.dto;

import com.hengtiansoft.common.dto.PagingDto;

/**
 * Description: 品牌列表分页dto
 * @author jiekaihu
 *
 */
public class ProductBrandPageDto extends PagingDto<ProductBrandDto> {

	private static final long serialVersionUID = 1L;

	private String keyWord;

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

}
