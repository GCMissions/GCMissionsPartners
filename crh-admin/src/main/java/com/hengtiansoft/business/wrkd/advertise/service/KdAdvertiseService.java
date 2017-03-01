package com.hengtiansoft.business.wrkd.advertise.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hengtiansoft.business.wrkd.advertise.dto.ActSearchDto;
import com.hengtiansoft.business.wrkd.advertise.dto.FeatureSearchDto;
import com.hengtiansoft.business.wrkd.advertise.dto.KdAdvertiseAddDto;
import com.hengtiansoft.business.wrkd.advertise.dto.KdAdvertiseSearchDto;
import com.hengtiansoft.business.wrkd.advertise.dto.KdEmptyDto;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.wrw.entity.CoolbagImageEntity;

public interface KdAdvertiseService {

    void searchAdvertise(KdAdvertiseSearchDto dto);
    
    /**
     * 
     * Description: 导出列表
     *
     * @param pageDto
     */

    void exportSearch(KdAdvertiseSearchDto dto);

    HSSFWorkbook toExcle(KdAdvertiseSearchDto dto);
    
    ResultDto<?> addOrUpdateAdvertise(KdAdvertiseAddDto dto);
    
    void searchFeatureList(FeatureSearchDto dto);
    
    void searchActList(ActSearchDto dto);
    
    /**
     * 
    * Description: TODO
    *
    * @param advertiseId
    *       广告id
    * @param adType
    *       广告模式
    * @return
     */
    List<KdAdvertiseAddDto> getAdvertiseList(Long advertiseId, String adType);
    
    /**
     * 
    * Description: 删除广告位
    *
    * @param advertiseId
    * @return
     */
    ResultDto<?> deleteAdvertise(Long advertiseId);
    
    List<KdAdvertiseAddDto> getAdvertiseOneList();
    
    void getAdvertiseTwoList(KdEmptyDto dto);
    
    List<CoolbagImageEntity> findAllTag();
    
    String getSwitchStatus(String type, String status);
    
    ResultDto<?> switchStatus(String openType, String closeType, String index);
    
    /**
     * 
    * Description: 删除专享活动时，变更广告位状态为删除
    *
    * @param actType
    * @param actIds
    * @return
    * @author chengchaoyin
     */
    ResultDto<?> updateAdvertiseStatus(String actType,List<Long> actIds);
    
    /**
     * 
    * Description: 删除商品时，变更广告位状态为删除
    *
    * @param productIds
    * @return
    * @author chengchaoyin
     */
    ResultDto<?> updateAdvertiseStatus(List<Long> productIds);
}
