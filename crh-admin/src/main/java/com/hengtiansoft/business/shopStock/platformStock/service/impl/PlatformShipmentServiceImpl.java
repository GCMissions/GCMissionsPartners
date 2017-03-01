/*
 * Project Name: wrw-admin
 * File Name: ShipmentService.java
 * Class Name: ShipmentService
 * Copyright 2014 Hengtian Software Inc
 * Licensed under the Hengtiansoft
 * http://www.hengtiansoft.com
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.shopStock.platformStock.service.impl;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.business.setting.dto.SBasicParaDto;
import com.hengtiansoft.business.setting.service.MessageModelService;
import com.hengtiansoft.business.shopStock.platformStock.dto.GoodsStockDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.ShipmentDetailDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.ShipmentSaveDto;
import com.hengtiansoft.business.shopStock.platformStock.dto.ShipmentSearchDto;
import com.hengtiansoft.business.shopStock.platformStock.service.PlatformShipmentService;
import com.hengtiansoft.business.shopStock.regionalPlatform.dto.ZSafeStockExcelDto;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.enumeration.EErrorCode;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.sequence.SequenceGenerator;
import com.hengtiansoft.common.sequence.SequenceType;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.MSmsDao;
import com.hengtiansoft.wrw.dao.PGoodsDao;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.dao.SShipmentDetailDao;
import com.hengtiansoft.wrw.dao.SShipmentOrderDao;
import com.hengtiansoft.wrw.dao.SStockDao;
import com.hengtiansoft.wrw.dao.SStockRecordDao;
import com.hengtiansoft.wrw.entity.MSmsEntity;
import com.hengtiansoft.wrw.entity.PGoodsEntity;
import com.hengtiansoft.wrw.entity.SOrgEntity;
import com.hengtiansoft.wrw.entity.SShipmentDetailEntity;
import com.hengtiansoft.wrw.entity.SShipmentOrderEntity;
import com.hengtiansoft.wrw.entity.SStockEntity;
import com.hengtiansoft.wrw.entity.SStockRecordEntity;
import com.hengtiansoft.wrw.entity.SUserEntity;
import com.hengtiansoft.wrw.enums.MessageModelEnum;
import com.hengtiansoft.wrw.enums.OrgTypeEnum;
import com.hengtiansoft.wrw.enums.ShipmentState;
import com.hengtiansoft.wrw.enums.SmsStatus;
import com.hengtiansoft.wrw.enums.StockRecordTypeEmum;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Class Name: ShipmentService Description: 发货管理业务类
 * 
 * @author xianghuang
 */
@Service
public class PlatformShipmentServiceImpl implements PlatformShipmentService {

    private static final Logger logger = LoggerFactory.getLogger(PlatformShipmentServiceImpl.class);

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private SOrgDao orgDao;

    @Autowired
    private PGoodsDao goodsDao;

    @Autowired
    private SShipmentOrderDao shipmentOrderDao;

    @Autowired
    private SShipmentDetailDao shipmentDetailDao;

    @Autowired
    private SStockDao stockDao;

    @Autowired
    private SStockRecordDao stockRecordDao;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MessageModelService messageModelService;
    @Resource(name = "freemarkerConfigurer")
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Autowired
    private MSmsDao smsDao;

    /*
     * @Autowired private MMemberDao mMemberDao;
     */

