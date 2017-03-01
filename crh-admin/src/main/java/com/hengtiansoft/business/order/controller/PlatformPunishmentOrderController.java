package com.hengtiansoft.business.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.order.servicer.PlatformPunishmentOrderService;
import com.hengtiansoft.business.shopStock.zStock.dto.OrderRewardSearchDto;

/**
 * Class Name: PunishmentOrderController
 * 
 * Description: 平台惩罚订单
 * 
 * @author zhishenghong
 *
 */
@Controller
@RequestMapping("/punishOrder")
public class PlatformPunishmentOrderController {

    @Autowired
    private PlatformPunishmentOrderService platformPunishmentOrderService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("orderManager/p_porder");
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public OrderRewardSearchDto search(@RequestBody OrderRewardSearchDto searchDto) {
        platformPunishmentOrderService.search(searchDto);
        return searchDto;
    }

}
