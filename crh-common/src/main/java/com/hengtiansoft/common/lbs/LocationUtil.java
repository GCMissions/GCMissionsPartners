package com.hengtiansoft.common.lbs;

/**
 * Description: Calculate the distance using the algorithm for Google Maps
 * 
 * @author taochen
 */
public class LocationUtil {

    // Equatorial radius (accuracy: m)
    private static final double EARTH_RADIUS_M = 6378137;

    // Equatorial radius (accuracy: km)
    private static final double EARTH_RADIUS_KM = 6378.137;

    /**
     * Description: Get the distance between two latitude and longitude (accurate to meters)
     *
     * @param lat1
     *            Latitude point 1
     * @param lng1
     *            Longitude point 1
     * @param lat2
     *            Latitude point 2
     * @param lng2
     *            Longitude point 2
     * @return
     */
    public static double getDistance_M(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);

        double dLat = radLat1 - radLat2;
        double dLng = rad(lng1 - lng2);

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(dLat / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(dLng / 2), 2)));

        s = s * EARTH_RADIUS_M;
        s = Math.round(s * 10000) / 10000;

        return s;
    }

    /**
     * Description: Get the distance between two latitude and longitude (accurate to kilometers)
     *
     * @param lat1
     *            Latitude point 1
     * @param lng1
     *            Longitude point 1
     * @param lat2
     *            Latitude point 2
     * @param lng2
     *            Longitude point 2
     * @return
     */
    public static double getDistance_KM(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);

        double dLat = radLat1 - radLat2;
        double dLng = rad(lng1 - lng2);

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(dLat / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(dLng / 2), 2)));

        s = s * EARTH_RADIUS_KM;
        s = Math.round(s * 10000) / 10000;

        return s;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

}
