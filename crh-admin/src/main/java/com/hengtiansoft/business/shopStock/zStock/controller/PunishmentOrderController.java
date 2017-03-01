package com.hengtiansoft.business.shopStock.zStock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.shopStock.zStock.dto.OrderRewardSearchDto;
import com.hengtiansoft.business.shopStock.zStock.service.PunishmentOrderService;

/**
 * Class Name: PunishmentOrderController
 * 
 * Description: 惩罚订单
 * 
 * @author zhishenghong
 *
 */
@Controller
@RequestMapping("/porder")
public class PunishmentOrderController {

    @Autowired
    private PunishmentOrderService pOrderService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("orderManager/porder");
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public OrderRewardSearchDto search(@RequestBody OrderRewardSearchDto searchDto) {
        pOrderService.search(searchDto);
        return searchDto;
    }

}
