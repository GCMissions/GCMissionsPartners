/*
 * Project Name: wrw-common
 * File Name: OrderReturnServiceImpl.java
 * Class Name: OrderReturnServiceImpl
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
package com.hengtiansoft.business.order.servicer.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.common.service.SMSService;
import com.hengtiansoft.business.order.dto.OrderRemarkDto;
import com.hengtiansoft.business.order.dto.OrderReturnDto;
import com.hengtiansoft.business.order.dto.selectSpecDto;
import com.hengtiansoft.business.order.servicer.OrderReturnService;
import com.hengtiansoft.business.pay.dto.PaymentParamDto;
import com.hengtiansoft.business.pay.plugin.UnionPayPlugin;
import com.hengtiansoft.business.pay.service.LlpayRefundService;
import com.hengtiansoft.business.pay.service.WechatRefundService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.sequence.SequenceGenerator;
import com.hengtiansoft.common.sequence.SequenceType;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.ActivityConst.StockType;
import com.hengtiansoft.wrw.dao.ActivitySpecDao;
import com.hengtiansoft.wrw.dao.ActivityStockDao;
import com.hengtiansoft.wrw.dao.MAccountDao;
import com.hengtiansoft.wrw.dao.MAcctRecordDao;
import com.hengtiansoft.wrw.dao.MCouponDao;
import com.hengtiansoft.wrw.dao.MOrderDetailDao;
import com.hengtiansoft.wrw.dao.MOrderMainDao;
import com.hengtiansoft.wrw.dao.MOrderRemarksDao;
import com.hengtiansoft.wrw.dao.MOrderReturnDao;
import com.hengtiansoft.wrw.dao.MVipDao;
import com.hengtiansoft.wrw.dao.PProductDao;
import com.hengtiansoft.wrw.dao.SBasicParaDao;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.dao.SUserDao;
import com.hengtiansoft.wrw.entity.ActivitySpec;
import com.hengtiansoft.wrw.entity.ActivityStock;
import com.hengtiansoft.wrw.entity.MCouponEntity;
import com.hengtiansoft.wrw.entity.MOrderDetailEntity;
import com.hengtiansoft.wrw.entity.MOrderMainEntity;
import com.hengtiansoft.wrw.entity.MOrderRemarksEntity;
import com.hengtiansoft.wrw.entity.MOrderReturnEntity;
import com.hengtiansoft.wrw.entity.MVipOrderEntity;
import com.hengtiansoft.wrw.entity.PProductEntity;
import com.hengtiansoft.wrw.entity.SBasicParaEntity;
import com.hengtiansoft.wrw.entity.SOrgEntity;
import com.hengtiansoft.wrw.entity.SUserEntity;
import com.hengtiansoft.wrw.enums.CodeUsedEnum;
import com.hengtiansoft.wrw.enums.CouponState;
import com.hengtiansoft.wrw.enums.MessageModelEnum;
import com.hengtiansoft.wrw.enums.OrderStatus;
import com.hengtiansoft.wrw.enums.PaymentType;
import com.hengtiansoft.wrw.enums.ReturnOrderStatusEnum;
import com.hengtiansoft.wrw.enums.ReturnOrderTypeEnum;

/**
 * Class Name: OrderReturnServiceImpl Description:
 * 
 * @author
 *
 */
@Service
public class OrderReturnServiceImpl implements OrderReturnService {
    
    private static String RETURN_DETAIL = "是，%s%s元";
    
    private static final String VIP_PRICE = "VIP_PRICE";
    
    @Autowired
    private MOrderReturnDao orderReturnDao;

    @Autowired
    private MOrderRemarksDao orderRemarksDao;

    @Autowired
    private SUserDao sUserDao;

    @Autowired
    private MOrderMainDao orderMainDao;

    @Autowired
    private MAccountDao accountDao;

    @Autowired
    private MAcctRecordDao acctRecordDao;

    @Autowired
    private MCouponDao couponDao;

    @Autowired
    private MOrderDetailDao orderDetailDao;

    @Autowired
    private ActivityStockDao activityStockDao;

    @Autowired
    private ActivitySpecDao activitySpecDao;

    @Autowired
    private PProductDao pProductDao;

    @Autowired
    private SOrgDao sOrgDao;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private SMSService sMSService;

    @Autowired
    private WechatRefundService wechatRefundService;

    @Autowired
    private LlpayRefundService llpayRefundService;
    
    @Autowired
    private MVipDao mVipDao;
    
    @Autowired
    private SBasicParaDao sBasicParaDao;
    
    @Autowired
    private UnionPayPlugin unionPayPlugin;

