package com.hengtiansoft.business.service;

import java.util.Map;

import com.hengtiansoft.business.dto.ShipmentFeeDto;

/**
 * Discription: 运费相关service
 * 
 * @author huizhuang
 */
public interface FreightService {
    
    ShipmentFeeDto getActualShipAmount(Map<Long, Integer> productNumMap);
    
    ShipmentFeeDto getActualShipAmount(Long addressId, Map<Long, Integer> productNumMap);
}
