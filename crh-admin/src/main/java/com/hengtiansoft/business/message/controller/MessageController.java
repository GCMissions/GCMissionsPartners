package com.hengtiansoft.business.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.message.dto.SMessagePageDto;
import com.hengtiansoft.business.message.service.SMessageService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.wrw.entity.SMessageEntity;
import com.hengtiansoft.wrw.enums.SMessageOrgReadStatusEnum;

/**
 * 
 * Class Name: PMessageController Description: 区域平台商-站内信
 * 
 * @author chenghongtu
 *
 */
@Controller
@RequestMapping("message")
public class MessageController {

    @Autowired
    private SMessageService sMessageService;

    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public ModelAndView list() throws Exception {
        ModelAndView mav = new ModelAndView("message/message_list");
        mav.addObject("readStatus", SMessageOrgReadStatusEnum.values());
        return mav;
    }
    
    @RequestMapping(value = { "/list" }, method = RequestMethod.POST)
    @ResponseBody
    public SMessagePageDto search(@RequestBody SMessagePageDto sMessagePageDto) throws Exception {
        sMessageService.search(sMessagePageDto);
        return sMessagePageDto;
    }
    
    /**
     * 
    * Description: 更新站内信状态
    *
    * @param messageId
    * @return
     */
    @RequestMapping(value="/detail/{messageId}",method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<SMessageEntity> updateOne(@PathVariable String messageId){
        return ResultDtoFactory.toAck("已更新为已读!",sMessageService.editOne(messageId));
    }
    

}
