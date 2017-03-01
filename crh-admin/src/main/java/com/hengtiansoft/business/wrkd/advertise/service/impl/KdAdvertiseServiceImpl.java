package com.hengtiansoft.business.wrkd.advertise.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.business.wrkd.advertise.dto.ActListDto;
import com.hengtiansoft.business.wrkd.advertise.dto.ActSearchDto;
import com.hengtiansoft.business.wrkd.advertise.dto.FeatureListDto;
import com.hengtiansoft.business.wrkd.advertise.dto.FeatureSearchDto;
import com.hengtiansoft.business.wrkd.advertise.dto.KdAdvertiseAddDto;
import com.hengtiansoft.business.wrkd.advertise.dto.KdAdvertiseDto;
import com.hengtiansoft.business.wrkd.advertise.dto.KdAdvertiseSearchDto;
import com.hengtiansoft.business.wrkd.advertise.dto.KdEmptyDto;
import com.hengtiansoft.business.wrkd.advertise.service.KdAdvertiseService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.authority.domain.UserInfo;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.util.ExportExcleUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.CoolbagFeatureDao;
import com.hengtiansoft.wrw.dao.CoolbagImageDao;
import com.hengtiansoft.wrw.dao.KdAdvertiseDao;
import com.hengtiansoft.wrw.dao.KdCharityDao;
import com.hengtiansoft.wrw.dao.KdTeamBuyProductDao;
import com.hengtiansoft.wrw.dao.KdTwentyFourHoursDao;
import com.hengtiansoft.wrw.dao.SUserDao;
import com.hengtiansoft.wrw.entity.CoolbagFeatureEntity;
import com.hengtiansoft.wrw.entity.CoolbagImageEntity;
import com.hengtiansoft.wrw.entity.KdAdvertiseEntity;
import com.hengtiansoft.wrw.entity.KdCharityEntity;
import com.hengtiansoft.wrw.entity.KdTeamBuyProductEntity;
import com.hengtiansoft.wrw.entity.KdTwentyFourHoursEntity;
import com.hengtiansoft.wrw.enums.ActTypeEnum;
import com.hengtiansoft.wrw.enums.KdAdvertiseSkipTypeEnum;
import com.hengtiansoft.wrw.enums.KdAdvertiseTypeEnum;
import com.hengtiansoft.wrw.enums.StatusEnum;

@Service
public class KdAdvertiseServiceImpl implements KdAdvertiseService {
    
    private static final Integer TYPE_ONE_MAX_COUNT = 8;
    
    private static final Integer TYPE_TWO_MAX_COUNT = 4;
    
    private static final String NOT_AVAILABLE = "0";
    
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private KdAdvertiseDao kdAdvertiseDao;
    @Autowired
    private SUserDao sUserDao;
    @Autowired
    private CoolbagFeatureDao coolbagFeatureDao;
    @Autowired
    private KdTeamBuyProductDao kdTeamBuyProductDao;
    @Autowired
    private KdCharityDao kdCharityDao;
    @Autowired
    private CoolbagImageDao coolbagImageDao;
    @Autowired
    private KdTwentyFourHoursDao kdTwentyFourHoursDao;

    @SuppressWarnings("unchecked")
    @Override
    public void searchAdvertise(final KdAdvertiseSearchDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder conditionSql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");
        StringBuilder sql = new StringBuilder(
                "SELECT AD.ID,AD.NAME,AD.SKIP_TYPE,AD.TYPE,AD.SKIP_URL,AD.ACT_TYPE,AD.ACT_ID FROM KD_ADVERTISE AD WHERE AD.STATUS = '1'");
        countSql.append("SELECT COUNT(1) FROM (").append(sql);
        if (!WRWUtil.isEmpty(dto.getAdvertiseName())) {
            String msg = dto.getAdvertiseName().trim();
            char escape = '\\';
            msg =msg.replace("\\", escape+"\\");
            msg =msg.replace("%", escape+"%");
            msg =msg.replace("_", escape+"_");
            conditionSql.append(" AND REPLACE(AD.NAME,' ','') LIKE REPLACE(:NAME,' ','') ");
            param.put("NAME", "%"+msg+"%");   
        }
        if (!WRWUtil.isEmpty(dto.getType())) {
            conditionSql.append(" AND AD.TYPE = :TYPE");
            param.put("TYPE", dto.getType());   
        }
        Query query = entityManager.createNativeQuery(sql.append(conditionSql).toString());
        QueryUtil.processParamForQuery(query, param);
        Query countQuery = entityManager.createNativeQuery(countSql.append(conditionSql).append(") A").toString());
        QueryUtil.processParamForQuery(countQuery, param);
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        dto.setTotalRecord(totalRecord.longValue());
        dto.setTotalPages(totalRecord.intValue() % dto.getPageSize() == 0 ? totalRecord.intValue() / dto.getPageSize()
                : ((totalRecord.intValue() / dto.getPageSize()) + 1));
        dto.setList(buildKdAdvertiseDtos(query.getResultList()));
    }

