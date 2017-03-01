package com.hengtiansoft.business.member.dto;

import java.io.Serializable;

import com.hengtiansoft.wrw.entity.MVipOrderEntity;

public class MemberIsVipDto implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Boolean isVip;
    
    private MVipOrderEntity vipOrder;

    public Boolean getIsVip() {
        return isVip;
    }

    public void setIsVip(Boolean isVip) {
        this.isVip = isVip;
    }

    public MVipOrderEntity getVipOrder() {
        return vipOrder;
    }

    public void setVipOrder(MVipOrderEntity vipOrder) {
        this.vipOrder = vipOrder;
    }
}
