package com.hengtiansoft.crh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.crh.dto.GroupDto;
import com.hengtiansoft.crh.service.GroupService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/partner")
@Api(value = "Partner", description = "Groups,Partners")
public class PartnerController {
    
    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/show", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "Partner Show", httpMethod = "POST", response = ResultDto.class)
    public ResultDto<List<GroupDto>> getGroups() {
        return groupService.getAllGroups();
    }
}
