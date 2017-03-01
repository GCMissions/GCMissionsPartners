package com.hengtiansoft.business.shopStock.zStock.service;

import com.hengtiansoft.business.shopStock.zStock.dto.OrderRewardSearchDto;

public interface PunishmentOrderService {

    /**
     * Description: 查询惩罚订单
     *
     * @param searchDto
     * @return
     */
    void search(OrderRewardSearchDto searchDto);

}
