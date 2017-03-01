/*
 * Project Name: wrw-admin
 * File Name: BarcodeCheckServiceImpl.java
 * Class Name: BarcodeCheckServiceImpl
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
package com.hengtiansoft.business.barcode.service.impl;

import com.hengtiansoft.business.barcode.service.BarcodeCheckService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.barcode.dto.BarcodeCheckDto;
import com.hengtiansoft.business.barcode.dto.BarcodeDto;
import com.hengtiansoft.business.order.servicer.DistributionOrderService;
import com.hengtiansoft.business.order.servicer.OrderReturnService;
import com.hengtiansoft.business.shopStock.platformStock.service.PlatformShipmentService;
import com.hengtiansoft.business.shopStock.regionalPlatform.service.PReceivingService;
import com.hengtiansoft.business.shopStock.regionalPlatform.service.PShipmentService;
import com.hengtiansoft.business.shopStock.zStock.service.ShipmentService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.sequence.SequenceGenerator;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.SystemConst;
import com.hengtiansoft.wrw.dao.MOrderDetailDao;
import com.hengtiansoft.wrw.dao.MOrderMainDao;
import com.hengtiansoft.wrw.dao.MOrderReturnDao;
import com.hengtiansoft.wrw.dao.PGoodsDao;
import com.hengtiansoft.wrw.dao.SBarcodeCycleDao;
import com.hengtiansoft.wrw.dao.SBasicCodeDao;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.dao.SShipmentDetailDao;
import com.hengtiansoft.wrw.dao.SShipmentOrderDao;
import com.hengtiansoft.wrw.dao.SStockDao;
import com.hengtiansoft.wrw.dao.SStockRecordDao;
import com.hengtiansoft.wrw.dao.SUserDao;
import com.hengtiansoft.wrw.entity.MOrderMainEntity;
import com.hengtiansoft.wrw.entity.PGoodsEntity;
import com.hengtiansoft.wrw.entity.SBarcodeCycleEntity;
import com.hengtiansoft.wrw.entity.SBasicCodeEntity;
import com.hengtiansoft.wrw.enums.BarcodeStatusEnum;
import com.hengtiansoft.wrw.enums.OrderStatus;
import com.hengtiansoft.wrw.enums.SBasicCodeCycleStatusEnum;

/**
* Class Name: BarcodeCheckServiceImpl
* Description: 二维码验实现类
* @author zhisongliu
*
*/
@Service
public class BarcodeCheckServiceImpl implements BarcodeCheckService {
  @Autowired
  private SBasicCodeDao            codeDao;

  @Autowired
  private SBarcodeCycleDao         cycleDao;

  @Autowired
  private MOrderDetailDao          detailDao;

  @Autowired
  private PGoodsDao                goodsDao;

  @Autowired
  private SShipmentDetailDao       shipmentDetailDao;

  @Autowired
  private SUserDao                 userDao;

  @Autowired
  private SOrgDao                  orgDao;

  @Autowired
  private MOrderMainDao            mainDao;

  @Autowired
  private SShipmentOrderDao        shipmentOrderDao;

  @Autowired
  private SStockDao                stockDao;

  @Autowired
  private SStockRecordDao          stockRecordDao;

  @Autowired
  private PlatformShipmentService  shipmentService;

  @Autowired
  private PShipmentService         pShipmentService;

  @Autowired
  private PReceivingService        pReceivingService;

  @Autowired
  private DistributionOrderService distributionOrderService;

  @Autowired
  private OrderReturnService       orderReturnService;

  @Autowired
  private ShipmentService          zShipmentService;

  @Autowired
  private SequenceGenerator        sequenceGenerator;

  @Autowired
  private MOrderReturnDao          orderReturnDao;

