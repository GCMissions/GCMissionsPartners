package com.hengtiansoft.business.product.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.product.dto.ProductDeliveryShiefDetailDto;
import com.hengtiansoft.business.product.dto.ProductDeliveryShiefDto;
import com.hengtiansoft.business.product.dto.ProductPriceDto;
import com.hengtiansoft.business.product.dto.ProductShiefDto;
import com.hengtiansoft.business.product.dto.ProductShiefSearchDto;
import com.hengtiansoft.business.product.dto.RegionPriceDto;
import com.hengtiansoft.business.product.service.ProductService;
import com.hengtiansoft.business.product.service.ProductShiefService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.ActivityConstranintDao;
import com.hengtiansoft.wrw.dao.ActivitySpecDao;
import com.hengtiansoft.wrw.dao.ActivityStockDao;
import com.hengtiansoft.wrw.dao.PCategoryDao;
import com.hengtiansoft.wrw.dao.PFloorDao;
import com.hengtiansoft.wrw.dao.PShiefDao;
import com.hengtiansoft.wrw.dao.PProductDao;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.dao.SRegionDao;
import com.hengtiansoft.wrw.dao.SStockDao;
import com.hengtiansoft.wrw.entity.ActivityConstranint;
import com.hengtiansoft.wrw.entity.ActivitySpec;
import com.hengtiansoft.wrw.entity.ActivityStock;
import com.hengtiansoft.wrw.entity.PCategoryEntity;
import com.hengtiansoft.wrw.entity.PShiefEntity;
import com.hengtiansoft.wrw.entity.PProductEntity;
import com.hengtiansoft.wrw.entity.SOrgEntity;
import com.hengtiansoft.wrw.enums.ProductTypeEnum;
import com.hengtiansoft.wrw.enums.SaleFlagEnum;
import com.hengtiansoft.wrw.enums.StatusEnum;

/**
 * 
 * Class Name: ProductShiefServiceImpl Description:
 * 
 * @author zhisongliu
 *
 */
@Service
public class ProductShiefServiceImpl implements ProductShiefService {

    @Autowired
    private PProductDao pProductDao;

    @Autowired
    private SStockDao sStockDao;

    @Autowired
    private PShiefDao priceDao;

    @Autowired
    private PFloorDao floorDao;

    @Autowired
    private SRegionDao regionDao;

    @Autowired
    private SOrgDao sOrgDao;

    @Autowired
    private PCategoryDao pcDao;

    @Autowired
    private ProductService productService;

    @Autowired
    private ActivityStockDao activityStockDao;

    @Autowired
    private ActivitySpecDao activitySpecDao;

    @Autowired
    private ActivityConstranintDao activityConstranintDao;

    private final String CATE_ID_FINLA = "0";

