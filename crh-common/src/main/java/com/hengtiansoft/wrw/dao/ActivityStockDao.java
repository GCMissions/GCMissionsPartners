/*
 * Project Name: wrw-common
 * File Name: ActStockDao.java
 * Class Name: ActStockDao
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
package com.hengtiansoft.wrw.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.ActivityStock;

/**
 * Class Name: ActivityStockDao
 * Description: 活动库存
 * @author zhongyidong
 *
 */
public interface ActivityStockDao extends JpaRepository<ActivityStock, Long>, JpaSpecificationExecutor<ActivityStock>  {
    
    /**
     * Description: 根据活动Id查询库存信息
     *
     * @param activityId
     * @return
     */
    @Query(value = "SELECT * FROM act_stock WHERE product_id = ?1 ORDER BY act_date ASC", nativeQuery = true)
    List<ActivityStock> findByActId(Long productId);
    
    /**
     * Description: 根据活动Id和活动日期查询库存编号
     *
     * @param activityId
     * @return
     */
    @Query(value = "SELECT * FROM act_stock WHERE product_id = ?1 and date(act_date) = ?2", nativeQuery = true)
    ActivityStock findIdByActId(Long productId, String actDate);
    
    /**
     * Description: 根据活动Id查询库存信息
     *
     * @param activityId
     * @return
     */
    @Query(value = "SELECT * FROM act_stock WHERE product_id = ?1 order by act_date desc limit 1", nativeQuery = true)
    ActivityStock findOneByActId(Long productId);
    
    /**
     * Description: 根据活动Id和活动日期查询库存记录
     *
     * @param activityId
     * @return
     */
    @Query(value = "SELECT * FROM act_stock WHERE product_id = ?1 and date(act_date) = ?2", nativeQuery = true)
    ActivityStock findOneByActIdAndActDate(Long productId, String actDate);
    
    /**
     * Description: 查询活动日期及库存总量
     *
     * @param productId
     * @return
     */
    @Query(value = "select act_date, total_count from act_stock where product_id = ?1", nativeQuery = true)
    List<Object[]> findActDateByActId(Long productId);
    
    /**
     * Description: 查询不在活动日期列表对应的库存记录编号
     *
     * @param productId
     * @param actDateList
     * @return
     */
    @Modifying
    @Query(value = "select id from act_stock where product_id = ?1 and date(act_date) not in ?2", nativeQuery = true)
    List<BigInteger> queryNotActStock(Long productId, List<String> actDateList);
    
    /**
     * Description: 查询在活动日期列表对应的库存记录编号
     *
     * @param productId
     * @param actDateList
     * @return
     */
    @Modifying
    @Query(value = "select id from act_stock where product_id = ?1 and date(act_date) in ?2", nativeQuery = true)
    List<BigInteger> queryInActStock(Long productId, List<String> actDateList);
    
    /**
     * Description: 修改是否显示库存
     *
     * @param productId
     * @param showStock
     * @return
     */
    @Modifying
    @Query(value = "update act_stock set show_stock = ?2 where product_id = ?1", nativeQuery = true)
    int updateIsShowStock(Long productId, String showStock);
    
    /**
     * Description: 删除库存
     *
     * @param actStockIdList
     * @return
     */
    @Modifying
    @Query(value = "delete from act_stock where id in ?1", nativeQuery = true)
    int deleteActStock(List<BigInteger> actStockIdList);
    
    /**
     * Description: 根据活动日期查询数据
     *
     * @param productId
     * @param date
     * @return
     */
    @Query(value="select count(*) from act_stock where product_id = ?1 and date(act_date) = ?2", nativeQuery = true)
    int countRowByActDate(Long productId, String date);
    
    /**
     * Description: 根据活动日期查询数据
     *
     * @param productId
     * @param date
     * @return
     */
    @Query(value="select count(*) from act_stock where product_id = ?1 and date(act_date) < ?2", nativeQuery = true)
    int countRowBySaleType(Long productId, String date);
    
    /**
     * Description: 根据活动日期时间段查询数据
     *
     * @param productId
     * @param dateStart
     * @param dateEnd
     * @return
     */
    @Query(value="select count(*) from act_stock where product_id = ?1 and "
            + " date(act_date) >= ?2 and date(act_date) <= ?3", nativeQuery = true)
    int countRowByActDate(Long productId, String dateStart, String dateEnd);
    
    /**
     * Description: 根据活动日期时间段查询数据
     *
     * @param productId
     * @param dateStart
     * @param dateEnd
     * @return
     */
    @Query(value="select count(*) from act_stock where product_id = ?1 and "
            + " (date(act_date) < ?2 or date(act_date) > ?3)", nativeQuery = true)
    int countRowBySaleType(Long productId, String dateStart, String dateEnd);
    
    @Query(value = "select distinct (product_id) ,LOW_PRICE,HIGH_PRICE from act_stock where PRODUCT_ID in (?1)", nativeQuery = true)
    List<Object[]> findByProductIds(List<Long> listIds);
    
    @Modifying
    @Query(value="update act_stock set total_count=(total_count + ?2) where  id = ?1 " ,nativeQuery=true)
    void updateActTotalStock(Long stockId,Integer num);
    
    @Query(value="select * from act_stock where  product_id = ?1  and act_date = ?2" ,nativeQuery=true)
    ActivityStock findActByProductIdAndActDate(Long productId,String actDate);
}
