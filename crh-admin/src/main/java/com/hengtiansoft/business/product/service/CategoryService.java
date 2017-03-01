package com.hengtiansoft.business.product.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;

import com.hengtiansoft.business.product.dto.ProductCategoryDto;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.xmemcached.constant.CacheType;
import com.hengtiansoft.wrw.entity.PCategoryEntity;

/**
 * 
 * @author jiekaihu
 *
 */
public interface CategoryService {

	/**
	 * Description: 新建分类信息
	 *
	 */
	ResultDto<String> saveCategory(ProductCategoryDto dto);

	/**
	 * Description: 修改分类信息
	 *
	 */
	ResultDto<String> editCategory(ProductCategoryDto dto);

	/**
	 * Description: 获取所有分类
	 *
	 */
	List<ProductCategoryDto> findAll();

	/**
	 * Description: 获取分类一级列表
	 *
	 */
	List<ProductCategoryDto> getCategoryFatherList();

	/**
	 * Description: 获取单个父节点下的分类列表
	 * 
	 * @param cateId
	 */
	List<ProductCategoryDto> getListById(Long cateId);

	/**
	 * Description: 根据分类ID获得分类详情
	 *
	 * @param cateId
	 */
	ProductCategoryDto getCategory(Long cateId);

	/**
	 * Description: 根据分类ID进行删除
	 * 
	 * @param cateId
	 */
	ResultDto<String> deleteById(Long cateId);

	List<PCategoryEntity> findAllCates();
	
	/**
	 * Description: 根据分类ID获取所有上一级分类
	 * @param cateId
	 * @return
	 */
	List<ProductCategoryDto> getParentListById(Long cateId);
	
	List<PCategoryEntity> findAllFathers();
	
	/**
	 * 
	* Description: 清除品类缓存
	*
	* @author chengchaoyin
	 */
	@CacheEvict(value = CacheType.WELY_CATEGORY_CACHE, key = "'findAllCate'")
	void cleanCategoryCache();
}
