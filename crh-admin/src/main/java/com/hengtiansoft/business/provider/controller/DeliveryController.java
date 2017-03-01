/*
 * Project Name: wrw-admin
 * File Name: DeliveryController.java
 * Class Name: DeliveryController
 * Copyright 2014 Hengtian Software Inc
 * Licensed under the Hengtiansoft
 * http://www.hengtiansoft.com
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.provider.dto.OrgDto;
import com.hengtiansoft.business.provider.dto.SearchOrgDto;
import com.hengtiansoft.business.provider.service.OrgService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.wrw.enums.OrgTypeEnum;
import com.wordnik.swagger.annotations.Api;

/**
 * Class Name: DeliveryController
 * Description: 终端配送商管理Z
 * 
 * @author chengminmiao
 */
@Api(value = "DeliveryController", description = "终端配送商管理Z")
@Controller
@RequestMapping(value = "/delivery")
public class DeliveryController {

    @Autowired
    private OrgService orgServicers;

    /**
     * Description: 首页
     *
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("/delivery/delivery_list");
    }

    /**
     * Description:搜索
     * 
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public SearchOrgDto findSupplier(@RequestBody SearchOrgDto dto) {
        orgServicers.findOrg(dto, OrgTypeEnum.Z.getKey());
        return dto;
    }

    /**
     * Description:新增页面
     */
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public ModelAndView addMav() {
        return new ModelAndView("/delivery/delivery_add");
    }

    /**
     * Description:保存
     * 
     * @param dto
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> add(@RequestBody OrgDto dto) {
        dto.setOrgType(OrgTypeEnum.Z.getKey());
        return orgServicers.save(dto);
    }

    /**
     * Description:详情页面
     * 
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail/{orgId}", method = RequestMethod.GET)
    public ModelAndView detailMav(@PathVariable(value = "orgId") Long orgId) {
        ModelAndView mav = new ModelAndView("/delivery/delivery_detail");
        mav.addObject("orgDto", orgServicers.findById(orgId));
        return mav;
    }

    /**
     * Description:编辑页面
     * 
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{orgId}", method = RequestMethod.GET)
    public ModelAndView updateMav(@PathVariable(value = "orgId") Long orgId) {
        ModelAndView mav = new ModelAndView("/delivery/delivery_edit");
        mav.addObject("orgDto", orgServicers.findById(orgId));
        return mav;
    }

    /**
     * Description:更新操作
     * 
     * @param dto
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> update(@RequestBody OrgDto dto) {

        return orgServicers.update(dto);
    }
}
