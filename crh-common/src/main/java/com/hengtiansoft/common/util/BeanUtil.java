/*
 * Project Name: zc-collect-common
 * File Name: BeanUtil.java
 * Class Name: BeanUtil
 * Copyright 2014 Hengtian Software Inc
 * Licensed under the Hengtiansoft
 * http://www.hengtiansoft.com
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class Name: BeanUtil
 * Description: TODO
 * 
 * @author chengminmiao
 */
public class BeanUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanUtil.class);

    /**
     * 重构BeanUtilsBean的copyProperties(Object dest, Object orig)方法 如果源对象值为空，则不拷贝
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
                    LOGGER.error("类型转换错误", e);
                }
        }

    }

}
