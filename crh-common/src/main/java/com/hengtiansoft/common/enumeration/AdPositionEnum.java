package com.hengtiansoft.common.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum AdPositionEnum {
    ALL("all", "全部", null), B2C_TOP("0", "商城首页顶部广告", 3), B2C_ROLL("1", "商城首页轮播广告", 5), B2C_OTHER("2", "商城首页", 3), APP_ROLL("3", "APP轮播广告", 5), APP_OTHER(
            "4", "APP首页图片", 1),APP_ACTIVITY("5", "APP活动广告", 5);

    /**
     * 对应广告的位子，数据库中的local字段
     */
    private String  position;

    /**
     * 广告位名称
     */
    private String  name;

    /**
     * 最多上传几张图片
     */
    private Integer maxImageCount;

    private AdPositionEnum(String position, String name, Integer maxImageCount) {
        this.position = position;
        this.name = name;
        this.maxImageCount = maxImageCount;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxImageCount() {
        return maxImageCount;
    }

    public void setMaxImageCount(Integer maxImageCount) {
        this.maxImageCount = maxImageCount;
    }

    public static List<String> getAppAdsPosition(){
        List<String> list = new ArrayList<String>();
        list.add(APP_ACTIVITY.getName());
        list.add(APP_OTHER.getName());
        list.add(APP_ROLL.getName());
        return list;
    }
}
