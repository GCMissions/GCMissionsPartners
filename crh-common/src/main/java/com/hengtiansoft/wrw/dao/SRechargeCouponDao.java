/*
 * Project Name: wrw-common
 * File Name: SRechargeCouponDao.java
 * Class Name: SRechargeCouponDao
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

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.SRechargeCouponEntity;
import com.hengtiansoft.wrw.entity.SRechargeCouponPK;

/**
 * Class Name: SRechargeCouponDao
 * Description: TODO
 * 
 * @author chengminmiao
 */
public interface SRechargeCouponDao extends JpaRepository<SRechargeCouponEntity, SRechargeCouponPK> {

    @Query("FROM SRechargeCouponEntity rc where rc.id.configId = ?")
    List<SRechargeCouponEntity> findByConfigId(Long configId);

    @Query("FROM SRechargeCouponEntity rc where rc.id.configId in (?1)")
    List<SRechargeCouponEntity> findByConfigIdIn(Collection<Long> configId);

    @Query(value = "SELECT c.COUPON_NAME, c.NUM, cc.INVALID_DATE, cc.VALUE, cc.EFFECT_DATE from S_RECHARGE_COUPON c inner join S_COUPON_CONFIG cc on c.COUPON_ID = cc.COUPON_ID where c.CONFIG_ID = ?1", nativeQuery = true)
    List<Object[]> findCouponByConfigId(Long configId);

    @Query(value = "SELECT c.COUPON_NAME, c.NUM, cc.INVALID_DATE, cc.VALUE, cc.EFFECT_DATE from S_RECHARGE_COUPON c inner join S_COUPON_CONFIG cc on c.COUPON_ID = cc.COUPON_ID where c.CONFIG_ID = (select r.CONFIG_ID from S_RECHARGE_CONFIG r where r.AMOUNT <= ?1 and r.STATUS = ?2 order by r.AMOUNT desc limit 1)", nativeQuery = true)
    List<Object[]> findByAmountAndStatus(double amount, Long status);

    @Query("select rc.id.couponId from SRechargeCouponEntity rc where rc.id.configId = ?1")
    List<Long> findCouponIdByConfigId(Long configId);

    @Query("select distinct(rc.id.couponId) from SRechargeCouponEntity rc where rc.id.configId in (?1)")
    List<Long> findCouponIdByConfigIdIn(Collection<Long> configId);

    @Query("delete from SRechargeCouponEntity where id.configId = ?1 and id.couponId not in ?2")
    @Modifying
    Integer deleteByConfigIdAndNotCouponIds(Long configId, List<Long> couponList);

    @Query("delete from SRechargeCouponEntity where id.configId = ?1 ")
    @Modifying
    void deleteByConfigId(Long configId);

    @Query("select count(1) from SRechargeCouponEntity rc , SRechargeConfigEntity r  where rc.id.configId = r.configId and r.status = '1' and rc.id.couponId = ?1 ")
    Integer countConfigByCouponId(Long CouponId);
}
