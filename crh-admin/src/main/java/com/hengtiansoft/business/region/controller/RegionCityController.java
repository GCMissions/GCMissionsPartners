package com.hengtiansoft.business.region.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.region.dto.RegionAddDto;
import com.hengtiansoft.business.region.dto.RegionCityDto;
import com.hengtiansoft.business.region.service.RegionCityService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;

@RequestMapping(value = "/regionCity")
@Controller
public class RegionCityController {

    @Autowired
    private RegionCityService regionCityService;

    /**
     * Description: 首页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home() {
        return new ModelAndView("/region/region_branch");
    }

    /**
     * 获取首页的数据
     * 
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<List<RegionCityDto>> list() {

        return ResultDtoFactory.toAck("dto", regionCityService.findRegionByLevelType());
    }

    /**
     * Description: 保存
     *
     * @param list
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> update(@RequestBody RegionAddDto dto) {
        regionCityService.update(dto);
        return ResultDtoFactory.toAck("保存成功！");
    }

}
