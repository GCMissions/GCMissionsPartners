package com.hengtiansoft.church.slides.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.church.slides.dto.SlidesSearchDto;
import com.hengtiansoft.church.slides.service.SlidesService;

@Controller
@RequestMapping(value = "/slides")
public class SlidesController {
    
    @Autowired
    private SlidesService slidesService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        return "slides/slides_list";
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public SlidesSearchDto slideList(@RequestBody SlidesSearchDto dto) {
        slidesService.getSlidesList(dto);
        return dto;
    }
}
