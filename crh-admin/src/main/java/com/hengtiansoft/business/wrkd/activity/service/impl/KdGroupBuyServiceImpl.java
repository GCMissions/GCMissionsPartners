package com.hengtiansoft.business.wrkd.activity.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.business.wrkd.activity.dto.KdGroupBuyListDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdGroupBuySaveDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdGroupBuySearchDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdStartDateAndEndDateDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdTeamActDetailResponseDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdTeamActRecordResponseDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdTeamBuyProductDetailInfoDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdTeamBuySpecInfoDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdTeamBuySpecListDto;
import com.hengtiansoft.business.wrkd.activity.dto.SpecPriceDto;
import com.hengtiansoft.business.wrkd.activity.service.KdGroupBuyService;
import com.hengtiansoft.business.wrkd.advertise.service.KdAdvertiseService;
import com.hengtiansoft.business.wrkd.image.dto.KdPImageDto;
import com.hengtiansoft.business.wrkd.product.dto.KdProductDto;
import com.hengtiansoft.business.wrkd.product.dto.KdProductSearchDto;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.BasicUtil;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.KdPImageDao;
import com.hengtiansoft.wrw.dao.KdProductDao;
import com.hengtiansoft.wrw.dao.KdProductSaleDao;
import com.hengtiansoft.wrw.dao.KdProductStockDao;
import com.hengtiansoft.wrw.dao.KdProductStockDetailDao;
import com.hengtiansoft.wrw.dao.KdTeamBuyProductDao;
import com.hengtiansoft.wrw.dao.KdTeamBuyProductDetailDao;
import com.hengtiansoft.wrw.dao.KdTeamBuyProductRecordDao;
import com.hengtiansoft.wrw.dao.KdTwentyFourHoursDao;
import com.hengtiansoft.wrw.entity.KdPImageEntity;
import com.hengtiansoft.wrw.entity.KdProductEntity;
import com.hengtiansoft.wrw.entity.KdProductSaleEntity;
import com.hengtiansoft.wrw.entity.KdProductStockDetailEntity;
import com.hengtiansoft.wrw.entity.KdProductStockEntity;
import com.hengtiansoft.wrw.entity.KdTeamBuyProductDetailEntity;
import com.hengtiansoft.wrw.entity.KdTeamBuyProductEntity;
import com.hengtiansoft.wrw.entity.SUserEntity;
import com.hengtiansoft.wrw.enums.ActTypeEnum;
import com.hengtiansoft.wrw.enums.KdPImageEnum;
import com.hengtiansoft.wrw.enums.KdSaleStatus;
import com.hengtiansoft.wrw.enums.KdTeamBuyStatusEnum;
import com.hengtiansoft.wrw.enums.StatusEnum;
import com.hengtiansoft.wrw.enums.StockTypeEnum;
import com.hengtiansoft.wrw.enums.TeamPriceTypeEnum;
import com.hengtiansoft.wrw.enums.TeamRecordStatusEnum;

@Service
public class KdGroupBuyServiceImpl implements KdGroupBuyService {

    // 商品名称中特殊字符
    private static final char ESCAPE = '\\';

    private List<String> statusList = Arrays.asList(KdTeamBuyStatusEnum.NOSTART.getCode(), KdTeamBuyStatusEnum.START.getCode(),
            KdTeamBuyStatusEnum.OVER.getCode());

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private KdTeamBuyProductDao kdTeamBuyProductDao;
    @Autowired
    private KdTeamBuyProductDetailDao kdTeamBuyProductDetailDao;
    @Autowired
    private KdPImageDao kdPImageDao;
    @Autowired
    private KdProductDao kdProductDao;
    @Autowired
    private KdProductStockDetailDao kdProductStockDetailDao;
    @Autowired
    private KdProductStockDao kdProductStockDao;
    @Autowired
    private KdProductSaleDao kdProductSaleDao;
    @Autowired
    private KdTeamBuyProductRecordDao kdTeamBuyProductRecordDao;
    @Autowired
    private KdTwentyFourHoursDao kdTwentyFourHoursDao;
    @Autowired
    private KdAdvertiseService kdAdvertiseService;

    @SuppressWarnings("unchecked")
    @Override
    public void searchGroupBuyList(final KdGroupBuySearchDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder conditionSql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");
        StringBuilder sql = new StringBuilder(
                " SELECT KP.ID,KP.NAME,P.PCODE,P.PNAME,KP.CREATE_DATE,KP.EFFECTIVE_START_DATE,KP.EFFECTIVE_END_DATE,KP.`STATUS` "
                        + " FROM KD_TEAM_BUY_PRODUCT KP JOIN KD_P_PRODUCT P ON KP.PRODUCT_ID = P.ID " + " WHERE 1=1 ");
        countSql.append("SELECT COUNT(1) FROM ( ").append(sql);
        if (!WRWUtil.isEmpty(dto.getGroupBuyName())) {
            String msg = dto.getGroupBuyName().trim();
            char escape = '\\';
            msg = msg.replace("\\", escape + "\\");
            msg = msg.replace("%", escape + "%");
            msg = msg.replace("_", escape + "_");
            conditionSql.append(" AND REPLACE(KP.NAME,' ','') LIKE REPLACE(:G_NAME,' ','') ");
            param.put("G_NAME", "%" + msg + "%");
        }
        if (!WRWUtil.isEmpty(dto.getProductName())) {
            String msg = dto.getProductName().trim();
            char escape = '\\';
            msg = msg.replace("\\", escape + "\\");
            msg = msg.replace("%", escape + "%");
            msg = msg.replace("_", escape + "_");
            conditionSql.append(" AND REPLACE(P.PNAME,' ','') LIKE REPLACE(:P_NAME,' ','') ");
            param.put("P_NAME", "%" + msg + "%");
        }
        if (!WRWUtil.isEmpty(dto.getProductCode())) {
            String msg = dto.getProductCode().trim();
            char escape = '\\';
            msg = msg.replace("\\", escape + "\\");
            msg = msg.replace("%", escape + "%");
            msg = msg.replace("_", escape + "_");
            conditionSql.append(" AND REPLACE(P.PCODE,' ','') LIKE REPLACE(:P_CODE,' ','') ");
            param.put("P_CODE", "%" + msg + "%");
        }
        // 创建时间
        if (!WRWUtil.isEmpty(dto.getStartTime())) {
            conditionSql.append("AND KP.CREATE_DATE >= :START ");
            param.put("START", DateTimeUtil.getDateBegin(dto.getStartTime()));
        }
        if (!WRWUtil.isEmpty(dto.getEndTime())) {
            conditionSql.append("AND KP.CREATE_DATE <= :END ");
            param.put("END", DateTimeUtil.getDateEnd(dto.getEndTime()));
        }
        if (!WRWUtil.isEmpty(dto.getStatus())) {
            conditionSql.append("AND KP.STATUS = :STATUS ");
            param.put("STATUS", dto.getStatus());
        } else {
            conditionSql.append("AND KP.STATUS IN (:STATUS_LIST) ");
            List<String> statusList = new ArrayList<String>();
            for (KdTeamBuyStatusEnum status : KdTeamBuyStatusEnum.getSearchStatus()) {
                statusList.add(status.getCode());
            }
            param.put("STATUS_LIST", statusList);
        }
        conditionSql.append(" ORDER BY KP.CREATE_DATE DESC ");
        Query query = entityManager.createNativeQuery(sql.append(conditionSql).toString());
        QueryUtil.processParamForQuery(query, param);
        Query countQuery = entityManager.createNativeQuery(countSql.append(conditionSql).append(" ) A").toString());
        QueryUtil.processParamForQuery(countQuery, param);
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        dto.setTotalRecord(totalRecord.longValue());
        dto.setTotalPages(totalRecord.intValue() % dto.getPageSize() == 0 ? totalRecord.intValue() / dto.getPageSize()
                : ((totalRecord.intValue() / dto.getPageSize()) + 1));
        dto.setList(buildGroupBuyDtos(query.getResultList()));
    }

