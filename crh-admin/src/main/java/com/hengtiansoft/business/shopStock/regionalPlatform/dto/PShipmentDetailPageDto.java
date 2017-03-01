package com.hengtiansoft.business.shopStock.regionalPlatform.dto;

import com.hengtiansoft.common.dto.PagingDto;

public class PShipmentDetailPageDto extends PagingDto<PShipmentDetailDto> {

    /**
     * Variables Name: serialVersionUID
     * Description: TODO
     * Value Description: TODO
     */
    private static final long serialVersionUID = 8992860924220504490L;

    private String            orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
