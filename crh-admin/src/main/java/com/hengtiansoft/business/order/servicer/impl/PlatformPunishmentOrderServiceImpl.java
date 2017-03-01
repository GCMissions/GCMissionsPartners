package com.hengtiansoft.business.order.servicer.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.order.servicer.PlatformPunishmentOrderService;
import com.hengtiansoft.business.shopStock.zStock.dto.OrderRewardDto;
import com.hengtiansoft.business.shopStock.zStock.dto.OrderRewardSearchDto;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.SOrderRewardDao;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.entity.SOrderRewardEntity;
import com.hengtiansoft.wrw.entity.SOrgEntity;
import com.hengtiansoft.wrw.enums.RewardOrderTypeEnum;

@Service
public class PlatformPunishmentOrderServiceImpl implements PlatformPunishmentOrderService {

    @Autowired
    private SOrderRewardDao orderRewardDao;

    @Autowired
    private SOrgDao sOrgDao;

    @Override
    public void search(final OrderRewardSearchDto dto) {
        // 按配送商姓名搜索
        final List<Long> orgIds = WRWUtil.isBlank(dto.getOrgName()) ? new ArrayList<Long>() : sOrgDao.findByOrgNameLike(dto.getOrgName().trim());

        Pageable pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.DESC,
                "createDate"));

        Page<SOrderRewardEntity> page = orderRewardDao.findAll(new Specification<SOrderRewardEntity>() {
            @Override
            public Predicate toPredicate(Root<SOrderRewardEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (WRWUtil.isNotEmpty(dto.getOrderId())) {
                    Predicate p1 = cb.equal(root.<String> get("orderId"), dto.getOrderId());
                    predicates.add(p1);
                }
                if (!orgIds.isEmpty()) {
                    Predicate p2 = root.<String> get("orgId").in(orgIds);
                    predicates.add(p2);
                }
                if (WRWUtil.isNotEmpty(dto.getType())) {
                    Predicate p3 = cb.equal(root.<String> get("type"), dto.getType());
                    predicates.add(p3);
                } 
                if (dto.getStartDate() != null && dto.getEndDate() != null) {
                    Predicate p4 = cb.between(root.<Date> get("createDate"), dto.getStartDate(), dto.getEndDate());
                    predicates.add(p4);
                }
                if (dto.getStartDate() != null && dto.getEndDate() == null) {
                    Predicate p5 = cb.greaterThanOrEqualTo(root.<Date> get("createDate"), dto.getStartDate());
                    predicates.add(p5);
                }
                if (dto.getStartDate() == null && dto.getEndDate() != null) {
                    Predicate p6 = cb.lessThanOrEqualTo(root.<Date> get("createDate"), dto.getEndDate());
                    predicates.add(p6);
                }

                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }
        }, pageable);
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalRecord(page.getTotalElements());
        dto.setList(buildPOrderDtos(page.getContent()));
    }

    public List<OrderRewardDto> buildPOrderDtos(List<SOrderRewardEntity> orders) {
        // 获取配送商信息
        Set<Long> orgIds = new HashSet<>();
        for (SOrderRewardEntity order : orders) {
            orgIds.add(order.getOrgId());
        }
        List<SOrgEntity> orgs = sOrgDao.findAll(orgIds);

        // 获取配送商姓名map
        Map<Long, String> orgNameMap = new HashMap<>();
        for (SOrgEntity org : orgs) {
            orgNameMap.put(org.getOrgId(), org.getOrgName());
        }

        // 数据转换
        List<OrderRewardDto> dtos = new ArrayList<OrderRewardDto>();
        for (SOrderRewardEntity order : orders) {
            OrderRewardDto dto = ConverterService.convert(order, OrderRewardDto.class);
            dto.setRewardType(RewardOrderTypeEnum.getValue(order.getType()));
//            dto.setAmount(WRWUtil.transFenToYuan(order.getAmount()));
            dto.setOrgName(orgNameMap.get(order.getOrgId()));
            dtos.add(dto);
        }
        return dtos;
    }
}
