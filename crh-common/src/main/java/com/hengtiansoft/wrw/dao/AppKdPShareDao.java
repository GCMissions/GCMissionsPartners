package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.AppKdPShareEntity;

public interface AppKdPShareDao extends JpaRepository<AppKdPShareEntity, Long>,
    JpaSpecificationExecutor<AppKdPShareEntity>{

    /**
     * 
    * Description: 获取商品被分享的次数
    *
    * @return
    * @author yiminli
     */
    @Query(value = "select p.product_id, count(1) from "
            + "(select distinct t.OPEN_ID, DATE(t.create_date) , t.product_id from app_kd_p_share t) p "
            + "group by p.product_id", nativeQuery = true)
    List<Object[]> findRecordsByProductId();
}
