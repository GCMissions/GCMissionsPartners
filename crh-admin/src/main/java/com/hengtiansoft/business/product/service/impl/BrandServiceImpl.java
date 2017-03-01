package com.hengtiansoft.business.product.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.product.dto.ProductBrandDto;
import com.hengtiansoft.business.product.dto.ProductBrandPageDto;
import com.hengtiansoft.business.product.service.BrandService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.enumeration.EErrorCode;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.BizUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.PBrandDao;
import com.hengtiansoft.wrw.dao.PTypeBrandDao;
import com.hengtiansoft.wrw.dao.PTypeDao;
import com.hengtiansoft.wrw.entity.PBrandEntity;
import com.hengtiansoft.wrw.enums.StatusEnum;

/**
 * @author jiekaihu
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private PBrandDao     brandDao;

    @Autowired
    private PTypeBrandDao typeBrandDao;

    @Autowired
    private PTypeDao      typeDao;

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> saveBrand(ProductBrandDto dto) {
        if (StringUtils.isNotBlank(dto.getBrandName())) {
            PBrandEntity entity = brandDao.findByBrandName(dto.getBrandName());
            if (entity != null && !entity.getBrandId().equals(dto.getBrandId()) && entity.getStatus().equals(StatusEnum.NORMAL.getCode())) {
                throw new WRWException(EErrorCode.ENTITY_BARND_IS_EXIST);
            }
        }
        if (dto.getSort() == null) {
            dto.setSort(0L);
        }
        PBrandEntity entity = ConverterService.convert(dto, PBrandEntity.class);
        entity.setStatus(StatusEnum.NORMAL.getCode());
        entity.setCreateId(Long.valueOf(AuthorityContext.getCurrentUser().getUserId()));
        brandDao.save(entity);
        return ResultDtoFactory.toAck("保存成功");
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> editBrand(ProductBrandDto dto) {
        PBrandEntity entity = brandDao.findOne(dto.getBrandId());

        if (!WRWUtil.isEmpty(dto.getBrandName())) {
            PBrandEntity entity2 = brandDao.findByBrandName(dto.getBrandName());
            if (entity2 != null && !entity2.getBrandId().equals(dto.getBrandId()) && entity.getStatus().equals(StatusEnum.NORMAL.getCode())) {
                throw new WRWException(EErrorCode.ENTITY_BARND_IS_EXIST);
            }
            entity.setBrandName(dto.getBrandName());
        }
        if (dto.getSort() != null) {
            entity.setSort(dto.getSort());
        } else {
            entity.setSort(0L);
        }
        if (WRWUtil.isEmpty(entity.getStatus())) {
            entity.setStatus(StatusEnum.NORMAL.getCode());
        }
        if (!WRWUtil.isEmpty(dto.getLogo())) {
            entity.setLogo(dto.getLogo());
        }
        if (!WRWUtil.isEmpty(dto.getDescription())) {
            entity.setDescription(dto.getDescription());
        }
        entity.setModifyDate(new Date());
        entity.setModifyId(Long.valueOf(AuthorityContext.getCurrentUser().getUserId()));
        brandDao.save(entity);
        return ResultDtoFactory.toAck("修改成功");
    }

    @Override
    public void getBrandList(final ProductBrandPageDto dto) {
        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.ASC, "sort").and(new Sort(Direction.DESC, "createDate")));
        Page<PBrandEntity> page = brandDao.findAll(new Specification<PBrandEntity>() {

            @Override
            public Predicate toPredicate(Root<PBrandEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                Predicate p1 = cb.equal(root.<String> get("status"), StatusEnum.NORMAL.getCode());
                predicates.add(p1);
                if (StringUtils.isNotBlank(dto.getKeyWord())) {
                    Predicate p2 = cb.like(root.<String> get("brandName"), BizUtil.filterString(dto.getKeyWord()));
                    Predicate p3 = cb.like(root.<String> get("keyWord"), BizUtil.filterString(dto.getKeyWord()));
                    Predicate predicate1 = cb.and(cb.or(p2, p3));
                    predicates.add(predicate1);
                }
                Predicate predicate2 = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate2);
                return query.getRestriction();
            }
        }, pageable);

        dto.setTotalPages(page.getTotalPages());
        dto.setTotalRecord(page.getTotalElements());
        dto.setList(buildProdcutBrandDto(page.getContent()));
    }

    private List<ProductBrandDto> buildProdcutBrandDto(List<PBrandEntity> entities) {
        List<ProductBrandDto> dtos = new ArrayList<ProductBrandDto>();
        for (PBrandEntity pBrandEntity : entities) {
            dtos.add(ConverterService.convert(pBrandEntity, ProductBrandDto.class));
        }
        return dtos;
    }

    @Override
    public ProductBrandDto findOne(Long brandId) {
        PBrandEntity entity = brandDao.findOne(brandId);
        if (entity != null && entity.getStatus().equals(StatusEnum.NORMAL.getCode())) {
            return ConverterService.convert(entity, ProductBrandDto.class);
        }
        return null;
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<StringBuilder> deleteById(Long brandId) {
        PBrandEntity entity = brandDao.findOne(brandId);
        // 判断类型P_TYPE_BRAND中，是否有这个值
        List<Long> typeIdList = typeBrandDao.selectByBrandId(brandId);
        if (typeIdList.size() > 0) {
            StringBuilder typeNames = new StringBuilder();
            typeNames.append(typeDao.findOne(typeIdList.get(0)).getTypeName());
            for (int i = 1; i < typeIdList.size(); i++) {
                String name = typeDao.findOne(typeIdList.get(i)).getTypeName();
                typeNames.append("、");
                typeNames.append(name);
            }
            return ResultDtoFactory.toNack("品牌已被类型：" + typeNames + " 使用,无法删除!");
        }
        if (entity == null) {
            throw new WRWException(EErrorCode.ENTITY_BARND_NOT_EXIST);
        }
        entity.setStatus(StatusEnum.DELETE.getCode());
        brandDao.save(entity);
        return ResultDtoFactory.toAck("删除成功!");
    }

    @Override
    public List<ProductBrandDto> findAll() {
        return buildProdcutBrandDto(brandDao.findAll());
    }

    @Override
    public List<PBrandEntity> findAllBrands() {

        return brandDao.findAllByStatus(StatusEnum.NORMAL.getCode());
    }

}
