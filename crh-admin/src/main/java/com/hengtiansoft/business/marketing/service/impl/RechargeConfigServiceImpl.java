/*
 * Project Name: wrw-admin
 * File Name: RechargeConfigServiceImpl.java
 * Class Name: RechargeConfigServiceImpl
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
package com.hengtiansoft.business.marketing.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.marketing.dto.CouponDto;
import com.hengtiansoft.business.marketing.dto.CouponPageDto;
import com.hengtiansoft.business.marketing.dto.RechargeConfDto;
import com.hengtiansoft.business.marketing.dto.RechargeConfPageDto;
import com.hengtiansoft.business.marketing.dto.RechargeCouponDto;
import com.hengtiansoft.business.marketing.service.RechargeConfigService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.enumeration.EErrorCode;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.SCouponConfigDao;
import com.hengtiansoft.wrw.dao.SRechargeConfigDao;
import com.hengtiansoft.wrw.dao.SRechargeCouponDao;
import com.hengtiansoft.wrw.entity.SCouponConfigEntity;
import com.hengtiansoft.wrw.entity.SRechargeConfigEntity;
import com.hengtiansoft.wrw.entity.SRechargeCouponEntity;
import com.hengtiansoft.wrw.entity.SRechargeCouponPK;
import com.hengtiansoft.wrw.enums.RechargeConfStatus;
import com.hengtiansoft.wrw.enums.StatusEnum;

/**
 * Class Name: RechargeConfigServiceImpl
 * Description: TODO
 * 
 * @author chengminmiao
 */
@Service
public class RechargeConfigServiceImpl implements RechargeConfigService {

    @Autowired
    private SRechargeConfigDao sRechargeConfigDao;

    @Autowired
    private SRechargeCouponDao sRechargeCouponDao;

    @Autowired
    private SCouponConfigDao   sCouponConfigDao;

    /**
     * 查询充值配置
     */
    @Override
    public void getList(RechargeConfPageDto dto) {
        List<SRechargeConfigEntity> configEntities = sRechargeConfigDao.findByStatus(RechargeConfStatus.NORMAL.getCode());
        if (null != configEntities && configEntities.size() > 0) {
            List<SRechargeCouponEntity> couponEntities = sRechargeCouponDao.findAll();
            List<RechargeConfDto> dtos = new ArrayList<RechargeConfDto>();
            List<SCouponConfigEntity> couponConfigEntities = sCouponConfigDao.findAllByRecharge();
            HashMap<Long, SCouponConfigEntity> couponMap = new HashMap<Long, SCouponConfigEntity>();
            for (SCouponConfigEntity entity : couponConfigEntities) {
                couponMap.put(entity.getCouponId(), entity);
            }

            for (SRechargeConfigEntity configEntity : configEntities) {
                RechargeConfDto rechargeConfDto = ConverterService.convert(configEntity, RechargeConfDto.class);
                for (SRechargeCouponEntity couponEntity : couponEntities) {
                    if (couponEntity.getId().getConfigId().equals(rechargeConfDto.getConfigId())) {
                        RechargeCouponDto counponDto = new RechargeCouponDto();
                        Long couponId = couponEntity.getId().getCouponId();
                        counponDto.setCouponId(couponId);
                        SCouponConfigEntity entity = couponMap.get(couponId);
                        if (null != entity) {
                            counponDto.setCouponName(entity.getCouponName());
                            counponDto.setValue(entity.getValue());
                        }

                        counponDto.setNum(couponEntity.getNum());
                        rechargeConfDto.addCoupon(counponDto);
                    }
                }
                dtos.add(rechargeConfDto);
            }
            dto.setTotalPages(1);
            dto.setTotalRecord(new Long(dtos.size()));
            dto.setList(dtos);
        }
    }

    /**
     * 保存
     */
    @Override
    @Transactional
    public ResultDto<String> save(RechargeConfDto dto) {
        SRechargeConfigEntity sameAmountEntity = sRechargeConfigDao.findSameAmount(dto.getAmount(), StatusEnum.NORMAL.getCode());

        Long configId = dto.getConfigId();
        // 修改
        if (configId != null) {
            if (null != sameAmountEntity && !configId.equals(sameAmountEntity.getConfigId())) {
                throw new WRWException(EErrorCode.BIZ_HAS_CONFIG);
            }

            SRechargeConfigEntity oldConfigEntity = sRechargeConfigDao.findOne(dto.getConfigId());
            if (null == oldConfigEntity) {
                throw new WRWException(EErrorCode.ENTITY_RECHARGE_CONFIG_NOT_EXIST);
            }
            oldConfigEntity.setNote(dto.getNote());
            oldConfigEntity.setAmount(dto.getAmount());
            oldConfigEntity.setModifyDate(new Date());
            oldConfigEntity.setModifyId(AuthorityContext.getCurrentUser().getUserId());
            sRechargeConfigDao.save(oldConfigEntity);
            // 删除原来的配置
            sRechargeCouponDao.deleteByConfigId(configId);

            List<SRechargeCouponEntity> couponEntities = new ArrayList<SRechargeCouponEntity>();
            List<Long> couponList = new ArrayList<Long>();
            for (RechargeCouponDto couponDto : dto.getCouponDtos()) {
                SRechargeCouponPK id = new SRechargeCouponPK(configId, couponDto.getCouponId());
                SRechargeCouponEntity couponEntity = new SRechargeCouponEntity();
                couponEntity.setId(id);
                couponEntity.setCouponName(couponDto.getCouponName());
                couponEntity.setNum(couponDto.getNum());
                couponEntities.add(couponEntity);
                couponList.add(couponDto.getCouponId());
            }
            sRechargeCouponDao.save(couponEntities);
            if (couponList.size() > 0) {
                sRechargeCouponDao.deleteByConfigIdAndNotCouponIds(configId, couponList);
            }
        } else {
            if (null != sameAmountEntity) {
                throw new WRWException(EErrorCode.BIZ_HAS_CONFIG);
            }
            // 新建
            SRechargeConfigEntity configEntity = ConverterService.convert(dto, SRechargeConfigEntity.class);
            configEntity.setStatus(RechargeConfStatus.NORMAL.getCode());
            configEntity.setCreateId(AuthorityContext.getCurrentUser().getUserId());
            sRechargeConfigDao.save(configEntity);
            List<SRechargeCouponEntity> couponEntities = new ArrayList<SRechargeCouponEntity>();
            for (RechargeCouponDto couponDto : dto.getCouponDtos()) {
                SRechargeCouponPK id = new SRechargeCouponPK(configEntity.getConfigId(), couponDto.getCouponId());
                SRechargeCouponEntity couponEntity = new SRechargeCouponEntity();
                couponEntity.setId(id);
                couponEntity.setCouponName(couponDto.getCouponName());
                couponEntity.setNum(couponDto.getNum());
                couponEntities.add(couponEntity);
            }
            sRechargeCouponDao.save(couponEntities);
        }
        return ResultDtoFactory.toAck(EErrorCode.SUCCESS);
    }

