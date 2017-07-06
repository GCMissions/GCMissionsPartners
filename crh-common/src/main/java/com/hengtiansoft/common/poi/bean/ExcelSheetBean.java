package com.hengtiansoft.common.poi.bean;

import java.util.ArrayList;
import java.util.List;

public class ExcelSheetBean {
    // sheet title
    private ExcelCellBean title;

    private int regionCount;

    private List<ExcelRegionBean> regionBeans = new ArrayList<>();

    public ExcelSheetBean() {
    }

    public ExcelSheetBean(ExcelCellBean title) {
        this.title = title;
    }

    public ExcelCellBean getTitle() {
        return title;
    }

    public void setTitle(ExcelCellBean title) {
        this.title = title;
    }

    public int getRegionCount() {
        return regionCount;
    }

    public void setRegionCount(int regionCount) {
        this.regionCount = regionCount;
    }

    public List<ExcelRegionBean> getRegionBeans() {
        return regionBeans;
    }

    public void setRegionBeans(List<ExcelRegionBean> regionBeans) {
        this.regionBeans = regionBeans;
    }
}
