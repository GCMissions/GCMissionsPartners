package com.hengtiansoft.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class Name: BeanUtil
 * Description: 
 * 
 * @author taochen
 */
public class BeanUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanUtil.class);

    /**
     * Rebuild the copyProperties (Object dest, Object orig) method in the BeanUtilsBean class 
     * If the source object value is empty, do not copy
     * 
     * @param dest
     * @param orig
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void copyProperties(Object dest, Object orig) {
        BeanUtilsBean beanUtils = BeanUtilsBean.getInstance();
        PropertyDescriptor origDescriptors[] = beanUtils.getPropertyUtils().getPropertyDescriptors(orig);
        for (int i = 0; i < origDescriptors.length; i++) {
            String name = origDescriptors[i].getName();
            if (!"class".equals(name) && beanUtils.getPropertyUtils().isReadable(orig, name) && beanUtils.getPropertyUtils().isWriteable(orig, name))
                try {
                    Object value = beanUtils.getPropertyUtils().getSimpleProperty(orig, name);
                    if (value != null)
                        beanUtils.copyProperty(dest, name, value);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    LOGGER.error("type conversion error", e);
                }
        }

    }

}
