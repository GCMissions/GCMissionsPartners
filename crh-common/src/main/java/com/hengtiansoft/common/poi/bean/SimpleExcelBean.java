package com.hengtiansoft.common.poi.bean;

import java.util.List;

public class SimpleExcelBean {
    // title
    private String             title;
    // column name
    private String[]           colNames;
    // content
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
