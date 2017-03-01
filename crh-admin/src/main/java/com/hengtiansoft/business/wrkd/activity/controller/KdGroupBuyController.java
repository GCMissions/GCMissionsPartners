package com.hengtiansoft.business.wrkd.activity.controller;

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

import com.hengtiansoft.business.wrkd.activity.dto.KdGroupBuySaveDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdGroupBuySearchDto;
import com.hengtiansoft.business.wrkd.activity.dto.SpecPriceDto;
import com.hengtiansoft.business.wrkd.activity.service.KdGroupBuyService;
import com.hengtiansoft.business.wrkd.image.service.KdPImageService;
import com.hengtiansoft.business.wrkd.product.dto.KdProductSearchDto;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.wrw.enums.KdPImageEnum;
import com.hengtiansoft.wrw.enums.KdTeamBuyStatusEnum;

@Controller
@RequestMapping(value = "/groupPurchase")
public class KdGroupBuyController {
    
    @Autowired
    private KdGroupBuyService kdGroupBuyService;
    @Autowired
    private KdPImageService kdPImageService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("searchStatus", KdTeamBuyStatusEnum.getSearchStatus());
        return "wrkd/groupBuy/group_buy_list";
    }
    
    @RequestMapping(value = "/add/{isReview}/{teamBuyId}", method = RequestMethod.GET)
    public String add(Model model, @PathVariable String isReview, @PathVariable Long teamBuyId) {
        model.addAttribute("isReview", isReview);
        if (!isReview.equals("0")) {
            model.addAttribute("groupBuyDto", kdGroupBuyService.getInfo(teamBuyId));
            model.addAttribute("listImages", kdPImageService.queryImage(teamBuyId, KdPImageEnum.TEAMBUY.getCode()));
            model.addAttribute("teamBuyId", teamBuyId);
        }
        return "wrkd/groupBuy/group_buy_add";
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public KdGroupBuySearchDto searchList(@RequestBody KdGroupBuySearchDto dto) {
        kdGroupBuyService.searchGroupBuyList(dto);
        return dto;
    }
    
    @RequestMapping(value = "/saveAndUpdate", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> saveAndUpdate(@RequestBody KdGroupBuySaveDto dto) {
        return kdGroupBuyService.saveAndUpdate(dto);
    }
    
    @RequestMapping(value = "/getSpecInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<List<SpecPriceDto>> getSpecPriceInfo(Long teamBuyId) {
        return kdGroupBuyService.getSpecPrice(teamBuyId);
    }
    
    /**
     * Description: 商品查询
     *
     * @param searchDto
     * @return
     */
    @RequestMapping(value = "/searchProduct", method = RequestMethod.POST)
    @ResponseBody
    public KdProductSearchDto listProduct(@RequestBody KdProductSearchDto searchDto){
        kdGroupBuyService.search(searchDto);
        return searchDto;
    }
    
    /**
     * 
    * Description: 逻辑删除团购
    *
    * @param teamBuyIds
    * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> deleteTeamBuy(@RequestParam("teamBuyIds[]") List<Long> teamBuyIds) {
        return kdGroupBuyService.deleteGroupBuy(teamBuyIds);
    }
    
    @RequestMapping(value = "/preview", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> getActDetail(Long id){
        return kdGroupBuyService.getActDetailInfo(id);
    }
    
    @RequestMapping(value = "/toPreview/{id}", method = RequestMethod.GET)
    public String preview(@PathVariable Long id, Model model){
        model.addAttribute("teamBuyId", id);
        return "wrkd/groupBuy/group_buy_preview";
    }
}
