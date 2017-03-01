package com.hengtiansoft.business.order.servicer;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hengtiansoft.business.order.dto.CouponDto;
import com.hengtiansoft.business.order.dto.CouponSearchDto;
import com.hengtiansoft.business.order.dto.LineOrderDetailDto;
import com.hengtiansoft.business.order.dto.LineOrderDto;
import com.hengtiansoft.business.order.dto.OrderDetailDto;
import com.hengtiansoft.business.order.dto.OrderDetailSearchDto;
import com.hengtiansoft.business.order.dto.OrderManagerExportSearchDto;
import com.hengtiansoft.business.order.dto.OrderManagerListSearchDto;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.wrw.entity.MMemberEntity;
import com.hengtiansoft.wrw.entity.SOrgEntity;

/**
 * 
 * Class Name: OrderManagerService Description: 订单管理service
 * 
 * @author kangruan
 *
 */
public interface OrderManagerService {

    /**
     * 
     * Description: 获取订单详情列表
     *
     * @param listDto
     */
    List<OrderDetailDto> findOrderDetails(OrderDetailSearchDto listDto);

    /**
     * 
     * Description: 获取优惠券列表
     *
     * @param listDto
     */
    List<CouponDto> findCoupons(CouponSearchDto listDto);

    /**
     * 
     * Description: 根据优惠券Id 删除用户优惠券
     *
     * @param couponId
     */
    void delCoupon(Long couponId);

    /**
     * 
     * Description: 获取服务商列表
     *
     * 
     */
    List<SOrgEntity> findOrgs();

    /**
     * 
     * Description: 获取订单列表
     *
     * @param dto
     */
    void search(OrderManagerListSearchDto dto);

    /**
     * 
     * Description: 导出列表
     *
     * @param pageDto
     */

    void exportSearch(OrderManagerExportSearchDto dto);

    HSSFWorkbook toExcle(OrderManagerExportSearchDto pageDto);
    
    String isOrg();
    
    /**
     * 
    * Description: 批量导入用户列表，用户发送优惠券
    *
    * @param inputStream
    * @return
    * @author chengchaoyin
     */
    ResultDto<String> importExcel(InputStream inputStream);
    
    /**
     * 
    * Description: 线下订单流程操作（生成用户并创建相应订单）
    *
    * @param lineOrderDto
    * @author chengchaoyin
     */
    ResultDto<String> lineOrderProcess(LineOrderDto lineOrderDto,Map<String, Object> map);
    
    /**
     * 
    * Description: 将线下订单生成线上订单
    *
    * @return
    * @author chengchaoyin
     */
    ResultDto<Boolean> createOnlineOrder(MMemberEntity mEntity,LineOrderDetailDto lineOrderDetailDto,LineOrderDto lineOrderDto,Map<String, Object> map);
    
    /**
     * 批量处理订单，更新状态
     * 
     * @param orderIds
     * @param status
     */
    void updateStatus(Long memberId,String status, String... orderIds);
    
    /**
     * 
    * Description: 订单完成之后赠送优惠券
    *
    * @param orderId
    * @param actualAmount
    * @param memberId
    * @author chengchaoyin
     */
    void giveCoupon(String orderId, Long actualAmount, Long memberId);
}