    @Override
    @Transactional
    public ShipmentSearchDto search(ShipmentSearchDto searchDto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder selectSql = new StringBuilder();
        StringBuilder paramSql = new StringBuilder();

        selectSql.append(" select new com.hengtiansoft.business.shopStock.platformStock.dto.ShipmentSearchRecordDto(");
        selectSql.append("  o.expressNum,org.orgName,org.orgType,sum(d.num),o.status,o.orderId,o.createDate");
        selectSql.append(" ) ");
        selectSql
                .append(" FROM SShipmentOrderEntity o , SShipmentDetailEntity d , SOrgEntity org where o.orderId=d.orderId and org.orgId=o.receivingId ");

        if (StringUtils.isNotBlank(searchDto.getShipmentConde())) {
            paramSql.append(" and o.orderId like:orderId ");
            param.put("orderId", "%" + searchDto.getShipmentConde().trim() + "%");
        }
        if (StringUtils.isNotBlank(searchDto.getShipmentState())) {
            paramSql.append(" and o.status=:status ");
            param.put("status", searchDto.getShipmentState());
        }

        if (StringUtils.isNotBlank(searchDto.getCreateDateStart())) {
            paramSql.append(" and o.createDate>=:createDateStart ");
            param.put("createDateStart",
                    DateTimeUtil.parseDate(searchDto.getCreateDateStart(), DateTimeUtil.SIMPLE_YMD));
        }

        if (StringUtils.isNotBlank(searchDto.getCreateDateEnd())) {
            paramSql.append(" and o.createDate<=:createDateEnd ");
            param.put("createDateEnd", DateTimeUtil.dateParse(searchDto.getCreateDateEnd() + " 23:59:59"));
        }
        if (StringUtils.isNotBlank(searchDto.getOrgName())) {
            paramSql.append(" and org.orgName like:orgName ");
            param.put("orgName", "%" + searchDto.getOrgName().trim() + "%");
        }
        if (StringUtils.isNotBlank(searchDto.getOrgCode())) {
            paramSql.append(" and org.orgCode=:orgCode ");
            param.put("orgCode", searchDto.getOrgCode());
        }
        if (StringUtils.isNotBlank(searchDto.getOrgCate())) {
            paramSql.append(" and org.orgType=:orgType ");
            param.put("orgType", searchDto.getOrgCate());
        }

        selectSql
                .append(paramSql.toString())
                .append(" group by o.expressNum,org.orgName,org.orgType,o.status,o.orderId,o.createDate,org.orgCode order by o.orderId desc");

        Query query = entityManager.createQuery(selectSql.toString());
        QueryUtil.processParamForQuery(query, param);

        Long totalRecord = Long.valueOf(query.getResultList().size());
        query.setFirstResult(searchDto.getPageSize() * (searchDto.getCurrentPage() - 1));
        query.setMaxResults(searchDto.getPageSize());

        QueryUtil.buildPagingDto(searchDto, totalRecord, query.getResultList());

        return searchDto;
    }

    @Override
    public ShipmentDetailDto get(final String shimentId) {
        ShipmentDetailDto dto = new ShipmentDetailDto();
        SShipmentOrderEntity orderEntity = shipmentOrderDao.findOne(String.valueOf(shimentId));
        if (orderEntity == null) {
            if (logger.isInfoEnabled()) {
                logger.info(String.format("根据OrderId：%s 查找SShipmentOrderEntity 不能为空", shimentId));
            }
        } else {
            Long receivingId = orderEntity.getReceivingId();
            SOrgEntity orgEntity = orgDao.findOne(receivingId);

            if (orgEntity == null) {
                if (logger.isInfoEnabled()) {
                    logger.info(String.format("根据收货人receivingId：%s 查找SOrgEntity 不能为空", receivingId));
                }
            } else {
                dto.setOrderId(shimentId);
                dto.setOrgCode(orgEntity.getOrgCode());
                dto.setOrgName(orgEntity.getOrgName());
                dto.setOrgCate(OrgTypeEnum.getValue(orgEntity.getOrgType()));
                dto.setCreateName(orgEntity.getContact());
                dto.setPhone(orgEntity.getPhone());
            }

            dto.setShipmentStatus(ShipmentState.getValue(orderEntity.getStatus()));
            dto.setCreateDate(orderEntity.getCreateDate());
            dto.setReceivingDate(orderEntity.getReceivingDate());

            List<SShipmentDetailEntity> detailEntityList = shipmentDetailDao
                    .findAll(new Specification<SShipmentDetailEntity>() {
                        @Override
                        public Predicate toPredicate(Root<SShipmentDetailEntity> root, CriteriaQuery<?> query,
                                CriteriaBuilder cb) {
                            List<Predicate> predicates = new ArrayList<Predicate>();
                            Predicate p1 = cb.equal(root.<String> get("orderId"), shimentId);
                            predicates.add(p1);
                            Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                            query.where(predicate);
                            return query.getRestriction();
                        }
                    });

            List<GoodsStockDto> productList = new ArrayList<GoodsStockDto>();
            for (SShipmentDetailEntity entity : detailEntityList) {
                GoodsStockDto recordDto = new GoodsStockDto();

                PGoodsEntity goodsEntity = goodsDao.findOne(entity.getGoodId());
                if (goodsEntity == null) {
                    if (logger.isInfoEnabled()) {
                        logger.info(String.format("根据getGoodId：%s查找goodsEntity 不能为空！", entity.getGoodId()));
                    }
                } else {
                    recordDto.setGoodsId(entity.getGoodId());
                    recordDto.setGoodsCode(goodsEntity.getGoodCode());
                    recordDto.setGoodsName(goodsEntity.getGoodName());
                    recordDto.setPrice(String.valueOf(WRWUtil.transFenToYuan(goodsEntity.getPrice())));
                    recordDto.setShipmentNum(entity.getNum());
                }
                productList.add(recordDto);
            }

            dto.setProductList(productList);
        }

        return dto;
    }

