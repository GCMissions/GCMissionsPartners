package com.hengtiansoft.business.shopStock.regionalPlatform.service.impl;

import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.business.provider.dto.OrgSimpleDto;
import com.hengtiansoft.business.provider.dto.SearchOrgDto;
import com.hengtiansoft.business.setting.dto.SBasicParaDto;
import com.hengtiansoft.business.setting.service.MessageModelService;
import com.hengtiansoft.business.shopStock.platformStock.dto.GoodsStockDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.GoodsStockSearchDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.dto.PShipmentDetailDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.dto.PShipmentDetailPageDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.dto.PShipmentDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.dto.PShipmentSearchDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.service.PShipmentService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.enumeration.EErrorCode;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.MSmsDao;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.dao.SRegionDao;
import com.hengtiansoft.wrw.dao.SShipmentDetailDao;
import com.hengtiansoft.wrw.dao.SShipmentOrderDao;
import com.hengtiansoft.wrw.dao.SStockDao;
import com.hengtiansoft.wrw.dao.SStockRecordDao;
import com.hengtiansoft.wrw.entity.MSmsEntity;
import com.hengtiansoft.wrw.entity.SOrgEntity;
import com.hengtiansoft.wrw.entity.SRegionEntity;
import com.hengtiansoft.wrw.entity.SShipmentDetailEntity;
import com.hengtiansoft.wrw.entity.SShipmentOrderEntity;
import com.hengtiansoft.wrw.entity.SStockEntity;
import com.hengtiansoft.wrw.entity.SStockRecordEntity;
import com.hengtiansoft.wrw.entity.SUserEntity;
import com.hengtiansoft.wrw.enums.MessageModelEnum;
import com.hengtiansoft.wrw.enums.ShipmentState;
import com.hengtiansoft.wrw.enums.SmsStatus;
import com.hengtiansoft.wrw.enums.StockRecordTypeEmum;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class PShipmentServiceImpl implements PShipmentService {

    @Autowired
    private EntityManager        entityManager;

    @Autowired
    private SShipmentOrderDao    shipmentDao;

    @Autowired
    private SShipmentDetailDao   shipmentDetailDao;

    @Autowired
    private SStockRecordDao      stockRecordDao;

    @Autowired
    private SStockDao            stockDao;

    @Autowired
    private SOrgDao              orgDao;

    @Autowired
    private SRegionDao           sRegionDao;

    @Autowired
    private MessageModelService  messageModelService;

    @Resource(name = "freemarkerConfigurer")
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    private MSmsDao              smsDao;

    @SuppressWarnings("unchecked")
    @Override
    public void searchDto(PShipmentSearchDto searchDto) {
        StringBuilder sqlDataBuilder = new StringBuilder();

        Map<String, Object> param = new HashMap<String, Object>();

        StringBuilder sqlCountBuilder = new StringBuilder();
        sqlDataBuilder.append("select s.order_id,s.total_num,o.org_code,o.org_name receiving_name,s.create_date,s.STATUS").append(
                " from S_SHIPMENT_ORDER s  left join S_ORG o on receiving_id= org_id where ");

        sqlCountBuilder.append("select count(order_id) from S_SHIPMENT_ORDER s where ");

        SUserEntity user = (SUserEntity) AuthorityContext.getCurrentUser();
        Long shipmentId = user.getOrgId();
        StringBuilder conditionBuilder = new StringBuilder(" ((SHIPMENT_ID = ").append(shipmentId).append(") or (receiving_id in ")
                .append("(select receiving_id from S_SHIPMENT_ORDER r where r.SHIPMENT_ID = '").append(0L).append("' and receiving_id in ")
                .append("(select org_id from S_ORG where ORG_TYPE= 'Z' and  PARENT_ID = '").append(shipmentId).append("')) ))"); // 公用条件condition
        if (!StringUtils.isEmpty(searchDto.getStartDate())) {
            conditionBuilder.append(" and s.CREATE_DATE >=:startDate");
            param.put("startDate", DateTimeUtil.getDateBegin(searchDto.getStartDate()));
        }
        if (!StringUtils.isEmpty(searchDto.getEndDate())) {
            conditionBuilder.append(" and s.CREATE_DATE <=:endDate");
            param.put("endDate", DateTimeUtil.getDateEnd(searchDto.getEndDate()));
        }
        if (!StringUtils.isEmpty(searchDto.getStatus())) {
            conditionBuilder.append(" and s.STATUS=:status");
            param.put("status", searchDto.getStatus());
        }
        if (!StringUtils.isEmpty(searchDto.getOrderId())) {
            conditionBuilder.append(" and s.ORDER_ID= :orderId");
            param.put("orderId", searchDto.getOrderId());
        }
        if (!StringUtils.isEmpty(searchDto.getTotalNoFrom())) {
            conditionBuilder.append(" and s.total_num >= :totalNoFrom");
            param.put("totalNoFrom", searchDto.getTotalNoFrom());
        }
        if (!StringUtils.isEmpty(searchDto.getTotalNoTo())) {
            conditionBuilder.append(" and s.total_num <= :totalNoTo");
            param.put("totalNoTo", searchDto.getTotalNoTo());
        }
        String orderString = " order by s.shipment_date desc";
        Query query = entityManager.createNativeQuery(sqlDataBuilder.append(conditionBuilder).append(orderString).toString());
        QueryUtil.processParamForQuery(query, param);
        Query countQuery = entityManager.createNativeQuery(sqlCountBuilder.append(conditionBuilder).toString());
        QueryUtil.processParamForQuery(countQuery, param);

        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();

        query.setFirstResult(searchDto.getPageSize() * (searchDto.getCurrentPage() - 1));
        query.setMaxResults(searchDto.getPageSize());
        searchDto.setTotalRecord(totalRecord.longValue());
        searchDto.setTotalPages(totalRecord.intValue() % searchDto.getPageSize() == 0 ? totalRecord.intValue() / searchDto.getPageSize()
                : ((totalRecord.intValue() / searchDto.getPageSize()) + 1));
        searchDto.setList(buildsearchDto(query.getResultList()));

    }

    private List<PShipmentDto> buildsearchDto(List<Object[]> resultList) {
        List<PShipmentDto> dtos = new ArrayList<PShipmentDto>();
        for (Object[] array : resultList) {
            PShipmentDto dto = new PShipmentDto();
            dto.setOrderId(array[0].toString());
            dto.setTotalNum(array[1] == (null) ? null : array[1].toString());
            dto.setRegNo(array[2] == (null) ? null : array[2].toString());
            dto.setRegName(array[3] == (null) ? null : array[3].toString());
            dto.setCreateDate(array[4] == (null) ? null : array[4].toString().substring(0, 19));
            dto.setStatus(array[5] == (null) ? null : ShipmentState.getValue(array[5].toString()));
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public Integer getShipmentCount() {

        SUserEntity user = (SUserEntity) AuthorityContext.getCurrentUser();
        Long shipmentId = user.getOrgId();
        return shipmentDao.getPShipmentCount(shipmentId);
    }

    @Override
    public PShipmentDto findOrder(String orderId) {
        PShipmentDto dto = new PShipmentDto();
        List<Object[]> orderDetail = shipmentDao.getOnePShipment(orderId);
        if (orderDetail.size() == 1) {
            Object[] array = orderDetail.get(0);
            dto.setOrderId(orderId);
            dto.setTotalNum(array[1] == (null) ? null : array[1].toString());
            dto.setRegNo(array[2] == (null) ? null : array[2].toString());
            dto.setRegName(array[3] == (null) ? null : array[3].toString());
            dto.setCreateDate(array[4] == (null) ? null : array[4].toString().substring(0, 19));
            dto.setStatus(array[5] == (null) ? null : ShipmentState.getValue(array[5].toString()));
            dto.setContact(array[6] == (null) ? null : array[6].toString());
            dto.setPhone(array[7] == (null) ? null : array[7].toString());
            dto.setAddress(array[8] == (null) ? null : array[8].toString());
        }
        return dto;
    }

    @Override
    public PShipmentDetailPageDto findDetailOrder(String orderId) {
        PShipmentDetailPageDto dto = new PShipmentDetailPageDto();
        dto.setList(buildShipmentDetailDto(shipmentDetailDao.getOrderDetail(orderId)));
        return dto;
    }

    private List<PShipmentDetailDto> buildShipmentDetailDto(List<Object[]> orderDetail) {
        List<PShipmentDetailDto> dtos = new ArrayList<PShipmentDetailDto>();
        for (Object[] array : orderDetail) {
            PShipmentDetailDto dto = new PShipmentDetailDto();
            // G.GOODS_ID,G.GOOD_CODE,G.GOOD_NAME,G.PRICE,G.CREATE_DATE,S.NUM
            dto.setGoodsId(array[0] == (null) ? null : array[0].toString());
            dto.setGoodCode(array[1] == (null) ? null : array[1].toString());
            dto.setGoodName(array[2] == (null) ? null : array[2].toString());
            dto.setPrice(array[3] == (null) ? null : array[3].toString());
            dto.setCreateDate(array[4] == (null) ? null : array[4].toString());
            dto.setNum(Long.valueOf(array[5].toString()));

            dto.setPriceYuan(WRWUtil.transFenToYuan(Long.parseLong(dto.getPrice())).toString());

            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional
    @Override
    public String changeStatus(String id) {

        SUserEntity user = (SUserEntity) AuthorityContext.getCurrentUser();

        List<SShipmentDetailEntity> shipmentDetailEntities = shipmentDetailDao.findByOrderId(id);
        for (SShipmentDetailEntity entity : shipmentDetailEntities) {

            List<SStockEntity> stockEntities = stockDao.findByOrgIdAndGoodsId(user.getOrgId(), entity.getGoodId());
            if (stockEntities.isEmpty()) {
                throw new WRWException(EErrorCode.BIZ_STOCK_NOT_ENOUGH);
            } else {
                SStockEntity stockEntity = stockEntities.get(0);
                if (stockEntity.getStockNum() < entity.getNum()) {
                    throw new WRWException(EErrorCode.BIZ_STOCK_NOT_ENOUGH);
                } else {
                    stockDao.updateStockNumById(stockEntity.getStockNum() - entity.getNum(), stockEntity.getStockId());

                    SStockRecordEntity stockRecordEntity = new SStockRecordEntity();
                    stockRecordEntity.setChangeNum(Long.valueOf(entity.getNum().toString()));
                    stockRecordEntity.setOperDate(new Date());
                    stockRecordEntity.setStockId(stockEntity.getStockId());
                    stockRecordEntity.setOperType(StockRecordTypeEmum.AUTOREDUCE.getCode());
                    stockRecordEntity.setOperId(user.getUserId());
                    stockRecordDao.save(stockRecordEntity);
                }

            }
        }
        shipmentDao.shipmentOrder(id, ShipmentState.SHIPMENT.getKey(), user.getUserId());
        SShipmentOrderEntity shipOrderEntity = shipmentDao.findOne(id);

        if (shipOrderEntity != null && null != shipOrderEntity.getReceivingId()) {
            SOrgEntity orgEntity = orgDao.findOne(shipOrderEntity.getReceivingId());
            // 发送短信
            if (StringUtils.isBlank(orgEntity.getPhone())) {
                throw new WRWException("收货人手机号不能为空！");
            }
//            insertSmsMessage(orgEntity.getPhone(), MessageModelEnum.sms_C_sendShipmentNotifyFromPToZ, id);
        } else {
            throw new WRWException("收货人信息为空！");
        }
        return "发货成功！";
    }

    private void insertSmsMessage(String phone, MessageModelEnum messageType, String orderId) {
        // 获取短信模板
        SBasicParaDto paraDto = messageModelService.getBasicParaByParaName(messageType.getValue());
        String message = paraDto.getParaValue1();
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Map<String, Object> dataMap = new HashMap<String, Object>();

        dataMap.put("orderId", orderId);
        StringWriter out = new StringWriter();
        try {
            new Template("template", new StringReader(message), configuration).process(dataMap, out);
        } catch (Exception e) {
            throw new WRWException("验证码短信模板解析失败！");
        }
        message = out.toString();
        MSmsEntity sms = new MSmsEntity();
        sms.setCreateDate(new Date());
        sms.setPhone(phone);
        sms.setStatus(SmsStatus.UNSEND.getKey());
        sms.setContent(message);
        sms.setType(String.valueOf(messageType.getKey()));

        smsDao.save(sms);
    }

    @Override
    public List<List<String>> findAllOrder(String orderId) {
        List<List<String>> listStr = new ArrayList<List<String>>();
        if (WRWUtil.isEmpty(orderId)) {
            throw new WRWException("订单不能为空!");
        }
        String[] orderIds = orderId.split(",");
        List<String> orders = new ArrayList<String>();
        for (String str : orderIds) {
            orders.add(str);
        }
        List<Object[]> list = shipmentDao.findAllOrder(orders);

        Integer numCount = 0;
        Integer numCountyg = 0;
        for (int i = 0; i < list.size(); i++) {
            List<String> strList = new ArrayList<String>();
            strList.add(String.valueOf(i + 1));
            strList.add(DateTimeUtil.parseDateToString((Date) list.get(i)[0], DateTimeUtil.CHINA_YMD));
            strList.add(list.get(i)[1].toString());
            strList.add(list.get(i)[2].toString());
            strList.add(list.get(i)[3].toString());
            strList.add(list.get(i)[4].toString());
            strList.add(list.get(i)[5].toString());
            strList.add(list.get(i)[5].toString());
            listStr.add(strList);
            numCount += Integer.valueOf((list.get(i)[5].toString()));
            numCountyg += Integer.valueOf((list.get(i)[5].toString()));
        }
        List<String> hzList = new ArrayList<String>();
        hzList.add("汇总");
        hzList.add("");
        hzList.add("");
        hzList.add("");
        hzList.add("");
        hzList.add("");
        hzList.add(numCount.toString());
        hzList.add(numCountyg.toString());
        listStr.add(hzList);
        return listStr;
    }

    @Override
    public SearchOrgDto getZorg() {
        SUserEntity user = (SUserEntity) AuthorityContext.getCurrentUser();
        Long pId = user.getOrgId();
        List<SOrgEntity> orgs = orgDao.findZOrgByPId(pId);
        SearchOrgDto dto = new SearchOrgDto();
        dto.setList(buildOrgDtos(orgs));
        return dto;
    }

    private List<OrgSimpleDto> buildOrgDtos(List<SOrgEntity> content) {
        List<OrgSimpleDto> dtos = new ArrayList<OrgSimpleDto>();

        if (content.size() > 0) {
            List<Integer> regionIds = new ArrayList<Integer>();
            HashMap<Integer, SRegionEntity> regionMap = new HashMap<Integer, SRegionEntity>();
            for (SOrgEntity entity : content) {
                regionIds.add(entity.getRegion());
            }
            if (regionIds.size() > 0) {
                List<SRegionEntity> regionEntities = sRegionDao.findAll(regionIds);
                for (SRegionEntity regionEntity : regionEntities) {
                    regionMap.put(regionEntity.getId(), regionEntity);
                }
            }

            for (SOrgEntity entity : content) {
                OrgSimpleDto dto = ConverterService.convert(entity, new OrgSimpleDto());
                dto.setAddress(regionMap.get(entity.getRegion()).getMergerName().replace(",", "") + dto.getAddress());
                dto.setProvinceName(regionMap.get(entity.getRegion()).getMergerName());
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public GoodsStockSearchDto searchProductStock(GoodsStockSearchDto searchDto) {
        Map<String, Object> param = new HashMap<String, Object>();
        List<GoodsStockDto> pagedList = new ArrayList<GoodsStockDto>();
        StringBuilder sqlDataBuilder = new StringBuilder();
        StringBuilder countSql = new StringBuilder();
        StringBuilder conditionSql = new StringBuilder();
        sqlDataBuilder
                .append("SELECT G.GOOD_CODE,G.GOOD_NAME,G.PRICE,a.STOCK_NUM,G.CREATE_DATE,G.GOODS_ID FROM S_STOCK S RIGHT JOIN P_GOODS G ON S.GOODS_ID=G.GOODS_ID "
                        + "left join (select GOODS_ID,STOCK_NUM from S_STOCK where org_id = :orgId) a ON S.GOODS_ID = a.GOODS_ID where S.org_id = :orgPId ");
        SUserEntity user = (SUserEntity) AuthorityContext.getCurrentUser();
        param.put("orgPId", user.getOrgId());
        param.put("orgId", searchDto.getOrgId());
        countSql.append("select count(1) from (").append(sqlDataBuilder);

        if (StringUtils.isNotBlank(searchDto.getGoodsCode())) {
            conditionSql.append(" AND G.GOOD_CODE =:code ");
            param.put("code", searchDto.getGoodsCode().trim());
        }
        if (StringUtils.isNotBlank(searchDto.getGoodsName())) {
            conditionSql.append(" AND G.GOOD_NAME LIKE :name ");
            param.put("name", "%" + searchDto.getGoodsName().trim() + "%");
        }
        conditionSql.append(" GROUP BY G.GOOD_CODE,G.GOOD_NAME,G.PRICE,a.STOCK_NUM,G.CREATE_DATE,G.GOODS_ID ORDER BY G.CREATE_DATE DESC ");
        Query query = entityManager.createNativeQuery(sqlDataBuilder.append(conditionSql).toString());
        QueryUtil.processParamForQuery(query, param);
        Query countQuery = entityManager.createNativeQuery(countSql.append(conditionSql).append(" ) A").toString());
        QueryUtil.processParamForQuery(countQuery, param);
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(searchDto.getPageSize() * (searchDto.getCurrentPage() - 1));
        query.setMaxResults(searchDto.getPageSize());

        List<Object[]> list = query.getResultList();
        for (Object[] objects : list) {
            GoodsStockDto dto = new GoodsStockDto();
            dto.setGoodsCode(String.valueOf(objects[0]));
            dto.setGoodsName(String.valueOf(objects[1]));
            dto.setPrice(WRWUtil.transFenToYuan(WRWUtil.objToLong(objects[2])).toString());
            dto.setCreateDate(String.valueOf(objects[4]));
            dto.setGoodsId(WRWUtil.objToLong(objects[5]));
            if (StringUtils.isNotBlank(String.valueOf(objects[3]))) {
                dto.setProductStockNum(WRWUtil.objToLong(objects[3]));
            } else {
                dto.setProductStockNum(0L);
            }

            pagedList.add(dto);
        }

        QueryUtil.buildPagingDto(searchDto, totalRecord.longValue(), pagedList);
        return searchDto;
    }

}
