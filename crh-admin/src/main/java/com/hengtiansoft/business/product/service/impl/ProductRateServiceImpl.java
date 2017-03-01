package com.hengtiansoft.business.product.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.business.product.dto.ProductRateDto;
import com.hengtiansoft.business.product.dto.ProductRatePageDto;
import com.hengtiansoft.business.product.dto.ProductSaveDto;
import com.hengtiansoft.business.product.service.ProductRateService;
import com.hengtiansoft.business.product.service.ProductService;
import com.hengtiansoft.wrw.dao.MOrderFeedbackDao;

@Service
public class ProductRateServiceImpl implements ProductRateService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ProductService productService;

    @Autowired
    private MOrderFeedbackDao mOrderFeedbackDao;

    @Override
    public String getProductName(Long id) {
        ProductSaveDto dto = productService.findById(id);
        return dto.getProductName();
    }

    @Override
    public Double getAvgStar(Long id) {
        Double avg = mOrderFeedbackDao.countAvgStarByProductId(id);
        if (null == avg) {
            return null;
        }
        return new BigDecimal(avg).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    @Override
    public Long getAllRateCount(Long id) {
        return mOrderFeedbackDao.countByProductId(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void search(ProductRatePageDto dto) {
        StringBuilder sqlDataBuilder = new StringBuilder();

        Map<String, Object> param = new HashMap<String, Object>();

        sqlDataBuilder
                .append("SELECT f.FEEDBACK_ID,CUST_NAME,login_id,f.create_date,feed_info,star,f.img_urls FROM m_order_feedback f ")
                .append("left join p_product p on p.PRODUCT_ID=f.PRODUCT_ID ")
                .append("left join m_member m on f.member_id = m.id ").append("where f.PRODUCT_ID = :productId ");

        String orderString = " order by f.CREATE_DATE desc";
        param.put("productId", dto.getProductNo());
        Query query = entityManager.createNativeQuery(sqlDataBuilder.append(orderString).toString());
        QueryUtil.processParamForQuery(query, param);

        Long totalRecord = mOrderFeedbackDao.countByProductId(dto.getProductNo());

        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        dto.setTotalRecord(totalRecord.longValue());
        dto.setTotalPages(totalRecord.intValue() % dto.getPageSize() == 0 ? totalRecord.intValue() / dto.getPageSize()
                : ((totalRecord.intValue() / dto.getPageSize()) + 1));
        dto.setList(buildsearchDto(query.getResultList()));
    }

    private List<ProductRateDto> buildsearchDto(List<Object[]> resultList) {
        List<ProductRateDto> dtos = new ArrayList<ProductRateDto>();
        for (Object[] array : resultList) {
            ProductRateDto dto = new ProductRateDto();
            dto.setFeedbackId(array[0].toString());
            dto.setUserName(array[1] == null ? null : array[1].toString());
            dto.setTelnum(array[2] == null ? null : array[2].toString());
            dto.setCreateDate(array[3] == null ? null : array[3].toString().substring(0, 10));
            dto.setFeedInfo(array[4].toString());
            dto.setStar(array[5].toString());
            dto.setImgUrl(array[6] == null ? null : array[6].toString());

            dtos.add(dto);
        }
        return dtos;
    }
}
