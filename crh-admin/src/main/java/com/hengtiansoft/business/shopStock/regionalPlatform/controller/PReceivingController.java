package com.hengtiansoft.business.shopStock.regionalPlatform.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.shopStock.regionalPlatform.dto.PReceivingDetailPageDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.dto.PReceivingDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.dto.PReceivingSearchDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.service.PReceivingService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.poi.PoiUtil;
import com.hengtiansoft.common.poi.bean.SimpleExcelBean;
import com.hengtiansoft.wrw.enums.ShipmentState;

@Controller
@RequestMapping("preceiving")
public class PReceivingController {
    private static final Logger log= LoggerFactory.getLogger(PReceivingController.class);
    @Autowired
    private PReceivingService pReceivingService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("regionalPlatform/preceiving_list");
        ShipmentState[] status = { ShipmentState.CREATE, ShipmentState.DELIVERY };
        mav.addObject("count", pReceivingService.getReceivingCount());
        mav.addObject("status", status);
        return mav;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public PReceivingSearchDto list(@RequestBody PReceivingSearchDto searchDto) {
        pReceivingService.searchDto(searchDto);
        return searchDto;
    }

    @RequestMapping(value = { "/detail/{id}" }, method = RequestMethod.GET)
    public ModelAndView searchOne(@PathVariable(value = "id") String orderId) {
        PReceivingDto dto = pReceivingService.findOrder(orderId);
        ModelAndView mav = new ModelAndView("regionalPlatform/preceiving_detail");
        mav.addObject("order", dto);
        mav.addObject("statusId", ShipmentState.getKey(dto.getStatus()));
        return mav;
    }

    @RequestMapping(value = "/detailList", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<PReceivingDetailPageDto> detailList(@RequestBody PReceivingSearchDto searchDto) {
        PReceivingDetailPageDto detailDto = pReceivingService.findDetailOrder(searchDto.getOrderId());
        return ResultDtoFactory.toAck("", detailDto);
    }

    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> save(@RequestBody String orderId) {
        return ResultDtoFactory.toAck(pReceivingService.changeStatus(orderId));
    }

    /**
     * 
    * Description: 导出收货单
    *
    * @param request
    * @param response
    * @param orderId
     * Description: 导出发货单
     *
     * @param request
     * @param response
     * @param orderId
     */
    
    @RequestMapping(value ="/toExcelReceive",method=RequestMethod.GET)
    public void ExcelShipment(HttpServletRequest request, HttpServletResponse response,String orderId){
        List<List<String>> content =pReceivingService.findAllOrder(orderId);   
        SimpleExcelBean bean = new SimpleExcelBean();
        bean.setTitle("P入库详情表");
        String[] colNames = { "序号", "时间", "商品名称", "规格", "产品编号", "数量" };
        bean.setColNames(colNames);
        bean.setContent(content);
        try {
            PoiUtil.toExcel(bean, response, request);
        } catch (IOException e) {
            log.error("msg",e);
        }

    }
}