    private List<KdGroupBuyListDto> buildGroupBuyDtos(List<Object[]> list) {
        List<KdGroupBuyListDto> groupBuyDtoList = new ArrayList<KdGroupBuyListDto>();
        for (Object[] obj : list) {
            KdGroupBuyListDto groupBuyDto = new KdGroupBuyListDto();
            groupBuyDto.setGroupBuyId(WRWUtil.objToLong(obj[0]));
            groupBuyDto.setGroupBuyName(WRWUtil.objToString(obj[1]));
            groupBuyDto.setProductCode(WRWUtil.objToString(obj[2]));
            groupBuyDto.setProductName(WRWUtil.objToString(obj[3]));
            groupBuyDto.setCreateDate(DateTimeUtil.parseDateToString((Date) obj[4], DateTimeUtil.SIMPLE_M_D_H_M_S));
            groupBuyDto.setStartDate(DateTimeUtil.parseDateToString((Date) obj[5], DateTimeUtil.SIMPLE_FMT_MINUTE));
            groupBuyDto.setEndDate(DateTimeUtil.parseDateToString((Date) obj[6], DateTimeUtil.SIMPLE_FMT_MINUTE));
            groupBuyDto.setStatus(KdTeamBuyStatusEnum.getText(WRWUtil.objToString(obj[7])));
            groupBuyDtoList.add(groupBuyDto);
        }
        return groupBuyDtoList;
    }

    @Override
    @Transactional
    public ResultDto<?> saveAndUpdate(KdGroupBuySaveDto dto) {
        SUserEntity user = (SUserEntity) AuthorityContext.getCurrentUser();
        // 添加
        if ("0".equals(dto.getOperType())) {
            return saveGroupBuyProduct(dto, user.getUserId());
        }
        // 编辑
        if ("1".equals(dto.getOperType())) {
            return updateGroupBuyProduct(dto, user.getUserId());
        }
        return ResultDtoFactory.toNack("操作指令有误！");
    }

    /**
     * 
     * Description: 判断日期是否已经到达
     *
     * @param compareDate
     * @return
     */
    private boolean isOverDate(Date compareDate) {
        Date date = new Date();
        return date.getTime() >= compareDate.getTime();
    }

