package com.hengtiansoft.common.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum AdPositionEnum {
    ALL("all", "all", null), B2C_TOP("0", "Mall Home Top Advertisement", 3), B2C_ROLL("1",
            "Mall Home Roll Advertisement", 5), B2C_OTHER("2", "Mall Home", 3), APP_ROLL("3",
            "APP Roll Advertisement ", 5), APP_OTHER("4", "APP Home Picture", 1), APP_ACTIVITY("5",
            "APP activities Advertisement", 5);

    /**
     * Corresponding to the ad's location, the "local" field in the database
     */
    private String position;

    /**
     * Name of the ad positions
     */
    private String name;

    /**
     * Upload the maximum number of pictures
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

    public static List<String> getAppAdsPosition() {
        List<String> list = new ArrayList<String>();
        list.add(APP_ACTIVITY.getName());
        list.add(APP_OTHER.getName());
        list.add(APP_ROLL.getName());
        return list;
    }
}
