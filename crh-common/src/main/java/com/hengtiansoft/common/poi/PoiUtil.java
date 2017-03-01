package com.hengtiansoft.common.poi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hengtiansoft.common.poi.bean.ExcelWorkBookBean;
import com.hengtiansoft.common.poi.bean.SimpleExcelBean;
import com.hengtiansoft.common.poi.bean.SimpleMulityExcelBean;
import com.hengtiansoft.common.util.DateTimeUtil;

/**
* Class Name: PoiUtil
* Description: POI工具类
* @author JL
*
*/
public class PoiUtil {
    private static final Logger LOGGER      = LoggerFactory.getLogger(PoiUtil.class);

    private static final String FILE_SUFFIX = ".xls";

    /**
    * Description: 多sheet excel
    *
    * @param beans
    * @param os
    */
    public static void toExcelMulity(List<SimpleExcelBean> beans, OutputStream os) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        for (int i = 0; i < beans.size(); i++) {
            ExcelBuilder builder = new ExcelBuilder(workbook, i, beans.get(i).getTitle());
            builder.buildTitle(beans.get(i).getTitle());
            builder.buildHeader(beans.get(i).getColNames());
            builder.buildContent(beans.get(i).getContent());
            builder.build();
        }
        new ExcelBuilder(workbook, null).buildToStream(os);
    }

    /**
    * Description: 把数据写入到excel
    *
    * @param bean
    * @param os
    */
    public static void toExcel(SimpleExcelBean bean, OutputStream os) {
        ExcelBuilder builder = new ExcelBuilder(new HSSFWorkbook(), bean.getTitle());
        builder.buildTitle(bean.getTitle());
        builder.buildHeader(bean.getColNames());
        builder.buildContent(bean.getContent());
        builder.build();
        builder.buildToStream(os);
    }

    /**
    * Description: 多sheet excel
    *
    * @param bean
    * @param response
    * @param request
    * @throws IOException
    */
    public static void toExcelMulity(SimpleMulityExcelBean bean, HttpServletResponse response, HttpServletRequest request) throws IOException {
        initHttpServletResponse(response, processFileName(request, bean.getTitle()));
        try (OutputStream os = response.getOutputStream()) {
            toExcelMulity(bean.getBeans(), os);
        }
    }

    /**
    * Description: 把数据写入到excel
    *
    * @param bean
    * @param response
    * @param request
    * @throws IOException
    */
    public static void toExcel(SimpleExcelBean bean, HttpServletResponse response, HttpServletRequest request) throws IOException {
        initHttpServletResponse(response, processFileName(request, bean.getTitle()));
        try (OutputStream os = response.getOutputStream()) {
            toExcel(bean, os);
        }
    }

    /**
    * Description: 根据模板导出excel
    *
    * @param workBookBean
    * @param response
    * @param request
    * @throws IOException
    */
    public static void toTemplateExcel(ExcelWorkBookBean workBookBean, HttpServletResponse response, HttpServletRequest request) throws IOException {
        initHttpServletResponse(response, processFileName(request, workBookBean.getFileName()));
        try (OutputStream os = response.getOutputStream()) {
            HSSFWorkbook workbook = ExcelTemplateUtil.buildExceFile(workBookBean);
            workbook.write(os);
            os.flush();
        }

    }

    /**
     * 从excel里读数据（以2003版本格式为准，xls结尾）
     * @param colLength --列长度，根据列长度读取
     * @param is 输入流（一般为文件流）
     * @return 返回一个二维数组格式的数据
     * @throws IOException 
     */
    public static String[][] getFromExcel(int colLength, InputStream is) {
        List<String[]> list = new ArrayList<String[]>();
        try (HSSFWorkbook wb = new HSSFWorkbook(is);) {
            for (int s = 0; s < wb.getNumberOfSheets(); s++) {
                HSSFSheet sheet = wb.getSheetAt(s);
                if (sheet != null && sheet.getRow(0) != null) {
                    int RowCount = sheet.getLastRowNum();
                    RowCount++;
                    for (int r = 0; r < RowCount; r++) {
                        HSSFRow row = sheet.getRow(r);
                        if (row != null) {
                            String[] colString = new String[colLength];
                            for (int c = 0; c < colLength; c++) {
                                HSSFCell cell = row.getCell(c);
                                colString[c] = getCellValueToString(cell);
                            }
                            list.add(colString);
                        }
                    }
                }
            }
            String[][] rowColString = new String[list.size()][colLength];
            for (int r = 0; r < list.size(); r++) {
                rowColString[r] = list.get(r);
            }
            return rowColString;
        } catch (Exception e) {
            LOGGER.warn("getFromExcel error:", e);
            throw new RuntimeException(e);
        }

    }

    /**
     * 转换得到string值
     * @param cellTypeCode
     * @return
     */
    private static String getCellValueToString(HSSFCell cell) {
        if (cell == null) {
            return null;
        }
        int cellTypeCode = cell.getCellType();
        switch (cellTypeCode) {
        case HSSFCell.CELL_TYPE_BLANK:
            return null;
        case HSSFCell.CELL_TYPE_STRING:
            return cell.getStringCellValue();
        case HSSFCell.CELL_TYPE_BOOLEAN:
            return String.valueOf(cell.getBooleanCellValue());
        case HSSFCell.CELL_TYPE_NUMERIC:
            if (HSSFDateUtil.isCellDateFormatted(cell)) {// 判断是否日期类型
                return DateTimeUtil.parseDateToString(cell.getDateCellValue(), DateTimeUtil.SIMPLE_FMT);
            }
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 如果是数字，返回原始类型数字的文本格式
            return cell.getStringCellValue();
        case HSSFCell.CELL_TYPE_FORMULA:
            return cell.getCellFormula();
        default:
            break;
        }
        return null;
    }

    public static void initHttpServletResponse(HttpServletResponse response, String title) {
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + title + FILE_SUFFIX);
    }

    public static String processFileName(HttpServletRequest request, String fileNames) {
        String newFileName = "";
        try {
            String agent = request.getHeader("USER-AGENT");
            if (null != agent && -1 != agent.indexOf("MSIE") || null != agent && -1 != agent.indexOf("Trident")) {// ie
                String name = java.net.URLEncoder.encode(fileNames, "UTF8");
                newFileName = name;
            } else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等
                newFileName = new String(fileNames.getBytes("UTF-8"), "iso-8859-1");
            }
        } catch (Exception e) {
            LOGGER.error("msg",e);
        }
        return newFileName;
    }
}
