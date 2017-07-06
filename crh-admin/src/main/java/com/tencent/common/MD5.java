package com.tencent.common;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: taochen Date:
 */
public class MD5 {
    private static final Logger log = LoggerFactory.getLogger(MD5.class);

    private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f" };

    /**
     * Converts a byte array to a hexadecimal string
     * 
     * @param b
     *            An array of byte types
     * @return Hexadecimal string
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }

    /**
     * Convert byte to hexadecimal
     * 
     * @param b
     *            The bytes to be converted
     * @return Hexadecimal format
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * MD5 code
     * 
     * @param original
     *            original Strign
     * @return the result After MD5 encryption
     */
    public static String MD5Encode(String origin) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes("UTF-8")));
        } catch (Exception e) {
            log.error("msg", e);
        }
        return resultString;
    }

}
