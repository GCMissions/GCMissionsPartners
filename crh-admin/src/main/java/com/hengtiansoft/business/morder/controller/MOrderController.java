package com.hengtiansoft.business.morder.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.morder.dto.MOrderSearchDto;
import com.hengtiansoft.business.morder.service.MOrderService;
import com.hengtiansoft.wrw.enums.OrderStatus;
import com.hengtiansoft.wrw.enums.ProductTypeEnum;

/**
 * 
* Class Name: MOrderControlle
* Description: 订单管理
* @author jiafengchen
*
 */
@Controller
@RequestMapping(value="morder")
public class MOrderController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MOrderController.class);
    
    @Autowired
    private MOrderService mOrderService;
    /**
     * Description :订单列表首页
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView maView = new ModelAndView("morder/morder_list");
        maView.addObject("listTypes", ProductTypeEnum.values());
        maView.addObject("listOrgs", mOrderService.findOrgs());
        maView.addObject("status",OrderStatus.values());
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
    public MOrderSearchDto list(@RequestBody MOrderSearchDto dto){
        mOrderService.search(dto);
        return dto;
    }
    //导出
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public void toExcle(HttpServletRequest request, HttpServletResponse response,MOrderSearchDto pageDto) 
            throws UnsupportedEncodingException {
        String excelName = "";
        HSSFWorkbook wb = null;
//            MOrderSearchDto pageDto = new MOrderSearchDto();
//            pageDto.setStatus(status);
            
            wb = mOrderService.toExcle(pageDto);
            excelName = "订单列表.xls";
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" 
            + new String(excelName.getBytes("GB2312"),"ISO8859-1"));
            ServletOutputStream outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            LOGGER.error("error", e);
        }
    }
}