    @Override
    @Transactional
    public ResultDto<?> saveGroupBuyProduct(KdGroupBuySaveDto dto, Long userId) {
        KdProductSaleEntity saleEntity = kdProductSaleDao.findByProductId(dto.getProductId());
        Date startDate = DateTimeUtil.parseDate(dto.getStartDate(), DateTimeUtil.SIMPLE_FMT_MINUTE);
        Date endDate = DateTimeUtil.parseDate(dto.getEndDate(), DateTimeUtil.SIMPLE_FMT_MINUTE);
        if (saleEntity == null) {
            return ResultDtoFactory.toNack("商品还未上架");
        } else {
            if (KdSaleStatus.NO_SALE.getCode().equals(saleEntity.getSaleStatus())) {
                if (saleEntity.getOnTime() == null) {
                    return ResultDtoFactory.toNack("商品还未上架");
                } else {
                    return ResultDtoFactory.toNack("商品已下架");
                }
            } else if (KdSaleStatus.WAIT_SALE.getCode().equals(saleEntity.getSaleStatus())) {
                return ResultDtoFactory.toNack("团购活动只能添加已上架商品");
            } else {
                if (startDate.getTime() < saleEntity.getOnTime().getTime()) {
                    return ResultDtoFactory.toNack("团购开始时间不能在商品上架时间之前！");
                } else {
                    if (saleEntity.getOffTime() != null) {
                        if (endDate.getTime() > saleEntity.getOffTime().getTime()) {
                            return ResultDtoFactory.toNack("团购结束时间不能在商品下架时间之后！");
                        }
                    }
                }
            }
        }
        if (!kdTeamBuyProductDao.findSameTimeGroupBuy(dto.getProductId(), startDate, endDate).isEmpty()) {
            return ResultDtoFactory.toNack("该商品在该活动时间段已经有团购活动！");
        }

        // 校验关联商品不能在同时间段关联24小时活动
        if (!kdTwentyFourHoursDao.findSameTimeTF(dto.getProductId(), startDate, endDate).isEmpty()) {
            return ResultDtoFactory.toNack("该商品在该活动时间段已经有24小时活动！");
        }

        if (dto.getImageDtos() == null) {
            return ResultDtoFactory.toNack("活动图片有误！");
        } else {
            if (dto.getImageDtos().size() == 0) {
                return ResultDtoFactory.toNack("请上传活动图片！");
            }
            if (dto.getImageDtos().size() < 3) {
                return ResultDtoFactory.toNack("活动图片最少需要上传3张！");
            }
            if (dto.getImageDtos().size() > 5) {
                return ResultDtoFactory.toNack("活动图片最多只能上传5张！");
            }
        }
        Date date = new Date();
        // 保存团购信息
        KdTeamBuyProductEntity teamBuy = new KdTeamBuyProductEntity();
        teamBuy.setProductId(dto.getProductId());
        teamBuy.setName(dto.getGroupBuyName());
        teamBuy.setDescription(dto.getDescription());
        teamBuy.setEffectiveStartDate(startDate);
        teamBuy.setEffectiveEndDate(endDate);
        if (isOverDate(startDate)) {
            if (isOverDate(endDate)) {
                teamBuy.setStatus(KdTeamBuyStatusEnum.OVER.getCode());
            } else {
                teamBuy.setStatus(KdTeamBuyStatusEnum.START.getCode());
            }
        } else {
            teamBuy.setStatus(KdTeamBuyStatusEnum.NOSTART.getCode());
        }
        teamBuy.setImage("".equals(dto.getGroupBuyImage()) ? null : dto.getGroupBuyImage());
        if (!"".equals(dto.getTotalPrice()) && dto.getTotalPrice() != null) {
            teamBuy.setPrice(WRWUtil.transYuanToFen(BigDecimal.valueOf(Double.parseDouble(dto.getTotalPrice())))
                    .intValue());
            teamBuy.setLimitCount(Integer.parseInt(dto.getLimitCount()));
            teamBuy.setPriceType("0");
        } else {
            teamBuy.setPriceType("1");
        }
        teamBuy.setStartCount(Integer.parseInt(dto.getStartCount()));
        teamBuy.setSpecialDesc(dto.getSpecialDesc());
        teamBuy.setCreateDate(date);
        teamBuy.setCreateId(userId);
        KdTeamBuyProductEntity newEntity = kdTeamBuyProductDao.save(teamBuy);
        // 保存规格价格信息
        if (dto.getSpecInfoArry() != null && !dto.getSpecInfoArry().isEmpty()) {
            List<KdTeamBuyProductDetailEntity> detailList = new ArrayList<KdTeamBuyProductDetailEntity>();
            for (int i = 0; i < dto.getSpecInfoArry().size(); i++) {
                KdTeamBuyProductDetailEntity detailEntity = new KdTeamBuyProductDetailEntity();
                detailEntity.setTeamBuyId(newEntity.getId());
                detailEntity.setSpecInfo(dto.getSpecInfoArry().get(i));
                detailEntity.setPrice(WRWUtil
                        .transYuanToFen(BigDecimal.valueOf(Double.valueOf(dto.getPrices().get(i)))).toString());
                detailEntity.setLimitCount(dto.getLimits().get(i));
                detailEntity.setStatus(StatusEnum.NORMAL.getCode());
                detailEntity.setCreateDate(date);
                detailEntity.setCreateId(userId);
                detailList.add(detailEntity);
            }
            kdTeamBuyProductDetailDao.save(detailList);
        }
        // 保存活动图片信息
        if (dto.getImageDtos() != null && !dto.getImageDtos().isEmpty()) {
            List<KdPImageEntity> imageList = new ArrayList<KdPImageEntity>();
            for (KdPImageDto imageDto : dto.getImageDtos()) {
                KdPImageEntity imageEntity = new KdPImageEntity();
                imageEntity.setImageKey(imageDto.getImageKey());
                imageEntity.setImageUrl(imageDto.getImageUrl());
                imageEntity.setIsDeleted("0");
                imageEntity.setKeyId(newEntity.getId());
                imageEntity.setType(KdPImageEnum.TEAMBUY.getCode());
                imageEntity.setCreateDate(date);
                imageEntity.setCreateId(userId);
                imageList.add(imageEntity);
            }
            kdPImageDao.save(imageList);
        }
        return ResultDtoFactory.toAck("添加成功！");
    }

