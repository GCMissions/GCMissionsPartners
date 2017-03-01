/*
 * Project Name: wrw-admin
 * File Name: BarcodeService.java
 * Class Name: BarcodeService
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.barcode.dto.BarcodeCheckDto;
import com.hengtiansoft.business.barcode.dto.BarcodeDto;
import com.hengtiansoft.business.barcode.dto.BarcodeGoodDto;
import com.hengtiansoft.business.barcode.dto.BarcodePageDto;
import com.hengtiansoft.business.barcode.dto.BarcodeStorageDto;
import com.hengtiansoft.business.barcode.service.BarcodeService;
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
import com.hengtiansoft.wrw.entity.SShipmentOrderEntity;
import com.hengtiansoft.wrw.entity.SStockEntity;
import com.hengtiansoft.wrw.entity.SStockRecordEntity;
import com.hengtiansoft.wrw.entity.SUserEntity;
import com.hengtiansoft.wrw.enums.BarcodeStatusEnum;
import com.hengtiansoft.wrw.enums.SBasicCodeCycleStatusEnum;
import com.hengtiansoft.wrw.enums.ShipmentState;
import com.hengtiansoft.wrw.enums.StockRecordTypeEmum;

/**
 * Class Name: BarcodeService Description:
 * 
 * @author zhisongliu
 *
 */
