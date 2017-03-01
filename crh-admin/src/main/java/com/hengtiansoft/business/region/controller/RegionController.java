package com.hengtiansoft.business.region.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.region.dto.ProvAndCityDto;
import com.hengtiansoft.business.region.dto.ProvDto;
import com.hengtiansoft.business.region.dto.RegionDto;
import com.hengtiansoft.business.region.dto.RegionOpenDto;
import com.hengtiansoft.business.region.service.RegionService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.wrw.enums.RegionLevelType;

@Controller
@RequestMapping(value = "/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    /**
     * Description: 查询所有的省
     *
     * @return
     */
    @RequestMapping(value = "/findAllProvince", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<List<RegionDto>> findAllProvince() {
        return regionService.findByLevelType(RegionLevelType.PROVINCE.getKey());
    }

    /**
     * Description: 查询所有的城市
     *
     * @return
     */
    @RequestMapping(value = "/findAllCity", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<List<RegionDto>> findAllCity() {
        return regionService.findByLevelType(RegionLevelType.CITY.getKey());
    }

    /**
     * Description: 查询省下面的所有市
     *
     * @return
     */
    @RequestMapping(value = "/findCityByProv/{parentId}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<List<RegionDto>> findAllProvince(@PathVariable(value = "parentId") Integer parentId) {
        return regionService.findByParentId(parentId);
    }

    /**
     * Description: 查询城市所属省
     *
     * @return
     */
    @RequestMapping(value = "/findProvByCity/{regionId}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<RegionDto> findProvByCity(@PathVariable(value = "regionId") Integer regionId) {
        return regionService.findProvByCity(regionId);
    }

    /**
     * Description: 开放城市
     *
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView branch() {
        ModelAndView mav = new ModelAndView("/region/region_branch");
        // 把所有的品牌和分类都拿过来
        return mav;
    }

    /**
     * Description: 查找省市区开放情况
     *
     * @return
     */
    @RequestMapping(value = "/findAllProvOpen", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<List<ProvDto>> findAllProvOpen() {
        return regionService.findAllProvOpen();
    }

    /**
     * Description: 查找省市区开放情况
     *
     * @return
     */
    @RequestMapping(value = "/findProvOpenCity/{regionId}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<List<RegionDto>> findProvOpenCity(@PathVariable(value = "regionId") Integer regionId) {
        return regionService.findProvOpenCity(regionId);
    }

    /**
     * Description: 查找所有的开放城市(包含开放城市的父节点以及无网点配送城市)
     * 
     * @return
     */
    @RequestMapping(value = "/findOpenCity", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<List<RegionOpenDto>> findOpenCity() {
        return regionService.findOpenCity();
    }

    /**
     * Description: TODO
     *
     * @return
     */
    @RequestMapping(value = "/findProvAndCity", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<List<ProvAndCityDto>> findProvAndCity() {
        return regionService.findProvAndCity();
    }

}
