package com.hengtiansoft.business.order.dto;

import java.util.List;

import com.hengtiansoft.wrw.entity.MOrderFeedbackEntity;

public class OrderInfoDto {

    // 订单详情
    private OrderManagerDto order;

    // 订单明细集合
    private List<OrderDetailDto> details;

    // 订单配送信息
    // private SShipmentOrderEntity shipmentOrder;

    // 订单评价
    private MOrderFeedbackEntity feedback;
    
    
    private String  idCard;

    public OrderManagerDto getOrder() {
        return order;
    }

    public void setOrder(OrderManagerDto order) {
        this.order = order;
    }

    public List<OrderDetailDto> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetailDto> details) {
        this.details = details;
    }

    public MOrderFeedbackEntity getFeedback() {
        return feedback;
    }

    public void setFeedback(MOrderFeedbackEntity feedback) {
        this.feedback = feedback;
    }

    public String getIdCard() {
      return idCard;
    }

    public void setIdCard(String idCard) {
      this.idCard = idCard;
    }
    
    

}
