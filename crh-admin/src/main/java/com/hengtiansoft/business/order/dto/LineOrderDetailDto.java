package com.hengtiansoft.business.order.dto;

import java.io.Serializable;
/**
 * 
* Class Name: LineOrderDetailDto
* Description: 记录线下订单详情信息
* @author chengchaoyin
*
 */
public class LineOrderDetailDto implements Serializable{

    /**
    * Variables Name: serialVersionUID
    * Description: TODO
    * Value Description: TODO
    */
    private static final long serialVersionUID = 1L;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 姓名
     */
    private String userName;
    
    /**
     * 订单金额
     */
    private Long orderAmount;
    
    /**
     * 购买的商品数量
     */
    private Integer productNum;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }
    
}
