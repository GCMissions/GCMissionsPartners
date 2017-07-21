package com.hengtiansoft.common.sequence;

public enum SequenceType {
    M_RETURN("mOrderReturn", "OrderReturn", "T"), M_KD_RETURN("mKdOrderReturn", "KdOrderReturn", "KT"), M_ORDER(
            "mOrderMainSeq", "MemberOrder", "D"), M_KD_ORDER("mKdOrderMainSeq", "KdMemberOrder", "KD"), ORG("orgSeq",
            "Organization", "");

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
