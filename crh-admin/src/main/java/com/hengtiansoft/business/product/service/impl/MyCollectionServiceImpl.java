package com.hengtiansoft.business.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.product.service.MyCollectionService;
import com.hengtiansoft.wrw.dao.CoolbagCollectDao;

@Service
public class MyCollectionServiceImpl implements MyCollectionService {

    @Autowired
    private CoolbagCollectDao coolbagCollectDao;
    
    @Override
    @Transactional
    public void updateMyCollection(Long productId) {
        coolbagCollectDao.updateCollect(productId);
    }

}
