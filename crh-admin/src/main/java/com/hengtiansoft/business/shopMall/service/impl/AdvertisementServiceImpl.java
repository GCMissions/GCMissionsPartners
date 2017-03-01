package com.hengtiansoft.business.shopMall.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.shopMall.dto.AdvertisementDto;
import com.hengtiansoft.business.shopMall.dto.AdvertisementPageDto;
import com.hengtiansoft.business.shopMall.dto.AdvertisementPositionDto;
import com.hengtiansoft.business.shopMall.service.AdvertisementService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.enumeration.AdPositionEnum;
import com.hengtiansoft.common.enumeration.EErrorCode;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.BizUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.common.xmemcached.constant.CacheType;
import com.hengtiansoft.wrw.dao.PProductDao;
import com.hengtiansoft.wrw.dao.SAdvertisementDao;
import com.hengtiansoft.wrw.entity.PProductEntity;
import com.hengtiansoft.wrw.entity.SAdEntity;
import com.hengtiansoft.wrw.enums.AdTypeEnum;
import com.hengtiansoft.wrw.enums.AdUrlFlagEnum;
import com.hengtiansoft.wrw.enums.StatusEnum;

/**
 * @author jiekaihu
 */
@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    @Autowired
    private PProductDao       productDao;

    @Autowired
    private SAdvertisementDao adverDao;

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> saveAdvertisement(AdvertisementDto dto) {
        if (StringUtils.isNotBlank(dto.getTitle())) {
            SAdEntity entity = adverDao.findByTitle(dto.getTitle());
            if (entity != null && !entity.getAdId().equals(dto.getAdId())) {
                throw new WRWException(EErrorCode.ENTITY_AD_IS_EXIST);
            }
        }
        if (dto.getSort() == null) {
            dto.setSort(0);
        }
        SAdEntity entity = ConverterService.convert(dto, SAdEntity.class);
        entity.setStatus(StatusEnum.NORMAL.getCode());
        entity.setCreateId(Long.valueOf(AuthorityContext.getCurrentUser().getUserId()));
        entity.setCreateDate(new Date());
        entity.setModifyDate(new Date());
        entity.setModifyId(Long.valueOf(AuthorityContext.getCurrentUser().getUserId()));
        adverDao.save(entity);
        return ResultDtoFactory.toAck("保存成功");
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    @Caching(  
            evict = {  
                    @CacheEvict(value = CacheType.DEFAULT, key = "'_appAds'",condition="#dto.local eq '3' or #dto.local eq '4'"),  
                    @CacheEvict(value = CacheType.DEFAULT, key = "'_appActivity'",condition="#dto.local eq '5'")  
            }  
    ) 
    public ResultDto<String> editAdvertisement(AdvertisementDto dto) {

        SAdEntity entity = adverDao.findOne(dto.getAdId());

        entity.setTitle(dto.getTitle());

        if (WRWUtil.isNotEmpty(dto.getUrlFlag())) {
            entity.setUrlFlag(dto.getUrlFlag());
        }

        if (AdUrlFlagEnum.INNER.getCode().equals(dto.getUrlFlag())) {
            String productCode = dto.getUrl();
            if (StringUtils.isNotBlank(productCode)) {
                PProductEntity productEntity = productDao.findByProductCodeNew(productCode);
                if (productEntity != null && productEntity.getProductId() != null) {
                    // 验证商品条码是否存在
                    entity.setUrl(productCode);
                } else {
                    throw new WRWException(EErrorCode.ENTITY_AD_PRODUCTCODE_NOTEXIST);
                }
            } else {
                entity.setUrl("");
            }

        } else {
            entity.setUrl(dto.getUrl());
        }

        if (!WRWUtil.isEmpty(dto.getImage())) {
            entity.setImage(dto.getImage());
        } else {
            // entity.setStatus(StatusEnum.DELETE.getCode());
            entity.setImage(null);
        }
        entity.setModifyDate(new Date());
        entity.setModifyId(Long.valueOf(AuthorityContext.getCurrentUser().getUserId()));
        adverDao.save(entity);
        return ResultDtoFactory.toAck("修改成功");
    }

    @Override
    public void getAdvertisementList(final AdvertisementPageDto dto) {
        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.ASC, "local", "sort", "createDate"));
        Page<SAdEntity> page = adverDao.findAll(new Specification<SAdEntity>() {

            @Override
            public Predicate toPredicate(Root<SAdEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                Predicate p0 = cb.equal(root.<String> get("status"), StatusEnum.NORMAL.getCode());
                predicates.add(p0);
                if (!WRWUtil.isEmpty(dto.getTitle())) {
                    Predicate p1 = cb.like(root.<String> get("title"), BizUtil.filterString(dto.getTitle()));
                    predicates.add(p1);
                }
                if (!WRWUtil.isEmpty(dto.getLocal())) {
                    Predicate p4 = cb.equal(root.<String> get("local"), dto.getLocal());
                    // 若广告位选择为全部，则不纳入筛选条件
                    if (!dto.getLocal().equals(AdPositionEnum.ALL.getPosition())) {
                        predicates.add(p4);
                    }
                }

                if (!WRWUtil.isEmpty(dto.getType())) {
                    List<String> localPositions = new ArrayList<String>();
                    if (AdTypeEnum.web.getCode().equals(dto.getType())) {
                        localPositions.add(AdPositionEnum.B2C_TOP.getPosition());
                        localPositions.add(AdPositionEnum.B2C_ROLL.getPosition());
                        localPositions.add(AdPositionEnum.B2C_OTHER.getPosition());
                        predicates.add(root.<String> get("local").in(localPositions));
                    } else if (AdTypeEnum.app.getCode().equals(dto.getType())) {
                        localPositions.add(AdPositionEnum.APP_ROLL.getPosition());
                        localPositions.add(AdPositionEnum.APP_OTHER.getPosition());
                        predicates.add(root.<String> get("local").in(localPositions));
                    } else if (AdTypeEnum.appActivity.getCode().equals(dto.getType())) {
                        localPositions.add(AdPositionEnum.APP_ACTIVITY.getPosition());
                        predicates.add(root.<String> get("local").in(localPositions));
                    }
                }

                if (dto.getStartDate() != null && dto.getEndDate() != null) {
                    Predicate p2 = cb.lessThan(root.<Date> get("beginDate"), dto.getEndDate());
                    Predicate p3 = cb.greaterThan(root.<Date> get("endDate"), dto.getStartDate());
                    predicates.add(p2);
                    predicates.add(p3);
                }
                Predicate predicate2 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate2);
                return query.getRestriction();
            }
        }, pageable);
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalRecord(page.getTotalElements());
        dto.setList(buildAdvertisementDto(page.getContent()));
    }

    private List<AdvertisementDto> buildAdvertisementDto(List<SAdEntity> entities) {
        List<AdvertisementDto> dtos = new ArrayList<AdvertisementDto>();
        for (SAdEntity one : entities) {
            AdvertisementDto dto = ConverterService.convert(one, AdvertisementDto.class);
            dto.setLocalName(getLocalName(dto.getLocal()));// 设置广告位为中文意义
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public AdvertisementDto getAdvertisement(Long adId) {
        SAdEntity entity = adverDao.findOne(adId);
        if (entity != null && entity.getStatus().equals(StatusEnum.NORMAL.getCode())) {
            AdvertisementDto dto = ConverterService.convert(entity, AdvertisementDto.class);
            dto.setLocalName(getLocalName(dto.getLocal()));
            return dto;
        }
        return null;
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> deleteById(Long[] adIds) {
        for (Long one : adIds) {
            SAdEntity entity = adverDao.findOne(one);
            if (entity == null) {
                throw new WRWException(EErrorCode.ENTITY_AD_NOT_EXIST);
            }
            entity.setStatus(StatusEnum.DELETE.getCode());
            adverDao.save(entity);
        }
        return ResultDtoFactory.toAck("删除成功!");
    }

    @Override
    public List<AdvertisementPositionDto> getPositionList() {

        List<AdvertisementPositionDto> positionList = new ArrayList<AdvertisementPositionDto>();

        AdvertisementPositionDto dto0 = new AdvertisementPositionDto();
        dto0.setName(AdPositionEnum.ALL.getName());
        dto0.setPosition(AdPositionEnum.ALL.getPosition());
        dto0.setMaxImageCount(AdPositionEnum.ALL.getMaxImageCount());
        positionList.add(dto0);

        AdvertisementPositionDto dto1 = new AdvertisementPositionDto();
        dto1.setName(AdPositionEnum.APP_OTHER.getName());
        dto1.setPosition(AdPositionEnum.APP_OTHER.getPosition());
        dto1.setMaxImageCount(AdPositionEnum.APP_OTHER.getMaxImageCount());
        positionList.add(dto1);

        AdvertisementPositionDto dto2 = new AdvertisementPositionDto();
        dto2.setName(AdPositionEnum.APP_ROLL.getName());
        dto2.setPosition(AdPositionEnum.APP_ROLL.getPosition());
        dto2.setMaxImageCount(AdPositionEnum.APP_ROLL.getMaxImageCount());
        positionList.add(dto2);

        AdvertisementPositionDto dto3 = new AdvertisementPositionDto();
        dto3.setName(AdPositionEnum.B2C_OTHER.getName());
        dto3.setPosition(AdPositionEnum.B2C_OTHER.getPosition());
        dto3.setMaxImageCount(AdPositionEnum.B2C_OTHER.getMaxImageCount());
        positionList.add(dto3);

        AdvertisementPositionDto dto4 = new AdvertisementPositionDto();
        dto4.setName(AdPositionEnum.B2C_ROLL.getName());
        dto4.setPosition(AdPositionEnum.B2C_ROLL.getPosition());
        dto4.setMaxImageCount(AdPositionEnum.B2C_ROLL.getMaxImageCount());
        positionList.add(dto4);

        AdvertisementPositionDto dto5 = new AdvertisementPositionDto();
        dto5.setName(AdPositionEnum.B2C_TOP.getName());
        dto5.setPosition(AdPositionEnum.B2C_TOP.getPosition());
        dto5.setMaxImageCount(AdPositionEnum.B2C_TOP.getMaxImageCount());
        positionList.add(dto5);

        AdvertisementPositionDto dto6 = new AdvertisementPositionDto();
        dto6.setName(AdPositionEnum.APP_ACTIVITY.getName());
        dto6.setPosition(AdPositionEnum.APP_ACTIVITY.getPosition());
        dto6.setMaxImageCount(AdPositionEnum.APP_ACTIVITY.getMaxImageCount());
        positionList.add(dto6);
        return positionList;
    }

    /**
     * 根据广告位序号，获取广告位中文释义
     * 
     * @param position
     * @return
     */
    public String getLocalName(String position) {
        List<AdvertisementPositionDto> list = this.getPositionList();
        for (AdvertisementPositionDto one : list) {
            if (one.getPosition().equals(position)) {
                return one.getName();
            }
        }
        return null;
    }
}
