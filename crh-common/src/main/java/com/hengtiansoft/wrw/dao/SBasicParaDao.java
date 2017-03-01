package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.SBasicParaEntity;

/**
 * Class Name: SBasicParaDao Description: 基础配置参数的Dao层
 * 
 * @author yigesong
 */
public interface SBasicParaDao extends JpaRepository<SBasicParaEntity, Integer> {

	@Query(value = "select a.PARA_VALUE1 from S_BASIC_PARA a where a.TYPE_ID=?1", nativeQuery = true)
	String findParaValue1ByTypeId(Integer id);

	List<SBasicParaEntity> findSBasicParaByTypeId(Integer typeId);

	List<SBasicParaEntity> findByTypeId(Integer key);

	SBasicParaEntity findByParaName(String paraName);
	
	/**
	 * Description: 根据typeId查找数据，在根据para_value1中的内容按数字排序
	 *
	 * @param typeId
	 * @return
	 */
	@Query(value = "select para_name from s_basic_para where type_id=?1 order by para_value1+0 asc", nativeQuery = true)
	List<String> queryActAbout(Integer typeId);
	
	/**
     * Description: 根据typeName查找数据，在根据para_value1中的内容按数字排序
     *
     * @param typeName
     * @return
     */
    @Query(value = "select a.para_name from s_basic_para a inner join s_basic_type b on a.type_id = b.type_id "
            + "where b.type_name=?1 order by a.para_value1+0 asc", nativeQuery = true)
    List<String> queryByTypeName(String typeName);
	
	@Query(value = "select a.PARA_VALUE1 from S_BASIC_PARA a join S_BASIC_TYPE b on a.TYPE_ID=b.TYPE_ID where b.TYPE_NAME=?1 and a.PARA_NAME=?2", nativeQuery = true)
	String findParaValue1ByTypeName(String typeName, String paraName);
}
