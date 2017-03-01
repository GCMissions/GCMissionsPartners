package com.hengtiansoft.business.activity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.business.activity.service.ValidateService;
import com.hengtiansoft.business.order.dto.OrderManagerListSearchDto;
import com.hengtiansoft.business.order.servicer.OrderManagerService;
import com.hengtiansoft.wrw.enums.OrderStatus;
import com.hengtiansoft.wrw.enums.ProductTypeEnum;

@Controller
@RequestMapping("/orgOrder")
public class ValidateController {
    
    @Autowired
    private OrderManagerService orderManagerService;
    @Autowired
    private ValidateService validateService;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("listTypes", ProductTypeEnum.values());
        model.addAttribute("listOrgs", orderManagerService.findOrgs());
        model.addAttribute("status", OrderStatus.values());
        return "orderManager/validate_list";
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public OrderManagerListSearchDto list(@RequestBody OrderManagerListSearchDto dto) {
        validateService.search(dto);
        return dto;
    }
}
