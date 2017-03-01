package com.hengtiansoft.business.member.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.business.member.dto.MemberCouponListDto;
import com.hengtiansoft.business.member.dto.MemberCouponSearchDto;
import com.hengtiansoft.business.member.dto.MemberDetailDto;
import com.hengtiansoft.business.member.dto.MemberIsVipDto;
import com.hengtiansoft.business.member.dto.MemberListDto;
import com.hengtiansoft.business.member.dto.MemberOrderDetailDto;
import com.hengtiansoft.business.member.dto.MemberOrderListDto;
import com.hengtiansoft.business.member.dto.MemberOrderSearchDto;
import com.hengtiansoft.business.member.dto.MemberSearchDto;
import com.hengtiansoft.business.member.service.MMemberService;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.KdOrderMainDao;
import com.hengtiansoft.wrw.dao.KdOrderReturnDao;
import com.hengtiansoft.wrw.dao.KdProductDao;
import com.hengtiansoft.wrw.dao.KdTeamBuyProductDao;
import com.hengtiansoft.wrw.dao.KdTwentyFourHoursDao;
import com.hengtiansoft.wrw.dao.MMemberDao;
import com.hengtiansoft.wrw.dao.MOrderMainDao;
import com.hengtiansoft.wrw.dao.MOrderReturnDao;
import com.hengtiansoft.wrw.dao.MVipDao;
import com.hengtiansoft.wrw.entity.KdProductEntity;
import com.hengtiansoft.wrw.entity.KdTeamBuyProductEntity;
import com.hengtiansoft.wrw.entity.KdTwentyFourHoursEntity;
import com.hengtiansoft.wrw.entity.MMemberEntity;
import com.hengtiansoft.wrw.entity.MVipOrderEntity;
import com.hengtiansoft.wrw.enums.CouponConfigStatusEnum;
import com.hengtiansoft.wrw.enums.CouponState;
import com.hengtiansoft.wrw.enums.KdOrderStatusEnum;
import com.hengtiansoft.wrw.enums.KdOrderTypeEnum;
import com.hengtiansoft.wrw.enums.OrderStatus;
import com.hengtiansoft.wrw.enums.ProductTypeEnum;
import com.hengtiansoft.wrw.enums.SexEnum;
import com.hengtiansoft.wrw.enums.StatusEnum;
import com.hengtiansoft.wrw.enums.VipOrderStatusEnum;

@Service
public class MMemberServiceImpl implements MMemberService {
    
    private static final Long ONE_YEAR_MILLISECOND = 60L * 60 * 24 * 365 * 1000;
    
    private static final String[] REMOVE_LY_ORDER_STATUS = {OrderStatus.WAIT_PAY.getKey(), OrderStatus.CANCELED.getKey()};
    
    private static final String[] REMOVE_KD_ORDER_STATUS = {KdOrderStatusEnum.WAIT_PAY.getCode(), KdOrderStatusEnum.CANCELED.getCode()};
    
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MVipDao mVipDao;
    @Autowired
    private MMemberDao mMemberDao;
    @Autowired
    private MOrderMainDao mOrderMainDao;
    @Autowired
    private MOrderReturnDao mOrderReturnDao;
    @Autowired
    private KdOrderMainDao kdOrderMainDao;
    @Autowired
    private KdOrderReturnDao kdOrderReturnDao;
    @Autowired
    private KdProductDao kdProductDao;
    @Autowired
    private KdTeamBuyProductDao kdTeamBuyProductDao;
    @Autowired
    private KdTwentyFourHoursDao kdTwentyFourHoursDao;

