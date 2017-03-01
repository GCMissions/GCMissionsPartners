package com.hengtiansoft.wrw.enums;

/**
 * 
 * Class Name: BargainStatisticType Description: 砍价查看、分享 参与记录类型
 * 
 * @author chenghongtu
 *
 */
public enum BargainStatisticType {
    VIEW("1", "查看记录"), PLAY("2", "参加记录"), SHARE("3", "分享记录");
    
    private String key;
    
    private String value;

    private BargainStatisticType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
