/*
 * Project Name: wrw-admin
 * File Name: MemberRechargeController.java
 * Class Name: MemberRechargeController
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
package com.hengtiansoft.business.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.customer.dto.RechargeSearchDto;
import com.hengtiansoft.business.customer.service.MemberRechargeService;
import com.wordnik.swagger.annotations.Api;

/**
 * Class Name: MemberRechargeController
 * Description: 消费者充值查询
 * 
 * @author chengminmiao
 */
@Api(value = "MemberRechargeController", description = "消费者充值查询管理")
@Controller
@RequestMapping("/memberRecharge")
public class MemberRechargeController {

    @Autowired
    private MemberRechargeService memberRechargeService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("member/recharge_list");
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public RechargeSearchDto list(@RequestBody RechargeSearchDto searchDto) {
        memberRechargeService.search(searchDto);
        return searchDto;
    }

}
