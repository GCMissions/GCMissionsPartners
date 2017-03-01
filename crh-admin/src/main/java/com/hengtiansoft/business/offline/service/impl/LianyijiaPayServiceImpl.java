/*
 * Project Name: wrw-admin
 * File Name: LianyijiaPayServiceImpl.java
 * Class Name: LianyijiaPayServiceImpl
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

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.hengtiansoft.business.common.service.OfflinePayActionListener;
import com.hengtiansoft.business.common.util.ProductUtil;
import com.hengtiansoft.business.offline.dto.LianyijiaBalanceQueryReqDto;
import com.hengtiansoft.business.offline.dto.LianyijiaBalanceQueryRespData;
import com.hengtiansoft.business.offline.dto.LianyijiaBalanceQueryRespDto;
import com.hengtiansoft.business.offline.dto.LianyijiaCancelReqDto;
import com.hengtiansoft.business.offline.dto.LianyijiaCancelRespData;
import com.hengtiansoft.business.offline.dto.LianyijiaCancelRespDto;
import com.hengtiansoft.business.offline.dto.LianyijiaConsumeReqDto;
import com.hengtiansoft.business.offline.dto.LianyijiaConsumeRespData;
import com.hengtiansoft.business.offline.dto.LianyijiaConsumeRespDto;
import com.hengtiansoft.business.offline.dto.LianyijiaGateWayRespDto;
import com.hengtiansoft.business.offline.dto.LianyijiaReverseReqDto;
import com.hengtiansoft.business.offline.dto.LianyijiaReverseRespData;
import com.hengtiansoft.business.offline.dto.LianyijiaReverseRespDto;
import com.hengtiansoft.business.offline.service.LianyijiaPayService;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.exception.BizServiceException;
import com.hengtiansoft.common.util.SHACoder;
import com.hengtiansoft.wrw.dao.MOrderMainDao;
import com.hengtiansoft.wrw.entity.MOrderMainEntity;
import com.hengtiansoft.wrw.enums.PaymentType;

/**
 * Class Name: LianyijiaPayServiceImpl
 * Description:
 * 
 * @author xiaoluzhou
 */
@Service
public class LianyijiaPayServiceImpl extends BaseLianyijiaPayAction implements LianyijiaPayService {
    private int                      reverse_time = 3;

    private static final String      SUCCESS_CODE = "00";

    private static final Logger      log          = LoggerFactory.getLogger(LianyijiaPayServiceImpl.class);

    @Autowired
    private MOrderMainDao            orderMainDao;

    @Value("${lianyijia_api_url}")
    private String                   apiUrl;

    @Value("${lianyijia_app_secret}")
    private String                   appSecret;
    
    @Value("${lianyijia_sha_key}")
    private String                   shaKey;
    
    @Value("${lianyijia_app_key}")
    private String                   appKey;
    
    @Value("${lianyijia_custom_no}")
    private String                   customNo;
    
    @Value("${lianyijia_ksn}")
    private String                   ksn;
    
    @Value("${lianyijia_channel_no}")
    private String                   channelNo;
    
    @Value("${lianyijia_pays_enum}")
    private String                   paysEnum;

    @Autowired
    private OfflinePayActionListener payListener;

