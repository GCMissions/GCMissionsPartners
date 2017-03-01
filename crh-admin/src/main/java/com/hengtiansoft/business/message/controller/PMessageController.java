package com.hengtiansoft.business.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.message.dto.PMessageSaveDto;
import com.hengtiansoft.business.message.dto.PMessageSearchDto;
import com.hengtiansoft.business.message.dto.SOrgSearchDto;
import com.hengtiansoft.business.message.service.PMessageService;
import com.hengtiansoft.common.dto.ResultDto;

/**
 * Class Name : PMessageController
 * 
 * Desc : 平台站内信控制器
 * 
 * @author zhisongliu
 *
 */
@Controller
@RequestMapping(value = "/pMessage")
public class PMessageController {

    @Autowired
    private PMessageService pMessageService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("pMessage/pMessage_list");
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public PMessageSearchDto list(@RequestBody PMessageSearchDto dto) {
        pMessageService.search(dto);
        return dto;
    }

    /**
     * 获取区域平台商,和终端配送商
     * 
     */
    @RequestMapping(value = "/memberList", method = RequestMethod.POST)
    @ResponseBody
    public SOrgSearchDto memberList(@RequestBody SOrgSearchDto dto) {
        pMessageService.findMember(dto);
        return dto;
    }

    /**
     * new message
     * 
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {

        return new ModelAndView("pMessage/pMessage_add");
    }

    /**
     * 保存
     * 
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> save(@RequestBody PMessageSaveDto dto) {
        return pMessageService.save(dto);
    }

    /**
     * 查看某条消息
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("pMessage/pMessage_add");
        mav.addObject("msgDto", pMessageService.findById(id));
        mav.addObject("isReview", 1);
        return mav;
    }

}
