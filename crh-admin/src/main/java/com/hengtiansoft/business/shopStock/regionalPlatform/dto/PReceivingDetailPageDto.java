package com.hengtiansoft.business.shopStock.regionalPlatform.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class PReceivingDetailPageDto extends PagingDto<PReceivingDetailDto> {

    /**
     * Variables Name: serialVersionUID
     * Description: TODO
     * Value Description: TODO
     */
    private static final long serialVersionUID = 6124672866080157954L;

    private String            orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
