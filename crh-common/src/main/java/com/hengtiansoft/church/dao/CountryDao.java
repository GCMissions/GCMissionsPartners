package com.hengtiansoft.church.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.church.entity.CountryEntity;

public interface CountryDao extends JpaRepository<CountryEntity, Long>,
    JpaSpecificationExecutor<CountryEntity>{

    @Query(value = "select c.* from country c left join country_region_ref gr on c.ID = gr.COUNTRY_ID where gr.REGION_ID = ?1 and c.del_flag = '1' and gr.del_flag = '1' order by c.country_name", nativeQuery = true)
    List<CountryEntity> findAllCountryByGroupId(Long groupId);
    
    @Query(value = "select c.* from country c left join country_region_ref cr on c.ID = cr.COUNTRY_ID where c.DEL_FLAG = '1' and (cr.ID is null or cr.DEL_FLAG = '0') order by c.country_name", nativeQuery = true)
    List<CountryEntity> findNoRegionCountries();
    
    @Query(value = "select c.* from country c left join country_region_ref gr on c.ID = gr.COUNTRY_ID where gr.REGION_ID = ?1 and c.id != ?2 and c.del_flag = '1' and gr.del_flag = '1' order by c.country_name", nativeQuery = true)
    List<CountryEntity> findAllCountryByGroupIdAndNotEqId(Long groupId, Long countryId);
}