  /**
   * 验证URL
   */
  @Override
  public ResultDto<BarcodeCheckDto> checkUrl(BarcodeDto dto) {
    if (WRWUtil.isEmpty(dto.getUrl())) {
      return ResultDtoFactory.toNack("扫码错误,扫码信息不能为空！");
    }
    String codeUrl = dto.getUrl();
    if (codeUrl.indexOf("c=") > 0) {
      // URL+16位明码，
      codeUrl = codeUrl.substring(0, codeUrl.indexOf("c=") + 18);
    }

    SBasicCodeEntity entity = codeDao.findByUrl(codeUrl);
    if (entity == null) {
      return ResultDtoFactory.toNack("该二维码不存在，请及时联系库存管理员！");
    }
    if (BarcodeStatusEnum.INVALID.getKey().equals(entity.getStatus())) {
      return ResultDtoFactory.toNack("该二维码已注销，请扫描其他二维码！");
    }
    PGoodsEntity gEntity = goodsDao.findOne(entity.getGoodId());
    if (gEntity == null) {
      return ResultDtoFactory.toNack("该二维码与物料不匹配，请及时联系库存管理员！");
    }
    List<String> prefixCodes = new ArrayList<String>();
    prefixCodes.add(entity.getPrefixCode());
    if (entity.getPrefixCode().equals(entity.getPackCode())) {
      List<String> codes = codeDao.findPrefixCodebyPackCode(entity.getPrefixCode());
      prefixCodes.addAll(codes);
    }
    // 验证二维码流水中的使用情况
    checkBarcodeCycleByUrl(prefixCodes, dto);
    String goodName = gEntity.getGoodName();
    // 验证二维码的发货方收货方情况
    checkOrderByUrl(goodName, dto, prefixCodes);
    // 验证二维码与订单的情况
    checkOrderAndStatus(prefixCodes, dto, entity.getGoodId(), goodName);

    return ResultDtoFactory.toAck("", getCheckDto(entity, goodName));
  }

  /**
   * 
  * Description: 收货订单中，判断该二维码是否是收货单中的二维码
  *
  * @param prefixCode
  * @param dto
  * @param packCode
   */
  private void checkOrderAndStatus(List<String> prefixCodes, BarcodeDto dto, Long goodId, String goodName) {
    List<String> nowPrefixCodes = prefixCodes;
    String status = dto.getStatus();
    // 订单是否包含该物料
    if (!SBasicCodeCycleStatusEnum.ADMIN_STORAGE.getKey().equals(status)) {
      checkOrderByGoodId(dto.getOrderId(), status, goodId, goodName);
    }
    if (SBasicCodeCycleStatusEnum.P_STORAGE.getKey().equals(status)) { // P收货
      // 验证流水中，该二维码是否存在于订单中
      checkOrderByPrefixCodes(prefixCodes, dto.getOrderId(), SBasicCodeCycleStatusEnum.ADMIN_P_SHIPMENT.getKey());
    }
    if (SBasicCodeCycleStatusEnum.Z_STORAGE.getKey().equals(status)) { // Z收货
      checkOrderByPrefixCodes(prefixCodes, dto.getOrderId(), SBasicCodeCycleStatusEnum.P_Z_SHIPMENT.getKey());
    }
    if (SBasicCodeCycleStatusEnum.C_Z_STOTAGE.getKey().equals(status)) { // C-Z退货
      List<String> listEntity = cycleDao.findOrderIdAndCodesAndStatus(dto.getOrderId(), nowPrefixCodes,
          SBasicCodeCycleStatusEnum.Z_C_SHIPMENT.getKey());
      nowPrefixCodes.removeAll(listEntity);
      if (nowPrefixCodes.size() > 0) {
        String codeName = "";
        for (String string : nowPrefixCodes) {
          codeName += string + ",";
        }
        throw new WRWException("扫码错误，该订单中不包含" + codeName + "的二维码");
      }
    }
    if (SBasicCodeCycleStatusEnum.C_SALE_STOTAGE.getKey().equals(status)) { // C-专卖店退货
      List<String> listEntity = cycleDao.findOrderIdAndCodesAndStatus(dto.getOrderId(), nowPrefixCodes,
          SBasicCodeCycleStatusEnum.Z_C_SHIPMENT.getKey());
      nowPrefixCodes.removeAll(listEntity);
      if (nowPrefixCodes.size() > 0) {
        String codeName = "";
        for (String string : nowPrefixCodes) {
          codeName += string + ",";
        }
        throw new WRWException("扫码错误，该订单中不包含" + codeName + "的二维码");
      }
    }

  }

