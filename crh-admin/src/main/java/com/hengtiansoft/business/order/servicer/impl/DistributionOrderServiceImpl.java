package com.hengtiansoft.business.order.servicer.impl;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.hengtiansoft.business.order.dto.DistributionOrdeDto;
import com.hengtiansoft.business.order.dto.DistributionOrdePayDto;
import com.hengtiansoft.business.order.dto.DistributionOrdeProductDto;
import com.hengtiansoft.business.order.dto.DistributionOrdeSearchDto;
import com.hengtiansoft.business.order.servicer.DistributionOrderService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.jpush.bean.JpushBean;
import com.hengtiansoft.common.jpush.enums.JpushExtraKeyEnum;
import com.hengtiansoft.common.jpush.util.JpushUtil;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.SpringUtils;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.common.xmemcached.constant.CacheType;
import com.hengtiansoft.wrw.dao.MMessageDao;
import com.hengtiansoft.wrw.dao.MOrderMainDao;
import com.hengtiansoft.wrw.dao.MOrderOperDao;
import com.hengtiansoft.wrw.dao.MOrderReturnDao;
import com.hengtiansoft.wrw.dao.MSmsDao;
import com.hengtiansoft.wrw.dao.SBasicParaDao;
import com.hengtiansoft.wrw.dao.SOrderRewardDao;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.dao.SRegionDao;
import com.hengtiansoft.wrw.dao.SUserDao;
import com.hengtiansoft.wrw.entity.MMessageEntity;
import com.hengtiansoft.wrw.entity.MOrderMainEntity;
import com.hengtiansoft.wrw.entity.MOrderOperEntity;
import com.hengtiansoft.wrw.entity.MOrderReturnEntity;
import com.hengtiansoft.wrw.entity.MSmsEntity;
import com.hengtiansoft.wrw.entity.SBasicParaEntity;
import com.hengtiansoft.wrw.entity.SOrderRewardEntity;
import com.hengtiansoft.wrw.entity.SOrgEntity;
import com.hengtiansoft.wrw.entity.SRegionEntity;
import com.hengtiansoft.wrw.entity.SUserEntity;
import com.hengtiansoft.wrw.enums.MMessageReadStatusEnum;
import com.hengtiansoft.wrw.enums.MMessageTypeEnum;
import com.hengtiansoft.wrw.enums.MessageModelEnum;
import com.hengtiansoft.wrw.enums.OrderResendFlagEnum;
import com.hengtiansoft.wrw.enums.OrderStatus;
import com.hengtiansoft.wrw.enums.PaymentStatus;
import com.hengtiansoft.wrw.enums.ReturnOrderTypeEnum;
import com.hengtiansoft.wrw.enums.RewardOrderTypeEnum;
import com.hengtiansoft.wrw.enums.ShipmentType;
import com.hengtiansoft.wrw.enums.SmsStatus;

@Service
public class DistributionOrderServiceImpl implements DistributionOrderService {

    @PersistenceContext
    private EntityManager   entityManager;

    @Autowired
    private SUserDao        sUserDao;

    @Autowired
    private SOrgDao         sOrgDao;

    @Autowired
    private SRegionDao      sRegionDao;

    @Autowired
    private MOrderMainDao   mOrderMainDao;

    @Autowired
    private MMessageDao     mMessageDao;

    @Autowired
    private SBasicParaDao   basicParaDao;

    @Autowired
    private MSmsDao         smsDao;

    @Autowired
    private SOrderRewardDao sOrderRewardDao;

    @Autowired
    private MOrderOperDao   mOrderOperDao;
    
    @Autowired
    private MOrderReturnDao mOrderReturnDao;

    @Autowired
    private CacheManager    cacheManager;

