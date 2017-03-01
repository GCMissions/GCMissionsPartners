package com.hengtiansoft.business.shopMall.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.shopMall.dto.AppMessageDto;
import com.hengtiansoft.business.shopMall.dto.AppMessagePageDto;
import com.hengtiansoft.business.shopMall.service.AppMessageService;
import com.hengtiansoft.common.dto.ResultDto;

/**
 * 
 * @author jiekaihu
 *
 */
@Controller
@RequestMapping("/appMessage")
public class AppMessageController {

	@Autowired
	private AppMessageService messageService;

	/**
	 * Description:首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index() {
	    return new ModelAndView("appMessage/appMessage_list");
	}

	/**
	 * 
	 * Description:搜索
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public AppMessagePageDto searchAttributes(@RequestBody AppMessagePageDto dto) {
		messageService.search(dto);
		return dto;
	}

	/**
	 * Description:新增页面
	 * 
	 * 
	 */
	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public ModelAndView toAdd() {
	    return new ModelAndView("appMessage/appMessage_add");
	}

	/**
	 * Description:保存
	 * 
	 * @param dto
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResultDto<String> add(@RequestBody AppMessageDto dto) throws UnsupportedEncodingException {
		return messageService.save(dto);
	}

	/**
	 * Description:查看详情页面
	 * 
	 * 
	 */
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public ModelAndView toView(@PathVariable(value = "id") Long messageId) {
		ModelAndView mav = new ModelAndView("appMessage/appMessage_add");
		Boolean review = true;
		AppMessageDto dto = messageService.view(messageId);
		mav.addObject("detail", dto);
		mav.addObject("review",review);
		return mav;
	}
}
