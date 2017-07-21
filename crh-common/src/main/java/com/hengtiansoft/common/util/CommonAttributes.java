package com.hengtiansoft.common.util;

/**
 * Public Parameter
 * 
 * @author taochen
 * @version 1.0_beta
 */
public final class CommonAttributes {

    /** Date format */
    public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd",
            "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

    private static String xylXmlPath = "/wrw.xml";

    /**
     * Can not be instantiated
     */
    private CommonAttributes() {
    }

    /**
     * @return the xylXmlPath
     */
    public static String getXylXmlPath() {
        return xylXmlPath;
    }

}
