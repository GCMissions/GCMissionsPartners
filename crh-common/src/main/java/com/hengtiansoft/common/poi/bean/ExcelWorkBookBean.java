package com.hengtiansoft.common.poi.bean;

import java.util.ArrayList;
import java.util.List;

public class ExcelWorkBookBean {
    // 文件名称
    private String               fileName;

    // sheet数量
    private int                  sheetCount = 1;

    // sheet集合
    private List<ExcelSheetBean> sheetBeans = new ArrayList<>();

    // 模板名称
    private String               templateName;

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
