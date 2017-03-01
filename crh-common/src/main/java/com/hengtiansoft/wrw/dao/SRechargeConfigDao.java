/*
 * Project Name: wrw-common
 * File Name: SRechargeConfigDao.java
 * Class Name: SRechargeConfigDao
 * Copyright 2014 Hengtian Software Inc
 * Licensed under the Hengtiansoft
 * http://www.hengtiansoft.com
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.SRechargeConfigEntity;

/**
 * Class Name: SRechargeConfigDao
 * Description: TODO
 * 
 * @author chengminmiao
 */
public interface SRechargeConfigDao extends JpaRepository<SRechargeConfigEntity, Long> {

    List<SRechargeConfigEntity> findByStatus(String code);
    
    @Query(value = "select CONFIG_ID from S_RECHARGE_CONFIG where STATUS = ?2 and AMOUNT <= ?1 order by AMOUNT DESC LIMIT 1", nativeQuery = true)
    Long findConfigIdByAmountAndStatus(Long amount, String status);
    
    @Query(value = "select * from S_RECHARGE_CONFIG where STATUS = ?2 and AMOUNT <= ?1 order by AMOUNT DESC LIMIT 1", nativeQuery = true)
    SRechargeConfigEntity findByAmountAndStatus(Long amount, String status);

    @Query("SELECT rc FROM SRechargeConfigEntity rc where rc.amount = ?1 and rc.status = ?2  ")
    SRechargeConfigEntity findSameAmount(Long amount, String code);
}
