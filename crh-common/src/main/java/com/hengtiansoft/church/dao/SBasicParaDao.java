package com.hengtiansoft.church.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.church.entity.SBasicParaEntity;

/**
 * Class Name: SBasicParaDao 
 * Description: Basic parameter configuration
 * 
 * @author taochen
 */
public interface SBasicParaDao extends JpaRepository<SBasicParaEntity, Integer> {

	@Query(value = "select a.PARA_VALUE1 from BASIC_PARA a where a.TYPE_ID=?1", nativeQuery = true)
	String findParaValue1ByTypeId(Integer id);

	List<SBasicParaEntity> findSBasicParaByTypeId(Integer typeId);

	List<SBasicParaEntity> findByTypeId(Integer key);

	SBasicParaEntity findByParaName(String paraName);
	
	/**
	 * Description: Find data according to typeId, sort by number according to the contents of para_value1
	 *
	 * @param typeId
	 * @return
	 */
	@Query(value = "select para_name from basic_para where type_id=?1 order by para_value1+0 asc", nativeQuery = true)
	List<String> queryActAbout(Integer typeId);
	
	/**
     * Description: Find data according to typeName, sort by number according to the contents of para_value1
     *
     * @param typeName
     * @return
     */
    @Query(value = "select a.para_name from basic_para a inner join basic_type b on a.type_id = b.type_id "
            + "where b.type_name=?1 order by a.para_value1+0 asc", nativeQuery = true)
    List<String> queryByTypeName(String typeName);
	
	@Query(value = "select a.PARA_VALUE1 from BASIC_PARA a join BASIC_TYPE b on a.TYPE_ID=b.TYPE_ID where b.TYPE_NAME=?1 and a.PARA_NAME=?2", nativeQuery = true)
	String findParaValue1ByTypeName(String typeName, String paraName);
}
