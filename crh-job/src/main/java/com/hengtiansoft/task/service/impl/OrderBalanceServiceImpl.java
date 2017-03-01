/*
 * Project Name: wrw-job
 * File Name: OrderBalanceServiceImpl.java
 * Class Name: OrderBalanceServiceImpl
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
package com.hengtiansoft.task.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.task.service.OrderBalanceService;
import com.hengtiansoft.wrw.SystemConst;
import com.hengtiansoft.wrw.dao.MCouponDao;
import com.hengtiansoft.wrw.dao.MMemberDao;
import com.hengtiansoft.wrw.dao.MOrderDetailDao;
import com.hengtiansoft.wrw.dao.MOrderMainDao;
import com.hengtiansoft.wrw.dao.MOrderReturnDao;
import com.hengtiansoft.wrw.dao.PPriceDao;
import com.hengtiansoft.wrw.dao.SBasicParaDao;
import com.hengtiansoft.wrw.dao.SFreightRebateDao;
import com.hengtiansoft.wrw.dao.SOrderBalanceDao;
import com.hengtiansoft.wrw.dao.SOrderRewardDao;
import com.hengtiansoft.wrw.dao.SRegionDao;
import com.hengtiansoft.wrw.entity.MMemberEntity;
import com.hengtiansoft.wrw.entity.MOrderDetailEntity;
import com.hengtiansoft.wrw.entity.MOrderMainEntity;
import com.hengtiansoft.wrw.entity.MOrderReturnEntity;
import com.hengtiansoft.wrw.entity.PPriceEntity;
import com.hengtiansoft.wrw.entity.SBasicParaEntity;
import com.hengtiansoft.wrw.entity.SFreightRebateEntity;
import com.hengtiansoft.wrw.entity.SOrderBalanceEntity;
import com.hengtiansoft.wrw.entity.SOrderRewardEntity;
import com.hengtiansoft.wrw.entity.SRegionEntity;
import com.hengtiansoft.wrw.enums.BasicTypeEnum;
import com.hengtiansoft.wrw.enums.RewardOrderTypeEnum;
import com.hengtiansoft.wrw.enums.ShipmentType;

/**
 * Class Name: OrderBalanceServiceImpl
 * Description: TODO
 * 
 * @author chengminmiao
 */
@Service
public class OrderBalanceServiceImpl implements OrderBalanceService {

    @Autowired
    private MOrderMainDao     mOrderMainDao;

    @Autowired
    private MOrderDetailDao   mOrderDetailDao;

    @Autowired
    private SFreightRebateDao sFreightRebateDao;

    @Autowired
    private PPriceDao         pPriceDao;

    @Autowired
    private SRegionDao        sRegionDao;

    @Autowired
    private SOrderBalanceDao  sOrderBalanceDao;

    @Autowired
    private MCouponDao        mCouponDao;

    @Autowired
    private MMemberDao        mMemberDao;

    @Autowired
    private SBasicParaDao     sBasicParaDao;

    @Autowired
    private SOrderRewardDao   sOrderRewardDao;

    @Autowired
    private MOrderReturnDao   mOrderReturnDao;

    /**
     * 查询未出账数据，取100条
     */
    @Override
    public List<MOrderMainEntity> getOrder() {
        return mOrderMainDao.findAllNoBalance();
    }

