package com.hengtiansoft.business.product.service;

import java.util.List;

import com.hengtiansoft.business.product.dto.PTypeSaveDto;
import com.hengtiansoft.business.product.dto.PTypeSearchDto;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.wrw.entity.PTypeEntity;

public interface PTypeService {
	
	/**
	 * 搜索某个类型
	 * @param dto
	 */
	void search(PTypeSearchDto dto);

	/**
	 * 保存某个类型
	 * @param dto
	 * @return
	 */
	ResultDto<String> save(PTypeSaveDto dto);
	
	/**
	 * 查询某个类型
	 * 
	 * @param id
	 * @return
	 */
	PTypeSaveDto findById(Long id);
	
	/**
	 * 更新某个类型
	 * 
	 * @param dto
	 * @return
	 */
	ResultDto<String> update(PTypeSaveDto dto);
	
	/**
	 * 删除某个类型
	 * 
	 * @param id
	 * @return
	 */
	ResultDto<String> delete(Long id);
	
	/**
	 * 查询所有的类型
	 * 
	 * 
	 * @return
	 */
	List<PTypeEntity> findAll();
}