    /*
     * 消费发生异常，除连接超时异常需要发起冲正；现定发起3次冲正，3次冲正失败的话，返回冲正失败的错误码跟错误消息。
     * 如果3次冲正都发生异常无返回的话，返回什么异常？
     */
    @Override
    public LianyijiaConsumeRespData consume(LianyijiaConsumeReqDto reqDto) {
        String orderNo = reqDto.getOrderNo();
        final MOrderMainEntity orderMain = orderMainDao.findOne(orderNo);
        if (orderMain == null) {
            throw new BizServiceException("未找到要支付的订单");
        }
//        orderMain.setPaymentType(PaymentType.LIANYIJIA.getKey());
        orderMainDao.save(orderMain);
        
        buildReqParams(reqDto, orderMain);
        
        LianyijiaConsumeRespData respDto = new LianyijiaConsumeRespData();
        Map<String, String> params = objectToMap(reqDto);
        if (params.size() == 0) {
            throw new BizServiceException("请设置请求参数");
        }
        String resp = null;
        try {
            resp = sendHttpRequest(apiUrl, params, appSecret, 5000, 70000, 5000);
            log.info(orderNo + "请求连亿家消费接口成功，返回数据为:" + resp);
        } catch (IOException e) {
            if (e instanceof ConnectTimeoutException) {
                respDto.setRespCode("FAIL");
                respDto.setRespMsg(e.getMessage());
                log.info(orderNo + "请求连亿家消费接口超时");
            } else {
                // 除连接超时外都发起冲正
                LianyijiaReverseRespData reverseRespDto = reverseAtferConsumeFailed(reqDto);
                if (reverseRespDto != null) {
                    respDto.setRespCode(reverseRespDto.getRespCode());
                    respDto.setRespMsg(reverseRespDto.getRespMsg());
                } else {
                    log.info("3次冲正都失败 ");
                    respDto.setRespCode("UNKNOW");
                    respDto.setRespMsg("支付发送异常，冲正也异常，请发起查询查看支付结果");
                }
            }
            return respDto;
        }
        if (StringUtils.isNotBlank(resp)) {
            Gson gson = new Gson();
            LianyijiaConsumeRespDto consumeRespDto = gson.fromJson(resp, LianyijiaConsumeRespDto.class);
            respDto = consumeRespDto.getLinkea_pays_query_sendConsume_response();
            if (SUCCESS_CODE.equals(respDto.getRespCode())) {
//                payListener.onPaySuccess(ProductUtil.getPrefixCodes(orderNo), orderNo, reqDto.getKsn(), new Double(Double.parseDouble(reqDto.getAmount())* 100).longValue(),
//                        PaymentType.LIANYIJIA.getKey());
            }else{
                //参数错误网关拦截掉
               if(resp.contains("result_code") && resp.contains("result_code_msg")){
                   LianyijiaGateWayRespDto gateWayRespDto = gson.fromJson(resp, LianyijiaGateWayRespDto.class);
                   respDto.setRespCode("FAIL");
                   respDto.setRespMsg(gateWayRespDto.getLinkea_pays_query_sendConsume_response().getResult_code_msg());
               }
            }
        } else {
            LianyijiaReverseRespData reverseRespDto = reverseAtferConsumeFailed(reqDto);
            if (reverseRespDto != null) {
                respDto.setRespCode(reverseRespDto.getRespCode());
                respDto.setRespMsg(reverseRespDto.getRespMsg());
            } else {
                log.info("支付发送异常，冲正结果未知");
                respDto.setRespCode("UNKNOW");
                respDto.setRespMsg("支付发送异常，冲正结果未知，请发起查询查看支付结果");
            }
        }
        return respDto;
    }

    private void buildReqParams(final LianyijiaConsumeReqDto reqDto, final MOrderMainEntity orderMain) {
        reqDto.setApp_key(appKey);
        reqDto.setCustomNo(customNo);
        reqDto.setKsn(ksn);
        reqDto.setChannelNo(channelNo);
        reqDto.setPaysEnum(paysEnum);
        
        reqDto.setAmount((new Double(orderMain.getActualAmount()) / 100) + "");
        try {
            reqDto.setCheckCode(getCheckCode(reqDto, shaKey));
        } catch (Exception e) {
            log.error("计算请求参数checkCode错误", e);
            throw new BizServiceException("计算请求参数checkCode错误, 请检查请求参数是否正确");
        }
        
    }
    
    private String getCheckCode(LianyijiaConsumeReqDto reqDto, String shaKey) throws Exception{
        StringBuilder sb = new StringBuilder();
        sb.append(reqDto.getCustomNo()).append(reqDto.getChannelNo()).append(reqDto.getAcctNo());
        if (StringUtils.isNotBlank(reqDto.getAmount())){
            sb.append(new Double(new Double(reqDto.getAmount()) * 100).longValue() );
        }
        sb.append(shaKey);
        return SHACoder.encodeSHA256((sb.toString()).getBytes());
    }

    private LianyijiaReverseRespData reverseAtferConsumeFailed(final LianyijiaConsumeReqDto reqDto) {
        LianyijiaReverseReqDto reverseReqDto = ConverterService.convert(reqDto, LianyijiaReverseReqDto.class);

        String orderNo = reqDto.getOrderNo();
        reverseReqDto.setOrigOrderNo(orderNo);
        LianyijiaReverseRespData reverseRespDto = null;
        for (int i = 0; i < reverse_time; i++) {
            reverseReqDto.setOrderNo(orderNo + (i + 1) + "r");
            try {
                Thread.sleep(3000);
                reverseRespDto = reverse(reverseReqDto);
                if (reverseRespDto != null) {
                    if (SUCCESS_CODE.equals(reverseRespDto.getRespCode())) {
                        log.info(reqDto.getOrderNo() + "请求连亿家消费接口失败，第" + i + "次发起冲正成功");
                        break;
                    } else {
                        log.info(reqDto.getOrderNo() + "请求连亿家消费接口失败，第" + i + "次发起冲正也失败");
                    }
                }
            } catch (InterruptedException e1) {
                // do noting
            }
        }
        return reverseRespDto;
    }

