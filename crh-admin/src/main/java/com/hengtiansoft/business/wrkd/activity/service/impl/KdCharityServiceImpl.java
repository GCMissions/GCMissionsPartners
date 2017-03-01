/*
 * Project Name: wrw-admin
 * File Name: KdCharityServiceImpl.java
 * Class Name: KdCharityServiceImpl
 *
 * Copyright 2014 Hengtian Software Inc
 *
 * Licensed under the Hengtiansoft
 *
 * http://www.hengtiansoft.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.wrkd.activity.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.business.wrkd.activity.dto.KdActivityDetailRequestDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdCharityDetailResponseDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdCharityDto;
import com.hengtiansoft.business.wrkd.activity.dto.KdCharitySearchDto;
import com.hengtiansoft.business.wrkd.activity.service.KdCharityService;
import com.hengtiansoft.business.wrkd.advertise.service.KdAdvertiseService;
import com.hengtiansoft.business.wrkd.image.dto.KdPImageDto;
import com.hengtiansoft.business.wrkd.image.service.KdPImageService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.CoolbagFeatureDao;
import com.hengtiansoft.wrw.dao.KdCharityDao;
import com.hengtiansoft.wrw.dao.KdCharityRecordDao;
import com.hengtiansoft.wrw.dao.KdPImageDao;
import com.hengtiansoft.wrw.entity.CoolbagFeatureEntity;
import com.hengtiansoft.wrw.entity.KdCharityEntity;
import com.hengtiansoft.wrw.entity.KdPImageEntity;
import com.hengtiansoft.wrw.enums.ActTypeEnum;
import com.hengtiansoft.wrw.enums.KdCharityStatusEnum;
import com.hengtiansoft.wrw.enums.KdPImageEnum;
import com.hengtiansoft.wrw.enums.StatusEnum;

/**
 * Class Name: KdCharityServiceImpl
 * Description: 酷袋公益活动
 * @author zhongyidong
 *
 */
@Service
public class KdCharityServiceImpl implements KdCharityService {
    
    // 活动正常标志
    private static final String NORMAL = "0";
    // 活动删除标志
    private static final String DELETED = "1";
    // 活动名称中特殊字符
    private static final char ESCAPE = '\\';
    
    @Autowired
    private KdCharityDao kdCharityDao;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private KdPImageService kdPImageService;
    @Autowired
    private KdPImageDao kdPImageDao;
    @Autowired
    private KdCharityRecordDao recordDao;
    @Autowired
    private CoolbagFeatureDao coolbagFeatureDao;
    @Autowired
    private KdAdvertiseService kdAdvertiseService;
    
    private static final String  SHEIF_STATUS = "0";
    
