package com.hengtiansoft.business.shopMall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.shopMall.dto.AdvertisementAddDto;
import com.hengtiansoft.business.shopMall.dto.AdvertisementDto;
import com.hengtiansoft.business.shopMall.dto.AdvertisementEditDto;
import com.hengtiansoft.business.shopMall.dto.AdvertisementPageDto;
import com.hengtiansoft.business.shopMall.dto.AdvertisementPositionDto;
import com.hengtiansoft.business.shopMall.service.AdvertisementService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.wrw.enums.AdTypeEnum;
import com.hengtiansoft.wrw.enums.AdUrlFlagEnum;
import com.wordnik.swagger.annotations.Api;

/**
 * @author jiekaihu
 */
@Api(value = "AdvertisementController")
@Controller
@RequestMapping(value = "/advertisement")
public class AdvertisementController {

	@Autowired
	private AdvertisementService adService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView mav = new ModelAndView("advertisement/ad_list");
		List<AdvertisementPositionDto> positionList = adService
				.getPositionList();
		mav.addObject("adTypeList", AdTypeEnum.values());
		mav.addObject("positionList", positionList);
		return mav;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public AdvertisementPageDto adList(@RequestBody AdvertisementPageDto dto) {

		adService.getAdvertisementList(dto);
		dto.setPositionList(adService.getPositionList());
		return dto;
	}

	/**
	 * Description: 添加广告界面
	 */
	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public ModelAndView add() {

	    return new ModelAndView("advertisement/ad_add");
	}

	/**
	 * Description:添加广告数据dto
	 */
	@RequestMapping(value = "/addData", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<AdvertisementAddDto> addData() {
		AdvertisementAddDto addDto = new AdvertisementAddDto();
		addDto.setPositionList(adService.getPositionList());
		return ResultDtoFactory.toAck("添加广告数据dto", addDto);
	}

	/**
	 * Description:添加单个广告
	 * 
	 * @param dto
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<String> addAd(@RequestBody AdvertisementDto dto) {

		return adService.saveAdvertisement(dto);
	}

	/**
	 * Description: 删除单个广告
	 * 
	 * @param adId
	 * @return
	 */
	@RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
	@ResponseBody
	public ResultDto<String> deleteById(
			@PathVariable(value = "ids") Long[] adIds) {

		return adService.deleteById(adIds);

	}

	/**
	 * Description:进入编辑界面
	 */
	@RequestMapping(value = "/editPage/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable(value = "id") Long adId) {

		ModelAndView mav = new ModelAndView("advertisement/ad_edit");
		mav.addObject("urlFlags",AdUrlFlagEnum.values());
		mav.addObject("adDto", adService.getAdvertisement(adId));
		return mav;
	}

	/**
	 * Description:编辑广告数据dto
	 * 
	 * @param adId
	 * @return
	 */
	@RequestMapping(value = "/editData/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<AdvertisementEditDto> editData(
			@PathVariable(value = "id") Long adId) {
		AdvertisementEditDto editDto = new AdvertisementEditDto();

		editDto.setAdDto(adService.getAdvertisement(adId));

		editDto.setPositionList(adService.getPositionList());

		return ResultDtoFactory.toAck("编辑广告数据dto", editDto);

	}

	/**
	 * Description:编辑保存
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<String> editAd(@RequestBody AdvertisementDto dto) {

		return adService.editAdvertisement(dto);
	}

}
