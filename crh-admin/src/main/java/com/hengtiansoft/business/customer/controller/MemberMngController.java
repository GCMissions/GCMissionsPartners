/*
 * Project Name: wrw-admin
 * File Name: MemberMngController.java
 * Class Name: MemberMngController
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hengtiansoft.business.customer.dto.MemberDetailDto;
import com.hengtiansoft.business.customer.dto.MemberOrderDetailDto;
import com.hengtiansoft.business.customer.dto.MemberSearchDto;
import com.hengtiansoft.business.customer.service.MemberService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.wrw.entity.MMemberEntity;
import com.wordnik.swagger.annotations.Api;

/**
 * Class Name: MemberMngController
 * Description: TODO
 * 
 * @author xianghuang
 */
@Api(value = "MemberMngController", description = "消费者管理")
@Controller
@RequestMapping("memberMng")
public class MemberMngController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView list() {
        return new ModelAndView("customer/member_list");
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public MemberSearchDto list(@RequestBody MemberSearchDto searchDto) {
        memberService.search(searchDto);
        return searchDto;
    }

    @RequestMapping(value = { "/detail/{memberId}" }, method = RequestMethod.GET)
    public ModelAndView detail(@PathVariable(value = "memberId") Long memberId) {
        MemberDetailDto dto = memberService.get(memberId);
        ModelAndView mav = new ModelAndView("customer/member_detail");
        mav.addObject("dto", dto);
        return mav;
    }

    @RequestMapping(value = { "/detail/orderDetail/{orderId}" }, method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<MemberOrderDetailDto> orderDetail(@PathVariable(value = "orderId") String orderId) {
        return ResultDtoFactory.toAck("SUCCESS", memberService.getOrderDetail(orderId));
    }

    @RequestMapping(value = { "/resetPwd/{memberId}" }, method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<String> resetPwd(@PathVariable(value = "memberId") Long memberId) {
        return memberService.resetPwd(memberId);
    }
    
    @RequestMapping(value = "/findBypPhone", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Long> findBypPhone(@RequestBody String memberPhone) {
        MMemberEntity entity = memberService.findByLoginId(memberPhone,"1");
        if(entity != null){
            return ResultDtoFactory.toAck("success!", entity.getUserId());
        }
        return ResultDtoFactory.toNack("没有该用户或者用户已失效！");
    }
}
