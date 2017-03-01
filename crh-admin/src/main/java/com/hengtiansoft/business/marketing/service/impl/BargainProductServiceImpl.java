package com.hengtiansoft.business.marketing.service.impl;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.business.marketing.dto.BargainChartDto;
import com.hengtiansoft.business.marketing.dto.BargainChartPageDto;
import com.hengtiansoft.business.marketing.dto.BargainDto;
import com.hengtiansoft.business.marketing.dto.BargainPageDto;
import com.hengtiansoft.business.marketing.dto.BargainSaveAndEditDto;
import com.hengtiansoft.business.marketing.service.BargainProductService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.enumeration.DelFlagEnum;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.BasicUtil;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.ExcleUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.BargainProductDao;
import com.hengtiansoft.wrw.entity.BargainProductEntity;
import com.hengtiansoft.wrw.enums.BargainStatisticType;
import com.hengtiansoft.wrw.enums.BargainStatusEnum;
import com.hengtiansoft.wrw.enums.BargainTypeEnum;

/**
 * 
 * Class Name: BargainProductServiceImpl Description: TODO
 * 
 * @author chenghongtu
 *
 */
@Service
public class BargainProductServiceImpl implements BargainProductService {

    @Autowired
    private BargainProductDao bargainProductDao;

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public void search(final BargainPageDto dto) {

        StringBuilder sqlDataBuilder = new StringBuilder(); // 详细分页数据
        StringBuilder sqlCountBuilder = new StringBuilder(); // 统计总数
        Map<String, Object> map = new HashMap<String, Object>();
        sqlDataBuilder
                .append("SELECT p.ID,p.PRODUCT_NAME,p.CREATE_DATE,p.EFFECTIVE_START_DATE,p.EFFECTIVE_END_DATE,ifnull(s.PLAY_TOTAL,0)+ifnull(p.PLAYER_TOTAL,0),p.PRICE,p.BASE_PRICE,p.`STATUS` "
                        + " FROM bargain_product p left join ( "
                        + " SELECT COUNT(*) PLAY_TOTAL,bs.BARGAIN_ID "
                        + " FROM bargain_statistic bs "
                        + " WHERE bs.`TYPE`= :type "
                        + " GROUP BY bs.BARGAIN_ID) s "
                        + " on p.ID=s.BARGAIN_ID WHERE p.DEL_FLAG = :delFlag ");

        sqlCountBuilder
                .append("select count(*) from (SELECT p.ID,p.PRODUCT_NAME,p.CREATE_DATE,p.EFFECTIVE_START_DATE,p.EFFECTIVE_END_DATE,ifnull(s.PLAY_TOTAL,0)+ifnull(p.PLAYER_TOTAL,0),p.PRICE,p.BASE_PRICE,p.`STATUS` "
                        + " FROM bargain_product p left join ( "
                        + " SELECT COUNT(*) PLAY_TOTAL,bs.BARGAIN_ID "
                        + " FROM bargain_statistic bs "
                        + " WHERE bs.`TYPE`= :type "
                        + " GROUP BY bs.BARGAIN_ID) s "
                        + " on p.ID=s.BARGAIN_ID WHERE p.DEL_FLAG = :delFlag ");

        StringBuffer conditionBuffer = new StringBuffer(); // 公用条件condition
        map.put("delFlag", DelFlagEnum.UN_DEL.getCode());
        // 参与人
        map.put("type", BargainStatisticType.PLAY.getKey());
        if (!BasicUtil.isEmpty(dto.getStatus())) {
            conditionBuffer.append(" and p.`STATUS`= :status ");
            map.put("status", dto.getStatus());
        }

        if (BasicUtil.isEmpty(dto.getStartDate())) {
            if (!BasicUtil.isEmpty(dto.getEndDate())) {
                conditionBuffer.append(" AND P.CREATE_DATE <= :createDate ");
                map.put("createDate", DateTimeUtil.getDateEnd(dto.getEndDate()));
            }

        }
        if (!BasicUtil.isEmpty(dto.getStartDate())) {
            if (BasicUtil.isEmpty(dto.getEndDate())) {
                conditionBuffer.append(" AND P.CREATE_DATE >= :createDate ");
                map.put("createDate", DateTimeUtil.getDateBegin(dto.getStartDate()));
            } else {
                conditionBuffer.append(" AND P.CREATE_DATE BETWEEN :sDate AND :eDate ");
                map.put("sDate", DateTimeUtil.getDateBegin(dto.getStartDate()));
                map.put("eDate", DateTimeUtil.getDateEnd(dto.getEndDate()));
            }
        }
        Query query = entityManager.createNativeQuery(sqlDataBuilder.append(conditionBuffer).append(" order by p.CREATE_DATE desc ").toString());
        Query countQuery = entityManager.createNativeQuery(sqlCountBuilder.append(conditionBuffer).append(" )ps ")
                .toString());
        QueryUtil.processParamForQuery(query, map);
        QueryUtil.processParamForQuery(countQuery, map);
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        dto.setTotalRecord(totalRecord.longValue());
        dto.setTotalPages(totalRecord.intValue() % dto.getPageSize() == 0 ? totalRecord.intValue() / dto.getPageSize()
                : ((totalRecord.intValue() / dto.getPageSize()) + 1));
        dto.setList(buildBargainDtos(query.getResultList()));
    }

