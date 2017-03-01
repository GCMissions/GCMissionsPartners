/*
 * Project Name: wrw-admin
 * File Name: CoolBagController.java
 * Class Name: CoolBagController
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
package com.hengtiansoft.business.wrkd.feature.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;





import com.hengtiansoft.business.wrkd.feature.dto.FeatureInfoDto;
import com.hengtiansoft.business.wrkd.feature.dto.FeaturePageDto;
import com.hengtiansoft.business.wrkd.feature.service.CoolBagService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.exception.BizServiceException;
import com.hengtiansoft.wrw.entity.CoolbagImageEntity;
import com.hengtiansoft.wrw.enums.AppImageType;

/**
 * Class Name: CoolBagController Description: TODO
 * 
 * @author kangruan
 *
 */
@Controller
@RequestMapping("/coolbag")
public class CoolBagController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoolBagController.class);

    @Autowired
    private CoolBagService coolBagService;

    /**
     * Description: 首页
     *
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("carouselList", coolBagService.findImageList(AppImageType.CAROUSEL));
        model.addAttribute("tagList", coolBagService.findImageList(AppImageType.TAG));
        return "coolbag/coolbag";
    }

    /**
     * Description: 轮播图更新
     *
     * @param entity
     * @return
     */
    @RequestMapping("/img/update")
    @ResponseBody
    public ResultDto<?> update(@RequestBody CoolbagImageEntity entity) {
        try {
            coolBagService.updateImage(entity);
            return ResultDtoFactory.toAck("保存成功");
        } catch (Exception e) {
            LOGGER.error("update error", e);
            return ResultDtoFactory.toNack("保存失败");
        }
    }
    /**
     * Description: 删除轮播广告
     * 
     * @param id
     * @return
     */
    @RequestMapping("/img/delete/{id}")
    @ResponseBody
    public ResultDto<String> deleteImg(@PathVariable Long id){
    	return coolBagService.deleteImage(id);
    }
    
    /**
     * Description: 专辑tab页
     *
     * @param model
     * @return
     */
    @RequestMapping("/feature/")
    public String toFeature(Model model) {
        model.addAttribute("active", "feature");
        return index(model);
    }

    /**
     * Description: 专辑列表
     *
     * @param pageDto
     * @return
     */
    @RequestMapping("/feature/list")
    @ResponseBody
    public FeaturePageDto list(@RequestBody FeaturePageDto pageDto) {
        coolBagService.findFeaturePage(pageDto);
        return pageDto;
    }

    /**
     * Description: 专辑新增页面
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/feature/add/{id}")
    public String add(Model model, @PathVariable Long id) {
        model.addAttribute("tagList", coolBagService.findImageList(AppImageType.TAG));
        if (id != -1L) {
            model.addAttribute("feature", coolBagService.findFeature(id));
        }
        return "coolbag/feature_add";
    }

    /**
     * Description: 专辑查看
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/feature/view/{id}")
    public String view(Model model, @PathVariable Long id) {
        model.addAttribute("tagList", coolBagService.findImageList(AppImageType.TAG));
        model.addAttribute("feature", coolBagService.findFeature(id));
        return "coolbag/feature_view";
    }
    
    /**
     * Description: 专辑删除操作
     * 
     * @return
     */
    @RequestMapping("/feature/delete/{id}")
    @ResponseBody
    public ResultDto<String> delete(@PathVariable Long id){
    	return coolBagService.deleteFeature(id);
    }
    
    /**
     * Description: 专辑上下架操作
     * 
     * @param 
     * @return
     */
    @RequestMapping("/feature/sheif/{id}")
    @ResponseBody
    public ResultDto<String> sheif(@PathVariable Long id){
    	return coolBagService.sheifFeature(id);
    }
    
    /**
     * Description: 专辑保存
     *
     * @param dto
     * @return
     */
    @RequestMapping("/feature/save")
    @ResponseBody
    public ResultDto<?> saveOrUpdate(@RequestBody FeatureInfoDto dto) {
        try {
            coolBagService.saveOrUpdate(dto);
            return ResultDtoFactory.toAck("保存成功");
        } catch (BizServiceException e) {
            return ResultDtoFactory.toNack(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("update error", e);
            return ResultDtoFactory.toNack("保存失败");
        }
    }
    
    /**
     * Description: 专辑排序
     *
     * @param id
     * @param sort
     * @param type
     * @return
     */
    @RequestMapping("/feature/sort/{id}/{sort}/{type}")
    @ResponseBody
    public ResultDto<?> sort(@PathVariable Long id, @PathVariable Integer sort, @PathVariable String type) {
        try {
            coolBagService.updateFeatureSort(id, sort, type);
            return ResultDtoFactory.toAck("保存成功");
        } catch (BizServiceException e) {
            return ResultDtoFactory.toNack(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("update error", e);
            return ResultDtoFactory.toNack("保存失败");
        }
    }

}
