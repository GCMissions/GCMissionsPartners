package com.hengtiansoft.business.marketing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.marketing.dto.FreePostageDto;
import com.hengtiansoft.business.marketing.dto.FreePostageListDto;
import com.hengtiansoft.business.marketing.dto.FreightConfigListDto;
import com.hengtiansoft.business.marketing.service.FreightConfigService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.wrw.enums.SFreightConfigType;
import com.wordnik.swagger.annotations.Api;

/**
 * Class Name: FreightConfigController
 * Description: 配送费管理
 * 
 * @author chengminmiao
 */
@Api(value = "FreightConfigController", description = "配送费管理")
@Controller
@RequestMapping("/freightConfig")
public class FreightConfigController {

    @Autowired
    private FreightConfigService freightConfigService;

    /**
     * Description: 运费freight
     *
     * @return
     */
    @RequestMapping(value = "/freightIndex", method = RequestMethod.GET)
    public ModelAndView freightList() {
        ModelAndView mav = new ModelAndView("freightConfig/freightConfig");
        mav.addObject("config", freightConfigService.configList(SFreightConfigType.FREIGHT.getKey()));
        return mav;
    }

    /**
     * Description: 邮寄费postage
     *
     * @return
     */
    @RequestMapping(value = "/postageIndex", method = RequestMethod.GET)
    public ModelAndView postageList() {
        ModelAndView mav = new ModelAndView("freightConfig/postageConfig");
        mav.addObject("config", freightConfigService.configList(SFreightConfigType.POSTAGE.getKey()));
        return mav;
    }

    @RequestMapping(value = "/freightSave", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> freightSave(@RequestBody FreightConfigListDto dto) {
        return freightConfigService.configSave(dto, SFreightConfigType.FREIGHT.getKey());
    }

    @RequestMapping(value = "/postageSave", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> postageSave(@RequestBody FreightConfigListDto dto) {
        return freightConfigService.configSave(dto, SFreightConfigType.POSTAGE.getKey());
    }

    /**
     * Description: 免邮free
     *
     * @return
     */
    @RequestMapping(value = "/freeIndex", method = RequestMethod.GET)
    public ModelAndView freeList() {
        ModelAndView mav = new ModelAndView("freightConfig/freeConfig");
        mav.addObject("cate", freightConfigService.findCate());
        mav.addObject("brand", freightConfigService.findBrand());
        return mav;
    }

    /**
     * Description: 商品免邮信息查询
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/freeList", method = RequestMethod.POST)
    @ResponseBody
    public FreePostageListDto freeList(@RequestBody FreePostageListDto dto) {
        freightConfigService.freeList(dto);
        return dto;
    }

    /**
     * Description: 商品免邮信息保存
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/saveFree", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> saveFree(@RequestBody FreePostageDto dto) {
        return freightConfigService.saveFree(dto);

    }

}
