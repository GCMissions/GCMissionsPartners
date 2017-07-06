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
     * The original sql query result set into a custom object, the custom object of the order of the attributes need to
     * be the same with the sql field. If the custom object contains other custom objects, you need to put them in the
     * sql field corresponding to the property behind. The sequence of serialVersionUID is not required. The set method
     * must be provided with the setter property method attribute, since the set value is set by using the reflection
     * set property value.
     * 
     * @param target
     *            Customize the class of the object
     * @param result
     *            Sql query results set
     * @return Returns Object, does not return the List collection because it is easy to cast to the corresponding List
     *         collection
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
                    if ("serialVersionUID".equals(fieldName)) {
                        continue;
                    }
                    Method method = target.getMethod("set" + StringUtils.capitalize(fieldName), field.getType());
                    if ("java.lang.Long".equals(field.getType().getName())
                            && "java.lang.Integer".equals(objects[index].getClass().getName())) {
                        method.invoke(obj, ((Integer) objects[index]).longValue());
                    } else if (field.getType().isPrimitive() && objects[index] == null) {
                        LOGGER.info(" field.getType().isPrimitive() && objects[index] == null ");
                        // do nothing...
                    } else {
                        method.invoke(obj, objects[index]);
                    }
                    ++index;
                    if (index > objects.length - 1) {
                        break;
                    }
                }
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
                    | IllegalArgumentException | InvocationTargetException e) {
                LOGGER.debug("The value of the " + index + "th attribute of the sql result set has an exception", e);
            }
            targetList.add(obj);
        }
        return targetList;
    }

    /**
     * native Sql paging time, get the beginning of the record and the end of the record
     * 
     * @param page
     * @param count
     * @return
     * @throws Exception
     */
    public static NativePageingBean getPageingBean(PagingDto page, int count) throws Exception {
        NativePageingBean pageingBean = new NativePageingBean();
        if (page != null) {
            int pageSize = page.getPageSize();
            int pageCount = (int) Math.ceil((double) count / (double) pageSize);
            if (page.getCurrentPage() > pageCount) {
                throw new Exception("The current number of pages is greater than the total number of pages");
            }
            pageingBean.setStartRow((page.getCurrentPage() - 1) * pageSize);
            pageingBean.setEndRow(pageingBean.getStartRow() + pageSize);
        } else {
            pageingBean.setEndRow(count);
        }
        return pageingBean;
    }
}
