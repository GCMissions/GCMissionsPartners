package com.hengtiansoft.crh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.church.model.dto.ModelAndDataDto;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.crh.service.MissionService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;

@Controller
@RequestMapping(value = "/mission")
@Api(value = "missions", description = "get Missions")
public class MissionController {
    
    @Autowired
    private MissionService missionService;

    @RequestMapping(value = "/show", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "mission show", httpMethod = "POST", response = ModelAndDataDto.class)
    @ApiResponse(code = 200,message = "OK" ,response = ModelAndDataDto.class)
    public ModelAndDataDto getMissions() {
        return missionService.getData();
    }
}
