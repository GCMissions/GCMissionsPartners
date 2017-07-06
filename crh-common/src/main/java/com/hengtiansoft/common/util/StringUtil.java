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
 * Class Name: StringUtil Description: Character type public processing class
 * 
 * @author taochen
 *
 */
public class StringUtil extends StringUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);
    private static final String NUMERRIC_PATTERN = "[0-9]*";

    /**
     * Determines whether the string <em> text </ em> is <strong> null or empty </ strong>
     * 
     * @param text
     *            Need to judge the string
     * @return Empty back to <strong> true </ strong>
     */
    public static boolean isEmpty(String text) {
        return (text == null) || "".equals(text);
    }

    /**
     * Determines whether the string <em> text </ em> is <strong> null or empty </ strong>
     * 
     * @param text
     *            Need to judge the string
     * @return Non-empty return <strong> true </ strong>
     */
    public static boolean isNotEmpty(String text) {
        return !isEmpty(text);
    }

    /**
     * To determine whether the transmitted id is a digit. Creator: bindi zhang
     * 
     * @param id
     * @return boolean
     */
    public static boolean isNumeric(String id) {
        return Pattern.compile(NUMERRIC_PATTERN).matcher(id).matches();
    }

    /**
     * Array conversion string creator: bindi zhang
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
     * Stitching string tool class.
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

    /** To determine whether the mailbox. */
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
     * Padded string length.
     * 
     * @param resource
     *            Source string
     * @param length
     * 
     * @param fillChar
     *            Fill in the string
     * @return If the source string length is greater than the total length, the source string is returned, otherwise
     *         the padding string
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
     * Stitching "in" statement: oracle has 1000 parameters of the length of the restrictions mysql by the system
     * parameters max_allowed_packet parameters set to default to 1M 25165824 so this method is currently useless
     * 
     * @param columnName
     * 
     * @param valueList
     * 
     * @return
     */
    public static String contactInSql(String columnName, List<String> valueList) {
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        int inPage = 1000; // in the largest number of parameter words
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
     * Get the configuration file information creator: bindi zhang
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
            LOGGER.error("Read the configuration file error!");
        }
        return props.getProperty(paramKey);
    }

}
