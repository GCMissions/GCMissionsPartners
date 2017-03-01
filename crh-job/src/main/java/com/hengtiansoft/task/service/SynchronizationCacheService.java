package com.hengtiansoft.task.service;

public interface SynchronizationCacheService {

    /**
    * Description: 记录同步到哪条数据
    *
    * @param begin
    * @return
    */
    Long rememberBegin(Long begin);
    
    
    /**
    * Description: 获取上次同步到哪里
    *
    * @return
    */
    Long getBegin();
    
}
