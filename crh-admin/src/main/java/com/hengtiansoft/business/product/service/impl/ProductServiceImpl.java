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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.order.dto.LineOrderDto;
import com.hengtiansoft.business.product.dto.AttributeDto;
import com.hengtiansoft.business.product.dto.PCateProductDto;
import com.hengtiansoft.business.product.dto.PTypeSaveDto;
import com.hengtiansoft.business.product.dto.ProductAttrDto;
import com.hengtiansoft.business.product.dto.ProductDto;
import com.hengtiansoft.business.product.dto.ProductImageDto;
import com.hengtiansoft.business.product.dto.ProductSaveDto;
import com.hengtiansoft.business.product.dto.ProductSearchDto;
import com.hengtiansoft.business.product.service.AttributeService;
import com.hengtiansoft.business.product.service.PTypeService;
import com.hengtiansoft.business.product.service.ProductService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.DateUtils;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.ActivityStockDao;
import com.hengtiansoft.wrw.dao.PBrandDao;
import com.hengtiansoft.wrw.dao.PCategoryDao;
import com.hengtiansoft.wrw.dao.PGoodsDao;
import com.hengtiansoft.wrw.dao.PProductAttributeDao;
import com.hengtiansoft.wrw.dao.PProductDao;
import com.hengtiansoft.wrw.dao.PProductImageDao;
import com.hengtiansoft.wrw.dao.PRelationShipDao;
import com.hengtiansoft.wrw.dao.PShiefDao;
import com.hengtiansoft.wrw.entity.ActivityStock;
import com.hengtiansoft.wrw.entity.PBrandEntity;
import com.hengtiansoft.wrw.entity.PCategoryEntity;
import com.hengtiansoft.wrw.entity.PGoodsEntity;
import com.hengtiansoft.wrw.entity.PProductAttrEntity;
import com.hengtiansoft.wrw.entity.PProductEntity;
import com.hengtiansoft.wrw.entity.PProductImageEntity;
import com.hengtiansoft.wrw.entity.PRelationShipEntity;
import com.hengtiansoft.wrw.enums.StatusEnum;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private PProductDao pProductDao;

    @Autowired
    private PProductAttributeDao pProductAttributeDao;

    @Autowired
    private PRelationShipDao psDao;

    @Autowired
    private PProductImageDao pImageDao;

    @Autowired
    private PCategoryDao pcDao;

    @Autowired
    private PTypeService pTypeService;

    @Autowired
    private PBrandDao pBrandDao;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private PShiefDao priceDao;

    private final String CATE_ID_FINLA = "0";

    @Autowired
    private PGoodsDao goodsDao;
    
    @Autowired
    private ActivityStockDao activityStockDao;

    @Override
    public void search(final ProductSearchDto dto) {
        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.DESC,
                "productId"));

        Page<PProductEntity> page = pProductDao.findAll(new Specification<PProductEntity>() {
            @Override
            public Predicate toPredicate(Root<PProductEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                Predicate p1 = cb.equal(root.<String> get("status"), StatusEnum.NORMAL.getCode());
                predicates.add(p1);
                if (!WRWUtil.isEmpty(dto.getProductName())) {
                    String msg = "%" + dto.getProductName() + "%";
                    Predicate p2 = cb.like(cb.lower(root.<String> get("productName")), msg);
                    predicates.add(p2);
                }
                // 前端传过来的0 代表的是不限。
                if (WRWUtil.isNotEmpty(dto.getCateId()) && !(CATE_ID_FINLA.equals(dto.getCateId()))) {
                    Predicate p3 = cb.equal(root.<Long> get("cateId"), Long.valueOf(dto.getCateId()));
                    predicates.add(p3);
                }
                if (!WRWUtil.isEmpty(dto.getProductCode())) {
                    Predicate p8 = cb.equal(root.<String> get("productCode"), dto.getProductCode());
                    predicates.add(p8);
                }
                if (!WRWUtil.isEmpty(dto.getBrandId())) {
                    // 判断brandId传入的值 是0代表无品牌
                    if (CATE_ID_FINLA.equals(dto.getBrandId())) {
                        predicates.add(cb.isNull(root.<Long> get("brandId")));
                    } else {
                        predicates.add(cb.equal(root.<Long> get("brandId"), Long.valueOf(dto.getBrandId())));
                    }
                }
                if (null != dto.getStartDate() && null != dto.getEndDate()) {
                    Predicate p5 = cb.between(root.<Date> get("createDate"), dto.getStartDate(), dto.getEndDate());
                    predicates.add(p5);
                }
                if (null != dto.getStartDate() && null == dto.getEndDate()) {
                    Predicate p6 = cb.greaterThanOrEqualTo(root.<Date> get("createDate"), dto.getStartDate());
                    predicates.add(p6);
                }
                if (null == dto.getStartDate() && null != dto.getEndDate()) {
                    Predicate p7 = cb.lessThanOrEqualTo(root.<Date> get("createDate"), dto.getEndDate());
                    predicates.add(p7);
                }
                // 区分是首页列表还是关联时的搜索
                if (null != dto.getRelateProductId() && 0 != dto.getRelateProductId()) {
                    Predicate p8 = cb.notEqual(root.<Long> get("productId"), dto.getRelateProductId());
                    predicates.add(p8);
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
        List<Long> goodsIds = new ArrayList<Long>();
        for (PProductEntity entity : content) {
            ProductDto dto = ConverterService.convert(entity, new ProductDto());
            if (entity.getGoodId() != null) {
                goodsIds.add(entity.getGoodId());
            }
            dto.setPrice(WRWUtil.transFenToYuan2String(entity.getPrice()));
            dto.setCreateDate(DateTimeUtil.parseDateToString(entity.getCreateDate(), DateTimeUtil.SIMPLE_FMT_MINUTE));
            list.add(dto);
        }

        if (CollectionUtils.isNotEmpty(goodsIds)) {
            List<PGoodsEntity> goods = goodsDao.findByGoodsIds(goodsIds);
            Map<Long, PGoodsEntity> map = new HashMap<Long, PGoodsEntity>();
            for (PGoodsEntity entity : goods) {
                if (map.get(entity.getGoodsId()) == null) {
                    map.put(entity.getGoodsId(), entity);
                }
            }
            for (ProductDto dto : list) {
                if (map.get(dto.getGoodId()) != null) {
                    dto.setGoodName(map.get(dto.getGoodId()).getGoodName());
                }
            }
        }

        return list;
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<ProductSaveDto> save(ProductSaveDto dto) {
        PProductEntity entity = new PProductEntity();
        if (WRWUtil.isEmpty(dto.getProductName())) {
            throw new WRWException("商品名称未填写,请重新输入!");
        } else {
            int countName = pProductDao.findCountByProductName(dto.getProductName());
            if (countName > 0) {
                throw new WRWException("该商品名称已存在,请检查后重新输入!");
            }
            entity.setProductName(dto.getProductName());
        }
        if (WRWUtil.isEmpty(dto.getProductCode())) {
            throw new WRWException("商品条码未填写,请重新输入!");
        } else {
            // 验证商品条码的唯一性
            int countCode = pProductDao.findByProductCode(dto.getProductCode());
            if (countCode > 0) {
                throw new WRWException("该商品条码已存在,请检查后重新输入!");
            }
            entity.setProductCode(dto.getProductCode());
        }

        if (dto.getGoodId() == null) {
            throw new WRWException("单瓶商品未填写，请重新输入！");
        } else {
            entity.setGoodId(dto.getGoodId());
        }

        if (dto.getCateId() == null) {
            throw new WRWException("商品分类传值错误,请检查后重新输入!");
        } else {
            PCategoryEntity cateEntity = pcDao.findOne(dto.getCateId());
            if (cateEntity == null) {
                throw new WRWException("新增错误,请检查输入!");
            }
            entity.setCateId(cateEntity.getCateId());
            entity.setCateName(cateEntity.getCateName());
            entity.setTypeId(cateEntity.getTypeId());
        }
        // 现在品牌也可以保存
        if (dto.getBrandId() != 0L) {
            PBrandEntity bEntity = pBrandDao.findOne(dto.getBrandId());
            if (bEntity == null) {
                throw new WRWException("商品品牌输入有误,请检查后重新输入!");
            }
            entity.setBrandId(bEntity.getBrandId());
            entity.setBrandName(bEntity.getBrandName());
        }
       
//        entity.setPrice(WRWUtil.transYuanToFen(dto.getPrice()));
        entity.setCostPrice(0L);
        // 判断传过来的图片中有没有默认为封面的，如果有，则塞到image中，如果没有，则去ListImages中拿第一个
        if (!WRWUtil.isEmpty(dto.getImage())) {
            entity.setImage(dto.getImage());
        } else {
            if (dto.getListImages() != null && dto.getListImages().size() > 0) {
                entity.setImage(dto.getListImages().get(0).getImageUrl());
            }
        }

        if (dto.getSpecNum().equals(1)) {
            List<PProductEntity> list = pProductDao.findByGoodIdAndSpecNum(dto.getGoodId(), dto.getSpecNum());
            if (list.size() > 0) {
                throw new WRWException("相同单瓶商品且瓶数为1的商品只能创建一个！");
            }
        }

        entity.setPromotion(dto.getPromotion());
        entity.setUrl(dto.getUrl());
        entity.setNote(dto.getNote());
        entity.setDescription(dto.getDesc());
        entity.setCreateId(AuthorityContext.getCurrentUser().getUserId());
        entity.setCreateDate(new Date());
        entity.setWeight(dto.getWeight());
        entity.setUnitName(dto.getUnitName());
        entity.setSpecNum(dto.getSpecNum());
        entity.setGoodNum(Long.parseLong(dto.getSpecNum().toString()));
        entity.setStatus(StatusEnum.NORMAL.getCode());
        entity.setProductionDate(dto.getProductionDate());
        pProductDao.save(entity);
        // 往product_attr表中插入记录
        if (dto.getListAttr() != null && dto.getListAttr().size() > 0) {
            List<PProductAttrEntity> listAttr = new ArrayList<PProductAttrEntity>(dto.getListAttr().size());
            List<ProductAttrDto> listpa = dto.getListAttr();
            for (ProductAttrDto productAttrDto : listpa) {
                PProductAttrEntity aEntity = ConverterService.convert(productAttrDto, new PProductAttrEntity());
                aEntity.setCreateDate(new Date());
                aEntity.setProductId(entity.getProductId());
                aEntity.setCreateId(AuthorityContext.getCurrentUser().getUserId());
                aEntity.setStatus(StatusEnum.NORMAL.getCode());
                listAttr.add(aEntity);
            }
            pProductAttributeDao.save(listAttr);
        }
        // 往product_image表中插入记录
        if (dto.getListImages() != null && dto.getListImages().size() > 0) {
            List<ProductImageDto> listImages = dto.getListImages();
            List<PProductImageEntity> imagesEntity = new ArrayList<PProductImageEntity>(listImages.size());
            for (ProductImageDto imageDto : listImages) {
                PProductImageEntity imageEntity = ConverterService.convert(imageDto, new PProductImageEntity());
                imageEntity.setStatus(StatusEnum.NORMAL.getCode());
                imageEntity.setProductId(entity.getProductId());
                imageEntity.setCreateId(AuthorityContext.getCurrentUser().getUserId());
                imageEntity.setCreateDate(new Date());
                imagesEntity.add(imageEntity);
            }
            pImageDao.save(imagesEntity);
        }

        // 往p_relationship表中插入记录
        if (dto.getRelationShipIds() != null && dto.getRelationShipIds().size() > 0) {
            List<Long> listIds = dto.getRelationShipIds();
            List<PRelationShipEntity> listps = new ArrayList<PRelationShipEntity>(listIds.size());
            for (Long shipId : listIds) {
                PRelationShipEntity psEntity = new PRelationShipEntity();
                psEntity.setProductId(entity.getProductId());
                psEntity.setRelatId(shipId);
                listps.add(psEntity);
            }
            psDao.save(listps);
        }
        return ResultDtoFactory.toAck("新增成功!");
    }

    @Override
    public ProductSaveDto findById(Long id) {
        PProductEntity pEntity = pProductDao.findOne(id);
        if (pEntity == null) {
            throw new WRWException("数据错误,查无此商品");
        }
        ProductSaveDto dto = ConverterService.convert(pEntity, new ProductSaveDto());
        dto.setPrice(WRWUtil.transFenToYuan(pEntity.getPrice()));
        dto.setDesc(pEntity.getDescription());
        if (pEntity.getGoodId() != null) {
            PGoodsEntity goodsEntity = goodsDao.findByGoodsId(pEntity.getGoodId());
            if (goodsEntity != null) {
                dto.setGoodName(goodsEntity.getGoodName());
            }
        }
        List<PProductAttrEntity> listpAEntity = pProductAttributeDao.findByProductIdAndStatus(id,
                StatusEnum.NORMAL.getCode());
        List<ProductAttrDto> listAttr = new ArrayList<ProductAttrDto>();
        for (PProductAttrEntity pAEntity : listpAEntity) {
            ProductAttrDto aDto = ConverterService.convert(pAEntity, new ProductAttrDto());
            listAttr.add(aDto);
        }
        dto.setListAttr(listAttr);

        // 获取所有的照片 排序获取
        List<PProductImageEntity> listImageEntity = pImageDao.findByProductIdAndStatus(id, StatusEnum.NORMAL.getCode());
        List<ProductImageDto> listImage = new ArrayList<ProductImageDto>();
        for (PProductImageEntity pProductImageEntity : listImageEntity) {
            ProductImageDto iDto = ConverterService.convert(pProductImageEntity, new ProductImageDto());
            listImage.add(iDto);
        }
        dto.setListImages(listImage);

        // 获取所有的关联Id
        List<Long> listIds = psDao.findByProductId(id);
        dto.setRelationShipIds(listIds);
        // 通过Id获取所有的商品信息
        List<PProductEntity> listProducts = pProductDao.findAll(listIds);
        dto.setListProducts(listProducts);

        return dto;
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> update(ProductSaveDto dto) {
        PProductEntity entity = pProductDao.findOne(dto.getProductId());
        if (entity == null) {
            throw new WRWException("传值错误,请检查后重新输入!");
        }
        if (WRWUtil.isEmpty(dto.getProductName())) {
            throw new WRWException("商品名称未填写,请重新输入!");
        } else {
            if (!entity.getProductName().equals(dto.getProductName())) {
                int countName = pProductDao.findCountByProductName(dto.getProductName());
                if (countName > 0) {
                    throw new WRWException("该商品名称已存在,请检查后重新输入!");
                }
                entity.setProductName(dto.getProductName());
            }
        }
        if (WRWUtil.isEmpty(dto.getProductCode())) {
            throw new WRWException("商品条码未填写,请重新输入!");
        } else {
            if (!entity.getProductCode().equals(dto.getProductCode())) {
                // 验证商品条码的唯一性
                int countCode = pProductDao.findByProductCode(dto.getProductCode());
                if (countCode > 0) {
                    throw new WRWException("该商品条码已存在,请检查后重新输入!");
                }
                entity.setProductCode(dto.getProductCode());
            }
        }
        if (dto.getCateId() == null) {
            throw new WRWException("商品分类传值错误,请检查后重新输入!");
        } else {
            PCategoryEntity cateEntity = pcDao.findOne(dto.getCateId());
            if (cateEntity == null) {
                throw new WRWException("新增错误,请检查输入!");
            }
            entity.setCateId(cateEntity.getCateId());
            entity.setCateName(cateEntity.getCateName());
            entity.setTypeId(cateEntity.getTypeId());
        }
        entity.setStatus(StatusEnum.NORMAL.getCode());
        // if (dto.getBrandId() == null) {
        // throw new WLYException("商品品牌输入有误,请检查后重新输入!");
        // } else {
        // PBrandEntity bEntity = pBrandDao.findOne(dto.getBrandId());
        // if (bEntity == null) {
        // throw new WLYException("商品品牌输入有误,请检查后重新输入!");
        // }
        // entity.setBrandId(bEntity.getBrandId());
        // entity.setBrandName(bEntity.getBrandName());
        // }
        // 现在品牌也可以保存
        if (dto.getBrandId() != 0L) {
            PBrandEntity bEntity = pBrandDao.findOne(dto.getBrandId());
            if (bEntity == null) {
                throw new WRWException("商品品牌输入有误,请检查后重新输入!");
            }
            entity.setBrandId(bEntity.getBrandId());
            entity.setBrandName(bEntity.getBrandName());
        } else {
            entity.setBrandId(null);
            entity.setBrandName(null);
        }
        if (dto.getGoodId() == null) {
            throw new WRWException("单瓶商品未填写，请重新输入！");
        } else {
            entity.setGoodId(dto.getGoodId());
        }
        if (dto.getSpecNum().equals(1)) {
            List<PProductEntity> list = pProductDao.findByGoodIdAndSpecNum(dto.getGoodId(), dto.getSpecNum(),
                    dto.getProductId());
            if (list.size() > 0) {
                throw new WRWException("相同单瓶商品且瓶数为1的商品只能创建一个！");
            }
        }
        entity.setPrice(WRWUtil.transYuanToFen(dto.getPrice()));
        entity.setCostPrice(0L);
        // 判断传过来的图片中有没有默认为封面的，如果有，则塞到image中，如果没有，则去ListImages中拿第一个
        if (!WRWUtil.isEmpty(dto.getImage())) {
            entity.setImage(dto.getImage());
        } else {
            if (dto.getListImages() != null && dto.getListImages().size() > 0) {
                entity.setImage(dto.getListImages().get(0).getImageUrl());
            }
        }
        entity.setPromotion(dto.getPromotion());
        entity.setUrl(dto.getUrl());
        entity.setNote(dto.getNote());
        entity.setWeight(dto.getWeight());
        entity.setSpecNum(dto.getSpecNum());
        entity.setGoodNum(Long.parseLong(dto.getSpecNum().toString()));
        entity.setDescription(dto.getDesc());
        entity.setModifyId(AuthorityContext.getCurrentUser().getUserId());
        entity.setModifyDate(new Date());
        pProductDao.save(entity);

        // 往product_attr表中插入记录
        if (dto.getListAttr() != null && dto.getListAttr().size() > 0) {
            List<PProductAttrEntity> listAttr = new ArrayList<PProductAttrEntity>(dto.getListAttr().size());
            List<ProductAttrDto> listpa = dto.getListAttr();
            for (ProductAttrDto productAttrDto : listpa) {
                PProductAttrEntity aEntity = ConverterService.convert(productAttrDto, new PProductAttrEntity());
                aEntity.setProductId(entity.getProductId());
                aEntity.setCreateDate(new Date());
                aEntity.setCreateId(AuthorityContext.getCurrentUser().getUserId());
                aEntity.setStatus(StatusEnum.NORMAL.getCode());
                listAttr.add(aEntity);
            }
            pProductAttributeDao.deletByProductId(dto.getProductId(), StatusEnum.DELETE.getCode(), new Date(),
                    AuthorityContext.getCurrentUser().getUserId());
            pProductAttributeDao.save(listAttr);
        }
        // 往product_image表中插入记录
        if (dto.getListImages() != null && dto.getListImages().size() > 0) {
            List<ProductImageDto> listImages = dto.getListImages();
            List<PProductImageEntity> imagesEntity = new ArrayList<PProductImageEntity>(listImages.size());
            List<Long> imageIds = new ArrayList<Long>();
            for (ProductImageDto imageDto : listImages) {
                PProductImageEntity imageEntity = ConverterService.convert(imageDto, new PProductImageEntity());
                imageEntity.setProductId(entity.getProductId());
                imageEntity.setStatus(StatusEnum.NORMAL.getCode());
                imageEntity.setCreateId(AuthorityContext.getCurrentUser().getUserId());
                imageEntity.setCreateDate(new Date());
                imagesEntity.add(imageEntity);
                if (!WRWUtil.isEmpty(String.valueOf(imageDto.getImageId()))) {
                    imageIds.add(imageDto.getImageId());
                }
            }
            if (imageIds != null && imageIds.size() > 0) {
                pImageDao.deleteByProductIdAndId(dto.getProductId(), imageIds);
            }
            pImageDao.save(imagesEntity);
        }

        // 往p_relationship表中插入记录
        if (dto.getRelationShipIds() != null && dto.getRelationShipIds().size() > 0) {
            List<Long> listIds = dto.getRelationShipIds();
            List<PRelationShipEntity> listps = new ArrayList<PRelationShipEntity>(listIds.size());
            for (Long shipId : listIds) {
                PRelationShipEntity psEntity = new PRelationShipEntity();
                psEntity.setProductId(entity.getProductId());
                psEntity.setRelatId(shipId);
                listps.add(psEntity);
            }
            // 先删除,再插入
            psDao.deleteByProductId(dto.getProductId());
            psDao.save(listps);
        }
        return ResultDtoFactory.toAck("修改成功!");
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> delete(Long id) {
        PProductEntity entity = pProductDao.findOne(id);
        if (entity == null) {
            throw new WRWException("传值错误,查无此数据!");
        }
        // 判断该商品是否有在任何一个地区处在上架状态
        int countNum = priceDao.findShiefByProductId(id);
        if (countNum > 0) {
            throw new WRWException("操作失败,该商品存在上架情况,请先将商品下架之后再进行删除!");
        }
        Long mid = AuthorityContext.getCurrentUser().getUserId();
        entity.setStatus(StatusEnum.DELETE.getCode());
        entity.setModifyDate(new Date());
        entity.setModifyId(mid);
        pProductDao.save(entity);
        pProductAttributeDao.deletByProductId(id, StatusEnum.DELETE.getCode(), new Date(), mid);
        pImageDao.deleteByProductId(id, StatusEnum.DELETE.getCode(), new Date(), mid);
        psDao.deleteByProductId(id);
        return ResultDtoFactory.toAck("删除成功!");
    }

    @Override
    public Object findCateByCateId(Long cateId, HttpServletRequest request, HttpServletResponse response) {
        PCategoryEntity entity = pcDao.findOne(cateId);
        String msg = "";
        if (entity == null) {
            msg = "数据错误!";
            request.setAttribute("msg", msg);
            return "redirect:/web/product/addPage";
        }
        PTypeSaveDto dto = pTypeService.findById(entity.getTypeId());
        if (dto == null) {
            msg = "数据错误!";
            request.setAttribute("msg", msg);
            return "redirect:/web/product/cateList";
        }
        List<Long> attrIds = dto.getAttrIds();
        List<Long> brands = dto.getBrandIds();
        List<AttributeDto> listAttr = new ArrayList<AttributeDto>();
        for (Long attrId : attrIds) {
            AttributeDto adto = attributeService.findById(attrId);
            listAttr.add(adto);
        }
        List<PBrandEntity> listBrand = pBrandDao.findAll(brands);

        PCateProductDto pdto = new PCateProductDto();
        pdto.setListAttr(listAttr);
        pdto.setListBrands(listBrand);
        pdto.setpCategoryEntity(entity);
        // TODO 通过cateId找到其父节点，并拼装起来
        return pdto;
    }

    @Override
    public Object findCateById(Long id, HttpServletRequest request, HttpServletResponse response) {
        PProductEntity pEntity = pProductDao.findOne(id);
        if (pEntity == null) {
            return "rediect:/web/product/list";
        }
        return findCateByCateId(pEntity.getCateId(), request, response);
    }

    @Override
    public PProductEntity findByActStockId(Long actStockId) {
        return pProductDao.findProductByActStockId(actStockId);
    }

    @Override
    public ResultDto<String> checkStatusForLineOrder(LineOrderDto lineOrderDto) {
        PProductEntity pEntity = pProductDao.findOne(lineOrderDto.getProductId());
        lineOrderDto.setProductName(pEntity != null ? pEntity.getProductName():null);
        if(pEntity == null){
            return ResultDtoFactory.toBusinessError("录入订单中商品不存在！");
        }else if(pEntity !=null && "0".equals(pEntity.getStatus())){
            return ResultDtoFactory.toBusinessError("录入订单中商品已失效！");
        }/*else if(pEntity !=null && !SaleFlagEnum.SHELF.getCode().equals(pEntity.getSaleStatus())){
            return ResultDtoFactory.toBusinessError("录入订单中商品未上架！");
        }*/else if(WRWUtil.isEmpty(lineOrderDto.getActDate())){
            return ResultDtoFactory.toBusinessError("录入订单中商品活动时间为空！");
        }else{
            ActivityStock activityStock = activityStockDao.findIdByActId(lineOrderDto.getProductId(), lineOrderDto.getActDate());
            if(activityStock == null){
                return ResultDtoFactory.toBusinessError("录入订单中商品活动时间无效！");
            }
            Date curDate = new Date();
            if(!curDate.after(DateUtils.getSpecifiedDayAfter(activityStock.getActDate()))){
                return ResultDtoFactory.toBusinessError("您好，只有活动结束之后才能进行此操作！");
            }
        }
        return ResultDtoFactory.toAck("商品信息状态正常！");
    }

    @Override
    public List<PProductEntity> findProductsByOrderId(String orderId) {
        return pProductDao.findProductsByOrderId(orderId);
    }

}
