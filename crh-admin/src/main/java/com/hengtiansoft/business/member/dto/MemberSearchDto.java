package com.hengtiansoft.business.member.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class MemberSearchDto extends PagingDto<MemberListDto> {

    private static final long serialVersionUID = 1L;

    private String startDate;
    
    private String endDate;
    
    private String mobile;
    
    private String custName;
    
    private String vip;
    
    private String sex;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
