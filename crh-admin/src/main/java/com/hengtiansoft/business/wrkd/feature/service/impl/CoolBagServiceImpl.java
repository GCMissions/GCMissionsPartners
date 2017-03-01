/*
 * Project Name: wrw-admin
 * File Name: CoolBagServiceImpl.java
 * Class Name: CoolBagServiceImpl
 *
 * Copyright 2014 Hengtian Software Inc
 *
 * Licensed under the Hengtiansoft
 *
 * http://www.hengtiansoft.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.wrkd.feature.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.wrkd.activity.service.KdCharityService;
import com.hengtiansoft.business.wrkd.feature.dto.FeatureDto;
import com.hengtiansoft.business.wrkd.feature.dto.FeatureInfoDto;
import com.hengtiansoft.business.wrkd.feature.dto.FeaturePageDto;
import com.hengtiansoft.business.wrkd.feature.service.CoolBagService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.exception.BizServiceException;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.BasicUtil;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.wrw.dao.CoolbagCollectDao;
import com.hengtiansoft.wrw.dao.CoolbagFeatureDao;
import com.hengtiansoft.wrw.dao.CoolbagFeatureTagRefDao;
import com.hengtiansoft.wrw.dao.CoolbagImageDao;
import com.hengtiansoft.wrw.dao.KdAdvertiseDao;
import com.hengtiansoft.wrw.entity.CoolbagFeatureEntity;
import com.hengtiansoft.wrw.entity.CoolbagFeatureTagRefEntity;
import com.hengtiansoft.wrw.entity.CoolbagImageEntity;
import com.hengtiansoft.wrw.enums.AppImageType;
import com.hengtiansoft.wrw.enums.BaseStatus;
import com.hengtiansoft.wrw.enums.KdAdvertiseSkipTypeEnum;
import com.hengtiansoft.wrw.enums.KdCharityStatusEnum;
import com.hengtiansoft.wrw.enums.StatusEnum;

/**
 * Class Name: CoolBagServiceImpl Description: TODO
 * 
 * @author kangruan
 *
 */
@Service
public class CoolBagServiceImpl implements CoolBagService {

    @Autowired
    private CoolbagImageDao coolbagImageDao;

    @Autowired
    private CoolbagFeatureDao coolbagFeatureDao;

    @Autowired
    private CoolbagFeatureTagRefDao coolbagFeatureTagRefDao;

    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private CoolbagCollectDao collectDao;
    
    @Autowired
    private KdAdvertiseDao kdAdvertiseDao;
    
    @Autowired
    private KdCharityService kdCharityService;
    
    //酷袋轮播广告 类型
    private final String  FEATURE_IMAGE_TYPE ="0";
    
    //酷袋轮播广告  至少存在的数量
    private final int     FEATURE_IMAGE_NUM = 3;
    
    private final String  FEATURE_SOURCE = "1";
    
    // 名称中特殊字符
    private static final char ESCAPE = '\\';
    
    /**
     * 
     * Description: 获取酷袋图片列表
     *
     * @param type
     *            图片类型
     */
    @Override
    public List<CoolbagImageEntity> findImageList(AppImageType type) {
        return coolbagImageDao.findByTypeAndStatus(type.getKey(),BaseStatus.EFFECT.getKey());
    }

