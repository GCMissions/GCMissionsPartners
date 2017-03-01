package com.hengtiansoft.business.shopStock.zStock.controller;

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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.finance.controller.FinancialStatementController;
import com.hengtiansoft.business.shopStock.zStock.dto.StockDto;
import com.hengtiansoft.business.shopStock.zStock.dto.StockRecordDto;
import com.hengtiansoft.business.shopStock.zStock.dto.StockSearchDto;
import com.hengtiansoft.business.shopStock.zStock.service.DistributionService;

@Controller
@RequestMapping("distribution")
public class DistributionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FinancialStatementController.class);

    @Autowired
    DistributionService distributionService;

    @RequestMapping(value = { "/list" }, method = RequestMethod.GET)
    public ModelAndView list(Model model) throws Exception {
        Integer waringCount = distributionService.getWarnStockCount("Z");
        model.addAttribute("waringCount", waringCount);
        return new ModelAndView("distribution/distribution_list");
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public StockSearchDto search(@RequestBody StockSearchDto infoDto, Model model) {
        distributionService.searchStock(infoDto);
        return infoDto;
    }

    /**
     * 详情
     * 
     * @param id
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(ModelMap model, @PathVariable(value = "id") Long id) {
        StockDto stockDto = distributionService.findStock(id);
        model.addAttribute("stockDto", stockDto);
        model.addAttribute("stockId", id);
        return "distribution/distribution_detail";
    }

    @RequestMapping(value = { "/recordlist" }, method = RequestMethod.POST)
    @ResponseBody
    public StockRecordDto getStockRecord(@RequestBody StockRecordDto dto) {
        return distributionService.findStockRecord(dto);
    }

    /**
     * 
     * Description: 库存导出
     *
     * @param request
     * @param response
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void toExcel(HttpServletRequest request, HttpServletResponse response, String isWarning, String orgType)
            throws UnsupportedEncodingException {
        StockSearchDto searchDto = new StockSearchDto();
        searchDto.setIsWarning(isWarning);
        searchDto.setOrgType(orgType);
        HSSFWorkbook wb = distributionService.toExcel(searchDto);
        String excelName = "安全库存预警.xls";
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-disposition", "attachment;filename="
                    + new String(excelName.getBytes("GB2312"), "ISO8859-1"));
            ServletOutputStream outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            LOGGER.error("error", e);
        }
    }

}
