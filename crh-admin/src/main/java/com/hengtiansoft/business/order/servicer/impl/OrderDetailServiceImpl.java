package com.hengtiansoft.business.order.servicer.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.order.dto.OrderDetailDto;
import com.hengtiansoft.business.order.servicer.OrderDetailService;

/**
 * 
 * Class Name: OrderDetailServiceImpl
 * Description: 订单详情service 实现类
 * @author kangruan
 *
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService{
    @Autowired
    EntityManager entityManager;

    /**
     * 
     * Description: 获取订单详情
     *
     * @param orderId 订单id
     * @return
     */
    @Override
    public List<OrderDetailDto> findByOrderId2Dto(String orderId) {
        StringBuilder hql = new StringBuilder();
        
        hql.append(" select new  com.hengtiansoft.business.order.dto.OrderDetailDto(p.productCode, ");
        hql.append(" p.productName,p.brandName,p.cateName,d.price,d.num,d.amount) ");
        hql.append(" from MOrderDetailEntity d, PProductEntity p");
        hql.append(" where d.productId = p.productId  ");
        hql.append(" and d.orderId =:orderId ");
        
        Query  query = entityManager.createQuery(hql.toString());
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }
    
   

}