    /**
     * 
     * Description: 更新图片信息/或者保存图片信息
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public CoolbagImageEntity updateImage(CoolbagImageEntity entity) {
        CoolbagImageEntity coolbag = null;
        if (null == entity.getId() ) {
        	synchronized (this) {
        		coolbag = ConverterService.convert(entity, CoolbagImageEntity.class);
                Integer sort = coolbagImageDao.findMaxSort(FEATURE_IMAGE_TYPE);
                coolbag.setStatus(BaseStatus.EFFECT.getKey());
                coolbag.setType(FEATURE_IMAGE_TYPE);
                coolbag.setCreateDate(new Date());
                coolbag.setCreateId(AuthorityContext.getCurrentUser().getUserId());
                coolbag.setSort(sort);
            }
        } else {
        	coolbag = coolbagImageDao.findOne(entity.getId());
        	coolbag.setUrl(entity.getUrl());
            coolbag.setFeatureId(entity.getFeatureId());
            coolbag.setName(entity.getName());
            coolbag.setActId(entity.getActId());
            coolbag.setActName(entity.getActName());
            coolbag.setProductId(entity.getProductId());
            coolbag.setProductName(entity.getProductName());
            coolbag.setRelfUrl(entity.getRelfUrl());
            coolbag.setModifyId(AuthorityContext.getCurrentUser().getUserId());
            coolbag.setModifyDate(new Date());
        }
        
        return coolbagImageDao.save(coolbag);
    }

    /**
     * 
     * Description: 获取专辑分页信息
     *
     * @param pageDto
     */
    @Override
    @SuppressWarnings("unchecked")
    public void findFeaturePage(FeaturePageDto pageDto) {
        StringBuilder countSql = new StringBuilder();
        countSql.append("SELECT count(*) FROM (");

        StringBuilder querySql = new StringBuilder();
        querySql.append(" select * from ( ");
        querySql.append(" select f.id, f.tag, f.name, f.description, f.image, f.create_date, f.sort, ");
        querySql.append(" f.sheif_status, group_concat(i.name,'') as tagName ");
        querySql.append(" from coolbag_feature f, coolbag_feature_tag_ref r, coolbag_image i ") ;
        querySql.append(" where r.tag_id = i.id and r.feature_id = f.id and f.status = ");
        querySql.append(StatusEnum.NORMAL.getCode());
        if (FEATURE_SOURCE.equals(pageDto.getSource())) {
            querySql.append(" and f.sheif_status = ").append(BaseStatus.EFFECT.getKey());
        }
        // 根据专辑名称查询
        if (StringUtils.isNotBlank(pageDto.getName())) {
            String featureName = pageDto.getName();
            featureName = featureName.replace("\\", ESCAPE + "\\");
            featureName = featureName.replace("%", ESCAPE + "%");
            featureName = featureName.replace("_", ESCAPE + "_");
            querySql.append(" and f.name like ").append("'%").append(featureName).append("%'");
            querySql.append(" escape '\\\\' ");
        }
        querySql.append(" group by f.id ) AS B");
        
        // 根据标签过滤
        if (ArrayUtils.isNotEmpty(pageDto.getTagNames())) {
            querySql.append(" WHERE 1=1 ");
            for (int i = 0, len = pageDto.getTagNames().length; i < len; i++) {
                querySql.append(" AND instr(B.tagName, '");
                querySql.append(pageDto.getTagNames()[i]);
                querySql.append("') > 0");
            }
        }
        
        countSql.append(querySql).append(" ) A");
        
        Query countQuery = entityManager.createNativeQuery(countSql.toString());
        int totalRecord = ((BigInteger) countQuery.getSingleResult()).intValue();
        pageDto.setTotalRecord((long)totalRecord);
        int pages = totalRecord / pageDto.getPageSize();
        pageDto.setTotalPages(totalRecord % pageDto.getPageSize() == 0 ? pages : pages + 1);
        
        querySql.append(" order by B.sort desc ");
        Query query = entityManager.createNativeQuery(querySql.toString());
        pageDto.setList(buildFeatureistDtos(query.getResultList(), pageDto.getPageSize(), pageDto.getCurrentPage()));
    }
    
    private List<FeatureDto> buildFeatureistDtos(List<Object[]> list, Integer pageSize, Integer currentPage) {
        Integer fromIndex = pageSize * (currentPage - 1);
        Integer toIndex = pageSize * currentPage;
        
        int listLen = list.size();
        fromIndex = (fromIndex >= listLen ? listLen : fromIndex);
        toIndex = (toIndex >= listLen ? listLen : toIndex);
        
        // 进行假分页
        List<Object[]> listResult = new ArrayList<Object[]>();
        listResult = list.subList(fromIndex, toIndex);
        
        List<FeatureDto> resultList = new ArrayList<FeatureDto>();
        if (CollectionUtils.isNotEmpty(listResult)) {
            FeatureDto listDto = null;
            // 获取最大值和最小值
//            Integer maxSortValue = BasicUtil.objToInteger(list.get(0)[6]);
//            Integer minSortValue = BasicUtil.objToInteger(list.get(listLen - 1)[6]);
            
            Integer maxSortValue = coolbagFeatureDao.findMaxSortByStatus(StatusEnum.NORMAL.getCode());
            Integer minSortValue = coolbagFeatureDao.findMinSortByStatus(StatusEnum.NORMAL.getCode());
            for (Object[] obj : listResult) {
                listDto = new FeatureDto();
                listDto.setId(BasicUtil.objToLong(obj[0]));
                listDto.setTag(BasicUtil.objToString(obj[1]));
                listDto.setName(BasicUtil.objToString(obj[2]));
                listDto.setDescription(BasicUtil.objToString(obj[3]));
                listDto.setImage(BasicUtil.objToString(obj[4]));
                listDto.setCreateDate(DateTimeUtil.parseDate(BasicUtil.objToString(obj[5]), DateTimeUtil.SIMPLE_FMT));
                listDto.setSort(BasicUtil.objToInteger(obj[6]));
                listDto.setSheifStatus(BasicUtil.objToString(obj[7]));
                listDto.setTagName(BasicUtil.objToString(obj[8]));
                listDto.setMaxSortValue(maxSortValue);
                listDto.setMinSortValue(minSortValue);
                resultList.add(listDto);
            }
        }
        return resultList;
    }