    @Override
    public void searchDistributionOrde(DistributionOrdeSearchDto infoDto) {
        Long useId = AuthorityContext.getCurrentUser().getUserId();
        SUserEntity user = sUserDao.findOne(useId);
        StringBuilder sqlDataBuilder = new StringBuilder();
        StringBuilder countDataBuilder = new StringBuilder();
        StringBuffer conditionBuffer = new StringBuffer();
        sqlDataBuilder
                .append("select ORDER_ID,TOTAL_AMOUNT,CONTACT,PHONE,STATUS,SHIPMENT_TYPE,CREATE_DATE,SOURCE, PAYMENT_STATUS,ORG_ID from M_ORDER_MAIN where ORG_ID =  ")
                .append(user.getOrgId());
        countDataBuilder.append("select count(1) from (select * from M_ORDER_MAIN where  ORG_ID = ").append(user.getOrgId());
        if (0L != user.getOrgId()) {
            conditionBuffer.append(" and ORG_ID != 0");
        } else {
            conditionBuffer.append(" and ORG_ID = 0");
        }

        // 新订单
        // 订单编号
        if (!StringUtils.isEmpty(infoDto.getOrderId())) {
            conditionBuffer.append(" and ORDER_ID = '").append(infoDto.getOrderId()).append("'");
        }
        // 下单数量LOW
        if (!StringUtils.isEmpty(infoDto.getNumLow())) {
            conditionBuffer.append(" and TOTAL_NUM >= ").append(infoDto.getNumLow());
        }
        // 下单数量HIGH
        if (!StringUtils.isEmpty(infoDto.getNumHigh())) {
            conditionBuffer.append(" and TOTAL_NUM <= ").append(infoDto.getNumHigh());
        }
        // 订单状态
        if (!StringUtils.isEmpty(infoDto.getStatus())) {
            if ("".equals(infoDto.getStatus())) {
                conditionBuffer.append(" and STATUS = STATUS ");
            } else {
                conditionBuffer.append(" and STATUS = '").append(infoDto.getStatus()).append("'");
            }
        }

        // 下单statDate
        if (!StringUtils.isEmpty(infoDto.getStartDate())) {
            conditionBuffer.append(" and CREATE_DATE >= '").append(infoDto.getStartDate()).append(" 00:00:00'");
        }
        // 下单endDate
        if (!StringUtils.isEmpty(infoDto.getEndDate())) {
            conditionBuffer.append(" and CREATE_DATE <= '").append(infoDto.getEndDate()).append(" 23:59:59'");
        }
        // 收货人
        if (!StringUtils.isEmpty(infoDto.getConsignee())) {
            conditionBuffer.append(" and MEMBER_NAME like '%").append(infoDto.getConsignee()).append("%'");
        }
        // 联系方式
        if (!StringUtils.isEmpty(infoDto.getPhone())) {
            conditionBuffer.append(" and PHONE like '%").append(infoDto.getPhone()).append("%'");
        }

        Query query = entityManager.createNativeQuery(sqlDataBuilder.append(conditionBuffer).append(" order by CREATE_DATE desc").toString());
        Query countQuery = entityManager.createNativeQuery(countDataBuilder.append(conditionBuffer).append(" ) A").toString());
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(infoDto.getPageSize() * (infoDto.getCurrentPage() - 1));
        query.setMaxResults(infoDto.getPageSize());
        infoDto.setTotalRecord(totalRecord.longValue());
        infoDto.setTotalPages(totalRecord.intValue() % infoDto.getPageSize() == 0 ? totalRecord.intValue() / infoDto.getPageSize() : ((totalRecord
                .intValue() / infoDto.getPageSize()) + 1));
        infoDto.setList(buildDistributionOrderDtos(query.getResultList()));
    }

    private List<DistributionOrdeDto> buildDistributionOrderDtos(List<Object[]> list) {
        List<DistributionOrdeDto> detailDtos = new ArrayList<DistributionOrdeDto>();
        for (Object[] array : list) {
            DistributionOrdeDto dto = new DistributionOrdeDto();
            dto.setOrderId(null != array[0] ? array[0].toString() : "");
            DecimalFormat df = new DecimalFormat("##0.00");
            String totalAmount = df.format(Double.valueOf(array[1].toString()) / 100);
            dto.setTotalAmount(null != array[1] ? String.valueOf(totalAmount) : "");
            dto.setMemberName(null != array[2] ? array[2].toString() : "");
            dto.setPhone(null != array[3] ? array[3].toString() : "");
            if (null != array[4]) {
                dto.setStatus(OrderStatus.getValue(array[4].toString()));
            }
            if (null != array[5]) {
                if (ShipmentType.express.getKey().equals(array[5].toString())) {
                    dto.setShipmentType(ShipmentType.express.getValue());
                } else if (ShipmentType.delivery.getKey().equals(array[5].toString())) {
                    dto.setShipmentType(ShipmentType.delivery.getValue());
                } else if (ShipmentType.oneself.getKey().equals(array[5].toString())) {
                    dto.setShipmentType(ShipmentType.oneself.getValue());
                }
            }
            dto.setCreateDate(null != array[6] ? array[6].toString().substring(0, array[6].toString().length() - 2) : "");
            dto.setSource(array[7] == null ? null : (String) array[7]);
            dto.setPaymentStatus(array[8] == null ? null : (String) array[8]);

            //是否有扫码权限
            detailDtos.add(dto);
        }
        return detailDtos;
    }
    
