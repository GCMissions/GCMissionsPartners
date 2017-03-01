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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.product.dto.ProductDto;
import com.hengtiansoft.business.product.dto.ProductFloorSaveDto;
import com.hengtiansoft.business.product.dto.ProductFloorSearchDto;
import com.hengtiansoft.business.product.dto.ProductPFloorDto;
import com.hengtiansoft.business.product.dto.ProductPageSearchDto;
import com.hengtiansoft.business.product.service.ProductPFloorService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.PFloorDao;
import com.hengtiansoft.wrw.dao.PShiefDao;
import com.hengtiansoft.wrw.dao.PProductDao;
import com.hengtiansoft.wrw.entity.PFloor;
import com.hengtiansoft.wrw.entity.PProductEntity;
import com.hengtiansoft.wrw.enums.ShipFlagEnum;
import com.hengtiansoft.wrw.enums.StatusEnum;

@Service
public class ProductPFloorServiceImpl implements ProductPFloorService {

    @Autowired
    private PFloorDao   floorDao;

    @Autowired
    private PProductDao productDao;

    
    @Autowired
    private PShiefDao   priceDao;

    @Override
    public ProductFloorSaveDto findByRegionIdAndfloorType(ProductFloorSearchDto dto) {
        // 获取所有的楼层商品
        List<PFloor> list = floorDao.findProductIdByRegionIdAndFloorType(dto.getRegionId(), dto.getFloorType(), StatusEnum.NORMAL.getCode());

        ProductFloorSaveDto saveDto = new ProductFloorSaveDto();
        List<ProductPFloorDto> listDtos = new ArrayList<ProductPFloorDto>();
        Map<Long, ProductPFloorDto> map = new HashMap<Long, ProductPFloorDto>();
        List<Long> listIds = new ArrayList<Long>();
        // 将楼层的商品ID、排序、图片塞到DTO中
        for (PFloor pFloor : list) {
            ProductPFloorDto floorDto = new ProductPFloorDto();
            floorDto.setProductId(pFloor.getProductId());
            floorDto.setSort(pFloor.getSort());
            floorDto.setImage(pFloor.getImage());
            map.put(pFloor.getProductId(), floorDto);
            listIds.add(pFloor.getProductId());
            listDtos.add(floorDto);
        }
        if (listDtos != null && listDtos.size() > 0) {

            // 将商品的其他信息塞到DTO中
            List<PProductEntity> products = productDao.findAll(listIds);
            for (PProductEntity pProductEntity : products) {
                ProductPFloorDto fDto = map.get(pProductEntity.getProductId());
                fDto.setBrandId(pProductEntity.getBrandId());
                fDto.setBrandName(pProductEntity.getBrandName());
                fDto.setCateId(pProductEntity.getCateId());
                fDto.setCateName(pProductEntity.getCateName());
                fDto.setProductCode(pProductEntity.getProductCode());
                fDto.setProductName(pProductEntity.getProductName());

            }
            // 获取商品在该地区的的上下架状态

            List<Object[]> listStatus = priceDao.findByProductIdsAndRegionId(listIds, dto.getRegionId());
            for (Object[] object : listStatus) {
                ProductPFloorDto pdto = map.get(Long.valueOf(String.valueOf(object[0])));
                if (pdto != null) {
                    if ("0".equals(object[1].toString())) {
                        pdto.setShiefStatus(ShipFlagEnum.UNSHELF.getKey());
                    } else {
                        pdto.setShiefStatus(ShipFlagEnum.SHELF.getKey());
                    }
                }
            }
        }
        saveDto.setFloorType(dto.getFloorType());
        saveDto.setRegionId(dto.getRegionId());
        saveDto.setList(listDtos);
        return saveDto;
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> save(ProductFloorSaveDto dto) {
        List<ProductPFloorDto> listDtos = dto.getList();
        List<PFloor> listFloor = new ArrayList<PFloor>();
        List<Long> listIds = new ArrayList<Long>();
        // 长度大于0
        if (listDtos != null && listDtos.size() > 0) {
            for (ProductPFloorDto productPFloorDto : listDtos) {
                listIds.add(productPFloorDto.getProductId());

            }
            // 进行验证 判断传过来的商品是否在上架中
            List<Object[]> objects = priceDao.findByProductIdsAndRegionId(listIds, dto.getRegionId());
            if (objects == null || objects.size() == 0) {
                throw new WRWException("保存失败,该地区没有商品处于上架中!");
            }
            for (Object[] object : objects) {
                if ("0".equals(object[1].toString())) {
                    throw new WRWException("保存失败,名称为" + productDao.findOne(Long.valueOf(object[0].toString())).getProductName() + "的商品,在该地区处于下架状态!");
                }
            }
            for (ProductPFloorDto productPFloorDto : listDtos) {
                PFloor floor = ConverterService.convert(productPFloorDto, new PFloor());
                floor.setCreateDate(new Date());
                floor.setCreateId(AuthorityContext.getCurrentUser().getUserId());
                floor.setStatus(StatusEnum.NORMAL.getCode());
                floor.setRegionId(dto.getRegionId());
                floor.setFloorType(dto.getFloorType());
                listFloor.add(floor);
            }
            // 先对原先的数据进行删除。
            // 再进行新增
            floorDao.updateByFloorTypeAndRegionId(dto.getFloorType(), dto.getRegionId());
            floorDao.save(listFloor);
        } else {
            // 传值为空，就直接全部删除!
            floorDao.updateByFloorTypeAndRegionId(dto.getFloorType(), dto.getRegionId());
        }

        return ResultDtoFactory.toAck("保存成功!");

    }

    @Override
    public void search(final ProductPageSearchDto dto, final List<Long> productIds) {

        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.DESC, "productId"));
        Page<PProductEntity> page = productDao.findAll(new Specification<PProductEntity>() {
            @Override
            public Predicate toPredicate(Root<PProductEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                Predicate p1 = cb.equal(root.<String> get("status"), StatusEnum.NORMAL.getCode());
                predicates.add(p1);
                // 通过regionId
                predicates.add(root.<Long> get("productId").in(productIds));

                if (!WRWUtil.isEmpty(dto.getProductCode())) {
                    predicates.add(cb.equal(root.<String> get("productCode"), dto.getProductCode()));
                }
                if (!WRWUtil.isEmpty(dto.getProductName())) {
                    predicates.add(cb.like(root.<String> get("productName"), "%" + dto.getProductName() + "%"));
                }
                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }
        }, pageable);

        dto.setTotalRecord(page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setList(buildProductDtos(page.getContent()));

    }

    private List<ProductDto> buildProductDtos(List<PProductEntity> content) {
        List<ProductDto> list = new ArrayList<ProductDto>();
        for (PProductEntity pProductEntity : content) {
            ProductDto dto = new ProductDto();
            dto.setBrandId(pProductEntity.getBrandId());
            dto.setBrandName(pProductEntity.getBrandName());
            dto.setCateId(pProductEntity.getCateId());
            dto.setCateName(pProductEntity.getCateName());
            dto.setProductCode(pProductEntity.getProductCode());
            dto.setProductName(pProductEntity.getProductName());
            dto.setProductId(pProductEntity.getProductId());
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<Long> findByRegionId(Integer regionId) {
        return priceDao.findByRegionId(regionId);
    }

}
