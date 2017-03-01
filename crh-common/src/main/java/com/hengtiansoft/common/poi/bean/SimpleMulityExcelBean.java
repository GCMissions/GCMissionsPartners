package com.hengtiansoft.common.poi.bean;

import java.util.ArrayList;
import java.util.List;

public class SimpleMulityExcelBean {

    private String                title;

    private List<SimpleExcelBean> beans = new ArrayList<SimpleExcelBean>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SimpleExcelBean> getBeans() {
        return beans;
    }

    public void setBeans(List<SimpleExcelBean> beans) {
        this.beans = beans;
    }

}
