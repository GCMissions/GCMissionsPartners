package com.hengtiansoft.business.marketing.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hengtiansoft.business.marketing.dto.CouponDto;
import com.hengtiansoft.business.marketing.dto.CouponPageDto;
import com.hengtiansoft.business.marketing.dto.CouponRecordPageDto;
import com.hengtiansoft.business.marketing.dto.SaveCouponDto;
import com.hengtiansoft.business.order.dto.SpecInfoDto;
import com.hengtiansoft.common.dto.ResultDto;

public interface CouponService {

    /**
     * Description: 新建/修改优惠券信息
     */
    ResultDto<String> saveCoupon(SaveCouponDto dto);

    /**
     * Description: 分页获取优惠券列表
     */
    void getCouponList(CouponPageDto dto);

    /**
     * Description: 根据优惠券ID获得优惠券详情
     *
     * @param CouponId
     */
    CouponDto getCoupon(Long CouponId);

    /**
     * Description: 删除优惠券
     *
     * @param couponId
     * @return
     */
    ResultDto<String> delete(Long couponId);

    /**
     * Description: 查找领取记录
     *
     * @param couponDto
     */
    void recordList(CouponRecordPageDto couponDto);

    /**
     * Description: 查询使用的优惠券数量
     *
     * @param couponId
     * @return
     */
    Long getUserNum(Long couponId);

    List<String> getProduct(Long couponId);

    /**
     * Description: 查询合作券的
     *
     * @param couponId
     * @return
     */

    ResultDto<List<String>> couponCodeList(Long couponId);
    
    /**
     * 
    * Description: 查询优惠券详情
    *
    * @param couponIds
    * @return
    * @author chengchaoyin
     */
    List<CouponDto> findCouponByCouponIds(List<Long> couponIds);
    
    /**
     * 
    * Description: 发送优惠券
    *
    * @param dto
    * @return
    * @author chengchaoyin
     */
    ResultDto<Boolean> sendCoupon(SaveCouponDto dto);
    
    /**
     * 
    * Description: 优惠券管理时变更状态
    *
    * @param dto
    * @return
    * @author chengchaoyin
     */
    ResultDto<Boolean> updateCouponStatus(SaveCouponDto dto);
    
    /**
     * 
    * Description: 批量导入用户列表，用户发送优惠券
    *
    * @param inputStream
    * @return
    * @author chengchaoyin
     */
    ResultDto<Map<String,Object>> importExcel(InputStream inputStream,SaveCouponDto couponDto);
    
    /**
     * 
    * Description: 导出下载模板
    *
    * @return
    * @author chengchaoyin
     */
    HSSFWorkbook toExcel();
    
    /**
     *  
    * Description: 获取商品某一个规格的价格信息
    *
    * @param productDto
    * @return
    * @author chengchaoyin
     */
    Map<String,Object> getProductPrice(Long productId,List<SpecInfoDto> specInfo,String actDate);

}
