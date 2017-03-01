/*
 * Project Name: wrw-admin
 * File Name: RechargeConfigController.java
 * Class Name: RechargeConfigController
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
package com.hengtiansoft.business.marketing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.marketing.dto.CouponPageDto;
import com.hengtiansoft.business.marketing.dto.RechargeConfDto;
import com.hengtiansoft.business.marketing.dto.RechargeConfPageDto;
import com.hengtiansoft.business.marketing.service.RechargeConfigService;
import com.hengtiansoft.common.dto.ResultDto;
import com.wordnik.swagger.annotations.Api;

/**
 * Class Name: RechargeConfigController
 * Description: 充值配置
 * 
 * @author chengminmiao
 */
@Api(value = "RechargeConfigController", description = "充值配置")
@Controller
@RequestMapping("/rechargeConfig")
public class RechargeConfigController {

    @Autowired
    private RechargeConfigService rechargeConfigService;

    /**
     * Description: 充值配置首页
     *
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("recharge/config_list");
    }

    /**
     * Description: 查询LIST
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public RechargeConfPageDto list(@RequestBody RechargeConfPageDto dto) {
        rechargeConfigService.getList(dto);
        return dto;
    }

    /**
     * Description: 添加页面
     *
     * @return
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public ModelAndView toAdd() {
        return new ModelAndView("recharge/config_add");
    }

    /**
     * Description: 编辑页面
     *
     * @return
     */
    @RequestMapping(value = "/detail/{configId}", method = RequestMethod.GET)
    public ModelAndView detail(@PathVariable(value = "configId") Long configId) {
        ModelAndView mav = new ModelAndView("recharge/config_detail");
        if (0 != configId) {
            mav.addObject("config", rechargeConfigService.find(configId));
        }
        return mav;
    }

    /**
     * Description: 添加充值配置
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> save(@RequestBody RechargeConfDto dto) {
        return rechargeConfigService.save(dto);
    }

    /**
     * Description: 查询优惠券
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/getCoupon", method = RequestMethod.POST)
    @ResponseBody
    public CouponPageDto getCoupon(@RequestBody CouponPageDto dto) {
        rechargeConfigService.getCoupon(dto);
        return dto;
    }

    /**
     * Description: 删除
     *
     * @return
     */
    @RequestMapping(value = "/delete/{configId}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<String> delete(@PathVariable(value = "configId") Long configId) {
        return rechargeConfigService.delete(configId);
    }

}