    private List<KdAdvertiseDto> buildKdAdvertiseDtos(List<Object[]> list) {
        List<KdAdvertiseDto> dtos = new ArrayList<KdAdvertiseDto>();
        for (Object[] objects : list) {
            KdAdvertiseDto dto = new KdAdvertiseDto();
            String skipType = WRWUtil.objToString(objects[2]);
            if (KdAdvertiseSkipTypeEnum.FEATURE.getCode().equals(skipType)) {
                dto.setAdvertiseName(coolbagFeatureDao.findByRefId(WRWUtil.objToLong(objects[6])).getName());
            }
            if (KdAdvertiseSkipTypeEnum.VIP.getCode().equals(skipType)) {
                String actType = WRWUtil.objToString(objects[5]);
                if (ActTypeEnum.GROUP_BUY.getKey().equals(actType)) {
                    dto.setAdvertiseName(kdTeamBuyProductDao.findOne(WRWUtil.objToLong(objects[6])).getName());
                }
                if (ActTypeEnum.TWENTY_FOUR.getKey().equals(actType)) {
                    dto.setAdvertiseName(kdTwentyFourHoursDao.findOne(WRWUtil.objToLong(objects[6])).getName());
                }
                if (ActTypeEnum.CHARITY.getKey().equals(actType)) {
                    dto.setAdvertiseName(kdCharityDao.findOne(WRWUtil.objToLong(objects[6])).getName());
                }
            }
            if (KdAdvertiseSkipTypeEnum.OTHER.getCode().equals(skipType)) {
                dto.setAdvertiseName(WRWUtil.objToString(objects[1]));
            }
            dto.setAdvertiseId(WRWUtil.objToLong(objects[0]));
            dto.setSkipType(KdAdvertiseSkipTypeEnum.getText(skipType));
            dto.setType(KdAdvertiseTypeEnum.getText(WRWUtil.objToString(objects[3])));
            dto.setSkipUrl(WRWUtil.objToString(objects[4]));
            dto.setActType(WRWUtil.objToString(objects[5]));
            dtos.add(dto);
        }
        return dtos;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void exportSearch(KdAdvertiseSearchDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder conditionSql = new StringBuilder("");
        StringBuilder sql = new StringBuilder(
                "SELECT AD.ID,AD.NAME,AD.SKIP_TYPE,AD.TYPE,AD.SKIP_URL FROM KD_ADVERTISE AD WHERE AD.STATUS = '1'");
        if (WRWUtil.isEmpty(dto.getAdvertiseIds())) {
            if (!WRWUtil.isEmpty(dto.getAdvertiseName())) {
                String msg = dto.getAdvertiseName().trim();
                char escape = '\\';
                msg =msg.replace("\\", escape+"\\");
                msg =msg.replace("%", escape+"%");
                msg =msg.replace("_", escape+"_");
                conditionSql.append(" AND REPLACE(AD.NAME,' ','') LIKE REPLACE(:NAME,' ','') ");
                param.put("NAME", "%"+msg+"%");   
            }
            if (!WRWUtil.isEmpty(dto.getType())) {
                conditionSql.append(" AND AD.TYPE = :TYPE");
                param.put("TYPE", dto.getType());   
            }
        } else {
            String[] advertiseId = dto.getAdvertiseIds().split(",");
            List<String> advertiseIdList = new ArrayList<String>();
            Collections.addAll(advertiseIdList, advertiseId);
            conditionSql.append("AND AD.ID IN :ADVERTISE_IDS ");
            param.put("ADVERTISE_IDS", advertiseIdList);
        }
        conditionSql.append(" ORDER BY AD.CREATE_DATE DESC ");
        Query query = entityManager.createNativeQuery(sql.append(conditionSql).toString());
        QueryUtil.processParamForQuery(query, param);
        dto.setList(buildKdAdvertiseDtos(query.getResultList()));
    }

    @Override
    public HSSFWorkbook toExcle(KdAdvertiseSearchDto pageDto) {
        ArrayList<String> fieldName = new ArrayList<String>();
        ArrayList<String> sheetName = new ArrayList<String>();
        ArrayList<String> fieldStyle = new ArrayList<String>();
        List<List<Object>> fieldData = new ArrayList<List<Object>>();
        ArrayList<Integer> orderNum = new ArrayList<Integer>();
        fieldName.add(0, "广告名称");
        fieldName.add(1, "跳转类型");
        fieldName.add(2, "所属模式");
        fieldName.add(3, "跳转链接");

        for (int j = 0; j <= fieldName.size(); j++) {
            fieldStyle.add(j, "6000");
        }
        pageDto.setPageSize(65536);
        this.exportSearch(pageDto);
        if (pageDto.getList().size() > 0) {
            for (int i = 0; i < pageDto.getList().size(); i++) {
                KdAdvertiseDto dto = pageDto.getList().get(i);
                String name = dto.getAdvertiseName();
                String skipType = dto.getSkipType();
                String type = dto.getType();
                String skipUrl = dto.getSkipUrl();
                List<Object> rowData = new ArrayList<Object>();
                rowData.add(0, name);
                rowData.add(1, skipType);
                rowData.add(2, type);
                rowData.add(3, skipUrl);
                fieldData.add(rowData);
            }
        } else {
            List<Object> rowData = new ArrayList<Object>();
            fieldData.add(rowData);
        }
        sheetName.add(0, "广告列表");
        ExportExcleUtil ex = new ExportExcleUtil(fieldName, sheetName, fieldData, fieldStyle);
        HSSFWorkbook wb = ex.createWorkbook();
        Integer sum = 1;
        if (CollectionUtils.isNotEmpty(orderNum)) {
            for (Integer num : orderNum) {
                for (int i = 0; i <= 5; i++) {
                    wb.getSheetAt(0).addMergedRegion(
                            new org.apache.poi.ss.util.CellRangeAddress(sum, sum + num - 1, i, i));
                }
                for (int i = 14; i <= 20; i++) {
                    wb.getSheetAt(0).addMergedRegion(
                            new org.apache.poi.ss.util.CellRangeAddress(sum, sum + num - 1, i, i));
                }
                sum += num;
            }
        }
        return wb;
    }

    @Override
    @Transactional
    public ResultDto<?> addOrUpdateAdvertise(KdAdvertiseAddDto dto) {
        if (dto.getOperType() == null) {
            return ResultDtoFactory.toNack("参数有误！");
        }
        if ("0".equals(dto.getOperType())) {
            List<KdAdvertiseEntity> advertiseList = new ArrayList<KdAdvertiseEntity>();
            if (KdAdvertiseTypeEnum.TYPE_ONE.getCode().equals(dto.getType())) {
                // 模式一最多八个广告
                advertiseList = kdAdvertiseDao.findByStatusAndType(StatusEnum.NORMAL.getCode(), KdAdvertiseTypeEnum.TYPE_ONE.getCode());
                if (!advertiseList.isEmpty() && advertiseList.size() >= TYPE_ONE_MAX_COUNT) {
                    return ResultDtoFactory.toNack("最多只能有八个广告");
                }
            } else {
                // 模式二最多四个广告
                advertiseList = kdAdvertiseDao.findByStatusAndType(StatusEnum.NORMAL.getCode(), KdAdvertiseTypeEnum.TYPE_TWO.getCode());
                if (!advertiseList.isEmpty() && advertiseList.size() >= TYPE_TWO_MAX_COUNT) {
                    return ResultDtoFactory.toNack("最多只能有四个广告");
                }
            }
            if (advertiseList.isEmpty()) {
                kdAdvertiseDao.addRecord(dto.getName(), dto.getSkipType(), dto.getSkipUrl(), dto.getType(), dto.getActId(), dto.getActType(), dto.getImageUrl(), StatusEnum.DELETE.getCode());
            } else {
                if (StatusEnum.NORMAL.getCode().equals(advertiseList.get(0).getIsAvailable())) {
                    kdAdvertiseDao.addRecord(dto.getName(), dto.getSkipType(), dto.getSkipUrl(), dto.getType(), dto.getActId(), dto.getActType(), dto.getImageUrl(), StatusEnum.NORMAL.getCode());
                } else {
                    kdAdvertiseDao.addRecord(dto.getName(), dto.getSkipType(), dto.getSkipUrl(), dto.getType(), dto.getActId(), dto.getActType(), dto.getImageUrl(), StatusEnum.DELETE.getCode());
                }
            }
            return ResultDtoFactory.toAck("保存成功！"); 
        } else {
            UserInfo userInfo = AuthorityContext.getCurrentUser();
            if (userInfo == null) {
                return ResultDtoFactory.toNack("无法获取当前管理员信息！");
            }
            kdAdvertiseDao.editorRecord(dto.getName(), dto.getSkipType(), dto.getSkipUrl(), dto.getType(), dto.getActId(), dto.getActType(), dto.getImageUrl(), userInfo.getUserId(), dto.getAdvertiseId());
            return ResultDtoFactory.toAck("编辑成功！"); 
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void searchFeatureList(final FeatureSearchDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder conditionSql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");
        StringBuilder sql = new StringBuilder(
                "SELECT * FROM (SELECT CFR.ID,CF.NAME FEATURE_NAME,CI.NAME,CF.ID C_ID,CF.CREATE_DATE FROM COOLBAG_FEATURE CF JOIN COOLBAG_FEATURE_TAG_REF CFR ON CF.ID = CFR.FEATURE_ID JOIN COOLBAG_IMAGE CI ON CFR.TAG_ID = CI.ID "
                + " WHERE CF.STATUS = '1' AND CF.SHEIF_STATUS = '1' AND CI.STATUS = '1' ");
        countSql.append("SELECT COUNT(1) FROM (").append(sql);
        if (!WRWUtil.isEmpty(dto.getFeatureName())) {
            String msg = dto.getFeatureName().trim();
            char escape = '\\';
            msg =msg.replace("\\", escape+"\\");
            msg =msg.replace("%", escape+"%");
            msg =msg.replace("_", escape+"_");
            conditionSql.append(" AND REPLACE(CF.NAME,' ','') LIKE REPLACE(:NAME,' ','') ");
            param.put("NAME", "%"+msg+"%");   
        }
        if (dto.getTagId() != null) {
            conditionSql.append(" AND CI.ID = :ID ");
            param.put("ID", dto.getTagId());   
        }
        conditionSql.append(" ORDER BY CF.CREATE_DATE DESC) B GROUP BY B.C_ID ORDER BY B.CREATE_DATE DESC ");
        Query query = entityManager.createNativeQuery(sql.append(conditionSql).toString());
        QueryUtil.processParamForQuery(query, param);
        Query countQuery = entityManager.createNativeQuery(countSql.append(conditionSql).append(") A").toString());
        QueryUtil.processParamForQuery(countQuery, param);
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        dto.setTotalRecord(totalRecord.longValue());
        dto.setTotalPages(totalRecord.intValue() % dto.getPageSize() == 0 ? totalRecord.intValue() / dto.getPageSize()
                : ((totalRecord.intValue() / dto.getPageSize()) + 1));
        dto.setList(buildFeatureDto(query.getResultList()));
    }

    private List<FeatureListDto> buildFeatureDto(List<Object[]> list) {
        List<FeatureListDto> featureListDtoList = new ArrayList<FeatureListDto>();
        for (int i = 0; i < list.size(); i++) {
            FeatureListDto featureDto = new FeatureListDto();
            featureDto.setFeatureId(WRWUtil.objToLong(list.get(i)[0]));
            featureDto.setFeatureName(WRWUtil.objToString(list.get(i)[1]));
            featureDto.setTagName(coolbagFeatureDao.findTagName(WRWUtil.objToLong(list.get(i)[3])));
            featureDto.setIndex(i + 1);
            featureListDtoList.add(featureDto);
        }
        return featureListDtoList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void searchActList(final ActSearchDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder countSql = new StringBuilder("");
        StringBuilder sql = new StringBuilder("");
        if(WRWUtil.isEmpty(dto.getActType())) {
            // 3张表的数据合并一个
            // 团购
            sql.append("SELECT ID,NAME,'0' TYPE,CREATE_DATE FROM KD_TEAM_BUY_PRODUCT WHERE STATUS = '3' ");
            if (!WRWUtil.isEmpty(dto.getActName())) {
                String msg = dto.getActName().trim();
                char escape = '\\';
                msg =msg.replace("\\", escape+"\\");
                msg =msg.replace("%", escape+"%");
                msg =msg.replace("_", escape+"_");
                sql.append(" AND REPLACE(NAME,' ','') LIKE REPLACE(:GP_NAME,' ','') ");
                param.put("GP_NAME", "%" + msg + "%");
            }
            // 24小时
            sql.append(" UNION ALL SELECT ID,NAME,'1' TYPE,CREATE_DATE FROM KD_TWENTY_FOUR_HOURS WHERE DEL_FLAG = '1' AND STATUS = '3'");
            if (!WRWUtil.isEmpty(dto.getActName())) {
                String msg = dto.getActName().trim();
                char escape = '\\';
                msg =msg.replace("\\", escape+"\\");
                msg =msg.replace("%", escape+"%");
                msg =msg.replace("_", escape+"_");
                sql.append(" AND REPLACE(NAME,' ','') LIKE REPLACE(:BG_NAME,' ','') ");
                param.put("BG_NAME", "%" + msg + "%");
            }
            sql.append(" UNION ALL SELECT ID,NAME,'2' TYPE,CREATE_DATE FROM KD_CHARITY WHERE STATUS = '3' ");
            if (!WRWUtil.isEmpty(dto.getActName())) {
                String msg = dto.getActName().trim();
                char escape = '\\';
                msg =msg.replace("\\", escape+"\\");
                msg =msg.replace("%", escape+"%");
                msg =msg.replace("_", escape+"_");
                sql.append(" AND REPLACE(NAME,' ','') LIKE REPLACE(:C_NAME,' ','') ");
                param.put("C_NAME", "%" + msg + "%");
            }
        } else {
            if(ActTypeEnum.GROUP_BUY.getKey().equals(dto.getActType())) {
                // 团购
                sql.append("SELECT ID,NAME,'0' TYPE,CREATE_DATE FROM KD_TEAM_BUY_PRODUCT WHERE STATUS = '3' ");
                if (!WRWUtil.isEmpty(dto.getActName())) {
                    String msg = dto.getActName().trim();
                    char escape = '\\';
                    msg =msg.replace("\\", escape+"\\");
                    msg =msg.replace("%", escape+"%");
                    msg =msg.replace("_", escape+"_");
                    sql.append(" AND REPLACE(NAME,' ','') LIKE REPLACE(:GP_NAME,' ','') ");
                    param.put("GP_NAME", "%" + msg + "%");
                }
            } else if (ActTypeEnum.CHARITY.getKey().equals(dto.getActType())) {
                sql.append("SELECT ID,NAME,'2' TYPE,CREATE_DATE FROM KD_CHARITY WHERE STATUS = '3' ");
                if (!WRWUtil.isEmpty(dto.getActName())) {
                    String msg = dto.getActName().trim();
                    char escape = '\\';
                    msg =msg.replace("\\", escape+"\\");
                    msg =msg.replace("%", escape+"%");
                    msg =msg.replace("_", escape+"_");
                    sql.append(" AND REPLACE(NAME,' ','') LIKE REPLACE(:C_NAME,' ','') ");
                    param.put("C_NAME", "%" + msg + "%");
                }
            } else {
                // 24小时
                sql.append("SELECT ID,NAME,'1' TYPE,CREATE_DATE FROM KD_TWENTY_FOUR_HOURS WHERE DEL_FLAG = '1' AND STATUS = '3' ");
                if (!WRWUtil.isEmpty(dto.getActName())) {
                    String msg = dto.getActName().trim();
                    char escape = '\\';
                    msg =msg.replace("\\", escape+"\\");
                    msg =msg.replace("%", escape+"%");
                    msg =msg.replace("_", escape+"_");
                    sql.append(" AND REPLACE(NAME,' ','') LIKE REPLACE(:BG_NAME,' ','') ");
                    param.put("BG_NAME", "%" + msg + "%");
                }
            }
        }
        sql.append(" ORDER BY CREATE_DATE DESC ");
        Query query = entityManager.createNativeQuery(sql.toString());
        QueryUtil.processParamForQuery(query, param);
        countSql.append("SELECT COUNT(1) FROM (").append(sql);
        Query countQuery = entityManager.createNativeQuery(countSql.append(") A").toString());
        QueryUtil.processParamForQuery(countQuery, param);
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        dto.setTotalRecord(totalRecord.longValue());
        dto.setTotalPages(totalRecord.intValue() % dto.getPageSize() == 0 ? totalRecord.intValue() / dto.getPageSize()
                : ((totalRecord.intValue() / dto.getPageSize()) + 1));
        dto.setList(buildActDto(query.getResultList()));
    }

    private List<ActListDto> buildActDto(List<Object[]> list) {
        List<ActListDto> actListDtos = new ArrayList<ActListDto>();
        for (int i = 0; i < list.size(); i++) {
            ActListDto actListDto = new ActListDto();
            actListDto.setActId(WRWUtil.objToLong(list.get(i)[0]));
            actListDto.setActName(WRWUtil.objToString(list.get(i)[1]));
            actListDto.setActType(ActTypeEnum.getValue(WRWUtil.objToString(list.get(i)[2])));
            actListDto.setIndex(i + 1);
            actListDtos.add(actListDto);
        }
        return actListDtos;
    }

    @Override
    public List<KdAdvertiseAddDto> getAdvertiseList(Long advertiseId, String adType) {
        List<KdAdvertiseAddDto> dtoList = new ArrayList<KdAdvertiseAddDto>();
        if (advertiseId != null && adType != null) {
            // 查看或编辑 
            KdAdvertiseEntity advertise = kdAdvertiseDao.findByIdAndStatus(advertiseId, StatusEnum.NORMAL.getCode());
            if (advertise != null) {
                KdAdvertiseAddDto dto = new KdAdvertiseAddDto();
                if (advertise.getSkipType().equals(KdAdvertiseSkipTypeEnum.FEATURE.getCode())) {
                    CoolbagFeatureEntity feature = coolbagFeatureDao.findByRefId(advertise.getActId());
                    dto.setName(feature == null ? "" : feature.getName());
                    dto.setImageUrl(feature.getImage());
                    dto.setSkipUrl(feature == null ? "" : feature.getBuyUrl() == null ? "" : feature.getBuyUrl());
                    dto.setTagName(coolbagFeatureDao.findTagName(feature.getId()));
                }
                if (advertise.getSkipType().equals(KdAdvertiseSkipTypeEnum.VIP.getCode())) {
                    // 团购
                    if (advertise.getActType().equals(ActTypeEnum.GROUP_BUY.getKey())) {
                        KdTeamBuyProductEntity groupBuy = kdTeamBuyProductDao.findOne(advertise.getActId());
                        dto.setName(groupBuy == null ? "" : groupBuy.getName());
                        dto.setImageUrl(groupBuy.getImage());
                        dto.setTagName(ActTypeEnum.GROUP_BUY.getValue());
                        dto.setSkipUrl("");
                    }
                    // 24小时
                    if (advertise.getActType().equals(ActTypeEnum.TWENTY_FOUR.getKey())) {
                        KdTwentyFourHoursEntity twentyFour = kdTwentyFourHoursDao.findOne(advertise.getActId());
                        dto.setName(twentyFour == null ? "" : twentyFour.getName());
                        dto.setImageUrl(null);
                        dto.setTagName(ActTypeEnum.TWENTY_FOUR.getValue());
                        dto.setSkipUrl("");
                    }
                    // 公益
                    if (advertise.getActType().equals(ActTypeEnum.CHARITY.getKey())) {
                        KdCharityEntity charity = kdCharityDao.findOne(advertise.getActId());
                        dto.setName(charity == null ? "" : charity.getName());
                        dto.setImageUrl(charity.getImgUrl());
                        dto.setTagName(ActTypeEnum.CHARITY.getValue());
                        dto.setSkipUrl("");
                    }
                }
                if (advertise.getSkipType().equals(KdAdvertiseSkipTypeEnum.OTHER.getCode())) {
                    dto.setSkipUrl(advertise.getSkipUrl());
                    dto.setName(advertise.getName());
                    dto.setImageUrl(advertise.getImageUrl());
                    dto.setTagName("");
                }
                dto.setType(KdAdvertiseTypeEnum.getText(adType));
                dto.setSkipType(KdAdvertiseSkipTypeEnum.getText(advertise.getSkipType()));
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    @Override
    @Transactional
    public ResultDto<?> deleteAdvertise(Long advertiseId) {
        if (kdAdvertiseDao.deleteAdvertise(advertiseId) > 0) {
            return ResultDtoFactory.toAck("删除成功！");
        }
        return ResultDtoFactory.toNack("删除失败！");
    }

    @Override
    public List<KdAdvertiseAddDto> getAdvertiseOneList() {
        List<KdAdvertiseAddDto> dtoList = new ArrayList<KdAdvertiseAddDto>();
        // 添加广告首页
        List<KdAdvertiseEntity> advertiseList = kdAdvertiseDao.findByStatusAndType(StatusEnum.NORMAL.getCode(), KdAdvertiseTypeEnum.TYPE_ONE.getCode());
        Integer index = 1;
        for (KdAdvertiseEntity advertise : advertiseList) {
            KdAdvertiseAddDto dto = new KdAdvertiseAddDto();
            if (advertise.getSkipType().equals(KdAdvertiseSkipTypeEnum.FEATURE.getCode())) {
                dto.setActId(advertise.getActId());
                CoolbagFeatureEntity feature = coolbagFeatureDao.findByRefId(advertise.getActId());
                dto.setName(feature == null ? "" : feature.getName());
                dto.setAdvertiseId(advertise.getId());
                dto.setSkipType(KdAdvertiseSkipTypeEnum.getDesc(advertise.getSkipType()));
                dto.setActId(advertise.getActId());
            }
            if (advertise.getSkipType().equals(KdAdvertiseSkipTypeEnum.VIP.getCode())) {
                // 团购
                if (advertise.getActType().equals(ActTypeEnum.GROUP_BUY.getKey())) {
                    KdTeamBuyProductEntity groupBuy = kdTeamBuyProductDao.findOne(advertise.getActId());
                    dto.setName(groupBuy == null ? "" : groupBuy.getName());
                    dto.setActType(ActTypeEnum.GROUP_BUY.getKey());
                }
                // 24小时
                if (advertise.getActType().equals(ActTypeEnum.TWENTY_FOUR.getKey())) {
                    KdTwentyFourHoursEntity twentyFour = kdTwentyFourHoursDao.findOne(advertise.getActId());
                    dto.setName(twentyFour == null ? "" : twentyFour.getName());
                    dto.setActType(ActTypeEnum.TWENTY_FOUR.getKey());
                }
                // 公益
                if (advertise.getActType().equals(ActTypeEnum.CHARITY.getKey())) {
                    KdCharityEntity charity = kdCharityDao.findOne(advertise.getActId());
                    dto.setName(charity == null ? "" : charity.getName());
                    dto.setActType(ActTypeEnum.CHARITY.getKey());
                }
                dto.setAdvertiseId(advertise.getId());
                dto.setSkipType(KdAdvertiseSkipTypeEnum.getDesc(advertise.getSkipType()));
                dto.setActId(advertise.getActId());
            }
            if (advertise.getSkipType().equals(KdAdvertiseSkipTypeEnum.OTHER.getCode())) {
                dto.setAdvertiseId(advertise.getId());
                dto.setSkipType(KdAdvertiseSkipTypeEnum.OTHER.getDesc());
                dto.setSkipUrl(advertise.getSkipUrl());
                dto.setName(advertise.getName());
                dto.setImageUrl(advertise.getImageUrl());
            }
            dto.setIndex(index++);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getAdvertiseTwoList(final KdEmptyDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder countSql = new StringBuilder("");
        StringBuilder sql = new StringBuilder(" SELECT ID,IMAGE_URL,NAME,SKIP_URL FROM KD_ADVERTISE "
                + " WHERE STATUS = '1' AND TYPE = '2' ");
        countSql.append(" SELECT COUNT(1) FROM ( ").append(sql);
        Query query = entityManager.createNativeQuery(sql.toString());
        QueryUtil.processParamForQuery(query, param);
        Query countQuery = entityManager.createNativeQuery(countSql.append(") A").toString());
        QueryUtil.processParamForQuery(countQuery, param);
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        dto.setTotalRecord(totalRecord.longValue());
        dto.setTotalPages(totalRecord.intValue() % dto.getPageSize() == 0 ? totalRecord.intValue() / dto.getPageSize()
                : ((totalRecord.intValue() / dto.getPageSize()) + 1));
        dto.setList(buildKdAdvertiseAddDto(query.getResultList()));
    }

    private List<KdAdvertiseAddDto> buildKdAdvertiseAddDto(List<Object[]> list) {
        List<KdAdvertiseAddDto> kdList = new ArrayList<KdAdvertiseAddDto>();
        for (int i = 0; i < list.size(); i++) {
            KdAdvertiseAddDto dto = new KdAdvertiseAddDto();
            dto.setIndex(i + 1);
            dto.setAdvertiseId(WRWUtil.objToLong(list.get(i)[0]));
            dto.setImageUrl(WRWUtil.objToString(list.get(i)[1]));
            dto.setName(WRWUtil.objToString(list.get(i)[2]));
            dto.setSkipUrl(WRWUtil.objToString(list.get(i)[3]));
            kdList.add(dto);
        }
        return kdList;
    }

    @Override
    public List<CoolbagImageEntity> findAllTag() {
        return coolbagImageDao.findByTypeAndStatus("1", "1");
    }

    @Override
    public String getSwitchStatus(String type, String status) {
        List<KdAdvertiseEntity> advertise = kdAdvertiseDao.findByStatusAndType(status, type);
        if (!advertise.isEmpty()) {
            return advertise.get(0).getIsAvailable();
        }
        return NOT_AVAILABLE;
    }

    @Override
    @Transactional
    public ResultDto<?> switchStatus(String openType, String closeType, String index) {
        if (index.equals(openType)) {
            kdAdvertiseDao.switchStatus("1", openType);
        } else {
            kdAdvertiseDao.switchStatus("0", closeType);
        }
        if (kdAdvertiseDao.findByStatusAndType(StatusEnum.NORMAL.getCode(), openType).isEmpty()) {
            if (index.equals(openType)) {
                return ResultDtoFactory.toNack("请先添加" + KdAdvertiseTypeEnum.getText(openType) + "广告");
            } else {
                return ResultDtoFactory.toAck("获取成功！");
            }
        }
        if (index.equals(openType)) {
            kdAdvertiseDao.switchStatus("0", closeType);
        } else {
            kdAdvertiseDao.switchStatus("1", openType);
        }
        return ResultDtoFactory.toAck("操作成功！");
    }

    @Override
    @Transactional
    public ResultDto<?> updateAdvertiseStatus(String actType,List<Long> actIds) {
        kdAdvertiseDao.updateAdvertiseStatus(StatusEnum.REMOVED.getCode(), actType, actIds);
        return ResultDtoFactory.toAck("操作成功！");
    }

    @Override
    @Transactional
    public ResultDto<?> updateAdvertiseStatus(List<Long> productIds) {
        if(!CollectionUtils.isEmpty(productIds)){
            for (Long pId : productIds) {
                kdAdvertiseDao.updateAdvertiseStatus(StatusEnum.REMOVED.getCode(), "proDetail.html?proId="+pId);
            }
        }
        return ResultDtoFactory.toAck("操作成功！");
    }
}
