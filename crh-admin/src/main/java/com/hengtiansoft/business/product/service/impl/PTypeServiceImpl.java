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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.product.dto.PTypeDto;
import com.hengtiansoft.business.product.dto.PTypeSaveDto;
import com.hengtiansoft.business.product.dto.PTypeSearchDto;
import com.hengtiansoft.business.product.service.PTypeService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.PAttributeDao;
import com.hengtiansoft.wrw.dao.PBrandDao;
import com.hengtiansoft.wrw.dao.PCategoryDao;
import com.hengtiansoft.wrw.dao.PTypeAttrDao;
import com.hengtiansoft.wrw.dao.PTypeBrandDao;
import com.hengtiansoft.wrw.dao.PTypeDao;
import com.hengtiansoft.wrw.entity.PCategoryEntity;
import com.hengtiansoft.wrw.entity.PTypeAttrEntity;
import com.hengtiansoft.wrw.entity.PTypeAttrPK;
import com.hengtiansoft.wrw.entity.PTypeBrandEntity;
import com.hengtiansoft.wrw.entity.PTypeBrandPK;
import com.hengtiansoft.wrw.entity.PTypeEntity;
import com.hengtiansoft.wrw.enums.StatusEnum;

@Service
public class PTypeServiceImpl implements PTypeService {

    @Autowired
    private PBrandDao     pBrandDao;

    @Autowired
    private PAttributeDao pAttributeDao;

    @Autowired
    private PTypeDao      pTypeDao;

    @Autowired
    private PTypeAttrDao  pTypeAttrDao;

    @Autowired
    private PTypeBrandDao pTypeBrandDao;

    @Autowired
    private PCategoryDao  pcateDao;

    @Override
    public void search(final PTypeSearchDto dto) {
        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(new Order(Direction.ASC, "sort"), new Order(
                Direction.DESC, "createDate")));
        Page<PTypeEntity> page = pTypeDao.findAll(new Specification<PTypeEntity>() {
            @Override
            public Predicate toPredicate(Root<PTypeEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                /*
                 * if (!WLYUtil.isEmpty(dto.getMsg())) {
                 * }
                 */
                Predicate p1 = cb.equal(root.<String> get("status"), StatusEnum.NORMAL.getCode());
                predicates.add(p1);
                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }
        }, pageable);

        dto.setTotalRecord(page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setList(buildTypeDtos(page.getContent()));

    }