    @Override
    public void search(final ProductShiefSearchDto dto) {
        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.DESC,
                "createDate"));
        Page<PProductEntity> page = pProductDao.findAll(new Specification<PProductEntity>() {
            @Override
            public Predicate toPredicate(Root<PProductEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.<String> get("status"), StatusEnum.NORMAL.getCode()));

                // 商品名称
                if (!WRWUtil.isEmpty(dto.getProductName())) {

                    char escape = '\\';
                    String msg = "%" + dto.getProductName().replace("\\", escape+"\\").replace("%", escape+"%").replace("_", escape+"_") + "%";
                    Predicate p2 = cb.like(cb.lower(root.<String> get("productName")), msg, escape);
                    predicates.add(p2);
                }
                // 商品品类
                if (WRWUtil.isEmpty(dto.getSecondCateId())) {
                    // 前端传过来的0代表的是不限。
                    if (WRWUtil.isNotEmpty(dto.getFirstCateId()) && !(CATE_ID_FINLA.equals(dto.getFirstCateId()))) {
                        List<Long> cateIds = new ArrayList<Long>();
                        cateIds.add(Long.valueOf(dto.getFirstCateId()));
                        List<PCategoryEntity> categorys = pcDao.findByParentId(Long.valueOf(dto.getFirstCateId()));
                        for (PCategoryEntity entity : categorys) {
                            cateIds.add(entity.getCateId());
                        }
                        predicates.add(root.<Long> get("cateId").in(cateIds));
                    }
                } else {

                    if (WRWUtil.isNotEmpty(dto.getSecondCateId())) {
                        Predicate p3 = cb.equal(root.<Long> get("cateId"), Long.valueOf(dto.getSecondCateId()));
                        predicates.add(p3);
                    }
                }

                // 服务商
                if (!WRWUtil.isEmpty(dto.getOrgId())) {
                    predicates.add(cb.equal(root.<Long> get("orgId"), Long.valueOf(dto.getOrgId())));

                }
                if (!WRWUtil.isEmpty(dto.getSaleStatus())) {
                    List<Long> productIds = new ArrayList<Long>();
                    // 先把所有的上架商品查询出来，
                    // 如果是搜索未上架的，则将所有上架商品的ID去PRODUCT表中NOT IN 一把。
                    productIds = priceDao.findByShelf();
                    if (SaleFlagEnum.UNSHELF.getCode().equals(dto.getSaleStatus()) && productIds.size() > 0) {
                        productIds = priceDao.findbyUnShelf(productIds, StatusEnum.NORMAL.getCode());
                    }

                    if (productIds.size() != 0) {
                        predicates.add(root.<Long> get("productId").in(productIds));
                    } else if (SaleFlagEnum.SHELF.getCode().equals(dto.getSaleStatus())) {
                        predicates.add(root.<Long> get("productId").isNull());
                    }
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

    private List<ProductShiefDto> buildProductDtos(List<PProductEntity> content) {
        List<ProductShiefDto> list = new ArrayList<ProductShiefDto>();
        Map<Long, ProductShiefDto> map = new HashMap<Long, ProductShiefDto>();
        List<Long> listIds = new ArrayList<Long>();
        List<Long> orgIds = new ArrayList<Long>();
        for (PProductEntity entity : content) {
            ProductShiefDto dto = ConverterService.convert(entity, new ProductShiefDto());
            listIds.add(entity.getProductId());
            orgIds.add(entity.getOrgId());
            map.put(entity.getProductId(), dto);
            if (null != entity.getTypeId()) {
                dto.setType(ProductTypeEnum.getValue(entity.getTypeId()));
            }
            list.add(dto);
        }
        if (listIds != null && listIds.size() > 0) {
            List<Object[]> listStatus = priceDao.findByProductIds(listIds);
            for (Object[] object : listStatus) {
                ProductShiefDto pdto = map.get(Long.valueOf(String.valueOf(object[0])));
                if (pdto != null) {
                    if ("0".equals(object[1].toString())) {
                        pdto.setSaleFlag(SaleFlagEnum.UNSHELF.getCode());
                    } else {
                        pdto.setSaleFlag(SaleFlagEnum.SHELF.getCode());
                    }
                }
            }
            List<Object[]> listPrice = activityStockDao.findByProductIds(listIds);
            Map<Long, Long> highPriceMap = new HashMap<Long, Long>();
            Map<Long, Long> lowPriceMap = new HashMap<Long, Long>();
            for (Object[] object : listPrice) {
                Long productId = Long.valueOf(String.valueOf(object[0]));
                Long lowPrice = object[1] == null ? 0L : WRWUtil.objToLong(object[1]);
                Long highPrice = object[2] == null ? 0L : WRWUtil.objToLong(object[2]);
                if (null == highPriceMap.get(productId) || highPriceMap.get(productId) < highPrice) {
                    highPriceMap.put(productId, highPrice);
                }
                if (null == lowPriceMap.get(productId) || lowPriceMap.get(productId) > lowPrice) {
                    lowPriceMap.put(productId, lowPrice);
                }

            }
            for (Long pid : highPriceMap.keySet()) {
                ProductShiefDto pdto = map.get(pid);
                if (pdto != null) {
                    Long lowPrice = lowPriceMap.get(pid);
                    Long highPrice = highPriceMap.get(pid);
                    if (!lowPrice.equals(0L) && !lowPrice.equals(highPrice)) {
                        pdto.setPrice(WRWUtil.transFenToYuan2String(lowPrice) + "~"
                                + WRWUtil.transFenToYuan2String(highPrice));
                    } else if (!lowPrice.equals(0L) && lowPrice.equals(highPrice)) {
                        pdto.setPrice(WRWUtil.transFenToYuan2String(lowPrice));
                    }
                }
            }
            List<Object[]> listCateName = priceDao.findCateNameByProductIds(listIds);
            for (Object[] object : listCateName) {
                ProductShiefDto pdto = map.get(Long.valueOf(String.valueOf(object[0])));
                if (pdto != null) {
                    if (object[2] != null && StringUtils.isNotEmpty(object[2].toString())) {
                        pdto.setCateName(object[2].toString() + "-" + object[1].toString());
                    } else if (object[1] != null) {
                        pdto.setCateName(object[1].toString());
                    } else {
                        pdto.setCateName(null);
                    }
                }
            }
        }
        if (CollectionUtils.isNotEmpty(orgIds)) {
            List<SOrgEntity> orgs = sOrgDao.findByOrgId(orgIds);
            Map<Long, SOrgEntity> orgMap = new HashMap<Long, SOrgEntity>();
            for (SOrgEntity entity : orgs) {
                if (orgMap.get(entity.getOrgId()) == null) {
                    orgMap.put(entity.getOrgId(), entity);
                }
            }
            for (ProductShiefDto dto : list) {
                if (orgMap.get(dto.getOrgId()) != null) {
                    dto.setOrgName(orgMap.get(dto.getOrgId()).getOrgName());
                }
            }
        }
        return list;
    }

    @Override
    public ProductPriceDto findShiefByProductId(Long productId) {
        PProductEntity entity = pProductDao.findOne(productId);

        ProductPriceDto ppDto = ConverterService.convert(entity, new ProductPriceDto());
        List<RegionPriceDto> rplist = new ArrayList<RegionPriceDto>();
        Map<Integer, RegionPriceDto> map = new LinkedHashMap<Integer, RegionPriceDto>();
        List<Object[]> list = priceDao.findPriceIdByProductId(productId);
        for (Object[] object : list) {
            RegionPriceDto df = new RegionPriceDto();
            df.setRegionId((Integer) object[2]);
            df.setRegionName(object[5].toString());
            RegionPriceDto dto = new RegionPriceDto();
            dto.setRegionId(object[0] == null ? null : (Integer) object[0]);
            dto.setRegionName(object[1] == null ? null : object[1].toString());
            dto.setParentId(object[2] == null ? null : (Integer) object[2]);
            dto.setLevelType(object[3] == null ? null : (Integer) object[3]);
            dto.setSaleFlag(object[4] == null ? "0" : object[4].toString());
            if ((!dto.getSaleFlag().equals(SaleFlagEnum.UNSHELF.getCode())) && StringUtils.isEmpty(ppDto.getSaleTime())) {
                ppDto.setSaleTime(object[6] == null ? null : object[6].toString().substring(0, 10));
                ppDto.setUnSaleTime(object[7] == null ? null : object[7].toString().substring(0, 10));
            }
            if (df.getRegionId().equals(0)) {
                dto.setRegionName(object[5].toString());
                map.put(dto.getRegionId(), dto);
            } else {
                if (map.containsKey(dto.getParentId())) {
                    map.get(dto.getParentId()).getChildrenList().add(dto);
                } else {
                    df.getChildrenList().add(dto);
                    map.put(df.getRegionId(), df);
                }
            }
        }
        for (Map.Entry<Integer, RegionPriceDto> entry : map.entrySet()) {
            rplist.add(entry.getValue());
        }
        ppDto.setList(rplist);
        return ppDto;
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> productShief(ProductDeliveryShiefDto dto) {
        String msg = "操作成功!";
        PProductEntity entity = pProductDao.findOne(dto.getProductId());
        if (entity == null) {
            throw new WRWException("数据错误,查不到此商品!");
        }
        List<ProductDeliveryShiefDetailDto> list = dto.getList();
        if (list != null && list.size() > 0) {
            List<PShiefEntity> listEntity = new ArrayList<PShiefEntity>(list.size());
            List<Integer> regionIds = new ArrayList<Integer>();
            for (ProductDeliveryShiefDetailDto psdDto : list) {
                regionIds.add(psdDto.getRegionId());
            }
            // 通过商品Id和productId查询出所有的shief
            List<PShiefEntity> listPriceEntity = priceDao.findByProductIdAndRegionIds(dto.getProductId(), regionIds);

            Map<Integer, PShiefEntity> map = new HashMap<Integer, PShiefEntity>();
            for (PShiefEntity pPriceEntity : listPriceEntity) {
                map.put(pPriceEntity.getRegionId(), pPriceEntity);
            }
            Long curUserId = AuthorityContext.getCurrentUser().getUserId();
            for (ProductDeliveryShiefDetailDto psdDto : list) {
                PShiefEntity pinfo = map.get(psdDto.getRegionId());
                if (pinfo != null) { // 更新
                    if (!pinfo.getProductId().equals(dto.getProductId())) {
                        throw new WRWException("操作失败,数据错误!");
                    }
                    if (DateTimeUtil.getDateBegin(dto.getStartDate()).before(new Date())) {
                        pinfo.setSaleFlag(SaleFlagEnum.SHELF.getCode());
                    } else {
                        pinfo.setSaleFlag(SaleFlagEnum.TO_SHELF.getCode());
                    }
                    pinfo.setShiefDate(DateTimeUtil.getDateBegin(dto.getStartDate()));
                    if (StringUtils.isNotEmpty(dto.getEndDate())) {
                        pinfo.setUnShiefDate(DateTimeUtil.getDateEnd(dto.getEndDate()));
                    } else {
                        pinfo.setUnShiefDate(null);
                    }
                    pinfo.setModifyDate(new Date());
                    pinfo.setModifyId(curUserId);
                    listEntity.add(pinfo);
                } else {
                    pinfo = new PShiefEntity();
                    pinfo.setProductId(dto.getProductId());
                    pinfo.setRegionId(psdDto.getRegionId());
                    if (DateTimeUtil.getDateBegin(dto.getStartDate()).before(new Date())) {
                        pinfo.setSaleFlag(SaleFlagEnum.SHELF.getCode());
                    } else {
                        pinfo.setSaleFlag(SaleFlagEnum.TO_SHELF.getCode());
                    }
                    pinfo.setShiefDate(DateTimeUtil.getDateBegin(dto.getStartDate()));
                    if (StringUtils.isNotEmpty(dto.getEndDate())) {
                        pinfo.setUnShiefDate(DateTimeUtil.getDateEnd(dto.getEndDate()));
                    } else {
                        pinfo.setUnShiefDate(null);
                    }
                    pinfo.setCreateDate(new Date());
                    pinfo.setCreateId(curUserId);
                    pinfo.setModifyDate(new Date());
                    pinfo.setModifyId(curUserId);
                    listEntity.add(pinfo);
                }
            }

            // 将那些有可能是下架了的城市给删除掉。
            priceDao.deleteByRegionIdsAndProductId(dto.getProductId(), regionIds);
            priceDao.save(listEntity);
        } else {
            priceDao.deleteByProductId(dto.getProductId());
            msg = "商品下架成功!";
        }

        return ResultDtoFactory.toAck(msg);
    }

    @Override
    public ResultDto<String> checkProduct(Long id) {

        PProductEntity entity = pProductDao.findOne(id);
        if (entity == null) {
            throw new WRWException("数据错误,查不到此商品!");
        }
        // if(StringUtils.isEmpty(entity.getDescription())) {
        // return ResultDtoFactory.toNack("请补全商品信息后再进行上架操作");
        // }
        if (ProductTypeEnum.SERVICE.getKey().equals(entity.getTypeId())) {
            List<ActivityStock> stocks = activityStockDao.findByActId(id);
            if (null == stocks || stocks.isEmpty()) {
                return ResultDtoFactory.toNack("请补全商品信息后再进行上架操作");
            }
            List<ActivitySpec> spec = activitySpecDao.findByStockId(stocks.get(0).getId());
            if (null == spec || spec.isEmpty()) {
                return ResultDtoFactory.toNack("请补全商品信息后再进行上架操作");
            }
            ActivityConstranint activityCon = activityConstranintDao.findByProductId(id);
            if (null == activityCon) {
                return ResultDtoFactory.toNack("请补全商品信息后再进行上架操作");
            }
        }
        return ResultDtoFactory.toAck("");
    }

    @Override
    public void clearHomePageDataCache() {
    }

}
