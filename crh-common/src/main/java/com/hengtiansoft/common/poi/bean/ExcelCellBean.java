package com.hengtiansoft.common.poi.bean;

public class ExcelCellBean {
    // Start column
    private Object value;

    private int collapseColumn = 1;

    // Start row
    private int row;

    // Start column
    private int col = 0;

    public ExcelCellBean(Object value) {
        this.value = value;
    }

    public ExcelCellBean(Object value, int collapseColumn) {
        this.value = value;
        this.collapseColumn = collapseColumn;
    }

    public ExcelCellBean(Object value, int row, int col, int collapseColumn) {
        this.value = value;
        this.row = row;
        this.col = col;
        this.collapseColumn = collapseColumn;
    }

    public ExcelCellBean(Object value, int row, int col) {
        this.value = value;
        this.row = row;
        this.col = col;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getCollapseColumn() {
        return collapseColumn;
    }

    public void setCollapseColumn(int collapseColumn) {
        this.collapseColumn = collapseColumn;
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