    private Boolean hasScanCodePermission(String orderId){
        MOrderReturnEntity returnEntity=mOrderReturnDao.findByOrderMainId(orderId);
        //未收货退款显示扫码
        if(returnEntity!=null&&ReturnOrderTypeEnum.NORECEIVE__RETURNGOODS.getKey().equals(String.valueOf(returnEntity.getReturnType()))){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public DistributionOrdeDto findDistributionOrder(String id) {
        DistributionOrdeDto dto = new DistributionOrdeDto();
        // 订单信息
        StringBuilder sqlOrderBuilder = new StringBuilder();
        sqlOrderBuilder
                .append("select o.ORDER_ID,o.STATUS,o.CREATE_DATE,o.TOTAL_AMOUNT,o.SHIPMENT_TYPE,o.RECEIVING_DATE,o.CONTACT,o.PHONE,o.ADDRESS,o.RECEVING_TIME,o.SHIPMENT_DATE from M_ORDER_MAIN o where ORDER_ID = '")
                .append(id).append("'");
        Query orderQuery = entityManager.createNativeQuery(sqlOrderBuilder.toString());
        List<Object[]> orderList = orderQuery.getResultList();
        for (Object[] array : orderList) {
            dto.setOrderId(null != array[0] ? array[0].toString() : "");
            if (null != array[1]) {
                dto.setStatus(OrderStatus.getValue(array[1].toString()));
            }

            DecimalFormat df = new DecimalFormat("##0.00");
            String totalAmount = df.format(Double.valueOf(array[3].toString()) / 100);
            dto.setCreateDate(null != array[2] ? array[2].toString().substring(0, array[2].toString().length() - 2) : "");
            dto.setTotalAmount(null != array[3] ? String.valueOf(totalAmount) : "");
            if (null != array[4]) {
                if (ShipmentType.express.getKey().equals(array[4].toString())) {
                    dto.setShipmentType(ShipmentType.express.getValue());
                } else if (ShipmentType.delivery.getKey().equals(array[4].toString())) {
                    dto.setShipmentType(ShipmentType.delivery.getValue());
                } else if (ShipmentType.oneself.getKey().equals(array[4].toString())) {
                    dto.setShipmentType(ShipmentType.oneself.getValue());
                }
            }
            dto.setFinishDate(null != array[5] ? array[5].toString().substring(0, array[5].toString().length() - 2) : "");
            dto.setMemberName(null != array[6] ? array[6].toString() : "");
            dto.setPhone(null != array[7] ? array[7].toString() : "");
            dto.setAddress(null != array[8] ? array[8].toString() : "");
            dto.setRecevingTime(null != array[9] ? array[9].toString() : "");
            dto.setShipmentDate(null != array[10] ? array[10].toString().substring(0, array[10].toString().length() - 2) : "");
        }
        // 商品信息
        StringBuilder sqlProductBuilder = new StringBuilder();
        sqlProductBuilder
                .append("select p.PRODUCT_CODE,d.PRODUCT_NAME,b.BRAND_NAME,c.CATE_NAME,d.num,d.price  from M_ORDER_MAIN o "
                        + " left join  M_ORDER_DETAIL d on o.ORDER_ID = d.ORDER_ID " + " left join P_PRODUCT p on d.PRODUCT_ID = p.PRODUCT_ID "
                        + " left join P_BRAND b on p.BRAND_ID = b.BRAND_ID "
                        + " left join P_CATEGORY c on p.CATE_ID = c.CATE_ID where o.ORDER_ID = '").append(id).append("'");
        Query productQuery = entityManager.createNativeQuery(sqlProductBuilder.toString());
        List<Object[]> productList = productQuery.getResultList();
        List<DistributionOrdeProductDto> productDtos = new ArrayList<DistributionOrdeProductDto>();
        for (Object[] array : productList) {
            DistributionOrdeProductDto productDto = new DistributionOrdeProductDto();
            productDto.setSn(null != array[0] ? array[0].toString() : "");
            productDto.setProductName(null != array[1] ? array[1].toString() : "");
            productDto.setBrand(null != array[2] ? array[2].toString() : "");
            productDto.setCategory(null != array[3] ? array[3].toString() : "");
            productDto.setNum(null != array[4] ? array[4].toString() : "");
            DecimalFormat df = new DecimalFormat("##0.00");
            String price = df.format(Double.valueOf(array[5].toString()) / 100);
            productDto.setPrice(null != array[5] ? String.valueOf(price) : "");
            if (null != array[5] && null != array[4]) {
                Double totalPrice = Integer.valueOf(array[4].toString()) * Double.valueOf(Double.valueOf(array[5].toString()) / 100);
                productDto.setTotalPrice(df.format(totalPrice));
            } else {
                productDto.setTotalPrice("##0.00");
            }
            productDtos.add(productDto);
        }
        dto.setDistributionOrdeProductDto(productDtos);
        // 支付信息
        StringBuilder sqlPayBuilder = new StringBuilder();
        sqlPayBuilder
                .append("select o.ORDER_ID,PAYMENT_TYPE,PAYMENT_STATUS,TOTAL_AMOUNT,ACTUAL_AMOUNT,PAY_DATE,COUPON_AMOUNT from M_ORDER_MAIN o "
                        + "where o.ORDER_ID =  '").append(id).append("'");
        Query payQuery = entityManager.createNativeQuery(sqlPayBuilder.toString());
        List<Object[]> payList = payQuery.getResultList();
        List<DistributionOrdePayDto> payDtos = new ArrayList<DistributionOrdePayDto>();
        for (Object[] array : payList) {
            DistributionOrdePayDto payDto = new DistributionOrdePayDto();
            payDto.setOrderId(null != array[0] ? array[0].toString() : "");
            if (null != array[1]) {
//                if (PaymentType.BALANCE.getKey().equals(array[1].toString())) {
//                    payDto.setBillType(PaymentType.BALANCE.getValue());
//                } else if (PaymentType.ALIPAY.getKey().equals(array[1].toString())) {
//                    payDto.setBillType(PaymentType.ALIPAY.getValue());
//                } else if (PaymentType.WECHAT.getKey().equals(array[1].toString())) {
//                    payDto.setBillType(PaymentType.WECHAT.getValue());
//                }
            }
            if (null != array[2]) {
                if (PaymentStatus.unpaid.getKey().equals(array[2].toString())) {
                    payDto.setStatus(PaymentStatus.unpaid.getValue());
                } else if (PaymentStatus.paid.getKey().equals(array[2].toString())) {
                    payDto.setStatus(PaymentStatus.paid.getValue());
                }
            }
            DecimalFormat df = new DecimalFormat("##0.00");
            String amount = df.format(Double.valueOf(array[3].toString()) / 100);
            String actualAmount = df.format(Double.valueOf(array[4].toString()) / 100);
            String coupon = df.format(Double.valueOf(array[6].toString()) / 100);
            payDto.setAmount(null != array[3] ? String.valueOf(amount) : "");
            payDto.setActualAmount(null != array[4] ? String.valueOf(actualAmount) : "");
            payDto.setPayTime(null != array[5] ? array[5].toString().substring(0, array[5].toString().length() - 2) : "");
            payDto.setCoupon(null != array[6] ? String.valueOf(coupon) : "");
            payDtos.add(payDto);
        }

        dto.setDistributionOrdePayDtos(payDtos);
        // 评价信息
        StringBuilder sqlEvaluateBuilder = new StringBuilder();
        sqlEvaluateBuilder
                .append("select f.ATTITUDE_LEVEL,f.SHIPMENT_LEVLE,f.SERVICE_LEVEL from M_ORDER_MAIN o "
                        + "left join  M_ORDER_FEEDBACK f on o.ORDER_ID = f.ORDER_ID " + "where o.ORDER_ID = '").append(id).append("'");
        Query evaluateQuery = entityManager.createNativeQuery(sqlEvaluateBuilder.toString());
        List<Object[]> evaluateList = evaluateQuery.getResultList();
        for (Object[] array : evaluateList) {
            dto.setAttitudeLevel(null != array[0] ? Integer.valueOf(array[0].toString()) : 0);
            dto.setShipmentLevle(null != array[1] ? Integer.valueOf(array[1].toString()) : 0);
            dto.setServiceLevel(null != array[2] ? Integer.valueOf(array[2].toString()) : 0);
        }
        return dto;
    }

    @Override
    public Integer getNewOrder() {
        Long useId = AuthorityContext.getCurrentUser().getUserId();
        SUserEntity user = sUserDao.findOne(useId);
        if (0L != user.getOrgId()) {
            return mOrderMainDao.getNewOrderCount(user.getOrgId(), OrderStatus.WAIT_PAY.getKey());
        } else {
            return mOrderMainDao.getPlatNewOrderCount(user.getOrgId(), OrderStatus.WAIT_PAY.getKey());
        }
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public void sendGoods(String id) {
        // 订单信息更新
        MOrderMainEntity order = mOrderMainDao.findOne(id);
        if (null == order) {
            throw new WRWException("订单不存在！");
        }
        if (OrderStatus.WAIT_PAY.getKey().equals(order.getStatus())) {
            throw new WRWException("订单状态异常！");
        }
        order.setStatus(OrderStatus.WAIT_PAY.getKey());
        order.setShipmentDate(new Date());
        mOrderMainDao.save(order);

        // 订单更新记录
        updateOrderStatus(order.getMemberId(), id, OrderStatus.WAIT_PAY.getKey());

        // 获取消息内容
        String orderMessage = getMessage(order);
        // 获取短信内容
        String orderSms = getSMS(order);

        // 交互信息
        Map<String, String> extras = new HashMap<String, String>();
        extras.put(JpushExtraKeyEnum.ORDER_ID.getValue(), id);

        // 存储消息
        MMessageEntity message = new MMessageEntity();
        message.setMemberId(order.getMemberId());
        message.setType(MMessageTypeEnum.ORDER.getKey());
        message.setTitle("您的订单已发货");
        message.setContent(orderMessage);
        message.setParam(WRWUtil.parseObjTOString(extras));
        message.setReadStatus(MMessageReadStatusEnum.UNREAD.getKey());
        message.setCreateId(AuthorityContext.getCurrentUser().getUserId());
        message.setCreateDate(new Date());
        mMessageDao.save(message);

        // 推送push
        JpushBean jpushBean = new JpushBean();
        jpushBean.setTitle("您的订单已发货");
        jpushBean.setAlert(orderMessage);
        // 设置别名
        jpushBean.getAliases().add(order.getMemberId().toString());
        // 设置交互数据
        extras.put(JpushExtraKeyEnum.ORDER_STATUS.getValue(), order.getStatus());
        extras.put(JpushExtraKeyEnum.MESSAGE_TYPE.getValue(), MMessageTypeEnum.ORDER.getKey());
        extras.put(JpushExtraKeyEnum.MESSAGE_ID.getValue(), message.getMessageId().toString());
        jpushBean.setExtras(extras);
        JpushUtil.push(jpushBean);

        // 短信
        MSmsEntity sms = new MSmsEntity();
        sms.setContent(orderSms);
        sms.setCreateDate(new Date());
        sms.setPhone(order.getPhone());
        sms.setStatus(SmsStatus.UNSEND.getKey());
       /* // 配送订单消息
        if (ShipmentType.delivery.getKey().equals(order.getShipmentType())) {
            sms.setType(String.valueOf(MessageModelEnum.sms_C_sendShipmentNotify.getKey()));
        }
        // 自提订单消息
        if (ShipmentType.oneself.getKey().equals(order.getShipmentType())) {
            sms.setType(String.valueOf(MessageModelEnum.sms_C_selfShipmentNotify.getKey()));
        }
        // 邮寄订单消息
        if (ShipmentType.express.getKey().equals(order.getShipmentType())) {
            sms.setType(String.valueOf(MessageModelEnum.sms_C_sendSmsByMailNotify.getKey()));
        }*/
        smsDao.save(sms);
    }

    /**
     * Description: 获取消息内容
     *
     * @param order
     * @return
     */
    private String getMessage(MOrderMainEntity order) {
        String message = null;

        /*// 配送订单消息
        if (ShipmentType.delivery.getKey().equals(order.getShipmentType())) {
            SBasicParaEntity messageEntity = basicParaDao.findByParaName(MessageModelEnum.sys_C_sendShipmentNotify.getValue());
            if (null != messageEntity) {
                message = messageEntity.getParaValue1().replace("${orderId}", order.getOrderId());
            }
        }

        // 自提订单消息
        if (ShipmentType.oneself.getKey().equals(order.getShipmentType())) {
            SBasicParaEntity messageEntity = basicParaDao.findByParaName(MessageModelEnum.sys_C_selfShipmentNotify.getValue());
            if (null != messageEntity) {
                // 获取自提点信息
                Long orgId = order.getOrgId();
                SOrgEntity org = sOrgDao.findOne(orgId);

                // 设置消息内容
                message = messageEntity.getParaValue1().replace("${orderId}", order.getOrderId()).replace("${address}", getFullAddress(org))
                        .replace("${contactName}", org.getContact()).replace("${contactPhone}", org.getPhone());
            }
        }

        // 邮寄订单消息您好，您的订单：${orderId}已经发货了，快递公司:${company}，快递单号：${expressOrderId}，请注意查收！【吾儿网】
        if (ShipmentType.express.getKey().equals(order.getShipmentType())) {
            SBasicParaEntity messageEntity = basicParaDao.findByParaName(MessageModelEnum.sms_C_sendByMailNotify.getValue());
            if (null != messageEntity) {
                // 设置消息内容
                message = messageEntity.getParaValue1().replace("${orderId}", order.getOrderId()).replace("${company}", order.getExpressName())
                        .replace("${expressOrderId}", order.getExpressNum());
            }
        }

        if (null == message) {
            throw new WRWException("消息模板丢失！");
        }*/

        return message;
    }

    /**
     * Description: 获取短信内容
     *
     * @param order
     * @return
     */
    private String getSMS(MOrderMainEntity order) {
        String sms = null;

        // 配送订单消息
        if (ShipmentType.delivery.getKey().equals(order.getShipmentType())) {
            SBasicParaEntity messageEntity = basicParaDao.findByParaName(MessageModelEnum.sms_C_refundNotify.getValue());
            if (null != messageEntity) {
                sms = messageEntity.getParaValue1().replace("${orderId}", order.getOrderId());
            }
        }

        if (null == sms) {
            throw new WRWException("短信模板丢失！");
        }

        return sms;
    }

    /**
     * Description: 获取完整收货地址
     *
     * @param org
     * @return
     */
    private String getFullAddress(SOrgEntity org) {
        SRegionEntity region = sRegionDao.findOne(org.getRegion());
        String regionAddress = region.getMergerName().replaceAll(",|中国", "");

        return regionAddress + org.getAddress();
    }

    /**
     * Description: 保存订单操作记录
     *
     * @param status
     * @param orderIds
     */
    @Transactional
    private void updateOrderStatus(Long memberId, String orderId, String operType) {
        MOrderOperEntity orderOper = mOrderOperDao.findByOrderIdAndOpertype(orderId, operType);
        if (null == orderOper) {
            orderOper = new MOrderOperEntity();
            orderOper.setOrderId(orderId);
            orderOper.setOpertype(operType);
        }
        orderOper.setComment("订单状态更新:" + OrderStatus.getValue(operType));
        orderOper.setUserId(memberId);
        orderOper.setOperDate(new Date());
        mOrderOperDao.save(orderOper);
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public void getGoods(String id) {
        SUserEntity user = (SUserEntity) AuthorityContext.getCurrentUser();
        Long orgId = user.getOrgId();

        // 订单状态更新
        MOrderMainEntity order = mOrderMainDao.findOne(id);
        if (!OrderStatus.WAIT_PAY.getKey().equals(order.getStatus())) {
            throw new WRWException("订单状态异常:订单" + OrderStatus.getValue(order.getStatus()));
        }
        if (OrderResendFlagEnum.RESEND.getKey().equals(order.getResendFlag()) && !order.getOrgId().equals(orgId)) {
            throw new WRWException("订单已被转移，无法接单");
        }
        order.setStatus(OrderStatus.WAIT_PAY.getKey());
        mOrderMainDao.save(order);

        // 订单更新记录
        updateOrderStatus(orgId, id, OrderStatus.WAIT_PAY.getKey());

        // 创建奖励订单
        Long rewardAmount = sOrderRewardDao.sumAmountByOrderIdAndType(id, RewardOrderTypeEnum.TAKE_TIMEOUT.getKey());
        if (null != rewardAmount && rewardAmount < 0) {
            SOrderRewardEntity reward = new SOrderRewardEntity();
            reward.setOrderId(id);
            reward.setOrgId(orgId);
            reward.setAmount(-1 * rewardAmount);
            reward.setAssignDate(order.getAssignDate());
            reward.setCreateDate(new Date());
            reward.setRewardDate(new Date());
            reward.setType(RewardOrderTypeEnum.REWARD.getKey());
            sOrderRewardDao.save(reward);
        }
        // 缓存处理
        // 查询当前Z的新订单数
        List<String> newOrders = mOrderMainDao.getNewOrders(orgId, OrderStatus.WAIT_PAY.getKey());
        StringBuilder newOrderssb = new StringBuilder();
        for (String newOrder : newOrders) {
            newOrderssb.append(newOrder).append(",");
        }
        String orders = newOrderssb.toString();
        if(newOrderssb.lastIndexOf(",")>0){
           orders = newOrderssb.toString().substring(0, newOrderssb.toString().length() - 1);
        }
        // 将z的所有新订单放缓存
        CacheManager cacheManager = (CacheManager) SpringUtils.getBean("cacheManager");
        Cache cache = cacheManager.getCache(CacheType.ORDERCACHE);
        cache.put(orgId + "_S_ORG_ID", orders);

    }

    @Override
    public Long getNewOrderRemind() {
        Long num = 0L;
        Long useId = AuthorityContext.getCurrentUser().getUserId();
        SUserEntity user = sUserDao.findOne(useId);
        Cache cache = cacheManager.getCache(CacheType.ORDERCACHE);
        ValueWrapper sOrderIdWrapper = cache.get(user.getOrgId() + "_S_ORG_ID");// 客户
        if (0L != user.getOrgId()) {
            // z的所有新订单
            List<String> newOrderIds = mOrderMainDao.getNewOrders(user.getOrgId(), OrderStatus.WAIT_PAY.getKey());
            if (newOrderIds.size() == 0) {
                return num;
            } else {
                // 当两部分memcache中订单都为空时，从DB中获取新订单 _S_ORG_ID
                if (null == sOrderIdWrapper) {
                    num = 1L;
                }
                // 当memcache中有订单数据时，从中获取;
                if (null != sOrderIdWrapper) {
                    String[] soders = sOrderIdWrapper.get().toString().split(",");
                    List<String> slist = new ArrayList(Arrays.asList(soders));
                    if (slist.size() > 0) {
                        num = 1L;
                    }
                }
            }
        }
        return num;
    }

    @Override
    public List<List<String>> findAllOrder(String orderId) {
        List<List<String>> listStr = new ArrayList<List<String>>();
        if (WRWUtil.isEmpty(orderId)) {
            throw new WRWException("订单不能为空!");
        }
        String[] orderIds = orderId.split(",");
        List<String> orders = new ArrayList<String>();
        for (String str : orderIds) {
            orders.add(str);
        }
        List<Object[]> list = mOrderMainDao.findAllOrder(orders);
        if (CollectionUtils.isNotEmpty(list)) {
            Integer numCount = 0;
            for (int i = 0; i < list.size(); i++) {
                List<String> strList = new ArrayList<String>();
                strList.add(DateTimeUtil.parseDateToString((Date) list.get(i)[0], DateTimeUtil.CHINA_YMD));
                strList.add(list.get(i)[1].toString());
                strList.add(list.get(i)[2].toString());
                strList.add(list.get(i)[3].toString());
                strList.add(list.get(i)[4].toString());
                strList.add(list.get(i)[5].toString());
                strList.add(list.get(i)[6].toString());
                strList.add(list.get(i)[7].toString());
                strList.add(list.get(i)[8].toString());
                listStr.add(strList);
                numCount += Integer.valueOf(list.get(i)[8].toString());
            }
            List<String> hzList = new ArrayList<String>();
            hzList.add("汇总");
            hzList.add("");
            hzList.add("");
            hzList.add("");
            hzList.add("");
            hzList.add("");
            hzList.add("");
            hzList.add("");
            hzList.add(numCount.toString());
            listStr.add(hzList);
        }

        return listStr;
    }

}
