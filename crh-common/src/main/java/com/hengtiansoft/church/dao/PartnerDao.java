package com.hengtiansoft.church.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.church.entity.PartnersEntity;

public interface PartnerDao extends JpaRepository<PartnersEntity, Long>,
    JpaSpecificationExecutor<PartnersEntity>{

    @Query(value = "select p.* from partners p left join country_region_ref gr on p.C_R_REF_ID = gr.ID where gr.REGION_ID = ?1 and gr.COUNTRY_ID = ?2 and p.DEL_FLAG = '1'", nativeQuery = true)
    List<PartnersEntity> getAllPartnersByRegionIdAndCountryId(Long regionId, Long countryId);
}
