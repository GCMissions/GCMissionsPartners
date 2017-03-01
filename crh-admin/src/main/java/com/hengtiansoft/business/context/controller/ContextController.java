package com.hengtiansoft.business.context.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.hengtiansoft.business.context.dto.ActStockIdDto;
import com.hengtiansoft.business.context.dto.SearchActImaDto;
import com.hengtiansoft.business.context.service.ContextService;
import com.hengtiansoft.common.dto.ResultDto;

@Controller
@RequestMapping(value = "/context")
public class ContextController {
    
    @Autowired
    private ContextService contextService;

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("today", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        return "context/context";
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public SearchActImaDto findList(@RequestBody SearchActImaDto dto) {
        contextService.findList(dto);
        return dto;
    }
    
    @RequestMapping(value = "/uploadPic", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> uploadActPicture(Long actStockId, String imageUrl) {
        return contextService.uploadActImag(actStockId, imageUrl);
    }
    
    @RequestMapping(value = "/uploadPics", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> uploadActPictures(Long actStockId, @RequestParam("imageUrls[]") List<String> imageUrls) {
        return contextService.uploadActImags(actStockId, imageUrls);
    }
    
    @RequestMapping(value = "/contextEditor/{actStockId}", method = RequestMethod.GET)
    public String editor(Model model, @PathVariable Long actStockId) {
        SearchActImaDto dto = contextService.getInfoByStockId(actStockId);
        model.addAttribute("infoDto", dto);
        model.addAttribute("actStockId", actStockId);
        return "context/context_editor";
    }
    
    @RequestMapping(value = "/editorList", method = RequestMethod.POST)
    @ResponseBody
    public ActStockIdDto findImags(@RequestBody ActStockIdDto dto) {
        contextService.findImages(dto);
        return dto;
    }
    
    @RequestMapping(value = "/deleteImages", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> deteleImages(@RequestParam("deleteIds[]") List<Long> actImageRecordId) {
        return contextService.deleteImages(actImageRecordId);
    }
    
    @RequestMapping(value = "/contextMaterial/{actStockId}", method = RequestMethod.GET)
    public String material(Model model, @PathVariable Long actStockId) {
        SearchActImaDto dto = contextService.getInfoByStockId(actStockId);
        model.addAttribute("fromUrl", "list");
        model.addAttribute("infoDto", dto);
        model.addAttribute("actStockId", actStockId);
        return "context/context_material";
    }
    
    @RequestMapping(value = "/contextEditor/contextMaterial/{actStockId}", method = RequestMethod.GET)
    public String contextEditorMaterial(Model model, @PathVariable Long actStockId) {
        SearchActImaDto dto = contextService.getInfoByStockId(actStockId);
        model.addAttribute("fromUrl", "editor");
        model.addAttribute("infoDto", dto);
        model.addAttribute("actStockId", actStockId);
        return "context/context_material";
    }
}
