/*
 * Project Name: wrw-admin
 * File Name: ProductController.java
 * Class Name: ProductController
 *
 * Copyright 2014 Hengtian Software Inc
 *
 * Licensed under the Hengtiansoft
 *
 * http://www.hengtiansoft.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.shopStock.platformStock.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.finance.controller.FinancialStatementController;
import com.hengtiansoft.business.message.dto.SOrgDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.OrgStockDetailDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.OrgStockSearchDto;
import com.hengtiansoft.business.shopStock.platformStock.service.OrgStockService;
import com.hengtiansoft.common.dto.ResultDto;
import com.wordnik.swagger.annotations.Api;

/**
 * Class Name: OrgStockMngController Description: 商家库存管理
 * 
 * @author xianghuang
 *
 */
@Api(value = "OrgStockMngController")
@Controller
@RequestMapping("orgStock")
public class OrgStockMngController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FinancialStatementController.class);

    @Autowired
    private OrgStockService orgStockService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("plateformStock/orgStock_list");
        List<SOrgDto> dtos = orgStockService.findAllP();
        mav.addObject("orgP", dtos);
        return mav;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public OrgStockSearchDto list(@RequestBody OrgStockSearchDto searchDto) {
        return orgStockService.searchOrgStock(searchDto);
    }

    @RequestMapping(value = { "/detail/{orgId}" }, method = RequestMethod.GET)
    public ModelAndView searchOne(@PathVariable(value = "orgId") Long orgId) {
        OrgStockDetailDto dto = orgStockService.getDetail(orgId);
        ModelAndView mav = new ModelAndView("plateformStock/orgStock_detail");
        mav.addObject("orgStockDetailDto", dto);
        return mav;
    }

    @RequestMapping(value = "/setting/{stockId}/{stockSetting}", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> setting(@PathVariable(value = "stockId") Long stockId,
            @PathVariable(value = "stockSetting") Long stockSetting) {
        return orgStockService.saveSetting(stockId, stockSetting);
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
    public void toExcel(HttpServletRequest request, HttpServletResponse response, String orgCode, String orgName,
            String orgCate, String orgState, Long orgP, Double orgPct) throws UnsupportedEncodingException {
        OrgStockSearchDto searchDto = new OrgStockSearchDto();
        searchDto.setOrgCode(orgCode);
        searchDto.setOrgName(orgName);
        searchDto.setOrgCate(orgCate);
        searchDto.setOrgState(orgState);
        searchDto.setOrgP(orgP);
        searchDto.setOrgPct(orgPct);
        HSSFWorkbook wb = orgStockService.toExcel(searchDto);
        String excelName = "商家商品库存信息.xls";
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
