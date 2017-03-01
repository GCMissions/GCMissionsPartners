package com.hengtiansoft.business.order.servicer;

import java.util.List;

import com.hengtiansoft.business.order.dto.OrderDetailDto;

/**
 * 
 * Class Name: OrderDetailService
 * Description: 订单详情 service
 * @author kangruan
 *
 */
public interface OrderDetailService {

    /**
     * 
     * Description: 获取订单详情
     *
     * @param orderId 订单id
     * @return
     */
    List<OrderDetailDto> findByOrderId2Dto(String orderId);
    
}
