/*
 * Project Name: wrw-admin
 * File Name: AdminStockController.java
 * Class Name: AdminStockController
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
package com.hengtiansoft.business.shopStock.platformStock.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.barcode.controller.BarcodeCycleController;
import com.hengtiansoft.business.shopStock.platformStock.dto.AdminSearchDto;
import com.hengtiansoft.business.shopStock.platformStock.service.AdminStockService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.poi.PoiUtil;
import com.hengtiansoft.common.poi.bean.SimpleExcelBean;
import com.hengtiansoft.common.util.WRWUtil;

/**
 * Class Name: AdminStockController Description: 平台库存管理
 * 
 * @author zhisongliu
 */
@Controller
@RequestMapping("adminStock")
public class AdminStockController {
    private static final Logger log = LoggerFactory.getLogger(BarcodeCycleController.class);

    @Autowired
    private AdminStockService   stockService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView list() {
        return new ModelAndView("plateformStock/platStock_list");
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public AdminSearchDto list(@RequestBody AdminSearchDto searchDto) {
        stockService.search(searchDto);
        return searchDto;
    }

    /**
     * Description: TODO
     *
     * @param request
     * @param response
     * @param startTime
     * @param endTime
     */
    @RequestMapping(value = "toExcel", method = RequestMethod.GET)
    public void toExcel(HttpServletRequest request, HttpServletResponse response, String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date sTime = null;
        Date eTime = null;
        if (WRWUtil.isNotEmpty(startTime)) {
            try {
                sTime = sdf.parse(startTime);
            } catch (ParseException e) {
                throw new WRWException(e.toString());
            }
        }
        if (WRWUtil.isNotEmpty(endTime)) {
            try {
                eTime = sdf.parse(endTime);
            } catch (ParseException e) {
                throw new WRWException(e.toString());
            }
        }

        List<List<String>> content = stockService.findAllOrder(sTime, eTime);
        SimpleExcelBean bean = new SimpleExcelBean();
        bean.setTitle("“1”入库详情汇总表");
        String[] colNames = { "序号", "时间", "单瓶商品编号", "单瓶商品名称", "规格", "数量", "批号", "入库人", "核查人" };
        bean.setColNames(colNames);
        bean.setContent(content);
        try {
            PoiUtil.toExcel(bean, response, request);
        } catch (IOException e) {
            log.error("msg", e);
        }

    }

    /**
     * Description: 解析导入的excel，excel表本身不存入服务器
     * TODO 为避免后期需求可能需要导入的数据进行一次用户确认再入库，所以预留的接口将excel解析的list也传给前台
     *
     * @param multipartFile
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> importExcel(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "postExcel") MultipartFile multipartFile) {

        String fileName = multipartFile.getName();
        String sourceName = multipartFile.getOriginalFilename(); // 原始文件名
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

        return stockService.importExcel(inputStream);
    }

    @RequestMapping(value = "toErrorCodeExcel", method = RequestMethod.GET)
    public void toErrorCodeExcel(HttpServletRequest request, HttpServletResponse response) {
        List<List<String>> content = stockService.getErrorCode();
        SimpleExcelBean bean = new SimpleExcelBean();
        bean.setTitle("入库失败条码表");
        String[] colNames = { "条码" };
        bean.setColNames(colNames);
        bean.setContent(content);
        try {
            PoiUtil.toExcel(bean, response, request);
        } catch (IOException e) {
            log.error("msg", e);
        }
    }
}
