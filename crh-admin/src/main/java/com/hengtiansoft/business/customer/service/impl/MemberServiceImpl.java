/*
 * Project Name: wrw-admin
 * File Name: MemberServiceImpl.java
 * Class Name: MemberServiceImpl
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
package com.hengtiansoft.business.customer.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.customer.dto.AccountRecordDto;
import com.hengtiansoft.business.customer.dto.CouponDetailDto;
import com.hengtiansoft.business.customer.dto.MAddressDto;
import com.hengtiansoft.business.customer.dto.MemberDetailDto;
import com.hengtiansoft.business.customer.dto.MemberDto;
import com.hengtiansoft.business.customer.dto.MemberOrderDetailDto;
import com.hengtiansoft.business.customer.dto.MemberSearchDto;
import com.hengtiansoft.business.customer.dto.OrderMainDetailDto;
import com.hengtiansoft.business.customer.dto.ProductDto;
import com.hengtiansoft.business.customer.service.MemberService;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.enumeration.EErrorCode;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.CurrencyUtils;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.EncryptUtil;
import com.hengtiansoft.wrw.dao.MAccountDao;
import com.hengtiansoft.wrw.dao.MAcctRechargeDao;
import com.hengtiansoft.wrw.dao.MAcctRecordDao;
import com.hengtiansoft.wrw.dao.MAddressDao;
import com.hengtiansoft.wrw.dao.MCouponDao;
import com.hengtiansoft.wrw.dao.MMemberDao;
import com.hengtiansoft.wrw.dao.MOrderDetailDao;
import com.hengtiansoft.wrw.dao.MOrderMainDao;
import com.hengtiansoft.wrw.dao.MPointsDetailDao;
import com.hengtiansoft.wrw.dao.PProductDao;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.dao.SRegionDao;
import com.hengtiansoft.wrw.entity.MAccountEnity;
import com.hengtiansoft.wrw.entity.MAcctRechargeEntity;
import com.hengtiansoft.wrw.entity.MAcctRecordEntity;
import com.hengtiansoft.wrw.entity.MAddressEntity;
import com.hengtiansoft.wrw.entity.MCouponEntity;
import com.hengtiansoft.wrw.entity.MMemberEntity;
import com.hengtiansoft.wrw.entity.MOrderDetailEntity;
import com.hengtiansoft.wrw.entity.MOrderMainEntity;
import com.hengtiansoft.wrw.entity.MPointsDetailEntity;
import com.hengtiansoft.wrw.entity.PProductEntity;
import com.hengtiansoft.wrw.entity.SOrgEntity;
import com.hengtiansoft.wrw.entity.SRegionEntity;
import com.hengtiansoft.wrw.enums.CouponState;
import com.hengtiansoft.wrw.enums.OrderStatus;
import com.hengtiansoft.wrw.enums.PaymentStatus;
import com.hengtiansoft.wrw.enums.PaymentType;
import com.hengtiansoft.wrw.enums.PointsTypeEnum;
import com.hengtiansoft.wrw.enums.ShipmentType;
import com.hengtiansoft.wrw.enums.StatusEnum;

/**
 * Class Name: MemberServiceImpl
 * Description: TODO
 * 
 * @author xianghuang
 */
@Service
public class MemberServiceImpl implements MemberService {

    private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Autowired
    private MMemberDao          memberDao;

    @Autowired
    private MAccountDao         accountDao;

    @Autowired
    private MAddressDao         addressDao;

    @Autowired
    private MOrderMainDao       orderMainDao;

    @Autowired
    private MOrderDetailDao     orderDetailDao;

    @Autowired
    private PProductDao         productDao;

    @Autowired
    private MAcctRecordDao      acctRecordDao;

    @Autowired
    private MAcctRechargeDao    acctRechargeDao;

    @Autowired
    private MPointsDetailDao    pointDetailDao;

    @Autowired
    private MCouponDao          couponDao;

    @Autowired
    private SRegionDao          regionDao;

    @Autowired
    private SOrgDao             orgDao;

