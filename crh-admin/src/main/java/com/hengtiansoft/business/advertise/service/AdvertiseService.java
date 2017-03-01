package com.hengtiansoft.business.advertise.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;

import com.hengtiansoft.business.activity.dto.ActivityCopyPageDto;
import com.hengtiansoft.business.advertise.dto.AddAdDto;
import com.hengtiansoft.business.advertise.dto.AdvertiseDto;
import com.hengtiansoft.business.advertise.dto.SearchPurchaseDto;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.xmemcached.constant.CacheType;
import com.hengtiansoft.wrw.entity.SAdEntity;
/**
 * 
* Class Name: AdvertiseService
* Description: 广告管理
* @author yiminli
*
 */
public interface AdvertiseService {
    
    List<AdvertiseDto> getAdvertise(String adType);
    
    SAdEntity updateOrInsert(AddAdDto addAdDto);
    
    Integer deleteAd(Long adId, Integer sort);
    
    void findPurchase(SearchPurchaseDto dto);
    
    ResultDto<?> upOrDownAd(Long adId, Integer sort);
    
    SAdEntity findByTypeAndSort(String type, Integer sort);
    
    void search(ActivityCopyPageDto dto);
    
    ResultDto<?> editorDesc(Long adId, String description, String url);
    
    /**
     * 
    * Description: 清除乐园热门推荐的缓存数据
    *
    * @author chengchaoyin
     */
    @CacheEvict(value = CacheType.WELYHOTCACHE, key = "'findHotList'")
    void clearHotCache();
    
    /**
     * 
     * Description: 清除乐园轮播位的缓存数据
     *
     * @author chengchaoyin
     */
    @CacheEvict(value = CacheType.WELY_AD_CACHE, key = "'findLbAds'")
    void clearAdCache();
}
