package com.hengtiansoft.business.marketing.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.common.service.SMSService;
import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.business.customer.service.MemberService;
import com.hengtiansoft.business.marketing.constant.CouponConstants;
import com.hengtiansoft.business.marketing.dto.CouponDto;
import com.hengtiansoft.business.marketing.dto.CouponPageDto;
import com.hengtiansoft.business.marketing.dto.CouponRecordDto;
import com.hengtiansoft.business.marketing.dto.CouponRecordPageDto;
import com.hengtiansoft.business.marketing.dto.SaveCouponDto;
import com.hengtiansoft.business.marketing.service.CouponService;
import com.hengtiansoft.business.order.dto.SpecInfoDto;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.constant.ResultCode;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.enumeration.EErrorCode;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.later.proxy.LaterProxy;
import com.hengtiansoft.common.util.ApplicationContextUtil;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.ExcleUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.ActivitySpecDao;
import com.hengtiansoft.wrw.dao.ActivityStockDao;
import com.hengtiansoft.wrw.dao.MCouponDao;
import com.hengtiansoft.wrw.dao.MMemberDao;
import com.hengtiansoft.wrw.dao.PCategoryDao;
import com.hengtiansoft.wrw.dao.SCouponConfigDao;
import com.hengtiansoft.wrw.dao.SCouponProductDao;
import com.hengtiansoft.wrw.dao.SRechargeCouponDao;
import com.hengtiansoft.wrw.entity.ActivitySpec;
import com.hengtiansoft.wrw.entity.ActivityStock;
import com.hengtiansoft.wrw.entity.MCouponEntity;
import com.hengtiansoft.wrw.entity.MMemberEntity;
import com.hengtiansoft.wrw.entity.PCategoryEntity;
import com.hengtiansoft.wrw.entity.PProductEntity;
import com.hengtiansoft.wrw.entity.SCouponConfigEntity;
import com.hengtiansoft.wrw.enums.CoupenConfigStateEnum;
import com.hengtiansoft.wrw.enums.CouponState;
import com.hengtiansoft.wrw.enums.CouponTypeEnum;
import com.hengtiansoft.wrw.enums.MessageModelEnum;
import com.hengtiansoft.wrw.enums.StatusEnum;

/**
 * Class Name: CouponServiceImpl Description: 平台优惠券（模板）管理
 * 
 * @author chengminmiao
 */

@Service
public class CouponServiceImpl implements CouponService {

