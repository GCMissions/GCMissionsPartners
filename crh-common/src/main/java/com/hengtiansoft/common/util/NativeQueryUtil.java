package com.hengtiansoft.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hengtiansoft.common.dto.NativePageingBean;
import com.hengtiansoft.common.dto.PagingDto;


public class NativeQueryUtil {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(NativeQueryUtil.class);
	/**
	 * 把原生sql查询的结果集转化为自定义的对象，自定义的对象的属性排列顺序需跟sql中对应的字段一样。
	 * 如果自定义对象中包含其他自定义的对象，需要把它们放在sql字段对应的属性后面。serialVersionUID的顺序不要求。
	 * 由于使用反射设置属性值，set方法必须提供，setter属性方法属性首字母大写。
	 * @param target 自定义对象的class
	 * @param result sql查询结果集
	 * @return 返回Object,不返回List是因为容易强制转换为对应 的List
	 */
	public static Object convertResult2Obj(Class<?> target, List<Object[]> result) {
		List<Object> targetList = new ArrayList<Object>();
		for (Object[] objects : result) {
			Object obj = null;
			Field[] fields = target.getDeclaredFields();
			int index = 0;
			try {
				obj = target.newInstance();
				for (Field field : fields) {
					String fieldName = field.getName();
					if("serialVersionUID".equals(fieldName)){
						continue;
					}
					Method method = target.getMethod("set"+ StringUtils.capitalize(fieldName), field.getType());
					if("java.lang.Long".equals(field.getType().getName())
							&& "java.lang.Integer".equals(objects[index].getClass().getName())){
						method.invoke(obj,((Integer)objects[index]).longValue());
					}else if (field.getType().isPrimitive() && objects[index] == null) {
					    LOGGER.info(" field.getType().isPrimitive() && objects[index] == null ");
					    //do nothing...
					}
					else {
						method.invoke(obj, objects[index]);
					}
					++index;
					if(index > objects.length-1){
						break;
					}
				}
			} catch (InstantiationException | IllegalAccessException
					| NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
				LOGGER.debug("sql结果集的第"+index+"个属性设值发生异常", e);
			}
			targetList.add(obj);
		}
		return targetList;
	}
	
	/**
	 * native sql分页的时候，获取开始记录，结束记录
	 * @param page page为空不分页，查询全部
	 * @param count
	 * @return
	 * @throws Exception
	 */
	public static NativePageingBean getPageingBean(PagingDto page, int count) throws Exception {
		NativePageingBean pageingBean = new NativePageingBean();
		if(page != null){
			int pageSize = page.getPageSize();
			int pageCount = (int) Math.ceil((double) count / (double) pageSize);
			if(page.getCurrentPage() > pageCount){
				throw new Exception("当前页数大于总页数");
			}
			pageingBean.setStartRow((page.getCurrentPage()-1) * pageSize);
			pageingBean.setEndRow(pageingBean.getStartRow() + pageSize);
		}else {
			pageingBean.setEndRow(count);
		}
		return pageingBean;
	}
}
