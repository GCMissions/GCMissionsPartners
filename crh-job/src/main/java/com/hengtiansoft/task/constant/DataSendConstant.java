package com.hengtiansoft.task.constant;

/**
 * 
* Class Name: RequestUrl
* Description: TODO
* @author huizhuang
*
 */
public final class DataSendConstant {
    
    // 条码信息转发接口URL
    public static final String SEND_BARCODE_URL = "http://183.131.27.120/zc-collect-datasync/web/barcode/save";
    
    // 物料信息转发接口URL
    public static final String SEND_PRODUCT_URL = "http://183.131.27.120/zc-collect-datasync/web/product/save";
    
    // 数据一次性转发数量限制
    public static final int DATA_FETCH_LIMIT = 200;
    
//      // 条码信息转发接口URL
//      public static final String SEND_BARCODE_URL = "http://localhost:8090/zc-collect-datasync/web/barcode/save";
//      
//      // 物料信息转发接口URL
//      public static final String SEND_PRODUCT_URL = "http://localhost:8090/zc-collect-datasync/web/product/save";
//      
//      // 数据一次性转发数量限制
//      public static final int DATA_FETCH_LIMIT = 1000;
}
