package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.AppKdPClickRefEntity;

public interface AppKdPClickRefDao extends JpaRepository<AppKdPClickRefEntity, Long>,
    JpaSpecificationExecutor<AppKdPClickRefEntity>{

    @Query(value = "select p.PRODUCT_ID,count(1) from app_kd_p_click_ref p group by p.PRODUCT_ID", nativeQuery = true)
    List<Object[]> getProductClickCount();
}