  private void checkOrderByPrefixCodes(List<String> prefixCodes, String orderId, String status) {
    List<String> listOrders = cycleDao.findByPrefixCodesAndStatus(prefixCodes, status);
    for (String str : listOrders) {
      List<String> orders = getOrderIdList(str);
      if (!orders.contains(orderId)) {
        throw new WRWException("扫码错误，订单中不包含此二维码!");
      }
    }
  }

  private void checkOrderByGoodId(String orderId, String status, Long goodId, String goodName) {
    List<String> orders = getOrderIdList(orderId);
    List<Object[]> list = findOrderDetailByOrderIdAndStatus(orders, status);
    // 查询出订单中所有的物料ID
    List<Long> goodOrderList = new ArrayList<Long>();
    for (Object[] o : list) {
      goodOrderList.add((Long) o[0]);
    }
    if (CollectionUtils.isEmpty(goodOrderList)) {
      barcodeException(status, SystemConst.EXCEPTION_CODE_ISEMPTY, null);
    }
    // 验证该二维码的物料ID是否与订单中的物料ID匹配
    if (!goodOrderList.contains(goodId)) {
      throw new WRWException("扫码错误,该订单中未包含物料为" + goodName + "的数据!");
    }
  }

  /**
   * 
   * Description: 验证URL与发货方收货方的关系
   *
   */
  private void checkOrderByUrl(String goodName, BarcodeDto dto, List<String> prefixCodes) {
    String status = dto.getStatus();
    if (SBasicCodeCycleStatusEnum.P_STORAGE.getKey().equals(status)) { // p收货
      List<SBarcodeCycleEntity> codeList = cycleDao.findAllByPrefixCodesAndStatus(prefixCodes,
          SBasicCodeCycleStatusEnum.ADMIN_P_SHIPMENT.getKey());
      if (CollectionUtils.isNotEmpty(codeList)) {
        Long fromId = codeList.get(0).getFromId();
        String toId = codeList.get(0).getToId();
        Long nowToId = getOrgIdByCurrentUser();
        String[] strs = toId.split(",");
        boolean checkFlag = false;
        for (String str : strs) {
          if (str.equals(nowToId.toString())) {
            checkFlag = true;
          }
        }
        if (!checkFlag) {
          barcodeException(status, SystemConst.EXCEPTION_CODE_ISEMPTY, null);
        }
        Long nowFromId = SystemConst.PLATFORM_USER_ORG_ID;
        if (nowFromId.longValue() != fromId.longValue()) {
          throw new WRWException("商品出库信息有误,请联系库存管理员!");
        }
      }

    } else if (SBasicCodeCycleStatusEnum.P_Z_SHIPMENT.getKey().equals(status)) { // p-z发货
      List<SBarcodeCycleEntity> codeList = cycleDao.findAllByPrefixCodesAndStatus(prefixCodes,
          SBasicCodeCycleStatusEnum.P_STORAGE.getKey());
      Long fromId = codeList.get(0).getFromId();
      String toId = codeList.get(0).getToId();
      Long nowToId = getOrgIdByCurrentUser();
      if (!toId.equals(nowToId.toString())) {
        barcodeException(status, SystemConst.EXCEPTION_CODE_ISEMPTY, null);
      }
      Long nowfromId = SystemConst.PLATFORM_USER_ORG_ID;
      if ((fromId.longValue() != nowfromId.longValue())) {
        barcodeException(status, SystemConst.EXCEPTION_CODE_ISEMPTY, null);
      }
    } else if (SBasicCodeCycleStatusEnum.Z_STORAGE.getKey().equals(status)) { // Z收货
      List<SBarcodeCycleEntity> codeList = cycleDao.findAllByPrefixCodesAndStatus(prefixCodes,
          SBasicCodeCycleStatusEnum.P_Z_SHIPMENT.getKey());
      Long fromId = codeList.get(0).getFromId();
      String toId = codeList.get(0).getToId();
      Long nowToId = getOrgIdByCurrentUser();
      if (!toId.equals(nowToId.toString())) {
        barcodeException(status, SystemConst.EXCEPTION_CODE_ISEMPTY, null);
      }
      Long now1ZFromId = SystemConst.PLATFORM_USER_ORG_ID;
      // p--z发货，因为创建单子的时候，1可以创建，p可以创建，所有发货人不确认，故取到收货人的父节点
      Long nowPZFromId = orgDao.findOne(nowToId).getParentId();
      if ((fromId.longValue() != now1ZFromId.longValue()) && fromId.longValue() != nowPZFromId.longValue()) {
        barcodeException(status, SystemConst.EXCEPTION_CODE_ISEMPTY, null);
      }
    } else if (SBasicCodeCycleStatusEnum.C_Z_STOTAGE.getKey().equals(status)) { // c未收货，c到z端退货
      List<SBarcodeCycleEntity> codeList = cycleDao.findAllByPrefixCodesAndStatus(prefixCodes,
          SBasicCodeCycleStatusEnum.Z_C_SHIPMENT.getKey());
      Long toId = codeList.get(0).getFromId();
      Long nowToId = getOrgIdByCurrentUser();
      if (toId.longValue() != nowToId.longValue()) {
        barcodeException(status, SystemConst.EXCEPTION_CODE_ISEMPTY, null);
      }
    }
  }