    @Override
    public ResultDto<?> updateGroupBuyProduct(KdGroupBuySaveDto dto, Long userId) {
        Date date = new Date();
        // 保存团购信息
        KdTeamBuyProductEntity teamBuy = kdTeamBuyProductDao.findOne(dto.getGroupBuyId());
        if (teamBuy.getStatus().equals(KdTeamBuyStatusEnum.START.getCode())) {
            return ResultDtoFactory.toNack("团购活动已经开始啦，不能修改信息！");
        }
        KdProductSaleEntity saleEntity = kdProductSaleDao.findByProductId(dto.getProductId());
        Date startDate = DateTimeUtil.parseDate(dto.getStartDate(), DateTimeUtil.SIMPLE_FMT_MINUTE);
        Date endDate = DateTimeUtil.parseDate(dto.getEndDate(), DateTimeUtil.SIMPLE_FMT_MINUTE);
        if (saleEntity == null) {
            return ResultDtoFactory.toNack("商品还未上架");
        } else {
            if (KdSaleStatus.NO_SALE.getCode().equals(saleEntity.getSaleStatus())) {
                if (saleEntity.getOnTime() == null) {
                    return ResultDtoFactory.toNack("商品还未上架");
                } else {
                    return ResultDtoFactory.toNack("商品已下架");
                }
            } else if (KdSaleStatus.WAIT_SALE.getCode().equals(saleEntity.getSaleStatus())) {
                return ResultDtoFactory.toNack("团购活动只能添加已上架商品");
            } else {
                if (startDate.getTime() < saleEntity.getOnTime().getTime()) {
                    return ResultDtoFactory.toNack("团购开始时间不能在商品上架时间之前！");
                } else {
                    if (saleEntity.getOffTime() != null) {
                        if (endDate.getTime() > saleEntity.getOffTime().getTime()) {
                            return ResultDtoFactory.toNack("团购结束时间不能在商品下架时间之后！");
                        }
                    }
                }
            }
        }

        if (!kdTeamBuyProductDao.findSameTimeGroupBuyNotSelf(dto.getProductId(), startDate, endDate,
                dto.getGroupBuyId()).isEmpty()) {
            return ResultDtoFactory.toNack("该商品在该活动时间段已经有团购活动！");
        }

        // 校验关联商品不能在同时间段关联24小时活动
        if (!kdTwentyFourHoursDao.findSameTimeTF(dto.getProductId(), startDate, endDate).isEmpty()) {
            return ResultDtoFactory.toNack("该商品在该活动时间段已经有24小时活动！");
        }

        // 校验修改后的规格是否存在
        if (dto.getSpecInfoArry() != null && !dto.getSpecInfoArry().isEmpty()) {
            for (int i = 0; i < dto.getSpecInfoArry().size(); i++) {
                // 解析为库存相同的格式
                String specInfo = dto.getSpecInfoArry().get(i);
                if(StringUtils.isNotBlank(specInfo)){
                    String key = this.getKeyOfSpecDetail(specInfo);
                    
                    List<KdProductStockDetailEntity> detailList = kdProductStockDetailDao.findBySpecGroup(
                            dto.getProductId(), key);
                    // 如果没有该规格或者已删除
                    if (CollectionUtils.isEmpty(detailList)) {
                        return ResultDtoFactory.toNack("该商品规格【"+specInfo+"】已删除");
                    } 
                }
            }
        }

        teamBuy.setProductId(dto.getProductId());
        teamBuy.setName(dto.getGroupBuyName());
        teamBuy.setDescription(dto.getDescription());
        teamBuy.setEffectiveStartDate(startDate);
        teamBuy.setEffectiveEndDate(endDate);
        if (isOverDate(startDate)) {
            if (isOverDate(endDate)) {
                teamBuy.setStatus(KdTeamBuyStatusEnum.OVER.getCode());
            } else {
                teamBuy.setStatus(KdTeamBuyStatusEnum.START.getCode());
            }
        } else {
            teamBuy.setStatus(KdTeamBuyStatusEnum.NOSTART.getCode());
        }
        teamBuy.setImage("".equals(dto.getGroupBuyImage()) ? null : dto.getGroupBuyImage());
        if (!"".equals(dto.getTotalPrice()) && dto.getTotalPrice() != null) {
            teamBuy.setPrice(WRWUtil.transYuanToFen(BigDecimal.valueOf(Double.parseDouble(dto.getTotalPrice())))
                    .intValue());
            teamBuy.setLimitCount(Integer.parseInt(dto.getLimitCount()));
            teamBuy.setPriceType("0");
        } else {
            teamBuy.setPrice(null);
            teamBuy.setLimitCount(null);
            teamBuy.setPriceType("1");
        }
        teamBuy.setStartCount(Integer.parseInt(dto.getStartCount()));
        teamBuy.setSpecialDesc(dto.getSpecialDesc());
        teamBuy.setModifyDate(date);
        teamBuy.setModifyId(userId);
        kdTeamBuyProductDao.save(teamBuy);
        // 保存规格价格信息
        kdTeamBuyProductDetailDao.updateStatusByTeamBuyId(teamBuy.getId());
        if (dto.getSpecInfoArry() != null && !dto.getSpecInfoArry().isEmpty()) {
            List<KdTeamBuyProductDetailEntity> detailList = new ArrayList<KdTeamBuyProductDetailEntity>();
            for (int i = 0; i < dto.getSpecInfoArry().size(); i++) {
                KdTeamBuyProductDetailEntity detailEntity = new KdTeamBuyProductDetailEntity();
                detailEntity.setTeamBuyId(teamBuy.getId());
                detailEntity.setSpecInfo(dto.getSpecInfoArry().get(i));
                detailEntity.setPrice(WRWUtil
                        .transYuanToFen(BigDecimal.valueOf(Double.valueOf(dto.getPrices().get(i)))).toString());
                detailEntity.setLimitCount(dto.getLimits().get(i));
                detailEntity.setStatus(StatusEnum.NORMAL.getCode());
                detailEntity.setCreateDate(date);
                detailEntity.setCreateId(userId);
                detailEntity.setModifyDate(date);
                detailEntity.setModifyId(userId);
                detailList.add(detailEntity);
            }
            kdTeamBuyProductDetailDao.save(detailList);
        }
        // 保存活动图片信息
        kdPImageDao.deleteImage(teamBuy.getId(), KdPImageEnum.TEAMBUY.getCode(), "1");
        if (dto.getImageDtos() != null && !dto.getImageDtos().isEmpty()) {
            List<KdPImageEntity> imageList = new ArrayList<KdPImageEntity>();
            for (KdPImageDto imageDto : dto.getImageDtos()) {
                KdPImageEntity imageEntity = new KdPImageEntity();
                imageEntity.setImageKey(imageDto.getImageKey());
                imageEntity.setImageUrl(imageDto.getImageUrl());
                imageEntity.setIsDeleted("0");
                imageEntity.setKeyId(teamBuy.getId());
                imageEntity.setType(KdPImageEnum.TEAMBUY.getCode());
                imageEntity.setCreateDate(date);
                imageEntity.setCreateId(userId);
                imageEntity.setModifyDate(date);
                imageEntity.setModifyId(userId);
                imageList.add(imageEntity);
            }
            kdPImageDao.save(imageList);
        }
        return ResultDtoFactory.toAck("修改成功！");
    }

    @Override
    public KdGroupBuySaveDto getInfo(Long teamBuyId) {
        KdTeamBuyProductEntity teamBuy = kdTeamBuyProductDao.findOne(teamBuyId);
        KdGroupBuySaveDto dto = new KdGroupBuySaveDto();
        if (teamBuy != null) {
            dto.setGroupBuyName(teamBuy.getName());
            dto.setStartDate(DateTimeUtil.parseDateToString(teamBuy.getEffectiveStartDate(),
                    DateTimeUtil.SIMPLE_FMT_MINUTE));
            dto.setEndDate(DateTimeUtil.parseDateToString(teamBuy.getEffectiveEndDate(), DateTimeUtil.SIMPLE_FMT_MINUTE));
            dto.setGroupBuyImage(teamBuy.getImage());
            dto.setSpecialDesc(teamBuy.getSpecialDesc() == null ? "" : teamBuy.getSpecialDesc());
            KdProductEntity product = kdProductDao.findOne(teamBuy.getProductId());
            if (product != null) {
                dto.setProductId(product.getId());
                dto.setProductCode(product.getPcode());
                dto.setProductName(product.getPname());
            }
            dto.setTotalPrice(teamBuy.getPrice() == null ? "" : WRWUtil.transFenToYuan2StringSimple(teamBuy.getPrice()
                    .longValue()));
            dto.setStartCount(teamBuy.getStartCount().toString());
            dto.setLimitCount(teamBuy.getLimitCount() == null ? "" : teamBuy.getLimitCount().toString());
            dto.setDescription(teamBuy.getDescription());
        }
        return dto;
    }