    /**
     * 
     * Description: 保存或更新 专辑信息
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public CoolbagFeatureEntity saveOrUpdate(FeatureInfoDto dto) {
        CoolbagFeatureEntity entity = null;

        if (StringUtils.isEmpty(dto.getDetails())) {
            throw new BizServiceException("专辑详情不能为空");
        }
        if (StringUtils.isEmpty(dto.getName())){
        	throw new BizServiceException("专辑名称不能为空");
        }
        if (dto.getId() == null) {
            synchronized (this) {
                entity = ConverterService.convert(dto, CoolbagFeatureEntity.class);
                Integer sort = coolbagFeatureDao.findMaxSort();
                entity.setStatus(BaseStatus.EFFECT.getKey());
                entity.setCreateDate(new Date());
                entity.setCreateId(AuthorityContext.getCurrentUser().getUserId());
                entity.setSheifStatus("0");
                entity.setSort(sort);
            }
        } else {
            entity = coolbagFeatureDao.findOne(dto.getId());
            entity.setImage(dto.getImage());
            entity.setTag(dto.getTag());
            entity.setName(dto.getName());
            entity.setDescription(dto.getDescription());
            entity.setDetails(dto.getDetails());
            entity.setBuyUrl(dto.getBuyUrl());
            entity.setIfHomeshow(dto.getIfHomeshow());
            
            entity.setModifyDate(new Date());
            entity.setModifyId(AuthorityContext.getCurrentUser().getUserId());
        }
        coolbagFeatureDao.save(entity);
        saveFeatureTagRef(entity.getId(), dto.getTagIds());

        return entity;
    }

    @Transactional
    private void saveFeatureTagRef(Long featureId, List<Long> tagIds) {
        List<CoolbagFeatureTagRefEntity> refList = null;
        coolbagFeatureTagRefDao.deleteByFeatureId(featureId);
        if (CollectionUtils.isNotEmpty(tagIds)) {
            refList = new ArrayList<CoolbagFeatureTagRefEntity>();
            CoolbagFeatureTagRefEntity entity = null;
            for (Long id : tagIds) {
                entity = new CoolbagFeatureTagRefEntity();
                entity.setFeatureId(featureId);
                entity.setTagId(id);
                refList.add(entity);
            }
            coolbagFeatureTagRefDao.save(refList);
        }
    }

    /**
     * 
     * Description: 根据ID查找专辑信息
     *
     * @param id
     * @return
     */
    @Override
    public FeatureInfoDto findFeature(Long id) {
        CoolbagFeatureEntity entity = coolbagFeatureDao.findOne(id);
        FeatureInfoDto dto = ConverterService.convert(entity, FeatureInfoDto.class);
        dto.setTagIds(coolbagFeatureTagRefDao.findTagIdsByFeatureId(entity.getId()));
        if (CollectionUtils.isNotEmpty(dto.getTagIds())) {
            // 专辑所属标签
            dto.setTagName(coolbagImageDao.findTagNameById(AppImageType.TAG.getKey(), BaseStatus.EFFECT.getKey(), dto.getTagIds()));
        }
        return dto;
    }

    /**
     * 
     * Description: 修改排序
     *
     * @param id
     *            专辑id
     * @param sort
     *            专辑 序号
     * @param type
     *            asc：升序 desc：降序
     */
    @Override
    @Transactional
    public void updateFeatureSort(Long id, Integer sort, String type) {
        CoolbagFeatureEntity feature = coolbagFeatureDao.findOne(id);
        if (!sort.equals(feature.getSort())) {
            throw new BizServiceException("专辑排序已改变，请重新刷新页面后再试");
        }
        if (sort == 1 && "asc".equals(type)) {
            return;
        }
        if (sort.equals(coolbagFeatureDao.countFeature()) && "desc".equals(type)) {
            return;
        }
        synchronized (this) {
            if ("asc".equals(type)) {
                coolbagFeatureDao.updateSortAsc(id, sort);
            } else {
                coolbagFeatureDao.updateSortDesc(id, sort);
            }
        }
    }
    
