/*
 * Project Name: wrw-admin
 * File Name: ActivityStockController.java
 * Class Name: ActivityStockController
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
package com.hengtiansoft.business.activity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengtiansoft.business.activity.dto.ActivityConstranintDto;
import com.hengtiansoft.business.activity.dto.ActivityDateDto;
import com.hengtiansoft.business.activity.dto.ActivityPartakeDto;
import com.hengtiansoft.business.activity.dto.ActivitySpecDto;
import com.hengtiansoft.business.activity.dto.ActivityStockDto;
import com.hengtiansoft.business.activity.service.ActivityConstranintService;
import com.hengtiansoft.business.activity.service.ActivityStockService;
import com.hengtiansoft.common.controller.AbstractController;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.wrw.ActivityConst.SaleType;
import com.hengtiansoft.wrw.entity.ActivityConstranint;

/**
 * Class Name: ActivityStockController
 * Description: 活动库存管理控制器
 * @author zhongyidong
 *
 */
@Controller
@RequestMapping(value = "/activity/stock")
public class ActivityStockController extends AbstractController{
    
    // 来自添加商品页面
    public static final String ADD = "add";
    // 来自编辑商品页面
    public static final String EDIT = "edit";
    // 删除指定日期列表的库存
    public static final String DELETE_IN = "in";
    // 删除不在指定日期列表的库存
    public static final String DELETE_NOTIN = "notin";
    
    @Autowired
    private ActivityStockService activityStockService;
    @Autowired
    private ActivityConstranintService activityConstranintService;
    
    /**
     * Description: 查询所有活动库存信息
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "/list/{prodId}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<List<ActivityStockDto>> listStock(@PathVariable("prodId") Long productId) {
        List<ActivityStockDto> actStockList = activityStockService.findAllByActId(productId);
        if (CollectionUtils.isEmpty(actStockList)) {
            return new ResultDto<List<ActivityStockDto>>();
        }
        return ResultDtoFactory.toAck("查询成功", actStockList);
    }
    
    /**
     * Description: 根据条件查询活动库存信息
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "/query/{prodId}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<ActivityStockDto> queryStock(@PathVariable("prodId") Long productId, 
            @RequestParam (required = false) String actDate) {
        ActivityStockDto actStockDto = activityStockService.findByActId(productId, actDate);
        if (null == actStockDto) {
            return new ResultDto<ActivityStockDto>();
        }
        return ResultDtoFactory.toAck("查询成功", actStockDto);
    }
    
    /**
     * Description: 添加或者更新活动库存信息（新增商品页面）
     *
     * @param objectJson
     * 
     * @return
     */
    @RequestMapping(value = "/addStock", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Boolean> addStock(@RequestParam("data") String objectJson) {
        JSONObject object = JSONObject.fromObject(StringEscapeUtils.unescapeHtml(objectJson));
        
        Map<String, Class<?>> classMap = new HashMap<String, Class<?>> ();
        classMap.put("actDateList", ActivityDateDto.class);
        classMap.put("actSpecList", ActivitySpecDto.class);
        ActivityStockDto actStockDto = (ActivityStockDto)JSONObject.toBean(object, ActivityStockDto.class, classMap);
      
        return activityStockService.updateActStock(actStockDto, ADD);
    }
    
    /**
     * Description: 添加或者更新活动库存信息（编辑商品页面）
     *
     * @param objectJson
     * 
     * @return
     */
    @RequestMapping(value = "/updateStock", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Boolean> updateStock(@RequestParam("data") String objectJson) {
        JSONObject object = JSONObject.fromObject(StringEscapeUtils.unescapeHtml(objectJson));
        
        Map<String, Class<?>> classMap = new HashMap<String, Class<?>> ();
        classMap.put("actDateList", ActivityDateDto.class);
        classMap.put("actSpecList", ActivitySpecDto.class);
        ActivityStockDto actStockDto = (ActivityStockDto)JSONObject.toBean(object, ActivityStockDto.class, classMap);
      
        return activityStockService.updateActStock(actStockDto, EDIT);
    }
    
    /**
     * Description: 修改是否前端显示库存信息
     *
     * @param activityId
     * @param startTime
     * @return
     */
    @RequestMapping(value = "/isShowStock/{prodId}", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Integer> updateShowStock(@PathVariable("prodId") Long productId, 
            @RequestParam("showStock") String showStock) {
        int row = activityStockService.updateShowStock(productId, showStock);
        if (row > 0) {
            return ResultDtoFactory.toAck("修改成功！", row);
        }
      
        return ResultDtoFactory.toNack("修改失败！", row);
    }
    
    /**
     * Description: 查询下单约束信息
     *
     * @param activityId
     * @param startTime
     * @return
     */
    @RequestMapping(value = "/findPurchaseInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<ActivityConstranintDto> findConstranint(@RequestParam Long productId) {
        ActivityConstranintDto actConstranint = activityConstranintService.findByActId(productId);
        if (null == actConstranint) {
            ResultDtoFactory.toNack("保存失败！时间格式非法或者定时开售开始时间为空");
        }
        return ResultDtoFactory.toAck("保存成功！", actConstranint);
    }
    
    /**
     * Description: 添加或者更新下单约束信息
     *
     * @param activityId
     * @param startTime
     * @return
     */
    @RequestMapping(value = "/updatePurchaseInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<ActivityConstranint> updateConstranint(@RequestBody ActivityConstranintDto actConstranintDto) {
        if (SaleType.ONTIME_SALE.equals(actConstranintDto.getSaleType())) {
            boolean status = activityStockService.checkSaleDate(actConstranintDto.getProductId(), actConstranintDto.getStartDate(), 
                    actConstranintDto.getEndDate());
            if (!status) {
                return ResultDtoFactory.toNack("保存失败！定时开售的时间段必须包含商品的活动日期时间段！");
            }
        }
        
        ActivityConstranint actConstranint = activityConstranintService.updateContranint(actConstranintDto);
        if (null == actConstranint) {
            return ResultDtoFactory.toNack("保存失败！时间格式非法或者定时开售开始时间为空");
        }
        
        return ResultDtoFactory.toAck("保存成功！", actConstranint);
    }
    
    /**
     * Description: 删除指定日期的库存
     *
     * @param productId
     * @param actDateList
     * @return
     */
    @RequestMapping(value = "/delete/{prodId}", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Integer> deleteStock(@PathVariable("prodId") Long productId, 
            @RequestParam("actDateList[]") List<String> actDateList) {
        return ResultDtoFactory.toAck("删除成功！", activityStockService.deleteActStock(productId, actDateList, DELETE_IN));
    }
    
    /**
     * Description: 删除指定日期的库存
     *
     * @param productId
     * @param actDateList
     * @return
     */
    @RequestMapping(value = "/check/actDate", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Boolean> checkActDate(@RequestParam Long productId, @RequestParam String dateStart, 
            @RequestParam(required = false) String dateEnd) {
        boolean status = activityStockService.checkActDate(productId, dateStart, dateEnd);
        if (status) {
            return ResultDtoFactory.toAck("可以添加！", status);
        }
        
        return new ResultDto<Boolean>();
    }
    
    /**
     * Description: 查询参与人数信息
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "/findPartakeInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<List<ActivityPartakeDto>> findPartakeInfo(@RequestParam Long productId) {
        List<ActivityPartakeDto> actPartakes = activityConstranintService.findPartakeInfo(productId);
        if (null == actPartakes) {
            return new ResultDto<List<ActivityPartakeDto>>();
        }
        return ResultDtoFactory.toAck("查询成功！", actPartakes);
    }
     
}
