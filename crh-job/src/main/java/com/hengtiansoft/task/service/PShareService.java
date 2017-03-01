package com.hengtiansoft.task.service;

import java.util.Map;

/**
 * 
* Class Name: PShareService
* Description: 商品分享业务接口
* @author chengchaoyin
*
 */
public interface PShareService {

    void pShareDeal();
    
    /**
     * 
    * Description: 查询前平台商品对应的订单数量（历史数据）
    *
    * @return
    * @author chengchaoyin
     */
    Map<Long,Long> getProductOrderCount();
}
