/*
 * Project Name: wrw-admin
 * File Name: ActivityStockServiceImpl.java
 * Class Name: ActivityStockServiceImpl
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
package com.hengtiansoft.business.activity.service.impl;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.activity.controller.ActivityStockController;
import com.hengtiansoft.business.activity.dto.ActivityConstranintDto;
import com.hengtiansoft.business.activity.dto.ActivityDateDto;
import com.hengtiansoft.business.activity.dto.ActivitySpecDto;
import com.hengtiansoft.business.activity.dto.ActivityStockDto;
import com.hengtiansoft.business.activity.service.ActivityConstranintService;
import com.hengtiansoft.business.activity.service.ActivitySpecService;
import com.hengtiansoft.business.activity.service.ActivityStockService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.wrw.ActivityConst.StockType;
import com.hengtiansoft.wrw.dao.ActivityStockDao;
import com.hengtiansoft.wrw.dao.SBasicParaDao;
import com.hengtiansoft.wrw.entity.ActivitySpec;
import com.hengtiansoft.wrw.entity.ActivityStock;

/**
 * Class Name: ActivityStockServiceImpl
 * Description: 活动库存
 * @author zhongyidong
 *
 */
@Service
public class ActivityStockServiceImpl implements ActivityStockService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityStockServiceImpl.class);

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final Integer MAIN_SPEC_TYPE = 2;
    private static final Integer REQ_FIELDS_TYPE = 3;
    
    @Autowired
    private SBasicParaDao basicParaDao;
    @Autowired
    private ActivityStockDao activityStockDao;
    @Autowired
    private ActivitySpecService activitySpecService;
    @Autowired
    private ActivityConstranintService actConstranintService;

    @Override
    public List<ActivityStockDto> findAllByActId(Long productId) {
        List<ActivityStockDto> actStockDtoList = new ArrayList<ActivityStockDto>();
        List<ActivityStock> actStockList = activityStockDao.findByActId(productId);
        if (CollectionUtils.isNotEmpty(actStockList)) {
            for (ActivityStock actStock : actStockList) {
                if (null != actStock) {
                    List<ActivitySpecDto> actSpecDtoList = new ArrayList<ActivitySpecDto>();
                    List<ActivitySpec> actSpecList = activitySpecService.findByStockId(actStock.getId());
                    for (ActivitySpec actSpec : actSpecList) {
                        ActivitySpecDto actSpecDto = ConverterService.convert(actSpec, ActivitySpecDto.class);
                        actSpecDto.setPriceDescJson(JSONObject.fromObject(actSpecDto.getPriceDesc()));
                        actSpecDtoList.add(actSpecDto);
                    }
                    
                    ActivityStockDto actStockDto = ConverterService.convert(actStock, ActivityStockDto.class);                    
                    actStockDto.setActSpecList(actSpecDtoList);
                    actStockDtoList.add(actStockDto);
                }
            }
        }
        return actStockDtoList;
    }
    
    @Override
    public ActivityStockDto findByActId(Long productId, String actDate) {
        ActivityStock actStock = null;
        if (null == actDate) {
            // 查询最近一次的库存记录
            actStock = activityStockDao.findOneByActId(productId);
        } else {
            // 查询活动日期对应的库存记录
            actStock = activityStockDao.findOneByActIdAndActDate(productId, actDate);
        }
        if (null != actStock) {
            ActivityStockDto actStockDto = ConverterService.convert(actStock, ActivityStockDto.class);
            List<ActivitySpecDto> actSpecDtoList = new ArrayList<ActivitySpecDto>();
            List<ActivitySpec> actSpecList = activitySpecService.findByStockId(actStock.getId());
            for (ActivitySpec actSpec : actSpecList) {
                ActivitySpecDto actSpecDto = ConverterService.convert(actSpec, ActivitySpecDto.class);
                actSpecDto.setPriceDescJson(JSONObject.fromObject(actSpecDto.getPriceDesc()));
                actSpecDtoList.add(actSpecDto);
            }
            actStockDto.setActSpecList(actSpecDtoList);
            actStockDto.setActConstranintDto(actConstranintService.findByActId(productId));
            
            return actStockDto;
        }
        return null;
    }

    @Override
    @Transactional
    public ResultDto<Boolean> updateActStock(ActivityStockDto actStockDto, String flag) {
        List<Integer> priceList = actStockDto.getPriceList();
        List<Integer> vipPriceList = actStockDto.getVipPriceList();
        List<ActivityDateDto> actDateList = actStockDto.getActDateList();
        List<ActivitySpecDto> actSpecDtoList = actStockDto.getActSpecList();
        if (CollectionUtils.isEmpty(actDateList) || CollectionUtils.isEmpty(actSpecDtoList)) {
            return ResultDtoFactory.toNack("活动时间或者规格为空！");
        }
        if (StringUtils.isBlank(actStockDto.getStockType())) {
            return ResultDtoFactory.toNack("库存类型不能为空 ！");
        }
        
        if (CollectionUtils.isEmpty(priceList) || CollectionUtils.isEmpty(vipPriceList)) {
            return ResultDtoFactory.toNack("价格为空！");
        }
        // 获取价格区间
        actStockDto.setLowPrice(Collections.min(priceList));
        actStockDto.setHighPrice(Collections.max(priceList));
        // 移出0的会员价
        List<Integer> removeVipPrice = new ArrayList<Integer>();
        for (Integer vipPrice : vipPriceList) {
            if (vipPrice == 0) {
                removeVipPrice.add(vipPrice);
            }
        }
        vipPriceList.removeAll(removeVipPrice);
        if (vipPriceList.isEmpty()) {
            actStockDto.setLowVipPrice(0);
            actStockDto.setHighVipPrice(0);
        } else {
            actStockDto.setLowVipPrice(Collections.min(vipPriceList));
            actStockDto.setHighVipPrice(Collections.max(vipPriceList));
        }
       
        // 获取当前用户id
        Long userId = AuthorityContext.getCurrentUser().getUserId();
        List<ActivityStock> actStockList = new  ArrayList<ActivityStock>();
        List<String> dateList = buildActStockList(actStockList, userId, actStockDto);
        
        // 在添加商品页面保存之后，再次编辑删除活动日期时调用此方法
        if (ActivityStockController.ADD.equals(flag)) {
            deleteActStock(actStockDto.getProductId(), dateList, ActivityStockController.DELETE_NOTIN);
        }
        
        actStockList = activityStockDao.save(actStockList);
        if (null != actStockList && !actStockList.isEmpty()) {
            for (ActivityStock activityStock : actStockList) {
                // 子规格列表（第一个主规格的子规格）
                List<String> subSpecList = new ArrayList<String>();
                List<ActivitySpec> actSpecList = activitySpecService.saveStock(actStockDto, subSpecList, activityStock, userId); 
                if (null == actSpecList || actSpecList.isEmpty()) {
                    throw new WRWException("保存活动规格信息失败!");
                }
                // 删除操作（子规格被删除） 活动规格记录
                activitySpecService.deleteByStockIdAndSpec(activityStock.getId(), subSpecList);
            }
        } else {
            return ResultDtoFactory.toNack("保存失败 ！");
        }
        
        return ResultDtoFactory.toAck("保存成功", true);
    }

    @Override
    @Transactional
    public int updateShowStock(Long productId, String showStock) {
        return activityStockDao.updateIsShowStock(productId, showStock);
    }
    
    @Override
    @Transactional
    public Integer deleteActStock(Long productId, List<String> actDateList, String flag) {
        if (null == actDateList || actDateList.isEmpty()) {
            return null;
        }
        
        List<BigInteger> actStockIdList = null;
        if (ActivityStockController.DELETE_IN.equals(flag)) {
            actStockIdList = activityStockDao.queryInActStock(productId, actDateList);;
        } else {
            actStockIdList = activityStockDao.queryNotActStock(productId, actDateList);;
        }
        if (CollectionUtils.isNotEmpty(actStockIdList)) {
            int row = activityStockDao.deleteActStock(actStockIdList);
            if (row > 0) {
                row = activitySpecService.deleteByStockId(actStockIdList);
                if (row == 0) {
                    throw new WRWException("删除活动规格记录失败！");
                }
            } else {
                throw new WRWException("删除活动库存失败！");
            }
            return row;
        }
        return null;
    }

    @Override
    public boolean checkActDate(Long productId, String dateStart, String dateEnd) {
        ActivityConstranintDto buyInfo = actConstranintService.findByActId(productId);
        
        int count = 0;
        // 检查是否已经添加过该活动日期
        if (StringUtils.isBlank(dateEnd)) {
            count = activityStockDao.countRowByActDate(productId, dateStart);
        } else {
            count = activityStockDao.countRowByActDate(productId, dateStart, dateEnd);
        }
        if (count > 0) {
            return false;
        } 
        
        // 检查是否在开售时间内
        if (null != buyInfo) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                Date startDate = dateFormat.parse(dateStart);
                if (buyInfo.getStartTime() != null && startDate.before(buyInfo.getStartTime())) {
                    return false;
                }
                if (buyInfo.getEndTime() != null && startDate.after(buyInfo.getEndTime())) {
                    return false;
                }
                if (StringUtils.isNotBlank(dateEnd)) {
                    Date endDate = dateFormat.parse(dateEnd);
                    if (buyInfo.getEndTime() != null && endDate.after(buyInfo.getEndTime())) {
                        return false;
                    }
                }
            } catch (ParseException e) {
                LOGGER.error("时间格式错误，{}", e);
            }
        }
        
        return true;
    }

    @Override
    public boolean checkSaleDate(Long productId, String dateStart, String dateEnd) {
        int count = 0;
        if (StringUtils.isBlank(dateEnd)) {
            count = activityStockDao.countRowBySaleType(productId, dateStart);
        } else {
            count = activityStockDao.countRowBySaleType(productId, dateStart, dateEnd);
        }
        if (count > 0) {
            return false;
        }
        
        return true;
    }

    @Override
    public List<String> queryMainSpecs() {
        return basicParaDao.queryActAbout(MAIN_SPEC_TYPE);
    }

    @Override
    public List<String> queryRequiredFields() {
        return basicParaDao.queryActAbout(REQ_FIELDS_TYPE);
    }

    /**
     * Description: 处理库存信息
     *
     * @param actStockList
     * @param userId
     * @param actStockDto
     * @return
     */
    private List<String> buildActStockList(List<ActivityStock> actStockList, Long userId, ActivityStockDto actStockDto) {
        // 日期列表
        List<String> dateList = new ArrayList<String>();
        Set<String> dateSet = new HashSet<String>();
        for (Iterator<ActivityDateDto> iter = actStockDto.getActDateList().iterator(); iter.hasNext();){
            ActivityDateDto actDateDto = iter.next();
            // 检查时间是否重复
            if (dateSet.add(actDateDto.getActDate())) {
                dateList.add(actDateDto.getActDate());
                ActivityStock activityStock = ConverterService.convert(actStockDto, ActivityStock.class);
                activityStock.setActDate(DateTimeUtil.parseDate(actDateDto.getActDate(), DateTimeUtil.SIMPLE_YMD));
                if (StockType.NONEED_STOCK.equals(actStockDto.getStockType())) {
                    activityStock.setTotalCount(0);
                    activityStock.setOriginalCount(0);
                } else {
                    activityStock.setTotalCount(actDateDto.getCount());
                    activityStock.setOriginalCount(actDateDto.getCount());
                }
                
                ActivityStock actStock = activityStockDao.findIdByActId(actStockDto.getProductId(), actDateDto.getActDate());
                if (null != actStock) {
                    // 更新操作
                    activityStock.setId(actStock.getId());
                    activityStock.setModifyId(userId);
                    activityStock.setModifyDate(new Date());
                } else {
                    activityStock.setCreateId(userId);
                }
                
                actStockList.add(activityStock);
            }
        }
        return dateList;
    }
}
