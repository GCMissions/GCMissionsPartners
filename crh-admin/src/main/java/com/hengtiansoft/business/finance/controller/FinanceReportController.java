/*
 * Project Name: wrw-admin
 * File Name: FinanceReportController.java
 * Class Name: FinanceReportController
 * Copyright 2014 Hengtian Software Inc
 * Licensed under the Hengtiansoft
 * http://www.hengtiansoft.com
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.finance.controller;

import java.io.IOException;
import java.util.Date;

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

import com.hengtiansoft.business.finance.dto.ReportDetailSearchDto;
import com.hengtiansoft.business.finance.dto.ReportSearchDto;
import com.hengtiansoft.business.finance.service.FinanceReportService;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.wrw.enums.OrgTypeEnum;

/**
 * Class Name: FinanceReportController
 * Description: TODO
 * 
 * @author chengminmiao
 */
@Controller
@RequestMapping("/financeReport")
public class FinanceReportController {

    private static final Logger  LOG = LoggerFactory.getLogger(FinanceReportController.class);

    @Autowired
    private FinanceReportService financeReportService;

    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public ModelAndView list() throws Exception {
        return new ModelAndView("financeReport/financeReport_list");
    }

    @RequestMapping(value = { "/detail" }, method = RequestMethod.GET)
    public ModelAndView detail(Long orgId, String beginDate, String endDate, String orgType) throws Exception {
        ReportDetailSearchDto dto = new ReportDetailSearchDto();
        dto.setOrgId(orgId);
        dto.setOrgType(orgType);
        dto.setBeginDate(null == beginDate ? null : DateTimeUtil.parseDate(beginDate, DateTimeUtil.SIMPLE_FMT));
        dto.setEndDate(null == endDate ? null : DateTimeUtil.parseDate(endDate, DateTimeUtil.SIMPLE_FMT));
        ModelAndView mav = new ModelAndView("financeReport/financeReport_detail");
        financeReportService.getFinanceDetailReport(dto);
        mav.addObject("beginDate", beginDate);
        mav.addObject("endDate", endDate);
        mav.addObject("dto", dto);
        return mav;
    }

    /**
     * Description: 财务报表
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/getFinanceReport", method = RequestMethod.POST)
    @ResponseBody
    public ReportSearchDto getFinanceReport(@RequestBody ReportSearchDto dto) {

        if (OrgTypeEnum.P.getKey().equalsIgnoreCase(dto.getOrgType())) {
            financeReportService.getPFinanceReport(dto);

        } else if (OrgTypeEnum.Z.getKey().equalsIgnoreCase(dto.getOrgType())) {
            financeReportService.getZFinanceReport(dto);
        }
        return dto;
    }

    /**
     * Description: TODO
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/getFinanceDetailReport", method = RequestMethod.POST)
    @ResponseBody
    public ReportDetailSearchDto getFinanceDetailReport(@RequestBody ReportDetailSearchDto dto) {

        financeReportService.getFinanceDetailReport(dto);
        return dto;
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void toExcle(HttpServletRequest request, HttpServletResponse response, String orgName, String sDate, String eDate, String orgType) {

        ReportSearchDto dto = new ReportSearchDto();
        dto.setOrgName(orgName);
        dto.setBeginDate(sDate == null ? DateTimeUtil.getDateBegin("2016-01-01") : DateTimeUtil.getDateBegin(sDate));
        dto.setEndDate(eDate == null ? new Date() : DateTimeUtil.getDateBegin(eDate));
        dto.setOrgType(orgType);
        HSSFWorkbook wb = financeReportService.toExcle(dto);
        String excelName = "平台报表.xls";
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String(excelName.getBytes("GB2312"), "ISO8859-1"));
            ServletOutputStream outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            LOG.error("error", e);
        }
    }

}
