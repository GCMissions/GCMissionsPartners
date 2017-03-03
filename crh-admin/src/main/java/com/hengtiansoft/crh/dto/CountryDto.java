package com.hengtiansoft.crh.dto;

import java.io.Serializable;
import java.util.List;

public class CountryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    
    private List<PartnerDto> partnerList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PartnerDto> getPartnerList() {
        return partnerList;
    }

    public void setPartnerList(List<PartnerDto> partnerList) {
        this.partnerList = partnerList;
    }
}
