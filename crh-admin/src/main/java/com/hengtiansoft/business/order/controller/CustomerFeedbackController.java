package com.hengtiansoft.business.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.order.dto.CustomerFeedbackPageDto;
import com.hengtiansoft.business.order.servicer.CustomerFeedbackService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.wrw.enums.FeedbackStatus;

@Controller
@RequestMapping("customerFeedback")
public class CustomerFeedbackController {

    @Autowired
    private CustomerFeedbackService customerFeedbackService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView listJson() throws Exception {
        ModelAndView mav = new ModelAndView("/orderManager/customer_feedback");
        mav.addObject("status",FeedbackStatus.values());
        return mav;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public CustomerFeedbackPageDto search(@RequestBody CustomerFeedbackPageDto customerFeedbackPageDto) {
        customerFeedbackService.searchFeedback(customerFeedbackPageDto);
        return customerFeedbackPageDto;
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> save( @RequestBody String id) {
        return ResultDtoFactory.toAck(customerFeedbackService.changeStatus(Long.valueOf(id)));
    }
}