  /**
   * 
   * Description: 获取验证的checkDto
   *
   * @param entity
   * @param goodName
   * @return
   */
  private BarcodeCheckDto getCheckDto(SBasicCodeEntity entity, String goodName) {
    BarcodeCheckDto checkDto = new BarcodeCheckDto();
    List<String> code = new ArrayList<String>();
    List<String> codeUrl = new ArrayList<String>();
    if (entity.getPrefixCode().equals(entity.getPackCode())) {
      // 如果相等，则提示说是扫码为母码，数量加+6
      checkDto.setGoodSelectNum(6);
      List<SBasicCodeEntity> entitys = codeDao.findbyParentIdEqualPrefixCode(entity.getPrefixCode());
      for (SBasicCodeEntity sEntity : entitys) {
        code.add(sEntity.getPrefixCode());
        codeUrl.add(sEntity.getUrl());
      }
      checkDto.setPackCode(entity.getPrefixCode());
    } else {
      code.add(entity.getPrefixCode());
      codeUrl.add(entity.getUrl());
    }
    checkDto.setPrefixCode(code);
    checkDto.setCodeUrl(codeUrl);
    checkDto.setGoodId(entity.getGoodId());
    checkDto.setGoodName(goodName);
    return checkDto;
  }

  private void checkBarcodeCycleByUrl(List<String> prefixCodes, BarcodeDto dto) {
    String status = dto.getStatus();
    // 验证当前情况下的流水情况
    checkNowCycleStatus(prefixCodes, status);
    if (SBasicCodeCycleStatusEnum.ADMIN_P_SHIPMENT.getKey().equals(status)
        || SBasicCodeCycleStatusEnum.P_STORAGE.getKey().equals(status)
        || SBasicCodeCycleStatusEnum.P_Z_SHIPMENT.getKey().equals(status)
        || SBasicCodeCycleStatusEnum.Z_STORAGE.getKey().equals(status)) {
      String lessStatus = getUpStatus(status);
      checkUpCycleStatus(prefixCodes, lessStatus);
    } else if (SBasicCodeCycleStatusEnum.Z_C_SHIPMENT.getKey().equals(status)) { // Z-C发货

      checkZCShipmentStatusCycle(status, prefixCodes);

    } else if (SBasicCodeCycleStatusEnum.C_Z_STOTAGE.getKey().equals(status)) { // C-Z退货

      checkCZStorageStatus(prefixCodes, status);

    } else if (SBasicCodeCycleStatusEnum.C_SALE_STOTAGE.getKey().equals(status)) { // C-专卖店退货

      // 验证这个二维码是否已经被收货了。
      MOrderMainEntity mainEntity = mainDao.findOne(dto.getOrderId());
      if (mainEntity == null) {
        throw new WRWException("订单有误，查不到当前订单!");
      }
    }
  }

