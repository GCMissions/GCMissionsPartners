package com.hengtiansoft.business.context.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.business.context.dto.ImageMaterialIdDto;
import com.hengtiansoft.business.context.service.ImageMaterialService;
import com.hengtiansoft.common.dto.ResultDto;

@Controller
@RequestMapping(value = "/material")
public class ImageMaterialController {

    @Autowired
    private ImageMaterialService imageMaterialService;
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ImageMaterialIdDto findList(@RequestBody ImageMaterialIdDto dto) {
        imageMaterialService.findList(dto);
        return dto;
    }
    
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> uploadImage(String url) {
        return imageMaterialService.uploadImage(url);
    }
    
    @RequestMapping(value = "/deleteImages", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> deleteImages(@RequestParam("deleteIds[]") List<Long> deleteIds) {
        return imageMaterialService.deleteImages(deleteIds);
    }
}
