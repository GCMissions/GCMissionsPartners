package com.hengtiansoft.business.product.service;


import java.util.List;

import com.hengtiansoft.business.product.dto.AttributeDto;
import com.hengtiansoft.business.product.dto.AttributeSearchDto;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.wrw.entity.PAttributeEntity;

/**
 * 
* Class Name: AttributeService
* Description: 属性业务类
* @author zhisongliu
*
 */
public interface AttributeService {
	
	/**
	 * 搜索某个属性
	 * 
	 * @param dto
	 */
	void search(AttributeSearchDto dto);
	
	/**
	 * 保存某个属性
	 * 
	 * @param dto
	 * @return
	 */
	ResultDto<String> save(AttributeDto dto);
	
	/**
	 * 查询某个属性
	 * @param id
	 * @return
	 */
	AttributeDto findById(Long id);
	
	/**
	 * 更新某个属性
	 * 
	 * @param dto
	 * @return
	 */
	ResultDto<String> update(AttributeDto dto);
	
	/**
	 * 删除某个属性
	 * @param id
	 * @return
	 */
	ResultDto<String> delete(Long id);
	
	/**
	 * 查询所有状态为正常的属性值
	 * @return
	 */
	List<PAttributeEntity> findAllAttributes();

}
