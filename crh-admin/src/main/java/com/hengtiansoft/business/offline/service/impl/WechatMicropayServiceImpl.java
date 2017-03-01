/*
 * Project Name: wrw-admin
 * File Name: MposWechatMicropayServiceImpl.java
 * Class Name: MposWechatMicropayServiceImpl
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

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.common.service.OfflinePayActionListener;
import com.hengtiansoft.business.common.util.HttpClientBuilderAdapter;
import com.hengtiansoft.business.common.util.ProductUtil;
import com.hengtiansoft.business.offline.dto.WechatMicropayReqDto;
import com.hengtiansoft.business.offline.dto.WechatMicropayRespDto;
import com.hengtiansoft.business.offline.dto.WechatOrderQueryReqDto;
import com.hengtiansoft.business.offline.service.WechatMicropayService;
import com.hengtiansoft.common.exception.BizServiceException;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.HttpUtils;
import com.hengtiansoft.wrw.dao.MOrderDetailDao;
import com.hengtiansoft.wrw.dao.MOrderMainDao;
import com.hengtiansoft.wrw.entity.MOrderDetailEntity;
import com.hengtiansoft.wrw.entity.MOrderMainEntity;
import com.hengtiansoft.wrw.enums.PaymentType;
import com.tencent.common.Configure;
import com.tencent.common.Signature;
import com.tencent.common.Util;
import com.tencent.protocol.pay_protocol.ScanPayReqData;
import com.tencent.protocol.pay_protocol.ScanPayResData;
import com.tencent.protocol.pay_query_protocol.ScanPayQueryReqData;
import com.tencent.protocol.pay_query_protocol.ScanPayQueryResData;
import com.tencent.protocol.reverse_protocol.ReverseReqData;
import com.tencent.protocol.reverse_protocol.ReverseResData;
import com.tencent.service.ReverseService;
import com.tencent.service.ScanPayQueryService;
import com.tencent.service.ScanPayService;

/**
 * Class Name: MposWechatMicropayServiceImpl
 * Description: TODO
 * 
 * @author xiaoluzhou
 */
@Service
public class WechatMicropayServiceImpl implements WechatMicropayService {

    private static final Logger      log                                     = LoggerFactory.getLogger(WechatMicropayServiceImpl.class);

    @Autowired
    private MOrderMainDao            orderMainDao;

    @Autowired
    private MOrderDetailDao          orderDetailDao;

    @Autowired
    private OfflinePayActionListener payListener;

    @Autowired
    private HttpServletRequest       request;

    @Value("${wechat.appid}")
    private String                   appID;

    @Value("${wechat.mchid}")
    private String                   mchID;

    @Value("${wechat.apikey}")
    private String                   privateKey;

    // 循环调用订单查询API的次数
    private int                      payQueryLoopInvokedCount                = 3;

    // 是否需要再调一次撤销，这个值由撤销API回包的recall字段决定
    private boolean                  needRecallReverse;

    // 每次调用撤销API的等待时间
    private int                      waitingTimeBeforeReverseServiceInvoked  = 5000;

    // 每次调用订单查询API时的等待时间，因为当出现支付失败的时候，如果马上发起查询不一定就能查到结果，所以这里建议先等待一定时间再发起查询
    private int                      waitingTimeBeforePayQueryServiceInvoked = 5000;

    private ScanPayQueryService      scanPayQueryService;

    private ScanPayService           scanPayService;

    private ReverseService           reverseService;

    private boolean                  flag;

    @PostConstruct
    public void init() {

        Configure.setAppID(appID);
        Configure.setMchID(mchID);
        Configure.setKey(privateKey);
        Configure.setHttpsRequestClassName(HttpClientBuilderAdapter.class.getName());

        try {
            scanPayQueryService = new ScanPayQueryService();
            scanPayService = new ScanPayService();
            reverseService = new ReverseService();
        } catch (Exception e) {
            log.error("初始化微信支付sdk服务失败", e);
            throw new BizServiceException("初始化微信支付sdk服务失败");
        }
        flag = true;
    }

