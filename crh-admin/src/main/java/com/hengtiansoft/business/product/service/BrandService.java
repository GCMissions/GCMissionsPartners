package com.hengtiansoft.business.product.service;

import java.util.List;

import com.hengtiansoft.business.product.dto.ProductBrandDto;
import com.hengtiansoft.business.product.dto.ProductBrandPageDto;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.wrw.entity.PBrandEntity;

/**
 * 
 * @author jiekaihu
 *
 */
public interface BrandService {

	/**
	 * Description: 新建品牌信息
	 *
	 */
	ResultDto<String> saveBrand(ProductBrandDto dto);

	/**
	 * Description: 修改品牌信息
	 */
	ResultDto<String> editBrand(ProductBrandDto dto);

	/**
	 * Description: 分页获取品牌列表
	 *
	 */
	void getBrandList(ProductBrandPageDto dto);

	/**
	 * Description: 根据品牌ID获得品牌详情
	 *
	 * @param brandId
	 */
	ProductBrandDto findOne(Long brandId);

	/**
	 * Description: 根据品牌ID进行删除
	 * 
	 * @param brandId
	 * @return
	 */
	ResultDto<StringBuilder> deleteById(Long brandId);
	
	/**
	 * Description: 简单获取所有品牌
	 */
	List<ProductBrandDto> findAll();

	List<PBrandEntity> findAllBrands();
}
