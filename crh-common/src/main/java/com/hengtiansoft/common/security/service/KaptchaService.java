package com.hengtiansoft.common.security.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.hengtiansoft.common.xmemcached.constant.CacheType;

@Repository
public class KaptchaService {

    @Cacheable(value = CacheType.MEMCACHED_FOR_SMS, key = "#key + '_capString'")
    public String saveKaptchToMemcache(String capString, String key) {
        return capString;
    }

    @CacheEvict(value = CacheType.MEMCACHED_FOR_SMS, key = "#key + '_capString'")
    public void cleanKaptchCache(String key) {
    }

}
