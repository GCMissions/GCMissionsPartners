package com.hengtiansoft.common.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.commons.net.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.hengtiansoft.common.util.web.WebUtil;

/**
 * 
* Class Name: WRWUtil
* Description: 吾儿乐园工具类
* @author chengchaoyin
*
 */
public class WRWUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(WRWUtil.class);
    
    private static DecimalFormat decimalFormat = new DecimalFormat("##,##0.00");
    
    private static DecimalFormat decimalFormatSimple = new DecimalFormat("#0.00");
    
    private static final String NUMERRIC_PATTERN = "[0-9]*";

    /**
     * 描述: 判断字符串是否为空
     * 
     * @param name
     * @return
     */
    public static boolean isEmpty(String name) {
        return ("null".equals(name) || null == name || name.length() == 0);
    }

    /**
     * 描述: 判断字符串是否为空
     * 
     * @param name
     * @return
     */
    public static boolean isBlank(String name) {
        return ("null".equals(name) || null == name || name.length() == 0);
    }

    /**
     * 描述: 判断字符串是否为空
     * 
     * @param name
     * @return
     */
    public static boolean isNotEmpty(String name) {
        return (null != name && name.length() > 0);
    }

    /**
     * 描述: 模糊查询时,过滤特殊字符
     * 
     * @param string
     * @return
     */
    public static String filterString(String string) {
        return "%" + string.replaceAll("_", "\\\\_").replaceAll("%", "\\\\%").replaceAll("＿", "\\\\＿").trim() + "%";
    }

    /**
     * 描述: 模糊查询时,过滤特殊字符
     * 
     * @param string
     * @return
     */
    public static String filterStringRight(String string) {
        return string.replaceAll("_", "\\\\_").replaceAll("%", "\\\\%").replaceAll("＿", "\\\\＿").trim() + "%";
    }

    /**
     * 描述: 模糊查询时,过滤特殊字符
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
     * 描述: 将对象转化成json字符串
     * 
     * @param object
     * @return
     */
    public static String parseObjTOString(Object object) {
        try {
            return WebUtil.getObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            LOGGER.error("Json转换错误", e);
        }
        return null;
    }

    /**
     * Description: 将json字符串转化为Object对象
     *
     * @param content
     * @param valueType
     * @return
     */
    public static <T> T parseStringToObj(String content, Class<T> valueType) {
        try {
            return WebUtil.getObjectMapper().readValue(content, valueType);
        } catch (IOException e) {
            LOGGER.error("Json转换错误", e);
        }
        return null;
    }

    public static InputStream base64ToInputStream(String base64Str) throws IOException {
        Base64 decoder = new Base64();
        return new ByteArrayInputStream(decoder.decode(base64Str));
    }

    /**
     * Description: Object 转 Long
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
     * Description: Object 转 Long
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
     * Description: Object 转 String
     * 当Object=null时，返回空白“”
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
     * Description: 元转分
     *
     * @param yuan
     * @return
     */
    public static Long transYuanToFen(BigDecimal yuan) {
        if (null != yuan) {
            return yuan.multiply(new BigDecimal(100)).longValue();
        }
        return null;
    }

    /**
     * Description: 分转元
     *
     * @param fen
     * @return
     */
    public static BigDecimal transFenToYuan(Long fen) {
        if (null != fen) {
            return BigDecimal.valueOf(fen).divide(new BigDecimal(100));
        }
        return null;
    }
    
    /**
     * Description: 分转远
     *
     * @param fen
     * @return
     */
    public static String transFenToYuan2String(Long fen) {
        if (null != fen) {
            return decimalFormat.format(fen.doubleValue() / 100);
        }
        return null;
    }
    
    /**
     * Description: 分转元无逗号
     *
     * @param fen
     * @return
     */
    public static String transFenToYuan2StringSimple(Long fen) {
        if (null != fen) {
            return decimalFormatSimple.format(fen.doubleValue() / 100);
        }
        return null;
    }

    
    /**
     * Description: 取随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) { // length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * Description: 取随机数字字符串
     *
     * @param length
     * @return
     */
    public static String getRandomNumberString(int length) { // length表示生成字符串的长度
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
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
    
    /**
     * 判断传输过来的id是否为数字. creater : bindizhang
     * 
     * @param id
     * @return boolean
     */
    public static boolean isNumeric(String id) {
        return Pattern.compile(NUMERRIC_PATTERN).matcher(id).matches();
    }
}
