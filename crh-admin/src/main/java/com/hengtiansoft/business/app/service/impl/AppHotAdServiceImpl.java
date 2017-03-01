/*
 * Project Name: wrw-admin
 * File Name: AppHotAdServiceImpl.java
 * Class Name: AppHotAdServiceImpl
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
package com.hengtiansoft.business.app.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.activity.dto.ActivityCopyDto;
import com.hengtiansoft.business.activity.dto.ActivityCopyPageDto;
import com.hengtiansoft.business.activity.dto.ActivityImageDto;
import com.hengtiansoft.business.app.dto.AppAddHotAdDto;
import com.hengtiansoft.business.app.dto.AppAdvertiseDto;
import com.hengtiansoft.business.app.dto.AppHotAdDto;
import com.hengtiansoft.business.app.dto.SearchAppHotAdDto;
import com.hengtiansoft.business.app.service.AppHotAdService;
import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.exception.BizServiceException;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.AppHotAdDao;
import com.hengtiansoft.wrw.dao.PCategoryDao;
import com.hengtiansoft.wrw.dao.PProductDao;
import com.hengtiansoft.wrw.dao.PProductImageDao;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.entity.AppHotAdEntity;
import com.hengtiansoft.wrw.entity.PCategoryEntity;
import com.hengtiansoft.wrw.entity.PProductImageEntity;
import com.hengtiansoft.wrw.entity.SOrgEntity;
import com.hengtiansoft.wrw.enums.AdTypeEnum;
import com.hengtiansoft.wrw.enums.ProductStatusEnum;
import com.hengtiansoft.wrw.enums.StatusEnum;

/**
 * Class Name: AppHotAdServiceImpl Description: TODO
 * 
 * @author qianqianzhu
 *
 */
