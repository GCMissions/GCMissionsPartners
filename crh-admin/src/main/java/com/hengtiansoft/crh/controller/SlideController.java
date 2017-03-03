package com.hengtiansoft.crh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.crh.dto.SlidesDto;
import com.hengtiansoft.crh.service.SlideService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/slide")
@Api(value = "Slides", description = "Slides Show")
public class SlideController {
    
    @Autowired
    private SlideService slideService;

    @RequestMapping(value = "/show", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "Slide Show", httpMethod = "POST", response = ResultDto.class)
    public ResultDto<SlidesDto> getSlides() {
        return slideService.getSlides();
    }
}
