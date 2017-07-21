package com.hengtiansoft.common.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hengtiansoft.common.util.web.WebUtil;

/**
 * Class Name: Description: Project tool class
 * 
 * @author taochen
 */
public class BasicUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicUtil.class);

    /**
     * Description: Determines whether the string is empty
     * 
     * @param name
     * @return
     */
    public static boolean isEmpty(String name) {
        return ("null".equals(name) || null == name || name.length() == 0);
    }

    /**
     * Description: Determines whether the string is empty
     * 
     * @param name
     * @return
     */
    public static boolean isBlank(String name) {
        return ("null".equals(name) || null == name || name.length() == 0);
    }

    /**
     * Description: Determines whether the string is empty
     * 
     * @param name
     * @return
     */
    public static boolean isNotEmpty(String name) {
        return (null != name && name.length() > 0);
    }

    /**
     * Description: Filtering special characters when blurring queries
     * 
     * @param string
     * @return
     */
    public static String filterString(String string) {
        return "%" + string.replaceAll("_", "\\\\_").replaceAll("%", "\\\\%").replaceAll("＿", "\\\\＿").trim() + "%";
    }

    /**
     * Description: Filtering special characters when blurring queries
     * 
     * @param string
     * @return
     */
    public static String filterStringRight(String string) {
        return string.replaceAll("_", "\\\\_").replaceAll("%", "\\\\%").replaceAll("＿", "\\\\＿").trim() + "%";
    }

    /**
     * Description: Filtering special characters when blurring queries
     * 
     * @param string
     * @return
     */
    public static String filterStringLeft(String string) {
        return "%" + string.replaceAll("_", "\\\\_").replaceAll("%", "\\\\%").replaceAll("＿", "\\\\＿").trim();
    }

    /**
     * @param obj1
     * @param obj2
     * @return
     */
    public static boolean objCompator(Object obj1, Object obj2) {
        if (obj1 == null && obj2 != null) {
            return false;
        } else if (obj1 != null) {
            return obj1.equals(obj2);
        }
        return true;
    }

    /**
     * Description: Converts an object to a json string
     * 
     * @param object
     * @return
     */
    public static String parseObjTOString(Object object) {
        try {
            return WebUtil.getObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            LOGGER.error("Json conversion error", e);
        }
        return null;
    }

    /**
     * Description: Converts a json string to an object
     *
     * @param content
     * @param valueType
     * @return
     */
    public static <T> T parseStringToObj(String content, Class<T> valueType) {
        try {
            return WebUtil.getObjectMapper().readValue(content, valueType);
        } catch (IOException e) {
            LOGGER.error("Json conversion error", e);
        }
        return null;
    }

    public static InputStream base64ToInputStream(String base64Str) throws IOException {
        Base64 decoder = new Base64();
        return new ByteArrayInputStream(decoder.decode(base64Str));
    }

    /**
     * Description: Object to Integer
     *
     * @param obj
     * @return
     */
    public static Integer objToInteger(Object obj) {
        if (obj != null) {
            return Integer.valueOf(String.valueOf(obj));
        }
        return 0;
    }

    /**
     * Description: Object to Long
     *
     * @param obj
     * @return
     */
    public static Long objToLong(Object obj) {
        if (obj != null) {
            return Long.valueOf(String.valueOf(obj));
        }
        return 0L;
    }

    /**
     * Description: Object to Double
     *
     * @param obj
     * @return
     */
    public static Double objToDouble(Object obj) {
        if (obj != null) {
            return Double.valueOf(String.valueOf(obj));
        }
        return 0.0D;
    }

    /**
     * Description: Object to String When Object = null, returns blank ""
     * 
     * @param obj
     * @return
     */
    public static String objToString(Object obj) {
        if (obj != null) {
            return String.valueOf(obj);
        }
        return "";
    }

    public static String listToString(List<String> stringList) {
        if (stringList == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }

    public static String intListToString(List<Integer> intList) {
        if (intList == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (Integer integer : intList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(integer.toString());
        }
        return result.toString();
    }

    /**
     * Description: yuan to fen
     *
     * @param yuan
     * @return
     */
    public static Long transYuanToFen(Double yuan) {
        if (null != yuan) {
            return (long) (yuan * 100);
        }
        return null;
    }

    /**
     * Description: fen to yuan
     *
     * @param fen
     * @return
     */
    public static Double transFenToYuan(Long fen) {
        if (null != fen) {
            return fen.doubleValue() / 100;
        }
        return null;
    }

    /**
     * Description: Converts an object to a json string
     *
     * @param content
     * @param valueType
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map<String, String> parseStringToMap(String content, Class<Map> valueType) {
        try {
            return WebUtil.getObjectMapper().readValue(content, valueType);
        } catch (IOException e) {
            LOGGER.error("Json conversion error", e);
        }
        return null;
    }
}
