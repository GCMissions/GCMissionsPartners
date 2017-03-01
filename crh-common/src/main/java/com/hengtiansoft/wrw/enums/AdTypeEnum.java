package com.hengtiansoft.wrw.enums;

public enum AdTypeEnum {
    all("0", "全部"), web("1", "PC广告"), app("2", "APP广告"), appActivity("3", "APP活动广告"), CAROUSEL_AD("4", "吾儿网轮播广告"), SALES_AD(
            "5", "吾儿网热门推荐"), APP_CAROUSEL("6", "app轮播广告"), APP_HOT("7", "app热门推荐");

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    private AdTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
