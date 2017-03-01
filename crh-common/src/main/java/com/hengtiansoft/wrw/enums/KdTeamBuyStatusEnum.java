package com.hengtiansoft.wrw.enums;

import java.util.ArrayList;
import java.util.List;

public enum KdTeamBuyStatusEnum {

    DELETE("0", "删除"),
    NORMAL("1", "正常"),
    NOSTART("2", "未开始"),
    START("3", "进行中"),
    OVER("4", "已结束"),
    PRODUCT_NOT_SALE("5", "商品已下架");
    
    private String code;
    private String text;
    
    private KdTeamBuyStatusEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }
    
    public static List<KdTeamBuyStatusEnum> getSearchStatus() {
        List<KdTeamBuyStatusEnum> teamStatus = new ArrayList<KdTeamBuyStatusEnum>();
        for (KdTeamBuyStatusEnum status : values()) {
            if (!(status.getCode().equals("0") || status.getCode().equals("1"))) {
                teamStatus.add(status);
            }
        }
        return teamStatus;
    }
    
    public static String getText(String code) {
        for (KdTeamBuyStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status.getText();
            }
        }
        return null;
    }
    
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    
}
