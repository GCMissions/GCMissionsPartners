package com.hengtiansoft.business.wrkd.activity.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hengtiansoft.business.wrkd.activity.dto.KdBargainAllSpecInfo;
import com.hengtiansoft.business.wrkd.activity.dto.KdBargainPageDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdBargainSaveAndEditDto;
import com.hengtiansoft.business.wrkd.activity.dto.TwentyfourInfo;
import com.hengtiansoft.common.dto.ResultDto;


/**
 * 
* Class Name: KdTwentyFourHoursService
* Description: 24小时业务接口
* @author chengchaoyin
*
 */
public interface KdTwentyFourHoursService {

    /**
     * 
    * Description: 24小时活动列表查询
    *
    * @param dto
    * @author chengchaoyin
     */
    void search(KdBargainPageDto dto);
    
    /**
     * 
    * Description: 24小时活动逻辑删除
    *
    * @param id
    * @author chengchaoyin
     */
    ResultDto<String> delete(String ids);
    
    /**
     * 
    * Description: 添加24小时活动
    *
    * @param dto
    * @author chengchaoyin
     */
    void save(KdBargainSaveAndEditDto dto);
    
    /**
     * 
    * Description: 查询24th详情
    *
    * @param id
    * @return
    * @author chengchaoyin
     */
    KdBargainSaveAndEditDto findById(Long id);
    
    /**
     * Description: 查询24th,关联商品的规格信息
     *
     * @param productId
     * @return
     */
    KdBargainAllSpecInfo findSpecInfo(Long tfId);
    
    /**
     * Description: 24活动详情
     *
     * @param twentyfourID
     * @return
     */
     TwentyfourInfo getTwentyfourInfo(Long twentyfourID, HttpServletRequest request, HttpServletResponse response);
     
     /**
      * 
     * Description: 查询商品相关联的24th活动（进行中）
     *
     * @param productId
     * @return
     * @author chengchaoyin
      */
     Boolean isTfBuyProduct(Long productId);
     
     /**
      * 
     * Description: 通过商品id,上下架时间查询是否有重叠的活动
     *
     * @param productId
     * @param onTime
     * @param offTime
     * @return
     * @author chengchaoyin
      */
     Integer getTfByProductIdAndTime(Long productId,String onTime,String offTime,Long tfId);
     
     void saleOffUpdateStatus(Long productId);
}
