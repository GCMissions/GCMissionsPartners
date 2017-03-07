package com.hengtiansoft.church.region.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.church.region.dto.RegionSaveDto;
import com.hengtiansoft.church.region.dto.RegionSearchDto;
import com.hengtiansoft.church.region.service.RegionAdminService;
import com.hengtiansoft.common.dto.ResultDto;

@RequestMapping(value = "/region")
@Controller
public class RegionAdminController {

    @Autowired
    private RegionAdminService regionService;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "region/region_list";
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public RegionSearchDto list(RegionSearchDto dto) {
        regionService.searchRegion(dto);
        return dto;
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<?> deleteRegion(@PathVariable Long id) {
        return regionService.deleteRegion(id);
    }
    
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, Model model) {
        if (id == 0L) {
            model.addAttribute("type", "0");
            model.addAttribute("countries", regionService.findNoRegionCountries());
        } else {
            model.addAttribute("type", "1");
            model.addAttribute("region", regionService.regionDetail(id));
        }
        return "region/region_detail";
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> saveResource(@RequestBody RegionSaveDto dto) {
        return regionService.saveRegion(dto);
    }
}
