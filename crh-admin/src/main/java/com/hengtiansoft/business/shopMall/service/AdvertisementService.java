package com.hengtiansoft.business.shopMall.service;

import java.util.List;

import com.hengtiansoft.business.shopMall.dto.AdvertisementDto;
import com.hengtiansoft.business.shopMall.dto.AdvertisementPageDto;
import com.hengtiansoft.business.shopMall.dto.AdvertisementPositionDto;
import com.hengtiansoft.common.dto.ResultDto;

/**
 * 
 * @author jiekaihu
 *
 */
public interface AdvertisementService {

	/**
	 * Description: 添加广告信息
	 *
	 */
	ResultDto<String> saveAdvertisement(AdvertisementDto dto);

	/**
	 * Description: 修改广告信息
	 *
	 */
	ResultDto<String> editAdvertisement(AdvertisementDto dto);

	/**
	 * Description: 分页获取所有广告
	 *
	 */
	void getAdvertisementList(AdvertisementPageDto dto);

	/**
	 * Description: 根据广告ID获得广告详情
	 *
	 * @param adId
	 */
	AdvertisementDto getAdvertisement(Long adId);

	/**
	 * Description: 根据广告ID进行删除（1个以上）
	 * 
	 * @param cateId
	 * @return 
	 */
	ResultDto<String> deleteById(Long[] adIds);

	List<AdvertisementPositionDto> getPositionList();

}
