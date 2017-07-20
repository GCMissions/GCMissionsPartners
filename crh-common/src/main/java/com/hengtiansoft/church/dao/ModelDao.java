package com.hengtiansoft.church.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.hengtiansoft.church.entity.ModelEntity;
import com.mangofactory.swagger.models.dto.Model;

public interface ModelDao extends JpaRepository<ModelEntity, Long>{
    
    @Query(value = "select * from model",nativeQuery = true)
    List<ModelEntity> getAllModel();
    
    @Query(value = "select * from model where display = '1'",nativeQuery = true)
    ModelEntity getCurrentModel();
    
    @Query(value ="update model set display values('1') where id =?1",nativeQuery = true)
    void chooseModel(Long id);
    
    @Query(value ="update model set display values('0') where id =?1",nativeQuery = true)
    void unchooseModel(Long id);

}