  /**
   * 
  * Description: 检查当前状态的二维码流水
  *
  * @param prefixCodes
  * @param status
   */
  private void checkNowCycleStatus(List<String> prefixCodes, String status) {
    if (!SBasicCodeCycleStatusEnum.Z_C_SHIPMENT.getKey().equals(status)) {
      List<SBarcodeCycleEntity> nowList = cycleDao.findAllByPrefixCodesAndStatus(prefixCodes, status);
      if (CollectionUtils.isNotEmpty(nowList)) {// 不为空
        String codeName = "";
        for (SBarcodeCycleEntity sBarcodeCycleEntity : nowList) {
          codeName += sBarcodeCycleEntity.getPrefixCode() + ",";
        }
        codeName = codeName.substring(0, codeName.length() - 1);
         barcodeException(status, SystemConst.EXCEPTION_CODE_CHECK, codeName);
        //throw new WLYException("扫码失败,该二维码中" + codeName + "已被使用！");
      }
    }
  }

  private void checkCZStorageStatus(List<String> prefixCodes, String status) {
    String prefixCodeName = "";
    for (String prefixCode : prefixCodes) {
      List<SBarcodeCycleEntity> listZStorageBarcode = cycleDao.findAllCycleOrderByCreateDateDesc(prefixCode);
      if (CollectionUtils.isEmpty(listZStorageBarcode)) {// 为空
        prefixCodeName += prefixCode + ",";
        continue;
      }
      String nowStatus = listZStorageBarcode.get(0).getStatus();
      // 是Z-C
      if (!SBasicCodeCycleStatusEnum.Z_C_SHIPMENT.getKey().equals(nowStatus)) {
        prefixCodeName += prefixCode + ",";
        continue;
      }
      String toId = listZStorageBarcode.get(0).getFromId().toString();
      Long nowToId = getOrgIdByCurrentUser();
      if (!toId.equals(nowToId.toString())) {
        prefixCodeName += prefixCode + ",";
        continue;
      }
    }
    if (prefixCodeName.lastIndexOf(",") > 0 && prefixCodeName.length() > 0) {
      throw new WRWException("商品" + prefixCodeName + "的出库信息有误，请联系库存管理员!");
    }
  }

  private String getUpStatus(String status) {
    String lessStatus = "";
    if (SBasicCodeCycleStatusEnum.ADMIN_P_SHIPMENT.getKey().equals(status)) {
      lessStatus = SBasicCodeCycleStatusEnum.ADMIN_STORAGE.getKey();
    }
    if (SBasicCodeCycleStatusEnum.P_STORAGE.getKey().equals(status)) {
      lessStatus = SBasicCodeCycleStatusEnum.ADMIN_P_SHIPMENT.getKey();
    }
    if (SBasicCodeCycleStatusEnum.P_Z_SHIPMENT.getKey().equals(status)) {
      lessStatus = SBasicCodeCycleStatusEnum.P_STORAGE.getKey();
    }
    if (SBasicCodeCycleStatusEnum.Z_STORAGE.getKey().equals(status)) {
      lessStatus = SBasicCodeCycleStatusEnum.P_Z_SHIPMENT.getKey();
    }

    return lessStatus;
  }

