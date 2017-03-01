package com.hengtiansoft.business.wrkd.advertise.controller;

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

import com.hengtiansoft.business.wrkd.advertise.dto.ActSearchDto;
import com.hengtiansoft.business.wrkd.advertise.dto.FeatureSearchDto;
import com.hengtiansoft.business.wrkd.advertise.dto.KdAdvertiseAddDto;
import com.hengtiansoft.business.wrkd.advertise.dto.KdAdvertiseSearchDto;
import com.hengtiansoft.business.wrkd.advertise.dto.KdEmptyDto;
import com.hengtiansoft.business.wrkd.advertise.service.KdAdvertiseService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.wrw.enums.ActTypeEnum;
import com.hengtiansoft.wrw.enums.KdAdvertiseSkipTypeEnum;
import com.hengtiansoft.wrw.enums.KdAdvertiseTypeEnum;
import com.hengtiansoft.wrw.enums.StatusEnum;

@Controller
@RequestMapping(value = "/kdAdvertise")
public class KdAdvertiseController{
    
    private static final Logger log = LoggerFactory.getLogger(KdAdvertiseController.class); 
    
    @Autowired
    private KdAdvertiseService kdAdvertiseService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("advertiseType", KdAdvertiseTypeEnum.values());
        return "wrkd/kdAdvertise/kd_advertise_list";
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public KdAdvertiseSearchDto findList(@RequestBody KdAdvertiseSearchDto dto) {
        kdAdvertiseService.searchAdvertise(dto);
        return dto;
    }
    
    // 导出
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public void toExcle(HttpServletRequest request, HttpServletResponse response, KdAdvertiseSearchDto dto)
            throws UnsupportedEncodingException {
        String excelName = "";
        HSSFWorkbook wb = null;
        // pageDto.setStatus(status);

        wb = kdAdvertiseService.toExcle(dto);
        excelName = "广告列表.xls";
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-disposition", "attachment;filename="
                    + new String(excelName.getBytes("GB2312"), "ISO8859-1"));
            ServletOutputStream outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            log.error("error", e);
        }
    }
    
    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> addAdvertise(@RequestBody KdAdvertiseAddDto dto) {
        return kdAdvertiseService.addOrUpdateAdvertise(dto);
    }
    
    @RequestMapping(value = "/featureList", method = RequestMethod.POST)
    @ResponseBody
    public FeatureSearchDto findFeatureList(@RequestBody FeatureSearchDto dto) {
        kdAdvertiseService.searchFeatureList(dto);
        return dto;
    }
    
    @RequestMapping(value = "/actList", method = RequestMethod.POST)
    @ResponseBody
    public ActSearchDto findActList(@RequestBody ActSearchDto dto) {
        kdAdvertiseService.searchActList(dto);
        return dto;
    }
    
    @RequestMapping(value = "/advertiseList/{advertiseId}/{adType}", method = RequestMethod.GET)
    public String getList(Model model, @PathVariable Long advertiseId, @PathVariable String adType) {
        model.addAttribute("advertiseList", kdAdvertiseService.getAdvertiseList(advertiseId, adType).get(0));
        return "wrkd/kdAdvertise/kd_advertise_view";
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> deleteAdvertise(@RequestBody KdAdvertiseAddDto dto) {
        return kdAdvertiseService.deleteAdvertise(dto.getAdvertiseId());
    }
    
    @RequestMapping(value = "/adListOne", method = RequestMethod.GET)
    public String getAdvertiseList(Model model) {
        model.addAttribute("advertiseOneList", kdAdvertiseService.getAdvertiseOneList());
        model.addAttribute("skipType", KdAdvertiseSkipTypeEnum.values());
        model.addAttribute("tags", kdAdvertiseService.findAllTag());
        model.addAttribute("actType", ActTypeEnum.values());
        model.addAttribute("firstSwitchStatus", kdAdvertiseService.getSwitchStatus(KdAdvertiseTypeEnum.TYPE_ONE.getCode(), StatusEnum.NORMAL.getCode()));
        model.addAttribute("secondSwitchStatus", kdAdvertiseService.getSwitchStatus(KdAdvertiseTypeEnum.TYPE_TWO.getCode(), StatusEnum.NORMAL.getCode()));
        return "wrkd/kdAdvertise/kd_advertise_add";
    }
    
    @RequestMapping(value = "/adListTwo", method = RequestMethod.POST)
    @ResponseBody
    public KdEmptyDto getAdvertiseTwoList(@RequestBody KdEmptyDto dto) {
        kdAdvertiseService.getAdvertiseTwoList(dto);
        return dto;
    }
    
    @RequestMapping(value = "/switchStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> switchStatus(String openType, String closeType, String index) {
        return kdAdvertiseService.switchStatus(openType, closeType, index);
    }
}
