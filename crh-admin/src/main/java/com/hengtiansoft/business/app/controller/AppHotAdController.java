/*
 * Project Name: wrw-admin
 * File Name: AppHotRecommendationController.java
 * Class Name: AppHotRecommendationController
 *
 * Copyright 2014 Hengtian Software Inc
 *
 * Licensed under the Hengtiansoft
 *
 * http://www.hengtiansoft.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.app.controller;

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
import com.hengtiansoft.business.advertise.dto.DescriptionDto;
import com.hengtiansoft.business.app.dto.AppAddHotAdDto;
import com.hengtiansoft.business.app.dto.AppAdvertiseDto;
import com.hengtiansoft.business.app.dto.SearchAppHotAdDto;
import com.hengtiansoft.business.app.service.AppHotAdService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.wrw.entity.AppHotAdEntity;
import com.hengtiansoft.wrw.enums.AdTypeEnum;


/**
* Class Name: AppHotAdController
* Description: app 首页管理
* @author qianqianzhu
*
*/
@Controller
@RequestMapping(value="/appHotAd")
public class AppHotAdController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AppHotAdController.class);
    
    @Autowired
    private AppHotAdService appHotAdService;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        List<AppAdvertiseDto> advertiseDtos = appHotAdService.getAdvertise(AdTypeEnum.APP_HOT.getCode());
        model.addAttribute("carouselList", appHotAdService.getAdvertise(AdTypeEnum.APP_CAROUSEL.getCode()));
        model.addAttribute("sort", advertiseDtos.isEmpty() ? 1
                : advertiseDtos.get(advertiseDtos.size() - 1).getSort() + 1);
        return "app/appHotAd/app_hot_ad_list";
    }
    
    @RequestMapping(value="/list",method=RequestMethod.POST)
    @ResponseBody
    public SearchAppHotAdDto findList(@RequestBody SearchAppHotAdDto dto) {
        appHotAdService.findAppHotAd(dto);
        return dto;
    } 
    
    @RequestMapping(value = "/editorDesc", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> editorDesc(@RequestBody DescriptionDto descriptionDto) {
        return appHotAdService.editorDesc(descriptionDto.getAdId(), descriptionDto.getDescription(), descriptionDto.getUrl());
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> updateImageUrl(@RequestBody AppAddHotAdDto addAdDto) {
        try {
            AppHotAdEntity advertise = appHotAdService.updateOrInsert(addAdDto);
            if (advertise == null) {
                return ResultDtoFactory.toNack("热门推荐最多只能有五个");
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
            Integer newSort = appHotAdService.deleteAd(adId, sort);
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
    
    @RequestMapping(value = "/upOrDwon", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> upOrDownAd(Long adId, Integer sort) {
        return appHotAdService.upOrDownAd(adId, sort);
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
        appHotAdService.search(activityCopyPageDto);
        return activityCopyPageDto;
    }

    
}
