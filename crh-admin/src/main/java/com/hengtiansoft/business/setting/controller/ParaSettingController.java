package com.hengtiansoft.business.setting.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.setting.dto.ParaSettingDto;
import com.hengtiansoft.business.setting.dto.SBasicParaDto;
import com.hengtiansoft.business.setting.service.MessageModelService;
import com.hengtiansoft.business.setting.service.ParaSettingService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.wrw.enums.AppAdSkipFlagEnum;

/**
 * @author jiekaihu
 *
 */
@Controller
@RequestMapping(value = "/paraSetting")
public class ParaSettingController {
	
	@Autowired
	ParaSettingService psService;
	
	@Autowired
	MessageModelService mmService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("setting/setting_system");
		ParaSettingDto psDto = psService.getParaSetting();
		psService.setAppParam(psDto);
		mav.addObject("paraList", psDto);
		mav.addObject("appAdSkipFlagEnum", AppAdSkipFlagEnum.values());
		return mav;
	}

	/**
	 * Description:编辑保存
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<String> edit(@RequestBody ParaSettingDto dtoList) {
		return psService.save(dtoList);
	}
	
	@RequestMapping(value = "/findByParaName", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<SBasicParaDto> getPara(@RequestBody String paraName) {

		SBasicParaDto dto = mmService.getBasicParaByParaName(paraName);
		return ResultDtoFactory.toAck("获取参数值成功",dto);
	}
	
	 /**
     * 
     * Description: upload a apk
     *
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/uploadApp", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> uploadApp(@RequestBody MultipartFile appFile,@RequestParam String version) {

        return psService.uploadApp(appFile,version);
    }
}
