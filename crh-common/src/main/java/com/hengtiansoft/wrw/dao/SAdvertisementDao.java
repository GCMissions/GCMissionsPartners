package com.hengtiansoft.wrw.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.SAdEntity;

/**
 * @author jiekaihu
 */
public interface SAdvertisementDao extends JpaRepository<SAdEntity, Long>, JpaSpecificationExecutor<SAdEntity> {

	@Query(value = "select ad.* from S_AD ad, S_AD_REGION adregion where ad.AD_ID=adregion.AD_ID and adregion.REGION_ID=?1 order by ad.sort asc", nativeQuery = true)
    List<SAdEntity> findByRegion(Integer regionId);

    @Query(value = "select * from S_AD where `LOCAL` in (?1) and status = 1 ORDER BY `LOCAL` ,SORT ;", nativeQuery = true)
    List<SAdEntity> findByType(List<String> types);
    
    @Query(value = "select ad.* from S_AD ad where ad.LOCAL in('3','4') and STATUS=?1 and IMAGE is not null order by ad.sort asc", nativeQuery = true)
    List<SAdEntity> findAppAds(String status);

    SAdEntity findByTitle(String title);
    
    @Query(value = "select ad from SAdEntity ad where ad.local = ?1 and ad.modifyDate > ?2 and ad.status = ?3 and ad.image is not null order by ad.sort")
    List<SAdEntity> findAppActivity(String local, Date lastShowDate, String status);
    
    @Query(value = "select ad from SAdEntity ad where ad.type = ?1 and ad.status = '1' order by ad.sort")
    List<SAdEntity> findAdvertiseByType(String adType);

    @Modifying
    @Query(value = "update s_ad set ac_id = ?1,url = ?2,modify_date = now() where ad_id=?3", nativeQuery = true)
    void updateSAd(Long acId, String url, Long adId);
    
    @Modifying
    @Query(value = "update s_ad set ac_id = null, url = null, modify_date = now() where ad_id = ?1", nativeQuery = true)
    void deleteCarouselAd(Long adId);
    
    @Modifying
    @Query(value = "update s_ad set status = 0 where ad_id = ?1", nativeQuery = true)
    void deleteSalesAd(Long adId);
    
    @Modifying
    @Query(value = "update s_ad set sort = sort - 1 where sort > ?1 and type = '5' and status = '1'", nativeQuery = true)
    void updateSort(Integer sort);
    
    @Query(value = "select sort from s_ad where type = '5' and status = '1' order by sort desc limit 1", nativeQuery = true)
    Integer findLastSort();
    
    @Query(value = "update s_ad set sort = sort + ?1 where ad_id = ?2 and type = '5' and status = '1'", nativeQuery = true)
    @Modifying
    void updateSortById(Integer sort, Long adId);
    
    @Query(value = "select * from s_ad where type = '5' and status = '1' and sort = ?1 limit 1", nativeQuery = true)
    SAdEntity findBySort(Integer sort);
    
    @Query(value = "select * from s_ad where type = ?1 and sort = ?2 and status = '1' limit 1", nativeQuery = true)
    SAdEntity findByTypeAndSort(String type, Integer sort);
    
    @Modifying
    @Query(value = "update s_ad set title = ?1 where ad_id = ?2 and status = '1'", nativeQuery = true)
    void updateTitle(String title, Long adId);
    
    @Modifying
    @Query(value = "update s_ad set image = ?1 where ad_id = ?2 and status = '1'", nativeQuery = true)
    void updateUrl(String title, Long adId);
    
    List<SAdEntity> findByTypeAndStatus(String type, String status);
}
