package com.hengtiansoft.business.order.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class ShopGroupPageDto extends PagingDto<ShopGroupDto> {

    private static final long serialVersionUID = -8606003352987475006L;

    // 团购状态
    private String status;

    // 开始时间
    private String startDate;

    // 结束时间
    private String endDate;
    
    // 联系电话
    private String addressPhone;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getAddressPhone() {
        return addressPhone;
    }

    public void setAddressPhone(String addressPhone) {
        this.addressPhone = addressPhone;
    }
}
