/*
 * Project Name: kd-wechat
 * File Name: UnionPayPlugin.java
 * Class Name: UnionPayPlugin
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
package com.hengtiansoft.business.pay.plugin;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hengtiansoft.business.pay.constant.PayConstants;
import com.hengtiansoft.business.pay.dto.PaymentParamDto;
import com.hengtiansoft.common.constant.ResultCode;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKUtil;

/**
 * Class Name: UnionPayPlugin
 * Description: 银联支付（无跳转支付-标准版）
 * @author zhongyidong
 *
 */
@Component
public class UnionPayPlugin{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UnionPayPlugin.class);
    
    public UnionPayPlugin() {
        initUnionPayConfig();
    }

    /**
     * UnionPayPlugin Constructor 加载银联支付配置文件.
     */
    public void initUnionPayConfig() {
        String basePath = getClass().getResource("/").getPath();

        Properties pro = new Properties();
        String filePath = basePath + PayConstants.UNION_FILENAME;
        InputStream input = null;
        try {
            input = new FileInputStream(filePath);
            pro.load(input);
            pro.setProperty(PayConstants.UNION_LOG_PATH, basePath + pro.getProperty(PayConstants.UNION_LOG_PATH));
            pro.setProperty(PayConstants.UNION_SIGN_CERT_PATH, basePath + pro.getProperty(PayConstants.UNION_SIGN_CERT_PATH));
            pro.setProperty(PayConstants.UNION_ENCRYPT_CERT_PATH, basePath + pro.getProperty(PayConstants.UNION_ENCRYPT_CERT_PATH));
            pro.setProperty(PayConstants.UNION_VALIDATE_CERT_DIR, basePath + pro.getProperty(PayConstants.UNION_VALIDATE_CERT_DIR));
            SDKConfig.getConfig().loadProperties(pro);
        } catch (IOException e) {
            LOGGER.error("加载银联支付配置文件失败！{}", e);
        } finally {
            IOUtils.closeQuietly(input);
        }
    }
    
    /**
     * Description: 设置退款请求表单数据.
     *
     * @param orderNo
     * @return
     */
    public Map<String, String> refundForm(PaymentParamDto paramDto) {
        Map<String, String> contentData = getFixedInfo();

        // M,取值：（01：消费，04：退货）
        contentData.put("txnType", "04"); 
        // M,（00：默认值，01：自助消费，通过地址的方式区分前台消费和后台消费（含无跳转支付））
        contentData.put("txnSubType", "00"); 

        // M,后台通知地址
        contentData.put("backUrl", "http://wely.hengtiansoft.com/kd-wechat/unionPay/refundNotify");

        // 商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则，重新产生，不同于原消费
        contentData.put("orderId", paramDto.getOrderNo());
        contentData.put("txnAmt", paramDto.getTxnAmount()); 
        contentData.put("currencyCode", "156");
        
        //M,原消费交易返回的的queryId，可以从消费交易后台通知接口中或者交易状态查询接口中获取
        contentData.put("origQryId", paramDto.getOrigQryId());
        
        return contentData;
    }
    
    /**
     * Description: 获取固定的表单信息
     *
     * @return
     */
    public Map<String, String> getFixedInfo() {
        Map<String, String> contentData = new HashMap<String, String>();
        
        // M,版本号,固定填写
        contentData.put("version", PayConstants.UNION_VERSION); 
        // M,字符集编码,默认取值：UTF-8
        contentData.put("encoding", PayConstants.UNION_ENCODING); 
        // M,01:RSA,02:MD5 (暂不支持)
        contentData.put("signMethod", "01");
        // M,0：普通商户直连接入1：收单机构接入2：平台类商户接入
        contentData.put("accessType", "0");
        // M,产品类型，（000201：B2C网关支付，000301：无跳转（商户侧），000902: Token支付）
        contentData.put("bizType", "000301");
        // M,渠道类型（07：互联网 08：移动 ）
        contentData.put("channelType", "07");
        // M,商户号
        contentData.put("merId", PayConstants.UNION_MERID);
        // M,订单发送时间（格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效）
        contentData.put("txnTime", getCurrentTime());
        
        return contentData;
    }
    
    /**
     * Description: 退款交易
     *
     * @param paramDto 退款信息
     * 
     * @return
     */
    public ResultDto<String> refund(PaymentParamDto paramDto){
        Map<String, String> data = refundForm(paramDto);
        String reqUrl = SDKConfig.getConfig().getBackRequestUrl();
        ResultDto<Map<String, String>> result = getResponseData(data, reqUrl);
        if (ResultCode.NACK.equals(result.getCode())) {
            return ResultDtoFactory.toAck("退款失败！", result.getMessage()); 
        }
        
        // 后续需发起交易状态查询交易确定交易状态
        return ResultDtoFactory.toAck("交易已受理，等待结果通知！");
    }
    
    /**
     * Description: 发送后台请求并获取处理结果
     *
     * @param oriData
     * @param reqUrl
     * @return
     */
    private ResultDto<Map<String, String>> getResponseData(Map<String, String> oriData, String reqUrl) {
        // 加密请求数据
        Map<String, String> reqData = AcpService.signData(oriData);
        Map<String, String> rspData =  AcpService.post(reqData, reqUrl);
        if (rspData.isEmpty()) {
            LOGGER.error("[unionPay]未获取到返回报文或返回http状态码非200");
            return ResultDtoFactory.toNack("未获取到返回报文或返回http状态码非200");
        }
        //　验签
        if (SDKUtil.validate(rspData, PayConstants.UNION_ENCODING)) {
            String respCode = rspData.get("respCode") ;
            if ("00".equals(respCode)) {
                return ResultDtoFactory.toAck("请求处理成功！", rspData);
            } else {
                return ResultDtoFactory.toNack(rspData.get("respMsg"), rspData);
            }
        }

        return ResultDtoFactory.toNack("验证签名失败！", rspData);
    }
    
    /**
     * Description: 商户发送交易时间
     *
     * @return
     */
    public String getCurrentTime() {
        // 格式:YYYYMMDDhhmmss
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(new Date());
    }
    
}
