package com.hengtiansoft.business.barcode.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.barcode.dto.BarcodeCycleSearchDto;
import com.hengtiansoft.business.barcode.service.BarcodeCycleService;
import com.hengtiansoft.business.barcode.service.BasicCodeService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.entity.SBasicCodeEntity;

/**
 * Class Name: BarcodeCycleController
 * Description: 二维码生命周期的控制器
 * 
 * @author hongqi
 */
@Controller
@RequestMapping(value = "/barcodeCycle")
public class BarcodeCycleController {
    private static final Logger            log = LoggerFactory.getLogger(BarcodeCycleController.class);

    @Autowired
    private BarcodeCycleService            cycleService;

    @Autowired
    private BasicCodeService               basicCodeService;

    @Autowired
    LocalContainerEntityManagerFactoryBean connectionFactory;

    /**
     * Description: 搜索列表
     *
     * @param dto
     *            传递的参数
     * @return 返回搜索结果
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public BarcodeCycleSearchDto searchCycle(@RequestBody BarcodeCycleSearchDto dto) {
        dto.setPrefixCode(dto.getPrefixCode().trim());
        String search = dto.getPrefixCode();

        if (search.indexOf("http") >= 0) {
            cycleService.searchByUrl(dto);
        } else {
            cycleService.searchByPrefixCode(dto);
        }
        return dto;
    }

    /**
     * Description: 显示二维码查询页
     *
     * @return 返回二维码查询页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("/barcodeCycle/list");
    }

    /**
     * Description: 导入excel
     *
     * @param file
     *            excel文件
     * @return 返回导入结果
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<List<SBasicCodeEntity>> upload(@RequestParam("excel") MultipartFile file) {
        if (WRWUtil.isEmpty(file.getName()) || file.getSize() == 0) {
            return ResultDtoFactory.toNack("文件错误 ");
        }
        if (!(".xlsx".equals(getFileType(file.getOriginalFilename())) || ".xls".equals(getFileType(file.getOriginalFilename())))) {
            return ResultDtoFactory.toNack("文件格式不正确 ");
        }

        ResultDto<List<SBasicCodeEntity>> res = null;
        try {
            res = basicCodeService.importBarcode(file.getInputStream());
        } catch (IOException e) {

            log.error("msg", e);
            return ResultDtoFactory.toNack("文件读取失败");
        }
        return res;
    }

    /**
     * Description: 获取文件后缀
     *
     * @param fileName
     *            文件名
     * @return
     */
    private String getFileType(String fileName) {
        int index = fileName.lastIndexOf(".");
        return fileName.substring(index);
    }

}
