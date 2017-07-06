package com.hengtiansoft.common.constant;

import com.hengtiansoft.common.util.StringUtil;

/**
 * 
 * Class Name: CommonConstants
 * 
 * Description:
 * 
 * @author taochen
 *
 */
public class CommonConstants {

    public static final String line_order_makeupTemplateUrl = "line.order.makeupTemplateUrl";

    public static String getProperty(String key) {
        return StringUtil.getProperty(key, "/env/env-var.properties");
    }

}
