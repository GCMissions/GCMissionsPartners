package com.hengtiansoft.business.order.servicer;

import com.hengtiansoft.business.shopStock.zStock.dto.OrderRewardSearchDto;

public interface PlatformPunishmentOrderService {

    /**
     * Description: 查询惩罚订单
     *
     * @param searchDto
     * @return
     */
    void search(OrderRewardSearchDto searchDto);

}