    @SuppressWarnings("unchecked")
    @Override
    public void searchMember(final MemberSearchDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder conditionSql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");
        StringBuilder sql = new StringBuilder(
                " select id,login_id,cust_name,sex,create_date from m_member where 1=1 ");
        countSql.append(" select count(1) from ( ").append(sql);
        if (!WRWUtil.isEmpty(dto.getVip())) {
            String vip = dto.getVip().trim();
            StringBuilder vipSql = new StringBuilder(
                    " (select m.ID from m_member m left join m_vip_order mv "
                    + " on m.ID = mv.MEMBER_ID where mv.ID is not null and mv.`STATUS` = '2' and mv.BUY_DATE is not null and "
                    + " date_add(mv.BUY_DATE, INTERVAL 1 YEAR) > now())");
            if (StatusEnum.NORMAL.getCode().equals(vip)) {
                // 查询是VIP的用户
                conditionSql.append(" and id in ");
            } else {
                // 查询非VIP用户
                conditionSql.append(" and id not in ");
            }
            conditionSql.append(vipSql);
        }
        // 创建时间
        if (!WRWUtil.isEmpty(dto.getStartDate())) {
            conditionSql.append("AND CREATE_DATE >= :START ");
            param.put("START", DateTimeUtil.getDateBegin(dto.getStartDate()));
        }
        if (!WRWUtil.isEmpty(dto.getEndDate())) {
            conditionSql.append("AND CREATE_DATE <= :END ");
            param.put("END", DateTimeUtil.getDateEnd(dto.getEndDate()));
        }
        // 手机号
        if (!WRWUtil.isEmpty(dto.getMobile())) {
            String msg = dto.getMobile().trim();
            char escape = '\\';
            msg = msg.replace("\\", escape + "\\");
            msg = msg.replace("%", escape + "%");
            msg = msg.replace("_", escape + "_");
            conditionSql.append(" AND REPLACE(login_id,' ','') LIKE REPLACE(:mobile,' ','') ");
            param.put("mobile", "%" + msg + "%");
        }
        // 昵称
        if (!WRWUtil.isEmpty(dto.getCustName())) {
            String msg = dto.getCustName().trim();
            char escape = '\\';
            msg = msg.replace("\\", escape + "\\");
            msg = msg.replace("%", escape + "%");
            msg = msg.replace("_", escape + "_");
            conditionSql.append(" AND REPLACE(cust_name,' ','') LIKE REPLACE(:custName,' ','') ");
            param.put("custName", "%" + msg + "%");
        }
        // 性别
        if (!WRWUtil.isEmpty(dto.getSex())) {
            conditionSql.append(" and sex = :sex ");
            param.put("sex", dto.getSex());
        }
        conditionSql.append(" and status = '1' order by create_date desc,id desc ");
        Query query = entityManager.createNativeQuery(sql.append(conditionSql).toString());
        QueryUtil.processParamForQuery(query, param);
        Query countQuery = entityManager.createNativeQuery(countSql.append(conditionSql).append(" ) a").toString());
        QueryUtil.processParamForQuery(countQuery, param);
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        dto.setTotalRecord(totalRecord.longValue());
        dto.setTotalPages(totalRecord.intValue() % dto.getPageSize() == 0 ? totalRecord.intValue() / dto.getPageSize()
                : ((totalRecord.intValue() / dto.getPageSize()) + 1));
        dto.setList(buildMemberList(query.getResultList()));
    }

    private List<MemberListDto> buildMemberList(List<Object[]> list) {
        List<MemberListDto> memberList = new ArrayList<MemberListDto>();
        for (Object[] obj : list) {
            MemberListDto dto = new MemberListDto();
            Long memberId = WRWUtil.objToLong(obj[0]);
            dto.setMemberId(memberId);
            if (isVip(memberId).getIsVip()) {
                dto.setVip("是");
            } else {
                dto.setVip("否");
            }
            dto.setMobile(WRWUtil.objToString(obj[1]));
            dto.setCustName(WRWUtil.objToString(obj[2]));
            dto.setSex(SexEnum.getTextByCode(WRWUtil.objToString(obj[3])));
            dto.setCreateDate(DateTimeUtil.parseDateToString((Date) obj[4], DateTimeUtil.SIMPLE_M_D));
            memberList.add(dto);
        }
        return memberList;
    }

    private MemberIsVipDto isVip(Long memberId) {
        List<MVipOrderEntity> vipList = mVipDao.findByMemberIdAndStatusOrderByBuyDateDesc(memberId, VipOrderStatusEnum.PAYED.getCode());
        Date nowDate = new Date();
        MemberIsVipDto dto = new MemberIsVipDto();
        if (!vipList.isEmpty()) {
            if (nowDate.getTime() - vipList.get(0).getBuyDate().getTime() <= ONE_YEAR_MILLISECOND) {
                // 没有过期则是会员
                dto.setIsVip(true);
                dto.setVipOrder(vipList.get(0));
                return dto;
            }
        }
        dto.setIsVip(false);
        return dto;
    }