    /**
     * 单个查询
     */
    @Override
    public RechargeConfDto find(Long configId) {
        SRechargeConfigEntity entity = sRechargeConfigDao.findOne(configId);
        if (null == entity) {
            return null;
        }
        RechargeConfDto dto = ConverterService.convert(entity, RechargeConfDto.class);
        List<SRechargeCouponEntity> couponEntities = sRechargeCouponDao.findByConfigId(configId);
        List<SCouponConfigEntity> couponConfigEntities = sCouponConfigDao.findAllByRecharge();
        HashMap<Long, SCouponConfigEntity> couponMap = new HashMap<Long, SCouponConfigEntity>();
        for (SCouponConfigEntity configEntity : couponConfigEntities) {
            couponMap.put(configEntity.getCouponId(), configEntity);
        }
        for (SRechargeCouponEntity couponEntity : couponEntities) {

            RechargeCouponDto couponDto = new RechargeCouponDto();
            Long couponId = couponEntity.getId().getCouponId();
            couponDto.setCouponId(couponId);
            SCouponConfigEntity configEntity = couponMap.get(couponId);
            if (null != configEntity) {
                couponDto.setCouponName(configEntity.getCouponName());
                couponDto.setValue(configEntity.getValue());
            }
            couponDto.setNum(couponEntity.getNum());

            dto.addCoupon(couponDto);
        }
        return dto;
    }

    /**
     * 查询优惠券配置列表
     */
    @Override
    public void getCoupon(final CouponPageDto dto) {
        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.DESC, "createDate"));
        Page<SCouponConfigEntity> page = sCouponConfigDao.findAll(new Specification<SCouponConfigEntity>() {

            @Override
            public Predicate toPredicate(Root<SCouponConfigEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();

                // 如果传入优惠券配置ID，直接查询
                if (null != dto.getCouponId()) {
                    predicates.add(cb.equal(root.<Long> get("couponId"), dto.getCouponId()));
                } else {
                    if (WRWUtil.isNotEmpty(dto.getCouponName())) {
                        predicates.add(cb.like(root.<String> get("couponName"), "%" + dto.getCouponName() + "%"));
                    }
//                    predicates.add(cb.equal(root.<String> get("type"), CouponTypeEnum.RECHARGE.getKey()));
                    predicates.add(cb.equal(root.<String> get("status"), StatusEnum.NORMAL.getCode()));

                    if (null != dto.getValue()) {
                        predicates.add(cb.equal(root.<Long> get("value"), dto.getValue()));
                    }
                    if (null != dto.getCreateDateStrat()) {
                        predicates.add(cb.greaterThanOrEqualTo(root.<Date> get("createDate"), dto.getCreateDateStrat()));
                    }
                    if (null != dto.getCreateDateEnd()) {
                        predicates.add(cb.lessThanOrEqualTo(root.<Date> get("createDate"), dto.getCreateDateEnd()));
                    }
                    if (null != dto.getEffectDateStart()) {
                        predicates.add(cb.greaterThanOrEqualTo(root.<Date> get("effectDate"), dto.getEffectDateStart()));
                    }
                    if (null != dto.getEffectDateEnd()) {
                        predicates.add(cb.lessThanOrEqualTo(root.<Date> get("effectDate"), dto.getEffectDateEnd()));
                    }
                }

                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }
        }, pageable);

        dto.setTotalPages(page.getTotalPages());
        dto.setTotalRecord(page.getTotalElements());
        dto.setList(buildCouponDto(page.getContent()));
    }

    /**
     * Description: 构造dto列表
     *
     * @param entities
     * @return
     */
    private List<CouponDto> buildCouponDto(List<SCouponConfigEntity> entities) {
        List<CouponDto> dtos = new ArrayList<CouponDto>();
        for (SCouponConfigEntity SCouponConfigEntity : entities) {
            dtos.add(ConverterService.convert(SCouponConfigEntity, CouponDto.class));
        }
        return dtos;
    }

    @Override
    @Transactional
    public ResultDto<String> delete(Long configId) {
        SRechargeConfigEntity entity = sRechargeConfigDao.findOne(configId);
        if (null == entity) {
            throw new WRWException(EErrorCode.ENTITY_RECHARGE_CONFIG_NOT_EXIST);
        }
        entity.setStatus(RechargeConfStatus.DELETE.getCode());
        sRechargeConfigDao.save(entity);
        return ResultDtoFactory.toAck("删除成功");
    }
}
