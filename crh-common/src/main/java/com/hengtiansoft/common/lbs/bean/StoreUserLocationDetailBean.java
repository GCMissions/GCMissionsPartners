package com.hengtiansoft.common.lbs.bean;

/**
 * Class Name: StoreUserLocationDetailBean Description:
 * 
 * @author taochen
 *
 */
public class StoreUserLocationDetailBean extends LocationDetailBean {

    private String longitude;

    private String latitude;

    private Integer user_id;

    /**
     * @return return the value of the var longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Description: Set longitude value
     * 
     * @param longitude
     * 
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return return the value of the var latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Description: Set latitude value
     * 
     * @param latitude
     * 
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return return the value of the var user_id
     */
    public Integer getUser_id() {
        return user_id;
    }

    /**
     * Description: Set user_id value
     * 
     * @param user_id
     * 
     */
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

}
