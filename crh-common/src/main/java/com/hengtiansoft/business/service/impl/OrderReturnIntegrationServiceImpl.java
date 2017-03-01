/*
 * Project Name: wrw-common
 * File Name: OrderReturnService.java
 * Class Name: OrderReturnService
 *
 * Copyright 2014 Hengtian Software Inc
 *
 * Licensed under the Hengtiansoft
 *
 * http://www.hengtiansoft.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.service.OrderReturnIntegrationService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.sequence.SequenceGenerator;
import com.hengtiansoft.common.sequence.SequenceType;
import com.hengtiansoft.wrw.dao.MOrderMainDao;
import com.hengtiansoft.wrw.dao.MOrderReturnDao;
import com.hengtiansoft.wrw.dao.MPaymentLogDao;
import com.hengtiansoft.wrw.dao.MPointDetailDao;
import com.hengtiansoft.wrw.entity.MOrderMainEntity;
import com.hengtiansoft.wrw.entity.MOrderReturnEntity;
import com.hengtiansoft.wrw.entity.MPaymentLogEntity;
import com.hengtiansoft.wrw.entity.MPointDetailEntity;
import com.hengtiansoft.wrw.enums.OrderStatus;
import com.hengtiansoft.wrw.enums.PaymentType;
import com.hengtiansoft.wrw.enums.ReturnOrderStatusEnum;
import com.hengtiansoft.wrw.enums.ReturnOrderTypeEnum;

/**
* Class Name: OrderReturnIntegrationService
* Description: 退货管理
* @author xianghuang
*
*/
@Service
public class OrderReturnIntegrationServiceImpl implements OrderReturnIntegrationService{
    
    @Autowired
    private MOrderReturnDao orderReturnDao;
    
    @Autowired
    private MOrderMainDao orderMainDao;
    
    @Autowired
    private MPointDetailDao pointDetailDao;
    
    @Autowired
    private MPaymentLogDao paymentLogDao;
    
    @Autowired
    private SequenceGenerator    sequenceGenerator;
    
    /**
     * Description: 申请退货
     *
     * @param orderId
     * @return
     */
    @Override
    public  ResultDto<String> applyForReturnOrder(String orderId){
        MOrderMainEntity orderMainEntity=orderMainDao.findOne(orderId);
        if(orderMainEntity!=null){
            String orderStatus=orderMainEntity.getStatus();
            if(OrderStatus.WATI_RATE.getKey().equals(orderStatus)||OrderStatus.COMPLETE.getKey().equals(orderStatus)){
                createReturnOrder(orderId,ReturnOrderStatusEnum.WAIT_RETURNGOODS.getKey());
                return ResultDtoFactory.toAck("OK", "申请退货成功");
            }else{
                throw new WRWException("该订单状态不能申请退货！"); 
            }
        }else{
            throw new WRWException("系统异常请联系管理员！");
        }
    }
    
