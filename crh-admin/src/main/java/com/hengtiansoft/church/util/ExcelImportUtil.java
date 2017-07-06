package com.hengtiansoft.church.util;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import com.hengtiansoft.common.enumeration.EErrorCode;
import com.hengtiansoft.common.exception.BaseException;

import jxl.Cell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Excel import tool
 * 
 * @author taochen
 */
public class ExcelImportUtil {

    /**
     * Get the Excel data and set it to the object
     * 
     * @param excelPath
     *            The path to the excel template
     * @param cl
     *            any Class
     * @param propertiesFilePath
     *            Property file path (relative path)
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTarget
     **/
    public static <T> List<T> getExcelData2Object(String excelPath, Class<T> cl, String propertiesFilePath) {

        List<T> objects = new ArrayList<T>();

        try {
            Workbook workbook = Workbook.getWorkbook(new File(excelPath));

            Map<String, Object> propertiesMap = new HashMap<String, Object>();

            propertiesMap = properties2Map(propertiesFilePath);

            int sheetCount = workbook.getNumberOfSheets();

            for (int sheetIndex = 0; sheetIndex < sheetCount; sheetIndex++) {
                Sheet sheet = workbook.getSheet(sheetIndex);

                Range[] ranges = sheet.getMergedCells();

                int rowCount = sheet.getRows();

                for (int rowIndex = 1; rowIndex < rowCount; rowIndex++) {
                    T object = (T) cl.newInstance();
                    Class<? extends Object> cl1 = object.getClass();
                    Method[] methods = cl1.getMethods();
                    for (int i = 0; i < methods.length; i++) {
                        if ("setFileName".equals(methods[i].getName())) {// Write file name
                            methods[i].invoke(object,
                                    excelPath.substring(excelPath.lastIndexOf(File.separator) + 1, excelPath.length()));
                        }
                        if ("setSheetName".equals(methods[i].getName())) {// write sheet name

                            methods[i].invoke(object, sheet.getName());
                        }
                        if ("setLineNum".equals(methods[i].getName())) {// Write line number
                            methods[i].invoke(object, rowIndex + 1);
                        }
                    }
                    Cell[] cells = sheet.getRow(rowIndex);

                    for (Cell cell : cells) {

                        setOneCellToObject(cell, object, ranges, propertiesMap);
                    }

                    objects.add(object);

                }

            }
        } catch (Exception e) {
            throw new BaseException(EErrorCode.FILE_PARSE_EXCEPTION);
        }

        return objects;

    }

    /**
     * Read excel template data encapsulate a row of data from excel with map set the map that encapsulates one line of
     * data to list.
     * 
     * @param excelPath
     * @return
     */
    public static List<Map<Object, Object>> getExcelData2Map(String excelPath) {
        List<Map<Object, Object>> cellMapList = new ArrayList<Map<Object, Object>>();
        try {
            Workbook workbook = Workbook.getWorkbook(new File(excelPath));

            int sheetCount = workbook.getNumberOfSheets();
            for (int sheetIndex = 0; sheetIndex < sheetCount; sheetIndex++) {
                Sheet sheet = workbook.getSheet(sheetIndex);

                Range[] ranges = sheet.getMergedCells();

                int rowCount = sheet.getRows();

                Map<Object, Object> headMap = new HashMap<Object, Object>();

                // Gets the header information on the premise that this table is not empty table
                if (rowCount >= 0) {
                    Cell[] headCells = sheet.getRow(0);

                    for (Cell cell : headCells) {
                        // Reserved processing means the header definition is undefined
                        headMap.put(cell.getColumn(), cell.getContents());
                    }
                }

                for (int rowIndex = 1; rowIndex < rowCount; rowIndex++) {
                    Map<Object, Object> rowMap = new HashMap<Object, Object>();

                    Cell[] cells = sheet.getRow(rowIndex);

                    for (Cell cell : cells) {
                        rowMap = (setOneCell2Map(cell, ranges, headMap, rowMap));
                    }

                    cellMapList.add(rowMap);

                }

            }
        } catch (Exception e) {
        }

        return cellMapList;
    }

    /**
     * Set the data contained in a cell to map
     * 
     * @param cell
     * @param ranges
     *            merge cell
     * @param headMap
     *            HeadMap is used to encapsulate excel header information
     * @param rowMap
     *            RowMap is used to encapsulate excel rows of data
     * @return
     */
    private static Map<Object, Object> setOneCell2Map(Cell cell, Range[] ranges, Map<Object, Object> headMap,
            Map<Object, Object> rowMap) {

        String cellData = cell.getContents();

        String getRangeCellData = setRangeCellValue(cell, ranges);

        if (getRangeCellData != null) {
            cellData = getRangeCellData;
        }

        int colNum = cell.getColumn();

        return setCellMapInfo(cellData, colNum, headMap, rowMap);
    }

