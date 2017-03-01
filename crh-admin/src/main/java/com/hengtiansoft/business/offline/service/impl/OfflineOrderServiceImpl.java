/*
 * Project Name: wrw-admin
 * File Name: MposOrderServiceImpl.java
 * Class Name: MposOrderServiceImpl
 * Copyright 2014 Hengtian Software Inc
 * Licensed under the Hengtiansoft
 * http://www.hengtiansoft.com
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.offline.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.common.util.UserUtil;
import com.hengtiansoft.business.offline.dto.OfflineOrderSubmitReqDto;
import com.hengtiansoft.business.offline.dto.OfflineOrderSubmitRespDto;
import com.hengtiansoft.business.offline.dto.OfflineProductDto;
import com.hengtiansoft.business.offline.service.OfflineOrderService;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.sequence.SequenceGenerator;
import com.hengtiansoft.common.sequence.SequenceType;
import com.hengtiansoft.wrw.dao.MOrderDetailDao;
import com.hengtiansoft.wrw.dao.MOrderMainDao;
import com.hengtiansoft.wrw.dao.PShiefDao;
import com.hengtiansoft.wrw.dao.PProductDao;
import com.hengtiansoft.wrw.entity.MOrderDetailEntity;
import com.hengtiansoft.wrw.entity.MOrderMainEntity;
import com.hengtiansoft.wrw.entity.MOrderOperEntity;
import com.hengtiansoft.wrw.entity.PShiefEntity;
import com.hengtiansoft.wrw.entity.PProductEntity;
import com.hengtiansoft.wrw.enums.OrderExceptionEnum;
import com.hengtiansoft.wrw.enums.OrderPressedStatus;
import com.hengtiansoft.wrw.enums.OrderStatus;
import com.hengtiansoft.wrw.enums.PaymentStatus;

/**
 * Class Name: MposOrderServiceImpl
 * Description:
 * 
 * @author xiaoluzhou
 */
@Service
public class OfflineOrderServiceImpl implements OfflineOrderService {

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private PProductDao       productDao;

    @Autowired
    private PShiefDao         priceDao;

    @Autowired
    private MOrderDetailDao   orderDetailDao;

    @Autowired
    private MOrderMainDao     orderMainDao;

    @Transactional
    @Override
    public OfflineOrderSubmitRespDto submitOrder(OfflineOrderSubmitReqDto submitDto) {

        String orderId = sequenceGenerator.getOrderId(SequenceType.M_ORDER);

        // 产品数量map
        Map<Long, Integer> productNumMap = new HashMap<>();
        for (OfflineProductDto product : submitDto.getProducts()) {
            Long key = product.getProductId();
            Integer num = productNumMap.get(key);
            productNumMap.put(key, (num == null ? 0 : num) + product.getNum());
        }
        // 产品信息map
        Map<Long, PProductEntity> productMap = new HashMap<>();
        List<PProductEntity> products = productDao.findAll(productNumMap.keySet());
        for (PProductEntity product : products) {
            productMap.put(product.getProductId(), product);
        }
        // 商品价格
        Integer regionId = UserUtil.getUserRegionId();
        Map<Long, Long> productPriceMap = new HashMap<>();
        if (!productMap.isEmpty()) {
//            List<PShiefEntity> prices = priceDao.findByProductIdInAndRegionId(productMap.keySet(), regionId);
//            for (PShiefEntity price : prices) {
//                productPriceMap.put(price.getProductId(), price.getPrice());
//            }
        }

        // 生成订单详情
        Long totalAmount = 0L;
        Integer totalnumber = 0;
        List<MOrderDetailEntity> details = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : productNumMap.entrySet()) {
            Long price = productPriceMap.get(entry.getKey());
            PProductEntity product = productMap.get(entry.getKey());
            if (null == price) {
                throw new WRWException("商品信息已失效");
            }

            MOrderDetailEntity detail = ConverterService.convert(product, MOrderDetailEntity.class);
            detail.setOrderId(orderId);
            detail.setPrice(price);
            detail.setNum(entry.getValue());
            detail.setAmount(detail.getNum() * price);
            detail.setPrefixCode(getPrefixCode(submitDto, product.getProductId()));
            details.add(detail);

            totalAmount += detail.getAmount();
            totalnumber += detail.getNum();
        }
        orderDetailDao.save(details);

        // 校验订单金额
        if (totalAmount <= 0) {
            throw new WRWException("该订单信息有误，请重新下单");
        }
        if (totalAmount > 1000000000) {
            throw new WRWException("coupon:订单金额过大，无法下单");
        }

        // 生成订单
        MOrderMainEntity order = new MOrderMainEntity();

        order.setOrderId(orderId);
        order.setMemberId(0L);
        order.setStatus(OrderStatus.WAIT_PAY.getKey());
        order.setPaymentStatus(PaymentStatus.unpaid.getKey());
        order.setPaymentType(submitDto.getPaymentType());
        order.setPressedFlag(OrderPressedStatus.UNPRESSED.getKey());
        order.setIfException(OrderExceptionEnum.NORMAL.getKey());
        order.setTotalAmount(totalAmount);
        order.setActualAmount(totalAmount);
        order.setCouponAmount(0L);
        order.setShipAmount(0L);
        order.setTotalNum(totalnumber);
        order.setRegionId(regionId);
        order.setCreateDate(new Date());
        order.setSource(submitDto.getSource());
        order.setOrgId(UserUtil.getUserOrgId());
        order.setOrgName(UserUtil.getStoreInfo());
        orderMainDao.save(order);

        // 订单操作记录
        saveOrderOperLog(orderId, OrderStatus.WAIT_PAY.getKey(), UserUtil.getUserId());

        // 处理结果返回
        OfflineOrderSubmitRespDto responseDto = new OfflineOrderSubmitRespDto();
        responseDto.setOrderId(orderId);
        responseDto.setActualAmount(formatNumber(order.getActualAmount()));

        return responseDto;
    }

    /**
     * Description:
     *
     * @param submitDto
     * @param productId
     * @return
     */
    private String getPrefixCode(OfflineOrderSubmitReqDto submitDto, Long productId) {
        List<OfflineProductDto> productDtos = submitDto.getProducts();

        StringBuilder sb = new StringBuilder();
        for (OfflineProductDto offlineProductDto : productDtos) {
            if (productId.equals(offlineProductDto.getProductId())) {
                sb.append(offlineProductDto.getPrefixCode()).append(",");
            }
        }
        if (sb.length() != 0) {
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }
        return null;
    }

    private MOrderOperEntity saveOrderOperLog(String orderId, String operType, Long memberId) {
        MOrderOperEntity orderOper = new MOrderOperEntity();
        orderOper.setOrderId(orderId);
        orderOper.setOpertype(operType);
        orderOper.setComment("订单状态更新:" + OrderStatus.getValue(operType));
        orderOper.setUserId(memberId);
        orderOper.setOperDate(new Date());
        return orderOper;
    }

    private Double formatNumber(long amount) {
        BigDecimal decimal = new BigDecimal(amount);
        decimal = decimal.divide(new BigDecimal(100));
        return decimal.setScale(2, BigDecimal.ROUND_UNNECESSARY).doubleValue();
    }

}
