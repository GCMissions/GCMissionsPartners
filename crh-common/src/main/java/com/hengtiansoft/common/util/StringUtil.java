package com.hengtiansoft.common.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 字符类型公共处理类 Class Name: StringUtil Description: TODO
 * 
 * @author chengchaoyin
 *
 */
public class StringUtil extends StringUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);
    private static final String NUMERRIC_PATTERN = "[0-9]*";

    /**
     * 判断字符串<em>text</em>是否为 <strong>null或空串</strong>
     * 
     * @param text
     *            需判断的字符串
     * @return 空返回 <strong>true</strong>
     */
    public static boolean isEmpty(String text) {
        return (text == null) || "".equals(text);
    }

    /**
     * 判断字符串<em>text</em>是否为 <strong>null或空串</strong>
     * 
     * @param text
     *            需判断的字符串
     * @return 非空返回 <strong>true</strong>
     */
    public static boolean isNotEmpty(String text) {
        return !isEmpty(text);
    }

    /**
     * 判断传输过来的id是否为数字. creater : bindizhang
     * 
     * @param id
     * @return boolean
     */
    public static boolean isNumeric(String id) {
        return Pattern.compile(NUMERRIC_PATTERN).matcher(id).matches();
    }

    /**
     * 数组转换字符串 creater : bindizhang
     */
    public static String arrayToString(Object[] array) {
        StringBuffer sb = new StringBuffer();
        Iterator ite = Arrays.asList(array).iterator();
        while (ite.hasNext()) {
            sb.append(ite.next());
            if (ite.hasNext()) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     * 拼接字符串工具类.
     * 
     * @author penglan
     * @param strs
     * @return
     */
    public static String concatString(String... strs) {
        if ((strs == null) || (strs.length == 0)) {
            return null;
        }
        StringBuilder strBuilder = new StringBuilder();
        for (String str : strs) {
            strBuilder.append(str);
        }

        return strBuilder.toString();
    }

    /** 判断是否为邮箱. */
    public static boolean isNameAdressFormat(String email) {
        boolean isExist = false;

        Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}");
        Matcher m = p.matcher(email);
        boolean b = m.matches();
        if (b) {
            isExist = true;
        }
        return isExist;
    }

    /**
     * 补齐字符串长度.
     * 
     * @param resource
     *            源字符串
     * @param length
     *            总长度
     * @param fillChar
     *            填入字符串
     * @return 如果源字符串长度大于总长度，则放回源字符串，否则返回补齐字符串
     */
    public static String StringFormat(String resource, int length, char fillChar) {
        if (resource.length() >= length) {
            return resource;
        } else {
            StringBuilder fillString = new StringBuilder();
            for (int i = 0; i < (length - resource.length()); i++) {
                fillString.append(fillChar);
            }
            resource = fillString.toString() + resource;
        }
        return resource;
    }

    /**
     * 拼接in语句:oracle有1000个参数长度限制 mysql的话 由系统参数 max_allowed_packet参数设置 默认为1M 25165824 所以此方法目前没用
     * 
     * @param columnName
     *            列名称
     * @param valueList
     *            列list值
     * @return
     */
    public static String contactInSql(String columnName, List<String> valueList) {
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        int inPage = 1000; // in字句最多参数个数
        int allSize = valueList.size();
        int count = allSize / inPage;
        int mode = allSize % inPage;
        for (int i = 0; i < count; i++) {
            String subList = valueList.subList(i * inPage, (i + 1) * inPage).toString();
            if (i == 0) {
                sb.append(columnName).append(" in (").append(subList.substring(1, subList.length() - 1)).append(")");
            } else {
                sb.append(" or").append(columnName).append(" in (").append(subList.substring(1, subList.length() - 1))
                        .append(")");
            }
        }
        if (mode > 0) {
            String subList = valueList.subList(count * inPage, valueList.size()).toString();
            if (count == 0) {
                sb.append(columnName).append(" in (").append(subList.substring(1, subList.length() - 1)).append(")");
            } else {
                sb.append(" or").append(columnName).append(" in (").append(subList.substring(1, subList.length() - 1))
                        .append(")");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * 获取配置文件信息 creater:bindizhang
     * 
     * @param paramKey
     * @return
     */
    public static String getProperty(String paramKey, String pathResource) {
        org.springframework.core.io.Resource resource = new ClassPathResource(pathResource);
        Properties props = new Properties();
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            LOGGER.error("读取配置文件错误!");
        }
        return props.getProperty(paramKey);
    }

}