    @Override
    public Long getRegisterCount() {
        return mMemberDao.countByStatus(StatusEnum.NORMAL.getCode());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void searchCoupon(final MemberCouponSearchDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder conditionSql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");
        StringBuilder sql = new StringBuilder(
                " select mc.COUPON_NAME,mc.VALUE,mc.CREATE_DATE,mc.EFFECT_DATE,mc.INVALID_DATE,sc.`STATUS` s_status,mc.`STATUS` m_status"
                + " from m_coupons mc left join s_coupon_config sc on mc.COUP_CON_ID = sc.COUPON_ID where 1=1 ");
        countSql.append(" select count(1) from ( ").append(sql);
        conditionSql.append("AND mc.member_id = :memberId ");
        param.put("memberId", dto.getMemberId());
        conditionSql.append(" order by mc.create_date desc,mc.COUPON_ID desc ");
        Query query = entityManager.createNativeQuery(sql.append(conditionSql).toString());
        QueryUtil.processParamForQuery(query, param);
        Query countQuery = entityManager.createNativeQuery(countSql.append(conditionSql).append(" ) a").toString());
        QueryUtil.processParamForQuery(countQuery, param);
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        dto.setTotalRecord(totalRecord.longValue());
        dto.setTotalPages(totalRecord.intValue() % dto.getPageSize() == 0 ? totalRecord.intValue() / dto.getPageSize()
                : ((totalRecord.intValue() / dto.getPageSize()) + 1));
        dto.setList(buildCouponList(query.getResultList()));
    }

    private List<MemberCouponListDto> buildCouponList(List<Object[]> list) {
        List<MemberCouponListDto> couponList = new ArrayList<MemberCouponListDto>();
        for (Object[] obj : list) {
            MemberCouponListDto dto = new MemberCouponListDto();
            dto.setCouponName(WRWUtil.objToString(obj[0]));
            dto.setCouponPrice(WRWUtil.transFenToYuan2String(WRWUtil.objToLong(obj[1])));
            dto.setContainDate(DateTimeUtil.parseDateToString((Date) obj[2], DateTimeUtil.SIMPLE_M_D_H_M_S));
            dto.setEffectiveDate(DateTimeUtil.parseDateToString((Date) obj[3], DateTimeUtil.YYYY_M_D) + "至" + DateTimeUtil.parseDateToString
                    ((Date) obj[4], DateTimeUtil.YYYY_M_D));
            dto.setStatus(CouponConfigStatusEnum.getTextByCode(WRWUtil.objToString(obj[5])));
            dto.setUseStatus(CouponState.getValue(WRWUtil.objToString(obj[6])));
            couponList.add(dto);
        }
        return couponList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void searchMemberOrder(final MemberOrderSearchDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder countSql = new StringBuilder("");
        StringBuilder sql = new StringBuilder(
                " SELECT * FROM ( "
                + " SELECT * FROM ( "
                + " SELECT O.ORDER_ID ORDER_ID,O.CREATE_DATE CREATE_DATE,M.LOGIN_ID PHONE,GROUP_CONCAT(P.PRODUCT_CODE SEPARATOR ',/,') PRODUCT_CODE, "
                + " GROUP_CONCAT(P.PRODUCT_NAME SEPARATOR '#&#') PRODUCT_NAME, "
                + " GROUP_CONCAT(SO.ORG_NAME SEPARATOR ',/,') ORG_NAME, CONCAT(',', GROUP_CONCAT(P.TYPE_ID SEPARATOR ','),',') TYPE_ID, "
                + " O.TOTAL_AMOUNT ORDER_AMOUNT,O.STATUS STATUS, "
                + " VIP.ID VIP_ID,'0' ORDER_TYPE  "
                + " FROM M_ORDER_MAIN O LEFT JOIN M_ORDER_DETAIL OD ON O.ORDER_ID = OD.ORDER_ID  "
                + " LEFT JOIN P_PRODUCT P ON P.PRODUCT_ID = OD.PRODUCT_ID LEFT JOIN S_ORG SO ON SO.ORG_ID = P.ORG_ID LEFT JOIN M_MEMBER M ON M.ID = O.MEMBER_ID  "
                + " LEFT JOIN M_VIP_ORDER VIP ON VIP.ORDER_MAIN_ID = O.ORDER_ID  "
                + " WHERE 1=1 AND O.MEMBER_ID = :memberId group by O.ORDER_ID) ORD "
                + " UNION ALL "
                + " SELECT * FROM ( "
                + " SELECT O.ORDER_ID ORDER_ID,O.CREATE_DATE CREATE_DATE,O.PHONE PHONE,  "
                + " GROUP_CONCAT(OD.PRODUCT_ID SEPARATOR ',') PRODUCT_CODE,'' PRODUCT_NAME, "
                + " '吾儿乐园' ORG_NAME,O.TYPE TYPE_ID,SUM(OD.REAL_PRICE) ORDER_AMOUNT,O.STATUS STATUS,VIP.ID VIP_ID,'1' ORDER_TYPE "
                + " FROM KD_ORDER_MAIN O LEFT JOIN KD_ORDER_DETAIL OD ON O.ORDER_ID = OD.ORDER_ID  "
                + " LEFT JOIN M_VIP_ORDER VIP ON O.ORDER_ID = VIP.ORDER_MAIN_ID  "
                + " WHERE 1=1 AND O.MEMBER_ID = :memberId GROUP BY O.ORDER_ID) ORD) A "
                + " ORDER BY A.CREATE_DATE DESC ");
        countSql.append(" select count(1) from ( ").append(sql);
        param.put("memberId", dto.getMemberId());
        Query query = entityManager.createNativeQuery(sql.toString());
        QueryUtil.processParamForQuery(query, param);
        Query countQuery = entityManager.createNativeQuery(countSql.append(" ) B").toString());
        QueryUtil.processParamForQuery(countQuery, param);
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        dto.setTotalRecord(totalRecord.longValue());
        dto.setTotalPages(totalRecord.intValue() % dto.getPageSize() == 0 ? totalRecord.intValue() / dto.getPageSize()
                : ((totalRecord.intValue() / dto.getPageSize()) + 1));
        dto.setList(buildMemberOrderList(query.getResultList()));
    }

    private List<MemberOrderListDto> buildMemberOrderList(List<Object[]> list) {
        List<MemberOrderListDto> orderList = new ArrayList<MemberOrderListDto>();
        for (Object[] obj : list) {
            MemberOrderListDto dto = new MemberOrderListDto();
            dto.setOrderId(WRWUtil.objToString(obj[0]));
            dto.setCreateDate(DateTimeUtil.parseDateToString((Date) obj[1], DateTimeUtil.SIMPLE_FMT));
            dto.setMobile(WRWUtil.objToString(obj[2]));
            dto.setOrgName(WRWUtil.objToString(obj[5]));
            dto.setOrderAmount(WRWUtil.transFenToYuan2String(WRWUtil.objToLong(obj[7])));
            String vipId = WRWUtil.objToString(obj[9]);
            if (WRWUtil.isEmpty(vipId)) {
                dto.setVip("否");
            } else {
                dto.setVip("是");
            }
            String orderType = WRWUtil.objToString(obj[10]);
            dto.setOrderType(orderType);
            if (orderType.equals("0")) {
                dto.setProductCode(WRWUtil.objToString(obj[3]));
                dto.setProductName(WRWUtil.objToString(obj[4]));
                String types = WRWUtil.objToString(obj[6]);
                if (StringUtils.isNotEmpty(types)) {
                    String[] typesIds = types.split(",");
                    StringBuilder productType = new StringBuilder("");
                    for (String typesId : typesIds) {
                        if (!typesId.equals("")) {
                            productType.append(ProductTypeEnum.getValue(WRWUtil.objToLong(typesId)) + "/");
                        }
                    }
                    dto.setProductType(productType.toString());
                } else {
                    dto.setProductType("/");
                }
                dto.setOrderStatus(OrderStatus.getValue(WRWUtil.objToString(obj[8])));
            } else {
                dto.setProductType("实物类商品/");
                if (obj[3] != null && obj[6] != null) {
                    String[] productIds = WRWUtil.objToString(obj[3]).split(",");
                    String productName = "";
                    String productCode = "";
                    if (KdOrderTypeEnum.NORMAL.getCode().equals(WRWUtil.objToString(obj[6]))) {
                        for (int i = 0; i < productIds.length; i++) {
                            KdProductEntity product = kdProductDao.findOne(Long.parseLong(productIds[i]));
                            if (i == productIds.length - 1) {
                                productName += product.getPname();
                                productCode += product.getPcode();
                            } else {
                                productName += product.getPname() + "#&#";
                                productCode += product.getPcode() + "#&#";
                            }
                        }
                    } else if (KdOrderTypeEnum.TEAM_BUY.getCode().equals(WRWUtil.objToString(obj[6]))) {
                        for (int i = 0; i < productIds.length; i++) {
                            KdTeamBuyProductEntity teambuy = kdTeamBuyProductDao.findOne(Long.parseLong(productIds[i]));
                            if (i == productIds.length - 1) {
                                productName += teambuy.getName();
                                productCode += kdProductDao.findOne(teambuy.getProductId()).getPcode();
                            } else {
                                productName += teambuy + "#&#";
                                productCode += kdProductDao.findOne(teambuy.getProductId()).getPcode() + "#&#";
                            }
                        }
                    } else {
                        for (int i = 0; i < productIds.length; i++) {
                            if (i == productIds.length - 1) {
                                KdTwentyFourHoursEntity tf = kdTwentyFourHoursDao.findOne(Long.parseLong(productIds[i]));
                                if(tf != null){
                                    productName += tf.getName();
                                    productCode += kdProductDao.findOne(tf.getProductId()).getPcode();
                                }
                            } else {
                                KdTwentyFourHoursEntity tf = kdTwentyFourHoursDao.findOne(Long.parseLong(productIds[i]));
                                if(tf != null){
                                    productName += tf.getName() + "#&#";
                                    productCode += kdProductDao.findOne(tf.getProductId()).getPcode() + "#&#";
                                }
                            }
                        }
                    }
                    dto.setProductName(productName);
                    dto.setProductCode(productCode);
                } else {
                    dto.setProductName("");
                    dto.setProductCode("");
                }
                dto.setOrderStatus(KdOrderStatusEnum.getText(WRWUtil.objToString(obj[8])));
            }
            orderList.add(dto);
        }
        return orderList;
    }

    @Override
    public MemberDetailDto getMemberDetail(Long memberId) {
        MemberDetailDto dto = new MemberDetailDto();
        MMemberEntity member = mMemberDao.findOne(memberId);
        if (member != null) {
            dto = ConverterService.convert(member, MemberDetailDto.class);
            dto.setMobile(member.getLoginId());
            dto.setSex(SexEnum.getTextByCode(member.getGender()));
            dto.setCreateDate(DateTimeUtil.parseDateToString(member.getCreateDate(), DateTimeUtil.SIMPLE_M_D));
            MemberIsVipDto vipDto = isVip(memberId);
            if (vipDto.getIsVip()) {
                dto.setVip("是");
                Date buyDate = vipDto.getVipOrder().getBuyDate();
                Date oneYearLateDate = DateTimeUtil.getDateEndAddYear(1);
                dto.setVipDate(DateTimeUtil.parseDateToString(buyDate, DateTimeUtil.YYYY_M_D) + "至" + DateTimeUtil.parseDateToString(
                        oneYearLateDate, DateTimeUtil.YYYY_M_D));
            } else {
                dto.setVip("否");
                dto.setVipDate("");
            }
        }
        return dto;
    }

    @Override
    public MemberOrderDetailDto getMemberOrderDetail(Long memberId) {
        MemberOrderDetailDto dto = new MemberOrderDetailDto();
        // 乐园
        List<Object[]> lyOrderObject = mOrderMainDao.getOrderCountAndAmount(memberId, REMOVE_LY_ORDER_STATUS);
        // 有效订单数
        Long lyOrderCount = WRWUtil.objToLong(lyOrderObject.get(0)[0]);
        // 有效消费金额
        Long lyOrderAmount = WRWUtil.objToLong(lyOrderObject.get(0)[1]);
        // 取消订单数
        Integer lyCancelOrder = mOrderMainDao.countByStatusAndMemberId(OrderStatus.CANCELED.getKey(), memberId);
        // 退款次数
        Long lyReturnAmount = mOrderReturnDao.getOrderReturnAmount(memberId);
        // 酷袋
        List<Object[]> kdOrderObject = kdOrderMainDao.getKdOrderCountAndAmount(memberId, REMOVE_KD_ORDER_STATUS);
        // 有效订单数
        Long kdOrderCount = WRWUtil.objToLong(kdOrderObject.get(0)[0]);
        // 有效消费金额
        Long kdOrderAmount = WRWUtil.objToLong(kdOrderObject.get(0)[1]);
        // 取消订单数
        Integer kdCancelOrder = kdOrderMainDao.countByStatusAndMemberId(KdOrderStatusEnum.CANCELED.getCode(), memberId);
        // 退款次数
        Long kdReturnAmount = kdOrderReturnDao.getKdOrderReturnAmount(memberId);
        dto.setEffectiveOrderCount(lyOrderCount + kdOrderCount + "");
        dto.setEffectivePrice(WRWUtil.transFenToYuan2String(lyOrderAmount + kdOrderAmount));
        dto.setCancelOrderCount(lyCancelOrder + kdCancelOrder + "");
        dto.setRefundCount(lyReturnAmount + kdReturnAmount + "");
        return dto;
    }
}
