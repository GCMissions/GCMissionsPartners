package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.SRegionEntity;

public interface SRegionDao extends JpaRepository<SRegionEntity, Integer> {

    public List<SRegionEntity> findByParentId(Integer parentId);

    public List<SRegionEntity> findByName(String name);

    public List<SRegionEntity> findByLevelType(Integer levelType);

    @Query(value = "SELECT id FROM SRegionEntity WHERE parentId = ?1")
    public List<Integer> findIdByParentId(Integer parentId);

    @Query(value = "SELECT t.parent_id,a.NAME, COUNT(1), SUM(t.OPEN_FLAG) FROM S_REGION t,S_REGION a WHERE a.ID = t.PARENT_ID AND t.level_type = 2 GROUP BY t.parent_id,a.NAME ", nativeQuery = true)
    public List<Object[]> findAllProvOpen();

    @Query("select s from SRegionEntity s where s.openFlag =1 or s.id =100000 ")
    public List<SRegionEntity> findByOpenFlag();

    public SRegionEntity findByShortName(String city);

    /**
     * Description: 查询出所有openFlag != 1的城市
     *
     * @return
     */
    @Query("select s from SRegionEntity s where s.openFlag =0  and s.levelType =2 ")
    public List<SRegionEntity> findAllOther();

    public List<SRegionEntity> findByLevelTypeOrderByPinyinAsc(Integer key);

    @Query(value = "select s from SRegionEntity s where s.levelType=?1 and s.pinyin is not null")
    public List<SRegionEntity> findAllCities(Integer level);

    @Query(value = "select parent.ID,parent.SHORT_NAME from S_REGION parent inner join S_REGION child on parent.ID = child.PARENT_ID where child.ID=?1", nativeQuery = true)
    public List<Object[]> getParent(Integer childRegion);
    
    @Query(value = "select parent.* from S_REGION parent inner join S_REGION child on parent.ID = child.PARENT_ID where child.ID=?1", nativeQuery=true)
    public SRegionEntity getParentEntity(Integer childRegion);

    @Query("select id from SRegionEntity s where s.treePath like ?1 ")
    public List<Integer> findAllByTreePath(String parentId);
    
    /**
     * 
    * Description: 通过regionId和openFlag来判断该传入城市是否为开放城市
    *
    * @param regionId
    * @param regionOpenFlag
    * @return
     */
    @Query("select count(s) from SRegionEntity s where s.id =?1 and s.openFlag = ?2 ")
    public int findByOpenFlagAndRegionId(Integer regionId, String regionOpenFlag);
    /**
     * 
    * Description: 查询出 所有设置为 ( login_flag = 1 )城市信息
    *
    * @param searchCityIdList
    * @return
     */
    @Query("select s from SRegionEntity s where s.loginFlag = ?1 order by id")
    public List<SRegionEntity> findAllByLoginFlag(String flag);
    
    /**
     * 
    * Description: 查询出 所有设置为 (HOT_FLAG =1) 的城市信息，前提条件是这些城市的父节点的城市的login_flag必须为1 
    *
    * @param searchCityIdList
    * @return
     */
    @Query("select s from SRegionEntity s ,SRegionEntity sr where s.hotFlag =?1 and s.parentId =sr.id  and sr.loginFlag =?2 order by s.id")
    public List<SRegionEntity> findAllByHotFlag(String hotFlag,String loginFlag);
    
    @Query("select s from SRegionEntity s where s.parentId in (?1) order by s.id")
    public List<SRegionEntity> findAllByParentIds(List<Integer> listIds);
    
    @Query("select s.name from SRegionEntity s where s.loginFlag = ?1 order by id")
    public List<String> findAllStringByLoginFlag(String regionLoginFlag);

}
