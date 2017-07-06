package com.hengtiansoft.common.util.pay.ib;

import com.hengtiansoft.common.util.AppConfigUtil;

public class IbPayConfig {
    // client number
    private static final String customerNo = AppConfigUtil.getConfig("ibpay.pay.customerNo");
    // business number
    private static final String merchantNo = AppConfigUtil.getConfig("ibpay.pay.merchantNo");

    // key
    private static final String key = AppConfigUtil.getConfig("ibpay.pay.key");

    // payment url
    private static final String gateWay = AppConfigUtil.getConfig("ibpay.pay.gateway");

    // Search the payment status of the order
    private static final String queryUrl = AppConfigUtil.getConfig("ibpay.pay.queryurl");

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
