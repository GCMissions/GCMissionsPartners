package com.hengtiansoft.task.service;

import java.util.Map;

public interface AppStatisticsService {

    void statisticsDeal();
    
    Map<Long, Long> getProductOrderCount();
}