    private static final Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);
    
    private static Random r = new Random();

    private static final Date END_DATE = DateTimeUtil.getDateEndAddYear(10);

    @Autowired
    private SCouponConfigDao sCouponConfigDao;

    @Autowired
    private SCouponProductDao sCouponProductDao;

    @Autowired
    private MCouponDao mCouponDao;

    @Autowired
    private MMemberDao mMemberDao;

    @Autowired
    private SRechargeCouponDao sRechargeCouponDao;

    @Autowired
    private PCategoryDao sCategoryDao;

    @Autowired
    private MemberService memberService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SMSService sMSService;
    
    @Autowired
    private ActivityStockDao activityStockDao;
    
    @Autowired
    private ActivitySpecDao activitySpecDao;

    /**
     * 保存优惠券
     */
    @Override
    @Transactional
    public ResultDto<String> saveCoupon(SaveCouponDto dto) {

        SCouponConfigEntity entity = ConverterService.convert(dto, SCouponConfigEntity.class);

        if ("1".equals(dto.getFetchType())) {
            entity.setFetchTypeDetail(null);
        }
        if ("1".equals(dto.getUseType())) {
            entity.setUseTypeDetail(null);
        }
        entity.setStatus(CoupenConfigStateEnum.NORMAL.getCode());
        entity.setCreateDate(new Date());
        entity.setCreateId(AuthorityContext.getCurrentUser().getUserId());
        entity.setModifyDate(new Date());
        entity.setModifyId(AuthorityContext.getCurrentUser().getUserId());
        sCouponConfigDao.save(entity);

        return ResultDtoFactory.toAck(EErrorCode.SUCCESS);
    }

    /**
     * 查询优惠券配置列表
     */
    @Override
    public void getCouponList(final CouponPageDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder sbSql = new StringBuilder();
        StringBuilder countSql = new StringBuilder();
        StringBuilder paramSql = new StringBuilder();
        sbSql.append("select * from s_coupon_config s where 1=1");
        countSql.append("select count(1) from s_coupon_config s where 1=1 ");
        if (null != dto.getCouponId()) {
            paramSql.append(" and s.COUPON_ID = :couponId");
            param.put("couponId", dto.getCouponId());
        }
        if (WRWUtil.isNotEmpty(dto.getCouponName())) {
            paramSql.append(" and s.COUPON_NAME like :couponName");
            param.put("couponName", "%" + dto.getCouponName().trim() + "%");
        }

        if (WRWUtil.isNotEmpty(dto.getStatus())) {
            String curTime = DateTimeUtil.dateFormat(new Date());
            if ("1".equals(dto.getStatus())) {
                paramSql.append(" and s.STATUS = :status");
                paramSql.append(" and s.EFFECT_DATE <= :curTime and s.INVALID_DATE >= :curTime");
                param.put("curTime", curTime);
            } else if ("0".equals(dto.getStatus())) {
                paramSql.append(" and ( s.STATUS = :status");
                paramSql.append(" or !(s.EFFECT_DATE <= :curTime and s.INVALID_DATE >= :curTime) )");
                param.put("curTime", curTime);
            } else if ("2".equals(dto.getStatus())) {
                paramSql.append(" and s.STATUS = :status");
            }
            param.put("status", dto.getStatus());
        }

        if (WRWUtil.isNotEmpty(dto.getType())) {
            paramSql.append(" and s.type = :type");
            param.put("type", dto.getType());
        }
        if (null != dto.getValue()) {
            paramSql.append(" and s.value = :value");
            param.put("value", dto.getValue());
        }
        if (null != dto.getCreateDateStrat()) {
            paramSql.append(" and s.CREATE_DATE >= :createDateStrat");
            param.put("createDateStrat", dto.getCreateDateStrat());
        }
        if (null != dto.getCreateDateEnd()) {
            paramSql.append(" and s.CREATE_DATE <= :createDateEnd");
            param.put("createDateEnd", dto.getCreateDateEnd());
        }
        if (null != dto.getEffectDateStart()) {
            paramSql.append(" and s.EFFECT_DATE >= :effectDateStart");
            param.put("effectDateStart", dto.getEffectDateStart());
        }
        if (null != dto.getEffectDateEnd()) {
            paramSql.append(" and s.EFFECT_DATE <= :effectDateEnd");
            param.put("effectDateEnd", dto.getEffectDateEnd());
        }
        if (StringUtils.isNotBlank(dto.getUseType())) {
            paramSql.append(" and s.USE_TYPE = :useType");
            param.put("useType", dto.getUseType().trim());
        }
        if ("0".equals(dto.getUseType()) && null != dto.getUseTypeDetail()) {
            String[] useTypeDetailArr = dto.getUseTypeDetail().trim().split(",");
            for (int i = 0, len = useTypeDetailArr.length; i < len; i++) {
                if (i == 0) {
                    paramSql.append(" and (s.USE_TYPE_DETAIL like :useTypeDetail" + i);
                } else {
                    paramSql.append(" or s.USE_TYPE_DETAIL like :useTypeDetail" + i);
                }
                param.put("useTypeDetail" + i, "%" + useTypeDetailArr[i].trim() + "%");
            }
            paramSql.append(" ) ");
        }

        sbSql.append(paramSql.toString()).append(" order by s.CREATE_DATE desc");
        countSql.append(paramSql.toString());

        Query query = entityManager.createNativeQuery(sbSql.toString(), SCouponConfigEntity.class);
        QueryUtil.processParamForQuery(query, param);
        Query countQuery = entityManager.createNativeQuery(countSql.toString());
        QueryUtil.processParamForQuery(countQuery, param);

        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        QueryUtil.buildPagingDto(dto, totalRecord.longValue(), buildCouponDto(query.getResultList()));
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
    public CouponDto getCoupon(Long couponId) {
        SCouponConfigEntity entity = sCouponConfigDao.findOne(couponId);
        if (entity != null) {
            CouponDto dto = ConverterService.convert(entity, CouponDto.class);
            dto.setSendNum(mCouponDao.getSendNum(couponId));
            if (WRWUtil.isNotEmpty(dto.getFetchTypeDetail())) {
                dto.setFetchTypeDetailNames(getCategoryNames(dto.getFetchTypeDetail()));
            }
            if (WRWUtil.isNotEmpty(dto.getUseTypeDetail())) {
                dto.setUseTypeDetailNames(getCategoryNames(dto.getUseTypeDetail()));
            }
            return dto;
        }
        return null;
    }

    /**
     * 
     * Description: 获取分类名称
     *
     * @param codes
     * @return
     * @author chengchaoyin
     */
    private String getCategoryNames(String codes) {
        List<Long> cateIds = new ArrayList<Long>();
        for (String cateId : codes.split(",")) {
            cateIds.add(Long.valueOf(cateId));
        }
        List<PCategoryEntity> listEntity = sCategoryDao.findByCateId(cateIds);
        String fetchTypeDetailNames = "";
        for (int i = 0; i < listEntity.size(); i++) {
            if (i == listEntity.size() - 1) {
                fetchTypeDetailNames = fetchTypeDetailNames + listEntity.get(i).getCateName();
            } else {
                fetchTypeDetailNames = fetchTypeDetailNames + listEntity.get(i).getCateName() + ",";
            }

        }
        return fetchTypeDetailNames;
    }

    @Override
    @Transactional
    public ResultDto<String> delete(Long couponId) {
        SCouponConfigEntity entity = sCouponConfigDao.findOne(couponId);
        if (null == entity) {
            throw new WRWException(EErrorCode.ENTITY_COUPON_CONFIG_NOT_EXIST);
        }

        entity.setStatus(CoupenConfigStateEnum.DELETE.getCode());
        sCouponConfigDao.save(entity);

        mCouponDao.deleteCouponByAdmin(entity.getCouponId());

        return ResultDtoFactory.toAck("删除成功");
    }

    /**
     * 查找优惠券领取使用记录
     */
    @Override
    public void recordList(final CouponRecordPageDto couponDto) {

        if (null != couponDto.getCouponId()) {
            final SCouponConfigEntity configEntity = sCouponConfigDao.findOne(couponDto.getCouponId());
            if (null != configEntity) {
                // couponDto.setTotalNum(configEntity.getTotalNum());
                couponDto.setSendNum(configEntity.getSendNum());
            }

            PageRequest pageable = new PageRequest(couponDto.getCurrentPage() - 1, couponDto.getPageSize(), new Sort(
                    Direction.DESC, "createDate"));
            Page<MCouponEntity> page = mCouponDao.findAll(new Specification<MCouponEntity>() {

                @Override
                public Predicate toPredicate(Root<MCouponEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> predicates = new ArrayList<Predicate>();

                    if (configEntity.getType().equalsIgnoreCase(CouponTypeEnum.EXCHANGE.getKey())) {
                        predicates.add(cb.isNotNull(root.<Long> get("memberId")));
                    }

                    predicates.add(cb.equal(root.<Long> get("coupConId"), couponDto.getCouponId()));
                    Predicate predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));

                    query.where(predicate);
                    return query.getRestriction();
                }
            }, pageable);

            couponDto.setTotalPages(page.getTotalPages());
            couponDto.setTotalRecord(page.getTotalElements());
            couponDto.setList(buildCouponRecord(page.getContent()));
        }
    }

    /**
     * Description: 构造领取记录
     *
     * @param content
     * @return
     */
    private List<CouponRecordDto> buildCouponRecord(List<MCouponEntity> content) {
        List<CouponRecordDto> dtos = new ArrayList<CouponRecordDto>();

        if (null != content && content.size() > 0) {
            HashMap<Long, MMemberEntity> memberMap = new HashMap<Long, MMemberEntity>();
            // 查询用户信息
            List<Long> memberIds = new ArrayList<Long>();
            for (MCouponEntity entity : content) {
                if (null != entity.getMemberId()) {
                    memberIds.add(entity.getMemberId());
                }
            }
            if (memberIds.size() > 0) {
                List<MMemberEntity> memberEntities = mMemberDao.findAll(memberIds);
                for (MMemberEntity entity : memberEntities) {
                    memberMap.put(entity.getUserId(), entity);
                }
            }

            for (MCouponEntity entity : content) {
                CouponRecordDto dto = new CouponRecordDto();
                dto.setCoupCode(entity.getCoupCode());
                dto.setCreateDate(entity.getCreateDate());
                dto.setMemberId(entity.getMemberId());
                if (null != entity.getMemberId()) {

                    dto.setMemberName(memberMap.get(entity.getMemberId()).getMemberName());
                    dto.setPhone(memberMap.get(entity.getMemberId()).getLoginId());

                }
                dto.setOrderId(entity.getUsedOrderId());
                dto.setUsedDate(entity.getUsedDate());
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Override
    public Long getUserNum(Long couponId) {
        return mCouponDao.getUsedNum(couponId);
    }

    /*
     * 返回长度为10的随机数，在前面补0
     */
    public static String getRandom() {
        long num = Math.abs(r.nextLong() % 10000000000L);
        String s = String.valueOf(num);
        for (int i = 0; i < 10 - s.length(); i++) {
            s = "0" + s;
        }

        return s;
    }

    @Override
    public List<String> getProduct(Long couponId) {
        List<PProductEntity> productEntities = sCouponProductDao.findProductCoupID(couponId);
        List<String> productInfo = new ArrayList<String>();

        for (PProductEntity entity : productEntities) {
            productInfo.add("商品名称：" + entity.getProductName() + "，商品编码：" + entity.getProductCode());
        }
        return productInfo;
    }

    @Override
    public ResultDto<List<String>> couponCodeList(Long couponId) {
        return ResultDtoFactory.toAck(EErrorCode.SUCCESS.getDisplayMsg(), mCouponDao.findCouponCodeList(couponId));
    }

    @Override
    public List<CouponDto> findCouponByCouponIds(List<Long> couponIds) {
        List<SCouponConfigEntity> listCoupon = sCouponConfigDao.findCouponByCouponIds(couponIds);
        List<CouponDto> list = new ArrayList<CouponDto>();
        for (SCouponConfigEntity sCouponConfigEntity : listCoupon) {
            CouponDto dto = ConverterService.convert(sCouponConfigEntity, CouponDto.class);
            list.add(dto);
        }
        return list;
    }

    @Override
    public ResultDto<Boolean> sendCoupon(SaveCouponDto dto) {
        String userIds = dto.getUserIds();
        String couponIds = dto.getCouponIds();
        String sendType = dto.getSendType();
        if (WRWUtil.isNotEmpty(couponIds) && WRWUtil.isNotEmpty(sendType)) {
            if ("0".equals(sendType)) {
                sendCouponToAll(couponIds);
            } else if ("1".equals(sendType) && WRWUtil.isNotEmpty(userIds)) {
                sendCouponToPart(couponIds, userIds);
            }
        } else {
            return ResultDtoFactory.toNack("优惠券缺失或者发送类型为空！");
        }
        return ResultDtoFactory.toAck("发送成功！", true);
    }

    /**
     * 
     * Description: 发送优惠券给所有有效用户
     *
     * @param couponIds
     * @author chengchaoyin
     */
    private void sendCouponToAll(String couponIds) {
        String[] couponIdsArr = couponIds.split(",");
        List<Long> couponIdsNew = new ArrayList<Long>();
        for (String couponId : couponIdsArr) {
            couponIdsNew.add(Long.valueOf(couponId));
        }
        List<SCouponConfigEntity> scList = sCouponConfigDao.findCouponByCouponIds(couponIdsNew);
        List<MMemberEntity> userList = memberService.findAllMember();
        List<MCouponEntity> couponEntities = new ArrayList<MCouponEntity>();
        HashMap<String, String> dataMap = new HashMap<String, String>();
        for (MMemberEntity mMemberEntity : userList) {
            for (SCouponConfigEntity entity : scList) {
                MCouponEntity couponEntity = new MCouponEntity();
                couponEntity.setCoupConId(entity.getCouponId());
                couponEntity.setCouponName(entity.getCouponName());
                couponEntity.setCreateDate(new Date());
                couponEntity.setEffectDate(entity.getEffectDate());
                couponEntity.setInvalidDate(entity.getInvalidDate());
                couponEntity.setLimitValue(entity.getUserValueLimit());
                couponEntity.setStatus(CouponState.INVALID.getKey());
                couponEntity.setType(entity.getType());
                couponEntity.setValue(entity.getValue());
                couponEntity.setCoupCode(getRandom());
                couponEntity.setMemberId(Long.valueOf(mMemberEntity.getUserId()));
                couponEntities.add(couponEntity);
                dataMap.put("couponName", entity.getCouponName());
                // 异步处理发送短信
                LaterProxy.run(() -> {
                    sMSService.sendSMS(mMemberEntity.getLoginId(), MessageModelEnum.sms_C_sendCouponNoitfy, dataMap);
                });
            }
        }
        mCouponDao.save(couponEntities);
    }

    /**
     * 
     * Description: 发送优惠券给指定用户
     *
     * @param couponIds
     * @param phoneNums
     * @author chengchaoyin
     */
    private void sendCouponToPart(String couponIds, String userIds) {
        String[] couponIdsArr = couponIds.split(",");
        List<Long> couponIdsNew = new ArrayList<Long>();
        for (String couponId : couponIdsArr) {
            couponIdsNew.add(Long.valueOf(couponId));
        }
        List<SCouponConfigEntity> scList = sCouponConfigDao.findCouponByCouponIds(couponIdsNew);
        List<MCouponEntity> couponEntities = new ArrayList<MCouponEntity>();
        HashMap<String, String> dataMap = new HashMap<String, String>();
        // 异步处理发送短信
        LaterProxy.run(() -> {
            for (SCouponConfigEntity entity : scList) {
                String[] userIdsArr = userIds.split(",");
                for (String userId : userIdsArr) {
                    MCouponEntity couponEntity = new MCouponEntity();
                    couponEntity.setCoupConId(entity.getCouponId());
                    couponEntity.setCouponName(entity.getCouponName());
                    couponEntity.setCreateDate(new Date());
                    couponEntity.setEffectDate(entity.getEffectDate());
                    couponEntity.setInvalidDate(entity.getInvalidDate());
                    couponEntity.setLimitValue(entity.getUserValueLimit());
                    couponEntity.setStatus(CouponState.INVALID.getKey());
                    couponEntity.setType(entity.getType());
                    couponEntity.setValue(entity.getValue());
                    couponEntity.setCoupCode(getRandom());
                    couponEntity.setMemberId(Long.valueOf(userId));
                    couponEntities.add(couponEntity);
                    dataMap.clear();
                    dataMap.put("couponName", entity.getCouponName());

                    MMemberEntity mMemberEntity = ApplicationContextUtil.getBean(MemberService.class).findByUserId(
                            Long.valueOf(userId));
                    sMSService.sendSMS(mMemberEntity.getLoginId(), MessageModelEnum.sms_C_sendCouponNoitfy, dataMap);
                }
            }
            if (couponEntities != null && !couponEntities.isEmpty()) {
                mCouponDao.save(couponEntities);
            }
        });
    }

    @Override
    @Transactional
    public ResultDto<Boolean> updateCouponStatus(SaveCouponDto dto) {
        // 修改优惠券配置表状态，STATUS=2（未启用）
        String status = "";
        String status2 = "";
        if ("0".equals(dto.getStatus())) {
            status = StatusEnum.UNENABLED.getCode();
            status2 = CouponState.UNENABLED.getKey();
        } else if ("1".equals(dto.getStatus())) {
            status = StatusEnum.NORMAL.getCode();
            status2 = CouponState.INVALID.getKey();
        }
        sCouponConfigDao.updateCouponStatus(status, dto.getCouponId(), AuthorityContext.getCurrentUser().getUserId());
        // 修改会员优惠券。
        String orgStatus = "";
        if (status2.equals(CouponState.UNENABLED.getKey())) {
            orgStatus = CouponState.INVALID.getKey();
        } else {
            orgStatus = CouponState.UNENABLED.getKey();
        }
        mCouponDao.updateCouponStatus(status2, dto.getCouponId(), orgStatus);
        return ResultDtoFactory.toAck("success!");
    }

    @Override
    public ResultDto<Map<String, Object>> importExcel(InputStream inputStream, SaveCouponDto couponDto) {

        Map<String, Object> mapData = new HashMap<String, Object>();

        long start = System.currentTimeMillis();
        // 导入的手机集合
        List<String> qrPhoneList = new ArrayList<String>();
        try {
            // 创建Excel对象
            Workbook wb = WorkbookFactory.create(inputStream);
            // 取出第一个工作表，索引从0开始
            Sheet sheet = wb.getSheetAt(0);
            // 从第3行开始遍历，标题不处理
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    // 如果行数据为空，则不处理
                    continue;
                }
                Cell cell = row.getCell(0);
                String phoneStr = "";
                DecimalFormat df = new DecimalFormat("0");
                if (cell == null) {
                    phoneStr = "";
                } else {
                    phoneStr = df.format(cell.getNumericCellValue());
                }
                if (!qrPhoneList.contains(phoneStr)) {
                    qrPhoneList.add(phoneStr);
                }
            }
        } catch (IOException | EncryptedDocumentException | InvalidFormatException e) {
            throw new WRWException(e.toString());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new WRWException(e.toString());
                }
            }
        }

        List<MMemberEntity> members = new ArrayList<MMemberEntity>();
        if (qrPhoneList.size() > 0) {
            members = memberService.findAllByPhones(qrPhoneList);
        }
        Map<Long, String> phoneMap = new HashMap<Long, String>();
        // 临时存放无效的手机号
        if (CollectionUtils.isNotEmpty(members)) {
            for (MMemberEntity member : members) {
                if (qrPhoneList.contains(member.getLoginId())) {
                    phoneMap.put(member.getUserId(), member.getLoginId());
                    qrPhoneList.remove(member.getLoginId());
                }
            }
        } else {
            return ResultDtoFactory.toNack("导入的所有手机号码都是无效用户或用户不存在！");
        }
        mapData.put("phones", phoneMap);
        // 如果无效手机号集合不为空，则取出这些无效手机号并返回给前台
        if (CollectionUtils.isNotEmpty(qrPhoneList)) {
            StringBuffer sb = new StringBuffer();
            for (String string : qrPhoneList) {
                if (!StringUtils.isEmpty(string)) {
                    sb.append(string + "，");
                }
            }
            sb.deleteCharAt(sb.lastIndexOf("，"));
            logger.error(sb.toString());

            long end = System.currentTimeMillis();
            logger.error("总共执行：" + (end - start));
            mapData.put("errorPhones", "以下手机号导入失败：\n" + sb.toString());
            ResultDto<Map<String, Object>> dto = new ResultDto<Map<String, Object>>();
            dto.setCode(ResultCode.BUSINESS_ERROR);
            dto.setMessage("以下手机号导入失败：\n" + sb.toString());
            dto.setData(mapData);
            return dto;
        }
        if (mapData != null && !mapData.isEmpty()) {
            return ResultDtoFactory.toAck("批量导入手机号成功！", mapData);
        } else {
            return ResultDtoFactory.toNack("批量导入手机号失败！");
        }
    }

    @Override
    public HSSFWorkbook toExcel() {
        ArrayList<String> fieldName = new ArrayList<String>();
        ArrayList<String> sheetName = new ArrayList<String>();
        ArrayList<String> fieldStyle = new ArrayList<String>();
        List<List<Object>> fieldData = new ArrayList<List<Object>>();
        fieldName.add(0, "11位已注册手机号");
        sheetName.add(0, "批量用户导入模板");
        for (int j = 0; j < fieldName.size(); j++) {
            fieldStyle.add(j, "6000");
        }
        List<Object> rowData = new ArrayList<Object>();
        fieldData.add(rowData);
        ExcleUtil ex = new ExcleUtil(fieldName, sheetName, fieldData, fieldStyle);
        return ex.createWorkbook();
    }

    @Override
    public Map<String, Object> getProductPrice(Long productId,List<SpecInfoDto> specInfo, String actDate) {
        Map<String,Object> map = new HashMap<String, Object>();
        List<SpecInfoDto> specInfoDtoList = specInfo;
        if (!CollectionUtils.isEmpty(specInfoDtoList)) {
            // 获取活动库存
            ActivityStock activityStock = activityStockDao.findActByProductIdAndActDate(
                    productId, actDate);
            if (activityStock != null) {
                // 获取所选子规格key值
                String subSpecKey = "";
                if (specInfoDtoList.size() > 1) {
                    for (SpecInfoDto specInfoDto : specInfoDtoList) {
                        if (WRWUtil.isNotEmpty(subSpecKey)) {
                            subSpecKey = subSpecKey + "-" + specInfoDto.getSubSpec();
                        } else {
                            subSpecKey = specInfoDto.getSubSpec();
                        }

                    }
                } else {
                    subSpecKey = specInfoDtoList.get(0).getSubSpec();
                }

                if (WRWUtil.isNotEmpty(subSpecKey)) {
                    SpecInfoDto specInfoDto = specInfoDtoList.get(0);
                    ActivitySpec activitySpec = activitySpecDao.getSubSpecStock(activityStock.getId(),
                            specInfoDto.getMainSpec(), specInfoDto.getSubSpec());
                    if (activitySpec != null) {
                        String prices = activitySpec.getPrices();
                        String limits = activitySpec.getLimits();
                        JSONObject jsonObject = JSONObject.fromObject(prices);
                        if(jsonObject.containsKey(subSpecKey)){
                            Object object = jsonObject.get(subSpecKey);
                            if(WRWUtil.isNumeric(object.toString())){
                                Integer pPrice = Integer.valueOf(object.toString());
                                map.put(CouponConstants.PRODUCT_PRICE, pPrice);
                                map.put(CouponConstants.ACT_SPEC, activitySpec);
                            }
                            Integer limitCnt = 0;
                            if(WRWUtil.isNotEmpty(limits)){
                                JSONObject jsonObject2 = JSONObject.fromObject(limits);
                                if(jsonObject2.containsKey(subSpecKey)){
                                    Object object2 = jsonObject2.get(subSpecKey);
                                    if(WRWUtil.isNumeric(object2.toString())){
                                        limitCnt = Integer.valueOf(object2.toString());
                                    }
                                }
                            }
                            map.put(CouponConstants.PRODUCT_SPEC_LIMITS, limitCnt);
                            return map;
                        }
                    }
                }
            }
        }
        return map;
    }

}
