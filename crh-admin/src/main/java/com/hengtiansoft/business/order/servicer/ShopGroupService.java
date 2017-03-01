package com.hengtiansoft.business.order.servicer;

import com.hengtiansoft.business.order.dto.ShopGroupPageDto;

/**
 * 
 * Class Name: ShopGroupService
 * Description: 团购订单 service
 * @author kangruan
 *
 */
public interface ShopGroupService {

    /**
     * 
     * Description: 根据条件进行分页查询
     *
     * @param pageDto
     */
    void findPage(ShopGroupPageDto pageDto);
    
    /**
     * 
     * Description: 处理团购订单
     *
     * @param groupId
     */
    void dealOrder(Long groupId);
}
