package com.hengtiansoft.crh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.crh.dto.ResourceDto;
import com.hengtiansoft.crh.service.ResourceService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RequestMapping(value = "/resource")
@Controller
@Api(value = "Resource", description = "get all resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @RequestMapping(value = "/show", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "Resource Show", httpMethod = "POST", response = ResultDto.class)
    public ResultDto<List<ResourceDto>> getAllResource() {
        return resourceService.findResources();
    }
}