@Service
public class AppHotAdServiceImpl implements AppHotAdService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppHotAdServiceImpl.class);

    @Autowired
    private AppHotAdDao appHotAdDao;

    @Autowired
    private PProductDao pProductDao;

    @Autowired
    private PCategoryDao pcDao;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SOrgDao sOrgDao;

    @Autowired
    private PProductImageDao pImageDao;

    @Override
    public List<AppAdvertiseDto> getAdvertise(String adType) {
        List<AppHotAdEntity> adEntities = appHotAdDao.findAdvertiseByType(adType);
        List<AppAdvertiseDto> adList = new ArrayList<AppAdvertiseDto>();
        for (AppHotAdEntity adEntity : adEntities) {
            AppAdvertiseDto advertiseDto = ConverterService.convert(adEntity, new AppAdvertiseDto());
            if (adEntity.getProductId() == null) {
                advertiseDto.setActiveName("");
            } else {
                String actName = pProductDao.findByProductId(adEntity.getProductId()).getProductName();
                if (actName.length() > 10) {
                    advertiseDto.setShortName(actName.substring(0, 10) + "...");
                } else {
                    advertiseDto.setShortName(actName);
                }
                advertiseDto.setActiveName(actName);
            }
            advertiseDto.setIndex(adEntities.indexOf(adEntity) + 1);
            adList.add(advertiseDto);
        }
        return adList;
    }

    /**
     * 热门推荐
     */
    @Override
    public void findAppHotAd(SearchAppHotAdDto dto) {
        Integer currentPage = dto.getCurrentPage();
        if (currentPage == 0) {
            currentPage = 1;
        }
        PageRequest pageable = new PageRequest(currentPage - 1, dto.getPageSize(), new Sort(Direction.ASC, "sort"));
        Page<AppHotAdEntity> page = appHotAdDao.findAll(new Specification<AppHotAdEntity>() {
            @Override
            public Predicate toPredicate(Root<AppHotAdEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.<String> get("type"), AdTypeEnum.APP_HOT.getCode()));
                predicates.add(cb.equal(root.<String> get("status"), "1"));

                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }
        }, pageable);

        dto.setTotalRecord(page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setList(buildAppHotAdDto(page.getContent(), page.getTotalElements()));

    }

    private List<AppHotAdDto> buildAppHotAdDto(List<AppHotAdEntity> content, long count) {
        List<AppHotAdDto> AppHotAdDtoList = new ArrayList<AppHotAdDto>();
        for (AppHotAdEntity appHotAdEntity : content) {
            AppHotAdDto appHotAdDto = new AppHotAdDto();
            appHotAdDto.setUrl(appHotAdEntity.getUploadImageUrl() + ";" + appHotAdEntity.getSort());
            String productName = "无";
            if (appHotAdEntity.getProductId() != null) {
                productName = pProductDao.findByProductId(appHotAdEntity.getProductId()).getProductName();
            }
            appHotAdDto.setInfo(productName + "#&#" + appHotAdEntity.getTitle() + "#&#" + appHotAdEntity.getId());
            appHotAdDto.setOperation(count + ";" + appHotAdEntity.getId().toString() + ";" + appHotAdEntity.getSort());
            appHotAdDto.setSkipUrl(appHotAdEntity.getRelatedUrl() == null ? "" : appHotAdEntity.getRelatedUrl());

            AppHotAdDtoList.add(appHotAdDto);
        }
        return AppHotAdDtoList;
    }

    @Override
    @Transactional
    public ResultDto<?> editorDesc(Long adId, String description, String relatedUrl) {
        try {
            if (relatedUrl == null || "".equals(relatedUrl)) {
                // 热门说明→title
                appHotAdDao.updateTitle(description, adId);
            } else {
                AppHotAdEntity apHotAdEntity = appHotAdDao.findOne(adId);
                if (apHotAdEntity.getProductId() != null) {
                    return ResultDtoFactory.toNack("该精品专题已经关联了商品");
                }
                // 关联URL
                appHotAdDao.updateUrl(relatedUrl, adId);
            }
            return ResultDtoFactory.toAck("修改成功！");
        } catch (Exception e) {
            LOGGER.error(e.toString());
            return ResultDtoFactory.toNack("修改失败！");
        }
    }

    /**
     *  update 轮播广告；Insert 热门推荐
     */
    @Override
    @Transactional
    public AppHotAdEntity updateOrInsert(AppAddHotAdDto addAdDto) {
        AppHotAdEntity appHotAdEntity = new AppHotAdEntity();
        // 热门推荐最多5个
        if (AdTypeEnum.APP_HOT.getCode().equals(addAdDto.getType())) {
            List<AppHotAdEntity> advertiseList = appHotAdDao.findByTypeAndStatus(addAdDto.getType(), StatusEnum.NORMAL.getCode());
            if (advertiseList.size() >= 5) {
                return null;
            }
        }
        if (addAdDto.getAdId() != null) {
            appHotAdDao.updateAppHotAdEntity(addAdDto.getProductId(), addAdDto.getUrl(), addAdDto.getSkipUrl(),
                    addAdDto.getAdId());
        } else {
            Date date = new Date();
            appHotAdEntity.setId(addAdDto.getAdId());
            appHotAdEntity.setType(addAdDto.getType());
            appHotAdEntity.setStatus(addAdDto.getStatus());
            if (addAdDto.getSkipUrl() == null || "".equals(addAdDto.getSkipUrl())) {
                appHotAdEntity.setProductId(addAdDto.getProductId());
            }
            appHotAdEntity.setUploadImageUrl(addAdDto.getUrl());;
            appHotAdEntity.setSort(addAdDto.getSort());
            appHotAdEntity.setCreateDate(date);
            appHotAdEntity.setModifyDate(date);
            appHotAdEntity.setTitle(addAdDto.getDescription());
            appHotAdEntity.setRelatedUrl(addAdDto.getSkipUrl());;

            appHotAdEntity = appHotAdDao.save(appHotAdEntity);
        }
        return appHotAdEntity;
    }

    /**
     * 轮播or热门推荐 删除
     */
    @Override
    @Transactional
    public Integer deleteAd(Long adId, Integer sort) {
        AppHotAdEntity appHotAdEntity = appHotAdDao.findOne(adId);
        Integer newSort = 0;
        if (AdTypeEnum.APP_CAROUSEL.getCode().equals(appHotAdEntity.getType())) {
            // 轮播广告，清空图片及活动信息
            if (appHotAdDao.findByTypeAndSort(AdTypeEnum.APP_CAROUSEL.getCode(), sort).getProductId() != null) {
                // 该广告位有商品才能删除
                appHotAdDao.deleteCarouselAd(adId);
            } else {
                newSort = -1;
            }
        }
        if (AdTypeEnum.APP_HOT.getCode().equals(appHotAdEntity.getType())) {
            // 热门推荐
            appHotAdDao.deleteHotAd(adId);
            // 把在这个广告后面的广告排序上升
            appHotAdDao.updateSort(appHotAdEntity.getSort());
            newSort = appHotAdDao.findLastSort();
        }
        return newSort;
    }
    
    @Override
    @Transactional
    public ResultDto<?> upOrDownAd(Long adId, Integer sort) {
        try {
            AppHotAdEntity appHotAdEntity = appHotAdDao.findBySort(appHotAdDao.findOne(adId).getSort() + sort);
            appHotAdDao.updateSortById(sort, adId);
            appHotAdDao.updateSortById(-sort, appHotAdEntity.getId());
            return ResultDtoFactory.toAck("success!");
        } catch (Exception e) {
            throw new BizServiceException("调序出错，请稍后再试！");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void search(final ActivityCopyPageDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder conditionSql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");
        StringBuilder sql = new StringBuilder("select p.org_id,p.cate_id,p.product_id,p.description, "
                + " p.IS_CAPTCHA,p.note,p.ORIGINAL_PRICE,p.price, "
                + " p.PRODUCT_NAME,p.REBACK_NOTE from p_product p join p_shief ps on p.product_id = ps.product_id "
                + " where 1=1 ");
        conditionSql.append(" AND P.STATUS = " + ProductStatusEnum.USED.getCode());
        conditionSql.append(" AND PS.SALE_FLAG = " + ProductStatusEnum.USED.getCode());
        countSql.append("select count(1) from (").append(sql);
        // 商品名称
        if (!WRWUtil.isEmpty(dto.getProductName())) {
            String msg = dto.getProductName();
            char escape = '\\';
            msg = msg.replace("\\", escape + "\\");
            msg = msg.replace("%", escape + "%");
            msg = msg.replace("_", escape + "_");
            conditionSql.append(" AND P.PRODUCT_NAME LIKE :NAME escape '\\\\' ");
            param.put("NAME", "%" + msg + "%");
        }
        // 品类
        if (null != dto.getCategoryId()) {
            // 判断是否有子类，无:查询本身；有：查询本身及子类
            List<Integer> noSonList = pcDao.noSonList();
            if (noSonList.contains(Integer.valueOf(dto.getCategoryId().toString()))) {
                conditionSql.append(" AND P.CATE_ID = :CATE_ID");
                param.put("CATE_ID", dto.getCategoryId());
            } else {
                List<Long> cateIds = pcDao.findChildByParentAndStatus(dto.getCategoryId(),
                        ProductStatusEnum.USED.getCode());
                cateIds.add(dto.getCategoryId());
                conditionSql.append(" AND P.CATE_ID IN :CATE_IDS");
                param.put("CATE_IDS", cateIds);
            }
        }
        if (null != dto.getOrgId()) {
            conditionSql.append(" AND P.ORG_ID = :ORG_ID");
            param.put("ORG_ID", dto.getOrgId());
        }
        conditionSql.append(" GROUP BY P.PRODUCT_ID");
        conditionSql.append(" ORDER BY P.CREATE_DATE DESC ");
        Query query = entityManager.createNativeQuery(sql.append(conditionSql).toString());
        QueryUtil.processParamForQuery(query, param);
        Query countQuery = entityManager.createNativeQuery(countSql.append(conditionSql).append(" ) A").toString());
        QueryUtil.processParamForQuery(countQuery, param);
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        dto.setTotalRecord(totalRecord.longValue());
        dto.setTotalPages(totalRecord.intValue() % dto.getPageSize() == 0 ? totalRecord.intValue() / dto.getPageSize()
                : ((totalRecord.intValue() / dto.getPageSize()) + 1));
        dto.setList(buildActivityDtos(query.getResultList()));
    }

    /*
     * 获得活动的服务商和品类名称
     */
    private List<ActivityCopyDto> buildActivityDtos(List<Object[]> content) {
        List<ActivityCopyDto> copyList = new ArrayList<ActivityCopyDto>();
        List<SOrgEntity> orgs = sOrgDao.findAll();
        List<PCategoryEntity> cates = pcDao.findAll();
        // 品类Map
        Map<Long, String> catesMap = new HashMap<Long, String>();
        for (PCategoryEntity cate : cates) {
            catesMap.put(cate.getCateId(), cate.getCateName());
        }
        for (Object[] entity : content) {
            ActivityCopyDto copyEntity = new ActivityCopyDto();
            ConverterService.convert(entity, new ActivityCopyDto());
            copyEntity.setCateId(WRWUtil.objToLong(entity[1]));
            copyEntity.setDescription(WRWUtil.objToString(entity[3]));
            copyEntity.setIsCaptcha(WRWUtil.objToString(entity[4]));
            copyEntity.setNote(WRWUtil.objToString(entity[5]));
            copyEntity.setOrgId(WRWUtil.objToLong(entity[0]));
            copyEntity.setOriginalPrice(WRWUtil.objToLong(entity[6]));
            copyEntity.setPrice(WRWUtil.objToLong(entity[7]));
            copyEntity.setProductId(WRWUtil.objToLong(entity[2]));
            copyEntity.setProductName(WRWUtil.objToString(entity[8]));
            copyEntity.setRebackNote(WRWUtil.objToString(entity[9]));
            // 服务商名称
            for (SOrgEntity org : orgs) {
                if (WRWUtil.objToLong(entity[0]).equals(org.getOrgId())) {
                    copyEntity.setOrgName(org.getOrgName());
                }
            }

            // 品类名称
            copyEntity.setCateName(catesMap.get(WRWUtil.objToLong(entity[1])));
            copyEntity.setPsCate(getPSCate(entity));
            // 图片
            // 获取所有的照片 排序获取
            List<PProductImageEntity> listImageEntity = pImageDao.findByProductIdAndStatus(WRWUtil.objToLong(entity[2]),
                    ProductStatusEnum.USED.getCode());
            List<ActivityImageDto> listImage = new ArrayList<ActivityImageDto>();
            for (PProductImageEntity pProductImageEntity : listImageEntity) {
                ActivityImageDto iDto = ConverterService.convert(pProductImageEntity, new ActivityImageDto());
                listImage.add(iDto);
            }
            copyEntity.setListImages(listImage);
            copyList.add(copyEntity);
        }
        return copyList;
    }

    private String getPSCate(Object[] entity) {
        boolean isParent = false;
        String cateName = "";
        PCategoryEntity cateEntity = pcDao.findOne(WRWUtil.objToLong(entity[1]));
        List<PCategoryEntity> parentList = pcDao.findAllFathers();
        for (PCategoryEntity e : parentList) {
            if (e.getCateId().equals(WRWUtil.objToLong(entity[1]))) {
                isParent = true;
            }
        }
        if (isParent) {
            cateName = cateEntity.getCateName();
        } else {
            PCategoryEntity parentCate = pcDao.findOne(cateEntity.getParentId());
            cateName = parentCate.getCateName() + "-" + cateEntity.getCateName();
        }
        return cateName;
    }


}