    @Override
    public ResultDto<List<SpecPriceDto>> getSpecPrice(Long teamBuyId) {
        List<SpecPriceDto> specPriceDtos = new ArrayList<SpecPriceDto>();
        List<KdTeamBuyProductDetailEntity> details = kdTeamBuyProductDetailDao.findByTeamBuyIdAndStatus(teamBuyId,
                StatusEnum.NORMAL.getCode());
        KdTeamBuyProductEntity teamBuy = kdTeamBuyProductDao.findOne(teamBuyId);
        KdProductEntity product = kdProductDao.findOne(teamBuy.getProductId());
        if (details.isEmpty()) {
            SpecPriceDto specPriceDto = new SpecPriceDto();
            specPriceDto.setProductId(product.getId());
            specPriceDto.setProductCode(product.getPcode());
            specPriceDto.setProductName(product.getPname());
            specPriceDtos.add(specPriceDto);
        } else {
            for (KdTeamBuyProductDetailEntity detail : details) {
                SpecPriceDto specPriceDto = new SpecPriceDto();
                specPriceDto.setProductId(product.getId());
                specPriceDto.setProductCode(product.getPcode());
                specPriceDto.setProductName(product.getPname());
                specPriceDto.setSpecInfo(detail.getSpecInfo());
                specPriceDto.setLimitCount(detail.getLimitCount());
                KdProductStockEntity stock = kdProductStockDao.findByProductId(product.getId());
                KdProductStockDetailEntity stockDetail = kdProductStockDetailDao.findByStockIdAndSpecGroup(
                        stock.getId(), buildStockSpec(detail.getSpecInfo()));
                if (stockDetail == null) {
                    specPriceDto.setRealPrice("0");
                } else {
                    if (stockDetail.getPrice() == null) {
                        specPriceDto.setRealPrice("0");
                    } else {
                        specPriceDto.setRealPrice(WRWUtil.transFenToYuan2String(stockDetail.getPrice().longValue()));
                    }
                }
                specPriceDto.setPrice(WRWUtil.transFenToYuan2String(Long.valueOf(detail.getPrice())));
                specPriceDtos.add(specPriceDto);
            }
        }
        return ResultDtoFactory.toAck("获取成功！", specPriceDtos);
    }

    @SuppressWarnings("rawtypes")
    private String buildStockSpec(String specInfo) {
        String specGroup = "";
        JSONObject jsonObject = JSONObject.fromObject(specInfo);
        Iterator iterator = jsonObject.keys();
        List<String> specs = new ArrayList<String>();
        while (iterator.hasNext()) {
            specs.add((String) jsonObject.get(String.valueOf(iterator.next())));
        }
        for (int i = 0; i < specs.size(); i++) {
            if (i == specs.size() - 1) {
                specGroup += specs.get(i);
            } else {
                specGroup += specs.get(i) + "-";
            }
        }
        return specGroup;
    }

    @Override
    public Boolean isGroupBuyProduct(Long productId) {
        return !kdTeamBuyProductDao.findNormalTeamBuy(productId).isEmpty();
    }

    @Override
    @Transactional
    public void saleOffUpdateStatus(Long productId) {
        kdTeamBuyProductDao.updateStatus(productId, KdTeamBuyStatusEnum.PRODUCT_NOT_SALE.getCode(),
                KdTeamBuyStatusEnum.NOSTART.getCode());
    }

    @Override
    public KdStartDateAndEndDateDto getEarlyStartDateAndLateEndDate(Long productId) {
        List<KdTeamBuyProductEntity> entityList = kdTeamBuyProductDao.findByProductId(productId);
        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        Date startDate = null;
        Date endDate = null;
        KdStartDateAndEndDateDto kdStartDateAndEndDateDto = new KdStartDateAndEndDateDto();
        for (KdTeamBuyProductEntity entity : entityList) {
            if (startDate == null) {
                startDate = entity.getEffectiveStartDate();
            } else {
                if (startDate.getTime() > entity.getEffectiveStartDate().getTime()) {
                    startDate = entity.getEffectiveStartDate();
                }
            }
            if (endDate == null) {
                endDate = entity.getEffectiveEndDate();
            } else {
                if (endDate.getTime() < entity.getEffectiveEndDate().getTime()) {
                    endDate = entity.getEffectiveEndDate();
                }
            }
        }
        kdStartDateAndEndDateDto.setStartDate(startDate);
        kdStartDateAndEndDateDto.setEndDate(endDate);
        return kdStartDateAndEndDateDto;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void search(KdProductSearchDto searchDto) {
        StringBuilder countBuilder = new StringBuilder("");
        countBuilder.append("SELECT count(*) FROM (");
        StringBuilder searchBuilder = new StringBuilder();
        searchBuilder.append("SELECT P.id, P.pcode, P.pname, P.origin_price, P.low_price, "
                + "P.high_price, P.create_date, S.sale_status, S.on_sale_time, S.off_sale_time "
                + "FROM kd_p_product P JOIN kd_p_sale S on P.id = S.product_id "
                + "JOIN kd_p_stock KS ON P.id = KS.product_id " + " LEFT JOIN KD_P_STOCK KPS ON P.ID = KPS.PRODUCT_ID "
                + "WHERE 1=1 AND P.is_deleted ='0' AND S.sale_status = '2' AND KPS.ID IS NOT NULL ");

        Map<String, Object> param = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(searchDto.getPcode())) {
            searchBuilder.append(" AND P.PCODE LIKE :CODE");
            param.put("CODE", "%" + searchDto.getPcode() + "%");
        }

        if (StringUtils.isNotBlank(searchDto.getPname())) {
            String productName = searchDto.getPname();
            productName = productName.replace("\\", ESCAPE + "\\");
            productName = productName.replace("%", ESCAPE + "%");
            productName = productName.replace("_", ESCAPE + "_");
            searchBuilder.append(" AND P.PNAME LIKE :NAME escape '\\\\' ");
            param.put("NAME", "%" + productName + "%");
        }

        // 　查询结果总数
        countBuilder.append(searchBuilder).append(" ) A");
        Query coutQuery = entityManager.createNativeQuery(countBuilder.toString());
        QueryUtil.processParamForQuery(coutQuery, param);
        int totalRecord = ((BigInteger) coutQuery.getSingleResult()).intValue();
        searchDto.setTotalRecord((long) totalRecord);

        searchBuilder.append(" ORDER BY P.id DESC");
        Query query = entityManager.createNativeQuery(searchBuilder.toString());
        QueryUtil.processParamForQuery(query, param);
        query.setFirstResult(searchDto.getPageSize() * (searchDto.getCurrentPage() - 1));
        query.setMaxResults(searchDto.getPageSize());
        int pages = totalRecord / searchDto.getPageSize();
        searchDto.setTotalPages(totalRecord % searchDto.getPageSize() == 0 ? pages : pages + 1);
        searchDto.setList(buildProductDtos(query.getResultList()));
    }

