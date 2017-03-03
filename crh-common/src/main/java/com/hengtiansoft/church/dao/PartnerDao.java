package com.hengtiansoft.church.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.church.entity.PartnersEntity;

public interface PartnerDao extends JpaRepository<PartnersEntity, Long>,
    JpaSpecificationExecutor<PartnersEntity>{

    @Query(value = "select p.PARTNER_NAME,p.IMAGE,p.MISSION,p.INTRODUCE,c.ID,c.COUNTRY_NAME from partners p left join country_group_ref gr "
                 + " on p.C_G_REF_ID = gr.ID left join country c on gr.COUNTRY_ID = c.ID " 
                 + " where gr.GROUP_ID = ?1 and p.DEL_FLAG = '1' and c.DEL_FLAG = '1' order by p.CREATE_DATE desc,p.ID desc ", nativeQuery = true)
    List<Object[]> getPartnersByGroupId(Long groupId);
}
