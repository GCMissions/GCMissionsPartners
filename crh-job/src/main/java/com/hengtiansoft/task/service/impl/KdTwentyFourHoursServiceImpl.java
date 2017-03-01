package com.hengtiansoft.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.task.repository.KdTfRecordRepository;
import com.hengtiansoft.task.repository.TwentyFourHoursRepository;
import com.hengtiansoft.task.service.KdTwentyFourHoursService;

/**
 * 
 * Class Name: KdTwentyFourHoursServiceImpl Description: TODO
 * 
 * @author chengchaoyin
 *
 */
@Service
public class KdTwentyFourHoursServiceImpl implements KdTwentyFourHoursService {

    @Autowired
    private TwentyFourHoursRepository twentyFourHoursRepository;

    @Autowired
    private KdTfRecordRepository kdTfRecordRepository;

    @Override
    @Transactional(value = "jpaTransactionManager")
    public void updateActStatusByTime() {

        twentyFourHoursRepository.updateBargainProductInStatus();

        twentyFourHoursRepository.updateBargainProductOutStatus();

    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public void updateReturnType() {
        kdTfRecordRepository.updateReturnType();
    }

}