    private List<BargainDto> buildBargainDtos(List<Object[]> resultList) {
        List<BargainDto> content = new ArrayList<BargainDto>();
        for (Object[] arr : resultList) {
            BargainDto rpd = new BargainDto();
            if (null != arr[0]) {
                rpd.setId(Long.valueOf(arr[0].toString()));
            }
            if (null != arr[1]) {
                rpd.setProductName(arr[1].toString());
            }
            if (null != arr[2]) {
                rpd.setCreateDate((Date) arr[2]);
            }
            if (null != arr[3]) {
                rpd.setEffectiveStartDate((Date)arr[3]);
            }
            if (null != arr[4]) {
                rpd.setEffectiveEndDate((Date)arr[4]);
            }
            if (null != arr[5]) {
                rpd.setPlayerTotal(Integer.valueOf(arr[5].toString()));
            }
            if (null != arr[6]) {
                rpd.setPrice(Long.valueOf(arr[6].toString()));
            }
            if (null != arr[7]) {
                rpd.setBasePrice(Long.valueOf(arr[7].toString()));
            }
            if (null != arr[8]) {
                rpd.setStatus(BargainStatusEnum.getValue(arr[8].toString()));
            }
            content.add(rpd);
        }
        return content;
    }

    @Override
    public BargainSaveAndEditDto findById(Long id) {
        BargainProductEntity entity = bargainProductDao.findOne(id);
        BargainSaveAndEditDto dto = ConverterService.convert(entity, BargainSaveAndEditDto.class);
        dto.setStatus(BargainStatusEnum.getValue(entity.getStatus()));
        return dto;
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public void delete(Long id) {
        BargainProductEntity entity = bargainProductDao.findOne(id);
        entity.setDelFlag(DelFlagEnum.DEL.getCode());
        bargainProductDao.save(entity);
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public void update(BargainSaveAndEditDto dto) {
        if (WRWUtil.isEmpty(dto.getProductName())) {
            throw new WRWException("商品名称不可为空，请输入!");
        }
        if (null == dto.getPrice()) {
            throw new WRWException("商品原价不可为空，请输入!");
        }
        if (null == dto.getBasePrice()) {
            throw new WRWException("商品底价不可为空，请输入!");
        }
        if (dto.getPrice() <= dto.getBasePrice()) {
            throw new WRWException("商品原价必须大于商品底价!");
        }
        if (null == dto.getEffectiveStartDate() || null == dto.getEffectiveEndDate()) {
            throw new WRWException("砍价有效期不可为空，请选择!");
        }
        if (WRWUtil.isEmpty(dto.getBargainType())) {
            throw new WRWException("砍价金额方式不可为空，请选择!");
        } else if (dto.getBargainType().equals(BargainTypeEnum.RANDOM.getKey())) {
            if (null == dto.getBargainMinAmount() || null == dto.getBargainMaxAmount()) {
                throw new WRWException("随机范围不可为空，请输入!");
            }
        } else if (dto.getBargainType().equals(BargainTypeEnum.FIXED.getKey())) {
            if (null == dto.getBargainAmount()) {
                throw new WRWException("固定金额不可为空，请输入!");
            }
        }
        if (WRWUtil.isEmpty(dto.getBargainImage())) {
            throw new WRWException("微信端列表页图片不可为空，请选择!");
        }
        if (WRWUtil.isEmpty(dto.getDescription())) {
            throw new WRWException("商品详情不可为空，请输入!");
        }
        BargainProductEntity entity = bargainProductDao.findOne(dto.getId());
        entity.setProductName(dto.getProductName());
        entity.setPrice(dto.getPrice());
        entity.setBasePrice(dto.getBasePrice());
        entity.setEffectiveStartDate(dto.getEffectiveStartDate());
        entity.setEffectiveEndDate(dto.getEffectiveEndDate());
        entity.setBargainType(dto.getBargainType());
        if (dto.getBargainType().equals(BargainTypeEnum.RANDOM.getKey())) {
            entity.setBargainMinAmount(dto.getBargainMinAmount());
            entity.setBargainMaxAmount(dto.getBargainMaxAmount());
        } else {
            entity.setBargainAmount(dto.getBargainAmount());
        }
        entity.setBargainImage(dto.getBargainImage());
        entity.setDescription(dto.getDescription());
        entity.setModifyDate(new Date());
        entity.setModifyId(AuthorityContext.getCurrentUser().getUserId());
        bargainProductDao.save(entity);
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public void save(BargainSaveAndEditDto dto) {
        // 验证
        if (WRWUtil.isEmpty(dto.getProductName())) {
            throw new WRWException("商品名称不可为空，请输入!");
        }
        if (null == dto.getPrice()) {
            throw new WRWException("商品原价不可为空，请输入!");
        }
        if (null == dto.getBasePrice()) {
            throw new WRWException("商品底价不可为空，请输入!");
        }
        if (dto.getPrice() <= dto.getBasePrice()) {
            throw new WRWException("商品原价必须大于商品底价!");
        }
        if (null == dto.getEffectiveStartDate() || null == dto.getEffectiveEndDate()) {
            throw new WRWException("砍价有效期不可为空，请选择!");
        }
        if (WRWUtil.isEmpty(dto.getBargainType())) {
            throw new WRWException("砍价金额方式不可为空，请选择!");
        } else if (dto.getBargainType().equals(BargainTypeEnum.RANDOM.getKey())) {
            if (null == dto.getBargainMinAmount() || null == dto.getBargainMaxAmount()) {
                throw new WRWException("随机范围不可为空，请输入!");
            }
        } else if (dto.getBargainType().equals(BargainTypeEnum.FIXED.getKey())) {
            if (null == dto.getBargainAmount()) {
                throw new WRWException("固定金额不可为空，请输入!");
            }
        }
        if (WRWUtil.isEmpty(dto.getBargainImage())) {
            throw new WRWException("微信端列表页图片不可为空，请选择!");
        }
        if (WRWUtil.isEmpty(dto.getDescription())) {
            throw new WRWException("商品详情不可为空，请输入!");
        }

        BargainProductEntity entity = new BargainProductEntity();
        entity.setProductName(dto.getProductName());
        entity.setPrice(dto.getPrice());
        entity.setBasePrice(dto.getBasePrice());
        entity.setEffectiveStartDate(dto.getEffectiveStartDate());
        entity.setEffectiveEndDate(dto.getEffectiveEndDate());
        entity.setBargainType(dto.getBargainType());
        if (dto.getBargainType().equals(BargainTypeEnum.RANDOM.getKey())) {
            entity.setBargainMinAmount(dto.getBargainMinAmount());
            entity.setBargainMaxAmount(dto.getBargainMaxAmount());
        } else {
            entity.setBargainAmount(dto.getBargainAmount());
        }
        entity.setBargainImage(dto.getBargainImage());
        entity.setDescription(dto.getDescription());
        // 新增时赋初始值，由job更新状态值
        entity.setStatus(BargainStatusEnum.NOSTART.getKey());
        entity.setDelFlag(DelFlagEnum.UN_DEL.getCode());
        entity.setCreateDate(new Date());
        entity.setCreateId(AuthorityContext.getCurrentUser().getUserId());
        bargainProductDao.save(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void searchChart(BargainChartPageDto dto) {

        StringBuilder sqlDataBuilder = new StringBuilder(); // 详细分页数据
        StringBuilder sqlCountBuilder = new StringBuilder(); // 统计总数
        Map<String, Object> map = new HashMap<String, Object>();
        sqlDataBuilder
                .append("SELECT r.ORIGIN_NIKENAME, m.LOGIN_ID, p.PRICE,"
                        + " (p.PRICE- IFNULL(rrr.bargain_price,0)), p.BASE_PRICE,r.SHARE_TOTAL, "
                        + " IFNULL(rrr.bargain_num,0)"
                        + ", IFNULL(rrr.bargain_price,0) HAVE_BARGAIN"
                        + " FROM m_member m,bargain_product p,bargain_record r"
                        + " LEFT JOIN ("
                        + " SELECT SUM(rr.bargain_price) bargain_price, COUNT(1)bargain_num,rr.ORIGIN_MEMBER_ID, max(rr.ID) ID "
                        + " FROM (" + " SELECT *" + " FROM bargain_record e" + " WHERE e.BARGAIN_ID = :id"
                        + " ORDER BY e.ID DESC) AS rr" + " WHERE rr.BARGAIN_ID=:id AND rr.IP IS NOT NULL"
                        + " GROUP BY rr.ORIGIN_MEMBER_ID) rrr ON rrr.ORIGIN_MEMBER_ID=r.ORIGIN_MEMBER_ID"
                        + " WHERE r.ORIGIN_MEMBER_ID=m.ID AND r.BARGAIN_ID=p.ID AND r.BARGAIN_ID=:id AND r.IP IS NULL"
                        + " ORDER BY HAVE_BARGAIN DESC,rrr.ID");

        sqlCountBuilder
                .append("SELECT COUNT(*) FROM "
                        + " (SELECT r.ORIGIN_NIKENAME, m.LOGIN_ID, p.PRICE,"
                        + " (p.PRICE- IFNULL(rrr.bargain_price,0)), p.BASE_PRICE,r.SHARE_TOTAL, "
                        + " IFNULL(rrr.bargain_num,0)"
                        + ", IFNULL(rrr.bargain_price,0) HAVE_BARGAIN"
                        + " FROM m_member m,bargain_product p,bargain_record r"
                        + " LEFT JOIN ("
                        + " SELECT SUM(rr.bargain_price) bargain_price, COUNT(1)bargain_num,rr.ORIGIN_MEMBER_ID, max(rr.ID) ID "
                        + " FROM (" + " SELECT *" + " FROM bargain_record e" + " WHERE e.BARGAIN_ID = :id"
                        + " ORDER BY e.ID DESC) AS rr" + " WHERE rr.BARGAIN_ID=:id AND rr.IP IS NOT NULL"
                        + " GROUP BY rr.ORIGIN_MEMBER_ID) rrr ON rrr.ORIGIN_MEMBER_ID=r.ORIGIN_MEMBER_ID"
                        + " WHERE r.ORIGIN_MEMBER_ID=m.ID AND r.BARGAIN_ID=p.ID AND r.BARGAIN_ID=:id AND r.IP IS NULL"
                        + " ORDER BY HAVE_BARGAIN DESC,rrr.ID)rrr");

        StringBuffer conditionBuffer = new StringBuffer(); // 公用条件condition
        map.put("id", dto.getBargainId());

        Query query = entityManager.createNativeQuery(sqlDataBuilder.append(conditionBuffer).toString());
        Query countQuery = entityManager.createNativeQuery(sqlCountBuilder.append(conditionBuffer).toString());
        QueryUtil.processParamForQuery(query, map);
        QueryUtil.processParamForQuery(countQuery, map);
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        dto.setTotalRecord(totalRecord.longValue());
        dto.setTotalPages(totalRecord.intValue() % dto.getPageSize() == 0 ? totalRecord.intValue() / dto.getPageSize()
                : ((totalRecord.intValue() / dto.getPageSize()) + 1));
        dto.setList(bulidBargainChartDto(query.getResultList(), dto));

    }

    private List<BargainChartDto> bulidBargainChartDto(List<Object[]> resultList, BargainChartPageDto dto) {
        List<BargainChartDto> list = new ArrayList<BargainChartDto>();
        for (int i = 0; i < resultList.size(); i++) {
            BargainChartDto chart = new BargainChartDto();
            chart.setRownum((dto.getCurrentPage() - 1) * dto.getPageSize() + i + 1);
            if (null != resultList.get(i)[0]) {
                chart.setBargainName(resultList.get(i)[0].toString());
            }
            if (null != resultList.get(i)[1]) {
                chart.setPhone(resultList.get(i)[1].toString());
            }
            if (null != resultList.get(i)[2]) {
                chart.setPrice(Long.valueOf(resultList.get(i)[2].toString()));
            }
            if (null != resultList.get(i)[3]) {
                //判断现在是否为负数 2016-10-14 涂成红 bug1799
                Long currPrice = Long.valueOf(resultList.get(i)[3].toString());
                if (currPrice < 0L) {
                    currPrice = 0L;
                }
                chart.setCurrentPrice(currPrice);
            }
            if (null != resultList.get(i)[4]) {
                chart.setBasePrice(Long.valueOf(resultList.get(i)[4].toString()));
            }
            if (null != resultList.get(i)[5]) {
                chart.setShareTotal(Integer.valueOf(resultList.get(i)[5].toString()));
            }
            if (null != resultList.get(i)[6]) {
                chart.setBargainNum(Integer.valueOf(resultList.get(i)[6].toString()));
            }
            list.add(chart);
        }
        return list;
    }

    @Override
    public HSSFWorkbook toExcle(BargainChartPageDto dto) {
        ArrayList<String> fieldName = new ArrayList<String>();
        ArrayList<String> sheetName = new ArrayList<String>();
        ArrayList<String> fieldStyle = new ArrayList<String>();
        List<List<Object>> fieldData = new ArrayList<List<Object>>();
        fieldName.add(0, "排名");
        fieldName.add(1, "用户昵称/姓名");
        fieldName.add(2, "手机号");
        fieldName.add(3, "原价");
        fieldName.add(4, "现价");
        fieldName.add(5, "底价");
        fieldName.add(6, "用户自身转发次数");
        fieldName.add(7, "帮砍人数");
        for (int j = 0; j <= fieldName.size(); j++) {
            fieldStyle.add(j, "6000");
        }
        dto.setPageSize(65536);
        searchChart(dto);
        if (dto.getList().size() > 0) {
            for (int i = 0; i < dto.getList().size(); i++) {
                BargainChartDto entity = dto.getList().get(i);
                List<Object> rowData = new ArrayList<Object>();
                rowData.add(0, entity.getRownum());
                rowData.add(1, entity.getBargainName());
                rowData.add(2, entity.getPhone());
                rowData.add(3, formatNumber2Str(entity.getPrice()));
                rowData.add(4, formatNumber2Str(entity.getCurrentPrice()));
                rowData.add(5, formatNumber2Str(entity.getBasePrice()));
                rowData.add(6, entity.getShareTotal());
                rowData.add(7, entity.getBargainNum());
                fieldData.add(rowData);
            }
        } else {
            List<Object> rowData = new ArrayList<Object>();
            fieldData.add(rowData);
        }
        sheetName.add(0, "活动详情表");
        ExcleUtil ex = new ExcleUtil(fieldName, sheetName, fieldData, fieldStyle);
        HSSFWorkbook wb = ex.createWorkbook();
        return wb;
    }

    // 格式化金额
    public String formatNumber2Str(Long amount) {
        if (null == amount) {
            amount = 0L;
        }
        DecimalFormat decimalFormat = new DecimalFormat("##,##0.00");
        return decimalFormat.format(amount.doubleValue() / 100);
    }

}
