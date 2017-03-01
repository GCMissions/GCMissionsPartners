package com.hengtiansoft.business.marketing.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.business.marketing.dto.ProductRebateDto;
import com.hengtiansoft.business.marketing.dto.ProductRebatePageDto;
import com.hengtiansoft.business.marketing.dto.RebateDto;
import com.hengtiansoft.business.marketing.dto.RebateProvinceDto;
import com.hengtiansoft.business.marketing.service.ProductRebateService;
import com.hengtiansoft.common.authority.AuthorityContext;
//import com.hengtiansoft.wrw.dao.PPriceDao;
import com.hengtiansoft.wrw.entity.PShiefEntity;
import com.hengtiansoft.wrw.enums.SaleFlagEnum;

/**
 * Class Name: ProductRebateServiceImpl Description: 返利
 * 
 * @author chenghongtu
 */
@Service
public class ProductRebateServiceImpl implements ProductRebateService {

    private static final Long BRAND_ID_FINLA = 0L;

    @PersistenceContext
    private EntityManager     entityManager;

//    @Autowired
//    private PPriceDao         pPriceDao;

    @SuppressWarnings("unchecked")
    @Override
    public void search(ProductRebatePageDto dto) {

        StringBuilder sqlDataBuilder = new StringBuilder(); // 详细分页数据
        StringBuilder sqlCountBuilder = new StringBuilder(); // 统计总数
        Map<String, Object> map = new HashMap<String, Object>();
        sqlDataBuilder.append("SELECT DISTINCT(R.PRODUCT_ID),P.PRODUCT_CODE,P.PRODUCT_NAME,C.CATE_NAME,B.BRAND_NAME FROM P_PRICE R"
                + " INNER JOIN P_PRODUCT P ON P.PRODUCT_ID = R.PRODUCT_ID" + " LEFT JOIN P_CATEGORY C ON P.CATE_ID = C.CATE_ID"
                + " LEFT JOIN P_BRAND B ON P.BRAND_ID = B.BRAND_ID WHERE R.SALE_FLAG > :saleFlag ");

        sqlCountBuilder.append("SELECT COUNT(DISTINCT(R.PRODUCT_ID)) FROM P_PRICE R" + " INNER JOIN P_PRODUCT P ON P.PRODUCT_ID = R.PRODUCT_ID"
                + " LEFT JOIN P_CATEGORY C ON P.CATE_ID = C.CATE_ID"
                + " LEFT JOIN P_BRAND B ON P.BRAND_ID = B.BRAND_ID WHERE R.SALE_FLAG > :saleFlag ");

        StringBuffer conditionBuffer = new StringBuffer(); // 公用条件condition
        // 商品名称条件
        if (StringUtils.isNotEmpty(dto.getProductName())) {
            conditionBuffer.append(" AND P.PRODUCT_NAME LIKE :productName ");
            map.put("productName", "%" + dto.getProductName() + "%");
        }
        if (StringUtils.isNotEmpty(dto.getProductCode())) {
            conditionBuffer.append(" AND P.PRODUCT_CODE = :productCode ");
            map.put("productCode", dto.getProductCode());
        }
        // 品牌条件
        if (null != dto.getBrandId()) {
            // 判断brandId传入的值 是0代表无品牌
            if (BRAND_ID_FINLA.equals(dto.getBrandId())) {
                conditionBuffer.append(" AND P.BRAND_ID IS NULL ");
            } else {
                conditionBuffer.append(" AND P.BRAND_ID = :brandId ");
                map.put("brandId", dto.getBrandId());
            }

        }
        // 分类条件
        if (null != dto.getCateId() && !dto.getCateId().equals(0L)) {
            conditionBuffer.append(" AND P.CATE_ID  = :cateId ");
            map.put("cateId", dto.getCateId());
        }
        map.put("saleFlag", SaleFlagEnum.UNSHELF.getCode());

        Query query = entityManager.createNativeQuery(sqlDataBuilder.append(conditionBuffer).append(" ORDER BY P.PRODUCT_NAME ").toString());
        Query countQuery = entityManager.createNativeQuery(sqlCountBuilder.append(conditionBuffer).toString());
        QueryUtil.processParamForQuery(query, map);
        QueryUtil.processParamForQuery(countQuery, map);
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        dto.setTotalRecord(totalRecord.longValue());
        dto.setTotalPages(totalRecord.intValue() % dto.getPageSize() == 0 ? totalRecord.intValue() / dto.getPageSize()
                : ((totalRecord.intValue() / dto.getPageSize()) + 1));
        dto.setList(bulidProductRebateDto(query.getResultList()));

    }