    private List<PTypeDto> buildTypeDtos(List<PTypeEntity> content) {
        List<PTypeDto> list = new ArrayList<PTypeDto>();
        Map<Long, PTypeDto> map = new HashMap<Long, PTypeDto>();
        List<Long> listLong = new ArrayList<Long>();
        for (PTypeEntity entity : content) {
            PTypeDto dto = new PTypeDto();
            dto.setSort(entity.getSort());
            dto.setTypeId(entity.getTypeId());
            dto.setTypeName(entity.getTypeName());
            map.put(entity.getTypeId(), dto);
            listLong.add(entity.getTypeId());
            list.add(dto);
        }

        for (Long id : listLong) {
            if (map.containsKey(id)) {
                PTypeDto dto = map.get(id);
                List<Long> attrIds = pTypeAttrDao.selectByPK(id);
                List<Long> brandIds = pTypeBrandDao.selectByPK(id);
                dto.setAttrIds(attrIds);
                dto.setBrandIds(brandIds);
                if (attrIds != null && attrIds.size() > 0) {
                    dto.setAttrNames(pAttributeDao.findByIds(attrIds));
                }
                if (brandIds != null && brandIds.size() > 0) {
                    dto.setBrandNames(pBrandDao.findByIds(brandIds));
                }
            }
        }

        return list;
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> save(PTypeSaveDto dto) {
        PTypeEntity entity = new PTypeEntity();
        if (WRWUtil.isEmpty(dto.getTypeName())) {
            throw new WRWException("类型名称不能为空,请重新输入!");
        } else {
            int countTypeName = pTypeDao.findByTypeNameAndStatus(dto.getTypeName(), StatusEnum.NORMAL.getCode());
            if (countTypeName > 0) {
                throw new WRWException("类型名称已经存在,请重新输入!");
            }
            entity.setTypeName(dto.getTypeName());
        }
        entity.setStatus(StatusEnum.NORMAL.getCode());
        entity.setSort(dto.getSort());
        entity.setCreateDate(new Date());
        entity.setCreateId(AuthorityContext.getCurrentUser().getUserId());
        entity.setRemark(dto.getRemark());
        pTypeDao.save(entity);
        // 判断属性IDs是否为空
        if (dto.getAttrIds() != null && dto.getAttrIds().size() > 0) {
            List<PTypeAttrEntity> listtaEntity = new ArrayList<PTypeAttrEntity>();
            for (Long attrId : dto.getAttrIds()) {
                PTypeAttrEntity taEntity = new PTypeAttrEntity();
                PTypeAttrPK pk = new PTypeAttrPK(entity.getTypeId(), attrId);
                taEntity.setId(pk);
                listtaEntity.add(taEntity);
            }
            pTypeAttrDao.save(listtaEntity);

        }
        // 判断品牌IDs是否为空
        if (dto.getBrandIds() != null && dto.getBrandIds().size() > 0) {
            List<PTypeBrandEntity> listtbEntity = new ArrayList<PTypeBrandEntity>();
            for (Long brandId : dto.getBrandIds()) {
                PTypeBrandEntity tbEntity = new PTypeBrandEntity();
                PTypeBrandPK pk = new PTypeBrandPK(entity.getTypeId(), brandId);
                tbEntity.setId(pk);
                listtbEntity.add(tbEntity);
            }
            pTypeBrandDao.save(listtbEntity);
        }

        return ResultDtoFactory.toAck("新增成功!");
    }

    @Override
    public PTypeSaveDto findById(Long id) {
        PTypeEntity entity = pTypeDao.findOne(id);
        if (entity == null) {
            return null;
        }
        PTypeSaveDto dto = new PTypeSaveDto();
        dto.setTypeId(entity.getTypeId());
        dto.setTypeName(entity.getTypeName());
        dto.setSort(entity.getSort());
        dto.setRemark(entity.getRemark());
        dto.setAttrIds(pTypeAttrDao.selectByPK(id));
        dto.setBrandIds(pTypeBrandDao.selectByPK(id));
        return dto;
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> update(PTypeSaveDto dto) {
        PTypeEntity entity = pTypeDao.findOne(dto.getTypeId());
        if (WRWUtil.isEmpty(dto.getTypeName())) {
            throw new WRWException("类型名称不能为空,请检查后重新输入!");
        } else {
            if (!dto.getTypeName().equals(entity.getTypeName())) {
                int countTypeName = pTypeDao.findByTypeNameAndStatus(dto.getTypeName(), StatusEnum.NORMAL.getCode());
                if (countTypeName > 0) {
                    throw new WRWException("类型名称已经存在,请重新输入!");
                }
                entity.setTypeName(dto.getTypeName());
            }
        }
        if (!WRWUtil.isEmpty(dto.getRemark())) {
            entity.setRemark(dto.getRemark());
        }
        if (!WRWUtil.isEmpty(String.valueOf(dto.getSort()))) {
            entity.setSort(dto.getSort());
        }
        entity.setModifyDate(new Date());
        entity.setModifyId(AuthorityContext.getCurrentUser().getUserId());
        pTypeDao.save(entity);
        // 先对属性以及品牌删除掉
        pTypeAttrDao.deleteByPK(dto.getTypeId());
        pTypeBrandDao.deleteByPK(dto.getTypeId());
        // 判断属性IDs是否为空
        if (dto.getAttrIds() != null && dto.getAttrIds().size() > 0) {
            List<PTypeAttrEntity> listtaEntity = new ArrayList<PTypeAttrEntity>();
            for (Long attrId : dto.getAttrIds()) {
                PTypeAttrEntity taEntity = new PTypeAttrEntity();
                PTypeAttrPK pk = new PTypeAttrPK(entity.getTypeId(), attrId);
                taEntity.setId(pk);
                listtaEntity.add(taEntity);
            }
            pTypeAttrDao.save(listtaEntity);

        }
        // 判断品牌IDs是否为空
        if (dto.getBrandIds() != null && dto.getBrandIds().size() > 0) {
            List<PTypeBrandEntity> listtbEntity = new ArrayList<PTypeBrandEntity>();
            for (Long brandId : dto.getBrandIds()) {
                PTypeBrandEntity tbEntity = new PTypeBrandEntity();
                PTypeBrandPK pk = new PTypeBrandPK(entity.getTypeId(), brandId);
                tbEntity.setId(pk);
                listtbEntity.add(tbEntity);
            }
            pTypeBrandDao.save(listtbEntity);
        }

        return ResultDtoFactory.toAck("更新成功!");
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> delete(Long id) {
        PTypeEntity entity = pTypeDao.findOne(id);
        if (entity == null) {
            throw new WRWException("数据错误,请检查传值是否正确!");
        }
        // 判断该类型是否在已存在的分类中存在
        List<PCategoryEntity> list = pcateDao.findCateByTypeIdAndStatus(id, StatusEnum.NORMAL.getCode());
        if (list != null && list.size() > 0) {
            String cateName = "";
            for (PCategoryEntity cateEntity : list) {
                cateName = cateName + cateEntity.getCateName() + "、";
            }
            // 将所有的分类名称组合起来，放在提示信息中
            cateName = cateName.substring(0, cateName.length() - 1);
            throw new WRWException("该类型在(" + cateName + ")等分类中存在,无法删除");
        }
        entity.setModifyId(AuthorityContext.getCurrentUser().getUserId());
        entity.setModifyDate(new Date());
        entity.setStatus(StatusEnum.DELETE.getCode());
        pTypeDao.save(entity);
        pTypeAttrDao.deleteByPK(id);
        pTypeBrandDao.deleteByPK(id);
        return ResultDtoFactory.toAck("删除成功!");
    }

    @Override
    public List<PTypeEntity> findAll() {
        List<PTypeEntity> list1 = pTypeDao.findAll();
        List<PTypeEntity> list2 = new ArrayList<PTypeEntity>();
        for (PTypeEntity one : list1) {
            if (one.getStatus().equals(StatusEnum.NORMAL.getCode())) {
                list2.add(one);
            }
        }
        return list2;
    }
}