    /**
     * Description: 创建退货单
     *
     * @param orderId
     * @param returnOrderStatus
     */
    @Override
    public void createReturnOrder(String orderId,String returnOrderStatus){
        MOrderMainEntity orderMainEntity= orderMainDao.findOne(orderId);
        
        MOrderReturnEntity returnEntity=orderReturnDao.findByOrderMainId(orderId);
        if(returnEntity!=null){
            throw new WRWException("该退货单已经存在，不能创建！");
        }
        
        if(orderMainEntity==null){
            throw new WRWException("该订单编号不存在，操作失败！");
        }else{
            String orderStatus=orderMainEntity.getStatus();
            MOrderReturnEntity orderReturnEnity=new MOrderReturnEntity();
            String returnId = sequenceGenerator.getOrderId(SequenceType.M_RETURN);
            orderReturnEnity.setReturnId(returnId);
            orderReturnEnity.setOrderMainId(orderId);
            orderReturnEnity.setOrderStatus(orderStatus);

            orderReturnEnity.setReturnAmount(orderMainEntity.getActualAmount() - (orderMainEntity.getShipAmount() == null ? 0L : orderMainEntity.getShipAmount()));//商品退款金额

            orderReturnEnity.setReturnCouponAmount(orderMainEntity.getCouponAmount());
            
            /*1.C端用户申请退货创建退货单
            2.C端用户申请退款创建退货单
            3.C端用户取消订单创建退货单(Z一旦接单，运费不退)*/ 
            if(OrderStatus.WAIT_START.getKey().equals(orderStatus)||OrderStatus.WAIT_START.getKey().equals(orderStatus)){
                orderReturnEnity.setReturnShipAmount(orderMainEntity.getShipAmount());
            }else{
                orderReturnEnity.setReturnShipAmount(0L);
            }
            
            List<MPointDetailEntity> listPoints=pointDetailDao.findByOrderId(orderId);
            if(CollectionUtils.isNotEmpty(listPoints)){
                orderReturnEnity.setReturnPoint(String.valueOf(listPoints.get(0).getChangeValue()));
            }
            
            orderReturnEnity.setCreateDate(new Date());
            orderReturnEnity.setCreateId(AuthorityContext.getCurrentUser().getUserId());
            orderReturnEnity.setReturnStatus(Integer.valueOf(returnOrderStatus));
            
            String returnOrderType=ReturnOrderTypeEnum.NORECEIVE__RETURNGOODS.getKey();
            if(ReturnOrderStatusEnum.WAIT_RETURNGOODS.getKey().equals(returnOrderStatus)){//待退货
                //Z发货，C未收货
                if(orderStatus.equals(OrderStatus.WAIT_START.getKey())){
                    returnOrderType=ReturnOrderTypeEnum.NORECEIVE__RETURNGOODS.getKey();
                }else{
                    //Z发货，C已收货
                    returnOrderType=ReturnOrderTypeEnum.RECEIVED__RETURNGOODS.getKey();
                }
                orderStatus=OrderStatus.WAIT_START.getKey();
            }else if(ReturnOrderStatusEnum.WAIT_RETURNMONEY.getKey().equals(returnOrderStatus)){//待退款
                if(OrderStatus.WAIT_START.getKey().equals(orderStatus)||OrderStatus.WAIT_START.getKey().equals(orderStatus)){
                    returnOrderType=ReturnOrderTypeEnum.CANCELED_RETURNMONEY.getKey();
                }
//                orderStatus=OrderStatus.WAIT_RETURNMONEY.getKey();
            }
            
//            if(PaymentType.ALIPAY.getKey().equals(orderMainEntity.getPaymentType())){
//                MPaymentLogEntity log=paymentLogDao.findByOrderId(orderId);
//                if(log!=null){
//                    orderReturnEnity.setPayAccount(log.getPayAccount());
//                }
//            }
            
            orderReturnEnity.setReturnType(Integer.valueOf(returnOrderType));
            orderReturnDao.save(orderReturnEnity);
            
            orderMainEntity.setStatus(orderStatus);
            orderMainEntity.setModifyDate(new Date());
            orderMainEntity.setModifyId(AuthorityContext.getCurrentUser().getUserId());
            orderMainDao.save(orderMainEntity);
        }
    }
    
    /**
     * Description: 申请退款
     *
     * @param orderId
     * @return
     */
    @Override
    public ResultDto<String> applyForReturnAmount(String orderId){
        MOrderMainEntity orderMainEntity= orderMainDao.findOne(orderId);
        if(orderMainEntity==null){
            throw new WRWException("该订单编号不存在，操作失败！");
        }else{
            String orderStatus=orderMainEntity.getStatus();
            if(OrderStatus.WAIT_START.getKey().equals(orderStatus)||OrderStatus.WAIT_START.getKey().equals(orderStatus)){
                //直接申请退款，创建退货单
                createReturnOrder(orderId,ReturnOrderStatusEnum.WAIT_RETURNMONEY.getKey());               
                
                return ResultDtoFactory.toAck("OK", "订单申请退款成功！");
            }else if(OrderStatus.WAIT_START.getKey().equals(orderStatus)){
                createReturnOrder(orderId,ReturnOrderStatusEnum.WAIT_RETURNGOODS.getKey());
                return ResultDtoFactory.toAck("OK", "订单申请退款成功！");
            }else{
                throw new WRWException("该订单不能申请退款！");
            }
        }
    }

}
