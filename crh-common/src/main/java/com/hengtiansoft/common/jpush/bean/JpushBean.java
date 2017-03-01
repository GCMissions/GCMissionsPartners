package com.hengtiansoft.common.jpush.bean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JpushBean {

    private String              type;                                      // 通知类型
    
    private String              title;                                     // 通知标题
    
    private Set<String>         aliases   = new HashSet<String>();         // 别名标记

    private Set<String>         tagValues = new HashSet<String>();         // 标签标记

    private Map<String, String> extras    = new HashMap<String, String>(); // 推送的数据信息,用于前后台交互

    private String              alert;                                     // 推送消息的内容

    private Long                fromId;                                    // 发送方ID

    private Set<Long>           toIds     = new HashSet<Long>();           // 接收方ID

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<String> getAliases() {
        return aliases;
    }

    public void setAliases(Set<String> aliases) {
        this.aliases = aliases;
    }

    public Set<String> getTagValues() {
        return tagValues;
    }

    public void setTagValues(Set<String> tagValues) {
        this.tagValues = tagValues;
    }

    public Map<String, String> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, String> extras) {
        this.extras = extras;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Set<Long> getToIds() {
        return toIds;
    }

    public void setToIds(Set<Long> toIds) {
        this.toIds = toIds;
    }

    @Override
    public String toString() {
        return "JpushBean [type=" + type + ", title=" + title + ", aliases=" + aliases + ", tagValues=" + tagValues + ", extras=" + extras
                + ", alert=" + alert + ", fromId=" + fromId + ", toIds=" + toIds + "]";
    }

    
}
