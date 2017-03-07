package com.hengtiansoft.church.resource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.church.resource.dto.ResourceSaveDto;
import com.hengtiansoft.church.resource.dto.ResourceSearchDto;
import com.hengtiansoft.church.resource.service.ResourceService;
import com.hengtiansoft.common.dto.ResultDto;

@RequestMapping(value = "/resource")
@Controller
public class ResourceController {
    
    @Autowired
    private ResourceService resourceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "resource/resource_list";
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ResourceSearchDto resourceList(ResourceSearchDto dto) {
        resourceService.searchResource(dto);
        return dto;
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<?> deleteResource(@PathVariable Long id) {
        return resourceService.deleteResource(id);
    }
    
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("resource", resourceService.resourceDetail(id));
        return "resource/resource_detail";
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> saveResource(@RequestBody ResourceSaveDto dto) {
        return resourceService.saveResource(dto);
    }
    
    @RequestMapping(value = "/sort/{id}/{type}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<?> adjustSort(@PathVariable Long id, @PathVariable String type) {
        return resourceService.adjustSort(id, type);
    }
}
