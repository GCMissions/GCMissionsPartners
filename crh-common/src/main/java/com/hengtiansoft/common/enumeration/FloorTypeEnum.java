package com.hengtiansoft.common.enumeration;

/**
 * Class Name: FloorTypeEnum Description: Ad Placement
 * 
 * @author taochen
 */
public enum FloorTypeEnum {
    HOT_SALE("1", "Hot Sale", 4), CORE_PRODUCT("2", "Core Products", 10), HIGH_END("3", "High-end Products", 10), AREA_PRODUCT(
            "4", "Regional Products", 10), AREA_SPEC("5", "Regional Specialty Products", 10);

    /**
     * Corresponding to the level of the floor
     */
    private String position;

    /**
     * The name of the floor
     */
    private String name;

    /**
     * The floor can display the maximum number of products
     */
    private Integer maxProduct;

    private FloorTypeEnum(String position, String name, Integer maxProduct) {
        this.position = position;
        this.name = name;
        this.maxProduct = maxProduct;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxProduct() {
        return maxProduct;
    }

    public void setMaxProduct(Integer maxProduct) {
        this.maxProduct = maxProduct;
    }

}
