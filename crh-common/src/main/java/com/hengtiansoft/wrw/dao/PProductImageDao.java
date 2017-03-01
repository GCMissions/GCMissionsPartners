package com.hengtiansoft.wrw.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.PProductImageEntity;

public interface PProductImageDao extends JpaRepository<PProductImageEntity, Long>{

	@Query("update PProductImageEntity p set p.status =?2,p.modifyDate =?3,p.modifyId =?4 where p.productId =?1")
	@Modifying
	void deleteByProductId(Long productId, String code, Date date, Long userId);
	
	@Query("delete from PProductImageEntity p where p.productId =?1 and p.imageId not in (?2)")
	@Modifying
    void deleteByProductIdAndId(Long productId, List<Long> imageIds);
	
	@Query("select i from PProductImageEntity i where i.productId=?1 and i.status=?2 order by i.sort ")
	List<PProductImageEntity> findByProductIdAndStatus(Long id, String status);

	@Query("delete from PProductImageEntity p where p.productId =?1 ")
    @Modifying
    void delByProductId(Long id);

}
