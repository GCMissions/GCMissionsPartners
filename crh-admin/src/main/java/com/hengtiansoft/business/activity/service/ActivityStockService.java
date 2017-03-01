/*
 * Project Name: wrw-admin
 * File Name: ActivityStockService.java
 * Class Name: ActivityStockService
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
package com.hengtiansoft.business.activity.service;

import java.util.List;

import com.hengtiansoft.business.activity.dto.ActivityStockDto;
import com.hengtiansoft.common.dto.ResultDto;

/**
 * Class Name: ActivityStockService
 * Description: 活动库存
 * @author zhongyidong
 *
 */
public interface ActivityStockService {
    
    /**
     * Description: 根据活动id查询库存信息（随机一条）
     *
     * @param productId
     * @param actDate
     * @return
     */
    ActivityStockDto findByActId(Long productId, String actDate);
    
    /**
     * Description: 根据活动id查询所有库存信息
     *
     * @param productId
     * @return
     */
    List<ActivityStockDto> findAllByActId(Long productId);
    
    /**
     * Description: 添加或者更新活动库存
     *
     * @param actStockDto
     * @param flag
     * 
     * @return
     */
    ResultDto<Boolean> updateActStock(ActivityStockDto actStockDto, String flag);
    
    /**
     * Description: 修改微信端是否显示库存
     *
     * @param productId
     * @param showStock
     * @return
     */
     int updateShowStock(Long productId, String showStock);
    
    /**
     * Description: 删除指定活动日期的库存（同一个商品）
     *
     * @param productId
     * @param actDateList
     * @param flag
     * 
     * @return
     */
    Integer deleteActStock(Long productId, List<String> actDateList, String flag);
    
    /**
     * Description: 检查活动时间是否可以添加
     *
     * @param productId
     * @param dateStart
     * @param dateEnd
     * @return
     */
    boolean checkActDate(Long productId, String dateStart, String dateEnd);
    
    /**
     * Description: 检查开售时间是否可以添加
     *
     * @param productId
     * @param dateStart
     * @param dateEnd
     * @return
     */
    boolean checkSaleDate(Long productId, String dateStart, String dateEnd);
    
    /**
     * Description: 获取所有可选的主规格
     *
     * @return
     */
    List<String> queryMainSpecs();
    
    /**
     * Description: 获取购买信息中的所有可选必填字段
     *
     * @return
     */
    List<String> queryRequiredFields();
    
}
