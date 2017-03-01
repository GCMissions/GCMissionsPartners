package com.hengtiansoft.task.service.impl;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.hengtiansoft.common.xmemcached.constant.CacheType;
import com.hengtiansoft.task.service.SynchronizationCacheService;

@Service
public class SynchronizationCacheServiceImpl implements SynchronizationCacheService{

    
    @Override
    @CachePut(value = CacheType.MEMBER_CACHE, key = "'synchronizationMemberBegin'")
    public Long rememberBegin(Long begin) {
        return begin;
    }


    @Override
    @Cacheable(value = CacheType.MEMBER_CACHE, key = "'synchronizationMemberBegin'")
    public Long getBegin() {
        return 0L;
    }
    
}
