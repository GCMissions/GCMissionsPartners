package com.hengtiansoft.business.wrkd.order.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

import com.hengtiansoft.business.order.dto.OrderDetailSearchDto;
import com.hengtiansoft.business.order.dto.OrderManagerListSearchDto;
import com.hengtiansoft.business.order.dto.OrderReturnDto;
import com.hengtiansoft.business.wrkd.order.dto.KdOrderManagerExportSearchDto;
import com.hengtiansoft.business.wrkd.order.dto.KdOrderReturnDto;
import com.hengtiansoft.business.wrkd.order.service.KdOrderManagerService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.wrw.entity.KdOrderMainEntity;
import com.hengtiansoft.wrw.enums.KdOrderStatusEnum;
import com.hengtiansoft.wrw.enums.KdOrderTypeEnum;

/**
 * Class Name: OrderManagerController Description: 订单管理 controller
 * 
 * @author kangruan
 */
@Controller
@RequestMapping("/kdOrder")
public class KdOrderManagerController {

    private Logger log = LoggerFactory.getLogger(KdOrderManagerController.class);

    @Autowired
    private KdOrderManagerService kdOrderManagerService;

    @RequestMapping(value = "/view/{orderId}/{type}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("orderId") String orderId, @PathVariable("type") String type) {
        ModelAndView mv = new ModelAndView("wrkd/kdOrderManager/order_view");
        KdOrderReturnDto dto = kdOrderManagerService.getOrderDetail(orderId);
        dto.setReturnType(type);
        mv.addObject("order", dto);
        KdOrderMainEntity kdOrderMain = kdOrderManagerService.findEntityByOrderId(orderId);
        mv.addObject("memberRemark", kdOrderMain == null ? "" : kdOrderMain.getComment());
        return mv;
    }
    
    @RequestMapping(value = "/view/{orderId}/{type}/{memberId}", method = RequestMethod.GET)
    public ModelAndView viewNew(@PathVariable("orderId") String orderId, @PathVariable("type") String type, @PathVariable Long memberId) {
        ModelAndView mv = new ModelAndView("wrkd/kdOrderManager/order_view");
        KdOrderReturnDto dto = kdOrderManagerService.getOrderDetail(orderId);
        dto.setReturnType(type);
        mv.addObject("order", dto);
        KdOrderMainEntity kdOrderMain = kdOrderManagerService.findEntityByOrderId(orderId);
        mv.addObject("memberRemark", kdOrderMain == null ? "" : kdOrderMain.getComment());
        mv.addObject("memberId", memberId);
        return mv;
    }

    @RequestMapping("/findDetails")
    @ResponseBody
    public ResultDto<?> getDetails(@RequestBody OrderDetailSearchDto listDto) {
        return ResultDtoFactory.toAck("", kdOrderManagerService.findOrderDetails(listDto));
    }

    /**
     * Description :订单列表首页
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView maView = new ModelAndView("wrkd/kdOrderManager/order_list");
        maView.addObject("listTypes", KdOrderTypeEnum.values());
//        OrderStatus[] status = {OrderStatus.WAIT_PAY,OrderStatus.WATI_RATE,OrderStatus.WAIT_START};
        maView.addObject("status", KdOrderStatusEnum.values());
        return maView;
    }

    /**
     * Description :订单首页数据
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public OrderManagerListSearchDto list(@RequestBody OrderManagerListSearchDto dto) {
        kdOrderManagerService.search(dto);
        return dto;
    }

    // 导出
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public void toExcle(HttpServletRequest request, HttpServletResponse response, KdOrderManagerExportSearchDto pageDto)
            throws UnsupportedEncodingException {
        String excelName = "";
        HSSFWorkbook wb = null;
        // pageDto.setStatus(status);

        wb = kdOrderManagerService.toExcle(pageDto);
        excelName = "订单列表.xls";
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-disposition", "attachment;filename="
                    + new String(excelName.getBytes("GB2312"), "ISO8859-1"));
            ServletOutputStream outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            log.error("error", e);
        }
    }
    
    @RequestMapping(value = "/changeActualAmount", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> changeActualAmount(@RequestBody OrderReturnDto dto) {
        return ResultDtoFactory.toAck(kdOrderManagerService.changeActualAmount(dto));
    }

    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> cancelOrder(@RequestBody OrderReturnDto dto) {
        return ResultDtoFactory.toAck(kdOrderManagerService.cancelOrder(dto));
    }

    @RequestMapping(value = "/addRemark", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> addRemark(@RequestBody OrderReturnDto dto) {
        return ResultDtoFactory.toAck(kdOrderManagerService.addRemark(dto));
    }
    
    @RequestMapping(value = "/return", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> returnAmount(@RequestBody OrderReturnDto dto) {
        return ResultDtoFactory.toAck(kdOrderManagerService.returnAmount(dto));
    }
    
    @RequestMapping(value = "/delivery", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> delivery(String orderId, String expressInfo) {
        return kdOrderManagerService.delivery(orderId, expressInfo);
    }
}
