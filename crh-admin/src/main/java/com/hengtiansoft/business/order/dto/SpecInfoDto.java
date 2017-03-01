package com.hengtiansoft.business.order.dto;

import java.io.Serializable;

/**
 * 
 * Class Name: SpecInfoDto Description: 下单商品规格信息
 * 
 * @author chengchaoyin
 *
 */
public class SpecInfoDto implements Serializable {

    private static final long serialVersionUID = 2006484690043743986L;

    /**
     * 主规格
     */
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
