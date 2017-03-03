package com.hengtiansoft.crh.dto;

import java.io.Serializable;
import java.util.List;

public class CountryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    
    private List<PartnerDto> item;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PartnerDto> getItem() {
        return item;
    }

    public void setItem(List<PartnerDto> item) {
        this.item = item;
    }
}
