package com.hengtiansoft.business.marketing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.marketing.dto.PointsDto;
import com.hengtiansoft.business.marketing.service.PointsService;
import com.hengtiansoft.common.dto.ResultDto;
import com.wordnik.swagger.annotations.Api;

/**
 * Class Name: PointsController
 * Description: 积分参数管理
 * 
 * @author yigesong
 */
@Api(value = "PointsController", description = "积分参数管理")
@Controller
@RequestMapping("/points")
public class PointsController {

    @Autowired
    private PointsService pointsService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("points/points_main");
        mav.addObject("points", pointsService.getPointConfig());
        return mav;
    }

    @RequestMapping(value = "/savePoints", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> savePoints(@RequestBody PointsDto pointsDto) {
        return pointsService.savePointsConfig(pointsDto);
    }

}
