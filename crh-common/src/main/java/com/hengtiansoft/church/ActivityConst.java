package com.hengtiansoft.church;

/**
 * Class Name: ActivityConst
 * Description: 服务类商品相关常量
 * @author zhongyidong
 *
 */
public class ActivityConst {
    private ActivityConst() {
        
    }
    
    public class StockType {
        // 按规格设置库存
        public static final String SPEC_STOCK = "0";
        // 按人数设置库存
        public static final String TOTAL_STOCK = "1";
        // 不需要库存
        public static final String NONEED_STOCK = "2";
    }
    
    public class SaleType {
        // 立即开售
        public static final String RIGHT_SALE = "0";
        // 定时开售
        public static final String ONTIME_SALE = "1";
    }
    
}
