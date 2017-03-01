package com.hengtiansoft.task.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.hengtiansoft.task.repository.IMOrderMainRepository;
import com.hengtiansoft.task.repository.IMOrderOperRepository;
import com.hengtiansoft.task.service.OrderCancelService;
import com.hengtiansoft.wrw.dao.ActivitySpecDao;
import com.hengtiansoft.wrw.dao.ActivityStockDao;
import com.hengtiansoft.wrw.dao.MCouponDao;
import com.hengtiansoft.wrw.dao.MOrderDetailDao;
import com.hengtiansoft.wrw.entity.ActivitySpec;
import com.hengtiansoft.wrw.entity.ActivityStock;
import com.hengtiansoft.wrw.entity.MOrderDetailEntity;
import com.hengtiansoft.wrw.entity.MOrderMainEntity;
import com.hengtiansoft.wrw.entity.MOrderOperEntity;
import com.hengtiansoft.wrw.enums.OrderStatus;
import com.hengtiansoft.wrw.enums.StockTypeEnum;

@Service
public class OrderCancelServiceImpl implements OrderCancelService {

    @Autowired
    private IMOrderMainRepository mOrderMainRepository;

    @Autowired
    private IMOrderOperRepository mOrderOperRepository;

    @Autowired
    private MOrderDetailDao mOrderDetailDao;

    @Autowired
    private ActivityStockDao activityStockDao;

    @Autowired
    private ActivitySpecDao activitySpecDao;
    
    @Autowired
    private MCouponDao mCouponDao;

    /**
     * 取消超时未支付订单
     */
    @Transactional("jpaTransactionManager")
    @Override
    public void cancelOrder() {
        List<MOrderMainEntity> orderToCancel = new ArrayList<>();

        Date now = new Date();
        List<MOrderMainEntity> orders = mOrderMainRepository.findByStatus(OrderStatus.WAIT_PAY.getKey());
        List<MOrderOperEntity> orderOpers = new ArrayList<>();
        List<MOrderDetailEntity> mOrderDetails = new ArrayList<MOrderDetailEntity>();
        for (MOrderMainEntity order : orders) {
            if (getLeftPayTime(order.getCreateDate()).longValue() <= 0L) {
                order.setStatus(OrderStatus.CANCELED.getKey());
                order.setCancelReason("系统自动取消");
                order.setModifyDate(now);
                orderToCancel.add(order);
                orderOpers.add(genarateOrderOper(order));

                // 库存处理
                List<MOrderDetailEntity> tempOrderDetails = mOrderDetailDao.findByOrderId(order.getOrderId());
                if (!CollectionUtils.isEmpty(tempOrderDetails)) {
                    mOrderDetails.addAll(tempOrderDetails);
                }
                
                //优惠券处理
                mCouponDao.releaseCouponByCancelOrder(order.getOrderId());
            }
        }
        mOrderMainRepository.save(orderToCancel);
        mOrderOperRepository.save(orderOpers);
        productStockDeal(mOrderDetails);
    }

    /**
     * 
     * Description: 订单商品库存释放
     *
     * @author chengchaoyin
     */
    @Transactional(value = "jpaTransactionManager")
    private void productStockDeal(List<MOrderDetailEntity> mOrderDetails) {
        if (!CollectionUtils.isEmpty(mOrderDetails)) {
            for (MOrderDetailEntity mOrderDetailEntity : mOrderDetails) {
                Long actStockId = mOrderDetailEntity.getActStockId();
                Long actSpecId = mOrderDetailEntity.getActSpecId();
                Integer reqNum = mOrderDetailEntity.getNum();

                ActivityStock actStock = activityStockDao.findOne(actStockId);
                ActivitySpec actSpec = activitySpecDao.findOne(actSpecId);

                if (actStock != null && actSpec != null) {
                    Integer tempTotalCount = reqNum * actSpec.getUnitNum();
                    // 按总库存
                    if (StockTypeEnum.STOCK.getKey().equals(actStock.getStockType())) {

                        activityStockDao.updateActTotalStock(actStockId, tempTotalCount);

                    } else if (StockTypeEnum.SPEC.getKey().equals(actStock.getStockType())) {
                        // 按规格
                        activityStockDao.updateActTotalStock(actStockId, tempTotalCount);
                        
                        activitySpecDao.updateActSpecStock(actSpecId, tempTotalCount);
                    }
                }
            }
        }
    }

    /**
     * Description: 保存订单操作记录
     *
     * @param status
     * @param orderIds
     */
    @Transactional(value = "jpaTransactionManager")
    private MOrderOperEntity genarateOrderOper(MOrderMainEntity order) {
        String orderId = order.getOrderId();
        String status = order.getStatus();

        MOrderOperEntity orderOper = mOrderOperRepository.findByOrderIdAndOpertype(orderId, status);
        if (null == orderOper) {
            orderOper = new MOrderOperEntity();
            orderOper.setOrderId(orderId);
            orderOper.setOpertype(status);
        }
        orderOper.setComment("订单状态更新:" + OrderStatus.getValue(status));
        orderOper.setUserId(order.getMemberId());
        orderOper.setOperDate(new Date());
        return orderOper;
    }

    /**
     * Description: 获取剩余付款时间（秒）(PS：1个小时倒计时)
     *
     * @param createDate
     *            订单生成时间
     * @return
     */
    private Long getLeftPayTime(Date createDate) {
        Long gapTime = new Date().getTime() - createDate.getTime();
        Long leftPayTime = 60 * 60 - gapTime / 1000;

        return leftPayTime;
    }
}
