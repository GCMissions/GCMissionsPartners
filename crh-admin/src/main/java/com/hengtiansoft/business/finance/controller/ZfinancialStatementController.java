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

import com.hengtiansoft.business.finance.dto.SOrderBalanceDto;
import com.hengtiansoft.business.finance.dto.SOrderBalancePageDto;
import com.hengtiansoft.business.finance.service.ZorderBalanceService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.wrw.enums.BalanceTypeEnum;

/**
 * Class Name: FinancialStatementController
 * Description: 财务报表
 * 
 * @author chenghongtu
 */
@Controller
@RequestMapping("zfinancialStatement")
public class ZfinancialStatementController {

    private static final Logger  log = LoggerFactory.getLogger(ZfinancialStatementController.class);

    @Autowired
    private ZorderBalanceService zorderBalanceService;

    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public ModelAndView list() throws Exception {
        return new ModelAndView("zfinancialManage/zfinancialStatement_list");
    }

    /**
     * Description: 财务报表
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    @ResponseBody
    public SOrderBalancePageDto findAll(@RequestBody SOrderBalancePageDto dto) {
        return zorderBalanceService.findAll(dto);

    }

    @RequestMapping(value = "/detail/{orderId}", method = RequestMethod.GET)
    public String getOrderDetail(Model model, @PathVariable String orderId) {
        model.addAttribute("info", zorderBalanceService.getOrderDetail(orderId));
        return "/zfinancialManage/zfinancialStatement_detail";
    }

    /**
     * Description: MPOS财务表的单条详情接口。
     *
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/detail/mpos/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<SOrderBalanceDto> getOrderDetailForMpos(@PathVariable String orderId) {
        return ResultDtoFactory.toAck(null, zorderBalanceService.getOrderDetail(orderId));
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
    public void toExcle(HttpServletRequest request, HttpServletResponse response, String orderId, String sDate, String eDate)
            throws UnsupportedEncodingException {
        SOrderBalancePageDto dto = new SOrderBalancePageDto();
        dto.setOrderId(orderId);
        dto.setsDate(sDate);
        dto.seteDate(eDate);
        dto.setBalanceType(BalanceTypeEnum.normal.getKey());
        HSSFWorkbook wb = zorderBalanceService.toExcle(dto);
        String excelName = "终端配送商报表.xls";
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