@Service
public class BarcodeServiceImpl implements BarcodeService {

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
   * 获取订单信息
   */
  @Override
  public ResultDto<BarcodePageDto> findByOrderId(String orderId, String status) {
    BarcodePageDto dto = new BarcodePageDto();
    if (WRWUtil.isEmpty(orderId)) {
      return ResultDtoFactory.toNack("订单信息不能为空！");
    }
    List<String> orders = getOrderIdList(orderId);
    List<Object[]> list = findOrderDetailByOrderIdAndStatus(orders, status);
    if (CollectionUtils.isNotEmpty(list)) {
      checkOrderInfoByListAndStatus(list, status);
      dto = getBarcodeDto(list, status);
    }
    return ResultDtoFactory.toAck("", dto);
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
   * Description: 获取扫码入库或者出库中物料信息
   *
   * @param list
   * @param status
   * @return
   */
  private BarcodePageDto getBarcodeDto(List<Object[]> list, String status) {
    BarcodePageDto dto = new BarcodePageDto();
    Map<Long, BarcodeGoodDto> map = new HashMap<Long, BarcodeGoodDto>();
    for (Object[] object : list) {
      if (object[0] != null) {
        BarcodeGoodDto gdto = map.get(Long.valueOf(object[0].toString()));
        if (gdto != null) {
          gdto.setGoodNum(gdto.getGoodNum() + (object[1] == null ? 0L : (Long) object[1]));
        } else {
          gdto = new BarcodeGoodDto();
          gdto.setGoodId(Long.valueOf(object[0].toString()));
          gdto.setGoodNum(object[1] == null ? 0L : (Long) object[1]);
          gdto.setGoodName(object[2] == null ? null : object[2].toString());
          map.put(Long.valueOf(object[0].toString()), gdto);
        }
      }
    }
    List<BarcodeGoodDto> nowDtos = new ArrayList<BarcodeGoodDto>();
    for (Long key : map.keySet()) {
      nowDtos.add(map.get(key));
    }
    dto.setGoodDtos(nowDtos);
    return dto;
  }

  /**
   * 
   * Description: 通过订单信息获取发货方与当前登录用户的关系
   *
   * @param list
   * @param status
   */
  private void checkOrderInfoByListAndStatus(List<Object[]> list, String status) {
    // C端退货，专卖店入库，不需要验证订单与当前用户的信息
    if (!SBasicCodeCycleStatusEnum.C_SALE_STOTAGE.getKey().equals(status)) {
        // list第[3]个值，对应的shipmentOrder中的recevingId  和 OrderMain中的orgId
        // Z-C 发货方为orgId, C_Z退货Z，收货方为orgId,
        Long orgId =(Long) list.get(0)[3];
        // 1--p 因为可以发多种货，所以验证订单的发货方是否正确
        if (SBasicCodeCycleStatusEnum.ADMIN_P_SHIPMENT.getKey().equals(status)) {
          orgId = (Long) list.get(0)[4];
        }
        // p--z发货，因为创建单子的时候，1可以创建，p可以创建，所有发货人不确认，故取到收货人的父节点
        if (SBasicCodeCycleStatusEnum.P_Z_SHIPMENT.getKey().equals(status)) {
          orgId = orgDao.findOne(orgId).getParentId();
        }
        // 当前登录用户的orgId
        Long nowOrgId = getOrgIdByCurrentUser();
        if (orgId.longValue() != nowOrgId.longValue()) {
          throw new WRWException("该订单有误,请检查!");
        }
      }
  }
  
  /**
   * 
  * Description: 判断是否是发货状态
  *
  * @param status
  * @return
   */
  private boolean checkShipmentStatus(String status){
    boolean shipmentStatus = false;
    if(SBasicCodeCycleStatusEnum.ADMIN_P_SHIPMENT.getKey().equals(status) || SBasicCodeCycleStatusEnum.P_Z_SHIPMENT.getKey().equals(status)
        ||SBasicCodeCycleStatusEnum.Z_C_SHIPMENT.getKey().equals(status)){
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
  private boolean checkStorageStatus(String status){
    boolean storageStatus = false;
    if(SBasicCodeCycleStatusEnum.P_STORAGE.getKey().equals(status)
        ||SBasicCodeCycleStatusEnum.Z_STORAGE.getKey().equals(status) || SBasicCodeCycleStatusEnum.C_Z_STOTAGE.getKey().equals(status) 
        || SBasicCodeCycleStatusEnum.C_SALE_STOTAGE.getKey().equals(status)){
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
    // 验证二维码流水中的情况
    checkBarcodeCycleByUrl(entity.getPrefixCode(), dto, entity.getPackCode());
    // 验证收货单中二维码
    checkOrderAndStatus(entity.getPrefixCode(), dto, entity.getPackCode());
    String goodName = gEntity.getGoodName();
    // 验证二维码与订单的关系
    checkOrderByUrl(goodName, dto, entity.getGoodId());

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
  private void checkOrderAndStatus(String prefixCode, BarcodeDto dto, String packCode) {
    if (SBasicCodeCycleStatusEnum.C_Z_STOTAGE.getKey().equals(dto.getStatus())  || SBasicCodeCycleStatusEnum.C_SALE_STOTAGE.getKey().equals(dto.getStatus())) {
      List<String> codes = new ArrayList<String>();
      codes.add(prefixCode);
      if (prefixCode.equals(packCode)) {
        List<String> prefixCodes = codeDao.findPrefixCodebyPackCode(prefixCode);
        codes.addAll(prefixCodes);
      }
      String status = "";
      if (SBasicCodeCycleStatusEnum.C_Z_STOTAGE.getKey().equals(dto.getStatus())) {// C未收货,到Z端退货
        status = SBasicCodeCycleStatusEnum.Z_C_SHIPMENT.getKey();
      } else if (SBasicCodeCycleStatusEnum.C_SALE_STOTAGE.getKey().equals(dto.getStatus())) {// C到专卖店退货
        status = SBasicCodeCycleStatusEnum.Z_C_SHIPMENT.getKey();
      }
      List<String> listEntity = cycleDao.findOrderIdAndCodesAndStatus(dto.getOrderId(), codes, status);
      codes.removeAll(listEntity);
      if (codes.size() > 0) {
        String codeName = "";
        for (String string : codes) {
          codeName += string + ",";
        }
        throw new WRWException("扫码错误，该订单中不包含" + codeName + "的二维码");
      }
    }
  }
  /**
   * 
   * Description: 验证URL与订单的关系
   *
   */
  private void checkOrderByUrl(String goodName, BarcodeDto dto, Long goodId) {
    // 1入库操作，没有订单号，故不需要验证与订单的关系
    if (!SBasicCodeCycleStatusEnum.ADMIN_STORAGE.getKey().equals(dto.getStatus())) {
      List<String> orders = getOrderIdList(dto.getOrderId());
      List<Object[]> list = findOrderDetailByOrderIdAndStatus(orders, dto.getStatus());
      // 查询出订单中所有的物料ID
      List<Long> goodOrderList = new ArrayList<Long>();
      for (Object[] o : list) {
        goodOrderList.add((Long) o[0]);
      }
      if (CollectionUtils.isEmpty(goodOrderList)) {
        barcodeException(dto.getStatus(), SystemConst.EXCEPTION_CODE_ISEMPTY, null);
      }
      // 验证该二维码的物料ID是否与订单中的物料ID匹配
      if (!goodOrderList.contains(goodId)) {
        throw new WRWException("扫码错误,该订单中未包含物料为" + goodName + "的数据!");
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


  private void checkBarcodeCycleByUrl(String prefixCode, BarcodeDto dto, String packCode) {
    String status = dto.getStatus();
    if (prefixCode.equals(packCode)) {
      checkUrlByPrefixCodeEqualPackCode(status, prefixCode);
    } else {
      checkUrlByPrefixNotEqualPackCode(status, prefixCode, dto.getOrderId());
    }

  }

  /**
   * 
  * Description: 验证扫描的是母码的情况
  *
  * @param status
  * @param prefixCode
   */
  private void checkUrlByPrefixCodeEqualPackCode(String status, String prefixCode) {
    // 查询出所有的子码
    List<String> prefixCodes = codeDao.findPrefixCodebyPackCode(prefixCode);
    // 查询出所有该子码在这种状态下的流水信息
    List<SBarcodeCycleEntity> cycleEntitys = cycleDao.findAllByPrefixCodesAndStatus(prefixCodes, status);
    if (CollectionUtils.isNotEmpty(cycleEntitys)) {// 不为空
      String codeName = "";
      for (SBarcodeCycleEntity sBarcodeCycleEntity : cycleEntitys) {
        codeName += sBarcodeCycleEntity.getPrefixCode() + ",";
      }
      codeName = codeName.substring(0, codeName.length() - 1);
      throw new WRWException("扫码失败,该二维码中" + codeName + "已被使用！");
    } else {
      // 验证子码在流水为空的情况，验证上一级的情况
      checkUrlByPrefixCodeEqualPackCodeAndCycleIsNull(status, prefixCodes);
    }
  }

  /**
   * 
   * Description: 扫描的是子码，验证该种情况下，子码在流水中的使用情况
   *
   * @param status
   * @param prefixCode
   * @param orderId
   */
  private void checkUrlByPrefixNotEqualPackCode(String status, String prefixCode, String orderId) {
    List<String> codes = new ArrayList<String>();
    codes.add(prefixCode);
    if (SBasicCodeCycleStatusEnum.ADMIN_STORAGE.getKey().equals(status)) {// 1入库
      // 判断该二维码是否在流水表中存在
      Integer countCode = cycleDao.findByPrefixCode(prefixCode);
      if (countCode > 0) {
        throw new WRWException("该二维码已存在，请及时联系库存管理员！");
      }
    } else if (SBasicCodeCycleStatusEnum.Z_C_SHIPMENT.getKey().equals(status)) {// Z-C发货，
      // 取该二维码的整个流水，按时间降序，取第一条，判断其状态是否为入库状态。
      List<SBarcodeCycleEntity> listBarcode = cycleDao.findAllCycleOrderByCreateDateDesc(prefixCode);
      if (CollectionUtils.isEmpty(listBarcode)) {
        barcodeException(status, SystemConst.EXCEPTION_CODE_CHECK, null);
      }
      String nowStatus = listBarcode.get(0).getStatus();
      // 验证是否是P-Z，Z入库，或者是 C-Z退货，Z入库
      if (!SBasicCodeCycleStatusEnum.Z_STORAGE.getKey().equals(nowStatus)
          && !SBasicCodeCycleStatusEnum.C_Z_STOTAGE.getKey().equals(nowStatus)) {
        throw new WRWException("该商品入库信息有误，请及时联系库存管理员！");
      }
      // 验证toId 与当前的Z是否相等
      String toId = listBarcode.get(0).getToId();
      Long nowToId = getOrgIdByCurrentUser();
      if (!toId.equals(nowToId.toString())) {
        barcodeException(status, SystemConst.EXCEPTION_CODE_CHECK, null);
      }

    } else if (SBasicCodeCycleStatusEnum.Z_STORAGE.getKey().equals(status)) { // P-Z入库
      // 取该二维码的整个流水，按时间降序，取第一条，判断其状态。
      List<SBarcodeCycleEntity> listZStorageBarcode = cycleDao.findAllCycleOrderByCreateDateDesc(prefixCode);
      if (CollectionUtils.isEmpty(listZStorageBarcode)) {// 为空
        barcodeException(status, SystemConst.EXCEPTION_CODE_CHECK, null);
      }
      String nowStatus = listZStorageBarcode.get(0).getStatus();
      // 验证是否为P-Z发货
      if (!SBasicCodeCycleStatusEnum.P_Z_SHIPMENT.getKey().equals(nowStatus)) {
        throw new WRWException("该商品出库信息有误，请及时联系库存管理员！");
      }
      String toId = listZStorageBarcode.get(0).getToId();
      Long nowToId = getOrgIdByCurrentUser();
      if (!toId.equals(nowToId.toString())) {
        barcodeException(status, SystemConst.EXCEPTION_CODE_CHECK, null);
      }

    } else if (SBasicCodeCycleStatusEnum.C_SALE_STOTAGE.getKey().equals(status)) { // C到专卖店退货
      // 查询该二维码在该状态下的使用情况
      Integer countNum = cycleDao.findCountByPrefixCodesAndStatus(codes, status);
      if (countNum > 0) {
        throw new WRWException("该二维码已失效！");
      }
      // 验证这个二维码是否已经被收货了。
      MOrderMainEntity mainEntity = mainDao.findOne(orderId);

    } else if (SBasicCodeCycleStatusEnum.C_Z_STOTAGE.getKey().equals(status)) {// C未收到货退货，Z收货
      List<SBarcodeCycleEntity> listZStorageBarcode = cycleDao.findAllCycleOrderByCreateDateDesc(prefixCode);
      if (CollectionUtils.isEmpty(listZStorageBarcode)) {// 为空
        barcodeException(status, SystemConst.EXCEPTION_CODE_CHECK, null);
      }
      String nowStatus = listZStorageBarcode.get(0).getStatus();
      // 验证是否为Z-C
      if (!SBasicCodeCycleStatusEnum.Z_C_SHIPMENT.getKey().equals(nowStatus)) {
        throw new WRWException("该商品出库信息有误，请及时联系库存管理员！");
      }
      String toId = "";
      if (SBasicCodeCycleStatusEnum.Z_C_SHIPMENT.getKey().equals(nowStatus)) {
        toId = listZStorageBarcode.get(0).getFromId().toString();
      }
      Long nowToId = getOrgIdByCurrentUser();
      if (!toId.equals(nowToId.toString())) {
        barcodeException(status, SystemConst.EXCEPTION_CODE_CHECK, null);
      }
    } else {
      // 1-P发货 p收货，p--z
      // 查询该二维码在该状态下的使用情况
      Integer countNum = cycleDao.findCountByPrefixCodesAndStatus(codes, status);
      if (countNum > 0) {
        throw new WRWException("该二维码已被使用！");
      }
      // 通过状态，查询流水表中该二维码的前一级流水
      String statusFlag = String.valueOf((Integer.valueOf(status) - 1));
      SBarcodeCycleEntity codeEntity = cycleDao.findByPrefixCodeAndStatus(prefixCode, statusFlag);
      if (codeEntity == null) {
        barcodeException(status, SystemConst.EXCEPTION_CODE_CHECK, null);
      }
      Long fromId = codeEntity.getFromId();
      String toId = codeEntity.getToId();
      // 验证toId是否与当前用户相等，
      Long nowToId = getOrgIdByCurrentUser();
      // 判断statusFlag 是否是 1--p 或者 p--z
      // 如果是，则toId可能有多个值，则只需要判断toId是否包含nowToId
      if (SBasicCodeCycleStatusEnum.ADMIN_P_SHIPMENT.getKey().equals(statusFlag)
          || SBasicCodeCycleStatusEnum.P_Z_SHIPMENT.getKey().equals(statusFlag)) {
        String[] strs = toId.split(",");
        boolean checkFlag = false;
        for (String str : strs) {
          if (str.equals(nowToId.toString())) {
            checkFlag = true;
          }
        }
        if (!checkFlag) {
          barcodeException(status, SystemConst.EXCEPTION_CODE_CHECK, null);
        }
      } else {
        if (!toId.equals(nowToId.toString())) {
          barcodeException(status, SystemConst.EXCEPTION_CODE_CHECK, null);
        }
      }
      // 验证fromId是否相等
      if (SBasicCodeCycleStatusEnum.P_Z_SHIPMENT.getKey().equals(status)) {
        Long now1ZFromId = SystemConst.PLATFORM_USER_ORG_ID;
        // p--z发货，因为创建单子的时候，1可以创建，p可以创建，所有发货人不确认，故取到收货人的父节点
        Long nowPZFromId = orgDao.findOne(nowToId).getParentId();

        if ((fromId.longValue() != now1ZFromId.longValue()) && fromId.longValue() != nowPZFromId.longValue()) {
          barcodeException(status, SystemConst.EXCEPTION_CODE_CHECK, null);
        }
      } else {
        // P入库 该流水的上一级中的fromId = 0L
        Long nowFromId = SystemConst.PLATFORM_USER_ORG_ID;
        // 如果是1--P ;
        if (SBasicCodeCycleStatusEnum.ADMIN_P_SHIPMENT.getKey().equals(status)) {
          nowFromId = SystemConst.SYSTEM_USER_ORG_ID;
        }
        if (fromId.longValue() != nowFromId.longValue()) {
          barcodeException(status, SystemConst.EXCEPTION_CODE_CHECK, null);
        }
      }

    }
  }

  /**
   * 
   * Description: 扫描的是母码，该子码在流水表为空,验证该子码的上一级情况，
   *
   * @param status
   * @param prefixCodes
   */
  private void checkUrlByPrefixCodeEqualPackCodeAndCycleIsNull(String status, List<String> prefixCodes) {
    if (SBasicCodeCycleStatusEnum.Z_STORAGE.getKey().equals(status)|| SBasicCodeCycleStatusEnum.C_Z_STOTAGE.getKey().equals(status)) { // Z入库
        checkZStorageStatusCycle(prefixCodes);
    } else if (SBasicCodeCycleStatusEnum.Z_C_SHIPMENT.getKey().equals(status)) { // Z-C发货
        checkZCShipmentStatusCycle(status, prefixCodes);
    } else {
      // 如果是1入库，则不需要判断
      if (!SBasicCodeCycleStatusEnum.ADMIN_STORAGE.getKey().equals(status)) {
          String statusless = String.valueOf((Integer.valueOf(status) - 1));
          if (SBasicCodeCycleStatusEnum.C_SALE_STOTAGE.getKey().equals(status)) {
              statusless = SBasicCodeCycleStatusEnum.C_STORAGE.getKey();
          }
          // 查询出上一级的操作信息
          List<SBarcodeCycleEntity> lessList = cycleDao.findAllByPrefixCodesAndStatus(prefixCodes, statusless);
          if (CollectionUtils.isEmpty(lessList)) {// 为空，说明上一级不存在
            barcodeException(status, SystemConst.EXCEPTION_CODE_CHECK, null);
          }
          if (lessList.size() < prefixCodes.size()) {// 子码比上一级的少
            String lessCodeName = "";
            List<String> lessPrefixCodes = new ArrayList<String>();
            for (SBarcodeCycleEntity sBarcodeCycleEntity : lessList) {
              lessPrefixCodes.add(sBarcodeCycleEntity.getPrefixCode());
            }
            prefixCodes.removeAll(lessPrefixCodes);
            if (prefixCodes.size() > 0) {
              for (String string : prefixCodes) {
                lessCodeName += string + ",";
              }
              lessCodeName = lessCodeName.substring(0, lessCodeName.length() - 1);
              if (SBasicCodeCycleStatusEnum.ADMIN_P_SHIPMENT.getKey().equals(status)) {
                throw new WRWException("扫码失败,该二维码中" + lessCodeName + "没有入库！");
              } else {
                throw new WRWException("扫码失败,该二维码中" + lessCodeName + "已被使用！");
              }
          }
        }
      }
    }
  }

  /**
   * 
  * Description: Z-C发货，验证流水
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
        prefixCodeName +=prefixCode+",";
      }
      String nowStatus = listBarcode.get(0).getStatus();
      // 验证是否是P-Z，Z入库，或者是 C-Z退货，Z入库
      if (!SBasicCodeCycleStatusEnum.Z_STORAGE.getKey().equals(nowStatus)
          && !SBasicCodeCycleStatusEnum.C_Z_STOTAGE.getKey().equals(nowStatus)) {
        throw new WRWException("该商品入库信息有误，请及时联系库存管理员！");
      }
      // 验证toId 与当前的Z是否相等
      String toId = listBarcode.get(0).getToId();
      Long nowToId = userDao.findOne(AuthorityContext.getCurrentUser().getUserId()).getOrgId();
      if (!toId.equals(nowToId.toString())) {
        barcodeException(status, SystemConst.EXCEPTION_CODE_CHECK, null);
      }
    }
    if (prefixCodeName.lastIndexOf(",") > 0 && prefixCodeName.length() > 0) {
      throw new WRWException("商品" + prefixCodeName + "的入库信息有误，请联系库存管理员!");
    }
  }

  /**
   * 
  * Description: Z收货，验证流水
  *
  * @param prefixCodes
   */
  private void checkZStorageStatusCycle(List<String> prefixCodes) {
    // 来源有2个，一个是P发货给Z，一个是C退货给Z
    String prefixCodeName = "";
    for (String prefixCode : prefixCodes) {
      List<SBarcodeCycleEntity> listZStorageBarcode = cycleDao.findAllCycleOrderByCreateDateDesc(prefixCode);
      if (CollectionUtils.isEmpty(listZStorageBarcode)) {// 为空
        prefixCodeName += prefixCode + ",";
        continue;
      }
      String nowStatus = listZStorageBarcode.get(0).getStatus();
      // 验证是否为P-Z发货，或者是Z-C
      if (!SBasicCodeCycleStatusEnum.P_Z_SHIPMENT.getKey().equals(nowStatus)
          && !SBasicCodeCycleStatusEnum.Z_C_SHIPMENT.getKey().equals(nowStatus)) {
        prefixCodeName += prefixCode + ",";
        continue;
      }
      String toId = "";
      if (SBasicCodeCycleStatusEnum.P_Z_SHIPMENT.getKey().equals(nowStatus)) {
        toId = listZStorageBarcode.get(0).getToId();
      }
      if (SBasicCodeCycleStatusEnum.Z_C_SHIPMENT.getKey().equals(nowStatus)) {
        toId = listZStorageBarcode.get(0).getToId().toString();
      }
      Long nowToId = userDao.findOne(AuthorityContext.getCurrentUser().getUserId()).getOrgId();
      if (!toId.equals(nowToId.toString())) {
        prefixCodeName += prefixCode + ",";
        continue;
      }
    }
    if (prefixCodeName.lastIndexOf(",") > 0 && prefixCodeName.length() > 0) {
      throw new WRWException("商品" + prefixCodeName + "的出库信息有误，请联系库存管理员!");
    }
  }

  /**
   * 扫码操作保存
   */
  @Override
  @Transactional
  public ResultDto<String> storageCode(BarcodeStorageDto sdto) {
    if (CollectionUtils.isEmpty(sdto.getPrefixCodes())) {
      return ResultDtoFactory.toNack("无有效条码,请检查后再试！");
    }
    List<SBasicCodeEntity> sbList = codeDao.findByPrefixCodes(sdto.getPrefixCodes());
    if (CollectionUtils.isEmpty(sbList)) {
      barcodeException(sdto.getStatus(), SystemConst.EXCEPTION_CODE_ISEMPTY, null);
    }
    List<String> subCodeList = sdto.getPrefixCodes();// 子码集合
    List<String> codeList = sdto.getPackCodes();// 母码集合
    Integer codeNum = subCodeList.size();
    // 验证查询出的码数 与传过来的商品物料总数是否一致
    if (!codeNum.equals(sdto.getGoodNum())) {
      return ResultDtoFactory.toNack("条码总数与商品实际总数不一致,请检查后确认！");
    }

    if (!SBasicCodeCycleStatusEnum.ADMIN_STORAGE.getKey().equals(sdto.getStatus())) {
      // 验证传过来的商品物料总数，与订单总数是否一致
      checkNumByOrder(codeNum, sdto);
      // 验证订单状态是否已被改变
      checkOrderStatus(sdto.getStatus(), sdto.getOrderId());
    }
    List<String> codes = new ArrayList<String>();
    List<Object[]> listEntity = codeDao.findAllPrefixCodes(subCodeList);
    if (CollectionUtils.isNotEmpty(listEntity)) {
      for (Object[] object : listEntity) {
        if (Integer.valueOf(object[1].toString()) == 6) {
          codes.add(object[0].toString());
        }
      }
    }
    subCodeList.addAll(codes);
    subCodeList.addAll(codeList);
    //对subCodeList去重
    List<String> nowList =new ArrayList<String>();
    for (String str : subCodeList) {
      if(!nowList.contains(str)){
        nowList.add(str);
      }
    }
    // 流水表中数据插入
    saveBarcodeCycle(nowList, sdto);
    // 更新订单信息，及更新库存
    updateAllOrder(sdto);

    return ResultDtoFactory.toAck("操作成功！");
  }

  /**
   * 
   * 删除操作时，验证该码对应的数据
   */
  @Override
  public ResultDto<Integer> checkCode(String prefixCode) {
    if (WRWUtil.isEmpty(prefixCode)) {
      return ResultDtoFactory.toNack("传值错误,请检查！");
    }
    SBasicCodeEntity entity = codeDao.findByPrefixCode(prefixCode);
    if (entity == null) {
      return ResultDtoFactory.toNack("传值错误,请检查！");
    }

    Integer numCount = 1;
    if (entity.getPackCode().equals(prefixCode)) {
      numCount = 6;
    }
    return ResultDtoFactory.toAck("", numCount);
  }

  /**
   * 
   * Description: 验证传过来的商品物料总数，与订单总数是否一致
   *
   * @param codeNum
   * @param sdto
   */
  private void checkNumByOrder(Integer codeNum, BarcodeStorageDto sdto) {

    // 1入库没有订单，故不需要验证
    List<String> orderIds = getOrderIdList(sdto.getOrderId());
    List<Object[]> list = findOrderDetailByOrderIdAndStatus(orderIds, sdto.getStatus());
    Long countOrderNum = 0L;
    for (Object[] obj : list) {
      Long objNum = obj[1] == null ? 0L : Long.valueOf(obj[1].toString());
      countOrderNum += objNum;
    }
    if (codeNum.intValue() != countOrderNum.longValue()) {
      throw new WRWException("扫码总数与订单总数不一致，请检查后确认!");
    }
  }

  /**
   * 
   * Description: 通过不同的状态，来更新不同的订单信息
   *
   * @param sdto
   */
  private void updateAllOrder(BarcodeStorageDto sdto) {
    if (SBasicCodeCycleStatusEnum.ADMIN_STORAGE.getKey().equals(sdto.getStatus())) {
      saveStock(sdto.getGoodDtos(), StockRecordTypeEmum.AUTOADD.getCode());
    } else {
      String[] orderIds = sdto.getOrderId().split(",");
      for (String str : orderIds) {
        // 更新订单状态 更新库存
        if (SBasicCodeCycleStatusEnum.C_Z_STOTAGE.getKey().equals(sdto.getStatus())
            || SBasicCodeCycleStatusEnum.C_SALE_STOTAGE.getKey().equals(sdto.getStatus())) {// c-z退货，
                                                                                            // c-专卖店退货
//          orderReturnService.confirmReturnOrder(str, sdto.getStatus(), sdto.getPackCodes(), sdto.getPrefixCodes());
        } else {
          updateOrder(str, sdto.getStatus());
        }
      }
    }
  }

  /**
   * 
   * Description: 保存库存
   *
   */
  private void saveStock(List<BarcodeGoodDto> goodDtos, String status) {
    SUserEntity user = userDao.findOne(AuthorityContext.getCurrentUser().getUserId());
    Long orgId = user.getOrgId();
    List<Long> goodOrderList = new ArrayList<Long>();
    for (BarcodeGoodDto barcodeGoodDto : goodDtos) {
      goodOrderList.add(barcodeGoodDto.getGoodId());
    }
    if (CollectionUtils.isNotEmpty(goodOrderList)) {
      List<SStockEntity> listEntity = stockDao.findByOrgIdAndGoodsIdIn(orgId, goodOrderList);
      Map<Long, SStockEntity> map = new HashMap<Long, SStockEntity>();
      Map<Long, Long> numMap = new HashMap<Long, Long>();
      for (SStockEntity sStockEntity : listEntity) {
        map.put(sStockEntity.getGoodsId(), sStockEntity);
      }
      for (BarcodeGoodDto barcodeGoodDto : goodDtos) {
        Long id = barcodeGoodDto.getGoodId();
        Long num = barcodeGoodDto.getGoodNum();
        SStockEntity entity = map.get(id);
        if (entity == null) {// 说明不存在
          entity = new SStockEntity();
          entity.setGoodsId(id);
          entity.setOrgId(orgId);
          entity.setOrgName(user.getUserName());
          entity.setSafeNum(0L);
          entity.setStandardNum(0L);
          entity.setStockNum(num);
          map.put(id, entity);
        } else {
          if (StockRecordTypeEmum.AUTOADD.getCode().equals(status)) {
            entity.setStockNum(entity.getStockNum() + num);
          } else {
            entity.setStockNum(entity.getStockNum() - num);
          }
        }
        numMap.put(id, num);
      }
      List<SStockEntity> nowlist = new ArrayList<SStockEntity>();
      for (Long key : map.keySet()) {
        nowlist.add(map.get(key));
      }
      stockDao.save(nowlist);

      List<SStockRecordEntity> sds = new ArrayList<SStockRecordEntity>();
      for (SStockEntity sEntity : nowlist) {
        Long changeNum = numMap.get(sEntity.getGoodsId());
        if (!changeNum.equals(SystemConst.PLATFORM_USER_ORG_ID)) {// change_num不为0
          // 插入库存变更记录
          SStockRecordEntity sd = new SStockRecordEntity();
          sd.setStockId(sEntity.getStockId());
          sd.setOperType(status);
          sd.setChangeNum(numMap.get(sEntity.getGoodsId()));
          sd.setOperId(AuthorityContext.getCurrentUser().getUserId());
          sd.setOperDate(new Date());
          sds.add(sd);
        }
      }
      stockRecordDao.save(sds);

    }
  }

  /**
   * Desc:更新订单状态
   * 
   */
  private void updateOrder(String orderId, String status) {
    if (SBasicCodeCycleStatusEnum.ADMIN_P_SHIPMENT.getKey().equals(status)) {// 1-p
      shipmentService.deliverGoods(orderId);
    } else if (SBasicCodeCycleStatusEnum.P_STORAGE.getKey().equals(status)) {// p入库
      pReceivingService.changeStatus(orderId);
    } else if (SBasicCodeCycleStatusEnum.P_Z_SHIPMENT.getKey().equals(status)) {// p--z
      pShipmentService.changeStatus(orderId);
    } else if (SBasicCodeCycleStatusEnum.Z_STORAGE.getKey().equals(status)) {// z入库
      zShipmentService.confirmGetGoods(orderId);
    } else if (SBasicCodeCycleStatusEnum.Z_C_SHIPMENT.getKey().equals(status)) {// z-c
      distributionOrderService.sendGoods(orderId);
    }
  }

  /**
   * 
   * Description: 流水表操作,插入语句
   */
  public void saveBarcodeCycle(List<String> sbList, BarcodeStorageDto sdto) {
    Long fromId = SystemConst.SYSTEM_USER_ORG_ID;
    String fromName = "";
    String toId = "";
    String toName = "";
    String status = sdto.getStatus();
    String content = "";
    String orderId = sdto.getOrderId();
    if (SBasicCodeCycleStatusEnum.ADMIN_STORAGE.getKey().equals(status)) { // 1入库
      toId = SystemConst.SYSTEM_USER_ORDER_ID;
      fromName = SystemConst.SYSTEM_USERNAME;
      toName = SystemConst.PLATFORM_USERNAME;
      content = SystemConst.PLATFORM_CONTENT;
      orderId = SystemConst.SYSTEM_USER_ORDER_ID;
    } else if (SBasicCodeCycleStatusEnum.ADMIN_P_SHIPMENT.getKey().equals(status)
        || SBasicCodeCycleStatusEnum.P_Z_SHIPMENT.getKey().equals(status)) { // 1发货给P，p发货给Z，
      // 此时有可能订单好有多个
      List<String> orders = getOrderIdList(sdto.getOrderId());
      List<SShipmentOrderEntity> listEntity = shipmentOrderDao.findAll(orders);
      for (SShipmentOrderEntity entity : listEntity) {
        toId += entity.getReceivingId().toString() + ",";
        toName += orgDao.findOne(entity.getReceivingId()).getOrgName() + ",";
      }
      fromId = listEntity.get(0).getShipmentId();
      if (fromId.equals(SystemConst.PLATFORM_USER_ORG_ID)) {
        fromName = SystemConst.PLATFORM_USERNAME;
        if (SBasicCodeCycleStatusEnum.P_Z_SHIPMENT.getKey().equals(status)) {// 1--z发货创建的订单，
          fromId = orgDao.findOne(Long.valueOf(toId.split(",")[0])).getParentId();
          fromName = orgDao.findOne(fromId).getOrgName();
        }
      } else {
        fromName = orgDao.findOne(fromId).getOrgName();
      }
      toId = toId.substring(0, toId.length() - 1);
      toName = toName.substring(0, toName.length() - 1);
      if (toName.lastIndexOf(",") > -1) {
        content = fromName + SystemConst.SHIPMENT_CONTENT + toName.substring(0, toName.length() - 1);
      }
      content = fromName + SystemConst.SHIPMENT_CONTENT + toName;

    } else if (SBasicCodeCycleStatusEnum.P_STORAGE.getKey().equals(status)
        || SBasicCodeCycleStatusEnum.Z_STORAGE.getKey().equals(status)) { // P收货，z收货，
      SShipmentOrderEntity entity = shipmentOrderDao.findOne(sdto.getOrderId());
      toId = entity.getReceivingId().toString();
      fromId = entity.getShipmentId();
      if (fromId.equals(SystemConst.PLATFORM_USER_ORG_ID)) {
        fromName = SystemConst.PLATFORM_USERNAME;
        if (SBasicCodeCycleStatusEnum.Z_STORAGE.getKey().equals(status)) {
          fromId = orgDao.findOne(Long.valueOf(toId)).getParentId();
          fromName = orgDao.findOne(fromId).getOrgName();
        }
      } else {
        fromName = orgDao.findOne(fromId).getOrgName();
      }
      toName = orgDao.findOne(Long.valueOf(toId)).getOrgName();
      content = fromName + SystemConst.SHIPMENT_CONTENT + toName + "," + toName + SystemConst.STORAGE_CONTENT;

    } else if (SBasicCodeCycleStatusEnum.Z_C_SHIPMENT.getKey().equals(status)) {// Z端发货给消费者
      MOrderMainEntity mainEntity = mainDao.findOne(sdto.getOrderId());
      fromId = mainEntity.getOrgId();
      fromName = mainEntity.getOrgName();
      toId = mainEntity.getMemberId().toString();
      toName = mainEntity.getMemberName();
      if(WRWUtil.isEmpty(toName)){
        toName =mainEntity.getPhone();
      }
      content = fromName + SystemConst.SHIPMENT_CONTENT + toName;

    } else if (SBasicCodeCycleStatusEnum.C_Z_STOTAGE.getKey().equals(status)) {// C端退货给Z端Z端入库
      MOrderMainEntity mainEntity = mainDao.findOne(sdto.getOrderId());
      fromId = mainEntity.getMemberId();
      fromName = mainEntity.getMemberName();
      if(WRWUtil.isEmpty(fromName)){
        fromName =mainEntity.getPhone();
      }
      toId = mainEntity.getOrgId().toString();
      toName = mainEntity.getOrgName();
      content = fromName + SystemConst.RETURN_CONTENT + toName + "," + toName + SystemConst.STORAGE_CONTENT;

    } else if (SBasicCodeCycleStatusEnum.C_SALE_STOTAGE.getKey().equals(status)) { // C端收货，在专卖店进行退货
      MOrderMainEntity mainEntity = mainDao.findOne(sdto.getOrderId());
      fromId = mainEntity.getMemberId();
      fromName = mainEntity.getMemberName();
      if(WRWUtil.isEmpty(fromName)){
        fromName =mainEntity.getPhone();
      }
      toId = mainEntity.getOrgId().toString();
      toName = mainEntity.getOrgName();
      String return_Name = orgDao.findOne(getOrgIdByCurrentUser()).getOrgName();
      content = fromName + SystemConst.RETURN_CONTENT + toName + "," + return_Name + SystemConst.STORAGE_CONTENT;
    }

    // 往二维码流水表中插入一条记录
    List<SBarcodeCycleEntity> listCycleEntity = new ArrayList<SBarcodeCycleEntity>();
    for (String code : sbList) {
      SBarcodeCycleEntity cycleEntity = new SBarcodeCycleEntity();
      cycleEntity.setPrefixCode(code);
      cycleEntity.setSource(sdto.getSource());
      cycleEntity.setStatus(status);
      cycleEntity.setCreateId(AuthorityContext.getCurrentUser().getUserId());
      cycleEntity.setCreateDate(new Date());
      cycleEntity.setOrderId(orderId);
      cycleEntity.setToId(toId);
      cycleEntity.setToName(toName);
      cycleEntity.setFromId(fromId);
      cycleEntity.setFromName(fromName);
      cycleEntity.setContent(content);
      listCycleEntity.add(cycleEntity);
    }
    cycleDao.save(listCycleEntity);
  }

  /**
   * 
   * Description: 报错信息提示
   *
   * @param status
   */
  private void barcodeException(String status, String source, String msg) {
    if (SystemConst.EXCEPTION_CODE_ISEMPTY.equals(source)) {
      if (checkShipmentStatus(status)) {
        throw new WRWException("该商品入库信息有误，请及时联系库存管理员！");
      } else if (checkStorageStatus(status)) {
        throw new WRWException("该商品出库信息有误，请及时联系库存管理员！");
      }
    } else if (SystemConst.EXCEPTION_CODE_CHECK.equals(source)) {
      if (checkShipmentStatus(status)) {
        throw new WRWException("该商品入库信息有误，请及时联系库存管理员！");
      } else if (checkStorageStatus(status)) {
        throw new WRWException("该商品出库信息有误，请及时联系库存管理员！");
      }
    } else if (SystemConst.EXCEPTION_ORDER_STATUS.equals(source)) {
      if (checkShipmentStatus(status)) {
        throw new WRWException("扫码出库失败，订单" + msg + "已扫码出库");
      } else if (checkStorageStatus(status)) {
        throw new WRWException("扫码入库失败，订单" + msg + "已扫码入库");
      }
    }
  }

  /**
   * 
   * Description: 通过不同状态，验证订单状态
   *
   * @param sdto
   */
  private void checkOrderStatus(String status, String orderId) {
    List<String> orderIds = getOrderIdList(orderId);
    String orderStatusNow = "";
    // Z-C发货，C退货给Z Z操作入库
    if (SBasicCodeCycleStatusEnum.Z_C_SHIPMENT.getKey().equals(status)
        || SBasicCodeCycleStatusEnum.C_Z_STOTAGE.getKey().equals(status)
        || SBasicCodeCycleStatusEnum.C_SALE_STOTAGE.getKey().equals(status)) {
      List<MOrderMainEntity> orderMainEntityList = mainDao.findAll(orderIds);
      for (MOrderMainEntity mOrderMainEntity : orderMainEntityList) {}
    } else if (SBasicCodeCycleStatusEnum.ADMIN_P_SHIPMENT.getKey().equals(status)
        || SBasicCodeCycleStatusEnum.P_Z_SHIPMENT.getKey().equals(status)
        || SBasicCodeCycleStatusEnum.P_STORAGE.getKey().equals(status)
        || SBasicCodeCycleStatusEnum.Z_STORAGE.getKey().equals(status)) {// 1-P,P,P-Z,Z
      List<SShipmentOrderEntity> orderEntityList = shipmentOrderDao.findAll(orderIds);

      for (SShipmentOrderEntity sShipmentOrderEntity : orderEntityList) {
        // 区分发货和收货的情况 现在是发货
        if (SBasicCodeCycleStatusEnum.ADMIN_P_SHIPMENT.getKey().equals(status)
            || SBasicCodeCycleStatusEnum.P_Z_SHIPMENT.getKey().equals(status)) {
          if (sShipmentOrderEntity.getStatus().equals(ShipmentState.SHIPMENT.getKey())) {
            orderStatusNow += sShipmentOrderEntity.getOrderId() + ",";
          }
        } else {
          if (sShipmentOrderEntity.getStatus().equals(ShipmentState.DELIVERY.getKey())) {
            orderStatusNow += sShipmentOrderEntity.getOrderId() + ",";
          }
        }
      }
    }// else结尾

    if (!"".equals(orderStatusNow)) {
      orderStatusNow = orderStatusNow.substring(0, orderStatusNow.length() - 1);
      barcodeException(status, SystemConst.EXCEPTION_ORDER_STATUS, orderStatusNow);
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
}
