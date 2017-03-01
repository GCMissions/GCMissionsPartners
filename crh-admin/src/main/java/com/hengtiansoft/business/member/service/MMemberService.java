package com.hengtiansoft.business.member.service;

import com.hengtiansoft.business.member.dto.MemberCouponSearchDto;
import com.hengtiansoft.business.member.dto.MemberDetailDto;
import com.hengtiansoft.business.member.dto.MemberOrderDetailDto;
import com.hengtiansoft.business.member.dto.MemberOrderSearchDto;
import com.hengtiansoft.business.member.dto.MemberSearchDto;

public interface MMemberService {

    /**
     * 
    * Description: 查找用户列表
    *
    * @param dto
     */
    void searchMember(MemberSearchDto dto);
    
    /**
     * 
    * Description: 查询有效注册人数
    *
    * @return
     */
    Long getRegisterCount();
    
    /**
     * 
    * Description: 查询优惠券列表
    *
    * @param dto
     */
    void searchCoupon(MemberCouponSearchDto dto);
    
    /**
     * 
    * Description: 查询用户订单列表信息
    *
    * @param dto
     */
    void searchMemberOrder(MemberOrderSearchDto dto);
    
    /**
     * 
    * Description: 获取用户详细信息
    *
    * @param memberId
    * @return
     */
    MemberDetailDto getMemberDetail(Long memberId);
    
    /**
     * 
    * Description: 获取用户下单详情
    *
    * @param memberId
    * @return
     */
    MemberOrderDetailDto getMemberOrderDetail(Long memberId);
}
