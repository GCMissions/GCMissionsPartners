/*
 * Project Name: wrw-admin
 * File Name: ActivityConstranintServiceImpl.java
 * Class Name: ActivityConstranintServiceImpl
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.activity.dto.ActivityConstranintDto;
import com.hengtiansoft.business.activity.dto.ActivityPartakeDto;
import com.hengtiansoft.business.activity.service.ActivityConstranintService;
import com.hengtiansoft.business.activity.service.ActivitySpecService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.wrw.ActivityConst.SaleType;
import com.hengtiansoft.wrw.dao.ActivityConstranintDao;
import com.hengtiansoft.wrw.entity.ActivityConstranint;
import com.hengtiansoft.wrw.entity.ActivitySpec;

/**
 * Class Name: ActivityConstranintServiceImpl
 * Description: 活动约束（购买信息）
 * @author zhongyidong
 *
 */
@Service
public class ActivityConstranintServiceImpl implements ActivityConstranintService {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Autowired
    private ActivitySpecService activitySpecService;
    @Autowired
    private ActivityConstranintDao activityConstranintDao;
    
    @Override
    public ActivityConstranintDto findByActId(Long productId) {
        ActivityConstranint actConstranint = activityConstranintDao.findByProductId(productId);
        if (null == actConstranint) {
            return null;
        }
        ActivityConstranintDto actConstranintDto = ConverterService.convert(actConstranint, ActivityConstranintDto.class);
        if (SaleType.ONTIME_SALE.equals(actConstranint.getSaleType())) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            if (null != actConstranint.getStartTime()) {
                actConstranintDto.setStartDate(dateFormat.format(actConstranint.getStartTime()));
            }
            if (null != actConstranint.getEndTime()) {
                actConstranintDto.setEndDate(dateFormat.format(actConstranint.getEndTime()));
            }
        }
        return actConstranintDto;
    }

    @Override
    @Transactional
    public ActivityConstranint addContranint(ActivityConstranintDto actConstranintDto) {

        ActivityConstranint actConstranint = ConverterService.convert(actConstranintDto, ActivityConstranint.class);
        if (SaleType.RIGHT_SALE.equals(actConstranintDto.getSaleType())) {
            actConstranint.setStartTime(new Date());
        } else {
            if (StringUtils.isBlank(actConstranintDto.getStartDate())) {
                return null;
            } else {
                actConstranint.setStartTime(DateTimeUtil.parseDate(actConstranintDto.getStartDate(), DATE_FORMAT));
            }
            if (StringUtils.isNotBlank(actConstranintDto.getEndDate())) {
                actConstranint.setEndTime(DateTimeUtil.parseDate(actConstranintDto.getEndDate(), DATE_FORMAT));
            }
        }
        // 记录创建人
        actConstranint.setCreateId(AuthorityContext.getCurrentUser().getUserId());
        return activityConstranintDao.saveAndFlush(actConstranint);
    }

    @Override
    @Transactional
    public ActivityConstranint updateContranint(ActivityConstranintDto actConstranintDto) {
        ActivityConstranint actConstranint = ConverterService.convert(actConstranintDto, ActivityConstranint.class);
        ActivityConstranint activityConstranint = activityConstranintDao.findByProductId(actConstranintDto.getProductId());
        if (SaleType.ONTIME_SALE.equals(actConstranintDto.getSaleType())) {
            if (StringUtils.isNotBlank(actConstranintDto.getStartDate())) {
                actConstranint.setStartTime(DateTimeUtil.parseDate(actConstranintDto.getStartDate(), DATE_FORMAT));
            }
            if (StringUtils.isNotBlank(actConstranintDto.getEndDate())) {
                actConstranint.setEndTime(DateTimeUtil.parseDate(actConstranintDto.getEndDate(), DATE_FORMAT));
            }
        } else {
            if (null != activityConstranint && SaleType.RIGHT_SALE.equals(activityConstranint.getSaleType())) {
                // 赋原值
                if (null != activityConstranint.getStartTime()) {
                    actConstranint.setStartTime(activityConstranint.getStartTime());
                }
            } 
            
            if (null == actConstranint.getStartTime()) {
                // 去除时分秒
                String sDate = DateTimeUtil.parseDateToString(new Date(), DATE_FORMAT);
                actConstranint.setStartTime(DateTimeUtil.parseDate(sDate, DATE_FORMAT));
            }
        }
        
        Long userId = AuthorityContext.getCurrentUser().getUserId();
        if (null != activityConstranint) {
            // 记录修改人
            actConstranint.setModifyId(userId);
            // 记录修改时间
            actConstranint.setModifyDate(new Date());
            // 更新操作
            actConstranint.setId(activityConstranint.getId());
        } else {
            // 记录创建人
            actConstranint.setCreateId(userId);
        }
        
        return activityConstranintDao.saveAndFlush(actConstranint);
    }

    @Override
    public List<ActivityPartakeDto> findPartakeInfo(Long productId) {
        List<ActivitySpec> actSpecs = activitySpecService.findFirstSubspecs(productId);
        if (!actSpecs.isEmpty()) {
            Map<String, JSONObject> actPartakeMap = new HashMap<String, JSONObject>();
            ActivityConstranint actConstranint = activityConstranintDao.findByProductId(productId);
            if (null != actConstranint && StringUtils.isNotBlank(actConstranint.getPartakeInfo())) {
                JSONArray array = JSONArray.fromObject(actConstranint.getPartakeInfo());
                for (int i = 0; i < array.size(); i++) {
                    JSONObject json = array.getJSONObject(i);
                    actPartakeMap.put(json.getString("subSpec"), json);
                }
            }
            ActivityPartakeDto actPartake = null;
            List<ActivityPartakeDto> actPartakes = new ArrayList<ActivityPartakeDto>(actSpecs.size());
            for (ActivitySpec actSpec : actSpecs) {
                actPartake = ConverterService.convert(actSpec, ActivityPartakeDto.class);
                JSONObject json = actPartakeMap.get(actSpec.getSubSpec());
                if (null != json) {
                    actPartake.setUnitNum(json.getInt("unitNum"));
                    actPartake.setEnabled(json.getBoolean("enabled"));
                }
                actPartakes.add(actPartake);
            }
            
            return actPartakes;
        }
        return null;
    }

}
