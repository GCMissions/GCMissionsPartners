package com.hengtiansoft.common.util.pay.ib;

import com.hengtiansoft.common.util.AppConfigUtil;

public class IbPayConfig {
    // 客户号
    private static final String customerNo = AppConfigUtil.getConfig("ibpay.pay.customerNo");
    // 商户号
    private static final String merchantNo = AppConfigUtil.getConfig("ibpay.pay.merchantNo");

    // key
    private static final String key        = AppConfigUtil.getConfig("ibpay.pay.key");

    // 支付url
    private static final String gateWay    = AppConfigUtil.getConfig("ibpay.pay.gateway");

    // 查询订单支付状态url
    private static final String queryUrl   = AppConfigUtil.getConfig("ibpay.pay.queryurl");

    public static String getCustomerno() {
        return customerNo;
    }

    public static String getMerchantno() {
        return merchantNo;
    }

    public static String getKey() {
        return key;
    }

    public static String getGateway() {
        return gateWay;
    }

    public static String getQueryurl() {
        return queryUrl;
    }

}