    @Override
    public LianyijiaCancelRespData cancel(LianyijiaCancelReqDto reqDto) {
        String orderNo = reqDto.getOrderNo();
        LianyijiaCancelRespData respDto = new LianyijiaCancelRespData();
        Map<String, String> params = objectToMap(reqDto);
        if (params.size() == 0) {
            throw new BizServiceException("请设置请求参数");
        }
        String resp = null;
        try {
            resp = sendHttpRequest(apiUrl, params, appSecret, 5000, 70000, 5000);
            log.info("撤销接口返回：" + resp);
        } catch (IOException e) {
            if (e instanceof ConnectTimeoutException) {
                log.info(orderNo + "撤销发生网络连接异常", e);
                respDto.setRespCode("FAIL");
                respDto.setRespMsg(e.getMessage());

            } else {
                log.info(orderNo + "撤销发生read timeout 异常", e);
                respDto.setRespCode("UNKNOW");
                respDto.setRespMsg(e.getMessage());
            }
            return respDto;
        }
        if (StringUtils.isNotBlank(resp)) {
            Gson gson = new Gson();
            LianyijiaCancelRespDto cancelRespDto = gson.fromJson(resp, LianyijiaCancelRespDto.class);
            return cancelRespDto.getLinkea_pays_query_sendCancel_response();
        } else {
            respDto.setRespCode("UNKNOW");
            respDto.setRespMsg("撤销异常，请发起查询查看撤销结果");
        }
        return respDto;
    }

    public LianyijiaReverseRespData reverse(LianyijiaReverseReqDto reqDto) {
        String orderNo = reqDto.getOrderNo();
        LianyijiaReverseRespData respDto = new LianyijiaReverseRespData();
        Map<String, String> params = objectToMap(reqDto);
        if (params.size() == 0) {
            throw new BizServiceException("请设置请求参数");
        }
        String resp = null;
        try {
            resp = sendHttpRequest(apiUrl, params, appSecret, 5000, 70000, 5000);
            log.info("冲正返回：" + resp);
        } catch (IOException e) {
            if (e instanceof ConnectTimeoutException) {
                log.info(orderNo + "冲正发生网络连接异常", e);
                respDto.setRespCode("FAIL");
                respDto.setRespMsg(e.getMessage());

            } else {
                log.info(orderNo + "冲正发生read timeout 异常", e);
                respDto.setRespCode("UNKNOW");
                respDto.setRespMsg(e.getMessage());
            }
            return respDto;
        }
        if (StringUtils.isNotBlank(resp)) {
            Gson gson = new Gson();
            LianyijiaReverseRespDto reverseRespDto = gson.fromJson(resp, LianyijiaReverseRespDto.class);
            return reverseRespDto.getLinkea_pays_query_sendReverse_response();
        } else {
            respDto.setRespCode("UNKNOW");
            respDto.setRespMsg("冲正失败，请发起查询查看冲正结果");
        }
        return respDto;
    }

    public LianyijiaBalanceQueryRespData queryBalance(LianyijiaBalanceQueryReqDto reqDto) {
        String acctNo = reqDto.getAcctNo();
        LianyijiaBalanceQueryRespData respDto = new LianyijiaBalanceQueryRespData();
        Map<String, String> params = objectToMap(reqDto);
        if (params.size() == 0) {
            throw new BizServiceException("请设置请求参数");
        }
        String resp = null;
        try {
            resp = sendHttpRequest(apiUrl, params, appSecret, 5000, 70000, 5000);
        } catch (IOException e) {
            if (e instanceof ConnectTimeoutException) {
                log.info(acctNo + "查询发生网络连接异常", e);
                respDto.setRespCode("ConnectTimeout");
                respDto.setRespMsg(e.getMessage());

            } else {
                log.info(acctNo + "查询发生read timeout 异常", e);
                respDto.setRespCode("UNKNOW");
                respDto.setRespMsg(e.getMessage());
            }
            return respDto;
        }
        if (StringUtils.isNotBlank(resp)) {
            Gson gson = new Gson();
            LianyijiaBalanceQueryRespDto queryRespDto = gson.fromJson(resp, LianyijiaBalanceQueryRespDto.class);
            return queryRespDto.getLinkea_pays_query_balance_response();
        } else {
            respDto.setRespCode("UNKNOW");
            respDto.setRespMsg("查询结果未知，请稍后再试");
        }
        return respDto;
    }

}
