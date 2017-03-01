package com.hengtiansoft.business.order.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.business.order.dto.ShopGroupPageDto;
import com.hengtiansoft.business.order.servicer.ShopGroupService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.wrw.enums.ShopGroupStatus;

/**
 * 
 * Class Name: ShopGroupController Description: 团购订单Controller
 * 
 * @author kangruan
 *
 */
@Controller
@RequestMapping("/group")
public class ShopGroupController{
    private Logger log = LoggerFactory.getLogger(ShopGroupController.class);
    
    @Autowired
    private ShopGroupService shopGroupSerivce;

    // 跳转到订单页面
    @RequestMapping("/")
    public String toGroup(Model model) {
        model.addAttribute("shopGroupStatus", ShopGroupStatus.values());
        return "/orderManager/group_list";
    }

    // 获取分页订单集合信息
    @RequestMapping(value = "/list")
    @ResponseBody
    public ShopGroupPageDto findPage(@RequestBody ShopGroupPageDto pageDto) {
        shopGroupSerivce.findPage(pageDto);
        return pageDto;
    }
    
    // 处理团购订单
    @RequestMapping(value = "/deal/{groupId}")
    @ResponseBody
    public ResultDto deal(@PathVariable String groupId) {
        try {
            shopGroupSerivce.dealOrder(Long.valueOf(groupId));
            return ResultDtoFactory.toAck("处理成功");
        } catch (Exception e) {
            log.error("团购订单处理 error", e);
            return ResultDtoFactory.toNack("处理成功");
        }
    }

}