    private List<KdProductDto> buildProductDtos(List<Object[]> list) {
        List<KdProductDto> productDtos = new ArrayList<KdProductDto>();
        KdProductDto dto = null;
        for (Object[] array : list) {
            dto = new KdProductDto();
            dto.setId(WRWUtil.objToLong(array[0]));
            dto.setPcode(WRWUtil.objToString(array[1]));
            dto.setPname(WRWUtil.objToString(array[2]));
            dto.setSaleStatus(WRWUtil.objToString(array[7]));
            Long lowPrice = WRWUtil.objToLong(array[4]);
            Long highPrice = WRWUtil.objToLong(array[5]);
            if (lowPrice == highPrice) {
                if (null == array[4]) {
                    dto.setPriceRange("-");
                } else {
                    dto.setPriceRange(WRWUtil.transFenToYuan2String(lowPrice));
                }
            } else {
                dto.setPriceRange(WRWUtil.transFenToYuan2String(lowPrice) + "~"
                        + WRWUtil.transFenToYuan2String(highPrice));
            }
            if (null != array[6]) {
                dto.setCreateDate(DateTimeUtil.parseDate(array[6].toString(), DateTimeUtil.SIMPLE_FMT));
            }
            if (null != array[8]) {
                dto.setOnTime(DateTimeUtil.parseDateToString((Date) array[8], DateTimeUtil.SIMPLE_FMT));
            }
            if (null != array[9]) {
                dto.setOffTime(DateTimeUtil.parseDateToString((Date) array[9], DateTimeUtil.SIMPLE_FMT));
            }
            productDtos.add(dto);
        }
        return productDtos;
    }

    @Override
    @Transactional
    public ResultDto<?> deleteGroupBuy(List<Long> teamBuyIds) {
        SUserEntity user = (SUserEntity) AuthorityContext.getCurrentUser();
        Integer delCnt = kdTeamBuyProductDao.updateStatus(KdTeamBuyStatusEnum.DELETE.getCode(), teamBuyIds, user.getUserId());
        if (delCnt > 0) {
            // 删除团购活动时，将相关的广告位状态标识为删除
            kdAdvertiseService.updateAdvertiseStatus(ActTypeEnum.GROUP_BUY.getKey(), teamBuyIds);
            return ResultDtoFactory.toAck("删除成功！");
        }
        return ResultDtoFactory.toNack("删除失败！");
    }

    @Override
    public ResultDto<?> getActDetailInfo(Long id) {
        // 团购活动
        KdTeamActDetailResponseDto teamResponseDto = getBuyTeamActDetailInfo(id);
        return ResultDtoFactory.toAck("", teamResponseDto);
    }

    /**
     * 
     * Description: 通过团购ID，来获取团购详情信息
     *
     * @param actId
     * @return
     */
    private KdTeamActDetailResponseDto getBuyTeamActDetailInfo(Long id) {
        Long actId = id;
        KdTeamActDetailResponseDto dto = new KdTeamActDetailResponseDto();
        // 封装返回DTO

        // 封装团购基本信息
        KdTeamBuyProductDetailInfoDto infoDto = getTeamActDetailInfo(actId);
        dto = ConverterService.convert(infoDto, KdTeamActDetailResponseDto.class);
        dto.setListSpec(infoDto.getListSpec());

        // 封装参团记录LIST
        List<KdTeamActRecordResponseDto> recordList = new ArrayList<KdTeamActRecordResponseDto>();

        recordList = getTeamActRecordList(actId, 0, 2);

        dto.setRecordList(recordList);

        // 封装 商品图片集合
        List<KdPImageEntity> imageList = kdPImageDao.findByKeyIdAndTypeAndIsDeleted(actId,
                KdPImageEnum.TEAMBUY.getCode(), "0");
        List<String> productImageList = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(imageList)) {
            for (KdPImageEntity imageEntity : imageList) {
                productImageList.add(imageEntity.getImageUrl());
            }
        }
        dto.setImageList(productImageList);

        // 获取参团购买总数
        Integer countNumAct = kdTeamBuyProductDao.findCountNumByTeamId(actId);
        if (countNumAct == null) {
            countNumAct = 0;
        }

        // 剩余件数 = 该商品的库存数 - 购买总数
        // 获取该商品的库存数 设置初始值库存为0
        int productStockNum = getTeamActStockNum(actId);
        if (productStockNum == 0) {
            dto.setSurplusCount(productStockNum);
        } else {
            dto.setSurplusCount(productStockNum - countNumAct);
        }

