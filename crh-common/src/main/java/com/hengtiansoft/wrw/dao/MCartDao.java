package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.MCartEntity;

public interface MCartDao extends JpaRepository<MCartEntity, Long> {

    @Modifying
    @Query(value = "update M_CART set FLAG = 0 where  PRODUCT_ID = ?1", nativeQuery = true)
    void changeCartByModifyProduct(Long pid);
    
    @Modifying
    @Query(value="UPDATE M_CART M SET M.FLAG =?1 WHERE M.PRODUCT_ID IN ?2 ", nativeQuery = true)
    void changeCartByFlagAndProductId(String flag,List<String> productId);
    
    /**
     * Description: 根据商品id和活动日期查找购物车信息
     *
     * @param productId
     * @param actDate
     * @return
     */
    @Query(value = "select * from m_cart where product_id =?1 and date(act_date) = ?2", nativeQuery = true)
    List<MCartEntity> findByProductId(Long productId, String actDate);
}
