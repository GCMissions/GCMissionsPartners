package com.hengtiansoft.business.product.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.product.dto.PGoodsDto;
import com.hengtiansoft.business.product.dto.PGoodsSearchDto;
import com.hengtiansoft.business.product.dto.ProductDto;
import com.hengtiansoft.business.product.dto.ProductSearchDto;
import com.hengtiansoft.business.product.service.GoodsService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.PGoodsDao;
import com.hengtiansoft.wrw.dao.PProductDao;
import com.hengtiansoft.wrw.entity.PGoodsEntity;
import com.hengtiansoft.wrw.entity.PProductEntity;
import com.hengtiansoft.wrw.enums.StatusEnum;

/**
 * Class Name: GoodsServiceImpl
 * Description: 单瓶商品service实现类
 * 
 * @author zhishenghong
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private PGoodsDao   goodsDao;

    @Autowired
    private PProductDao productDao;

    @Override
    public ResultDto<String> saveGoods(PGoodsDto dto) {
        dto.setGoodsId(0L);
        PGoodsEntity entity = new PGoodsEntity();
        if (WRWUtil.isEmpty(dto.getGoodCode())) {
            throw new WRWException("单瓶商品编码不能为空！");
        } else {
            int goodsCount = goodsDao.findCountByGoodCode(dto.getGoodCode(), dto.getGoodsId());
            if (goodsCount > 0) {
                throw new WRWException("单瓶商品编码已存在，请重新输入！");
            } else {
                entity.setGoodCode(dto.getGoodCode());
            }
        }
        if (WRWUtil.isEmpty(dto.getGoodName())) {
            throw new WRWException("单瓶商品名称不能为空！");
        } else {
            int goodsCount = goodsDao.findCountByGoodsName(dto.getGoodName(), dto.getGoodsId());
            if (goodsCount > 0) {
                throw new WRWException("单瓶商品名称已存在，请重新输入！");
            } else {
                entity.setGoodName(dto.getGoodName());
            }
        }
        if (dto.getPrice() == null || dto.getPrice().equals(0L)) {
            throw new WRWException("价格不能为空！");
        }
        if (WRWUtil.isEmpty(dto.getSpecs())) {
            throw new WRWException("规格不能为空！");
        } else {
            entity.setSpecs(dto.getSpecs());
        }
        entity.setPrice(dto.getPrice());
        entity.setCreateDate(new Date());
        entity.setCreateId(AuthorityContext.getCurrentUser().getUserId());
        goodsDao.save(entity);
        return ResultDtoFactory.toAck("保存成功");
    }

    @Transactional
    @Override
    public ResultDto<String> updateGoods(PGoodsDto dto) {
        if (dto.getGoodsId().equals(0L) || dto.getGoodsId() == null) {
            throw new WRWException("单瓶商品ID不能为空！");
        }
        if (WRWUtil.isEmpty(dto.getGoodCode())) {
            throw new WRWException("单瓶商品编码不能为空！");
        } else {
            int goodsCount = goodsDao.findCountByGoodCode(dto.getGoodCode(), dto.getGoodsId());
            if (goodsCount > 0) {
                throw new WRWException("单瓶商品编码已存在，请重新输入！");
            }
        }
        if (WRWUtil.isEmpty(dto.getGoodName())) {
            throw new WRWException("单瓶商品名称不能为空！");
        } else {
            int goodsCount = goodsDao.findCountByGoodsName(dto.getGoodName(), dto.getGoodsId());
            if (goodsCount > 0) {
                throw new WRWException("单瓶商品名称已存在，请重新输入！");
            }
        }
        if (dto.getPrice() == null || dto.getPrice().equals(0L)) {
            throw new WRWException("价格不能为空！");
        }
        if (WRWUtil.isEmpty(dto.getSpecs())) {
            throw new WRWException("规格不能为空！");
        }
        Long modifyId = AuthorityContext.getCurrentUser().getUserId();
        goodsDao.update(dto.getGoodCode(), dto.getGoodName(), dto.getSpecs(), dto.getPrice(), modifyId, dto.getGoodsId());
        return ResultDtoFactory.toAck("保存成功");
    }

    @Override
    public void findProducts(final ProductSearchDto dto) {
        Pageable pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.DESC, "createDate"));
        Page<PProductEntity> page = productDao.findAll(new Specification<PProductEntity>() {
            @Override
            public Predicate toPredicate(Root<PProductEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (dto.getGoodsId() != null) {
                    Predicate predicate = cb.equal(root.<Long> get("goodId"), dto.getGoodsId());
                    predicates.add(predicate);
                }
                Predicate predicate = cb.equal(root.<String> get("status"), StatusEnum.NORMAL.getCode());
                predicates.add(predicate);
                predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }
        }, pageable);
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalRecord(page.getTotalElements());
        dto.setList(buildProductDtos(page.getContent()));
    }

    private List<ProductDto> buildProductDtos(List<PProductEntity> entities) {
        List<ProductDto> dtos = new ArrayList<ProductDto>();
        for (PProductEntity entity : entities) {
            ProductDto dto = ConverterService.convert(entity, ProductDto.class);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public void searchGoods(final PGoodsSearchDto dto) {
        Pageable pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.DESC, "createDate"));
        Page<PGoodsEntity> page = goodsDao.findAll(new Specification<PGoodsEntity>() {

            @Override
            public Predicate toPredicate(Root<PGoodsEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (!WRWUtil.isEmpty(dto.getGoodCode())) {
                    Predicate p1 = cb.equal(root.<String> get("goodCode"), dto.getGoodCode());
                    predicates.add(p1);
                }
                if (!WRWUtil.isEmpty(dto.getGoodName())) {
                    Predicate p2 = cb.like(root.<String> get("goodName"), WRWUtil.filterString(dto.getGoodName()));
                    predicates.add(p2);
                }
                if (dto.getBeginDate() != null && dto.getEndDate() != null) {
                    Predicate p3 = cb.between(root.<Date> get("createDate"), dto.getBeginDate(), dto.getEndDate());
                    predicates.add(p3);
                }
                if (dto.getBeginDate() != null && dto.getEndDate() == null) {
                    Predicate p4 = cb.greaterThanOrEqualTo(root.<Date> get("createDate"), dto.getBeginDate());
                    predicates.add(p4);
                }
                if (dto.getBeginDate() == null && dto.getEndDate() != null) {
                    Predicate p5 = cb.lessThanOrEqualTo(root.<Date> get("createDate"), dto.getEndDate());
                    predicates.add(p5);
                }
                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }
        }, pageable);
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalRecord(page.getTotalElements());
        dto.setList(buildGoodsDtos(page.getContent()));
    }

    public List<PGoodsDto> buildGoodsDtos(List<PGoodsEntity> entities) {
        List<PGoodsDto> dtos = new ArrayList<PGoodsDto>();
        for (PGoodsEntity entity : entities) {
            PGoodsDto dto = ConverterService.convert(entity, PGoodsDto.class);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public PGoodsDto findByGoodsId(Long goodsId) {
        PGoodsEntity entity = goodsDao.findByGoodsId(goodsId);
        return ConverterService.convert(entity, PGoodsDto.class);
    }

}
