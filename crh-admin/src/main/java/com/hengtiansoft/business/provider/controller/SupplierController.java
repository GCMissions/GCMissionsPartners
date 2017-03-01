/*
 * Project Name: wrw-admin
 * File Name: SupplierController.java
 * Class Name: SupplierController
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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.provider.dto.OrgDto;
import com.hengtiansoft.business.provider.dto.OrgSaveDto;
import com.hengtiansoft.business.provider.dto.SearchSalerDto;
import com.hengtiansoft.business.provider.service.OrgService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.entity.SOrgEntity;
import com.hengtiansoft.wrw.enums.OrgTypeEnum;
import com.wordnik.swagger.annotations.Api;

/**
 * Class Name: SupplierController
 * Description: 区域供应商管理P
 * 
 * @author chengminmiao
 */
@Api(value = "SupplierController", description = "区域供应商管理P")
@Controller
@RequestMapping(value = "/supplier")
public class SupplierController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SupplierController.class);

    @Autowired
    private OrgService orgServicers;
    @Autowired
    private SOrgDao sOrgDao;

    /**
     * Description: 首页
     *
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("/supplier/supplier_list");
    }

    /**
     * Description:搜索
     * 
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public SearchSalerDto findList(@RequestBody SearchSalerDto dto) {
        dto.setStatus("1");
        orgServicers.findSaler(dto, OrgTypeEnum.S.getKey());
        return dto;
    }

    /**
     * Description:新增页面
     */
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public ModelAndView addMav() {
        return new ModelAndView("/supplier/supplier_add");
    }

    /**
     * Description:保存
     * 
     * @param dto
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> add(@RequestBody OrgSaveDto dto) {
        dto.setOrgType(OrgTypeEnum.S.getKey());
        dto.setStatus("1");
        return orgServicers.saveSOrg(dto);
    }
    
    @RequestMapping(value = "/editor/{orgCode}", method = RequestMethod.GET)
    public String editor(Model model, @PathVariable String orgCode) {
        model.addAttribute("org", sOrgDao.findByOrgCode(orgCode));
        return "supplier/org_editor";
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> deleteOrg(@RequestParam String orgCode) {
        return orgServicers.deleteOrg(orgCode);
    }
    
    @RequestMapping(value = "/editor/editor", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> editor(@RequestBody OrgSaveDto dto) {
        return orgServicers.editorOrg(dto);
    }

    /**
     * Description:详情页面
     * 
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail/{orgId}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView detailMav(@PathVariable(value = "orgId") Long orgId) {
        ModelAndView mav = new ModelAndView("/supplier/supplier_detail");
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
    @ResponseBody
    public ModelAndView updateMav(@PathVariable(value = "orgId") Long orgId) {
        ModelAndView mav = new ModelAndView("/supplier/supplier_edit");
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

    @RequestMapping(value = { "/resetPwd/{orgId}" }, method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> resetPwd(@PathVariable(value = "orgId") Long orgId) {
        return orgServicers.resetPwd(orgId);
    }
    
    @RequestMapping(value = "/getOrgCode", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> getOrgCode() {
        return orgServicers.getOrgCode();
    }
    
    @RequestMapping(value = "/getSuppliers", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<List<SOrgEntity>> getSuppliers(){
        List<SOrgEntity> orgs = new ArrayList<>();
        try {
            orgs = orgServicers.findByType(OrgTypeEnum.S.getKey());
        } catch (Exception e) {
            LOGGER.error("查询失败:",e);
            return ResultDtoFactory.toNack("操作失败!");
        }
        return ResultDtoFactory.toAck("供应商",orgs);
    }
}
