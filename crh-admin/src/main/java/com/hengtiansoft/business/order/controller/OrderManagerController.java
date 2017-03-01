package com.hengtiansoft.business.order.controller;

import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.order.dto.CouponSearchDto;
import com.hengtiansoft.business.order.dto.OrderDetailSearchDto;
import com.hengtiansoft.business.order.dto.OrderManagerExportSearchDto;
import com.hengtiansoft.business.order.dto.OrderManagerListSearchDto;
import com.hengtiansoft.business.order.dto.OrderReturnDto;
import com.hengtiansoft.business.order.servicer.OrderManagerService;
import com.hengtiansoft.business.order.servicer.OrderReturnService;
import com.hengtiansoft.common.constant.CommonConstants;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.exception.BizServiceException;
import com.hengtiansoft.wrw.enums.OrderStatus;
import com.hengtiansoft.wrw.enums.ProductTypeEnum;

/**
 * Class Name: OrderManagerController Description: 订单管理 controller
 * 
 * @author kangruan
 */
@Controller
@RequestMapping("/order")
public class OrderManagerController {

    private Logger logger = LoggerFactory.getLogger(OrderManagerController.class);

    @Autowired
    private OrderManagerService orderManagerService;

    @Autowired
    private OrderReturnService orderReturnService;

    @RequestMapping(value = "/view/{orderId}/{type}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("orderId") String orderId, @PathVariable("type") String type) {
        ModelAndView mv = new ModelAndView("orderManager/order_view");
        OrderReturnDto dto = orderReturnService.getOrderDetail(orderId);
        dto.setReturnType(type);
        mv.addObject("order", dto);
        mv.addObject("remarks", orderReturnService.getRemarks(orderId));
        return mv;
    }
    
    @RequestMapping(value = "/view/{orderId}/{type}/{memberId}", method = RequestMethod.GET)
    public ModelAndView viewNew(@PathVariable("orderId") String orderId, @PathVariable("type") String type, @PathVariable Long memberId) {
        ModelAndView mv = new ModelAndView("orderManager/order_view");
        OrderReturnDto dto = orderReturnService.getOrderDetail(orderId);
        dto.setReturnType(type);
        mv.addObject("order", dto);
        mv.addObject("remarks", orderReturnService.getRemarks(orderId));
        mv.addObject("memberId", memberId);
        return mv;
    }

    @RequestMapping("/findDetails")
    @ResponseBody
    public ResultDto<?> getDetails(@RequestBody OrderDetailSearchDto listDto) {
        return ResultDtoFactory.toAck("", orderManagerService.findOrderDetails(listDto));
    }

    @RequestMapping("/findCoupons")
    @ResponseBody
    public ResultDto<?> getCoupons(@RequestBody CouponSearchDto listDto) {
        return ResultDtoFactory.toAck("", orderManagerService.findCoupons(listDto));
    }

    @RequestMapping("/delCoup/{couponId}")
    @ResponseBody
    public ResultDto<?> delCoup(@PathVariable Long couponId) {
        try {
            orderManagerService.delCoupon(couponId);
            return ResultDtoFactory.toAck("删除失败");
        } catch (BizServiceException e) {
            return ResultDtoFactory.toNack(e.getMessage());
        } catch (Exception e) {
            return ResultDtoFactory.toNack("优惠券删除失败", e);
        }

    }

    /**
     * Description :订单列表首页
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView maView = new ModelAndView("orderManager/order_list");
        maView.addObject("listTypes", ProductTypeEnum.values());
        maView.addObject("listOrgs", orderManagerService.findOrgs());
        maView.addObject("status", OrderStatus.values());
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
        orderManagerService.search(dto);
        return dto;
    }

    // 导出
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public void toExcle(HttpServletRequest request, HttpServletResponse response, OrderManagerExportSearchDto pageDto)
            throws UnsupportedEncodingException {
        String excelName = "";
        HSSFWorkbook wb = null;
        // pageDto.setStatus(status);

        wb = orderManagerService.toExcle(pageDto);
        excelName = "订单列表.xls";
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-disposition", "attachment;filename="
                    + new String(excelName.getBytes("GB2312"), "ISO8859-1"));
            ServletOutputStream outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            logger.error("error", e);
        }
    }

    /**
     * 
     * Description: 获取线下补录订单模板
     *
     * @param request
     * @param response
     * @author chengchaoyin
     */
    @RequestMapping(value = "toTmlExcel", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> toTemplateExcel() {
        return ResultDtoFactory.toAck("success!",CommonConstants.getProperty(CommonConstants.line_order_makeupTemplateUrl));
    }
    
    /**
     * 
    * Description: 解析导入的excel，excel表本身不存入服务器
    * @param request
    * @param response
    * @param multipartFile
    * @return
    * @author chengchaoyin
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> importExcel(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "postExcel") MultipartFile multipartFile) {
        String fileName = multipartFile.getName();
        // 原始文件名
        String sourceName = multipartFile.getOriginalFilename(); 
        String fileType = sourceName.substring(sourceName.lastIndexOf("."));

        if (null == fileName || "".equals(fileName)) {
            return ResultDtoFactory.toNack("请传入文件名！");
        }

        if (!".xlsx".equals(fileType) && !".xls".equals(fileType)) {
            return ResultDtoFactory.toNack("文件格式错误！");
        }

        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            return ResultDtoFactory.toNack(e.toString());
        }
        return orderManagerService.importExcel(inputStream);
    }
    
}
