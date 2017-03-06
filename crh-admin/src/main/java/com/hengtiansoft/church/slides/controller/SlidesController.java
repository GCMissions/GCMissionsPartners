package com.hengtiansoft.church.slides.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.church.slides.dto.SlidesSaveDto;
import com.hengtiansoft.church.slides.dto.SlidesSearchDto;
import com.hengtiansoft.church.slides.service.SlidesService;
import com.hengtiansoft.common.dto.ResultDto;

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
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<?> deleteSlide(@PathVariable Long id) {
        return slidesService.deleteSlide(id);
    }
    
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("slide", slidesService.slideDetail(id));
        return "slides/slides_detail";
    }
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> saveSlides(@RequestBody SlidesSaveDto dto) {
        return slidesService.saveSlides(dto);
    }
    
    @RequestMapping(value = "/sort/{id}/{type}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<?> adjustSort(@PathVariable Long id, @PathVariable String type) {
        return slidesService.adjustSort(id, type);
    }
}