    @Transactional
    @Override
    public void orderBalance(MOrderMainEntity entity) {

        String balanceFlag = "2";
        String orderId = entity.getOrderId();
        try {
            SRegionEntity regionEntity = sRegionDao.findOne(entity.getRegionId());
            Integer regionId = Integer.valueOf(regionEntity.getTreePath().split(",")[2]);

            SOrderBalanceEntity orderBalanceEntity = new SOrderBalanceEntity();
            orderBalanceEntity.setOrderId(orderId);
            orderBalanceEntity.setShipmentType(entity.getShipmentType());
            orderBalanceEntity.setTotalAmount(entity.getTotalAmount());
            orderBalanceEntity.setActualAmount(entity.getActualAmount());
            orderBalanceEntity.setCouponAmount(entity.getCouponAmount());
            orderBalanceEntity.setAcctAmount(entity.getAcctAmount());
            orderBalanceEntity.setShipAmount(entity.getShipAmount());
            orderBalanceEntity.setFinishDate(entity.getFinishDate());
            orderBalanceEntity.setCreateDate(entity.getCreateDate());
            orderBalanceEntity.setPayDate(entity.getPayDate());
            orderBalanceEntity.setOrgId(entity.getOrgId());
            orderBalanceEntity.setCityId(Integer.valueOf(regionEntity.getTreePath().split(",")[2]));
            orderBalanceEntity.setProvId(Integer.valueOf(regionEntity.getTreePath().split(",")[1]));

            // 计算推广费用
            MMemberEntity memberEntity = mMemberDao.findOne(entity.getMemberId());
            Double refereeRebate = 0.0;
            if (null != memberEntity.getReferee()) {
                List<SBasicParaEntity> entities = sBasicParaDao.findByTypeId(BasicTypeEnum.REFEREE_REBATE.getKey());
                refereeRebate = new Double(entities.get(0).getParaValue1() == null ? "0" : entities.get(0).getParaValue1());
            }
            // 自提与配送的
            if (entity.getShipmentType().equalsIgnoreCase(ShipmentType.delivery.getKey())
                    || entity.getShipmentType().equalsIgnoreCase(ShipmentType.oneself.getKey())) {

                // 算订单价格
                List<MOrderDetailEntity> detailEntities = mOrderDetailDao.findByOrderId(orderId);
                List<PPriceEntity> priceEntities = pPriceDao.findByOrderAndRegion(orderId, regionId);
                HashMap<Long, PPriceEntity> priceMap = new HashMap<Long, PPriceEntity>();
                for (PPriceEntity priceEntity : priceEntities) {
                    priceMap.put(priceEntity.getProductId(), priceEntity);
                }

                Long pProdProfit = 0L;
                Long zProdProfit = 0L;
                Long prodProfit = 0L;
                Long referee = 0L;

                for (MOrderDetailEntity detailEntity : detailEntities) {
                    PPriceEntity priceEntity = priceMap.get(detailEntity.getProductId());

                    Long couponValue = mCouponDao.countValueByOrderIdAndProdId(detailEntity.getProductId(), orderId);
                    // 利润 = 商品金额 -优惠券减免-商品成本-运行成本
                    Long profit = detailEntity.getAmount() - (couponValue == null ? 0 : couponValue)
                            - (detailEntity.getNum() * (priceEntity.getCostPrice() + priceEntity.getOperCosts()));
                    // 推广费 = 利润 * 分成比例
                    referee = (long) (referee + profit * refereeRebate / 100);
                    // 利润 = 利润-分成比例
                    profit = profit - referee;

                    // 利润 * 分成比例
                    pProdProfit = pProdProfit + (long) (profit * (priceEntity.getpRebate() == null ? 0 : priceEntity.getpRebate()) / 100);
                    zProdProfit = zProdProfit + (long) (profit * (priceEntity.getzRebate() == null ? 0 : priceEntity.getzRebate()) / 100);
                    prodProfit = prodProfit + profit - pProdProfit - zProdProfit;

                }

                orderBalanceEntity.setpProdProfit(pProdProfit);
                orderBalanceEntity.setzProdProfit(zProdProfit);
                orderBalanceEntity.setProdProfit(prodProfit);

                // 计算配送费价格
                if (entity.getShipAmount() > 0) {
                    SFreightRebateEntity freightRebateEntity = sFreightRebateDao.findByRegionId(regionId);
                    if (null == freightRebateEntity) {
                        freightRebateEntity = sFreightRebateDao.findByRegionId(SystemConst.REGION_NOT_OPEN_CITY);
                    }

                    Long shipAmount = entity.getShipAmount();
                    Long pShipProfit = (long) (shipAmount * (freightRebateEntity.getpRebate() == null ? 0 : freightRebateEntity.getpRebate()) / 100);
                    Long zShipProfit = (long) (shipAmount * (freightRebateEntity.getzRebate() == null ? 0 : freightRebateEntity.getzRebate()) / 100);

                    orderBalanceEntity.setpShipProfit(pShipProfit);
                    orderBalanceEntity.setzShipProfit(zShipProfit);
                    orderBalanceEntity.setShipProfit(shipAmount - pShipProfit - zShipProfit);
                } else {
                    orderBalanceEntity.setpShipProfit(0L);
                    orderBalanceEntity.setzShipProfit(0L);
                    orderBalanceEntity.setShipProfit(0L);
                }
                sOrderBalanceDao.save(orderBalanceEntity);
                balanceFlag = "1";

                // 保存推广费用
                SOrderRewardEntity rewardEntity = new SOrderRewardEntity();
                rewardEntity.setOrderId(orderId);
                rewardEntity.setAmount(referee);
                rewardEntity.setOrgId(entity.getOrgId());
                rewardEntity.setAssignDate(entity.getAssignDate());
                rewardEntity.setType(RewardOrderTypeEnum.INVITE.getKey());
                rewardEntity.setCreateDate(new Date());

                sOrderRewardDao.save(rewardEntity);

            }
            // 邮寄的
            else if (entity.getShipmentType().equalsIgnoreCase(ShipmentType.express.getKey())) {

                // 算订单价格
                List<MOrderDetailEntity> detailEntities = mOrderDetailDao.findByOrderId(orderId);
                // 先取全国的价格
                List<PPriceEntity> priceEntities = pPriceDao.findByOrderAndRegion(orderId, SystemConst.REGION_NOT_OPEN_CITY);
                HashMap<Long, PPriceEntity> priceMap = new HashMap<Long, PPriceEntity>();
                for (PPriceEntity priceEntity : priceEntities) {
                    priceMap.put(priceEntity.getProductId(), priceEntity);
                }
                // 取本地区的价格，然后覆盖全国价格
                priceEntities = pPriceDao.findByOrderAndRegion(orderId, regionId);
                for (PPriceEntity priceEntity : priceEntities) {
                    priceMap.put(priceEntity.getProductId(), priceEntity);
                }

                Long prodProfit = 0L;
                Long referee = 0L;

                for (MOrderDetailEntity detailEntity : detailEntities) {
                    PPriceEntity priceEntity = priceMap.get(detailEntity.getProductId());

                    Long couponValue = mCouponDao.countValueByOrderIdAndProdId(detailEntity.getProductId(), orderId);
                    Long profit = detailEntity.getAmount() - (couponValue == null ? 0 : couponValue)
                            - (detailEntity.getNum() * (priceEntity.getCostPrice() + priceEntity.getOperCosts()));

                    // 推广费 = 利润 * 分成比例
                    referee = (long) (referee + profit * refereeRebate / 100);
                    // 利润 = 利润-分成比例
                    profit = profit - referee;

                    prodProfit = prodProfit + profit;

                }

                orderBalanceEntity.setpProdProfit(0L);
                orderBalanceEntity.setzProdProfit(0L);
                orderBalanceEntity.setProdProfit(prodProfit);
                // 邮寄费用利润
                orderBalanceEntity.setpShipProfit(0L);
                orderBalanceEntity.setzShipProfit(0L);
                orderBalanceEntity.setShipProfit(entity.getShipAmount());
                sOrderBalanceDao.save(orderBalanceEntity);

                // 保存推广费用
                SOrderRewardEntity rewardEntity = new SOrderRewardEntity();
                rewardEntity.setOrderId(orderId);
                rewardEntity.setAmount(referee);
                rewardEntity.setOrgId(entity.getOrgId());
                rewardEntity.setAssignDate(entity.getAssignDate());
                rewardEntity.setType(RewardOrderTypeEnum.INVITE.getKey());
                rewardEntity.setCreateDate(new Date());

                sOrderRewardDao.save(rewardEntity);

                balanceFlag = "1";
            }
        } catch (Exception e) {
            mOrderMainDao.updateOrderBalance(orderId, balanceFlag);
            e.printStackTrace();
        }
        mOrderMainDao.updateOrderBalance(orderId, balanceFlag);

    }