    @Override
    public void search(final MemberSearchDto dto) {

        PageRequest pageable = new PageRequest(dto.getCurrentPage() - 1, dto.getPageSize(), new Sort(Direction.DESC, "userId"));

        final List<Long> memberIds = new ArrayList<Long>();
        List<MAccountEnity> accountEnities = null;

        if (null != dto.getBalanceStart() && null != dto.getBalanceEnd()) {
            accountEnities = accountDao.findByBalanceStartAndBalanceEnd(CurrencyUtils.rmbYuanToFen(dto.getBalanceStart()),
                    CurrencyUtils.rmbYuanToFen(dto.getBalanceEnd()));

        } else if (null == dto.getBalanceStart() && null != dto.getBalanceEnd()) {
            accountEnities = accountDao.findByBalanceEnd(CurrencyUtils.rmbYuanToFen(dto.getBalanceEnd()));
        } else if (null != dto.getBalanceStart() && null == dto.getBalanceEnd()) {
            accountEnities = accountDao.findByBalanceStart(CurrencyUtils.rmbYuanToFen(dto.getBalanceStart()));
        }

        if (accountEnities != null && accountEnities.size() == 0) {
            dto.setTotalRecord(0L);
            dto.setTotalPages(0);
            dto.setList(new ArrayList<MemberDto>());
            return;
        }

        if (accountEnities != null) {
            for (MAccountEnity mAccountEnity : accountEnities) {
                memberIds.add(mAccountEnity.getMemberId());
            }
        }

        Page<MMemberEntity> page = memberDao.findAll(new Specification<MMemberEntity>() {
            @Override
            public Predicate toPredicate(Root<MMemberEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();

                if (StringUtils.isNotBlank(dto.getPhone())) {
                    Predicate p = cb.like(cb.lower(root.<String> get("loginId")), dto.getPhone().trim() + "%");
                    predicates.add(p);
                }
                if (StringUtils.isNotBlank(dto.getMemberName())) {
                    Predicate p = cb.like(cb.lower(root.<String> get("memberName")), "%" + dto.getMemberName().trim() + "%");
                    predicates.add(p);
                }

                if (StringUtils.isNotBlank(dto.getDateStart()) && StringUtils.isNotBlank(dto.getDateEnd())) {
                    Predicate p = cb.between(root.<Date> get("createDate"), DateTimeUtil.parseDate(dto.getDateStart(), DateTimeUtil.SIMPLE_YMD),
                            DateTimeUtil.dateParse(dto.getDateEnd() + " 23:59:59"));
                    predicates.add(p);
                } else if (StringUtils.isNotBlank(dto.getDateStart()) && StringUtils.isBlank(dto.getDateEnd())) {
                    Predicate p = cb.greaterThanOrEqualTo(root.<Date> get("createDate"),
                            DateTimeUtil.parseDate(dto.getDateStart(), DateTimeUtil.SIMPLE_YMD));
                    predicates.add(p);
                } else if (StringUtils.isBlank(dto.getDateStart()) && StringUtils.isNotBlank(dto.getDateEnd())) {
                    Predicate p = cb.lessThanOrEqualTo(root.<Date> get("createDate"), DateTimeUtil.dateParse(dto.getDateEnd() + " 23:59:59"));
                    predicates.add(p);
                }

                if (null != dto.getPointStart() && null != dto.getPointEnd()) {
                    Predicate p = cb.between(root.<Long> get("point"), dto.getPointStart(), dto.getPointEnd());
                    predicates.add(p);
                } else if (null != dto.getPointStart() && null == dto.getPointEnd()) {
                    Predicate p = cb.greaterThanOrEqualTo(root.<Long> get("point"), dto.getPointStart());
                    predicates.add(p);
                } else if (null == dto.getPointStart() && null != dto.getPointEnd()) {
                    Predicate p = cb.lessThanOrEqualTo(root.<Long> get("point"), dto.getPointEnd());
                    predicates.add(p);
                }

                if (memberIds.size() > 0) {
                    predicates.add(root.<Integer> get("userId").in(memberIds));
                }

                Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                query.where(predicate);
                return query.getRestriction();
            }
        }, pageable);

        dto.setTotalRecord(page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setList(buildMemberDtos(page.getContent(), accountEnities));
    }

    private List<MemberDto> buildMemberDtos(List<MMemberEntity> content, List<MAccountEnity> acctList) {
        List<MemberDto> list = new ArrayList<MemberDto>();
        Map<Long, MAccountEnity> accMap = new HashMap<Long, MAccountEnity>();

        if (org.apache.commons.collections.CollectionUtils.isEmpty(acctList)) {
            List<MAccountEnity> accList = accountDao.findAll();
            for (MAccountEnity acct : accList) {
                accMap.put(acct.getMemberId(), acct);
            }

        } else {
            for (MAccountEnity acct : acctList) {
                accMap.put(acct.getMemberId(), acct);
            }
        }

        for (MMemberEntity member : content) {
            MemberDto dto = new MemberDto();
            dto.setMemberNo(String.valueOf(member.getUserId()));
            dto.setPhone(member.getLoginId());
            dto.setMemberName(member.getMemberName());

            MAccountEnity accountEnity = accMap.get(member.getUserId());
            if (null != accountEnity) {
                dto.setBalance(String.valueOf(CurrencyUtils.rmbFenToYuan(accountEnity.getBalance())));
            } else {
                dto.setBalance("0");
            }
            dto.setPoint(member.getPoint());
            dto.setRegisterDate(member.getCreateDate());

            list.add(dto);
        }
        return list;
    }

    private String getRegionName(Long addressId, Integer regionId, MAddressDto addressDto) {
        if (null != regionId) {
            SRegionEntity regionEntity = regionDao.findOne(regionId);
            if (regionEntity != null) {
                addressDto.setRegionId(regionEntity.getParentId());
                return regionEntity.getName();
            } else {
                if (logger.isInfoEnabled()) {
                    logger.info(String.format("根据regionId：%s 找到regionEntity为空", regionId));
                }
                return "";
            }
        } else {
            if (logger.isInfoEnabled()) {
                logger.info(String.format("regionId为空，对应Id:%s", addressId));
            }
            return "";
        }
    }

    private void setRegion(Long addressId, Integer regionId, MAddressDto addressDto) {
        addressDto.setArea(getRegionName(addressId, regionId, addressDto));
        addressDto.setCity(getRegionName(addressId, addressDto.getRegionId(), addressDto));
        addressDto.setProvince(getRegionName(addressId, addressDto.getRegionId(), addressDto));
    }

    @Override
    public MemberDetailDto get(Long id) {
        MemberDetailDto dto = new MemberDetailDto();

        MMemberEntity memberEntity = memberDao.findOne(id);
        if (memberEntity == null) {
            if (logger.isInfoEnabled()) {
                logger.info(String.format("根据memberId：%s没有找到对应的MMemberEntity", id));
            }
            throw new WRWException(EErrorCode.ENTITY_MEMBER_NOT_EXIST);
        } else {
            dto.setPhone(memberEntity.getLoginId());
            dto.setMemberName(memberEntity.getMemberName());
            dto.setPoint(memberEntity.getPoint());
            dto.setMemberNo(memberEntity.getUserId());
            dto.setRegisterDate(memberEntity.getCreateDate());

            List<MAddressEntity> addresseEntity = addressDao.findByMemberIdAndStatus(id, StatusEnum.NORMAL.getCode());
            List<MAddressDto> addresses = new ArrayList<MAddressDto>();
            for (MAddressEntity mAddressEntity : addresseEntity) {
                MAddressDto addressDto = new MAddressDto();
                addressDto.setPhone(mAddressEntity.getAddressPhone());
                addressDto.setAddress(mAddressEntity.getAddress());
                addressDto.setAddressContact(mAddressEntity.getAddressContact());
                addressDto.setDefFlag(mAddressEntity.getDefFlag());
                // addressDto.setZipcode(mAddressEntity.getZipcode());
                addressDto.setAreaName(mAddressEntity.getAreaName());

                setRegion(mAddressEntity.getId(), mAddressEntity.getRegionId(), addressDto);

                addresses.add(addressDto);
            }

            // 地址
            dto.setAddresses(addresses);

            // 订单
            List<MOrderMainEntity> orderMainEntities = orderMainDao.findByMemberId(id); // orderMainDao.findByMemberIdAndStatus(id,
                                                                                        // OrderStatus.COMPLETE.getKey());
            Double consumeNum = 0d;

            List<OrderMainDetailDto> orderMainDetailDtos = new ArrayList<OrderMainDetailDto>();
            for (MOrderMainEntity mOrderMainEntity : orderMainEntities) {
                OrderMainDetailDto orderMainDetailDto = ConverterService.convert(mOrderMainEntity, new OrderMainDetailDto());
                orderMainDetailDto.setStatus(OrderStatus.getValue(mOrderMainEntity.getStatus()));
                orderMainDetailDto.setPaymentType(PaymentType.getValue(mOrderMainEntity.getPaymentType()));
                orderMainDetailDto.setTotalAmount(String.valueOf(CurrencyUtils.rmbFenToYuan(mOrderMainEntity.getTotalAmount())));
                orderMainDetailDto.setActualAmount(String.valueOf(CurrencyUtils.rmbFenToYuan(mOrderMainEntity.getActualAmount())));
                orderMainDetailDtos.add(orderMainDetailDto);
            }

            dto.setOrders(orderMainDetailDtos);
            dto.setConsumeNum(consumeNum);

            // 积分
            List<MPointsDetailEntity> pointsDetailEntities = pointDetailDao.findByMemberId(id);
            for (MPointsDetailEntity mPointsDetailEntity : pointsDetailEntities) {
                mPointsDetailEntity.setType(PointsTypeEnum.getValue(mPointsDetailEntity.getType()));
            }
            dto.setPoints(pointsDetailEntities);
            dto.setPoint(memberEntity.getPoint());

            MAccountEnity accountEnity = accountDao.findByMemberId(id);
            if (accountEnity == null) {
                if (logger.isInfoEnabled()) {
                    logger.info(String.format("根据memberId：%s没有找到对应的MAccountEnity", id));
                }
                dto.setBalance("0.0");
            } else {
                // 账户余额
                List<MAcctRecordEntity> recordEntities = acctRecordDao.findByAcctId(accountEnity.getAcctId());
                List<MAcctRechargeEntity> acctRechargeEntities = acctRechargeDao.findByAcctId(accountEnity.getAcctId());
                List<AccountRecordDto> accountRecordDtos = new ArrayList<AccountRecordDto>();
                for (MAcctRecordEntity accountRecordDto : recordEntities) {
                    AccountRecordDto recordDto = new AccountRecordDto();
                    recordDto.setType("消费");
                    recordDto.setAmount(String.valueOf(CurrencyUtils.rmbFenToYuan(accountRecordDto.getChangeValue())));
                    if (StringUtils.isNotBlank(accountRecordDto.getOrderId())) {
                        MOrderMainEntity orderMainEntity = orderMainDao.findOne(accountRecordDto.getOrderId());
                        if (null != orderMainEntity) {
                            recordDto.setPaymentType(PaymentType.getValue(orderMainEntity.getPaymentType()));
                        } else {
                            recordDto.setPaymentType("-");
                        }
                    }

                    recordDto.setDate(accountRecordDto.getCreateDate());
                    recordDto.setOrderId(accountRecordDto.getOrderId());
                    accountRecordDtos.add(recordDto);
                }

                for (MAcctRechargeEntity acctRechargeEntity : acctRechargeEntities) {
                    AccountRecordDto recordDto = new AccountRecordDto();
                    recordDto.setType("充值");
                    recordDto.setAmount(String.valueOf(CurrencyUtils.rmbFenToYuan(acctRechargeEntity.getAmount())));
                    recordDto.setPaymentType(PaymentType.getValue(acctRechargeEntity.getPaymentType()));
                    recordDto.setDate(acctRechargeEntity.getPayDate());
                    recordDto.setOrderId("-");
                    accountRecordDtos.add(recordDto);
                }
                Collections.sort(accountRecordDtos, Collections.reverseOrder());
                dto.setBalances(accountRecordDtos);
                dto.setBalance(String.valueOf(CurrencyUtils.rmbFenToYuan(accountEnity.getBalance())));
            }

            // 优惠券记录
            List<MCouponEntity> couponEntities = couponDao.findByMemberId(id);
            Long couponNum = 0L;
            for (MCouponEntity mCouponEntity : couponEntities) {
                if (CouponState.INVALID.getKey().equals(mCouponEntity.getStatus()) || CouponState.VALID.getKey().equals(mCouponEntity.getStatus())) {
                    couponNum++;
                }
            }

            List<CouponDetailDto> couponDetailDtos = new ArrayList<CouponDetailDto>();
            for (MCouponEntity mCouponEntity : couponEntities) {
                CouponDetailDto couponDetailDto = ConverterService.convert(mCouponEntity, new CouponDetailDto());
                couponDetailDto.setStatus(CouponState.getValue(mCouponEntity.getStatus()));
                couponDetailDto.setValue(String.valueOf(CurrencyUtils.rmbFenToYuan(mCouponEntity.getValue())));
                couponDetailDtos.add(couponDetailDto);
            }

            dto.setCurrentCoupoints(couponNum);
            dto.setCoupons(couponDetailDtos);

            return dto;
        }

    }

    @Override
    public MemberOrderDetailDto getOrderDetail(String orderId) {
        MOrderMainEntity orderMainEntity = orderMainDao.findOne(orderId);

        MemberOrderDetailDto dto = ConverterService.convert(orderMainEntity, new MemberOrderDetailDto());
        dto.setReceivingDate(DateTimeUtil.parseDateToString(orderMainEntity.getReceivingDate(), DateTimeUtil.SIMPLE_FMT));

        if (null != orderMainEntity.getOrgId()) {
            SOrgEntity orgEntity = orgDao.findOne(orderMainEntity.getOrgId());
            if (orgEntity != null) {
                dto.setOrgCode(orgEntity.getOrgCode());
            } else {
                dto.setOrgCode(null);
            }
        }

        /*
         * dto.setTotalNum(orderMainEntity.getTotalNum());
         * dto.setContact(orderMainEntity.getContact());
         * dto.setAddress(orderMainEntity.getAddress());
         * dto.setPhone(orderMainEntity.getPhone());
         * dto.setOrgName(orderMainEntity.getOrgName());
         * dto.setOrgId(orderMainEntity.getOrgId());
         */

        dto.setTotalAmount(String.valueOf(CurrencyUtils.rmbFenToYuan(orderMainEntity.getTotalAmount())));
        dto.setCouponAmount(String.valueOf(CurrencyUtils.rmbFenToYuan(orderMainEntity.getCouponAmount())));
        dto.setActualAmount(String.valueOf(CurrencyUtils.rmbFenToYuan(orderMainEntity.getActualAmount())));

        dto.setStatus(OrderStatus.getValue(dto.getStatus()));
        dto.setPaymentType(PaymentType.getValue(dto.getPaymentType()));
        dto.setPaymentStatus(PaymentStatus.getValue(dto.getPaymentStatus()));
        dto.setShipmentType(ShipmentType.getValue(dto.getShipmentType()));

        List<MOrderDetailEntity> orderDetailDaos = orderDetailDao.findByOrderId(orderId);
        List<ProductDto> productDtos = new ArrayList<ProductDto>();
        for (MOrderDetailEntity mOrderDetailEntity : orderDetailDaos) {
            if (null != mOrderDetailEntity.getProductId()) {
                PProductEntity productEntity = productDao.findOne(mOrderDetailEntity.getProductId());
                ProductDto productDto = ConverterService.convert(productEntity, new ProductDto());
                productDto.setCostPrice(CurrencyUtils.rmbFenToYuan(productEntity.getPrice()));
                productDto.setBuyNum(mOrderDetailEntity.getNum());
                productDtos.add(productDto);
            }

        }
        dto.setList(productDtos);
        return dto;
    };

    @Override
    @Transactional
    public ResultDto<String> resetPwd(Long id) {
        MMemberEntity memberEntity = memberDao.findOne(id);
        if (memberEntity != null) {
            memberDao.resetPwd(id, EncryptUtil.encryptMd5(EncryptUtil.encryptMd5("123456"), String.valueOf(memberEntity.getLoginId())));
            return ResultDtoFactory.toAck("密码重置成功");
        } else {
            throw new WRWException(EErrorCode.ENTITY_MEMBER_NOT_EXIST);
        }
    }

    @Override
    public MMemberEntity findByLoginId(String loginId, String status) {
        return memberDao.findByLoginId(loginId, status);
    }

    @Override
    public List<MMemberEntity> findAllMember() {
        return memberDao.findAllMember();
    }

    @Override
    public List<MMemberEntity> findAllByPhones(List<String> memberPhones) {
        return memberDao.findAllByPhones(memberPhones);
    }

    @Override
    public MMemberEntity findByUserId(Long userId) {
        return memberDao.findByUserId(userId);
    }

    @Override
    public MMemberEntity findById(Long memberId) {
        return memberDao.findOne(memberId);
    }

}
