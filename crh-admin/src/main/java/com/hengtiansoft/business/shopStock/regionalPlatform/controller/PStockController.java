package com.hengtiansoft.business.shopStock.regionalPlatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.shopStock.service.SStockRecordService;
import com.hengtiansoft.business.shopStock.zStock.dto.StockDto;
import com.hengtiansoft.business.shopStock.zStock.dto.StockRecordDto;
import com.hengtiansoft.business.shopStock.zStock.dto.StockSearchDto;
import com.hengtiansoft.business.shopStock.zStock.service.DistributionService;

/**
 * 
* Class Name: PStockController
* Description: 区域平台商管理自身库存以及查看终端配送商库存
* @author fengpan
*
 */
@Controller
@RequestMapping(value = "regionalPlatform")
public class PStockController {

    @Autowired
    private DistributionService distributionService;

    @Autowired
    private SStockRecordService sStockRecordService;

    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public ModelAndView pStock(Model model) {
        ModelAndView mav = new ModelAndView("regionalPlatform/pstock_list");
        model.addAttribute("waringCount", distributionService.getWarnStockCount("P"));
        return mav;
    }

    @RequestMapping(value = { "/list" }, method = RequestMethod.POST)
    @ResponseBody
    public StockSearchDto getPStock(@RequestBody StockSearchDto infoDto,Model model) {
        distributionService.searchStock(infoDto);
        return infoDto;
    }

    @RequestMapping(value = { "/detail/{id}" }, method = RequestMethod.GET)
    public ModelAndView searchOne(@PathVariable(value = "id") Integer stockId) {
        StockDto dto = distributionService.findStock(Long.valueOf(stockId));
        ModelAndView mav = new ModelAndView("regionalPlatform/pstock_detail");
        mav.addObject("stock", dto);
        mav.addObject("stockId", stockId);
        return mav;
    }

    @RequestMapping(value = { "/recordlist" }, method = RequestMethod.POST)
    @ResponseBody
    public StockRecordDto getStockRecord(@RequestBody StockRecordDto dto) {
        return sStockRecordService.findByStockId(dto);
    }
}
