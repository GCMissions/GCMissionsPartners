package com.hengtiansoft.common.util.pay;

import java.util.Random;

public class RandomUtil {
    
    public static final String ALLCHAR    = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String LETTERCHAR = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String NUMBERCHAR = "0123456789";

    /**
     * Description: randomly get a string of the specified length (contains only numbers)
     *
     * @param length  The length of the string
     * @return
     */
    public static String generateNumberString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(NUMBERCHAR.charAt(random.nextInt(NUMBERCHAR.length())));
        }
        return sb.toString();
    }
    
    /**
     * Description: randomly get a string of the specified length (including uppercase and lowercase letters, numbers)
     *
     * @param length The length of the string
     * @return
     */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }

    /**
     * Description: randomly get a string of the specified length (including uppercase and lowercase letters)
     *
     * @param length
     * @return
     */
    public static String generateMixString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(LETTERCHAR.charAt(random.nextInt(LETTERCHAR.length())));
        }
        return sb.toString();
    }

    /**
     * Description: Randomly fetches a lowercase letter string of the specified length
     *
     * @param length
     * @return
     */
    public static String generateLowerString(int length) {
        return generateMixString(length).toLowerCase();
    }

    /**
     * Description: Randomly fetches a uppercase letter string of the specified length
     *
     * @param length
     * @return
     */
    public static String generateUpperString(int length) {
        return generateMixString(length).toUpperCase();
    }
    
    public static void main(String[] args) {
        System.out.println(RandomUtil.generateString(32));
    }
}
