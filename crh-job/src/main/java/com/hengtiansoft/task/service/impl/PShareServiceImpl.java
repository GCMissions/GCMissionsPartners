package com.hengtiansoft.task.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.task.service.PShareService;
import com.hengtiansoft.wrw.dao.MOrderDetailDao;
import com.hengtiansoft.wrw.dao.POrderHistoryDao;
import com.hengtiansoft.wrw.dao.PShareDao;
import com.hengtiansoft.wrw.dao.ProductClickRefDao;
import com.hengtiansoft.wrw.dao.ProductSortParamDao;
/**
 * 
* Class Name: PShareServiceImpl
* Description: 商品分享业务实现
* @author chengchaoyin
*
 */
@Service
public class PShareServiceImpl implements PShareService {

    @Autowired
    private PShareDao pShareDao;
    
    @Autowired
    private ProductSortParamDao productSortParamDao;
    
    @Autowired
    private MOrderDetailDao mOrderDetailDao;
    
    @Autowired
    private ProductClickRefDao productClickRefDao;
    
    @Autowired
    private POrderHistoryDao pOrderHistoryDao;
    
    @Override
    @Transactional("jpaTransactionManager")
    public void pShareDeal() {
        //1.商品分享，点击统计
        List<Object[]> objects = pShareDao.findRecordsByProductId();
        if(!CollectionUtils.isEmpty(objects)){
            for (Object[] object : objects) {
                Long productId = WRWUtil.objToLong(object[0]);
                Long shareCnt = WRWUtil.objToLong(object[1]);
                productSortParamDao.updateShareCount(productId, shareCnt);
            }
        }
        
        // 2.商品销量统计
        List<Object[]> mOrderDetails =  mOrderDetailDao.getProductSaleCount();
        if(!CollectionUtils.isEmpty(mOrderDetails)){
            Map<Long, Long> map = getProductOrderCount();
            for (Object[] objects2 : mOrderDetails) {
                Long productId = WRWUtil.objToLong(objects2[0]);
                Long saleCnt = WRWUtil.objToLong(objects2[1]);
                if(map.containsKey(productId)){
                    saleCnt = saleCnt + map.get(productId);
                }
                productSortParamDao.updateSaleCount(productId, saleCnt);
            }
        }
        
        // 3.商品点击量排序
        List<Object[]> pClicks = productClickRefDao.getProductClickCount();
        if(!CollectionUtils.isEmpty(pClicks)){ 
            for (Object[] object : pClicks) {
                Long productId = WRWUtil.objToLong(object[0]);
                Long clickCnt = WRWUtil.objToLong(object[1]);
                productSortParamDao.updateClickCount(productId, clickCnt);
            }
        }
        
    }

    @Override
    public Map<Long, Long> getProductOrderCount() {
        List<Object[]> list = pOrderHistoryDao.findProductOrderCount();
        Map<Long, Long> map = new HashMap<Long, Long>();
        if(!CollectionUtils.isEmpty(list)){
            for (Object[] objects : list) {
                Long productId = Long.valueOf(objects[0].toString());
                Long orderCount = Long.valueOf(objects[1].toString());
                if(!map.containsKey(productId)){
                    map.put(productId, orderCount);
                }
            }
            return map;
        }
        return null;
    }

}
