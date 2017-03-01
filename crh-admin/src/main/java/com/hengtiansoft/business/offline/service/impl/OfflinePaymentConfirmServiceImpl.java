/*
 * Project Name: wrw-admin
 * File Name: OfflinePaymentConfirmServiceImpl.java
 * Class Name: OfflinePaymentConfirmServiceImpl
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
package com.hengtiansoft.business.offline.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.response.AlipayTradeQueryResponse;
import com.hengtiansoft.business.common.service.OfflinePayActionListener;
import com.hengtiansoft.business.common.util.ProductUtil;
import com.hengtiansoft.business.offline.dto.AliTradeQueryReqDto;
import com.hengtiansoft.business.offline.dto.OfflinePaymentConfirmReqDto;
import com.hengtiansoft.business.offline.dto.OfflinePaymentConfirmRespDto;
import com.hengtiansoft.business.offline.dto.WechatOrderQueryReqDto;
import com.hengtiansoft.business.offline.service.AlipayService;
import com.hengtiansoft.business.offline.service.WechatMicropayService;
import com.hengtiansoft.business.offline.service.OfflinePaymentConfirmService;
import com.hengtiansoft.common.exception.BizServiceException;
import com.hengtiansoft.wrw.dao.MOrderDetailDao;
import com.hengtiansoft.wrw.dao.MOrderMainDao;
import com.hengtiansoft.wrw.entity.MOrderDetailEntity;
import com.hengtiansoft.wrw.entity.MOrderMainEntity;
import com.hengtiansoft.wrw.enums.PaymentType;
import com.tencent.protocol.pay_query_protocol.ScanPayQueryResData;

/**
 * Class Name: OfflinePaymentConfirmServiceImpl
 * Description: 
 * @author xiaoluzhou
 *
 */
@Service
public class OfflinePaymentConfirmServiceImpl implements OfflinePaymentConfirmService{
    
    @Autowired
    private AlipayService alipayService;
    
    @Autowired
    private WechatMicropayService wechatMicropayService;
    
    @Autowired
    private MOrderMainDao  mainOrderDao;
    
    @Autowired
    private MOrderDetailDao orderDetailDao;
    
    @Autowired
    private OfflinePayActionListener payActionListener;

    @Override
    public OfflinePaymentConfirmRespDto confirm(OfflinePaymentConfirmReqDto reqDto) {
        String orderNo = reqDto.getOrderNo();
        MOrderMainEntity mainOrder = mainOrderDao.findOne(orderNo);
        if(mainOrder == null){
            throw new BizServiceException("未找到对应的订单信息");
        }
        String tsn = mainOrder.getTsn();
        
        Long amount = mainOrder.getActualAmount();
        
        List<MOrderDetailEntity> orderDetails = orderDetailDao.findByOrderId(orderNo);
        if(CollectionUtils.isEmpty(orderDetails)){
            throw new BizServiceException("未找到对应的订单的详细信息");
        }
        
        OfflinePaymentConfirmRespDto respDto = new OfflinePaymentConfirmRespDto();
        
//        if(PaymentType.ALIPAY.getKey().equals(mainOrder.getPaymentType())){
//            AliTradeQueryReqDto aliReqDto = new AliTradeQueryReqDto();
//            aliReqDto.setOutTradeNo(orderNo);
//            aliReqDto.setTradeNo(tsn);
//            AlipayTradeQueryResponse aliResp = alipayService.tradeQuery(aliReqDto);
//            if(aliResp != null && "TRADE_SUCCESS".equals(aliResp.getTradeStatus())){
////                payActionListener.onPaySuccess(ProductUtil.getPrefixCodes(orderDetails), orderNo, 
////                        aliResp.getTradeNo(), amount, PaymentType.ALIPAY.getKey());
//                respDto.setCode("SUCCESS");
//            }
//        }else if (PaymentType.WECHAT.getKey().equals(mainOrder.getPaymentType())) {
//            WechatOrderQueryReqDto wechatReqDto = new WechatOrderQueryReqDto();
//            wechatReqDto.setOutTradeNo(orderNo);
//            wechatReqDto.setTransactionId(tsn);
//            ScanPayQueryResData wechatResp = wechatMicropayService.scanPayQuery(wechatReqDto);
//            if(wechatResp != null && "SUCCESS".equals(wechatResp.getTrade_state())){
//                payActionListener.onPaySuccess(ProductUtil.getPrefixCodes(orderDetails), orderNo, 
//                        wechatResp.getTransaction_id(), amount, PaymentType.WECHAT.getKey());
//                respDto.setCode("SUCCESS");
//            }
//        }
        return respDto;
    }


}