    // 封装
    private List<ProductRebateDto> bulidProductRebateDto(List<Object[]> resultList) {
        List<ProductRebateDto> content = new ArrayList<ProductRebateDto>();
        for (Object[] array : resultList) {
            ProductRebateDto pr = new ProductRebateDto();
            if (null != array[0]) {
                pr.setProductId(Long.valueOf(array[0].toString()));
            }
            if (null != array[1]) {
                pr.setProductCode(array[1].toString());
            }
            if (null != array[2]) {
                pr.setProductName(array[2].toString());
            }
            if (null != array[3]) {
                pr.setCateName(array[3].toString());
            }
            if (null != array[4]) {
                pr.setBrandName(array[4].toString());
            }
            content.add(pr);
        }
        return content;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RebateProvinceDto> findOne(Long productId) {
        StringBuilder hql = new StringBuilder();
        hql.append("SELECT DISTINCT(SS.ID),SS.NAME " + " FROM P_PRICE R" + " INNER JOIN S_REGION S ON R.REGION_ID = S.ID"
                + " INNER JOIN S_REGION SS ON S.PARENT_ID = SS.ID" + " WHERE R.PRODUCT_ID =  :productId AND R.SALE_FLAG > :saleFlag");

        Query query = entityManager.createNativeQuery(hql.toString());
        query.setParameter("productId", productId);
        query.setParameter("saleFlag", SaleFlagEnum.UNSHELF.getCode());
        return bulidRebateDto(query.getResultList(), productId);
    }

    private List<RebateProvinceDto> bulidRebateDto(List<Object[]> resultList, Long productId) {
        List<RebateProvinceDto> list = new ArrayList<RebateProvinceDto>();
        for (Object[] array : resultList) {
            RebateProvinceDto p = new RebateProvinceDto();
            if (null != array[0]) {
                p.setRegionId(Long.valueOf(array[0].toString()));
                p.setDetail(searchRebateDto(productId, Long.valueOf(array[0].toString())));
            }
            if (null != array[1]) {
                p.setRegionName(array[1].toString());
            }
            list.add(p);
        }
        RebateProvinceDto p1 = new RebateProvinceDto();
        // 无配送网点城市
        p1.setRegionId(0L);
        p1.setRegionName("其它");
        p1.setDetail(searchRebateDto(productId, 0L));
        list.add(p1);
        return list;
    }

    @SuppressWarnings("unchecked")
    private List<RebateDto> searchRebateDto(Long productId, Long regionId) {
        List<Object[]> resultList = new ArrayList<Object[]>();
        if (null != regionId && regionId.longValue() == 0) {
            StringBuilder strb = new StringBuilder();
            strb.append("SELECT '100000','无配送网点城市',P_REBATE,R.Z_REBATE FROM P_PRICE R "
                    + " WHERE R.PRODUCT_ID = :productId AND R.REGION_ID = :regionId ");
            Query query = entityManager.createNativeQuery(strb.toString());
            query.setParameter("productId", productId);
            query.setParameter("regionId", 100000);
            resultList.addAll(query.getResultList());
        } else {
            StringBuilder hql = new StringBuilder();
            hql.append("SELECT S.ID,S.NAME,P_REBATE,R.Z_REBATE FROM P_PRICE R " + " INNER JOIN S_REGION S ON R.REGION_ID = S.ID"
                    + " WHERE R.PRODUCT_ID = :productId AND S.PARENT_ID = :regionId AND R.SALE_FLAG > :saleFlag ");
            Query query1 = entityManager.createNativeQuery(hql.toString());
            query1.setParameter("productId", productId);
            query1.setParameter("regionId", regionId);
            query1.setParameter("saleFlag", SaleFlagEnum.UNSHELF.getCode());
            resultList.addAll(query1.getResultList());
        }

        return bulidRebateDto(resultList);
    }

    private List<RebateDto> bulidRebateDto(List<Object[]> resultList) {
        List<RebateDto> list = new ArrayList<RebateDto>();
        for (Object[] array : resultList) {
            RebateDto rebate = new RebateDto();
            if (null != array[0]) {
                rebate.setCityId(Integer.valueOf(array[0].toString()));
            }
            if (null != array[1]) {
                rebate.setCityName(array[1].toString());
            }
            if (null != array[2]) {
                rebate.setpRebate(Double.valueOf(array[2].toString()));
            }
            if (null != array[3]) {
                rebate.setzRebate(Double.valueOf(array[3].toString()));
            }
            list.add(rebate);
        }
        return list;
    }

    @Override
    @Transactional
    public void save(RebateProvinceDto rebateProvinceDto) {
        if (null != rebateProvinceDto.getDetail()) {
            for (int i = 0; i < rebateProvinceDto.getDetail().size(); i++) {
                Integer regionId = rebateProvinceDto.getDetail().get(i).getCityId();
                Long productId = rebateProvinceDto.getDetail().get(i).getProductId();
//                PShiefEntity entity = pPriceDao.findByProductIdAndRegionId(productId, regionId);
//                entity.setpRebate(rebateProvinceDto.getDetail().get(i).getpRebate());
//                entity.setzRebate(rebateProvinceDto.getDetail().get(i).getzRebate());
//                entity.setModifyDate(new Date());
//                entity.setModifyId(AuthorityContext.getCurrentUser().getUserId());
//                pPriceDao.save(entity);
            }
        }

    }

}
