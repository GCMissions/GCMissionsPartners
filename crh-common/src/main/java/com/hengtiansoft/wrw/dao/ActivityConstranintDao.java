/*
 * Project Name: wrw-common
 * File Name: ActConstranintDao.java
 * Class Name: ActConstranintDao
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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.ActivityConstranint;

/**
 * Class Name: ActivityConstranintDao
 * Description: 活动下单约束信息
 * @author zhongyidong
 *
 */
public interface ActivityConstranintDao extends JpaRepository<ActivityConstranint, Long> {
    
    /**
     * Description: 查询某个活动的约束信息
     *
     * @param productId
     * @return
     */
    @Query(value = "SELECT * FROM act_constranint where product_id = ?1", nativeQuery = true)
    ActivityConstranint findByProductId(Long productId);
    
    /**
     * Description: 查询某个活动的约束信息编号
     *
     * @param productId
     * @return
     */
    @Query(value = "SELECT id FROM act_constranint where product_id = ?1", nativeQuery = true)
    Long findIdByProductId(Long productId);

}
