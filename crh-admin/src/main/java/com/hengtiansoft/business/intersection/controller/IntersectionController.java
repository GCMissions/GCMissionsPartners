package com.hengtiansoft.business.intersection.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.business.intersection.dto.IntersectionSaveDto;
import com.hengtiansoft.business.intersection.dto.IntersectionSearchDto;
import com.hengtiansoft.business.intersection.service.IntersectionService;
import com.hengtiansoft.common.dto.ResultDto;

/**
 * 
* Class Name: IntersectionController
* Description: 合集
* @author yiminli
*
 */
@Controller
@RequestMapping(value = "intersection")
public class IntersectionController {
    
    // 查看操作
    private static final String LOOK = "1";
    
    // 编辑操作
    private static final String EDITOR = "2";
    
    @Autowired
    private IntersectionService intersectionService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "intersection/intersection_list";
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public IntersectionSearchDto search(@RequestBody IntersectionSearchDto dto) {
        intersectionService.searchIntersection(dto);
        return dto;
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> save(@RequestBody IntersectionSaveDto dto) {
        return intersectionService.save(dto);
    }
    
    @RequestMapping(value = "/editor/{id}/{opration}", method = RequestMethod.GET)
    public String editorIndex(Model model, @PathVariable Long id, @PathVariable String opration) {
        model.addAttribute("opration", opration);
        if (LOOK.equals(opration) || EDITOR.equals(opration)) {
            model.addAttribute("intersection", intersectionService.findIntersection(id));
        }
        return "intersection/intersection_add";
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> delete(@RequestParam("ids[]") List<Long> ids) {
        return intersectionService.delete(ids);
    }
    
    @RequestMapping(value = "/toPreview/{id}", method = RequestMethod.GET)
    public String toPreview(Model model, @PathVariable Long id) {
        model.addAttribute("id", id);
        return "intersection/intersection_preview";
    }
    
    @RequestMapping(value = "/preview", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> getIntersectionDetail(Long id) {
        return intersectionService.getIntersectionDetail(id);
    }
}
