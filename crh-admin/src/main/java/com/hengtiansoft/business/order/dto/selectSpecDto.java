package com.hengtiansoft.business.order.dto;


public class selectSpecDto {

    private String mainSpec;
    
    /**
     * 子规格
     */
    private String subSpec;

    public String getMainSpec() {
        return mainSpec;
    }

    public void setMainSpec(String mainSpec) {
        this.mainSpec = mainSpec;
    }

    public String getSubSpec() {
        return subSpec;
    }

    public void setSubSpec(String subSpec) {
        this.subSpec = subSpec;
    }
}
