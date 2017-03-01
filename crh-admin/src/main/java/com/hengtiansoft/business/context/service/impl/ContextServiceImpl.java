package com.hengtiansoft.business.context.service.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
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

import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.business.context.dto.ActImagDto;
import com.hengtiansoft.business.context.dto.ActStockIdDto;
import com.hengtiansoft.business.context.dto.ImageListDto;
import com.hengtiansoft.business.context.dto.SearchActImaDto;
import com.hengtiansoft.business.context.dto.StockImageDto;
import com.hengtiansoft.business.context.service.ContextService;
import com.hengtiansoft.business.product.service.ProductService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.ActImageRecordDao;
import com.hengtiansoft.wrw.dao.ActivityStockDao;
import com.hengtiansoft.wrw.dao.PCategoryDao;
import com.hengtiansoft.wrw.dao.PProductDao;
import com.hengtiansoft.wrw.entity.ActImageRecordEntity;
import com.hengtiansoft.wrw.entity.PCategoryEntity;
import com.hengtiansoft.wrw.entity.PProductEntity;

@Service
public class ContextServiceImpl implements ContextService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ContextServiceImpl.class);
    
    @Autowired
    private PProductDao pProductDao;
    @Autowired
    private ActivityStockDao activityStockDao;
    @Autowired
    private ActImageRecordDao actImageRecordDao;
    @Autowired
    private ProductService productService;
    @Autowired
    private PCategoryDao pCategoryDao;
    @Autowired
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public void findList(final SearchActImaDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder conditionSql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");
        StringBuilder sql = new StringBuilder("SELECT A.PRODUCT_ID, A.ACT_DATE, A.ID "
                + " FROM ACT_STOCK A JOIN P_PRODUCT P ON A.PRODUCT_ID = P.PRODUCT_ID WHERE 1=1 ");
        countSql.append("SELECT COUNT(1) FROM (").append(sql);
        // 商品名称
        if (!WRWUtil.isEmpty(dto.getActName())) {
            String msg = dto.getActName();
            char escape = '\\';
                msg =msg.replace("\\", escape+"\\");
                msg =msg.replace("%", escape+"%");
                msg =msg.replace("_", escape+"_");
            conditionSql.append(" AND REPLACE(P.PRODUCT_NAME,' ','') LIKE REPLACE(:NAME,' ','') ");
                param.put("NAME", "%"+msg+"%");   
        }
        // 商品编号
        if (!WRWUtil.isEmpty(dto.getActCode())) {
            String msg = dto.getActCode();
            char escape = '\\';
                msg =msg.replace("\\", escape+"\\");
                msg =msg.replace("%", escape+"%");
                msg =msg.replace("_", escape+"_");
            conditionSql.append(" AND REPLACE(P.PRODUCT_CODE,' ','') LIKE REPLACE(:CODE,' ','') ");
                param.put("CODE", "%"+msg+"%");   
        }
        // 活动日期
        if (null == dto.getStartDate() || "".equals(dto.getStartDate())) {
            conditionSql.append(" AND A.ACT_DATE <= :DATE ");
            if (null != dto.getEndDate() && !"".equals(dto.getEndDate())) {
                param.put("DATE", DateTimeUtil.getDateEnd(dto.getEndDate()));
            } else {
                param.put("DATE", DateTimeUtil.getDayEnd());
            }
        }
        if (null != dto.getStartDate() && !"".equals(dto.getStartDate())) {
            conditionSql.append(" AND A.ACT_DATE >= :BDATE ");
            param.put("BDATE", DateTimeUtil.getDateBegin(dto.getStartDate()));
            conditionSql.append(" AND A.ACT_DATE <= :EDATE ");
            if (null != dto.getEndDate() && !"".equals(dto.getEndDate())) {
                param.put("EDATE", DateTimeUtil.getDateEnd(dto.getEndDate()));
            } else {
                param.put("EDATE", DateTimeUtil.getDayEnd());
            }
        }
        conditionSql.append(" ORDER BY A.ACT_DATE DESC, A.CREATE_DATE DESC ");
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
        dto.setList(buildActImageDto(query.getResultList(), dto.getCurrentPage(), dto.getPageSize()));
    }

    private List<ActImagDto> buildActImageDto(List<Object[]> entities, Integer currentPage, Integer pageSize) {
        List<ActImagDto> actImagDtos = new ArrayList<ActImagDto>();
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-M-d");
        for (int i = 0; i < entities.size(); i++) {
            ActImagDto actImagDto = new ActImagDto();
            PProductEntity product = pProductDao.findByProductId(WRWUtil.objToLong(entities.get(i)[0]));
            if (product != null) {
                if ("0".equals(product.getTypeId().toString())) {
                    actImagDto.setIndex((currentPage - 1) * pageSize + i + 1 + "");
                    actImagDto.setDescription(product.getProductCode() + "#&#" + product.getProductName() + "#&#" + spf.format((Date) entities.get(i)[1]));
                    actImagDto.setActName(product.getProductName());
                    PCategoryEntity categoryEntity = pCategoryDao.findOne(product.getCateId());
                    actImagDto.setCateName(categoryEntity == null ? "出错啦" : categoryEntity.getCateName());
                    actImagDto.setProductId(WRWUtil.objToLong(entities.get(i)[2]));
                    actImagDto.setStartDate(spf.format((Date) entities.get(i)[1]));
                    List<ActImageRecordEntity> actImageRecords = actImageRecordDao.findByTypeAndStockId("1", WRWUtil.objToLong(entities.get(i)[2]));
                    List<String> imageList = new ArrayList<String>();
                    for (ActImageRecordEntity actImageRecord : actImageRecords) {
                        imageList.add(actImageRecord.getUrl() + ";" + WRWUtil.objToLong(entities.get(i)[2]));
                    }
                    actImagDto.setStatus(WRWUtil.objToLong(entities.get(i)[2]) + ";" + imageList.size());
                    actImagDto.setImageList(imageList);
                    actImagDtos.add(actImagDto);
                }
            }
        }
        return actImagDtos;
    }

    @Override
    @Transactional
    public ResultDto<?> uploadActImag(Long actStockId, String imageUrl) {
        try {
            actImageRecordDao.addActivityImage(actStockId, imageUrl);
            return ResultDtoFactory.toAck("上传成功！");
        } catch (Exception e) {
            LOGGER.error("上传出现异常----------{}", e.toString());
            return ResultDtoFactory.toNack("上传失败！");
        }
    }

    @Override
    public SearchActImaDto getInfoByStockId(Long stockId) {
        SearchActImaDto dto = new SearchActImaDto();
        PProductEntity product = productService.findByActStockId(stockId);
        if (product != null) {
            PCategoryEntity category = pCategoryDao.findOne(product.getCateId());
            dto.setCateName(category == null ? "品类获取失败！" : category.getCateName());
            dto.setActName(product.getProductName());
            dto.setStartDate(new SimpleDateFormat("yyyy-M-d").format(activityStockDao.findOne(stockId).getActDate()));
            dto.setProductId(stockId);
        } else {
            dto.setCateName("出错啦！");
            dto.setActName("出错啦！");
            dto.setStartDate("出错啦！");
            dto.setProductId(stockId);
        }
        return dto;
    }

    @Override
    public void findImages(final ActStockIdDto dto) {
        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.DESC,
                "createDate"));
        Page<ActImageRecordEntity> page = actImageRecordDao.findAll(new Specification<ActImageRecordEntity>() {
            @Override
            public Predicate toPredicate(Root<ActImageRecordEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.<Long> get("actStockId"), dto.getActStockId()));
                predicates.add(cb.equal(root.<String> get("status"), "1"));
                predicates.add(cb.equal(root.<String> get("type"), "1"));
                
                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }
        }, pageable);
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalRecord(page.getTotalElements());
        dto.setList(buildImageListDto(page.getContent()));
    }

    private List<ImageListDto> buildImageListDto(List<ActImageRecordEntity> content) {
        List<ImageListDto> imageListDtos = new ArrayList<ImageListDto>();
        List<StockImageDto> stockImageList = new ArrayList<StockImageDto>();
        for (ActImageRecordEntity actImageRecord : content) {
            StockImageDto stockImageDto = new StockImageDto();
            stockImageDto.setImageUrl(actImageRecord.getUrl());
            stockImageDto.setActImageRecordId(actImageRecord.getId());
            stockImageList.add(stockImageDto);
            if (stockImageList.size() == 5) {
                ImageListDto imageListDto = new ImageListDto();
                List<StockImageDto> newStockImageList = stockImageList;
                imageListDto.setStockImageDtos(newStockImageList);
                imageListDtos.add(imageListDto);
                stockImageList = new ArrayList<StockImageDto>();
            }
        }
        if (!stockImageList.isEmpty()) {
            ImageListDto imageListDto = new ImageListDto();
            imageListDto.setStockImageDtos(stockImageList);
            imageListDtos.add(imageListDto);
        }
        return imageListDtos;
    }

    @Override
    @Transactional
    public ResultDto<?> deleteImages(List<Long> actImageRecordId) {
        try {
            actImageRecordDao.updateStatus("0", actImageRecordId);
            return ResultDtoFactory.toAck("删除成功");
        } catch (Exception e) {
            LOGGER.error("删除出现错误！---------{}", e.toString());
            return ResultDtoFactory.toNack("删除失败！请稍后再试");
        }
    }

    @Override
    @Transactional
    public ResultDto<?> uploadActImags(Long actStockId, List<String> imageUrls) {
        try {
            List<ActImageRecordEntity> actImageRecords = new ArrayList<ActImageRecordEntity>();
            for (String imageUrl : imageUrls) {
                ActImageRecordEntity actImageRecord = new ActImageRecordEntity();
                actImageRecord.setActStockId(actStockId);
                actImageRecord.setCreateDate(new Date());
                actImageRecord.setStatus("1");
                actImageRecord.setType("1");
                actImageRecord.setUrl(imageUrl);
                actImageRecords.add(actImageRecord);
            }
            actImageRecordDao.save(actImageRecords);
            return ResultDtoFactory.toAck("上传成功！");
        } catch (Exception e) {
            LOGGER.error("上传出现异常----------{}", e.toString());
            return ResultDtoFactory.toNack("上传失败！");
        }
    }
}
