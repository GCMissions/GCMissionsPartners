package com.hengtiansoft.business.shopStock.regionalPlatform.service.impl;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.shopStock.regionalPlatform.dto.PReceivingDetailDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.dto.PReceivingDetailPageDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.dto.PReceivingDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.dto.PReceivingSearchDto;
import com.hengtiansoft.business.shopStock.regionalPlatform.service.PReceivingService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.dao.SShipmentDetailDao;
import com.hengtiansoft.wrw.dao.SShipmentOrderDao;
import com.hengtiansoft.wrw.dao.SStockDao;
import com.hengtiansoft.wrw.dao.SStockRecordDao;
import com.hengtiansoft.wrw.entity.SOrgEntity;
import com.hengtiansoft.wrw.entity.SShipmentDetailEntity;
import com.hengtiansoft.wrw.entity.SShipmentOrderEntity;
import com.hengtiansoft.wrw.entity.SStockEntity;
import com.hengtiansoft.wrw.entity.SStockRecordEntity;
import com.hengtiansoft.wrw.entity.SUserEntity;
import com.hengtiansoft.wrw.enums.ShipmentState;
import com.hengtiansoft.wrw.enums.StockRecordTypeEmum;

@Service
public class PReceivingServiceImpl implements PReceivingService {
    // 1平台orgId
    private static Long        orgIdOf1 = 0L;

    @Autowired
    private SShipmentOrderDao  shipmentDao;

    @Autowired
    private SShipmentDetailDao shipmentDetailDao;

    @Autowired
    private SStockRecordDao    stockRecordDao;

    @Autowired
    private SStockDao          stockDao;

    @Autowired
    private SOrgDao            orgDao;