    /**
     * According to the excel header information, the headMap encapsulates the data into the map
     * 
     * @param cellData
     *            The text data that the cell contains
     * @param colNum
     *            The column number to which the cell belongs
     * @param headMap
     *            HeadMap is used to encapsulate excel header information
     * @param rowMap
     *            RowMap is used to encapsulate excel rows of data
     * @return
     */
    private static Map<Object, Object> setCellMapInfo(String cellData, int colNum, Map<Object, Object> headMap,
            Map<Object, Object> rowMap) {

        if (headMap == null) {
            return rowMap;
        }

        if (headMap.containsKey(colNum)) {
            rowMap.put(headMap.get(colNum), cellData);
        }

        return rowMap;
    }

    /**
     * Set a cell data to the object
     * 
     * @param <T>
     *            Generic object
     * @param cell
     *            Excel cell object
     * @param object
     *            object
     * @param ranges
     *            merge cell
     * @param propertiesFilePath
     *            Property file path (relative path)
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private static <T> void setOneCellToObject(Cell cell, T object, Range[] ranges, Map<String, Object> propertiesMap)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (cell == null) {
            return;
        }

        String cellData = cell.getContents().trim();

        String getRangeCellData = setRangeCellValue(cell, ranges);

        if (getRangeCellData != null) {
            cellData = getRangeCellData;
        }

        int colNum = cell.getColumn();

        object = setObjectInfo(propertiesMap, object, cellData, colNum);
    }

    /**
     * Set object information
     * 
     * @param propertiesMap
     *            Property file map
     * @param object
     *            object
     * @param cellData
     *            excel cell data
     * @param colNum
     *            The number of columns to which the cell belongs to excel
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static <T> T setObjectInfo(Map<String, Object> propertiesMap, T object, String cellData, int colNum)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Set<String> keySets = propertiesMap.keySet();

        Class<? extends Object> cl = object.getClass();

        Method[] methods = cl.getDeclaredMethods();

        Map<String, Method> methodMap = new HashMap<String, Method>();

        for (Method m : methods) {
            methodMap.put(m.getName(), m);
        }

        for (Object obj : keySets) {
            int index = 0;
            try {
                index = Integer.parseInt(obj.toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                continue;
            }

            Object propertiesObject = propertiesMap.get(obj);

            if (propertiesObject == null) {
                continue;
            }

            String propertiesName = propertiesObject.toString();

            if (index == colNum) {
                String methodName = "set" + propertiesName.substring(0, 1).toUpperCase() + propertiesName.substring(1);

                Method method = methodMap.get(methodName);

                if (method == null) {
                    continue;
                }

                String methodType = method.getParameterTypes()[0].getName();

                if (methodType.equals("java.lang.String")) {
                    method.invoke(object, cellData);
                } else if (methodType.equals("int")) {
                    method.invoke(object, Integer.parseInt(cellData));
                } else if (methodType.equals("java.lang.Long")) {
                    method.invoke(object, Long.parseLong(cellData));
                } else if (methodType.equals("java.lang.Double")) {
                    method.invoke(object, Double.parseDouble(cellData));
                } else if (methodType.equals("java.lang.Float")) {
                    method.invoke(object, Float.parseFloat(cellData));
                } else if (methodType.equals("java.math.BigDecimal")) {
                    method.invoke(object, new BigDecimal(cellData));
                }

            }

        }

        return object;
    }

    /**
     * Property file converted to map
     * 
     * @param propertiesFilePath
     *            Property file path (relative path)
     * @return
     */
    private static <T> Map<String, Object> properties2Map(String propertiesFilePath) {
        Map<String, Object> propertiesMap = new HashMap<String, Object>();

        try {

            Properties properties = new Properties();

            InputStream inputStream = ExcelImportUtil.class.getClassLoader().getResourceAsStream(propertiesFilePath);

            properties.load(inputStream);

            Set<Entry<Object, Object>> entrySets = properties.entrySet();

            for (Entry<Object, Object> entry : entrySets) {
                propertiesMap.put(entry.getKey().toString().trim(), entry.getValue().toString().trim());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return propertiesMap;
    }

    /**
     * Sets the child cell information that makes up the merged cell
     * 
     * @param cell
     *            cell message
     * @param ranges
     *            merge cell
     * @return
     */
    private static String setRangeCellValue(Cell cell, Range[] ranges) {
        for (Range range : ranges) {
            Cell topLeftCell = range.getTopLeft();
            Cell bottomRightCell = range.getBottomRight();

            int firstRowNumber = topLeftCell.getRow();
            int lastRowNumber = bottomRightCell.getRow();
            int firstColNumber = topLeftCell.getColumn();
            int lstColNumber = bottomRightCell.getColumn();

            int rowNumber = cell.getRow();
            int colNumber = cell.getColumn();

            if (firstRowNumber <= rowNumber && lastRowNumber >= rowNumber && firstColNumber <= colNumber
                    && lstColNumber >= colNumber) {

                return topLeftCell.getContents();
            }

        }
        return null;
    }

}
