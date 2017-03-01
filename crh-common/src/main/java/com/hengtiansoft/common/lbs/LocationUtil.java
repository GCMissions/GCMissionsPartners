package com.hengtiansoft.common.lbs;

/**
 * Description: 计算距离，采用Google Maps的算法
 * 
 * @author huizhuang
 */
public class LocationUtil {

    // 赤道半径（精确到米）
    private static final double EARTH_RADIUS_M  = 6378137;

    // 赤道半径（精确到千米）
    private static final double EARTH_RADIUS_KM = 6378.137;

    /**
     * Description: 获取2个经纬度间的距离（精确到米）
     *
     * @param lat1
     *            点1的经度
     * @param lng1
     *            点1的纬度
     * @param lat2
     *            点2的经度
     * @param lng2
     *            点2的纬度
     * @return
     */
    public static double getDistance_M(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);

        double dLat = radLat1 - radLat2;
        double dLng = rad(lng1 - lng2);

        double s = 2
                * Math.asin(Math.sqrt(Math.pow(Math.sin(dLat / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(dLng / 2), 2)));

        s = s * EARTH_RADIUS_M;
        s = Math.round(s * 10000) / 10000;

        return s;
    }

    /**
     * Description: 获取2个经纬度间的距离（精确到千米）
     *
     * @param lat1
     *            点1的经度
     * @param lng1
     *            点1的纬度
     * @param lat2
     *            点2的经度
     * @param lng2
     *            点2的纬度
     * @return
     */
    public static double getDistance_KM(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);

        double dLat = radLat1 - radLat2;
        double dLng = rad(lng1 - lng2);

        double s = 2
                * Math.asin(Math.sqrt(Math.pow(Math.sin(dLat / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(dLng / 2), 2)));
        
        s = s * EARTH_RADIUS_KM;
        s = Math.round(s * 10000) / 10000;

        return s;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

}