        // 获取活动参与人数
        int countRecordCycle = kdTeamBuyProductRecordDao.findCountByTeamId(actId);
        dto.setActNum(countRecordCycle);
        dto.setSaleCount(kdTeamBuyProductRecordDao.findSaleCountByTeamId(actId));
        return dto;
    }

    /**
     * 
     * Description: 获取团购基本信息
     *
     * @param actId
     */
    @Override
    public KdTeamBuyProductDetailInfoDto getTeamActDetailInfo(Long actId) {
        KdTeamBuyProductDetailInfoDto dto = new KdTeamBuyProductDetailInfoDto();

        // 封装返回 DTO
        // 封装团购基本信息
        KdTeamBuyProductEntity teamEntity = kdTeamBuyProductDao.findOne(actId);
        if (teamEntity == null) {
            throw new WRWException("获取团购详情信息失败!");
        }

        Long teamId = teamEntity.getId();
        dto = ConverterService.convert(teamEntity, KdTeamBuyProductDetailInfoDto.class);
        dto.setTeamName(teamEntity.getName());

        // 判断是否按价格来还是按规格
        if (TeamPriceTypeEnum.FOLLOW_PRICE.getKey().equals(teamEntity.getPriceType())) {
            dto.setTeamPrice(teamEntity.getPrice().longValue());
            dto.setFormatTeamPrice(WRWUtil.transFenToYuan(teamEntity.getPrice().longValue()).toString());
            dto.setLimitCount(teamEntity.getLimitCount());
        } else {
            Long minPrice = kdTeamBuyProductDetailDao.findMinPriceByTeamId(teamId);
            Long maxPrice = kdTeamBuyProductDetailDao.findMaxPriceByTeamId(teamId);
            String formatMinPrice = WRWUtil.transFenToYuan2String(minPrice);
            String formatMaxPrice = WRWUtil.transFenToYuan2String(maxPrice);
            String formatTeamPrice = "";
            if (minPrice.longValue() == maxPrice.longValue()) {
                formatTeamPrice = formatMinPrice;
            } else {
                formatTeamPrice = formatMinPrice + "~" + formatMaxPrice;
            }
            dto.setFormatTeamPrice(formatTeamPrice);
            dto.setLimitCount(null);
        }

        // 封装团购中商品价格信息
        KdProductStockEntity stockEntity = kdProductStockDao.findByProductId(teamEntity.getProductId());
        if (stockEntity == null) {
            throw new WRWException("获取团购中商品库存信息失败!");
        }
        KdProductEntity productEntity = kdProductDao.findOne(teamEntity.getProductId());
        if (productEntity == null) {
            throw new WRWException("获取团购中商品信息失败!");
        }
        List<String> specActList = kdTeamBuyProductDetailDao.findAllByActId(actId);
        if (!CollectionUtils.isEmpty(specActList)) {// 说明是部分规格都参与
            List<String> specNewList = getProductSpecByTeamSpec(specActList);
            if (!CollectionUtils.isEmpty(specNewList)) {
                List<Long> listAct = new ArrayList<Long>();
                listAct.add(actId);
                List<Object[]> listObj = kdProductStockDetailDao.findAllByProductIdsAndSpecGroup(listAct, specNewList,
                        statusList);
                for (Object[] obj : listObj) {
                    Long maxPrice = WRWUtil.objToLong(obj[1]);
                    Long minPrice = WRWUtil.objToLong(obj[2]);
                    String formatMinPrice = WRWUtil.transFenToYuan2String(minPrice);
                    String formatMaxPrice = WRWUtil.transFenToYuan2String(maxPrice);
                    String formatProductPrice = "";
                    if (maxPrice.longValue() == minPrice.longValue()) {// 说明只有一个规格参与
                        formatProductPrice = formatMaxPrice;
                    } else {
                        formatProductPrice = formatMinPrice + "~" + formatMaxPrice;
                    }
                    dto.setFormatProductPrice(formatProductPrice);
                }
            }
        } else { // 说明是全部规格都参与
            String maxPrice = WRWUtil.transFenToYuan2String(productEntity.getHighPrice().longValue());
            String minPrice = WRWUtil.transFenToYuan2String(productEntity.getLowPrice().longValue());
            dto.setFormatProductPrice(minPrice + "~" + maxPrice);
        }
        dto.setListSpec(getSpecforTeam(actId));
        return dto;
    }

    /**
     * 
     * Description: 通过id和分页参数 来获取参团LIST
     *
     * @param id
     * @param fromIndex
     * @param endIndex
     * @return
     */
    private List<KdTeamActRecordResponseDto> getTeamActRecordList(Long id, int fromIndex, int endIndex) {
        List<KdTeamActRecordResponseDto> recordList = new ArrayList<KdTeamActRecordResponseDto>();
        KdTeamBuyProductEntity teamEntity = kdTeamBuyProductDao.findOne(id);
        if (teamEntity == null) {
            ResultDtoFactory.toNack("获取团购详情信息失败!");
        }
        List<Object[]> list = kdTeamBuyProductRecordDao.findRecordAndCycle(id,
                TeamRecordStatusEnum.RECORD_NOTFULL.getKey(), fromIndex, endIndex);
        if (!CollectionUtils.isEmpty(list)) {
            for (Object[] obj : list) {
                KdTeamActRecordResponseDto recordDto = new KdTeamActRecordResponseDto();
                recordDto.setCreateTeamId(WRWUtil.objToLong(obj[1]));
                recordDto.setRecordId(WRWUtil.objToLong(obj[0]));
                recordDto.setCreateTeamName(WRWUtil.objToString(obj[3]));
                Integer lessNum = teamEntity.getStartCount() - Integer.parseInt(WRWUtil.objToString(obj[2]));
                recordDto.setLessNum(lessNum);
                recordDto.setCustImage(WRWUtil.objToString(obj[4]));
                Double percentage = ((double) Integer.parseInt(WRWUtil.objToString(obj[2])) / (double) teamEntity
                        .getStartCount()) * 100;
                recordDto.setPercentage(percentage);
                recordDto.setEffectiveEndDate(obj[5] == null ? null : (Date) obj[5]);
                recordList.add(recordDto);
            }
        }
        return recordList;
    }

    /**
     * 
     * Description: 通过ActId，获取该团购对应的商品的库存
     *
     * @return
     */
    private Integer getTeamActStockNum(Long actId) {
        Integer productStockNum = 0;
        KdTeamBuyProductEntity teamProductEntity = kdTeamBuyProductDao.findOne(actId);
        if (KdTeamBuyStatusEnum.NORMAL.getCode().equals(teamProductEntity.getStatus())) {
            return productStockNum;
        }
        KdProductStockEntity pStockEntity = kdProductStockDao.findByProductId(teamProductEntity.getProductId());
        if (pStockEntity == null) {
            return productStockNum;
        }
        if (StockTypeEnum.STOCK.getKey().equals(pStockEntity.getStockType())) { // 按总库存
            productStockNum = pStockEntity.getRestAmount();
        } else {// 按规格
                // 获取该团购活动的规格总数
            List<String> specInfos = kdTeamBuyProductDetailDao.findAllByActId(actId);
            if (CollectionUtils.isEmpty(specInfos)) {// 说明该团购活动所有的规格都符合
                Integer countSpec = kdProductStockDetailDao.findCountByStockId(pStockEntity.getId());
                productStockNum = countSpec == null ? 0 : countSpec;
            } else {
                // 找到该团购活动的所有规格
                List<String> productSpec = getProductSpecByTeamSpec(specInfos);
                if (!CollectionUtils.isEmpty(productSpec)) {
                    Integer sumSomeSpecStock = kdProductStockDetailDao.findSumSpecByStockIdAndSomeSpecInfos(
                            pStockEntity.getId(), productSpec);
                    productStockNum = sumSomeSpecStock == null ? 0 : sumSomeSpecStock;
                }
            }
        }
        return productStockNum;
    }

    /**
     * 
     * Description: 通过团购规格集合，转换成商品规格集合
     *
     * @param specInfos
     * @return
     */
    private List<String> getProductSpecByTeamSpec(List<String> specInfos) {
        List<String> productSpec = new ArrayList<String>();
        if (CollectionUtils.isEmpty(specInfos)) {
            return productSpec;
        }
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        for (String str : specInfos) {
            Map<String, String> map = new HashMap<String, String>();
            map = BasicUtil.parseStringToMap(str, Map.class);
            mapList.add(map);
        }
        for (Map<String, String> map : mapList) {
            String specStr = "";
            for (String key : map.keySet()) {
                specStr += map.get(key) + "-";
            }
            productSpec.add(specStr.substring(0, specStr.length() - 1));
        }
        return productSpec;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<KdTeamBuySpecListDto> getSpecforTeam(Long actId) {
        List<KdTeamBuySpecListDto> specTeamList = new ArrayList<KdTeamBuySpecListDto>();
        KdTeamBuyProductEntity teamEntity = kdTeamBuyProductDao.findOne(actId);
        if (teamEntity == null) {
            throw new WRWException("获取团购详情信息失败!");
        }
        // 封装团购中的规格信息
        List<KdTeamBuyProductDetailEntity> list = kdTeamBuyProductDetailDao.findAllByTeamId(actId);
        if (!CollectionUtils.isEmpty(list)) {
            List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
            for (KdTeamBuyProductDetailEntity kdTeamBuyProductDetailEntity : list) {
                Map<String, String> map = new HashMap<String, String>();
                map = BasicUtil.parseStringToMap(kdTeamBuyProductDetailEntity.getSpecInfo(), Map.class);
                mapList.add(map);
            }
            List<KdTeamBuySpecInfoDto> specList = new ArrayList<KdTeamBuySpecInfoDto>();
            for (Map<String, String> map : mapList) {
                for (String key : map.keySet()) {
                    KdTeamBuySpecInfoDto infoDto = new KdTeamBuySpecInfoDto();
                    infoDto.setSpecName(key);
                    infoDto.setSpecValue(map.get(key));
                    specList.add(infoDto);
                }
            }
            Map<String, HashSet<String>> mapInfo = new LinkedHashMap<String, HashSet<String>>();
            for (KdTeamBuySpecInfoDto kdTeamBuySpecInfoDto : specList) {
                HashSet<String> strList = mapInfo.get(kdTeamBuySpecInfoDto.getSpecName());
                if (CollectionUtils.isEmpty(strList)) {
                    strList = new HashSet<String>();
                    strList.add(kdTeamBuySpecInfoDto.getSpecValue());
                    mapInfo.put(kdTeamBuySpecInfoDto.getSpecName(), strList);
                } else {
                    mapInfo.get(kdTeamBuySpecInfoDto.getSpecName()).add(kdTeamBuySpecInfoDto.getSpecValue());
                }
            }
            for (String key : mapInfo.keySet()) {
                KdTeamBuySpecListDto listDto = new KdTeamBuySpecListDto();
                listDto.setMainSpec(key);
                List<String> subSpecs = new ArrayList<String>();
                subSpecs.addAll(mapInfo.get(key));
                listDto.setSubSpecs(subSpecs);
                specTeamList.add(listDto);
            }

        } else {
            if (TeamPriceTypeEnum.FOLLOW_PRICE.getKey().equals(teamEntity.getPriceType())) {// 按全部规格统一价格的话，这样就表示所有的规则都允许
                KdProductStockEntity productStock = kdProductStockDao.findByProductId(teamEntity.getProductId());
                if (StringUtils.isNotBlank(productStock.getSpecInfo())) {
                    JSONArray array = JSONArray.fromObject(productStock.getSpecInfo());
                    for (Iterator<JSONObject> iter = array.iterator(); iter.hasNext();) {
                        JSONObject object = (JSONObject) iter.next();
                        specTeamList.add((KdTeamBuySpecListDto) JSONObject.toBean(object, KdTeamBuySpecListDto.class));
                    }
                }
            }
        }

        return specTeamList;
    }
    
    
    /**
     * Description: 解析规格信息
     *
     * @param specInfo
     * @return
     */
    @SuppressWarnings("unchecked")
    private String getKeyOfSpecDetail(String specInfo) {
        JSONObject json = JSONObject.fromObject(specInfo);
        Iterator<String> it = json.keys();
        String speckey = "";
        while (it.hasNext()) {
            String key = it.next();
            if ("".equals(speckey)) {
                speckey += json.getString(key);
            } else {
                speckey += ("-" + json.getString(key));
            }
        }
        return speckey;
    }
    
}
