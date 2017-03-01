package com.hengtiansoft.task.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.task.service.AppStatisticsService;
import com.hengtiansoft.wrw.dao.AppKdPClickRefDao;
import com.hengtiansoft.wrw.dao.AppKdPShareDao;
import com.hengtiansoft.wrw.dao.AppLyPClickRefDao;
import com.hengtiansoft.wrw.dao.AppLyPShareDao;
import com.hengtiansoft.wrw.dao.AppProductSortParamDao;
import com.hengtiansoft.wrw.dao.KdOrderDetailDao;
import com.hengtiansoft.wrw.dao.MOrderDetailDao;
import com.hengtiansoft.wrw.dao.POrderHistoryDao;
import com.hengtiansoft.wrw.enums.AppProductTypeEnum;

@Service
public class AppStatisticsServiceImpl implements AppStatisticsService {
    
    @Autowired
    private AppLyPShareDao appLyPShareDao;
    @Autowired
    private AppKdPShareDao appKdPShareDao;
    @Autowired
    private AppLyPClickRefDao appLyPClickRefDao;
    @Autowired
    private AppKdPClickRefDao appKdPClickRefDao;
    @Autowired
    private AppProductSortParamDao appProductSortParamDao;
    @Autowired
    private MOrderDetailDao mOrderDetailDao;
    @Autowired
    private KdOrderDetailDao kdOrderDetailDao;
    @Autowired
    private POrderHistoryDao pOrderHistoryDao;

    @Override
    @Transactional
    public void statisticsDeal() {
        //1.商品分享，点击统计
        List<Object[]> lyObjects = appLyPShareDao.findRecordsByProductId();
        List<Object[]> kdObjects = appKdPShareDao.findRecordsByProductId();
        if(!CollectionUtils.isEmpty(lyObjects)){
            for (Object[] object : lyObjects) {
                Long productId = WRWUtil.objToLong(object[0]);
                Long shareCnt = WRWUtil.objToLong(object[1]);
                if (appProductSortParamDao.findByProductIdAndType(productId, AppProductTypeEnum.LY_PRODUCT.getCode()).isEmpty()) {
                    appProductSortParamDao.saveRecord(productId, 0L, shareCnt, 0L, null, AppProductTypeEnum.LY_PRODUCT.getCode());
                } else {
                    appProductSortParamDao.updateShareCount(productId, shareCnt, AppProductTypeEnum.LY_PRODUCT.getCode());
                }
            }
        }
        if(!CollectionUtils.isEmpty(kdObjects)){
            for (Object[] object : kdObjects) {
                Long productId = WRWUtil.objToLong(object[0]);
                Long shareCnt = WRWUtil.objToLong(object[1]);
                if (appProductSortParamDao.findByProductIdAndType(productId, AppProductTypeEnum.KD_PRODUCT.getCode()).isEmpty()) {
                    appProductSortParamDao.saveRecord(productId, 0L, shareCnt, 0L, null, AppProductTypeEnum.KD_PRODUCT.getCode());
                } else {
                    appProductSortParamDao.updateShareCount(productId, shareCnt, AppProductTypeEnum.KD_PRODUCT.getCode());
                }
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
                if (appProductSortParamDao.findByProductIdAndType(productId, AppProductTypeEnum.LY_PRODUCT.getCode()).isEmpty()) {
                    appProductSortParamDao.saveRecord(productId, saleCnt, 0L, 0L, null, AppProductTypeEnum.LY_PRODUCT.getCode());
                } else {
                    appProductSortParamDao.updateSaleCount(productId, saleCnt, AppProductTypeEnum.LY_PRODUCT.getCode());
                }
            }
        }
        List<Object[]> kdOrderDetails = kdOrderDetailDao.getProductSaleCount();
        if(!CollectionUtils.isEmpty(kdOrderDetails)){
            for (Object[] objects : kdOrderDetails) {
                Long productId = WRWUtil.objToLong(objects[0]);
                Long saleCnt = WRWUtil.objToLong(objects[1]);
                if (appProductSortParamDao.findByProductIdAndType(productId, AppProductTypeEnum.KD_PRODUCT.getCode()).isEmpty()) {
                    appProductSortParamDao.saveRecord(productId, saleCnt, 0L, 0L, null, AppProductTypeEnum.KD_PRODUCT.getCode());
                } else {
                    appProductSortParamDao.updateSaleCount(productId, saleCnt, AppProductTypeEnum.KD_PRODUCT.getCode());
                }
            }
        }
        
        // 3.商品点击量排序
        List<Object[]> lyClicks = appLyPClickRefDao.getProductClickCount();
        if(!CollectionUtils.isEmpty(lyClicks)){ 
            for (Object[] object : lyClicks) {
                Long productId = WRWUtil.objToLong(object[0]);
                Long clickCnt = WRWUtil.objToLong(object[1]);
                if (appProductSortParamDao.findByProductIdAndType(productId, AppProductTypeEnum.LY_PRODUCT.getCode()).isEmpty()) {
                    appProductSortParamDao.saveRecord(productId, 0L, 0L, clickCnt, null, AppProductTypeEnum.LY_PRODUCT.getCode());
                } else {
                    appProductSortParamDao.updateClickCount(productId, clickCnt, AppProductTypeEnum.LY_PRODUCT.getCode());
                }
            }
        }
        List<Object[]> kdClicks = appKdPClickRefDao.getProductClickCount();
        if(!CollectionUtils.isEmpty(kdClicks)){ 
            for (Object[] object : kdClicks) {
                Long productId = WRWUtil.objToLong(object[0]);
                Long clickCnt = WRWUtil.objToLong(object[1]);
                if (appProductSortParamDao.findByProductIdAndType(productId, AppProductTypeEnum.KD_PRODUCT.getCode()).isEmpty()) {
                    appProductSortParamDao.saveRecord(productId, 0L, 0L, clickCnt, null, AppProductTypeEnum.KD_PRODUCT.getCode());
                } else {
                    appProductSortParamDao.updateClickCount(productId, clickCnt, AppProductTypeEnum.KD_PRODUCT.getCode());
                }
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
