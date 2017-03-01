/*
 * Project Name: wrw-job
 * File Name: OrderFeedbackServiceImpl.java
 * Class Name: OrderFeedbackServiceImpl
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
package com.hengtiansoft.task.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.task.service.OrderFeedbackService;
import com.hengtiansoft.wrw.dao.MOrderDetailDao;
import com.hengtiansoft.wrw.dao.MOrderFeedbackDao;
import com.hengtiansoft.wrw.dao.MOrderMainDao;
import com.hengtiansoft.wrw.dao.MOrderOperDao;
import com.hengtiansoft.wrw.entity.MOrderDetailEntity;
import com.hengtiansoft.wrw.entity.MOrderFeedbackEntity;
import com.hengtiansoft.wrw.entity.MOrderMainEntity;
import com.hengtiansoft.wrw.entity.MOrderOperEntity;
import com.hengtiansoft.wrw.enums.OrderStatus;

/**
 * Class Name: OrderFeedbackServiceImpl
 * Description: 订单评价
 * @author zhongyidong
 *
 */
@Service
public class OrderFeedbackServiceImpl implements OrderFeedbackService {
    
    private static final Integer DAYS_EVL = 10;
    private static final String UNFEEDBACK = "0";
    private static final String FEEDBACKED = "1";
    private static final String EVL_CONTENT = "该用户超时未评价，系统默认好评！";
    
    @Autowired
    private MOrderOperDao orderOperDao;
    
    @Autowired
    private MOrderMainDao orderMainDao;

    @Autowired
    private MOrderDetailDao orderDetailDao;
    
    @Autowired
    private MOrderFeedbackDao orderFeedbackDao;
    
    @Override
    @Transactional("jpaTransactionManager")
    public void addFeedback() {
        List<MOrderMainEntity> orders = orderMainDao.findOrderByStatus(OrderStatus.WATI_RATE.getKey());
        if (CollectionUtils.isNotEmpty(orders)) {
            List<String> orderIds = new ArrayList<String>();
            Map<String, Long> orderMap = new HashMap<String, Long> ();
            for (MOrderMainEntity order : orders) {
                orderIds.add(order.getOrderId());
                orderMap.put(order.getOrderId(), order.getMemberId());
            }
            List<MOrderDetailEntity> orderDetails = orderDetailDao.findByUnevl(DAYS_EVL, orderIds);
            if (CollectionUtils.isNotEmpty(orderDetails)) {
                for (MOrderDetailEntity orderDetail : orderDetails) {
                    // 添加评价
                    handleOrderFeedback(orderMap.get(orderDetail.getOrderId()), orderDetail);
                }
            }
        }
    }
    
    @Transactional("jpaTransactionManager")
    private void handleOrderFeedback(Long memberId, MOrderDetailEntity orderDetail) {
        if (null == memberId) {
            return;
        }
        Date date = new Date();
        MOrderFeedbackEntity feedback = new MOrderFeedbackEntity();
        feedback.setStar(5);
        feedback.setAnonymous("1");
        feedback.setCreateDate(date);
        feedback.setFeedInfo(EVL_CONTENT);
        feedback.setOrderId(orderDetail.getOrderId());
        feedback.setMemberId(memberId);
        feedback.setProductId(orderDetail.getProductId());
        feedback.setOrderDetailId(orderDetail.getDetailId());
        feedback = orderFeedbackDao.save(feedback);
        if (null != feedback) {
            // 修改订单中商品的评价状态
            int row = orderDetailDao.updateStatus(orderDetail.getDetailId(), FEEDBACKED);
            // 检查订单中的商品是否全部评价，若全部评价，则修改订单状态为已点评
            int count = orderDetailDao.countUnfeedback(orderDetail.getOrderId(), UNFEEDBACK);
            if (row > 0 && count == 0) {
                row = orderMainDao.updateOrderStatus(orderDetail.getOrderId(), OrderStatus.COMPLETE.getKey());
            }
            if (row > 0) {
                MOrderOperEntity orderOper = new MOrderOperEntity();
                orderOper.setOperDate(date);
                orderOper.setUserId(0L);
                orderOper.setOrderId(orderDetail.getOrderId());
                orderOper.setOpertype(OrderStatus.COMPLETE.getKey());
                orderOper.setComment("订单状态更新:" + OrderStatus.COMPLETE.getValue());
                orderOperDao.save(orderOper);
            }
        } 
    }

}
