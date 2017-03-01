package com.hengtiansoft.business.finance.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.finance.dto.ProductDto;
import com.hengtiansoft.business.finance.dto.ProductPageDto;
import com.hengtiansoft.business.finance.service.CostPriceManageService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.wrw.dao.PProductDao;
import com.hengtiansoft.wrw.entity.PProductEntity;

/**
 * Class Name: CostPriceManageServiceImpl
 * Description: TODO
 * 
 * @author chenghongtu
 */
@Service
public class CostPriceManageServiceImpl implements CostPriceManageService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PProductDao   pProductDao;

    @SuppressWarnings("unchecked")
    @Override
    public void searchProduct(ProductPageDto productPageDto) {
        StringBuilder sqlDataBuilder = new StringBuilder(); // 详细分页数据
        StringBuilder sqlCountBuilder = new StringBuilder(); // 统计总数
        sqlDataBuilder
                .append("SELECT P.PRODUCT_ID,P.PRODUCT_CODE,P.PRODUCT_NAME,B.BRAND_NAME,C.CATE_NAME,P.COST_PRICE " + " FROM P_PRODUCT P "
                        + " INNER JOIN P_BRAND B ON P.BRAND_ID = B.BRAND_ID " + " INNER JOIN P_CATEGORY C ON P.CATE_ID = C.CATE_ID "
                        + " WHERE P.STATUS = 1 ");

        sqlCountBuilder.append("SELECT count(P.PRODUCT_ID) " + " FROM P_PRODUCT P " + " INNER JOIN P_BRAND B ON P.BRAND_ID = B.BRAND_ID "
                + " INNER JOIN P_CATEGORY C ON P.CATE_ID = C.CATE_ID " + " WHERE P.STATUS = 1 ");

        StringBuffer conditionBuffer = new StringBuffer(); // 公用条件condition
        // 商品名称条件
        if (StringUtils.isNotEmpty(productPageDto.getProductName())) {
            conditionBuffer.append(" AND P.PRODUCT_NAME LIKE ").append("'%" + productPageDto.getProductName() + "%'");
        }
        // 品牌条件
        if (!StringUtils.isEmpty(productPageDto.getBrandId())) {
            conditionBuffer.append(" AND P.BRAND_ID =  ").append(productPageDto.getBrandId());
        }
        // 分类条件
        if (!StringUtils.isEmpty(productPageDto.getCateId())) {
            conditionBuffer.append(" AND P.CATE_ID  = ").append(productPageDto.getCateId());
        }

        Query query = entityManager.createNativeQuery(sqlDataBuilder.append(conditionBuffer).append(" ORDER BY P.PRODUCT_NAME ").toString());
        Query countQuery = entityManager.createNativeQuery(sqlCountBuilder.append(conditionBuffer).toString());

        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(productPageDto.getPageSize() * (productPageDto.getCurrentPage() - 1));
        query.setMaxResults(productPageDto.getPageSize());
        productPageDto.setTotalRecord(totalRecord.longValue());
        productPageDto.setTotalPages(totalRecord.intValue() % productPageDto.getPageSize() == 0 ? totalRecord.intValue()
                / productPageDto.getPageSize() : ((totalRecord.intValue() / productPageDto.getPageSize()) + 1));
        productPageDto.setList(bulidProductDto(query.getResultList()));
    }

    private List<ProductDto> bulidProductDto(List<Object[]> resultList) {
        List<ProductDto> content = new ArrayList<ProductDto>();
        for (Object[] array : resultList) {
            ProductDto product = new ProductDto();
            if (null != array[0]) {
                product.setProductId(Long.valueOf(array[0].toString()));
            }
            if (null != array[1]) {
                product.setProductCode(array[1].toString());
            }
            if (null != array[2]) {
                product.setProductName(array[2].toString());
            }
            if (null != array[3]) {
                product.setBrandName(array[3].toString());
            }
            if (null != array[4]) {
                product.setCateName(array[4].toString());
            }
            if (null != array[5]) {
                product.setCostPrice(Long.valueOf(array[5].toString()));
            }
            content.add(product);
        }
        return content;
    }

    @Transactional
    @Override
    public ResultDto<String> updateCostPrice(List<ProductDto> productDtoList) {
        for (int i = 0; i < productDtoList.size(); i++) {
            PProductEntity pProductEntity = pProductDao.findOne(productDtoList.get(i).getProductId());
            pProductEntity.setCostPrice(productDtoList.get(i).getCostPrice());
            pProductDao.save(pProductEntity);
        }
        return ResultDtoFactory.toAck("保存成功！", "");
    }

}
