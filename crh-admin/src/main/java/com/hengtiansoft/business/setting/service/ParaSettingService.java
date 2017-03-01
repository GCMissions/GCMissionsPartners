package com.hengtiansoft.business.setting.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.multipart.MultipartFile;

import com.hengtiansoft.business.setting.dto.ParaSettingDto;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.xmemcached.constant.CacheType;

/**
 * 
 * @author jiekaihu
 *
 */
public interface ParaSettingService {
	/**
	 * 获取参数设置数据
	 * 
	 * @return
	 */
	@Cacheable(value = CacheType.DEFAULT, key = "'Para_Data'")
	ParaSettingDto getParaSetting();

	/**
	 * 编辑保存
	 * 
	 * @param dto
	 */
	@CacheEvict(value = CacheType.DEFAULT, key = "'Para_Data'")
	ResultDto<String> save(ParaSettingDto dtoList);
	
    ResultDto<String> uploadApp(MultipartFile multipartFile,String appType);
    
    /**
    * Description: 设置App版本参数
    *
    * @param dtoList
    */
    void setAppParam(ParaSettingDto dtoList);
}
