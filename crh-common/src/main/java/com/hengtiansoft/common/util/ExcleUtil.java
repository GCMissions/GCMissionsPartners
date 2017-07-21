package com.hengtiansoft.common.util;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * Class Name: ExcleUtil Description:
 * 
 * @author taochen
 */
public class ExcleUtil {

    private final int SPLIT_COUNT = 65536; // Excel The number of rows per worksheet

    private ArrayList fieldName = null; // Name column

    private ArrayList sheetName = null; // sheet name

    private List<List<Object>> fieldData = null; // Export excel actual data

    private List<String> fieldStyle = null; // style

    private HSSFWorkbook workBook = null; // An excel file

    private List<CellRangeAddress> mergingCells = null; // Need to merge the area

    // When you use this class, you must first build two list collections
    // and place the required data in the two list sets.
    // Where fieldName this list collection can use the generic constraint List <String>
    // fieldData This can use the generic constraint List <List <Object >>
    public ExcleUtil(ArrayList fieldName, ArrayList sheetName, List<List<Object>> fieldData, ArrayList fieldStyle) {
        this.fieldName = fieldName;
        this.sheetName = sheetName;
        this.fieldData = fieldData;
        this.fieldStyle = fieldStyle;
    }

    public ExcleUtil(ArrayList fieldName, ArrayList sheetName, List<List<Object>> fieldData, ArrayList fieldStyle,
            List<CellRangeAddress> mergingCells) {
        this.fieldName = fieldName;
        this.sheetName = sheetName;
        this.fieldData = fieldData;
        this.fieldStyle = fieldStyle;
        this.mergingCells = mergingCells;
    }

    /**
     * @return HSSFWorkbook
     */
    public HSSFWorkbook createWorkbook() {
        workBook = new HSSFWorkbook();// Create a workbook
        int rows = fieldData.size();// Count out the number of row of input data
        int sheetNum = 0;// The number of worksheets is cleared
        // According to the number of rows of data and each worksheet can accommodate the number of rows,
        // the need to create the number of worksheet
        if (rows % SPLIT_COUNT == 0) {
            sheetNum = rows / SPLIT_COUNT;
        } else {
            sheetNum = rows / SPLIT_COUNT + 1;
        }

        for (int i = 1; i <= sheetNum; i++) {
            HSSFSheet sheet = workBook.createSheet(sheetName.get(0).toString() + i);// Create a worksheet
            HSSFRow headRow = sheet.createRow(0); // Create the first column
            for (int j = 0; j < fieldName.size(); j++) {
                HSSFCell cell = headRow.createCell(j);// Create a header column cell
                // Set the cell formatting
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                sheet.setColumnWidth(j, Integer.valueOf(this.fieldStyle.get(j)));
                HSSFCellStyle style = workBook.createCellStyle();
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // Create a centered format
                HSSFFont font = workBook.createFont();
                font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                // short color = HSSFColor.RED.index;
                // font.setColor(color);
                style.setFont(font);
                // Fill in the data into the cell
                if (fieldName.get(j) != null) {
                    cell.setCellStyle(style);
                    cell.setCellValue((String) fieldName.get(j));
                } else {
                    cell.setCellStyle(style);
                    cell.setCellValue("-");
                }
            }
            // Create a data field cell and fill in the data
            for (int k = 0; k < (rows < SPLIT_COUNT ? rows : SPLIT_COUNT); k++) {
                if (((i - 1) * SPLIT_COUNT + k) >= rows)
                    break;
                HSSFRow row = sheet.createRow(k + 1);
                ArrayList rowList = (ArrayList) fieldData.get((i - 1) * SPLIT_COUNT + k);
                for (int n = 0; n < rowList.size(); n++) {
                    HSSFCell cell = row.createCell(n);
                    if (rowList.get(n) != null) {
                        cell.setCellValue((String) rowList.get(n).toString());
                    } else {
                        cell.setCellValue("");
                    }
                }
            }
            if (mergingCells != null) {
                for (CellRangeAddress crd : mergingCells) {
                    sheet.addMergedRegion(crd);
                }
            }
        }
        return workBook;
    }

    // The method of writing information to the output stream.
    public void exportExcel(OutputStream os) throws Exception {
        workBook = createWorkbook();
        workBook.write(os);
        os.close();
    }
}
