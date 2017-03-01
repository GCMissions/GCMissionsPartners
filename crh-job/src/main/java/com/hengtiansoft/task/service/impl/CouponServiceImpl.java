package com.hengtiansoft.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.task.repository.IMCouponRepository;
import com.hengtiansoft.task.service.CouponService;
import com.hengtiansoft.wrw.dao.SCouponConfigDao;
import com.hengtiansoft.wrw.enums.CouponState;
import com.hengtiansoft.wrw.enums.StatusEnum;
/**
 * 
* Class Name: CouponServiceImpl
* Description: 优惠券业务实现类
* @author chengchaoyin
*
 */
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private IMCouponRepository iMCouponRepository;
    
    @Autowired
    private SCouponConfigDao sCouponConfigDao;
    
    @Override
    @Transactional(value = "jpaTransactionManager")
    public void updateCouponStatusByTime() {
        iMCouponRepository.updateCouponStatus(CouponState.EXPIRED.getKey());
        sCouponConfigDao.updateCouponConfigStatus(StatusEnum.DELETE.getCode());
    }


}
