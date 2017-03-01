package com.hengtiansoft.business.product.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.product.dto.AttributeDto;
import com.hengtiansoft.business.product.dto.AttributeSearchDto;
import com.hengtiansoft.business.product.dto.AttributeValueDto;
import com.hengtiansoft.business.product.service.AttributeService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.enumeration.EErrorCode;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.PAttributeDao;
import com.hengtiansoft.wrw.dao.PAttributeValueDao;
import com.hengtiansoft.wrw.dao.PProductAttributeDao;
import com.hengtiansoft.wrw.dao.PTypeAttrDao;
import com.hengtiansoft.wrw.dao.PTypeDao;
import com.hengtiansoft.wrw.entity.PAttrValueEntity;
import com.hengtiansoft.wrw.entity.PAttributeEntity;
import com.hengtiansoft.wrw.entity.PTypeEntity;
import com.hengtiansoft.wrw.enums.StatusEnum;

@Service
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    private PAttributeDao        attributeDao;

    @Autowired
    private PAttributeValueDao   attributeValueDao;

    @Autowired
    private PTypeAttrDao         pTypeAttrDao;

    @Autowired
    private PTypeDao             typeDao;

    @Autowired
    private PProductAttributeDao pProductAttributeDao;

    @Override
    public void search(final AttributeSearchDto dto) {
        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(new Order(Direction.ASC, "sort"), new Order(
                Direction.DESC, "createDate")));
        Page<PAttributeEntity> page = attributeDao.findAll(new Specification<PAttributeEntity>() {
            @Override
            public Predicate toPredicate(Root<PAttributeEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                Predicate p1 = cb.equal(root.<String> get("status"), StatusEnum.NORMAL.getCode());
                predicates.add(p1);
                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }
        }, pageable);

        dto.setTotalRecord(page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setList(buildAttributeDtos(page.getContent()));

    }

    private List<AttributeDto> buildAttributeDtos(List<PAttributeEntity> list) {
        List<AttributeDto> listAttribute = new ArrayList<AttributeDto>();
        List<Long> listAttrIds = new ArrayList<Long>();
        for (PAttributeEntity entity : list) {
            listAttrIds.add(entity.getAttrId());
        }
        if (listAttrIds != null && listAttrIds.size() > 0) {
            List<PAttrValueEntity> elist = attributeValueDao.findByAttrIdsAndStatus(listAttrIds, StatusEnum.NORMAL.getCode());
            Map<Long, List<AttributeValueDto>> map = new HashMap<Long, List<AttributeValueDto>>();
            for (PAttrValueEntity pAttrValueEntity : elist) {
                List<AttributeValueDto> vlist = map.get(pAttrValueEntity.getAttrId());
                if (vlist != null && vlist.size() > 0) {
                    AttributeValueDto vdto = new AttributeValueDto();
                    vdto.setAttrValueId(pAttrValueEntity.getAttrValueId());
                    vdto.setValueInfo(pAttrValueEntity.getValueInfo());
                    vdto.setSort(pAttrValueEntity.getSort());
                    vlist.add(vdto);
                } else {
                    vlist = new ArrayList<AttributeValueDto>();
                    AttributeValueDto vdto = new AttributeValueDto();
                    vdto.setAttrValueId(pAttrValueEntity.getAttrValueId());
                    vdto.setValueInfo(pAttrValueEntity.getValueInfo());
                    vdto.setSort(pAttrValueEntity.getSort());
                    vlist.add(vdto);
                    map.put(pAttrValueEntity.getAttrId(), vlist);
                }
            }
            for (PAttributeEntity entity : list) {
                AttributeDto dto = new AttributeDto();
                dto.setAttrId(entity.getAttrId());
                dto.setAttrName(entity.getAttrName());
                dto.setStatus(entity.getStatus());
                dto.setSort(entity.getSort());
                dto.setSearchable(entity.getSearchable());
                dto.setList(map.get(entity.getAttrId()));
                listAttribute.add(dto);
            }
        }
        return listAttribute;
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> save(AttributeDto dto) {
        PAttributeEntity entity = new PAttributeEntity();
        if (StringUtil.isEmpty(dto.getAttrName())) {
            throw new WRWException(EErrorCode.PARA_ATTR_NAME_IS_NULL);
        } else {
            // 验证属性名称是否存在
            int countName = attributeDao.findByNameAndStatus(dto.getAttrName(), StatusEnum.NORMAL.getCode());
            if (countName > 0) {
                throw new WRWException(EErrorCode.ENTITY_ATTR_IS_EXIST);
            }
            entity.setAttrName(dto.getAttrName());
        }
        entity.setAttrType(dto.getAttrType());
        entity.setStatus(StatusEnum.NORMAL.getCode());
        entity.setCreateId(Long.valueOf(AuthorityContext.getCurrentUser().getUserId()));
        entity.setCreateDate(new Date());
        entity.setSearchable(dto.getSearchable());// 默认值为0,意义为前台设置为不可查询
        entity.setSort(dto.getSort());
        List<AttributeValueDto> list = dto.getList();
        if (list == null || list.size() == 0) {
            throw new WRWException(EErrorCode.PARA_ATTR_VALUE_IS_NULL);
        }
        List<PAttrValueEntity> entitys = new ArrayList<PAttrValueEntity>();
        for (int i = 0; i < list.size() - 1; i++) {
            String valueInfo = list.get(i).getValueInfo();
            for (int j = list.size() - 1; j > i; j--) {
                if (WRWUtil.isEmpty(valueInfo) && valueInfo.equals(list.get(j).getValueInfo())) {
                    throw new WRWException(EErrorCode.ENTITY_ATTR_ATTR_IS_EXIST);
                }
            }
        }
        attributeDao.save(entity);
        for (AttributeValueDto valueDto : list) {
            PAttrValueEntity ventity = new PAttrValueEntity();
            ventity.setAttrId(entity.getAttrId());
            ventity.setSort(valueDto.getSort() == null ? 0 : valueDto.getSort());
            ventity.setValueInfo(valueDto.getValueInfo());
            ventity.setStatus(StatusEnum.NORMAL.getCode());
            ventity.setCreateId(AuthorityContext.getCurrentUser().getUserId());
            ventity.setCreateDate(new Date());
            entitys.add(ventity);
        }
        attributeValueDao.save(entitys);
        return ResultDtoFactory.toAck(EErrorCode.SUCCESS);

    }

    @Override
    public AttributeDto findById(Long id) {
        AttributeDto dto = new AttributeDto();
        PAttributeEntity entity = attributeDao.findOne(id);
        if (entity == null) {
            return null;
        }
        dto.setAttrId(id);
        dto.setAttrName(entity.getAttrName());
        dto.setStatus(entity.getStatus());
        dto.setAttrType(entity.getAttrType());
        dto.setSort(entity.getSort());
        dto.setSearchable(entity.getSearchable());
        List<PAttrValueEntity> entitys = attributeValueDao.findByAttrIdAndStatus(id, StatusEnum.NORMAL.getCode());
        List<AttributeValueDto> listDtos = new ArrayList<AttributeValueDto>();
        for (PAttrValueEntity pAttrValueEntity : entitys) {
            listDtos.add(ConverterService.convert(pAttrValueEntity, new AttributeValueDto()));
        }
        dto.setList(listDtos);
        return dto;
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> update(AttributeDto dto) {

        PAttributeEntity entity = attributeDao.findOne(dto.getAttrId());
        if (entity == null) {
            throw new WRWException(EErrorCode.ENTITY_ATTR_NOT_EXIST);
        }
        if (StringUtil.isEmpty(dto.getAttrName())) {
            throw new WRWException(EErrorCode.PARA_ATTR_NAME_IS_NULL);
        } else {
            if (!dto.getAttrName().equals(entity.getAttrName())) {
                // 验证属性名称是否存在
                int countName = attributeDao.findByNameAndStatus(dto.getAttrName(), StatusEnum.NORMAL.getCode());
                if (countName > 0) {
                    throw new WRWException(EErrorCode.ENTITY_ATTR_IS_EXIST);
                }
                entity.setAttrName(dto.getAttrName());
            }

        }
        entity.setAttrName(dto.getAttrName());
        if (!StringUtil.isEmpty(dto.getAttrType())) {
            entity.setAttrType(dto.getAttrType());
        }
        if (!StringUtil.isEmpty(dto.getAttrType())) {
            entity.setAttrType(dto.getAttrType());
        }
        entity.setModifyDate(new Date());
        entity.setModifyId(Long.valueOf(AuthorityContext.getCurrentUser().getUserId()));
        entity.setSort(dto.getSort());
        entity.setSearchable(dto.getSearchable());
        List<AttributeValueDto> listDtos = dto.getList();
        if (listDtos == null || listDtos.size() == 0) {
            throw new WRWException(EErrorCode.PARA_ATTR_VALUE_IS_NULL);
        }
        for (int i = 0; i < listDtos.size() - 1; i++) {
            String valueInfo = listDtos.get(i).getValueInfo();
            for (int j = listDtos.size() - 1; j > i; j--) {
                if (WRWUtil.isEmpty(valueInfo) && valueInfo.equals(listDtos.get(j).getValueInfo())) {
                    throw new WRWException(EErrorCode.ENTITY_ATTR_ATTR_IS_EXIST);
                }
            }
        }
        attributeDao.save(entity);

        List<PAttrValueEntity> entitys = new ArrayList<PAttrValueEntity>();

        List<Long> newValueId = new ArrayList<Long>();

        List<Long> attrValueIds = new ArrayList<Long>();
        for (AttributeValueDto attributeValueDto : listDtos) {
            newValueId.add(attributeValueDto.getAttrValueId());
            PAttrValueEntity ventity = new PAttrValueEntity();
            ventity.setAttrValueId(attributeValueDto.getAttrValueId());
            ventity.setAttrId(dto.getAttrId());
            ventity.setSort(attributeValueDto.getSort() == null ? 0 : attributeValueDto.getSort());
            ventity.setValueInfo(attributeValueDto.getValueInfo());
            ventity.setStatus(StatusEnum.NORMAL.getCode());
            ventity.setModifyId(Long.valueOf(AuthorityContext.getCurrentUser().getUserId()));
            ventity.setModifyDate(new Date());
            entitys.add(ventity);
            attrValueIds.add(attributeValueDto.getAttrValueId());
        }
        // 查找数据库里面已经有的属性ID。
        List<Long> oldValueId = attributeValueDao.findIdByAttrId(dto.getAttrId(),StatusEnum.NORMAL.getCode());
        // 删除已经前台传过来的ID
        oldValueId.removeAll(newValueId);
        // 做属性的判断，如果已经被使用，就不能被删除
        if (oldValueId.size() > 0) {
            // 判断是否被应用
            for (Long valueId : oldValueId) {
                if (pProductAttributeDao.countByAttrValueIdAndStatus(valueId,StatusEnum.NORMAL.getCode()) > 0) {
                    throw new WRWException(EErrorCode.BIZ_ATTRVALUE_HAS_USING);
                }
            }
            // 先将原来的值进行status变成0
            attributeValueDao.updateByValueId(oldValueId, StatusEnum.DELETE.getCode());
        }

        // 进行新值的插入
        attributeValueDao.save(entitys);

        return ResultDtoFactory.toAck(EErrorCode.SUCCESS);
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> delete(Long id) {
        PAttributeEntity entity = attributeDao.findOne(id);
        if (entity == null) {
            throw new WRWException(EErrorCode.ENTITY_ATTR_NOT_EXIST);
        }
        // 判断类型P_TYPE_ATTR中，是否有这个值
        List<Long> list = pTypeAttrDao.selectByAttrId(id);
        if (list != null && list.size() > 0) {
            // 将这些类型ID，获取类型名称
            List<PTypeEntity> listEntity = typeDao.findAll(list);
            String typeName = "";
            for (PTypeEntity pTypeEntity : listEntity) {
                typeName = typeName + pTypeEntity.getTypeName() + "、";
            }
            typeName = typeName.substring(0, typeName.length() - 1);
            throw new WRWException("该属性已被(" + typeName + ")等类型使用,无法删除!");
        }
        entity.setStatus(StatusEnum.DELETE.getCode());
        attributeDao.save(entity);
        attributeValueDao.updateById(id, StatusEnum.DELETE.getCode());
        return ResultDtoFactory.toAck(EErrorCode.SUCCESS);
    }

    @Override
    public List<PAttributeEntity> findAllAttributes() {
        return attributeDao.findAllByStatus(StatusEnum.NORMAL.getCode());
    }

}