    /**
     * Description:删除专辑操作
     * 
     */
	@Override
	@Transactional
	public ResultDto<String> deleteFeature(Long id) {
		CoolbagFeatureEntity feature = coolbagFeatureDao.findOne(id);
        if (feature == null) {
            throw new WRWException("获取专辑信息失败，无法删除！");
        }
        // 判断该专辑是否已上架，如果已上架，则无法删除!
        if ("1".equals(feature.getSheifStatus())) {
            throw new WRWException("该专辑已上架,请先下架之后再进行删除操作！");
        }
        Long userId =AuthorityContext.getCurrentUser().getUserId();
        // 与之绑定的公益活动失效
        kdCharityService.updateCharityStatus(feature.getId(), KdCharityStatusEnum.INVALID.getKey(), userId);
		feature.setStatus(StatusEnum.REMOVED.getCode());
		feature.setModifyDate(new Date());
		feature.setModifyId(userId);
		coolbagFeatureDao.save(feature);
		
		//更新收藏中包含该专辑的状态
		collectDao.updateCollect(id);
		return ResultDtoFactory.toAck("删除成功!");
	}
	
	/**
	 * Description:专辑上下架操作
	 * 
	 */
	@Override
	@Transactional
	public ResultDto<String> sheifFeature(Long id) {
		CoolbagFeatureEntity feature = coolbagFeatureDao.findOne(id);
        if (feature == null) {
            throw new WRWException("获取专辑信息失败，无法操作!");
        }
        List<CoolbagFeatureTagRefEntity> tagRefList = coolbagFeatureTagRefDao.findByFeatureId(feature.getId());
        for (CoolbagFeatureTagRefEntity tagRef : tagRefList) {
            if (!kdAdvertiseDao.findBySkipTypeAndActIdAndStatus(KdAdvertiseSkipTypeEnum.FEATURE.getCode(), tagRef.getId(), StatusEnum.NORMAL.getCode()).isEmpty()) {
                throw new WRWException("该专辑已绑定了广告位!");
            }
        }
        // 与之绑定的公益活动在进行中
        if (kdCharityService.checkCharityByFeature(feature.getId())) {
            throw new WRWException("该专辑已绑定的公益活动正在进行中!");
        }
        
        Long userId = AuthorityContext.getCurrentUser().getUserId();
        if (BaseStatus.EFFECT.getKey().equals(feature.getSheifStatus())) {
            // 与之绑定的公益活动失效
            kdCharityService.updateCharityStatus(feature.getId(), KdCharityStatusEnum.OFFSALE.getKey(), userId);
            feature.setSheifStatus(BaseStatus.INVALID.getKey());
            // 更新收藏中包含该专辑的状态
            collectDao.updateCollect(id);
        } else {
            feature.setSheifStatus(BaseStatus.EFFECT.getKey());
            feature.setSheifDate(new Date());// 设置上架时间
            //更新收藏中包含该专辑的状态
            collectDao.updateCollectValid(id);
        }
		feature.setModifyDate(new Date());
		feature.setModifyId(userId);
		coolbagFeatureDao.save(feature);
		
		return ResultDtoFactory.toAck("操作成功!");
	}

	@Override
	@Transactional
	public ResultDto<String> deleteImage(Long id) {
		CoolbagImageEntity	coolbag = coolbagImageDao.findOne(id);
        if (coolbag == null) {
            throw new WRWException("获取轮播广告信息失败，无法操作!");
        }
        Integer countCoolbagImage = coolbagImageDao.findCountByStatus(BaseStatus.EFFECT.getKey(), FEATURE_IMAGE_TYPE);
        if (countCoolbagImage <= FEATURE_IMAGE_NUM) {
            throw new WRWException("删除失败，请至少拥有3个轮播广告位!");
        }
		
		coolbag.setStatus(BaseStatus.INVALID.getKey());
		coolbag.setModifyDate(new Date());
		coolbag.setModifyId(AuthorityContext.getCurrentUser().getUserId());
		
		coolbagImageDao.save(coolbag);
		return ResultDtoFactory.toAck("删除成功!");
	}

}
