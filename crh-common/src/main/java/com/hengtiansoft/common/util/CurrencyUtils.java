package com.hengtiansoft.common.util;

import java.text.DecimalFormat;

/**
 * Class Name: CurrencyUtils Description: Amount conversion tool class： Use of the scene: （1）yuan to fen, Stored in the
 * database （2）fen to yuan,Show on the page
 * 
 * @author taochen
 *
 */
public class CurrencyUtils {

    /**
     * Description: yuan to fen，
     *
     * @param yuan
     *            the value preserves two decimal places
     * 
     * @return
     */
    public static Long rmbYuanToFen(Double yuan) {
        if (yuan == null) {
            return 0L;
        }

        DecimalFormat decimalFormat = new DecimalFormat("##");
        String str = decimalFormat.format(yuan.doubleValue() * 100);

        return Long.parseLong(str);
    }

    /**
     * Description: fen to yuan ，the value preserves two decimal places
     *
     * @param fen
     * @return
     */
    public static Double rmbFenToYuan(Long fen) {
        if (fen == null) {
            return new Double("0.00");
        }
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        String str = decimalFormat.format(fen.doubleValue() / 100);
        return Double.valueOf(str);
    }

    public static void main(String[] args) {
        System.out.println(rmbYuanToFen(new Double(0.100001)));
        System.out.println(rmbFenToYuan(new Long(0L)));
    }

}
