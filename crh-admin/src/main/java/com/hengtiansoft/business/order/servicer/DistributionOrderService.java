package com.hengtiansoft.business.order.servicer;

import java.util.List;

import com.hengtiansoft.business.order.dto.DistributionOrdeDto;
import com.hengtiansoft.business.order.dto.DistributionOrdeSearchDto;


public interface DistributionOrderService{

    void searchDistributionOrde(DistributionOrdeSearchDto infoDto);

    DistributionOrdeDto findDistributionOrder(String id);

    Integer getNewOrder();

    void sendGoods(String id);

    void getGoods(String id);

    Long getNewOrderRemind();

    List<List<String>> findAllOrder(String orderId);

}
