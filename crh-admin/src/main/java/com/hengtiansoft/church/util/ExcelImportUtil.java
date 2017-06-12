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


/**excel导入工具
 * @author wang.wei
 * @2015/04/12
 */
public class ExcelImportUtil {
	
	
	/**获取excel数据并设置到对象
	 * @param excelPath					excel模板的路径
	 * @param cl						任意Class
	 * @param propertiesFilePath		属性文件路径（相对路径）
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTarget
	 **/
	public static<T> List<T> getExcelData2Object(String excelPath,Class<T> cl,String propertiesFilePath){
		
		
		List<T> objects =  new ArrayList<T>();
		
		try {
			Workbook workbook = Workbook.getWorkbook(new File(excelPath));
			
			Map<String , Object> propertiesMap = new HashMap<String,Object>();
			
			propertiesMap = properties2Map(propertiesFilePath);
			
			int sheetCount  = workbook.getNumberOfSheets();
			
			for(int sheetIndex = 0 ; sheetIndex < sheetCount ; sheetIndex++){
				Sheet sheet = workbook.getSheet(sheetIndex);
				
				Range[] ranges = sheet.getMergedCells();
				
				int rowCount = sheet.getRows();
				
				for(int rowIndex = 1 ;rowIndex < rowCount ;rowIndex++){
					T object =  (T) cl.newInstance();
					Class<? extends Object> cl1 = object.getClass();
                    Method[] methods= cl1.getMethods();
                    for (int i = 0; i < methods.length; i++) {
                        if("setFileName".equals(methods[i].getName())){//写入文件名
                            methods[i].invoke(object, excelPath.substring(excelPath.lastIndexOf(File.separator)+1, excelPath.length()));
                        }
                        if("setSheetName".equals(methods[i].getName())){//写入sheet名
                            methods[i].invoke(object,sheet.getName());
                        }
                        if("setLineNum".equals(methods[i].getName())){//写入行号
                            methods[i].invoke(object,rowIndex+1);
                        }
                    }
					Cell[] cells = sheet.getRow(rowIndex);
					
					for(Cell cell : cells){
							
						setOneCellToObject(cell , object , ranges ,propertiesMap);
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
	 * 读取excel模板数据
	 * 用map封装excel的一行数据
	 * 将封装好一行数据的map设置到list里。
	 * @param excelPath    excel文件的路径
	 * @return
	 */
	public static List<Map<Object,Object>> getExcelData2Map(String excelPath){
		List<Map<Object,Object>> cellMapList = new ArrayList<Map<Object,Object>>();
		try {
			Workbook workbook = Workbook.getWorkbook(new File(excelPath));
			
			int sheetCount = workbook.getNumberOfSheets();
 			for(int sheetIndex = 0 ; sheetIndex < sheetCount ;sheetIndex++){
				Sheet sheet = workbook.getSheet(sheetIndex);
				
				Range[] ranges = sheet.getMergedCells();
				
				int rowCount = sheet.getRows();				
			
				Map<Object, Object> headMap =  new HashMap<Object, Object>();
				
				//获取表头信息 前提是这个表格不是空表
				if(rowCount >= 0 ){
					Cell[] headCells = sheet.getRow(0); 			
					
					for(Cell cell : headCells){
						//TODO 预留处理方式 即表头定义未定
						headMap.put(cell.getColumn(), cell.getContents());						
					}
				}		
				
				for(int rowIndex = 1 ; rowIndex < rowCount ; rowIndex++){						
					Map<Object,Object> rowMap = new HashMap<Object, Object>();	
					
			    	Cell[] cells = sheet.getRow(rowIndex);
					
			    	for(Cell cell : cells){
			    		rowMap = (setOneCell2Map(cell,ranges,headMap , rowMap));				    		
			    	}	
			    	
				cellMapList.add(rowMap);		
				
				}
			
			}
		} catch (Exception e) {
		}
		
		return cellMapList;		
	}
	
	/** 
	 * 将一个单元格包含的数据设置到map里
	 * @param cell		单元格
	 * @param ranges	合并单元格
	 * @param headMap	headMap用来封装excel表头信息
	 * @param rowMap 	rowMap用来封装excel一行数据
	 * @return
	 */
	private static Map<Object, Object> setOneCell2Map(Cell cell, Range[] ranges,
			Map<Object, Object> headMap, Map<Object, Object> rowMap) {		
		
		String cellData = cell.getContents();
		
		String getRangeCellData = setRangeCellValue(cell ,ranges); 
		
		if(getRangeCellData != null){
			cellData = getRangeCellData;
		}
		
		int colNum = cell.getColumn();
		
		return setCellMapInfo(cellData,colNum,headMap,rowMap);
	}

	/**
	 * 根据excel表头信息headMap封装数据到map里
	 * @param cellData   单元格包含的文本数据
	 * @param colNum	   单元格所属的列号
	 * @param headMap    headMap用来封装excel表头信息
	 * @param rowMap     rowMap用来封装excel一行数据
	 * @return
	 */
	private static Map<Object, Object> setCellMapInfo(String cellData, int colNum,
			Map<Object, Object> headMap, Map<Object, Object> rowMap) {
		
		if(headMap == null){
			return rowMap;
		}
		
		if(headMap.containsKey(colNum)){
			rowMap.put(headMap.get(colNum), cellData);
		}
		
		return rowMap;
	}

	/**
	 * 设置一个cell数据到对象里
	 * @param <T>					泛型对象
	 * @param cell					excel单元格对象
	 * @param object				对象
	 * @param ranges				合并单元格	
	 * @param propertiesFilePath	属性文件路径（相对路径） 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private static <T> void setOneCellToObject(Cell cell, T object, Range[] ranges ,Map<String ,Object> propertiesMap) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if(cell == null){
			return;
		}
		
		String cellData = cell.getContents().trim();
		
		String getRangeCellData = setRangeCellValue(cell ,ranges); 
		
		if(getRangeCellData != null){
			cellData = getRangeCellData;
		}
		
		int colNum = cell.getColumn();
		
		
		object = setObjectInfo(propertiesMap,object,cellData,colNum);
	}

	/**
	 * 设置对象的信息
	 * @param propertiesMap		属性文件map
	 * @param object			对象
	 * @param cellData			excel单元格的数据
	 * @param colNum			单元格所属excel的列数
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private static <T> T setObjectInfo(Map<String, Object> propertiesMap, T object,
			String cellData, int colNum) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Set<String> keySets = propertiesMap.keySet();
		
		Class<? extends Object> cl = object.getClass();
		
		Method[] methods = cl.getDeclaredMethods();
		
		Map<String ,Method> methodMap = new HashMap<String, Method>();
		
		for(Method m : methods){
			methodMap.put(m.getName(), m);
		}
		
		for(Object obj : keySets){
			int index = 0;
			try{
				index = Integer.parseInt(obj.toString());
			}catch(NumberFormatException e){
				e.printStackTrace();
				continue;
			}
			
			Object propertiesObject = propertiesMap.get(obj);			
			
			
			if(propertiesObject == null){
				continue;
			}
			
			String propertiesName =  propertiesObject.toString();
			
			if(index == colNum){
				String methodName = "set" + propertiesName.substring(0,1).toUpperCase() + propertiesName.substring(1);
				
				Method method = methodMap.get(methodName);
				
				if(method == null){
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
				} else if(methodType.equals("java.math.BigDecimal")){
				    method.invoke(object, new BigDecimal(cellData));
				}
				
			}
							
		}		
		
		return object;
	}

	/**
	 * 属性文件转换成map
	 * @param propertiesFilePath    属性文件路径（相对路径）
	 * @return
	 */
	private static<T> Map<String, Object> properties2Map(String propertiesFilePath) {
		Map<String , Object> propertiesMap = new HashMap<String, Object>();
		
		try {
			
			Properties properties = new Properties();
			
			InputStream inputStream = ExcelImportUtil.class.getClassLoader().getResourceAsStream(propertiesFilePath);
			
			properties.load(inputStream);
			
			Set<Entry<Object, Object>> entrySets = properties.entrySet();
			
			for(Entry<Object, Object> entry : entrySets){
				propertiesMap.put(entry.getKey().toString().trim(), entry.getValue().toString().trim());
			}
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		return propertiesMap;
	}

	/** 设置构成合并单元格的子单元格信息
	 * @param cell		单元格信息
	 * @param ranges	合并单元格
	 * @return
	 */
	private static String setRangeCellValue(Cell cell, Range[] ranges) {
		for(Range range : ranges){
			Cell topLeftCell = range.getTopLeft();
		    Cell bottomRightCell = range.getBottomRight();
		    
		    int firstRowNumber = topLeftCell.getRow();
		    int lastRowNumber = bottomRightCell.getRow();
		    int firstColNumber = topLeftCell.getColumn();
		    int lstColNumber = bottomRightCell.getColumn();
			
		    int rowNumber = cell.getRow();
		    int colNumber = cell.getColumn();
		    
		    if(firstRowNumber <= rowNumber 
		    		&& lastRowNumber >= rowNumber
		    		&& firstColNumber <= colNumber 
		    		&& lstColNumber >= colNumber){
		    	    
		    	return topLeftCell.getContents();
		    }
			
		}
		return null;
	}

	
	
	
}