  /**
   * 
  * Description: 查询上一级的情况
  *
  * @param prefixCodes
  * @param status
   */
  private void checkUpCycleStatus(List<String> prefixCodes, String status) {
    List<String> nowPrefixCodes = prefixCodes;
    List<SBarcodeCycleEntity> lessList = cycleDao.findAllByPrefixCodesAndStatus(nowPrefixCodes, status);
    if (CollectionUtils.isEmpty(lessList)) {
      barcodeException(status, SystemConst.EXCEPTION_CODE_ISEMPTY, "");
    }
    if (lessList.size() < nowPrefixCodes.size()) {// 查询出来的流水Size小于传出来的码集合
      String lessCodeName = "";
      List<String> lessPrefixCodes = new ArrayList<String>();
      // 获取流水集合中的二维码集合
      for (SBarcodeCycleEntity sBarcodeCycleEntity : lessList) {
        lessPrefixCodes.add(sBarcodeCycleEntity.getPrefixCode());
      }
      // 传过来的集合删除二维码集合
      nowPrefixCodes.removeAll(lessPrefixCodes);
      if (nowPrefixCodes.size() > 0) {
        for (String string : nowPrefixCodes) {
          lessCodeName += string + ",";
        }
        lessCodeName = lessCodeName.substring(0, lessCodeName.length() - 1);
      }
      if (lessCodeName.length() > 0 && lessCodeName != "") {
        throw new WRWException("扫码失败，该二维码中" + lessCodeName + "入库信息有误,请联系库存管理员");
      }
    }
  }

  /**
   * 
  * Description: Z-C发货，验证流水使用情况
  *
  * @param status
  * @param prefixCodes
   */
  private void checkZCShipmentStatusCycle(String status, List<String> prefixCodes) {
    // 来源有2个，一个是P发货给Z，Z入库了，一个是C退货给Z，Z入库了
    String prefixCodeName = "";
    for (String prefixCode : prefixCodes) {
      // 取该二维码的整个流水，按时间降序，取第一条，判断其状态是否为入库状态。
      List<SBarcodeCycleEntity> listBarcode = cycleDao.findAllCycleOrderByCreateDateDesc(prefixCode);
      if (CollectionUtils.isEmpty(listBarcode)) {
        prefixCodeName += prefixCode + ",";
        continue;
      }
      String nowStatus = listBarcode.get(0).getStatus();
      // 验证是否是P-Z，Z入库，或者是 C-Z退货，Z入库
      if (!SBasicCodeCycleStatusEnum.Z_STORAGE.getKey().equals(nowStatus)
          && !SBasicCodeCycleStatusEnum.C_Z_STOTAGE.getKey().equals(nowStatus)) {
        prefixCodeName += prefixCode + ",";
        continue;
      }
      // 验证toId 与当前的Z是否相等
      String toId = listBarcode.get(0).getToId();
      Long nowToId = userDao.findOne(AuthorityContext.getCurrentUser().getUserId()).getOrgId();
      if (!toId.equals(nowToId.toString())) {
        prefixCodeName += prefixCode + ",";
        continue;
      }
    }
    if (prefixCodeName.lastIndexOf(",") > 0 && prefixCodeName.length() > 0) {
      throw new WRWException("二维码" + prefixCodeName + "的入库信息有误，请联系库存管理员!");
    }
  }

  /**
   * 
   * Description: 报错信息提示
   *
   * @param status
   */
  private void barcodeException(String status, String source, String msg) {
    if (SystemConst.EXCEPTION_CODE_ISEMPTY.equals(source)) {
      if (SBasicCodeCycleStatusEnum.ADMIN_P_SHIPMENT.getKey().equals(status)
          || SBasicCodeCycleStatusEnum.P_Z_SHIPMENT.getKey().equals(status)
          || SBasicCodeCycleStatusEnum.Z_C_SHIPMENT.getKey().equals(status)) {
        throw new WRWException("该商品入库信息有误，请及时联系库存管理员！");
      } else if (SBasicCodeCycleStatusEnum.Z_STORAGE.getKey().equals(status)
          || SBasicCodeCycleStatusEnum.P_STORAGE.getKey().equals(status)) {
        throw new WRWException("该商品出库信息有误，请及时联系库存管理员！");
      }
    } else if (SystemConst.EXCEPTION_CODE_CHECK.equals(source)) {
      if (checkShipmentStatus(status)) {
        throw new WRWException("扫码失败,物料" + msg + "已被使用!");
      } else if (checkStorageStatus(status)) {
        throw new WRWException("扫码失败,物料" + msg + "已被收货入库!");
      }
    } else if (SystemConst.EXCEPTION_ORDER_STATUS.equals(source)) {
      if (SBasicCodeCycleStatusEnum.ADMIN_P_SHIPMENT.getKey().equals(status)
          || SBasicCodeCycleStatusEnum.P_Z_SHIPMENT.getKey().equals(status)
          || SBasicCodeCycleStatusEnum.Z_C_SHIPMENT.getKey().equals(status)) {
        throw new WRWException("扫码出库失败，订单" + msg + "已扫码出库");
      } else if (SBasicCodeCycleStatusEnum.Z_STORAGE.getKey().equals(status)
          || SBasicCodeCycleStatusEnum.P_STORAGE.getKey().equals(status)) {
        throw new WRWException("扫码入库失败，订单" + msg + "已扫码入库");
      }
    }
  }

