package com.hengtiansoft.business.shopStock.zStock.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.hengtiansoft.business.shopStock.zStock.dto.OrderRewardDto;
import com.hengtiansoft.business.shopStock.zStock.dto.OrderRewardSearchDto;
import com.hengtiansoft.business.shopStock.zStock.service.PunishmentOrderService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.SOrderRewardDao;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.dao.SUserDao;
import com.hengtiansoft.wrw.entity.SOrderRewardEntity;
import com.hengtiansoft.wrw.entity.SUserEntity;
import com.hengtiansoft.wrw.enums.RewardOrderTypeEnum;

@Service
public class PunishmentOrderServiceImpl implements PunishmentOrderService {

    @Autowired
    private SOrderRewardDao orderRewardDao;

    @Autowired
    private SOrgDao sOrgDao;

    @Autowired
    private SUserDao userDao;

    @Override
    public void search(final OrderRewardSearchDto dto) {
        String loginId = AuthorityContext.getCurrentUser().getLoginId();
        final SUserEntity userEntity = userDao.findByLoginId(loginId);
        if (userEntity == null) {
            throw new WRWException("当前用户不存在！");
        }
        Pageable pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.DESC,
                "createDate"));

        Page<SOrderRewardEntity> page = orderRewardDao.findAll(new Specification<SOrderRewardEntity>() {

            @Override
            public Predicate toPredicate(Root<SOrderRewardEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (!WRWUtil.isEmpty(dto.getOrderId())) {
                    Predicate p1 = cb.equal(root.<String> get("orderId"), dto.getOrderId());
                    predicates.add(p1);
                }
                if (!WRWUtil.isEmpty(dto.getType())) {
                    Predicate p2 = cb.equal(root.<String> get("type"), dto.getType());
                    predicates.add(p2);
                }
                if (dto.getStartDate() != null && dto.getEndDate() != null) {
                    Predicate p3 = cb.between(root.<Date> get("createDate"), dto.getStartDate(), dto.getEndDate());
                    predicates.add(p3);
                }
                if (dto.getStartDate() != null && dto.getEndDate() == null) {
                    Predicate p4 = cb.greaterThanOrEqualTo(root.<Date> get("createDate"), dto.getStartDate());
                    predicates.add(p4);
                }
                if (dto.getStartDate() == null && dto.getEndDate() != null) {
                    Predicate p5 = cb.lessThanOrEqualTo(root.<Date> get("createDate"), dto.getEndDate());
                    predicates.add(p5);
                }
                Long orgId = userEntity.getOrgId();
                Predicate predicate = cb.equal(root.<Long> get("orgId"), orgId);
                predicates.add(predicate);
                predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }
        }, pageable);
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalRecord(page.getTotalElements());
        dto.setList(buildPOrderDtos(page.getContent()));
    }

    public List<OrderRewardDto> buildPOrderDtos(List<SOrderRewardEntity> entities) {
        List<OrderRewardDto> dtos = new ArrayList<OrderRewardDto>();
        for (SOrderRewardEntity entity : entities) {
            OrderRewardDto dto = ConverterService.convert(entity, OrderRewardDto.class);
            String orgName = sOrgDao.findOrgNameByOrgId(entity.getOrgId());
//            dto.setAmount(WRWUtil.transFenToYuan(entity.getAmount()));
            dto.setOrgName(orgName);
            dto.setRewardType(RewardOrderTypeEnum.getValue(entity.getType()));
            dtos.add(dto);
        }
        return dtos;
    }
}
