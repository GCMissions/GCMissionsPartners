package com.hengtiansoft.business.shopStock.zStock.service;

import java.util.List;

import com.hengtiansoft.business.shopStock.zStock.dto.ShipmentDto;
import com.hengtiansoft.business.shopStock.zStock.dto.ShipmentSearchDto;

public interface ShipmentService {

    void searchShipment(ShipmentSearchDto infoDto);

    ShipmentDto findShipment(String id);

//    void sendGoods(String id);

//    void getGoods(String id);

    Integer getNoShipmentCount();

    void confirmGetGoods(String id);

    List<List<String>> findAllOrder(String orderId);


}
