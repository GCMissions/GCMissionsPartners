package com.hengtiansoft.business.wrkd.order.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hengtiansoft.business.order.dto.OrderDetailDto;
import com.hengtiansoft.business.order.dto.OrderDetailSearchDto;
import com.hengtiansoft.business.order.dto.OrderManagerListSearchDto;
import com.hengtiansoft.business.order.dto.OrderReturnDto;
import com.hengtiansoft.business.wrkd.order.dto.KdOrderManagerExportSearchDto;
import com.hengtiansoft.business.wrkd.order.dto.KdOrderRemarkDto;
import com.hengtiansoft.business.wrkd.order.dto.KdOrderReturnDto;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.wrw.entity.KdOrderMainEntity;
import com.hengtiansoft.wrw.entity.SUserEntity;

/**
 * 
 * Class Name: OrderManagerService Description: 订单管理service
 * 
 * @author kangruan
 *
 */
public interface KdOrderManagerService {

    /**
     * 
     * Description: 获取订单详情列表
     *
     * @param listDto
     */
    List<OrderDetailDto> findOrderDetails(OrderDetailSearchDto listDto);

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

    void exportSearch(KdOrderManagerExportSearchDto dto);

    HSSFWorkbook toExcle(KdOrderManagerExportSearchDto pageDto);
    
    KdOrderReturnDto getOrderDetail(String orderId);
    
    List<KdOrderRemarkDto> getRemarks(String orderId);
    
    KdOrderMainEntity findEntityByOrderId(String orderId);

    String changeActualAmount(OrderReturnDto dto);

    String cancelOrder(OrderReturnDto dto);

    String addRemark(OrderReturnDto dto);
    
    /**
     * 
    * Description: 操作订单时保存备注和操作记录
    *
    * @param dto
    * @param user
     */
    void addRemarkAndOper(OrderReturnDto dto, SUserEntity user);
    
    String returnAmount(OrderReturnDto dto);
    
    /**
     * 
    * Description: 订单所有商品是否全部退款
    *
    * @param orderId
    * @return
     */
    Boolean isAllReturn(String orderId);
    
    /**
     * 
    * Description: 确认发货
    *
    * @param orderId
    * @return
     */
    ResultDto<?> delivery(String orderId, String expressInfo);
    
    /**
     * 释放库存
    * Description: TODO
    *
    * @param productId
    * @param count
     */
    void addStockCount(Long productId, Integer count, Long userId, String specGroup, String orderType);
}
