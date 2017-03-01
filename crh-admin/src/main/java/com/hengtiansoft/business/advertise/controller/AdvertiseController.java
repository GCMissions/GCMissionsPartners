package com.hengtiansoft.business.advertise.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.business.activity.dto.ActivityCopyPageDto;
import com.hengtiansoft.business.advertise.dto.AddAdDto;
import com.hengtiansoft.business.advertise.dto.AdvertiseDto;
import com.hengtiansoft.business.advertise.dto.DescriptionDto;
import com.hengtiansoft.business.advertise.dto.SearchPurchaseDto;
import com.hengtiansoft.business.advertise.service.AdvertiseService;
import com.hengtiansoft.business.product.service.CategoryService;
import com.hengtiansoft.business.provider.service.OrgService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.util.ApplicationContextUtil;
import com.hengtiansoft.wrw.entity.SAdEntity;
import com.hengtiansoft.wrw.enums.AdTypeEnum;
/**
 * 
* Class Name: AdvertiseController
* Description: 广告管理
* @author chengchaoyin
*
 */
@Controller
@RequestMapping(value = "/advertise")
public class AdvertiseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdvertiseController.class);

    @Autowired
    private AdvertiseService advertiseService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 
    * Description: 轮播位列表
    *
    * @param model
    * @return
    * @author chengchaoyin
     */
    @RequestMapping("/")
    public String index(Model model) {
        List<AdvertiseDto> advertiseDtos = advertiseService.getAdvertise(AdTypeEnum.SALES_AD.getCode());
        model.addAttribute("carouselList", advertiseService.getAdvertise(AdTypeEnum.CAROUSEL_AD.getCode()));
        model.addAttribute("sort", advertiseDtos.isEmpty() ? 1
                : advertiseDtos.get(advertiseDtos.size() - 1).getSort() + 1);
        return "advertise/advertise_list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public SearchPurchaseDto findList(@RequestBody SearchPurchaseDto dto) {
        advertiseService.findPurchase(dto);
        return dto;
    }

    /**
     * 
    * Description: 热门推荐的描述
    *
    * @param descriptionDto
    * @return
    * @author chengchaoyin
     */
    @RequestMapping(value = "/editorDesc", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> editorDesc(@RequestBody DescriptionDto descriptionDto) {
        return advertiseService.editorDesc(descriptionDto.getAdId(), descriptionDto.getDescription(), descriptionDto.getUrl());
    }

    /**
     * 
    * Description: add or update 
    *
    * @param addAdDto
    * @return
    * @author chengchaoyin
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> updateImageUrl(@RequestBody AddAdDto addAdDto) {
        try {
            SAdEntity advertise = advertiseService.updateOrInsert(addAdDto);
            if (advertise == null) {
                return ResultDtoFactory.toNack("热门推荐最多只能有四个");
            }
            return ResultDtoFactory.toAck("保存成功！");
        } catch (Exception e) {
            LOGGER.error("update error: {}", e);
            return ResultDtoFactory.toNack("保存失败");
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Integer> deleteAd(Long adId, Integer sort) {
        try {
            Integer newSort = advertiseService.deleteAd(adId, sort);
            if (newSort == null || newSort >= 0) {
                return ResultDtoFactory.toAck("删除成功！", newSort);
            } else {
                return ResultDtoFactory.toNack("该广告位还未绑定商品，无法删除！");
            }
        } catch (Exception e) {
            LOGGER.error("delete error: {}", e);
            return ResultDtoFactory.toNack("删除失败！");
        }
    }

    /**
     * 
    * Description: 乐园热门推荐升降序操作
    *
    * @param adId
    * @param sort
    * @return
    * @author chengchaoyin
     */
    @RequestMapping(value = "/upOrDwon", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> upOrDownAd(Long adId, Integer sort) {
        //清除乐园热门推荐缓存
        ApplicationContextUtil.getBean(AdvertiseService.class).clearHotCache();
        return advertiseService.upOrDownAd(adId, sort);
    }

    /**
     * 
     * Description: 获得已有活动列表
     *
     * @param activityCopyPageDto
     * @return
     */
    @RequestMapping(value = "/activityList", method = RequestMethod.POST)
    @ResponseBody
    public ActivityCopyPageDto search(@RequestBody ActivityCopyPageDto activityCopyPageDto) {
        advertiseService.search(activityCopyPageDto);
        return activityCopyPageDto;
    }
}
