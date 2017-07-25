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
import com.hengtiansoft.church.resource.dto.ResourceSortDto;
import com.hengtiansoft.church.resource.service.ResourceAdminService;
import com.hengtiansoft.common.dto.ResultDto;

@RequestMapping(value = "/resource")
@Controller
public class ResourceAdminController {

    @Autowired
    private ResourceAdminService resourceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "resource/resource_list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ResourceSearchDto resourceList(@RequestBody ResourceSearchDto dto) {
        resourceService.searchResource(dto);
        return dto;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> deleteResource(Long id) {
        return resourceService.deleteResource(id);
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, Model model) {
        if (id != 0L) {
            model.addAttribute("resource", resourceService.resourceDetail(id));
            model.addAttribute("id", id);
            model.addAttribute("showType", "1");
        } else {
            model.addAttribute("showType", "0");
        }
        return "resource/resource_detail";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> saveResource(@RequestBody ResourceSaveDto dto) {
        return resourceService.saveResource(dto);
    }

    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> adjustSort(@RequestBody ResourceSortDto dto) {
        return resourceService.adjustSort(dto.getId(), dto.getType());
    }
    
}
