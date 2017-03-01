package com.hengtiansoft.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.task.service.ShareStatisticsService;
import com.hengtiansoft.wrw.dao.PProductDao;
import com.hengtiansoft.wrw.dao.PShareDao;

@Service
public class ShareStatisticsServiceImpl implements ShareStatisticsService {
    
    @Autowired
    private PProductDao productDao;
    @Autowired
    private PShareDao shareDao;
    

    @Override
    public void statisticsShare() {
        // TODO Auto-generated method stub
        
    }

}
