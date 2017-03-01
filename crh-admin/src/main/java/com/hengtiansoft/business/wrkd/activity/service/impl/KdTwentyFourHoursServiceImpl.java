package com.hengtiansoft.business.wrkd.activity.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.business.wrkd.activity.dto.KdBargainAllSpecInfo;
import com.hengtiansoft.business.wrkd.activity.dto.KdBargainDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdBargainPageDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdBargainProSpecPriceDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdBargainSaveAndEditDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdBargainSpecDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdBargainSubSpecDetailDto;
import com.hengtiansoft.business.wrkd.activity.dto.TwentyfourInfo;
import com.hengtiansoft.business.wrkd.activity.exception.TwentyfourException;
import com.hengtiansoft.business.wrkd.activity.service.KdTwentyFourHoursService;
import com.hengtiansoft.business.wrkd.advertise.service.KdAdvertiseService;
import com.hengtiansoft.business.wrkd.image.dto.KdPImageDto;
import com.hengtiansoft.business.wrkd.image.service.KdPImageService;
import com.hengtiansoft.business.wrkd.product.dto.KdProductDto;
import com.hengtiansoft.business.wrkd.product.dto.KdProductSpecDto;
import com.hengtiansoft.business.wrkd.product.service.KdProductService;
import com.hengtiansoft.business.wrkd.product.service.KdProductSpecService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.enumeration.DelFlagEnum;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.BasicUtil;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.DateUtils;
import com.hengtiansoft.common.util.StringUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.KdPImageDao;
import com.hengtiansoft.wrw.dao.KdProductDao;
import com.hengtiansoft.wrw.dao.KdProductStockDao;
import com.hengtiansoft.wrw.dao.KdTFPriceDetailDao;
import com.hengtiansoft.wrw.dao.KdTeamBuyProductDao;
import com.hengtiansoft.wrw.dao.KdTwentyFourHoursDao;
import com.hengtiansoft.wrw.entity.KdProductEntity;
import com.hengtiansoft.wrw.entity.KdProductStockEntity;
import com.hengtiansoft.wrw.entity.KdTFPriceDetailEntity;
import com.hengtiansoft.wrw.entity.KdTwentyFourHoursEntity;
import com.hengtiansoft.wrw.enums.ActTypeEnum;
import com.hengtiansoft.wrw.enums.BargainTypeEnum;
import com.hengtiansoft.wrw.enums.KdBargainStatusEnum;
import com.hengtiansoft.wrw.enums.KdPImageEnum;

/**
 * 
 * Class Name: KdTwentyFourHoursServiceImpl Description: 24小时管理业务实现
 * 
 * @author chengchaoyin
 *
 */
