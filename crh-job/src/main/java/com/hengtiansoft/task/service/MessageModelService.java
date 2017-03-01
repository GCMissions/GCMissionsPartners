package com.hengtiansoft.task.service;

import com.hengtiansoft.task.dto.MessageModelDto;
import com.hengtiansoft.task.dto.SBasicParaDto;


/**
 * 
 * @author jiekaihu
 *
 */
public interface MessageModelService {
	/**
	 * 获取消息模板数据
	 * 
	 * @return
	 */
//	@Cacheable(value = CacheType.DEFAULT, key = "'Message_Data'")
	MessageModelDto getMessageModel();

	/**
	 * 编辑保存
	 * 
	 * @param dto
	 */
//	@CacheEvict(value = CacheType.DEFAULT, key = "'Message_Data'")
	void save(MessageModelDto dto);
	
	/**
	 * 根据参数名 获取参数dto
	 * @param paraName
	 * @return
	 */
	SBasicParaDto getBasicParaByParaName(String paraName);
}