    @Override
    public void searchDto(final PReceivingSearchDto dto) {
        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.DESC, "createDate"));
        Page<SShipmentOrderEntity> page = shipmentDao.findAll(new Specification<SShipmentOrderEntity>() {

            @Override
            public Predicate toPredicate(Root<SShipmentOrderEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();

                SUserEntity user = (SUserEntity) AuthorityContext.getCurrentUser();

                Predicate p0 = cb.equal(root.<Integer> get("receivingId"), user.getOrgId());
                predicates.add(p0);
                Predicate p1 = cb.equal(root.<Integer> get("shipmentId"), orgIdOf1);
                predicates.add(p1);
                if (!StringUtils.isEmpty(dto.getOrderId())) {
                    Predicate p2 = cb.equal(root.<Long> get("orderId"), dto.getOrderId());
                    predicates.add(p2);
                }
                if (!StringUtils.isEmpty(dto.getStatus())) {
                    Predicate p3 = cb.equal(root.<String> get("status"), dto.getStatus());
                    predicates.add(p3);
                }
                if (!StringUtils.isEmpty(dto.getTotalNoFrom())) {
                    Predicate p4 = cb.ge(root.<Long> get("totalNum"), Long.valueOf(dto.getTotalNoFrom()));
                    predicates.add(p4);
                }
                if (!StringUtils.isEmpty(dto.getTotalNoTo())) {
                    Predicate p5 = cb.le(root.<Long> get("totalNum"), Long.valueOf(dto.getTotalNoTo()));
                    predicates.add(p5);
                }
                if (!StringUtils.isEmpty(dto.getStartDate())) {
                    Predicate p6 = cb.greaterThanOrEqualTo(root.<Date> get("createDate"), DateTimeUtil.getDateBegin(dto.getStartDate()));
                    predicates.add(p6);
                }
                if (!StringUtils.isEmpty(dto.getEndDate())) {
                    Predicate p7 = cb.lessThanOrEqualTo(root.<Date> get("createDate"), DateTimeUtil.getDateEnd(dto.getEndDate()));
                    predicates.add(p7);
                }
                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }
        }, pageable);

        dto.setTotalPages(page.getTotalPages());
        dto.setTotalRecord(page.getTotalElements());
        dto.setList(buildShipmentDto(page.getContent()));
    }

    /**
     * Description: 构造dto列表
     *
     * @param entities
     * @return
     */
    private List<PReceivingDto> buildShipmentDto(List<SShipmentOrderEntity> entities) {
        List<PReceivingDto> dtos = new ArrayList<PReceivingDto>();
        for (SShipmentOrderEntity entity : entities) {
            PReceivingDto dto = new PReceivingDto();
            dto.setOrderId(entity.getOrderId());
            dto.setTotalNum(entity.getTotalNum());
            dto.setStatus(ShipmentState.getValue(entity.getStatus()));
            dto.setCreateDate(DateTimeUtil.dateFormat(entity.getCreateDate()));
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public Integer getReceivingCount() {

        SUserEntity user = (SUserEntity) AuthorityContext.getCurrentUser();

        Long receivingId = user.getOrgId();
        return shipmentDao.getSomebodyReceivingCount(receivingId, orgIdOf1);
    }

    @Override
    public PReceivingDetailPageDto findDetailOrder(String orderId) {
        PReceivingDetailPageDto dto = new PReceivingDetailPageDto();
        dto.setList(buildShipmentDetailDto(shipmentDetailDao.getOrderDetail(orderId)));
        return dto;
    }

    private List<PReceivingDetailDto> buildShipmentDetailDto(List<Object[]> orderDetail) {
        List<PReceivingDetailDto> dtos = new ArrayList<PReceivingDetailDto>();
        for (Object[] array : orderDetail) {
            PReceivingDetailDto dto = new PReceivingDetailDto();
            // G.GOODS_ID,G.GOOD_CODE,G.GOOD_NAME,G.PRICE,G.CREATE_DATE,S.NUM
            dto.setGoodsId(array[0] == (null) ? null : array[0].toString());
            dto.setGoodsCode(array[1] == (null) ? null : array[1].toString());
            dto.setName(array[2] == (null) ? null : array[2].toString());
            dto.setPrice(array[3] == (null) ? null : array[3].toString());
            dto.setCreateDate(array[4] == (null) ? null : array[4].toString());
            dto.setNum(Long.valueOf(array[5].toString()));
            dto.setPriceYuan(WRWUtil.transFenToYuan2String(Long.parseLong(dto.getPrice())));

            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public PReceivingDto findOrder(String orderId) {
        SShipmentOrderEntity entity = shipmentDao.findOne(orderId);
        PReceivingDto dto = new PReceivingDto();
        dto.setOrderId(entity.getOrderId());
        dto.setTotalNum(entity.getTotalNum());
        dto.setStatus(ShipmentState.getValue(entity.getStatus()));
        dto.setCreateDate(DateTimeUtil.dateFormat(entity.getCreateDate()));
        return dto;
    }

    @Transactional
    @Override
    public String changeStatus(String id) {

        SUserEntity user = (SUserEntity) AuthorityContext.getCurrentUser();
        SOrgEntity org = orgDao.findOne(user.getOrgId());
        List<SShipmentDetailEntity> shipmentDetailEntities = shipmentDetailDao.findByOrderId(id);
        for (SShipmentDetailEntity entity : shipmentDetailEntities) {
            List<SStockEntity> stockEntities = stockDao.findByOrgIdAndGoodsId(user.getOrgId(), entity.getGoodId());
            if (stockEntities.isEmpty()) {
                SStockEntity stockEntity = new SStockEntity();
                stockEntity.setOrgId(user.getOrgId());
                stockEntity.setGoodsId(entity.getGoodId());
                stockEntity.setStockNum(entity.getNum());
                stockEntity.setSafeNum(0L);
                stockEntity.setStandardNum(0L);
                stockEntity.setOrgName(org.getOrgName());
                stockDao.save(stockEntity);

                SStockRecordEntity stockRecordEntity = new SStockRecordEntity();
                stockRecordEntity.setChangeNum(Long.valueOf(entity.getNum().toString()));
                stockRecordEntity.setOperDate(new Date());
                stockRecordEntity.setStockId(stockEntity.getStockId());
                stockRecordEntity.setOperType(StockRecordTypeEmum.AUTOADD.getCode());
                stockRecordEntity.setOperId(user.getUserId());
                stockRecordDao.save(stockRecordEntity);
            } else {
                SStockEntity stockEntity = stockEntities.get(0);
                stockDao.updateStockNumById(stockEntity.getStockNum() + entity.getNum(), stockEntity.getStockId());
                SStockRecordEntity stockRecordEntity = new SStockRecordEntity();
                stockRecordEntity.setChangeNum(Long.valueOf(entity.getNum().toString()));
                stockRecordEntity.setOperDate(new Date());
                stockRecordEntity.setOperType(StockRecordTypeEmum.AUTOADD.getCode());
                stockRecordEntity.setStockId(stockEntity.getStockId());
                stockRecordEntity.setOperId(user.getUserId());
                stockRecordDao.save(stockRecordEntity);
            }
        }
        shipmentDao.receivingOrder(id, ShipmentState.DELIVERY.getKey(), user.getUserId());

        return "收货成功！";
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
        for (int i = 0; i < list.size(); i++) {
            List<String> strList = new ArrayList<String>();
            strList.add(String.valueOf(i + 1));
            strList.add(DateTimeUtil.parseDateToString((Date) list.get(i)[0], DateTimeUtil.CHINA_YMD));
            strList.add(list.get(i)[2].toString());
            strList.add(list.get(i)[3].toString());
            strList.add(list.get(i)[4].toString());
            strList.add(list.get(i)[5].toString());
            listStr.add(strList);
            numCount += Integer.valueOf(list.get(i)[5].toString());
        }
        List<String> hzList = new ArrayList<String>();
        hzList.add("汇总");
        hzList.add("");
        hzList.add("");
        hzList.add("");
        hzList.add("");
        hzList.add(numCount.toString());
        listStr.add(hzList);
        return listStr;
    }

}