@Service
public class KdTwentyFourHoursServiceImpl implements KdTwentyFourHoursService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private KdTwentyFourHoursDao kdTwentyFourHoursDao;

    @Autowired
    private KdPImageService kdPImageService;

    @Autowired
    private KdTFPriceDetailDao kdTFPriceDetailDao;

    @Autowired
    private KdProductService kdProductService;

    @Autowired
    private KdProductStockDao kdProductStockDao;

    @Autowired
    private KdProductSpecService kdProductSpecService;

    @Autowired
    private KdPImageDao kdPImageDao;

    @Autowired
    private KdProductDao kdProductDao;
    
    @Autowired
    private KdTeamBuyProductDao kdTeamBuyProductDao;
    
    @Autowired
    private KdAdvertiseService kdAdvertiseService;
    

    @SuppressWarnings("unchecked")
    @Override
    public void search(KdBargainPageDto dto) {

        StringBuilder sqlDataBuilder = new StringBuilder(); // 详细分页数据
        StringBuilder sqlCountBuilder = new StringBuilder(); // 统计总数
        Map<String, Object> map = new HashMap<String, Object>();
        sqlDataBuilder
                .append("SELECT tf.ID,tf.NAME,tf.PRODUCT_ID,p.PNAME as productName,tf.CREATE_DATE,tf.EFFECTIVE_START_DATE,tf.EFFECTIVE_END_DATE,tf.`STATUS`,p.PCODE "
                        + " FROM kd_twenty_four_hours tf INNER JOIN kd_p_product p ON tf.PRODUCT_ID = p.ID "
                        + " where tf.DEL_FLAG = :delFlag ");

        sqlCountBuilder.append("select count(1) "
                + " FROM kd_twenty_four_hours tf INNER JOIN kd_p_product p ON tf.PRODUCT_ID = p.ID "
                + " where tf.DEL_FLAG = :delFlag ");

        StringBuffer conditionBuffer = new StringBuffer(); // 公用条件condition
        map.put("delFlag", DelFlagEnum.UN_DEL.getCode());

        if (!WRWUtil.isEmpty(dto.getStatus())) {
            String status = dto.getStatus();
            String[] statusArray = status.split(",");
            if (statusArray.length > 1) {
                List<String> statusList = new ArrayList<String>();
                Collections.addAll(statusList, statusArray);
                conditionBuffer.append(" and tf.STATUS IN (:status) ");
                map.put("status", statusList);
            } else {
                conditionBuffer.append(" and tf.`STATUS`= :status ");
                map.put("status", dto.getStatus());
            }
        }

        // 活动名称
        if (!WRWUtil.isEmpty(dto.getName())) {
            conditionBuffer.append(" and tf.NAME like :name ");
            map.put("name", "%" + dto.getName().trim() + "%");
        }

        // 商品名
        if (!WRWUtil.isEmpty(dto.getProductName())) {
            conditionBuffer.append(" and p.PNAME like :productName ");
            map.put("productName", "%" + dto.getProductName().trim() + "%");
        }

        // 商品名id
        if (dto.getProductId() != null) {
            conditionBuffer.append(" and p.id= :productId ");
            map.put("productId", dto.getProductId());
        }

        // 商品名编号
        if (dto.getProductCode() != null) {
            conditionBuffer.append(" and p.PCODE like :productCode ");
            map.put("productCode", "%" + dto.getProductCode() + "%");
        }

        // 开始时间为空但是结束时间不为空
        if (WRWUtil.isEmpty(dto.getStartDate())) {
            if (!WRWUtil.isEmpty(dto.getEndDate())) {
                conditionBuffer.append(" AND tf.CREATE_DATE <= :createDate ");
                map.put("createDate", DateTimeUtil.getDateEnd(dto.getEndDate()));
            }
        }

        // 开始时间不为空
        if (!WRWUtil.isEmpty(dto.getStartDate())) {
            // 结束时间为空
            if (WRWUtil.isEmpty(dto.getEndDate())) {
                conditionBuffer.append(" AND tf.CREATE_DATE >= :createDate ");
                map.put("createDate", DateTimeUtil.getDateBegin(dto.getStartDate()));
            } else {
                // 结束时间不为空
                conditionBuffer.append(" AND tf.CREATE_DATE BETWEEN :sDate AND :eDate ");
                map.put("sDate", DateTimeUtil.getDateBegin(dto.getStartDate()));
                map.put("eDate", DateTimeUtil.getDateEnd(dto.getEndDate()));
            }
        }
        if (!WRWUtil.isEmpty(dto.getOnTime()) && !WRWUtil.isEmpty(dto.getOffTime())) {
            // 结束时间不为空
            conditionBuffer.append(" AND tf.EFFECTIVE_END_DATE > :onTime AND tf.EFFECTIVE_START_DATE < :offTime ");
            map.put("onTime", DateUtils.getFormatDateByString(dto.getOnTime(), DateUtils.STRING_PATTERN_FULL));
            map.put("offTime", DateUtils.getFormatDateByString(dto.getOffTime(), DateUtils.STRING_PATTERN_FULL));
        }

        // 过滤已有的活动
        if (dto.getId() != null) {
            conditionBuffer.append(" AND tf.id != :tfId ");
            map.put("tfId", dto.getId());
        }

        Query query = entityManager.createNativeQuery(sqlDataBuilder.append(conditionBuffer)
                .append(" order by tf.CREATE_DATE desc ").toString());
        Query countQuery = entityManager.createNativeQuery(sqlCountBuilder.append(conditionBuffer).toString());
        QueryUtil.processParamForQuery(query, map);
        QueryUtil.processParamForQuery(countQuery, map);
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        dto.setTotalRecord(totalRecord.longValue());
        dto.setTotalPages(totalRecord.intValue() % dto.getPageSize() == 0 ? totalRecord.intValue() / dto.getPageSize()
                : ((totalRecord.intValue() / dto.getPageSize()) + 1));
        dto.setList(buildBargainDtos(query.getResultList()));
    }

    private List<KdBargainDto> buildBargainDtos(List<Object[]> resultList) {
        List<KdBargainDto> content = new ArrayList<KdBargainDto>();
        for (Object[] arr : resultList) {
            KdBargainDto rpd = new KdBargainDto();
            if (null != arr[0]) {
                rpd.setId(Long.valueOf(arr[0].toString()));
            }
            if (null != arr[1]) {
                rpd.setName(arr[1].toString());
            }
            if (null != arr[2]) {
                rpd.setProductId(Long.valueOf(arr[2].toString()));
            }
            if (null != arr[3]) {
                rpd.setProductName(arr[3].toString());
            }
            if (null != arr[4]) {
                rpd.setCreateDate((Date) arr[4]);
            }
            if (null != arr[5]) {
                rpd.setEffectiveStartDate((Date) arr[5]);
            }
            if (null != arr[6]) {
                rpd.setEffectiveEndDate((Date) arr[6]);
            }
            if (null != arr[7]) {
                rpd.setStatus(KdBargainStatusEnum.getValue(arr[7].toString()));
            }
            if (null != arr[8]) {
                rpd.setProductCode(arr[8].toString());
            }
            content.add(rpd);
        }
        return content;
    }

    @Override
    @Transactional
    public void save(KdBargainSaveAndEditDto dto) {
        // 验证
        if (WRWUtil.isEmpty(dto.getName())) {
            throw new WRWException("活动名称不可为空，请输入!");
        }
        if (dto.getExplainInfoPics() == null || dto.getExplainInfoPics().getImageKey() == null) {
            throw new WRWException("活动说明图片不可为空，请添加!");
        }
        if (null == dto.getEffectiveStartDate() || null == dto.getEffectiveEndDate()) {
            throw new WRWException("活动有效期不可为空，请选择!");
        }
        if (WRWUtil.isEmpty(dto.getDescription())) {
            throw new WRWException("商品详情不可为空，请输入!");
        }
        if (WRWUtil.isEmpty(dto.getSpecType()) && "1".equals(dto.getSpecType())
                && CollectionUtils.isEmpty(dto.getSpecInfoList())) {
            throw new WRWException("选中商品规格参数不可为空，请联系后台管理员!");
        }
        if (WRWUtil.isNotEmpty(dto.getSpecType()) && "1".equals(dto.getSpecType())
                && !CollectionUtils.isEmpty(dto.getSpecInfoList())) {
            for (KdBargainProSpecPriceDto kdBargainProSpecPriceDto : dto.getSpecInfoList()) {
                if (kdBargainProSpecPriceDto.getProductBasePrice() > kdBargainProSpecPriceDto.getProductPrice()) {
                    throw new WRWException("规格商品的底价必须小于该规格商品价格！");
                }
            }
        }
        if (WRWUtil.isEmpty(dto.getSpecType()) && "0".equals(dto.getSpecType()) && null == dto.getBasePrice()) {
            throw new WRWException("统一的活动商品底价不可为空，请输入!");
        }
        if (WRWUtil.isEmpty(dto.getBargainType())) {
            throw new WRWException("砍价金额方式不可为空，请选择!");
        } else if (dto.getBargainType().equals(BargainTypeEnum.RANDOM.getKey()) && "0".equals(dto.getSpecType())) {
            if (null == dto.getBargainMinAmount() || null == dto.getBargainMaxAmount()) {
                throw new WRWException("随机范围不可为空，请输入!");
            }
        } else if (dto.getBargainType().equals(BargainTypeEnum.FIXED.getKey()) && "0".equals(dto.getSpecType())) {
            if (null == dto.getBargainAmount()) {
                throw new WRWException("固定金额不可为空，请输入!");
            }
        }
        if (CollectionUtils.isEmpty(dto.getListImages())) {
            throw new WRWException("活动图片不可为空，请选择!");
        }
        
        // 不能在同一时间存在团购活动
        Date startDate = dto.getEffectiveStartDate();
        Date endDate = dto.getEffectiveEndDate();
        if (!kdTeamBuyProductDao.findSameTimeGroupBuy(dto.getProductId(), startDate, endDate).isEmpty()) {
            throw new WRWException("该商品在该活动时间段已经有团购活动！");
        }
        

        Date curDate = new Date();

        KdTwentyFourHoursEntity entity = new KdTwentyFourHoursEntity();
        if (dto.getId() != null) {
            entity = kdTwentyFourHoursDao.findOne(dto.getId());
        }
        // 商品id
        entity.setProductId(dto.getProductId());
        // 商品规格类型
        entity.setSpecType(dto.getSpecType());
        // 活动商品低价
        entity.setBasePrice(dto.getBasePrice());
        // 砍价方式 (砍价方式：0范围内随机，1固定金额)
        entity.setBargainType(dto.getBargainType());
        // 砍价金额设置
        if (dto.getBargainType().equals(BargainTypeEnum.RANDOM.getKey())) {
            entity.setBargainMinAmount(dto.getBargainMinAmount());
            entity.setBargainMaxAmount(dto.getBargainMaxAmount());
        } else {
            entity.setBargainAmount(dto.getBargainAmount());
        }
        // 活动有效开始时间
        entity.setEffectiveStartDate(dto.getEffectiveStartDate());
        // 活动有效结束时间
        entity.setEffectiveEndDate(dto.getEffectiveEndDate());
        // 活动名称
        entity.setName(dto.getName());
        // 活动说明图片
//        entity.setExplainInfo(dto.getExplainInfo());
        // 活动特别说明
        entity.setSpecialDesc(dto.getSpecialDesc());
        // 资助说明
        entity.setSupportDesc(dto.getSupportDesc());
        // 活动详情
        entity.setDescription(dto.getDescription());
        // 新增时赋初始值，由job更新状态值
        if(dto.getId() != null && StringUtil.isNotEmpty(dto.getStatus())){
            if(dto.getStatus().equals(KdBargainStatusEnum.PRODUCT_NOT_SALE.getKey())){
                entity.setStatus(KdBargainStatusEnum.ACT_NOSTART.getKey());
            }else {
                entity.setStatus(dto.getStatus());
            }
        }else{
            entity.setStatus(KdBargainStatusEnum.ACT_NOSTART.getKey());
        }
        entity.setDelFlag(DelFlagEnum.UN_DEL.getCode());
        entity.setCreateDate(curDate);
        entity.setCreateId(AuthorityContext.getCurrentUser().getUserId());
        KdTwentyFourHoursEntity kdTwentyFourHoursEntity = kdTwentyFourHoursDao.save(entity);

        // 活动商品保存操作（酷袋图片表，业务类型：KdPImageEnum）
        List<KdPImageDto> tfList = dto.getListImages();
        tfList.sort((KdPImageDto tf1, KdPImageDto tf2) -> tf1.getSort().compareTo(tf2.getSort()));
        kdPImageService.saveImage(kdTwentyFourHoursEntity.getId(), KdPImageEnum.BARGAIN_ACT.getCode(), dto.getListImages());
        
        //活动说明图片存储
        List<KdPImageDto> explainPics = new ArrayList<KdPImageDto>();
        explainPics.add(dto.getExplainInfoPics());
        kdPImageService.saveImage(kdTwentyFourHoursEntity.getId(), KdPImageEnum.BARGAIN_EXPLAIN.getCode(), explainPics);

        // 酷袋24小时活动商品底价详情表
        List<KdTFPriceDetailEntity> tfPriceDetailEntityList = new ArrayList<KdTFPriceDetailEntity>();
        if (WRWUtil.isNotEmpty(dto.getSpecType()) && "1".equals(dto.getSpecType())
                && !CollectionUtils.isEmpty(dto.getSpecInfoList())) {
            if (dto.getId() != null) {
                List<KdTFPriceDetailEntity> tfEntitys = kdTFPriceDetailDao.findByTfId(dto.getId());
                if (!CollectionUtils.isEmpty(tfEntitys)) {
                    kdTFPriceDetailDao.updateDelStatusByTfId(dto.getId(), DelFlagEnum.DEL.getCode());
                }
            }
            for (KdBargainProSpecPriceDto kdBargainProSpecPriceDto : dto.getSpecInfoList()) {
                KdTFPriceDetailEntity tfEntity = new KdTFPriceDetailEntity();
                tfEntity.setTfId(kdTwentyFourHoursEntity.getId());
                tfEntity.setSpecGroup(kdBargainProSpecPriceDto.getSpecInfo());
                tfEntity.setPrice(kdBargainProSpecPriceDto.getProductBasePrice());
                tfEntity.setBargainType(kdBargainProSpecPriceDto.getBargainType());
                tfEntity.setBargainAmount(kdBargainProSpecPriceDto.getBargainAmount());
                tfEntity.setBargainMinAmount(kdBargainProSpecPriceDto.getBargainMinAmount());
                tfEntity.setBargainMaxAmount(kdBargainProSpecPriceDto.getBargainMaxAmount());
                tfEntity.setIsDeleted(DelFlagEnum.UN_DEL.getCode());
                tfPriceDetailEntityList.add(tfEntity);
            }
        }
        kdTFPriceDetailDao.save(tfPriceDetailEntityList);
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public ResultDto<String> delete(String ids) {
        String[] idsp = ids.split(";");
        List<Long> pIds = new ArrayList<Long>();
        List<String> pShiefIds = new ArrayList<String>();
        List<Long> pStatusList = kdTwentyFourHoursDao.findByStatus();
        for (String id : idsp) {
            pIds.add(Long.valueOf(id));
            // 判断该活动是否可以删除
            if (!pStatusList.contains(Integer.valueOf(id))) {
                pShiefIds.add("活动ID:" + id);
                break;
            }
        }
        if (!CollectionUtils.isEmpty(pShiefIds)) {
            return ResultDtoFactory.toNack("操作失败，" + WRWUtil.listToString(pShiefIds) + "不可删除！");
        }
        Integer delCnt = kdTwentyFourHoursDao.deleteById(pIds, DelFlagEnum.DEL.getCode());
        if(delCnt != null && delCnt >0){
            // 删除24h活动时，将相关的广告位状态标识为删除
            kdAdvertiseService.updateAdvertiseStatus(ActTypeEnum.TWENTY_FOUR.getKey(), pIds);
            return ResultDtoFactory.toAck("删除成功!");
        }
        return ResultDtoFactory.toNack("删除失败！");
    }

    @Override
    public KdBargainSaveAndEditDto findById(Long id) {
        KdTwentyFourHoursEntity entity = kdTwentyFourHoursDao.findOne(id);
        KdBargainSaveAndEditDto dto = ConverterService.convert(entity, KdBargainSaveAndEditDto.class);
        dto.setStatus(entity.getStatus());
        List<KdPImageDto> listImages = kdPImageService.queryImage(entity.getId(), KdPImageEnum.BARGAIN_ACT.getCode());
        dto.setListImages(listImages);
        List<KdPImageDto> explainImages = kdPImageService.queryImage(entity.getId(), KdPImageEnum.BARGAIN_EXPLAIN.getCode());
        dto.setExplainInfoPics(CollectionUtils.isEmpty(explainImages) ? null : explainImages.get(0));
        KdProductDto kdProductDto = kdProductService.findById(dto.getProductId());
        if (kdProductDto != null) {
            dto.setpCode(kdProductDto.getPcode());
            dto.setpName(kdProductDto.getPname());
            dto.setPriceRange(kdProductDto.getPriceRange());
        }
        return dto;
    }

    @Override
    @SuppressWarnings("unchecked")
    public KdBargainAllSpecInfo findSpecInfo(Long tfId) {
        // 1.获取活动主表信息
        KdTwentyFourHoursEntity kdTwentyFourHoursEntity = kdTwentyFourHoursDao.findOne(tfId);
        // 1.1检验不为空并且是否为选择规格
        if (kdTwentyFourHoursEntity != null && kdTwentyFourHoursEntity.getSpecType().equals("1")) {
            // 2.获取商品规格信息map
            Map<String, List<String>> productSpecMap = kdProductSpecService
                    .getProductSpecMapById(kdTwentyFourHoursEntity.getProductId());

            if (!CollectionUtils.isEmpty(productSpecMap) && productSpecMap.size() > 0) {

                List<KdTFPriceDetailEntity> entitys = kdTFPriceDetailDao.findByTfId(tfId);

                // 3.返回对象
                KdBargainAllSpecInfo kdBargainAllSpecDto = new KdBargainAllSpecInfo();
                Set<String> mainSpecArry = new LinkedHashSet<String>();

                if (!CollectionUtils.isEmpty(entitys)) {

                    List<KdBargainSpecDto> subSpecDtos = new LinkedList<KdBargainSpecDto>();

                    Map<String, Long> productPriceMap = kdProductSpecService.findPriceMapDetail(kdTwentyFourHoursEntity
                            .getProductId());

                    for (KdTFPriceDetailEntity kdTFPriceDetailEntity : entitys) {

                        // 一条完整规格记录
                        JSONArray array = JSONArray.fromObject(kdTFPriceDetailEntity.getSpecGroup());

                        List<KdBargainSubSpecDetailDto> subPecDetailDtos = new ArrayList<KdBargainSubSpecDetailDto>();
                        KdBargainSpecDto kdBargainSpecDto = new KdBargainSpecDto();

                        StringBuffer tempSpecGroup = new StringBuffer("");
                        for (Iterator<JSONObject> iter = array.iterator(); iter.hasNext();) {
                            JSONObject object = (JSONObject) iter.next();
                            KdProductSpecDto tempDto = (KdProductSpecDto) JSONObject.toBean(object,
                                    KdProductSpecDto.class);
                            String curSubSpec = tempDto.getSubSpecs().get(0);
                            tempSpecGroup.append(curSubSpec).append("-");
                            // 主规格名称操作
                            if (!mainSpecArry.contains(tempDto.getMainSpec())) {
                                mainSpecArry.add(tempDto.getMainSpec());
                            }
                            KdBargainSubSpecDetailDto kdBargainSubSpecDetailDto = new KdBargainSubSpecDetailDto();
                            // 主规格名称
                            kdBargainSubSpecDetailDto.setMainSpec(tempDto.getMainSpec());
                            List<String> subSpecList = productSpecMap.get(tempDto.getMainSpec());
                            Map<String, Boolean> subSpecMap = new LinkedHashMap<String, Boolean>();
                            for (String tempString : subSpecList) {
                                Boolean result = tempString.equals(curSubSpec);
                                subSpecMap.put(tempString, result);
                            }
                            kdBargainSubSpecDetailDto.setSubSpecData(subSpecMap);
                            subPecDetailDtos.add(kdBargainSubSpecDetailDto);
                        }

                        String curSpecGroup = tempSpecGroup.toString().substring(0, tempSpecGroup.length() - 1);
                        kdBargainSpecDto.setSubPecDetailDtos(subPecDetailDtos);
                        kdBargainSpecDto.setProductPrice(productPriceMap.get(curSpecGroup));
                        kdBargainSpecDto.setProductBasePrice(kdTFPriceDetailEntity.getPrice());
                        kdBargainSpecDto.setSpecGroup(curSpecGroup);
                        kdBargainSpecDto.setSpecInfo(kdTFPriceDetailEntity.getSpecGroup());
                        kdBargainSpecDto.setBargainType(kdTFPriceDetailEntity.getBargainType());
                        kdBargainSpecDto.setBargainAmount(kdTFPriceDetailEntity.getBargainAmount());
                        kdBargainSpecDto.setBargainMinAmount(kdTFPriceDetailEntity.getBargainMinAmount());
                        kdBargainSpecDto.setBargainMaxAmount(kdTFPriceDetailEntity.getBargainMaxAmount());
                        subSpecDtos.add(kdBargainSpecDto);
                    }
                    kdBargainAllSpecDto.setMainSpecArry(mainSpecArry);
                    kdBargainAllSpecDto.setKdBargainSpecDtos(subSpecDtos);
                    kdBargainAllSpecDto.setProductId(kdTwentyFourHoursEntity.getProductId());
                    return kdBargainAllSpecDto;
                }
            }
        }
        return null;
    }

    @Override
    public TwentyfourInfo getTwentyfourInfo(Long twentyfourID, HttpServletRequest request, HttpServletResponse response) {
        // 参数校验
        if (twentyfourID == null) {
            throw new TwentyfourException("24小时活动信息参数错误");
        }

        // 24活动基本信息
        KdTwentyFourHoursEntity tfEntity = kdTwentyFourHoursDao.findOne(twentyfourID);
        if (tfEntity == null) {
            throw new TwentyfourException("24小时活动信息数据异常");
        }

        // 精简活动基本信息
        TwentyfourInfo info = new TwentyfourInfo();
        info.setName(tfEntity.getName());
        info.setShortDesc(tfEntity.getExplainInfo());
        info.setSpecialDesc(tfEntity.getSpecialDesc());
        info.setDetailDesc(tfEntity.getDescription());
        info.setActStatus(tfEntity.getStatus());
        info.setBeginDate(DateUtils.format(tfEntity.getEffectiveStartDate(), DateUtils.STRING_PATTERN_FULL));
        info.setEndDate(DateUtils.format(tfEntity.getEffectiveEndDate(), DateUtils.STRING_PATTERN_FULL));
        info.setProductId(tfEntity.getId());

        // 活动图片链接
        List<String> imgUrls = kdPImageDao.findByKeyIdAndType(twentyfourID, KdPImageEnum.BARGAIN_ACT.getCode());
        info.setImgUrls(imgUrls);
        // 活动说明图片
        List<String> explainImages = kdPImageDao.findByKeyIdAndType(twentyfourID, KdPImageEnum.BARGAIN_EXPLAIN.getCode());
        info.setExplainInfoPic(CollectionUtils.isEmpty(explainImages) ? null : explainImages.get(0));
        
        // 活动参加人数
        info.setParticipantsNum(tfEntity.getPlayerTotal() == null ? 0 : tfEntity.getPlayerTotal().intValue());

        // 活动商品原价及库存
        // 全规格活动
        // 活动关联商品信息
        KdProductEntity product = kdProductDao.findOne(tfEntity.getProductId());
        if (product == null) {
            throw new TwentyfourException("24小时活动相关商品信息数据异常");
        }

        info.setProductDesc(product.getQuickDesc());

        // 商品原价
        String lowPrice = BasicUtil.transFenToYuan(product.getLowPrice().longValue()).toString();
        String highPrice = BasicUtil.transFenToYuan(product.getHighPrice().longValue()).toString();

        // 最低价与最高价相同则取一值
        if (lowPrice.equals(highPrice)) {
            info.setProductPrice(lowPrice);
        } else {
            // 最低价与最高价不同则取范围值
            info.setProductPrice(lowPrice + "-" + highPrice);
        }

        // 商品库存
        KdProductStockEntity stock = kdProductStockDao.findByProductId(product.getId());
        info.setRemainingAmount(stock.getRestAmount());

        // 选择规格进行活动
        /*
         * if (SpecTypeEnums.SUB_SPEC.getCode().equals(tfEntity.getSpecType())) { // TODO 感觉没有什么信息需要的 }
         */

        return info;
    }

    @Override
    public Boolean isTfBuyProduct(Long productId) {
        return !kdTwentyFourHoursDao.findNormalTfByproductId(productId).isEmpty();
    }

    @Override
    public Integer getTfByProductIdAndTime(Long productId, String onTime, String offTime, Long tfId) {
        if (tfId == null) {
            return kdTwentyFourHoursDao.getTfByProductIdAndTime(productId, onTime, offTime);
        } else {
            return kdTwentyFourHoursDao.getTfByProductIdAndTimeAndTfId(productId, onTime, offTime, tfId);
        }

    }

    @Override
    @Transactional
    public void saleOffUpdateStatus(Long productId) {
        kdTwentyFourHoursDao.updateStatus(productId, KdBargainStatusEnum.PRODUCT_NOT_SALE.getKey(),
                KdBargainStatusEnum.ACT_NOSTART.getKey());
    }

}
