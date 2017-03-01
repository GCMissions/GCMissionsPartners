package com.hengtiansoft.business.shopStock.regionalPlatform.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

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

import com.hengtiansoft.business.finance.controller.FinancialStatementController;
import com.hengtiansoft.business.shopStock.regionalPlatform.dto.TerminalDetailDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.dto.TerminalStockSearchDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.service.TerminalStockService;
import com.hengtiansoft.business.shopStock.zStock.dto.StockSearchDto;
import com.hengtiansoft.business.shopStock.zStock.service.DistributionService;
import com.hengtiansoft.wrw.enums.OrgTypeEnum;

/**
 * 
 * Class Name: ZStockController Description: 终端配送商查看库存以及详情
 * 
 * @author fengpan
 *
 */
@Controller
@RequestMapping(value = "terminalStock")
public class ZStockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FinancialStatementController.class);

    @Autowired
    private TerminalStockService terminalStockService;

    @Autowired
    private DistributionService distributionService;


    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public ModelAndView pStock(Model model) {
        ModelAndView mav = new ModelAndView("regionalPlatform/zstock_list");
        List<Integer> list = terminalStockService.getWarnZStock(OrgTypeEnum.Z.getKey());
        String orgIdStr = "";
        for (Integer orgId : list) {
            orgIdStr = orgIdStr + orgId + ",";
        }
        model.addAttribute("orgIdStr", orgIdStr);
        model.addAttribute("waringCount", list.size());
        return mav;
    }

    @RequestMapping(value = { "/list" }, method = RequestMethod.POST)
    @ResponseBody
    public TerminalStockSearchDto getPStock(@RequestBody TerminalStockSearchDto infoDto) {
        terminalStockService.searchDto(infoDto);
        return infoDto;
    }

    @RequestMapping(value = { "/detail/{code}" }, method = RequestMethod.GET)
    public ModelAndView searchOne(@PathVariable(value = "code") String orgCode) {
        TerminalDetailDto dto = terminalStockService.terminalDetail(orgCode);
        ModelAndView mav = new ModelAndView("regionalPlatform/zstock_detail");
        mav.addObject("terminalDetail", dto);
        mav.addObject("orgCode", orgCode);
        return mav;
    }

    @RequestMapping(value = { "/zStocklist" }, method = RequestMethod.POST)
    @ResponseBody
    public StockSearchDto getZStock(@RequestBody StockSearchDto infoDto) {
        // StockSearchDto infoDto = new StockSearchDto();
        infoDto.setOrgType(OrgTypeEnum.Z.getKey());
        distributionService.searchStock(infoDto);
        return infoDto;
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
    public void toExcel(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        HSSFWorkbook wb = terminalStockService.toExcel();
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
