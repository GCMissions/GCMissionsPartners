package com.hengtiansoft.business.pay.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.hengtiansoft.business.pay.service.LlpayRefundService;
import com.hengtiansoft.business.pay.service.dto.LlpayRefundDto;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.wrw.common.util.llpay.LLPayUtil;
import com.hengtiansoft.wrw.common.util.llpay.PartnerConfig;
import com.hengtiansoft.wrw.common.util.llpay.conn.HttpRequestSimple;

@Service
public class LlpayRefundServiceImpl implements LlpayRefundService {

    
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
    
    @Override
    public String llpayRefund(String refundFee, String refundOrderId, String tsn) {
        LlpayRefundDto dto = new LlpayRefundDto();
        String currentTime = DateTimeUtil.getCurrentTime(DateTimeUtil.SIMPLE_SECONDS);
        dto.setNo_refund(refundOrderId + currentTime);
        dto.setOid_partner(PartnerConfig.OID_PARTNER);
        dto.setDt_refund(formatter.format(new Date()));
        dto.setOid_paybill(tsn);
        dto.setSign_type(PartnerConfig.SIGN_TYPE);
        DecimalFormat decimalFormat = new DecimalFormat("##,##0.00");
        dto.setMoney_refund(decimalFormat.format(Long.valueOf(refundFee).doubleValue() / 100));
        dto.setNotify_url(PartnerConfig.NOTIFY_URL);
        String sign = LLPayUtil.addSign(JSON.parseObject(JSON.toJSONString(dto)), PartnerConfig.TRADER_PRI_KEY, PartnerConfig.MD5_KEY);
        dto.setSign(sign);
        String resJSON = HttpRequestSimple.getInstance().postSendHttp(PartnerConfig.GATE_WAY, JSON.toJSONString(dto));
        Gson gson = new Gson();
        Map<String, String> response = gson.fromJson(resJSON, Map.class);
        System.out.println("连连支付退款结果：" + response);
        if("0000".equals(response.get("ret_code"))){
           return "SUCCESS"; 
        }
        return null;
    }

}
