package com.hengtiansoft.common.sequence;

public enum SequenceType {
    M_RETURN("mOrderReturn", "吾儿乐园退货订单", "T"), 
    M_KD_RETURN("mKdOrderReturn", "吾儿酷袋退货订单", "KT"), 
    M_ORDER("mOrderMainSeq", "吾儿乐园会员订单", "D"), 
    M_KD_ORDER("mKdOrderMainSeq", "吾儿酷袋会员订单", "KD"), 
    ORG("orgSeq", "组织Seq", "");

    private String code;

    private String text;

    private String head;

    private SequenceType(String code, String text, String head) {
        this.code = code;
        this.text = text;
        this.head = head;
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

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

}
