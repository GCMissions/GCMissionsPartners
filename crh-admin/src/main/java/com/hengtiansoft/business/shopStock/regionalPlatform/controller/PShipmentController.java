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

import com.hengtiansoft.business.provider.dto.SearchOrgDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.GoodsStockSearchDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.dto.PShipmentDetailPageDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.dto.PShipmentDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.dto.PShipmentSearchDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.service.PShipmentService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.poi.PoiUtil;
import com.hengtiansoft.common.poi.bean.SimpleExcelBean;
import com.hengtiansoft.wrw.enums.ShipmentState;

@Controller
@RequestMapping("pshipment")
public class PShipmentController {
    private static final Logger log = LoggerFactory.getLogger(PShipmentController.class);

    @Autowired
    private PShipmentService    pShipmentService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("regionalPlatform/pshipment_list");

        ShipmentState[] status = { ShipmentState.DELIVERY, ShipmentState.SHIPMENT };
        mav.addObject("status", status);
        mav.addObject("count", pShipmentService.getShipmentCount());
        return mav;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public PShipmentSearchDto list(@RequestBody PShipmentSearchDto searchDto) {
        pShipmentService.searchDto(searchDto);
        return searchDto;
    }

    @RequestMapping(value = "/creatShipment", method = RequestMethod.GET)
    public ModelAndView creatShipment() {
        return new ModelAndView("regionalPlatform/creatShipment");
    }

    @RequestMapping(value = "/matchingOrg", method = RequestMethod.POST)
    @ResponseBody
    public SearchOrgDto getZorg() {
        return pShipmentService.getZorg();
    }

    @RequestMapping(value = { "/detail/{id}" }, method = RequestMethod.GET)
    public ModelAndView searchOne(@PathVariable(value = "id") String orderId) {
        PShipmentDto dto = pShipmentService.findOrder(orderId);
        ModelAndView mav = new ModelAndView("regionalPlatform/pshipment_detail");
        mav.addObject("order", dto);
        mav.addObject("statusId", ShipmentState.getKey(dto.getStatus()));
        return mav;
    }

    /**
     * Description: 选择商品
     *
     * @param searchDto
     * @return
     */
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @ResponseBody
    public GoodsStockSearchDto select(@RequestBody GoodsStockSearchDto searchDto) {
        return pShipmentService.searchProductStock(searchDto);
    }

    @RequestMapping(value = "/detailList", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<PShipmentDetailPageDto> detailList(@RequestBody PShipmentSearchDto searchDto) {
        PShipmentDetailPageDto detailDto = pShipmentService.findDetailOrder(searchDto.getOrderId());
        return ResultDtoFactory.toAck("", detailDto);
    }

    @RequestMapping(value = "/shipment", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> save(@RequestBody String orderId) {
        return ResultDtoFactory.toAck(pShipmentService.changeStatus(orderId));
    }

    /**
     * Description: 导出发货单
     *
     * @param request
     * @param response
     * @param orderId
     */

    @RequestMapping(value = "/toExcelShipment", method = RequestMethod.GET)
    public void ExcelShipment(HttpServletRequest request, HttpServletResponse response, String orderId) {
        List<List<String>> content = pShipmentService.findAllOrder(orderId);
        SimpleExcelBean bean = new SimpleExcelBean();
        bean.setTitle("P给Z出库详情表");
        String[] colNames = { "序号", "时间", "终端配送商名称", "商品名称", "规格", "产品编号", "应配数量", "实配数量" };
        bean.setColNames(colNames);
        bean.setContent(content);
        try {
            PoiUtil.toExcel(bean, response, request);
        } catch (IOException e) {
            log.error("msg", e);
        }

    }
}
