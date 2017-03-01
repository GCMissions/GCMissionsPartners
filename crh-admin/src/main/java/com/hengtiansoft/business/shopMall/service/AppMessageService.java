package com.hengtiansoft.business.shopMall.service;

import java.io.UnsupportedEncodingException;

import com.hengtiansoft.business.shopMall.dto.AppMessageDto;
import com.hengtiansoft.business.shopMall.dto.AppMessagePageDto;
import com.hengtiansoft.common.dto.ResultDto;

/**
 * 
 * @author jiekaihu
 *
 */
public interface AppMessageService {

	void search(AppMessagePageDto dto);

	AppMessageDto view(Long messageId);

	ResultDto<String> save(AppMessageDto dto) throws UnsupportedEncodingException;
	
}