    @Override
    public List<MOrderReturnEntity> getOrderReturn() {
        return mOrderReturnDao.findAllNoBalance();
    }

    @Override
    @Transactional
    public void orderReturn(MOrderReturnEntity entity) {
        String balanceFlag = "2";
        String orderId = entity.getReturnId();
        try {
            SOrderBalanceEntity oldEntity = sOrderBalanceDao.findOne(entity.getOrderMainId());
            if (null == oldEntity) {
                balanceFlag = "3";
            } else {
                SOrderBalanceEntity newEntity = new SOrderBalanceEntity();
                newEntity.setOrderId(orderId);
                newEntity.setTotalAmount(0L - entity.getReturnAmount() - entity.getReturnAmount());
                newEntity.setActualAmount(0L - entity.getReturnAmount());
                newEntity.setCouponAmount(0L - entity.getReturnAmount());
                newEntity.setAcctAmount(0L);
                newEntity.setShipAmount(0L);
                newEntity.setFinishDate(entity.getCreateDate());
                newEntity.setCreateDate(entity.getCreateDate());
                newEntity.setPayDate(entity.getCreateDate());
                newEntity.setOrgId(oldEntity.getOrgId());
                newEntity.setCityId(oldEntity.getCityId());
                newEntity.setProvId(oldEntity.getProvId());
                newEntity.setpProdProfit(0L - oldEntity.getpProdProfit());
                newEntity.setpShipProfit(0L);
                newEntity.setzProdProfit(0L - oldEntity.getzProdProfit());
                newEntity.setzShipProfit(0L);
                newEntity.setProdProfit(0L - oldEntity.getProdProfit());
                newEntity.setShipProfit(0L);

                balanceFlag = "1";

                sOrderBalanceDao.save(newEntity);

            }
        } catch (Exception e) {
            mOrderMainDao.updateOrderBalance(orderId, balanceFlag);
            e.printStackTrace();
        }
        mOrderMainDao.updateOrderBalance(orderId, balanceFlag);

    }
}
