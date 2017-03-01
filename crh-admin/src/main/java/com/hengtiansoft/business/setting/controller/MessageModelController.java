package com.hengtiansoft.business.setting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.setting.dto.MessageModelDto;
import com.hengtiansoft.business.setting.service.MessageModelService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;

/**
 * @author jiekaihu
 *
 */
@Controller
@RequestMapping(value = "/messageModel")
public class MessageModelController {

	@Autowired
	MessageModelService mmService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView sys() {
		ModelAndView mav = new ModelAndView("setting/setting_template");
		MessageModelDto mmDto = mmService.getMessageModel();
		mav.addObject("messageDto", mmDto);
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
	public ResultDto<String> edit(@RequestBody MessageModelDto dto) {

		mmService.save(dto);
		
		return ResultDtoFactory.toAck("系统消息保存成功");
	}

}
