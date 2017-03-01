package com.hengtiansoft.business.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.business.member.dto.MemberCouponSearchDto;
import com.hengtiansoft.business.member.dto.MemberOrderSearchDto;
import com.hengtiansoft.business.member.dto.MemberSearchDto;
import com.hengtiansoft.business.member.service.MMemberService;
import com.hengtiansoft.common.controller.AbstractController;

@Controller
@RequestMapping(value = "/member")
public class MMemberController extends AbstractController {
    
    @Autowired
    private MMemberService memberService;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("registerCount", memberService.getRegisterCount());
        return "member/member_list";
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public MemberSearchDto search(@RequestBody MemberSearchDto dto) {
        memberService.searchMember(dto);
        return dto;
    }
    
    @RequestMapping(value = "/detail/{memberId}", method = RequestMethod.GET)
    public String memberDetail(@PathVariable Long memberId, Model model) {
        model.addAttribute("memberDetail", memberService.getMemberDetail(memberId));
        model.addAttribute("memberId", memberId);
        return "member/member_detail";
    }
    
    @RequestMapping(value = "/coupon/list", method = RequestMethod.POST)
    @ResponseBody
    public MemberCouponSearchDto searchCoupon(@RequestBody MemberCouponSearchDto dto) {
        memberService.searchCoupon(dto);
        return dto;
    }
    
    @RequestMapping(value = "/order/{memberId}", method = RequestMethod.GET)
    public String memberOrder(@PathVariable Long memberId, Model model) {
        model.addAttribute("memberOrderDetail", memberService.getMemberOrderDetail(memberId));
        model.addAttribute("memberId", memberId);
        return "member/member_order";
    }
    
    @RequestMapping(value = "/order/list", method = RequestMethod.POST)
    @ResponseBody
    public MemberOrderSearchDto searchMemberOrder(@RequestBody MemberOrderSearchDto dto) {
        memberService.searchMemberOrder(dto);
        return dto;
    }
}
