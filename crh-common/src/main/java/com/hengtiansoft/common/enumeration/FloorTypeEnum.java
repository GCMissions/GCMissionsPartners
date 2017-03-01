package com.hengtiansoft.common.enumeration;

/**
 * Class Name: FloorTypeEnum Description: 广告位枚举
 * 
 * @author yigesong
 */
public enum FloorTypeEnum {
    HOT_SALE("1", "热销商品", 4), CORE_PRODUCT("2", "核心产品", 10),HIGH_END("3", "高端产品", 10), AREA_PRODUCT("4", "区域产品", 10), AREA_SPEC("5", "当地特供", 10);

    /**
     * 对应楼层的层级
     */
    private String  position;

    /**
     * 楼层名称
     */
    private String  name;

    /**
     * 楼层最多显示几个产品
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