    /**
     * Description: 确认退款
     *
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public String changeReturnOrderAmount(OrderReturnDto dto) {
        SUserEntity user = (SUserEntity) AuthorityContext.getCurrentUser();
        MOrderMainEntity orderMainEntity = orderMainDao.findOne(dto.getOrderId());
        if (orderMainEntity == null) {
            throw new WRWException("该订单编号不存在");
        }
        if (StringUtils.isEmpty(dto.getReturnOrderAmount())) {
            throw new WRWException("请输入退款金额");
        }
        
        Long vipPrice = 0L;
        List<MVipOrderEntity> vipOrderList = mVipDao.findByOrderMainId(dto.getOrderId());
        if (!vipOrderList.isEmpty()) {
            SBasicParaEntity data = sBasicParaDao.findByParaName(VIP_PRICE);
            if (data != null) {
                vipPrice = Long.parseLong(data.getParaValue1());
            }
        }
        Long reqReturnAmount = Double.valueOf(Double.valueOf(dto.getReturnOrderAmount()) * 100L).longValue();
        if (reqReturnAmount > (orderMainEntity.getActualAmount() - vipPrice)) {
            throw new WRWException("退款金额不能大于实付金额");
        }

        if (Double.valueOf(dto.getReturnOrderAmount()) <= 0D) {
            throw new WRWException("退款金额不能小于等于0元");
        }

        // 支持多次退款
        Long returnAmount = 0L;
        List<Object[]> returnEntity= orderReturnDao.findReturnDetail(dto.getOrderId());
        if (null != returnEntity && !returnEntity.isEmpty()) {
            Object[] data = returnEntity.get(0);
            returnAmount = (data[1] == null ? 0L : (BigDecimal)data[1]).longValue();
            if (returnAmount >= (orderMainEntity.getActualAmount() - vipPrice)) {
                throw new WRWException("该笔订单可退金额为0元");
            }
            if (reqReturnAmount > (orderMainEntity.getActualAmount() - vipPrice - returnAmount)) {
                throw new WRWException("超出该笔订单可退金额");
            }
        }
        
        // 根据支付类型选择退款方式
        String result = "";
        if (PaymentType.WECHAT.getKey().equals(orderMainEntity.getPaymentType())) {
            result = wechatRefundService.wechatRefund(String.valueOf(orderMainEntity.getActualAmount()),
                    String.valueOf(reqReturnAmount), orderMainEntity.getOrderId(), orderMainEntity.getTsn());

        } else if (PaymentType.LLPAY.getKey().equals(orderMainEntity.getPaymentType())) {
            result = llpayRefundService.llpayRefund(String.valueOf(reqReturnAmount), orderMainEntity.getOrderId(), orderMainEntity.getTsn());
        } else if (PaymentType.UNION.getKey().equals(orderMainEntity.getPaymentType())) {
            result = unionPayPlugin.refund(new PaymentParamDto(
                    orderMainEntity.getOrderId() + unionPayPlugin.getCurrentTime(), String.valueOf(reqReturnAmount), orderMainEntity.getTsn())).getCode();
        }
        
        // 退款申请不成功
        if(!"SUCCESS".equalsIgnoreCase(result)){
            throw new WRWException("退款失败，请稍后再试！");
        }
        
        String orderStatus = orderMainEntity.getStatus();
        if (OrderStatus.WATI_RATE.getKey().equals(orderStatus) || OrderStatus.WAIT_START.getKey().equals(orderStatus)
                || OrderStatus.COMPLETE_RETURNMONEY.getKey().equals(orderStatus)) {

            // 只有第一次退款才修改订单状态
            if (!OrderStatus.COMPLETE_RETURNMONEY.getKey().equals(orderStatus)) {
                orderMainEntity.setModifyId(user.getUserId());
                orderMainEntity.setModifyDate(new Date());
                orderMainEntity.setStatus(OrderStatus.COMPLETE_RETURNMONEY.getKey());
                orderMainDao.save(orderMainEntity);
            }

            MOrderReturnEntity orderReturnEnity = new MOrderReturnEntity();

            String returnId = sequenceGenerator.getOrderId(SequenceType.M_RETURN);
            orderReturnEnity.setReturnId(returnId);
            orderReturnEnity.setOrderMainId(orderMainEntity.getOrderId());
            orderReturnEnity.setReturnAmount(reqReturnAmount);
            orderReturnEnity.setOrderStatus(orderStatus);
            orderReturnEnity.setReturnType(Integer.valueOf(ReturnOrderTypeEnum.RETURNMONEY.getKey()));
            orderReturnEnity.setReturnStatus(Integer.valueOf(ReturnOrderStatusEnum.COMPLETE_RETURNMONEY.getKey()));
            orderReturnEnity.setCreateId(user.getUserId());
            orderReturnEnity.setCreateDate(new Date());

            orderReturnDao.save(orderReturnEnity);

            
            
            // 订单全额退款时，返回优惠券
            if (reqReturnAmount  == (orderMainEntity.getActualAmount() - returnAmount)) {
                couponDao.releaseCouponByReturnAllAmount(orderMainEntity.getOrderId());
            }
            
            List<MOrderDetailEntity> details = orderDetailDao.findByOrderId(orderMainEntity.getOrderId());
            if (details != null && !details.isEmpty()) {

                for (MOrderDetailEntity detail : details) {
                    String name = "";
                    detail.setCodeUsed(CodeUsedEnum.INVALID.getKey());
                    orderDetailDao.save(detail);
                    HashMap<String, String> dataMap = new HashMap<String, String>();
                    dataMap.put("amount", dto.getReturnOrderAmount());
                    dataMap.put("day", "5");

                    PProductEntity pEntity = pProductDao.findByProductId(detail.getProductId());
                    SOrgEntity sOrgEntity = sOrgDao.findOne(pEntity.getOrgId());
                    name += detail.getProductName() + ",";

                    name += DateTimeUtil.parseDateToString(detail.getActDate(), DateTimeUtil.CHINA_YMD) + ",";
                    String spcinfo = "";
                    JSONArray data = JSONArray.fromObject(detail.getSpecInfo());
                    List<selectSpecDto> plist = data.toList(data, selectSpecDto.class);
                    for (selectSpecDto sdto : plist) {
                        spcinfo += sdto.getSubSpec();
                    }
                    name += spcinfo;

                    dataMap.put("serviceName", sOrgEntity.getOrgName());
                    dataMap.put("productName", name);

                    sMSService.sendSMS(orderMainEntity.getPhone(), MessageModelEnum.sms_C_refundNotify, dataMap);
                    sMSService.sendSMS(sOrgEntity.getPhone(), MessageModelEnum.sms_C_refundNotify_org, dataMap);

                }
            }

        } else {
            throw new WRWException("该订单状态不可退款");
        }

        return "确认退款成功！";

    }

    @Override
    public OrderReturnDto getOrderDetail(String orderId) {
        MOrderMainEntity entity = orderMainDao.findOne(orderId);

        if (null == entity) {
            throw new WRWException("该订单编号不存在");
        }

        OrderReturnDto dto = ConverterService.convert(entity, OrderReturnDto.class);
        dto.setStatus(OrderStatus.getValue(entity.getStatus()));
        dto.setActualAmount(WRWUtil.transFenToYuan2String(entity.getActualAmount()));
        dto.setCouponAmount(WRWUtil.transFenToYuan2String(entity.getCouponAmount()));
        dto.setTotalAmount(WRWUtil.transFenToYuan2String(entity.getTotalAmount()));
        dto.setShipAmount(WRWUtil.transFenToYuan2String(entity.getShipAmount()));
        dto.setSource(entity.getSource());
        dto.setBuyVip("0");
        List<MVipOrderEntity> vipOrderList = mVipDao.findByOrderMainId(orderId);
        if (!vipOrderList.isEmpty()) {
            dto.setBuyVip("1");
        }
        if (OrderStatus.COMPLETE_RETURNMONEY.getKey().equals(entity.getStatus())) {
            // 返回类，{@link BigInteger, @link BigDecimal}
            Object[] data = orderReturnDao.findReturnDetail(orderId).get(0);
            dto.setReturnTimes((data[0] == null ? 0 : (BigInteger)data[0]).intValue());
            Long returnAmount = (data[1] == null ? 0L : (BigDecimal)data[1]).longValue();
            dto.setReturnable(returnAmount < entity.getActualAmount() ? "1" : "0");
            
            String returnDetail = String.format(RETURN_DETAIL, OrderStatus.COMPLETE_RETURNMONEY.getValue(), 
                    WRWUtil.transFenToYuan2String(returnAmount));
            dto.setReturnOrderAmount(returnDetail);
        }
        return dto;
    }

    @Override
    public List<OrderRemarkDto> getRemarks(String orderId) {
        List<OrderRemarkDto> dtos = new ArrayList<OrderRemarkDto>();
        List<MOrderRemarksEntity> entities = orderRemarksDao.findByOrderIdOrderByCreateDateDesc(orderId);
        Long id = 1L;
        for (MOrderRemarksEntity entity : entities) {
            OrderRemarkDto dto = new OrderRemarkDto();
            dto.setRemark(entity.getRemark());
            dto.setCreateDate(entity.getCreateDate().toString().substring(0, 19));

            SUserEntity userEntity = sUserDao.findOne(Long.valueOf(entity.getCreater()));
            dto.setCreater(userEntity.getUserName());
            dto.setId(id++);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    @Transactional
    public String changeActualAmount(OrderReturnDto dto) {

        SUserEntity user = (SUserEntity) AuthorityContext.getCurrentUser();
        MOrderMainEntity entity = orderMainDao.findOne(dto.getOrderId());

        if (null == entity) {
            throw new WRWException("该订单编号不存在");
        }
        if (!OrderStatus.WAIT_PAY.getKey().equals(entity.getStatus())) {
            throw new WRWException("该订单状态不可改支付金额");
        }
        if (StringUtils.isEmpty(dto.getActualAmount())) {
            throw new WRWException("请输入订单金额");
        }
        if (Double.valueOf(dto.getActualAmount()) > 99999L) {
            throw new WRWException("订单金额不能大于99999元");
        }
        if (Double.valueOf(dto.getActualAmount()) <= 0D) {
            throw new WRWException("订单金额不能改为0元");
        }
        if (null != entity.getIsPrepay() && entity.getIsPrepay().equals("1")) {
            throw new WRWException("支付请求已提交，无法修改订单信息");
        }
        entity.setActualAmount(Double.valueOf(Double.valueOf(dto.getActualAmount()) * 100L).longValue());
        entity.setModifyId(user.getUserId());
        entity.setModifyDate(new Date());
        orderMainDao.save(entity);
        return "改价成功";
    }

    @Override
    @Transactional
    public String cancelOrder(OrderReturnDto dto) {
        SUserEntity user = (SUserEntity) AuthorityContext.getCurrentUser();
        MOrderMainEntity entity = orderMainDao.findOne(dto.getOrderId());

        if (null == entity) {
            throw new WRWException("该订单编号不存在");
        }
        if (!OrderStatus.WAIT_PAY.getKey().equals(entity.getStatus())) {
            throw new WRWException("该订单状态不可取消订单");
        }

        List<MOrderDetailEntity> productsEntities = orderDetailDao.findProductInfoByOrderId(dto.getOrderId());
        for (MOrderDetailEntity product : productsEntities) {
            ActivityStock stock = activityStockDao.findOne(product.getActStockId());
            if (null == stock) {
                continue;
            }
            ActivitySpec stpc = activitySpecDao.findOne(product.getActSpecId());
            if (null == stpc) {
                continue;
            }
            if (StockType.TOTAL_STOCK.equals(stock.getStockType())) {
                stock.setTotalCount(stock.getTotalCount() + product.getNum() * stpc.getUnitNum());
                stock.setModifyDate(new Date());
                stock.setModifyId(user.getUserId());
                activityStockDao.save(stock);
            } else if (StockType.SPEC_STOCK.equals(stock.getStockType())) {
                // 按规格
                stpc.setTotal(stpc.getTotal() + product.getNum() * stpc.getUnitNum());
                stpc.setModifyDate(new Date());
                stpc.setModifyId(user.getUserId());
                activitySpecDao.save(stpc);

                stock.setTotalCount(stock.getTotalCount() + product.getNum() * stpc.getUnitNum());
                stock.setModifyDate(new Date());
                stock.setModifyId(user.getUserId());
                activityStockDao.save(stock);
            }
        }

        List<MCouponEntity> couponEntities = couponDao.findByMemberIdAndUsedOrderId(entity.getMemberId(),
                entity.getOrderId());
        if (null != couponEntities) {
            for (MCouponEntity mCouponEntity : couponEntities) {
                mCouponEntity.setUsedOrderId(null);
                if (new Date().after(mCouponEntity.getInvalidDate())) {
                    // 过期
                    mCouponEntity.setStatus(CouponState.EXPIRED.getKey());
                } else {
                    mCouponEntity.setStatus(CouponState.INVALID.getKey());
                }
                couponDao.save(mCouponEntity);
            }
        }
        entity.setStatus(OrderStatus.CANCELED.getKey());
        entity.setModifyId(user.getUserId());
        entity.setModifyDate(new Date());
        orderMainDao.save(entity);
        return "取消订单成功";
    }

    @Override
    @Transactional
    public String addRemark(OrderReturnDto dto) {
        SUserEntity user = (SUserEntity) AuthorityContext.getCurrentUser();
        MOrderMainEntity orderEntity = orderMainDao.findOne(dto.getOrderId());
        if (null == orderEntity) {
            throw new WRWException("该订单编号不存在");
        }
        if (StringUtils.isBlank(dto.getRemark())) {
            throw new WRWException("备注不能为空");
        }
        if (dto.getRemark().length() > 150) {
            throw new WRWException("备注内容不可超过150字");
        }
        MOrderRemarksEntity entity = new MOrderRemarksEntity();
        entity.setRemark(dto.getRemark());
        entity.setOrderId(dto.getOrderId());
        entity.setCreateDate(new Date());
        entity.setCreater(user.getUserId().toString());
        orderRemarksDao.save(entity);
        return "追加成功";
    }
}
