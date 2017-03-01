package com.hengtiansoft.task.service.impl;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.hengtiansoft.common.util.SpringUtils;
import com.hengtiansoft.common.xmemcached.constant.CacheType;
import com.hengtiansoft.task.service.PayNoticeService;
import com.hengtiansoft.task.util.SpringUtil;

/**
 * 
 * Class Name: OrderNoticeServiceImpl Description: 商家时候有新订单的提醒
 * 
 * @author yigesong
 *
 */
@Service
public class PayNoticeServiceImpl implements PayNoticeService {

    @Override
    public void newOrderNoticeByOrg(Long orgId, String orderId) {
        CacheManager cacheManager = (CacheManager) SpringUtil.getBean("cacheManager");
        Cache cache = cacheManager.getCache(CacheType.ORDERCACHE);
        cache.put(orgId + "_S_ORG_ID", orderId);
    }

    @Override
    public boolean getOrderNotice(Long orgId) {
        CacheManager cacheManager = (CacheManager) SpringUtils.getBean("cacheManager");
        Cache cache = cacheManager.getCache(CacheType.APPLICATIONAL);
        ValueWrapper cOrderIdWrapper = cache.get(orgId + "_C_ORG_ID");
        ValueWrapper sOrderIdWrapper = cache.get(orgId + "_S_ORG_ID");
        // 如果#orgId_S_ORG_ID缓存中没有订单id
        if (sOrderIdWrapper == null || sOrderIdWrapper.get() == null) {
            return false;
        } else {
            boolean noNewOrder = cOrderIdWrapper != null && cOrderIdWrapper.get() != null
                    && cOrderIdWrapper.get().equals(sOrderIdWrapper.get());
            // 如果有新订单，则更新#orgId_C_ORG_ID缓存
            if (!noNewOrder) {
                cacheManager.getCache(CacheType.APPLICATIONAL).put(orgId + "_S_ORG_ID", (Long) sOrderIdWrapper.get());
            }
            return !noNewOrder;
        }
    }

}
