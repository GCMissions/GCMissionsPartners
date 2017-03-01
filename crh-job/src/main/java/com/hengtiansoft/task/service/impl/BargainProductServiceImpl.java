package com.hengtiansoft.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.task.service.BargainProductService;
import com.hengtiansoft.wrw.dao.BargainProductDao;
/**
 * 
* Class Name: BargainProductServiceImpl
* Description: TODO
* @author chengchaoyin
*
 */
@Service
public class BargainProductServiceImpl implements BargainProductService{
    
    @Autowired
    private BargainProductDao bargainProductDao;
    
    @Override
    @Transactional(value = "jpaTransactionManager")
    public Integer updateBargainProductInStatus() {
        return bargainProductDao.updateBargainProductInStatus();
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public Integer updateBargainProductOutStatus() {
        return bargainProductDao.updateBargainProductOutStatus();
    }

}
