package com.hengtiansoft.crh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.church.entity.MissionEntity;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.crh.service.MissionService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/mission")
@Api(value = "missions", description = "get Missions")
public class MissionController {
    
    @Autowired
    private MissionService missionService;

    @RequestMapping(value = "/show", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "mission show", httpMethod = "POST", response = ResultDto.class)
    public ResultDto<List<MissionEntity>> getMissions() {
        return missionService.getMissions();
    }
}
