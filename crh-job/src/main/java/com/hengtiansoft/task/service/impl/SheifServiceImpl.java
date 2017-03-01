package com.hengtiansoft.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.task.service.SheifService;
import com.hengtiansoft.wrw.dao.PShiefDao;
import com.hengtiansoft.wrw.enums.SaleFlagEnum;

@Service
public class SheifServiceImpl implements SheifService {


    @Autowired
    private PShiefDao priceDao;
    
    @Override
    @Transactional("jpaTransactionManager")
    public void updateSheif() {
        priceDao.unShiefProduct(SaleFlagEnum.UNSHELF.getCode());
        priceDao.shiefProduct(SaleFlagEnum.SHELF.getCode());
    }

}
