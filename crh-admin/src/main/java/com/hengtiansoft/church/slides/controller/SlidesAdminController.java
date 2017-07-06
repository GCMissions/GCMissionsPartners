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
import com.hengtiansoft.church.slides.dto.SlidesSortDto;
import com.hengtiansoft.church.slides.service.SlidesAdminService;
import com.hengtiansoft.common.dto.ResultDto;

@Controller
@RequestMapping(value = "/slides")
public class SlidesAdminController {

    @Autowired
    private SlidesAdminService slidesService;

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

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> deleteSlide(Long id) {
        return slidesService.deleteSlide(id);
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, Model model) {
        if (id != 0L) {
            model.addAttribute("slide", slidesService.slideDetail(id));
            model.addAttribute("id", id);
            model.addAttribute("showType", "1");
        } else {
            model.addAttribute("showType", "0");
        }
        return "slides/slides_detail";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> saveSlides(@RequestBody SlidesSaveDto dto) {
        return slidesService.saveSlides(dto);
    }

    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> adjustSort(@RequestBody SlidesSortDto dto) {
        return slidesService.adjustSort(dto.getId(), dto.getType());
    }
}
