package com.hengtiansoft.common.poi.bean;

import java.util.ArrayList;
import java.util.List;

public class ExcelRegionBean {
    private ExcelCellBean title;

    // Start row
    private int row;

    // Start column
    private int col;

    private List<List<ExcelCellBean>> excelRows = new ArrayList<>();

    public ExcelRegionBean(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public ExcelRegionBean(ExcelCellBean title, int row, int col) {
        this(row, col);
        this.title = title;
    }

    public ExcelCellBean getTitle() {
        return title;
    }

    public void setTitle(ExcelCellBean title) {
        this.title = title;
    }

    public List<List<ExcelCellBean>> getExcelRows() {
        return excelRows;
    }

    public void setExcelRows(List<List<ExcelCellBean>> excelRows) {
        this.excelRows = excelRows;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
