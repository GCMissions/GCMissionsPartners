package com.hengtiansoft.common.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.util.CollectionUtils;

import com.hengtiansoft.common.poi.bean.ExcelCellBean;
import com.hengtiansoft.common.poi.bean.ExcelRegionBean;
import com.hengtiansoft.common.poi.bean.ExcelSheetBean;
import com.hengtiansoft.common.poi.bean.ExcelWorkBookBean;

/**
 * Generate excel tool classes based on templates
 * 
 * @author taochen
 *
 */
public class ExcelTemplateUtil {

    private static final String FILE_SUFFIX = ".xls";

    private static final String FILE_FOLDER = "/excelTemplate";

    public static HSSFWorkbook buildExceFile(ExcelWorkBookBean workBookBean) throws IOException {
        StringBuilder filePath = new StringBuilder(File.separator).append(FILE_FOLDER).append(File.separator)
                .append(workBookBean.getTemplateName()).append(FILE_SUFFIX);
        try (InputStream inputStream = new FileInputStream(filePath.toString());
                HSSFWorkbook workbook = new HSSFWorkbook(inputStream);) {
            // Traverse the sheet
            for (int i = 0; i < workBookBean.getSheetBeans().size(); i++) {
                ExcelSheetBean excelSheetBean = workBookBean.getSheetBeans().get(i);
                if (null == excelSheetBean) {
                    continue;
                }
                HSSFSheet hssfSheet = workbook.getSheetAt(i);
                if (null == hssfSheet) {
                    continue;
                }
                if (null != excelSheetBean.getTitle()) {
                    // create sheet title
                    buildSheetTitle(hssfSheet, excelSheetBean.getTitle());
                }
                // Traverse region
                for (int j = 0; j < excelSheetBean.getRegionBeans().size(); j++) {
                    ExcelRegionBean regionBean = excelSheetBean.getRegionBeans().get(j);
                    if (null != regionBean.getTitle()) {
                        // create region title
                        buildSheetTitle(hssfSheet, regionBean.getTitle());
                    }
                    int startRowInx = 0;
                    // Fill row value
                    for (int k = 0; k < regionBean.getExcelRows().size(); k++) {
                        // Get the row and column information
                        List<ExcelCellBean> cellBeans = regionBean.getExcelRows().get(k);
                        // Gets the starting row to be filled
                        HSSFRow startRow = hssfSheet.getRow(regionBean.getRow());
                        if (startRow == null) {
                            startRow = hssfSheet.createRow(regionBean.getRow());
                        }
                        // Column offset
                        int regionCol = regionBean.getCol();
                        if (0 == k) {
                            // Fill the starting row value
                            buildExcelRow(startRow, cellBeans, regionCol);
                        } else {
                            startRowInx = regionBean.getRow() + k;
                            HSSFRow targetRow = hssfSheet.createRow(startRowInx);
                            // Set the row properties
                            targetRow.setHeightInPoints(startRow.getHeightInPoints());
                            targetRow.setRowNum(targetRow.getRowNum());
                            HSSFCell sourceCell = null;
                            HSSFCell targetCell = null;
                            for (int m = 0; m < cellBeans.size(); m++) {
                                ExcelCellBean cellBean = cellBeans.get(m);
                                // If there is a row offset, the first row of data is copied
                                for (int h = 0; h < cellBean.getCol(); h++) {
                                    sourceCell = startRow.getCell(regionCol);
                                    targetCell = targetRow.createCell(regionCol);
                                    copyCell(sourceCell, targetCell, true);
                                    regionCol++;
                                }
                                // Gets the columns in the starting row
                                sourceCell = startRow.getCell(regionCol);
                                targetCell = targetRow.createCell(regionCol);
                                copyCell(sourceCell, targetCell, false);
                                setCellValue(targetCell, cellBean.getValue());
                                regionCol++;
                            }
                            // The number of complement columns is the same as the first row
                            for (; regionCol < startRow.getLastCellNum(); regionCol++) {
                                sourceCell = startRow.getCell(regionCol);
                                targetCell = targetRow.createCell(regionCol);
                                copyCell(sourceCell, targetCell, true);
                            }
                        }
                    }
                    // If there is no data to put the first row is set to null
                    if (CollectionUtils.isEmpty(regionBean.getExcelRows())) {
                        removeStartRowData(hssfSheet.getRow(regionBean.getRow()));
                    }
                }
            }
            return workbook;
        }
    }

    private static void buildSheetTitle(HSSFSheet hssfSheet, ExcelCellBean title) {
        setCellValue(hssfSheet.getRow(title.getRow()).getCell(title.getCol()), title.getValue());
    }

    private static void setCellValue(HSSFCell cell, Object obj) {
        if (null == cell) {
            return;
        }
        obj = null == obj ? "" : obj;
        if (obj instanceof String) {
            cell.setCellValue((String) obj);
        } else if (obj instanceof Date) {
            cell.setCellValue((Date) obj);
        } else if (obj instanceof Calendar) {
            cell.setCellValue((Calendar) obj);
        } else if (obj instanceof Number) {
            cell.setCellValue(((Number) obj).doubleValue());
        } else if (obj instanceof Boolean) {
            cell.setCellValue(((Boolean) obj).booleanValue());
        } else {
            cell.setCellValue(String.valueOf(obj));
        }
    }

    private static void buildExcelRow(HSSFRow hssfRow, List<ExcelCellBean> cellBeans, int col) {
        for (int i = 0; i < cellBeans.size(); i++) {
            ExcelCellBean cellBean = cellBeans.get(i);
            col += cellBean.getCol();
            HSSFCell cell = hssfRow.getCell(col);
            if (cell == null) {
                cell = hssfRow.createCell(col);
            }
            setCellValue(cell, cellBean.getValue());
            // col += cellBean.getCollapseColumn();
            col++;
        }
    }

    private static void removeStartRowData(HSSFRow row) {
        if (null == row) {
            return;
        }
        for (int i = 0; i < row.getLastCellNum(); i++) {
            setCellValue(row.getCell(i), "");
        }
    }

    /**
     * Copy cell, if copyValueFlag = true, then replicated together with the data
     * 
     * @param sourceCell
     * @param targetCell
     * @param copyValueFlag
     */
    private static void copyCell(HSSFCell sourceCell, HSSFCell targetCell, boolean copyValueFlag) {
        if (sourceCell == null)
            return;
        int srcCellType = sourceCell.getCellType();
        targetCell.setCellStyle(sourceCell.getCellStyle());
        targetCell.setCellType(srcCellType);
        // Different types of data processing
        if (copyValueFlag) {
            if (srcCellType == HSSFCell.CELL_TYPE_NUMERIC) {
                if (HSSFDateUtil.isCellDateFormatted(sourceCell)) {
                    targetCell.setCellValue(sourceCell.getDateCellValue());
                } else {
                    targetCell.setCellValue(sourceCell.getNumericCellValue());
                }
            } else if (srcCellType == HSSFCell.CELL_TYPE_STRING) {
                targetCell.setCellValue(sourceCell.getRichStringCellValue());
            } else if (srcCellType == HSSFCell.CELL_TYPE_BOOLEAN) {
                targetCell.setCellValue(sourceCell.getBooleanCellValue());
            } else if (srcCellType == HSSFCell.CELL_TYPE_ERROR) {
                targetCell.setCellErrorValue(sourceCell.getErrorCellValue());
            } else if (srcCellType == HSSFCell.CELL_TYPE_FORMULA) {
                targetCell.setCellFormula(sourceCell.getCellFormula());
            }
        }
    }

}
