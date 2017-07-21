package com.hengtiansoft.common.poi;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class Name: ExcelBuilder Description: excel
 * 
 * @author taochen
 *
 */
public class ExcelBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelBuilder.class);

    private HSSFWorkbook wb = null;
    private HSSFSheet sheet = null;

    /**
     * Constructor method--Initialize the table
     */
    public ExcelBuilder(final HSSFWorkbook wb, final int sheetIndex, final String sheetName) {
        this.wb = wb;
        if (wb.getNumberOfSheets() == 0 || sheetIndex > wb.getNumberOfSheets() - 1) {
            this.sheet = wb.createSheet();
        } else {
            this.sheet = wb.getSheetAt(sheetIndex);
        }
        if (!StringUtils.isBlank(sheetName)) {// set sheetName
            wb.setSheetName(sheetIndex, sheetName);
        }
    }

    /**
     * Constructor Method - Initialize the table
     */
    public ExcelBuilder(final HSSFWorkbook wb, final String sheetName) {
        this.wb = wb;
        if (wb.getNumberOfSheets() == 0) {
            this.sheet = wb.createSheet();
        } else {
            this.sheet = wb.getSheetAt(0);
        }
        if (!StringUtils.isBlank(sheetName)) {// set sheetName
            wb.setSheetName(0, sheetName);
        }
    }

    public void buildTitle(final String title) {
        this.titleCellSet(title);
    }

    public void buildHeader(final String[] colNames) {
        this.headerCellSet(colNames);
    }

    public void buildContent(final List<List<String>> content) {
        this.contentCellSet(content);
    }

    public void build() {
        if (null != this.sheet.getRow(0) && null != this.sheet.getRow(1)
                && this.sheet.getRow(0).getLastCellNum() < this.sheet.getRow(1).getLastCellNum()) {
            final CellRangeAddress range = new CellRangeAddress(0, 0, 0, this.sheet.getRow(1).getLastCellNum() - 1);// Merge
                                                                                                                    // the
                                                                                                                    // title
                                                                                                                    // cell
            this.sheet.addMergedRegion(range);
            for (int i = 0; i < this.sheet.getRow(1).getLastCellNum(); i++) {// Automatically adjust the column width
                this.sheet.autoSizeColumn(i);
            }
        } else {
            for (int i = 0; i < this.sheet.getRow(this.sheet.getFirstRowNum()).getLastCellNum(); i++) {// Automatically
                                                                                                       // adjust the
                                                                                                       // column width
                this.sheet.autoSizeColumn(i);
            }
        }
    }

    public void buildToStream(final OutputStream os) {
        try {
            this.wb.write(os);
        } catch (final IOException e) {
            LOGGER.warn("toExcel error:" + e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param cells
     * @param wb
     * @param sheet
     */
    private void contentCellSet(final List<List<String>> cells) {
        if (CollectionUtils.isEmpty(cells)) {
            return;
        }
        int startRowIndex = this.culCurrRowIndex();
        HSSFCellStyle style = this.getContentCellStyle(this.wb, this.getContentFont(this.wb));
        for (int i = 0; i < cells.size(); i++, startRowIndex++) {
            final HSSFRow row = this.sheet.createRow(startRowIndex);
            for (int j = 0; j < cells.get(i).size(); j++) {
                final HSSFCell cell = row.createCell(j);
                cell.setCellStyle(style);
                cell.setCellValue(cells.get(i).get(j));
            }
        }
    }

    /**
     * Column header setting
     * 
     * @param colNames
     * @param wb
     * @param sheet
     */
    private void headerCellSet(final String[] colNames) {
        if (null == colNames || colNames.length == 0) {
            return;
        }
        final HSSFRow colRow = this.sheet.createRow(this.culCurrRowIndex());
        for (int c = 0; c < colNames.length; c++) {
            final HSSFCell cell = colRow.createCell(c);
            cell.setCellStyle(this.getHeaderCellStyle(this.wb, this.getHeaderFont(this.wb)));
            cell.setCellValue(colNames[c]);
        }
    }

    /**
     * Calculate the current line subscript
     * 
     * @return
     */
    private int culCurrRowIndex() {
        if (this.sheet.getLastRowNum() == 0) {
            if (this.sheet.getPhysicalNumberOfRows() == 0) {// If the number of physical rows is 0,
                                                            // it means that no row is created and the first row is
                                                            // returned.
                return 0;
            }
            return 1;
        }
        return this.sheet.getLastRowNum() + 1;
    }

    /**
     * Set the title
     * 
     * @param title
     * @param colNames
     * @param wb
     * @param sheet
     */
    private void titleCellSet(final String title) {
        if (StringUtils.isBlank(title)) {
            return;
        }
        final HSSFRow allTitleRow = this.sheet.createRow(this.culCurrRowIndex());
        allTitleRow.setHeightInPoints(20);
        final HSSFCell titleCell = allTitleRow.createCell(0);
        titleCell.setCellStyle(this.getTitleCellStyle(this.wb, this.getTitleFont(this.wb)));
        titleCell.setCellValue(title);
    }

    /* Set the font format */
    private HSSFFont getTitleFont(final HSSFWorkbook wb) {
        final HSSFFont fontStyle = wb.createFont();
        fontStyle.setFontName("Times New Roman");
        fontStyle.setFontHeightInPoints((short) 14);
        fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        return fontStyle;
    }

    private HSSFFont getHeaderFont(final HSSFWorkbook wb) {
        final HSSFFont fontStyle = wb.createFont();
        fontStyle.setFontName("Times New Roman");
        fontStyle.setFontHeightInPoints((short) 14);
        fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        return fontStyle;
    }

    private HSSFFont getContentFont(final HSSFWorkbook wb) {
        final HSSFFont fontStyle = wb.createFont();
        fontStyle.setFontName("Times New Roman");
        fontStyle.setFontHeightInPoints((short) 12);
        fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        return fontStyle;
    }

    private HSSFCellStyle getHeaderCellStyle(final HSSFWorkbook wb, final HSSFFont font) {
        final HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);// Set the fill style
        cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);// Set the fill color
        if (font != null) {
            cellStyle.setFont(font);
        }
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        this.cellBorderSet(cellStyle, HSSFCellStyle.BORDER_MEDIUM);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    /**
     * @param cellStyle
     */
    private void cellBorderSet(final HSSFCellStyle cellStyle, final short border) {
        cellStyle.setBorderBottom(border);
        cellStyle.setBorderTop(border);
        cellStyle.setBorderLeft(border);
        cellStyle.setBorderRight(border);
    }

    private HSSFCellStyle getTitleCellStyle(final HSSFWorkbook wb, final HSSFFont font) {
        final HSSFCellStyle cellStyle = wb.createCellStyle();
        // Set the fill color
        if (font != null) {
            cellStyle.setFont(font);
        }
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        this.cellBorderSet(cellStyle, HSSFCellStyle.BORDER_THIN);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    private HSSFCellStyle getContentCellStyle(final HSSFWorkbook wb, final HSSFFont font) {
        final HSSFCellStyle cellStyle = wb.createCellStyle();
        if (font != null) {
            cellStyle.setFont(font);
        }
        this.cellBorderSet(cellStyle, HSSFCellStyle.BORDER_THIN);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

}
