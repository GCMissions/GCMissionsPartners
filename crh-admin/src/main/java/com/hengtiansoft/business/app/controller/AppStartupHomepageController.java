/*
 * Project Name: wrw-admin
 * File Name: AppStartupHomepageController.java
 * Class Name: AppStartupHomepageController
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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.business.app.dto.AppStartUpHomepageDto;
import com.hengtiansoft.business.app.dto.SearchAppStartUpHomepageDto;
import com.hengtiansoft.business.app.service.AppStartupHomepageService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.wrw.enums.AppSource;

/**
 * Class Name: AppStartupHomepageController Description: TODO
 * 
 * @author qianqianzhu
 *
 */
@Controller
@RequestMapping(value = "/appStartupHomepage")
public class AppStartupHomepageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppStartupHomepageController.class);

    @Autowired
    private AppStartupHomepageService appStartupHomepageService;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        return "app/appStartupHomepage/ash_list";
    }
    
    @RequestMapping(value="/list",method=RequestMethod.POST)
    @ResponseBody
    public SearchAppStartUpHomepageDto findList(@RequestBody SearchAppStartUpHomepageDto dto) {
        appStartupHomepageService.findList(dto);
        return dto;
    }
    
    /**
     * Description:新增页面
     */
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public String addPage(Model model) {
        model.addAttribute("appSource", AppSource.values());
        return "app/appStartupHomepage/ash_add";
    }
    
    @RequestMapping(value = "/editor/{id}", method = RequestMethod.GET)
    public String editor(Model model, @PathVariable String id) {
        model.addAttribute("dto", appStartupHomepageService.findDto(id));
        model.addAttribute("appSource", AppSource.values());
        return "app/appStartupHomepage/ash_edit";
    }
    
    @RequestMapping(value = "/updateOrInsert", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<?> updateImageUrl(@RequestBody AppStartUpHomepageDto addAdDto) {
        try {
            appStartupHomepageService.updateOrInsert(addAdDto);
            return ResultDtoFactory.toAck("保存成功！");
        } catch (Exception e) {
            LOGGER.error("update error: {}", e);
            return ResultDtoFactory.toNack("保存失败");
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Integer> deleteAd(Long id) {
        try {
            appStartupHomepageService.deleteEntity(id);
            return ResultDtoFactory.toAck("删除成功！");
        } catch (Exception e) {
            LOGGER.error("delete error: {}", e);
            return ResultDtoFactory.toNack("删除失败！");
        }
    }

}