    @Override
    @Transactional
    public ResultDto<String> save(ShipmentSaveDto dto) {
        if (null == dto.getOrgId()) {
            throw new WRWException("请选择商家!");
        }

        if (dto.getShipmentDetails().size() < 1) {
            throw new WRWException("发货单必须有数据!");
        }

        for (GoodsStockDto recordDto : dto.getShipmentDetails()) {
            if (null == recordDto.getGoodsId() || null == recordDto.getShipmentNum()) {
                throw new WRWException("发货单物料Id/发货数量不能为空!");
            }
        }

        SShipmentOrderEntity entity = new SShipmentOrderEntity();
//        entity.setOrderId(sequenceGenerator.getOrderId(SequenceType.SHIPMENT));

        entity.setCreateDate(new Date());
        entity.setShipmentDate(new Date());
        entity.setCreateId(AuthorityContext.getCurrentUser().getUserId());
        entity.setReceivingId(dto.getOrgId());

        Long shipmentOrgId = ((SUserEntity) AuthorityContext.getCurrentUser()).getOrgId();
        entity.setShipmentId(shipmentOrgId);
        entity.setStatus(ShipmentState.CREATE.getKey());
        SOrgEntity receivingOrg = orgDao.findOne(dto.getOrgId());
        if (receivingOrg == null) {
            if (logger.isInfoEnabled()) {
                logger.info(String.format("根据收货人OrgId:%s没有找到SOrgEntity", dto.getOrgId()));
            }
            throw new WRWException(EErrorCode.ENTITY_CREATESHIPMENT_RECEIVINGORG_NOT_EXIST);
        } else {
            entity.setPhone(receivingOrg.getPhone());
        }

        Long totalNum = 0L;
        for (GoodsStockDto recordDto : dto.getShipmentDetails()) {
            totalNum += recordDto.getShipmentNum();
        }
        entity.setTotalNum(totalNum);
        entity.setType(dto.getType());
        SShipmentOrderEntity aEntity = shipmentOrderDao.save(entity);

        for (GoodsStockDto recordDto : dto.getShipmentDetails()) {
            SShipmentDetailEntity detailEntity = new SShipmentDetailEntity();
            detailEntity.setOrderId(aEntity.getOrderId());
            detailEntity.setGoodId(recordDto.getGoodsId());
            detailEntity.setNum(recordDto.getShipmentNum());
            shipmentDetailDao.save(detailEntity);
        }

        return ResultDtoFactory.toAck("发货单创建成功");
    }

