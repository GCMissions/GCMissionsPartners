package com.hengtiansoft.church.enums;

public enum StatusEnum {

    NORMAL("1", "Enable"), DELETE("0", "Delete"), UNENABLED("2", "Disable"), REMOVED("3", "Removed");

    private String code;

    private String text;

    private StatusEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public static String getTextByCode(String code) {
        for (StatusEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type.getText();
            }
        }
        return null;
    }

    public static String getCodeByText(String text) {
        for (StatusEnum type : values()) {
            if (type.getText().equals(text)) {
                return type.getCode();
            }
        }
        return null;
    }
}