    @Override
    public KdCharityDto findCharity(Long charityId) {
        KdCharityEntity kdCharityEntity = kdCharityDao.findOne(charityId);
        if (null != kdCharityEntity) {
            KdCharityDto charityDto = ConverterService.convert(kdCharityEntity, KdCharityDto.class);
            if (null != kdCharityEntity.getStartTime()) {
                charityDto.setStartTime(DateTimeUtil.parseDateToString(kdCharityEntity.getStartTime(), DateTimeUtil.SIMPLE_FMT_MINUTE));
            }
            if (null != kdCharityEntity.getEndTime()) {
                charityDto.setEndTime(DateTimeUtil.parseDateToString(kdCharityEntity.getEndTime(), DateTimeUtil.SIMPLE_FMT_MINUTE));
            }
            charityDto.setListImages(kdPImageService.queryImage(charityDto.getId(), KdPImageEnum.CHARITY.getCode()));
            
            // 公益活动说明换行符缓存\r\n
            String notes = kdCharityEntity.getExplainNote().replace("<br />", "\r\n");
            charityDto.setExplainNote(notes);
            
            return charityDto;
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void searchCharity(KdCharitySearchDto searchDto) {
        StringBuilder countBuilder = new StringBuilder("");
        countBuilder.append("SELECT count(*) FROM (");
        StringBuilder searchBuilder = new StringBuilder();
        searchBuilder.append("SELECT kc.id, kc.name, kc.status, kc.start_time, kc.end_time, kc.create_date, kcr.involveNum, kc.FEATURE_ID FROM kd_charity as kc "
                + "left join(select charity_id, count(charity_id) as involveNum from kd_charity_record group by charity_id) kcr "
                + "on kc.id = kcr.charity_id "
                + "WHERE 1=1 AND kc.is_deleted ='0' ");
        
        Map<String, Object> param = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(searchDto.getName())) {
            String charityName = searchDto.getName();
            charityName = charityName.replace("\\", ESCAPE + "\\");
            charityName = charityName.replace("%", ESCAPE + "%");
            charityName = charityName.replace("_", ESCAPE + "_");
            searchBuilder.append(" AND kc.NAME LIKE :NAME escape '\\\\' ");
            param.put("NAME", "%" + charityName + "%");
        }
        
        if (StringUtils.isNotBlank(searchDto.getStartDate())) {
            searchBuilder.append(" AND kc.CREATE_DATE >= :START_TIME ");
            param.put("START_TIME", DateTimeUtil.getDateBegin(searchDto.getStartDate()));
        }
        
        if (StringUtils.isNotBlank(searchDto.getEndDate())) {
            searchBuilder.append(" AND kc.CREATE_DATE <= :END_TIME ");
            param.put("END_TIME", DateTimeUtil.getDateEnd(searchDto.getEndDate()));
        }
        
        if (StringUtils.isNotBlank(searchDto.getStatus())) {
            searchBuilder.append(" AND kc.STATUS = :STATUS ");
            param.put("STATUS", searchDto.getStatus());
        }
        
        //　查询结果总数
        countBuilder.append(searchBuilder).append(" ) A");
        Query coutQuery = entityManager.createNativeQuery(countBuilder.toString());
        QueryUtil.processParamForQuery(coutQuery, param);
        int totalRecord = ((BigInteger)coutQuery.getSingleResult()).intValue();
        searchDto.setTotalRecord((long)totalRecord);

        searchBuilder.append(" ORDER BY kc.id DESC");
        Query query = entityManager.createNativeQuery(searchBuilder.toString());
        QueryUtil.processParamForQuery(query, param);
        query.setFirstResult(searchDto.getPageSize() * (searchDto.getCurrentPage() - 1));
        query.setMaxResults(searchDto.getPageSize());
        int pages = totalRecord / searchDto.getPageSize();
        searchDto.setTotalPages(totalRecord % searchDto.getPageSize() == 0 ? pages : pages + 1);
        
        // 专享名称
        List<KdCharityDto> charityList = buildCharityDtos(query.getResultList());
        List<KdCharityDto> resultList = new ArrayList<KdCharityDto>();
        if(charityList != null && !charityList.isEmpty()){
            for(KdCharityDto dto : charityList){
                if(dto.getFeatureId() != null){
                    //封装 专享名称
                    CoolbagFeatureEntity featureEntity = coolbagFeatureDao.findOne(dto.getFeatureId());
                    if(featureEntity!=null){
                        dto.setFeatureName(featureEntity.getName());
                    }
                }
                resultList.add(dto);
            }
        }
        
        searchDto.setList(resultList);
    }

    @Override
    @Transactional
    public ResultDto<Long> saveCharity(KdCharityDto charityDto) {
        if (StringUtils.isBlank(charityDto.getStartTime()) || StringUtils.isBlank(charityDto.getEndTime())) {
            return ResultDtoFactory.toNack("活动开始时间或结束时间为空！");
        }
        if (null == charityDto.getFeatureId()) {
            return ResultDtoFactory.toNack("活动关联的专辑为空！");
        }
        List<KdPImageDto> images = charityDto.getListImages();
        if (CollectionUtils.isEmpty(images) || StringUtils.isBlank(charityDto.getImgUrl())) {
            return ResultDtoFactory.toNack("活动图片为空！");
        }
        if(images.size()<3 || images.size()>5){
            return ResultDtoFactory.toNack("活动图片数量有误,请至少上传3张，最多5张!");
        }
        int count = kdCharityDao.findByName(charityDto.getName(), NORMAL);
        if (null == charityDto.getId() && 0 < count) {
            return ResultDtoFactory.toNack("已存在相同名称的公益活动！");
        }
        Date startTime = DateTimeUtil.parseDate(charityDto.getStartTime(), DateTimeUtil.SIMPLE_FMT_MINUTE);
        Date endTime = DateTimeUtil.parseDate(charityDto.getEndTime(), DateTimeUtil.SIMPLE_FMT_MINUTE);
        if (startTime.after(endTime)) {
            return ResultDtoFactory.toNack("活动开始时间应该在结束时间之前！");
        }
        Date nowDate = new Date();
        if(nowDate.after(endTime)){
            return ResultDtoFactory.toNack("活动结束时间应该在当前时间之后!");
        }
        //新增公益
        if(null == charityDto.getId()){
            count = kdCharityDao.checkDate(startTime, endTime, charityDto.getFeatureId(), KdCharityStatusEnum.INVALID.getKey(), NORMAL);
            if (0 < count) {
                return ResultDtoFactory.toNack("同一个专辑在一个时间段内只能绑定一个活动！");
            }
        }else{
        //编辑公益
            KdCharityEntity c = kdCharityDao.findOne(charityDto.getId());
            //相同的专辑ID，说明没有修改 ，不同的话，则去验证一下
            if(charityDto.getFeatureId().longValue() != c.getFeatureId().longValue()){
                count = kdCharityDao.checkDate(startTime, endTime, charityDto.getFeatureId(), KdCharityStatusEnum.INVALID.getKey(), NORMAL);
                if (0 < count) {
                    return ResultDtoFactory.toNack("同一个专辑在一个时间段内只能绑定一个活动！");
                }
            }
        }
        //判断该专辑是否下架
        CoolbagFeatureEntity featureEntity = coolbagFeatureDao.findOne(charityDto.getFeatureId());
        if(SHEIF_STATUS.equals(featureEntity.getSheifStatus())){
            return ResultDtoFactory.toNack("该专辑已经下架，请重新选择专辑!");
        }
        String status = KdCharityStatusEnum.NOTSTART.getKey();
        if (startTime.before(new Date())){
            status = KdCharityStatusEnum.ONGOING.getKey();
        }
        if (endTime.before(new Date())) {
            status = KdCharityStatusEnum.FINISHED.getKey();
        }
        
        // 获取当前用户id
        Long userId = AuthorityContext.getCurrentUser().getUserId();
        KdCharityEntity charityEntity = null;
        if (null == charityDto.getId()) {
            charityEntity = ConverterService.convert(charityDto, KdCharityEntity.class);
            charityEntity.setStartTime(startTime);
            charityEntity.setEndTime(endTime);
            charityEntity.setStatus(status);
            charityEntity.setIsDeleted(NORMAL);
            charityEntity.setCreateId(userId);
            charityEntity.setCreateDate(new Date());
            charityEntity.setDetailDesc(charityDto.getDetailDesc());
            charityEntity = kdCharityDao.save(charityEntity);
        } else {
            charityEntity = kdCharityDao.findOne(charityDto.getId());
            if (!KdCharityStatusEnum.NOTSTART.getKey().equals(charityEntity.getStatus())) {
                if(KdCharityStatusEnum.OFFSALE.getKey().equals(charityEntity.getStatus())){
                    charityEntity.setStatus(status);
                }else{
                    return ResultDtoFactory.toNack("活动不是未开始状态，不可编辑！");
                }
            }
            charityEntity.setName(charityDto.getName());
            charityEntity.setStartTime(startTime);
            charityEntity.setEndTime(endTime);
            charityEntity.setFeatureId(charityDto.getFeatureId());
            charityEntity.setImgUrl(charityDto.getImgUrl());
            charityEntity.setExplainNote(charityDto.getExplainNote());
            charityEntity.setModifyId(userId);
            charityEntity.setModifyDate(new Date());
            charityEntity.setDetailDesc(charityDto.getDetailDesc());
        }
        
        if (null != charityEntity) {
            //　保存商品图片
            kdPImageService.saveImage(charityEntity.getId(), KdPImageEnum.CHARITY.getCode(), images);
        }
        return ResultDtoFactory.toAck("保存成功", charityEntity.getId());
    }
    
    @Override
    @Transactional
    public ResultDto<String> saveDetail(Long charityId, String detail) {
        KdCharityEntity charity = kdCharityDao.findOne(charityId);
        if (!KdCharityStatusEnum.NOTSTART.getKey().equals(charity.getStatus())) {
            return ResultDtoFactory.toNack("活动不是未开始状态，不可编辑！");
        }
        int row = kdCharityDao.saveDetail(charityId, detail);
        if (0 == row) {
            return ResultDtoFactory.toNack("保存失败");
        }
        return ResultDtoFactory.toAck("保存成功");
    }
    
    @Override
    @Transactional
    public int saveFeedback(Long charityId, String feedback) {
        return kdCharityDao.saveFeedback(charityId, feedback);
    }

    @Override
    @Transactional
    public ResultDto<String> deleteCharity(List<Long> charityIds) {
        if (CollectionUtils.isNotEmpty(charityIds)) {
            int count = kdCharityDao.countByStatus(charityIds, KdCharityStatusEnum.ONGOING.getKey(), NORMAL);
            if (0 < count) {
                return ResultDtoFactory.toNack("删除失败，存在进行中的活动！");
            }
            int row = kdCharityDao.deleteCharity(charityIds, DELETED);
            if (0 < row) {
                // 删除公益活动时，将相关的广告位状态标识为删除
                kdAdvertiseService.updateAdvertiseStatus(ActTypeEnum.CHARITY.getKey(), charityIds);
                return ResultDtoFactory.toAck("删除成功！");
            }
        }
        
        return ResultDtoFactory.toNack("删除失败！");
    }
    
    @Override
    public boolean checkCharityByFeature(Long featureId) {
        int count = kdCharityDao.countOngoingByFeature(featureId, KdCharityStatusEnum.ONGOING.getKey(), NORMAL);
        return count > 0;
    }

    @Override
    @Transactional
    public boolean updateCharityStatus(Long featureId, String status, Long userId) {
        int row = kdCharityDao.updateStatus(status, userId, featureId);
        return row > 0;
    }
    
    /**
     * Description: 将entity转为dto
     *
     * @param results
     * @param searchDto
     * @return
     */
    private List<KdCharityDto> buildCharityDtos(List<Object[]> results) {
        List<KdCharityDto> charityDtos = new ArrayList<KdCharityDto>(results.size());
        KdCharityDto dto = null;
        for (Object[] array : results) {
            dto = new KdCharityDto();
            dto.setId(WRWUtil.objToLong(array[0]));
            dto.setName(WRWUtil.objToString(array[1]));
            dto.setStatus(WRWUtil.objToString(array[2]));
            if (null != array[3]) {
                dto.setStartTime(DateTimeUtil.parseDateToString((Date)array[3], DateTimeUtil.SIMPLE_FMT_MINUTE)); 
            }
            if (null != array[4]) {
                dto.setEndTime(DateTimeUtil.parseDateToString((Date)array[4], DateTimeUtil.SIMPLE_FMT_MINUTE));
            }
            if (null != array[5]) {
                dto.setCreateDate(DateTimeUtil.parseDate(array[5].toString(), DateTimeUtil.SIMPLE_FMT));
            }
            dto.setInvolveNum(WRWUtil.objToInteger(array[6]));
            if(array[7] != null){
                dto.setFeatureId(Long.parseLong(array[7].toString()));
            }
            
            charityDtos.add(dto);
        }
        return charityDtos;
    }
    
    @Override
    public ResultDto<KdCharityDetailResponseDto> getDetail(KdActivityDetailRequestDto requestDto) {
        Long actId = requestDto.getActId();
        if (actId == null) {
            return ResultDtoFactory.toNack("传值错误，活动ID不能为空");
        }
        KdCharityEntity cEntity = kdCharityDao.findOne(actId);
        if (cEntity == null) {
            return ResultDtoFactory.toNack("查询不到当前活动信息!");
        }
        KdCharityDetailResponseDto responseDto = new KdCharityDetailResponseDto();
        responseDto = ConverterService.convert(cEntity, KdCharityDetailResponseDto.class);
        responseDto.setActId(cEntity.getId());
        responseDto.setActName(cEntity.getName());

        // 封装 商品图片集合
        List<KdPImageEntity> imageList = kdPImageDao.findByKeyIdAndTypeAndIsDeleted(actId,
                KdPImageEnum.CHARITY.getCode(), "0");
        List<String> productImageList = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(imageList)) {
            for (KdPImageEntity imageEntity : imageList) {
                productImageList.add(imageEntity.getImageUrl());
            }
        }
        responseDto.setImageList(productImageList);
        // 封装 商品参与人数
        int actNum = recordDao.findCountByStutas(actId, StatusEnum.NORMAL.getCode());
        responseDto.setActNum(actNum);
        //封装 专享名称
        CoolbagFeatureEntity featureEntity = coolbagFeatureDao.findOne(cEntity.getFeatureId());
        if(featureEntity!=null){
            responseDto.setFeatureName(featureEntity.getName());
        }
        return ResultDtoFactory.toAck("", responseDto);
    }
    
}
