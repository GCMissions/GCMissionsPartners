package com.hengtiansoft.crh.dto;

import java.io.Serializable;
import java.util.List;

public class RegionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    // region Name
    private String name;
    
    // region Color
    private String color;
    
    // region Countries
    private List<CountryDto> item;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<CountryDto> getItem() {
        return item;
    }

    public void setItem(List<CountryDto> item) {
        this.item = item;
    }
}
