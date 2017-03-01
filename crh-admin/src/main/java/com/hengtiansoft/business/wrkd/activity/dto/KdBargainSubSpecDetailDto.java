package com.hengtiansoft.business.wrkd.activity.dto;

import java.util.Map;

public class KdBargainSubSpecDetailDto {

    /**
     * 主规格名称
     */
    private String mainSpec;
    
    /**
     * 子规格数据,String:子规格名称；Boolean:是否选中的子规格名称
     */
    private Map<String,Boolean> subSpecData;
    
    public Map<String, Boolean> getSubSpecData() {
        return subSpecData;
    }

    public void setSubSpecData(Map<String, Boolean> subSpecData) {
        this.subSpecData = subSpecData;
    }

    public String getMainSpec() {
        return mainSpec;
    }

    public void setMainSpec(String mainSpec) {
        this.mainSpec = mainSpec;
    }
}
