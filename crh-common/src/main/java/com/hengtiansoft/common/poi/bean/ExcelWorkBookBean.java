package com.hengtiansoft.common.poi.bean;

import java.util.ArrayList;
import java.util.List;

public class ExcelWorkBookBean {
    // file Name
    private String fileName;

    // sheet count
    private int sheetCount = 1;

    // sheet collection
    private List<ExcelSheetBean> sheetBeans = new ArrayList<>();

    // template name
    private String templateName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getSheetCount() {
        return sheetCount;
    }

    public void setSheetCount(int sheetCount) {
        this.sheetCount = sheetCount;
    }

    public List<ExcelSheetBean> getSheetBeans() {
        return sheetBeans;
    }

    public void setSheetBeans(List<ExcelSheetBean> sheetBeans) {
        this.sheetBeans = sheetBeans;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
