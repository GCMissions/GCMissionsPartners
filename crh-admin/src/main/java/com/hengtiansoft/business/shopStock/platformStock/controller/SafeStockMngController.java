/*
 * Project Name: wrw-admin
 * File Name: SafeStockMngController.java
 * Class Name: SafeStockMngController
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
package com.hengtiansoft.business.shopStock.platformStock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.shopStock.platformStock.dto.OrgStockDetailDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.OrgStockSearchDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.GoodsStockDto;
import com.hengtiansoft.business.shopStock.platformStock.service.OrgStockService;
import com.hengtiansoft.common.dto.ResultDto;
import com.wordnik.swagger.annotations.Api;

/**
 * Class Name: SafeStockMngController
 * Description:
 * 
 * @author xianghuang
 */
@Api(value = "SafeStockMngController", description = "安全库存设置")
@Controller
@RequestMapping("safeStock")
public class SafeStockMngController {
    @Autowired
    private OrgStockService orgStockService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView list() {
        return new ModelAndView("plateformStock/safeStock_list");
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public OrgStockSearchDto list(@RequestBody OrgStockSearchDto searchDto) {
        return orgStockService.searchOrgStock(searchDto);
    }

    @RequestMapping(value = { "/detail/{orgId}" }, method = RequestMethod.GET)
    public ModelAndView searchOne(@PathVariable(value = "orgId") Long orgId) {
        OrgStockDetailDto dto = orgStockService.getDetail(orgId);
        ModelAndView mav = new ModelAndView("plateformStock/safeStock_detail");
        mav.addObject("orgStockDetailDto", dto);
        return mav;
    }

    @RequestMapping(value = "/setting", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> setting(@RequestBody GoodsStockDto setting) {
        return orgStockService.saveSetting(setting.getStockId(), setting.getSafeStockSetting(), setting.getStandardStockSetting());
    }
}
