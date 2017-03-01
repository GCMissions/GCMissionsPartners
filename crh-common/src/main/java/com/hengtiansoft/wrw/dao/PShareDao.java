package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.PShareEntity;

/**
* Class Name: PShareDao
* Description: 分享记录表
* @author changchen
*
*/
public interface PShareDao extends JpaRepository<PShareEntity, Long>,JpaSpecificationExecutor<PShareEntity>{

    /**
     * 
    * Description: 获取商品被分享的次数
    *
    * @return
    * @author chengchaoyin
     */
    @Query(value = "select p.product_id, count(1) from "
            + "(select distinct t.OPEN_ID, DATE(t.create_date) , t.product_id from p_share t) p "
            + "group by p.product_id", nativeQuery = true)
    List<Object[]> findRecordsByProductId();
}
