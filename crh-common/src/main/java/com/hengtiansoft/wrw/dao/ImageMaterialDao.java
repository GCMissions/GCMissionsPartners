package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.ImageMaterialEntity;

public interface ImageMaterialDao extends JpaRepository<ImageMaterialEntity, Long>, JpaSpecificationExecutor<ImageMaterialEntity> {

    @Query(value = "insert into image_material (url, status, create_date) values (?1, '1', now())", nativeQuery = true)
    @Modifying
    void addRecord(String url);
    
    @Query(value = "update image_material set status = ?1 where id in ?2", nativeQuery = true)
    @Modifying
    void updateStatus(String status, List<Long> ids);
}
