package com.hengtiansoft.common.poi.bean;

import java.util.List;

public class SimpleExcelBean {
    // 标题
    private String             title;
    // 列名
    private String[]           colNames;
    // 内容
    private List<List<String>> content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getColNames() {
        return colNames;
    }

    public void setColNames(String[] colNames) {
        this.colNames = colNames;
    }

    public List<List<String>> getContent() {
        return content;
    }

    public void setContent(List<List<String>> content) {
        this.content = content;
    }

}
