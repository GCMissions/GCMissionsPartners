package com.hengtiansoft.church.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.church.entity.CountryRegionRefEntity;

public interface CountryRegionRefDao extends JpaRepository<CountryRegionRefEntity, Long>,
    JpaSpecificationExecutor<CountryRegionRefEntity> {

    @Modifying
    @Query(value = "update country_region_ref set del_flag = '0' where region_id = ?1 and country_id in ?2", nativeQuery = true)
    void initCountryRegionRef(Long regionId, List<Long> countryList);
    
    @Query(value = "select * from country_region_ref where country_id = ?1 and region_id = ?2 and del_flag = '1' order by id desc limit 1", nativeQuery = true)
    CountryRegionRefEntity findByCountyIdAndRegionId(Long countryId, Long regionId);
    
    List<CountryRegionRefEntity> findByRegionIdAndDelFlag(Long regionId, String delFlag);
    
    @Query(value = "select * from country_region_ref where country_id = ?1 and region_id = ?2 order by id desc limit 1", nativeQuery = true)
    CountryRegionRefEntity findByCountyIdWithRegionId(Long countryId, Long regionId);
    
    @Modifying
    @Query(value = "update country_region_ref set del_flag = '0' where region_id = ?1", nativeQuery = true)
    void updateDelFlag(Long regionId);
}