    @Override
    public WechatMicropayRespDto wechatMicropay(WechatMicropayReqDto reqDto) {
        WechatMicropayRespDto respDto = new WechatMicropayRespDto();
        String orderId = reqDto.getOrderId();
        MOrderMainEntity mainOrder = orderMainDao.findOne(orderId);
        if (mainOrder == null) {
            respDto.setCode("FAIL");
            respDto.setMessage("未找到要支付的订单" + orderId);
            return respDto;
        }
        mainOrder.setPaymentType(PaymentType.WECHAT.getKey());
        orderMainDao.save(mainOrder);

        List<MOrderDetailEntity> details = orderDetailDao.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(details)) {
            throw new BizServiceException("未找到要支付订单的订单详情");
        }
        ScanPayReqData scanPayReqData = new ScanPayReqData(reqDto.getAuthCode(), generateBody(details), reqDto.getAttach(), orderId, mainOrder
                .getActualAmount().intValue(), reqDto.getDeviceInfo(), HttpUtils.getUserIP(request), DateTimeUtil.getFormatDate(mainOrder.getCreateDate(),
                DateTimeUtil.SIMPLE_SECONDS), null, null);
        try {
            if (!flag || scanPayService == null) {
                init();
            }
            String payServiceResponseString = scanPayService.request(scanPayReqData);
            log.info("微信支付返回结果：" + payServiceResponseString);
            ScanPayResData scanPayResData = (ScanPayResData) Util.getObjectFromXML(payServiceResponseString, ScanPayResData.class);
            if (scanPayResData == null || scanPayResData.getReturn_code() == null) {
                log.info("【支付失败】支付请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问");
                respDto.setCode("UNKNOWN");
                respDto.setMessage("【支付失败】支付请求逻辑错误，请仔细检测传过去的每一个参数是否合法");
                return respDto;
            }

            if (scanPayResData.getReturn_code().equals("FAIL")) {
                // 注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
                log.error("【支付失败】支付API系统返回失败，请检测Post给API的数据是否规范合法");
                respDto.setCode(scanPayResData.getReturn_code());
                respDto.setMessage(scanPayResData.getReturn_msg());
                return respDto;
            } else {
                log.info("支付API系统成功返回数据");

                if (!Signature.checkIsSignValidFromResponseString(payServiceResponseString)) {
                    log.error("【支付失败】支付请求API返回的数据签名验证失败，有可能数据被篡改了");
                    respDto.setCode("FAIL");
                    respDto.setMessage("【支付失败】支付请求API返回的数据签名验证失败，有可能数据被篡改了");
                    return respDto;
                }

                if (scanPayResData.getResult_code().equals("SUCCESS")) {

                    // --------------------------------------------------------------------
                    // 1)直接扣款成功
                    // --------------------------------------------------------------------
                    payListener.onPaySuccess(ProductUtil.getPrefixCodes(details), orderId, scanPayResData.getTransaction_id(),
                            Long.parseLong(scanPayResData.getTotal_fee()), PaymentType.WECHAT.getKey());
                    log.info("【一次性支付成功】");
                    respDto.setCode("SUCCESS");
                    respDto.setMessage("【一次性支付成功】");
                    return respDto;
                } else {

                    String errorCode = scanPayResData.getErr_code();
                    String errorCodeDes = scanPayResData.getErr_code_des();
                    // 出现业务错误
                    log.info("业务返回失败");
                    log.info("err_code:" + errorCode);
                    log.info("err_code_des:" + errorCodeDes);
                    respDto.setCode("FAIL");
                    respDto.setMessage(errorCodeDes);
                    // 业务错误时错误码有好几种，商户重点提示以下几种
                    if ("AUTHCODEEXPIRE".equals(errorCode) || "AUTH_CODE_INVALID".equals(errorCode) || "NOTENOUGH".equals(errorCode)) {

                        // --------------------------------------------------------------------
                        // 2)扣款明确失败
                        // --------------------------------------------------------------------

                        // 对于扣款明确失败的情况直接走撤销逻辑
                        doReverseLoop(reqDto.getOrderId());

                        // 以下几种情况建议明确提示用户，指导接下来的工作
                        if ("AUTHCODEEXPIRE".equals(errorCode)) {
                            // 表示用户用来支付的二维码已经过期，提示收银员重新扫一下用户微信“刷卡”里面的二维码
                            log.warn("【支付扣款明确失败】原因是：" + errorCodeDes);
                        } else if ("AUTH_CODE_INVALID".equals(errorCode)) {
                            // 授权码无效，提示用户刷新一维码/二维码，之后重新扫码支付
                            log.warn("【支付扣款明确失败】原因是：" + errorCodeDes);
                        } else if ("NOTENOUGH".equals(errorCode)) {
                            // 提示用户余额不足，换其他卡支付或是用现金支付
                            log.warn("【支付扣款明确失败】原因是：" + errorCodeDes);
                        }
                        return respDto;
                    } else if ("USERPAYING".equals(errorCode)) {

                        // --------------------------------------------------------------------
                        // 3)需要输入密码
                        // --------------------------------------------------------------------

                        // 表示有可能单次消费超过300元，或是免输密码消费次数已经超过当天的最大限制，这个时候提示用户输入密码，商户自己隔一段时间去查单，查询一定次数，看用户是否已经输入了密码
                        if (doPayQueryLoop(payQueryLoopInvokedCount, reqDto.getOrderId())) {
                            log.warn("【需要用户输入密码、查询到支付成功】");
                            respDto.setCode("SUCCESS");
                            respDto.setMessage("【支付成功】");
                            return respDto;
                        } else {
                            log.warn("【需要用户输入密码、在一定时间内没有查询到支付成功、走撤销流程】");
                            doReverseLoop(reqDto.getOrderId());
                            respDto.setCode("FAIL");
                            respDto.setMessage("【支付扣款未知失败、已撤销支付】");
                            return respDto;
                        }
                    } else {

                        // --------------------------------------------------------------------
                        // 4)扣款未知失败
                        // --------------------------------------------------------------------

                        if (doPayQueryLoop(payQueryLoopInvokedCount, reqDto.getOrderId())) {
                            log.warn("【支付扣款未知失败、查询到支付成功】");
                            respDto.setCode("SUCCESS");
                            respDto.setMessage("【支付成功】");
                            return respDto;
                        } else {
                            log.warn("【支付扣款未知失败、在一定时间内没有查询到支付成功、走撤销流程】");
                            doReverseLoop(reqDto.getOrderId());
                            respDto.setCode("FAIL");
                            respDto.setMessage("【支付扣款未知失败、已撤销支付】");
                            return respDto;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("支付发生异常", e);
            respDto.setCode("UNKNOWN");
            respDto.setMessage("【支付发生异常，请主动发起查询请求查看支付结果】");
        }
        return respDto;
    }

    /**
     * Description:
     *
     * @param mainOrder
     * @return
     */
    private String generateBody(List<MOrderDetailEntity> details) {
        String productName = details.get(0).getProductName();
        if (details.size() > 1) {
            productName = productName + "等";
        }
        if (productName.length() > 40) {
            productName = productName.substring(0, 39) + "...";
        }
        return productName;
    }

    /**
     * Description:
     *
     * @param payQueryLoopInvokedCount2
     * @param orderId
     * @return
     * @throws Exception
     */
    private boolean doPayQueryLoop(int loopCount, String outTradeNo) throws Exception {
        // 至少查询一次
        if (loopCount == 0) {
            loopCount = 1;
        }
        // 进行循环查询
        for (int i = 0; i < loopCount; i++) {
            if (doOnePayQuery(outTradeNo)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 进行一次支付订单查询操作
     *
     * @param outTradeNo
     *            商户系统内部的订单号,32个字符内可包含字母, [确保在商户系统唯一]
     * @return 该订单是否支付成功
     * @throws Exception
     */
    private boolean doOnePayQuery(String outTradeNo) throws Exception {

        Thread.sleep(waitingTimeBeforePayQueryServiceInvoked);// 等待一定时间再进行查询，避免状态还没来得及被更新

        String payQueryServiceResponseString;

        ScanPayQueryReqData scanPayQueryReqData = new ScanPayQueryReqData("", outTradeNo);
        payQueryServiceResponseString = scanPayQueryService.request(scanPayQueryReqData);

        log.info("支付订单查询API返回的数据如下：");
        log.info(payQueryServiceResponseString);

        // 将从API返回的XML数据映射到Java对象
        ScanPayQueryResData scanPayQueryResData = (ScanPayQueryResData) Util.getObjectFromXML(payQueryServiceResponseString,
                ScanPayQueryResData.class);
        if (scanPayQueryResData == null || scanPayQueryResData.getReturn_code() == null) {
            log.info("支付订单查询请求逻辑错误，请仔细检测传过去的每一个参数是否合法");
            return false;
        }

        if (scanPayQueryResData.getReturn_code().equals("FAIL")) {
            // 注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
            log.info("支付订单查询API系统返回失败，失败信息为：" + scanPayQueryResData.getReturn_msg());
            return false;
        } else {
            if (scanPayQueryResData.getResult_code().equals("SUCCESS")) {// 业务层成功
                if (scanPayQueryResData.getTrade_state().equals("SUCCESS")) {
                    // 表示查单结果为“支付成功”
                    log.info("查询到订单支付成功");
                    return true;
                } else {
                    // 支付不成功
                    log.info("查询到订单支付不成功");
                    return false;
                }
            } else {
                log.info("查询出错，错误码：" + scanPayQueryResData.getErr_code() + "     错误信息：" + scanPayQueryResData.getErr_code_des());
                return false;
            }
        }
    }

    /**
     * Description:
     *
     * @param orderId
     * @throws Exception
     */
    private void doReverseLoop(String outTradeNo) throws Exception {
        // 初始化这个标记
        needRecallReverse = true;
        // 进行循环撤销，直到撤销成功，或是API返回recall字段为"Y"
        while (needRecallReverse) {
            if (doOneReverse(outTradeNo)) {
                return;
            }
        }

    }

    /**
     * 进行一次撤销操作
     *
     * @param outTradeNo
     *            商户系统内部的订单号,32个字符内可包含字母, [确保在商户系统唯一]
     * @return 该订单是否支付成功
     * @throws Exception
     */
    private boolean doOneReverse(String outTradeNo) throws Exception {

        // 等待一定时间再进行查询，避免状态还没来得及被更新
        Thread.sleep(waitingTimeBeforeReverseServiceInvoked);

        String reverseResponseString;

        ReverseReqData reverseReqData = new ReverseReqData("", outTradeNo);
        reverseResponseString = reverseService.request(reverseReqData);

        log.info("撤销API返回的数据如下：");
        log.info(reverseResponseString);
        // 将从API返回的XML数据映射到Java对象
        ReverseResData reverseResData = (ReverseResData) Util.getObjectFromXML(reverseResponseString, ReverseResData.class);
        if (reverseResData == null) {
            log.info("支付订单撤销请求逻辑错误，请仔细检测传过去的每一个参数是否合法");
            return false;
        }
        if (reverseResData.getReturn_code().equals("FAIL")) {
            // 注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
            log.info("支付订单撤销API系统返回失败，失败信息为：" + reverseResData.getReturn_msg());
            return false;
        } else {
            if (reverseResData.getResult_code().equals("FAIL")) {
                log.info("撤销出错，错误码：" + reverseResData.getErr_code() + "     错误信息：" + reverseResData.getErr_code_des());
                if (reverseResData.getRecall().equals("Y")) {
                    // 表示需要重试
                    needRecallReverse = true;
                    return false;
                } else {
                    // 表示不需要重试，也可以当作是撤销成功
                    needRecallReverse = false;
                    return true;
                }
            } else {
                // 查询成功，打印交易状态
                log.info("支付订单撤销成功");
                return true;
            }
        }
    }

    @Override
    public ScanPayQueryResData scanPayQuery(WechatOrderQueryReqDto reqDto) {
        ScanPayQueryResData scanPayQueryResData = null;
        try {
            ScanPayQueryReqData reqDta = new ScanPayQueryReqData(reqDto.getTransactionId(), reqDto.getOutTradeNo());
            if (!flag || scanPayQueryService == null) {
                init();
            }
            long start = System.currentTimeMillis();
            String result = scanPayQueryService.request(reqDta);
            log.info("查询耗时" + (System.currentTimeMillis() - start));
            // 将从API返回的XML数据映射到Java对象
            scanPayQueryResData = (ScanPayQueryResData) Util.getObjectFromXML(result, ScanPayQueryResData.class);
        } catch (Exception e) {
            log.error("查询发生异常", e);
        }
        return scanPayQueryResData;
    }

}
