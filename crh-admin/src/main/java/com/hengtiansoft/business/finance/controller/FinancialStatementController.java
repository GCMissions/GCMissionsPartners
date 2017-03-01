package com.hengtiansoft.business.finance.controller;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.finance.dto.SOrderBalancePageDto;
import com.hengtiansoft.business.finance.service.SOrderBalanceService;
import com.hengtiansoft.wrw.enums.BalanceTypeEnum;

/**
 * Class Name: FinancialStatementController
 * Description: 财务报表
 * 
 * @author chenghongtu
 */
@Controller
@RequestMapping("financialStatement")
public class FinancialStatementController {

    private static final Logger  log = LoggerFactory.getLogger(FinancialStatementController.class);

    @Autowired
    private SOrderBalanceService sOrderBalanceService;

    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public ModelAndView list() throws Exception {
        return new ModelAndView("financialManage/financialStatement_list");
    }

    /**
     * Description: 财务报表
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    @ResponseBody
    public SOrderBalancePageDto findAll(@RequestBody SOrderBalancePageDto dto) {
        return sOrderBalanceService.findAll(dto);

    }

    @RequestMapping(value = "/detail/{orderId}", method = RequestMethod.GET)
    public String getOrderDetail(Model model, @PathVariable String orderId) {
        model.addAttribute("info", sOrderBalanceService.getOrderDetail(orderId));
        return "/financialManage/financialStatement_detail";
    }

    /**
     * Description: 平台财务报表导出
     *
     * @param request
     * @param response
     * @param dto
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void toExcle(HttpServletRequest request, HttpServletResponse response, String orderId, String sDate, String eDate, String balanceType)
            throws UnsupportedEncodingException {
        SOrderBalancePageDto dto = new SOrderBalancePageDto();
        dto.setOrderId(orderId);
        dto.setsDate(sDate);
        dto.seteDate(eDate);
        dto.setBalanceType(balanceType);
        HSSFWorkbook wb = sOrderBalanceService.toExcle(dto);
        String excelName = "";
        if (dto.getBalanceType().equals(BalanceTypeEnum.normal.getKey())) {
            excelName = "平台报表.xls";
        } else {
            excelName = "平台邮寄.xls";
        }
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String(excelName.getBytes("GB2312"), "ISO8859-1"));
            ServletOutputStream outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            log.error("error", e);
        }
    }

}
