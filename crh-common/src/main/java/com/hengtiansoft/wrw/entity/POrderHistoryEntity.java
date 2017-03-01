package com.hengtiansoft.wrw.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hengtiansoft.common.domain.BaseEntity;
/**
 * 
* Class Name: POrderHistoryEntity
* Description: 商品订单历史数据
* @author chengchaoyin
*
 */
@Entity
@Table(name = "p_order_history")
public class POrderHistoryEntity extends BaseEntity{

    /**
    * Variables Name: serialVersionUID
    * Description: TODO
    * Value Description: TODO
    */
    private static final long serialVersionUID = -2432970871056096426L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long              id;

    /**
     * 商品id
     */
    @Column(name = "PRODUCT_ID")
    private Long              productId;

    /**
     * 订单数量
     */
    @Column(name = "ORDER_COUNT")
    private Long              orderCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Long orderCount) {
        this.orderCount = orderCount;
    }
    
}
