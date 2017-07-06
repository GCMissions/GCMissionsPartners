package com.hengtiansoft.common.lbs.bean;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDetailBean {

    private String   uid;                       // Baidu data ID

    private String   geotable_id;               // Baidu table ID

    private String   title;

    private String   address;

    private String   province;

    private String   city;

    private String   district;

    private Integer  coord_type = 3;            //1: GPS latitude and longitude coordinates 2: National Bureau of Surveying latitude and longitude coordinates
                                              // 3: Baidu encryption latitude and longitude coordinates 4: Baidu encryption Mecato coordinates

    private String[] location   = new String[2]; // 0 Longitude 1 latitude

    private String   tags;

    private Integer  distance;

    private Integer  weight;

    private Long     create_time;               // Second level timestamp

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGeotable_id() {
        return geotable_id;
    }

    public void setGeotable_id(String geotable_id) {
        this.geotable_id = geotable_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getCoord_type() {
        return coord_type;
    }

    public void setCoord_type(Integer coord_type) {
        this.coord_type = coord_type;
    }

    public String[] getLocation() {
        return location;
    }

    public void setLocation(String[] location) {
        if (null != location) {
            this.location = Arrays.copyOf(location, location.length);
        }
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

}
