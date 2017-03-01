package com.hengtiansoft.business.finance.dto;

import java.util.List;

/**
 * 
* Class Name: SOrderBalanceDetailDto
* Description: 报表详情
* @author chenghongtu
*
 */
public class SOrderBalanceDto {
    
    private String orderId;
    
    private List<SOrderBalanceDetailDto> detail;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<SOrderBalanceDetailDto> getDetail() {
        return detail;
    }

    public void setDetail(List<SOrderBalanceDetailDto> detail) {
        this.detail = detail;
    }
    
    
    
    
}