    @Override
    @Transactional
    public ResultDto<String> deliverGoods(String shipmentId) {
        shipmentOrderDao.changeStatus(shipmentId, ShipmentState.SHIPMENT.getKey());

        // 减1库存
        SUserEntity user = (SUserEntity) AuthorityContext.getCurrentUser();

        List<SShipmentDetailEntity> shipmentDetailEntities = shipmentDetailDao.findByOrderId(shipmentId);
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
                    stockRecordEntity.setOperId(AuthorityContext.getCurrentUser().getUserId());
                    stockRecordDao.save(stockRecordEntity);
                }

            }
        }

        SShipmentOrderEntity shipOrderEntity = shipmentOrderDao.findOne(shipmentId);

        if (shipOrderEntity != null && null != shipOrderEntity.getReceivingId()) {
            SOrgEntity orgEntity = orgDao.findOne(shipOrderEntity.getReceivingId());
            // 发送短信
            if (StringUtils.isBlank(orgEntity.getPhone())) {
                throw new WRWException("收货人手机号不能为空！");
            }
//            insertSmsMessage(orgEntity.getPhone(), MessageModelEnum.sms_C_sendShipmentNotifyFrom1ToP, shipmentId);
        } else {
            throw new WRWException("收货人信息为空！");
        }
        return ResultDtoFactory.toAck("发货成功");
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
            logger.error("验证码短信模板解析失败...");
            throw new WRWException("验证码短信模板解析失败！");
        }
        message = out.toString();
        MSmsEntity sms = new MSmsEntity();
        sms.setCreateDate(new Date());
        sms.setPhone(phone);
        sms.setStatus(SmsStatus.UNSEND.getKey());
        sms.setContent(message);
        sms.setType(String.valueOf(messageType.getKey()));

        /*
         * MMemberEntity member=mMemberDao.findByLoginId(phone); sms.setMemberId(member.getUserId());
         */
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
        List<Object[]> list = shipmentOrderDao.findAllOrder(orders);

        Integer numCount = 0;
        Integer numCountYp = 0;
        for (int i = 0; i < list.size(); i++) {
            List<String> strList = new ArrayList<String>();
            strList.add(String.valueOf(i + 1));
            strList.add(DateTimeUtil.parseDateToString((Date) list.get(i)[0], DateTimeUtil.SIMPLE_YMD));
            strList.add(list.get(i)[1].toString());
            strList.add(list.get(i)[2].toString());
            strList.add(list.get(i)[3].toString());
            strList.add(list.get(i)[4].toString());
            strList.add(list.get(i)[5].toString());
            strList.add(list.get(i)[5].toString());
            strList.add("");
            strList.add("");
            strList.add("");
            listStr.add(strList);
            numCount += Integer.valueOf(list.get(i)[5].toString());
            numCountYp += Integer.valueOf(list.get(i)[5].toString());
        }
        List<String> hzList = new ArrayList<String>();
        hzList.add("汇总");
        hzList.add("");
        hzList.add("");
        hzList.add("");
        hzList.add("");
        hzList.add("");
        hzList.add(numCount.toString());
        hzList.add(numCountYp.toString());
        hzList.add("");
        hzList.add("");
        hzList.add("");
        listStr.add(hzList);
        return listStr;
    }

    @SuppressWarnings("unchecked")
    private List<ZSafeStockExcelDto> findAll(String orgType) {

        Map<String, Object> param = new HashMap<String, Object>();

        StringBuilder sqlSb = new StringBuilder();
        sqlSb.append("SELECT date(now()), O.ORG_NAME, G.GOOD_NAME, G.SPECS, S.STOCK_NUM, S.STANDARD_NUM - S.STOCK_NUM, S.ORG_ID, O.PARENT_ID "
                + " FROM S_STOCK S INNER JOIN P_GOODS G ON S.GOODS_ID = G.GOODS_ID AND S.STOCK_NUM < S.SAFE_NUM "
                + "INNER JOIN S_ORG O ON S.ORG_ID = O.ORG_ID ");
        if (!StringUtils.isEmpty(orgType)) {
            sqlSb.append(" AND O.ORG_TYPE = :orgType ");
            param.put("orgType", orgType);
        }
        sqlSb.append(" WHERE 1=1 ");

        Query query = entityManager.createNativeQuery(sqlSb.toString());
        QueryUtil.processParamForQuery(query, param);

        List<Object[]> safeStockList = query.getResultList();

        List<ZSafeStockExcelDto> list = new ArrayList<ZSafeStockExcelDto>();

        List<Long> orgIds = new ArrayList<Long>();

        for (Object[] obj : safeStockList) {
            ZSafeStockExcelDto dto = new ZSafeStockExcelDto();
            dto.setCreateDate(obj[0] != null ? obj[0].toString() : "");
            dto.setOrgName(obj[1] != null ? obj[1].toString() : "");
            dto.setGoodName(obj[2] != null ? obj[2].toString() : "");
            dto.setSpecs(obj[3] != null ? obj[3].toString() : "");
            dto.setStockNum(obj[4] != null ? Integer.parseInt(obj[4].toString()) : 0);
            dto.setNum(obj[5] != null ? Integer.parseInt(obj[5].toString()) : 0);
            dto.setOrgId(obj[6] != null ? Long.parseLong(obj[6].toString()) : 0L);
            if (obj[6] != null) {
                orgIds.add(Long.parseLong(obj[6].toString()));
            }
            dto.setParentId(obj[7] != null ? Long.parseLong(obj[7].toString()) : 0L);
            list.add(dto);
        }
        if (!CollectionUtils.isEmpty(orgIds)) {
            if (orgType.equals(OrgTypeEnum.Z.getKey())) {
                List<SOrgEntity> orgPs = orgDao.findPNameByZOrgId(orgIds);
                if (!CollectionUtils.isEmpty(orgPs)) {
                    for (int i = 0; i < list.size(); i++) {
                        for (int j = 0; j < orgPs.size(); j++) {
                            if (list.get(i).getParentId().equals(orgPs.get(j).getOrgId())) {
                                list.get(i).setOrgNameP(orgPs.get(j).getOrgName());
                            }
                        }
                    }
                }
            }
        }

        return list;
    }

    @Override
    public List<List<String>> toExcel(String orgType) {
        List<List<String>> listStr = new ArrayList<List<String>>();
        if (WRWUtil.isEmpty(orgType)) {
            throw new WRWException("导出商家类型不能为空!");
        }
        List<ZSafeStockExcelDto> list = findAll(orgType);

        Integer stockNum = 0;
        Integer num = 0;
        for (int i = 0; i < list.size(); i++) {
            List<String> strList = new ArrayList<String>();
            strList.add(String.valueOf(i + 1));
            strList.add(list.get(i).getCreateDate());
            if (orgType.equals(OrgTypeEnum.Z.getKey())) {
                strList.add(list.get(i).getOrgNameP());
            }
            strList.add(list.get(i).getOrgName());
            strList.add(list.get(i).getGoodName());
            strList.add(list.get(i).getSpecs());
            strList.add(list.get(i).getStockNum().toString());
            strList.add(list.get(i).getNum().toString());
            stockNum += list.get(i).getStockNum();
            num += list.get(i).getNum();
            listStr.add(strList);
        }
        List<String> hzList = new ArrayList<String>();
        hzList.add("汇总");
        hzList.add("");
        if (orgType.equals(OrgTypeEnum.Z.getKey())) {
            hzList.add("");
        }
        hzList.add("");
        hzList.add("");
        hzList.add("");
        hzList.add(stockNum.toString());
        hzList.add(num.toString());
        listStr.add(hzList);
        return listStr;
    }

}
