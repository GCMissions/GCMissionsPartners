package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.PCategoryEntity;

/**
 * @author jiekaihu
 */
public interface PCategoryDao extends JpaRepository<PCategoryEntity, Long>,
		JpaSpecificationExecutor<PCategoryEntity> {
	
    @Query("select c from PCategoryEntity c  where c.cateName = ?1 and c.status ='1'")
	PCategoryEntity findByCateName(String cateName);

	@Query(value = "select * from P_CATEGORY where PARENT_ID = 0 and STATUS =1 order by SORT ASC,CREATE_DATE DESC ", nativeQuery = true)
	List<PCategoryEntity> findAllFathers();

	@Query(value = "select c.cate_id from P_CATEGORY c where c.cate_id not in (select parent_id from P_CATEGORY where status != '0')", nativeQuery = true)
	List<Integer> noSonList();

	@Query(value = "select * from P_CATEGORY where PARENT_ID = ?1 and STATUS=?2 order by SORT ,CREATE_DATE DESC ", nativeQuery = true)
	List<PCategoryEntity> getListById(Long cateId, String status);
	
	@Query("select a from PCategoryEntity a where a.typeId =?1 and a.status =?2 ")
	List<PCategoryEntity> findCateByTypeIdAndStatus(Long id, String code);
	
	@Query("select a from PCategoryEntity a where a.status =?1 ")
	List<PCategoryEntity> findAllByStatus(String code);
	
	@Query("select a from PCategoryEntity a where a.parentId =?1")
	PCategoryEntity findbyParentId(Long cateId);
	
	@Query("select a from PCategoryEntity a where a.parentId =?1")
	List<PCategoryEntity> findByParentId(Long cateId);
	
	@Query("from PCategoryEntity a where a.cateId in ?1")
	List<PCategoryEntity> findByCateId(List<Long> cateIds);
	
	@Query("select a from PCategoryEntity a,PCategoryEntity b where a.cateId =b.parentId and b.cateId =?1")
	PCategoryEntity findCateByParentId(Long cateId);
	
	@Query
	PCategoryEntity findPathByCateId(Long cateId);
	
	@Query("SELECT p.cateId FROM PCategoryEntity p WHERE LOCATE(?1,p.path)>0 ")
    List<Long> findAllChild(String cateId);
	
	@Query("SELECT P.cateId FROM PCategoryEntity P WHERE P.parentId = ?1 AND P.status = ?2 ")
    List<Long> findChildByParentAndStatus(Long cateId, String status);

	@Query(value="select c from PCategoryEntity c where c.status=?1 and c.parentId=?2 order by c.sort asc, c.createDate desc")
    List<PCategoryEntity> findByStatusAndParentId(String code,Long parentId);
    
    @Query(value="select p from PCategoryEntity p where p.status=?1 and p.parentId in ?2 order by p.parentId,p.sort, p.createDate desc")
    List<PCategoryEntity> findChildByParentIds(String code,List<Long> parentIds);
    /**
     * 
    * Description: 查询出所有的二级分类
    *
    * @param code
    * @return
     */
    @Query("select a from PCategoryEntity a ,PCategoryEntity b where a.parentId =b.cateId and b.parentId = ?2 and a.status =?1 order by a.sort, a.createDate desc")
    List<PCategoryEntity> findAllCateByStatus(String code,Long parentId);
}