  /**
   * 
   * Description: 通过id来获取ids
   *
   * @param orderId
   * @return
   */
  private List<String> getOrderIdList(String orderId) {
    String[] orders = orderId.split(",");
    List<String> orderIds = new ArrayList<String>();
    for (String str : orders) {
      orderIds.add(str);
    }
    return orderIds;
  }

  /**
   * Description: 通过不同status来查询订单的详情List
   *
   * @param orderId
   * @param status
   * @return
   */
  private List<Object[]> findOrderDetailByOrderIdAndStatus(List<String> orderIds, String status) {
    List<Object[]> list = new ArrayList<Object[]>();
    if (Integer.valueOf(status) > 0 && Integer.valueOf(status) < 5) {// 1--p、z端入库
      list = shipmentDetailDao.findNumByOrderId(orderIds);
    } else if (SBasicCodeCycleStatusEnum.Z_C_SHIPMENT.getKey().equals(status)
        || SBasicCodeCycleStatusEnum.C_Z_STOTAGE.getKey().equals(status)
        || SBasicCodeCycleStatusEnum.C_SALE_STOTAGE.getKey().equals(status)) {// Z端给C端发货,C端未收货退货，C端收货退货
      list = detailDao.findNumByOrderId(orderIds);
    }
    return list;
  }

  /**
   * 
  * Description: 判断是否是发货状态
  *
  * @param status
  * @return
   */
  private boolean checkShipmentStatus(String status) {
    boolean shipmentStatus = false;
    if (SBasicCodeCycleStatusEnum.ADMIN_P_SHIPMENT.getKey().equals(status)
        || SBasicCodeCycleStatusEnum.P_Z_SHIPMENT.getKey().equals(status)
        || SBasicCodeCycleStatusEnum.Z_C_SHIPMENT.getKey().equals(status)) {
      shipmentStatus = true;
    }
    return shipmentStatus;
  }

  /**
   * 
  * Description: 判断是否是收货状态
  *
  * @param status
  * @return
   */
  private boolean checkStorageStatus(String status) {
    boolean storageStatus = false;
    if (SBasicCodeCycleStatusEnum.ADMIN_STORAGE.getKey().equals(status)
        || SBasicCodeCycleStatusEnum.P_STORAGE.getKey().equals(status)
        || SBasicCodeCycleStatusEnum.Z_STORAGE.getKey().equals(status)
        || SBasicCodeCycleStatusEnum.C_Z_STOTAGE.getKey().equals(status)
        || SBasicCodeCycleStatusEnum.C_SALE_STOTAGE.getKey().equals(status)) {
      storageStatus = true;
    }
    return storageStatus;
  }

  /**
   * 
  * Description: 获取当前用户的orgId
  *
  * @return
   */
  private Long getOrgIdByCurrentUser() {
    Long userId = AuthorityContext.getCurrentUser().getUserId();
    if (userId == null) {
      throw new WRWException("获取不到当前登录用户的信息,请重新登录!");
    }
    return userDao.findOne(userId).getOrgId();
  }
}
