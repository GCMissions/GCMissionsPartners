package com.hengtiansoft.business.member.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class MemberOrderSearchDto extends PagingDto<MemberOrderListDto> {

    private static final long serialVersionUID = 1L;

    private Long memberId;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
