package com.hengtiansoft.business.wrkd.order.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.common.service.SMSService;
import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.business.order.dto.OrderDetailDto;
import com.hengtiansoft.business.order.dto.OrderDetailSearchDto;
import com.hengtiansoft.business.order.dto.OrderManagerListDto;
import com.hengtiansoft.business.order.dto.OrderManagerListSearchDto;
import com.hengtiansoft.business.order.dto.OrderReturnDto;
import com.hengtiansoft.business.pay.dto.PaymentParamDto;
import com.hengtiansoft.business.pay.plugin.UnionPayPlugin;
import com.hengtiansoft.business.pay.service.WechatRefundService;
import com.hengtiansoft.business.wrkd.order.dto.KdOrderManagerExportDto;
import com.hengtiansoft.business.wrkd.order.dto.KdOrderManagerExportSearchDto;
import com.hengtiansoft.business.wrkd.order.dto.KdOrderRemarkDto;
import com.hengtiansoft.business.wrkd.order.dto.KdOrderReturnDto;
import com.hengtiansoft.business.wrkd.order.service.KdOrderManagerService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.sequence.SequenceGenerator;
import com.hengtiansoft.common.sequence.SequenceType;
import com.hengtiansoft.common.util.BasicUtil;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.ExportExcleUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.KdOrderDetailDao;
import com.hengtiansoft.wrw.dao.KdOrderMainDao;
import com.hengtiansoft.wrw.dao.KdOrderOperDao;
import com.hengtiansoft.wrw.dao.KdOrderRemarksDao;
import com.hengtiansoft.wrw.dao.KdOrderReturnDao;
import com.hengtiansoft.wrw.dao.KdProductDao;
import com.hengtiansoft.wrw.dao.KdProductStockDao;
import com.hengtiansoft.wrw.dao.KdProductStockDetailDao;
import com.hengtiansoft.wrw.dao.KdTeamBuyProductDao;
import com.hengtiansoft.wrw.dao.KdTfRecordDao;
import com.hengtiansoft.wrw.dao.KdTwentyFourHoursDao;
import com.hengtiansoft.wrw.dao.MCouponDao;
import com.hengtiansoft.wrw.dao.MVipDao;
import com.hengtiansoft.wrw.dao.SBasicParaDao;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.dao.SUserDao;
import com.hengtiansoft.wrw.entity.KdOrderDetailEntity;
import com.hengtiansoft.wrw.entity.KdOrderMainEntity;
import com.hengtiansoft.wrw.entity.KdOrderOperEntity;
import com.hengtiansoft.wrw.entity.KdOrderRemarksEntity;
import com.hengtiansoft.wrw.entity.KdOrderReturnEntity;
import com.hengtiansoft.wrw.entity.KdProductEntity;
import com.hengtiansoft.wrw.entity.KdProductStockDetailEntity;
import com.hengtiansoft.wrw.entity.KdProductStockEntity;
import com.hengtiansoft.wrw.entity.KdTeamBuyProductEntity;
import com.hengtiansoft.wrw.entity.KdTfRecordEntity;
import com.hengtiansoft.wrw.entity.KdTwentyFourHoursEntity;
import com.hengtiansoft.wrw.entity.MVipOrderEntity;
import com.hengtiansoft.wrw.entity.SBasicParaEntity;
import com.hengtiansoft.wrw.entity.SUserEntity;
import com.hengtiansoft.wrw.enums.KdOrderOperTypeEnum;
import com.hengtiansoft.wrw.enums.KdOrderStatusEnum;
import com.hengtiansoft.wrw.enums.KdOrderTypeEnum;
import com.hengtiansoft.wrw.enums.KdPImageEnum;
import com.hengtiansoft.wrw.enums.KdPayTypeEnum;
import com.hengtiansoft.wrw.enums.MessageModelEnum;
import com.hengtiansoft.wrw.enums.OrderStatus;
import com.hengtiansoft.wrw.enums.StatusEnum;

/**
 * Class Name: OrderManagerServiceImpl Description: 订单管理service 实现类
 * 
 * @author yiminli
 */
@Service
public class KdOrderManagerServiceImpl implements KdOrderManagerService {
    
    private static final String SPEC = "0";
    
    private static final String STOCK = "1";
    
    private static final String BARGAIN_TIME = "砍价持续时间";
    
    private static final String BARGAIN = "BARGAIN_TIME";
    
    private static final String VIP_PRICE = "VIP_PRICE";

    @Autowired
    private EntityManager EntityManager;

    @Autowired
    private SOrgDao sOrgDao;

    @Autowired
    private MCouponDao couponDao;

    @Autowired
    private SUserDao sUserDao;
    
    @Autowired
    private KdOrderMainDao kdOrderMainDao;
    
    @Autowired
    private KdOrderRemarksDao kdOrderRemarksDao;
    
    @Autowired
    private KdOrderReturnDao kdOrderReturnDao;
    
    @Autowired
    private KdOrderOperDao kdOrderOperDao;
    
    @Autowired
    private KdOrderDetailDao kdOrderDetailDao;
    
    @Autowired
    private SequenceGenerator sequenceGenerator;
    
    @Autowired
    private WechatRefundService wechatRefundService;
    
    @Autowired
    private KdProductStockDao kdProductStockDao;
    
    @Autowired
    private KdProductStockDetailDao kdProductStockDetailDao;
    
    @Autowired
    private KdProductDao kdProductDao;
    
    @Autowired
    private KdTeamBuyProductDao kdTeamBuyProductDao;
    
    @Autowired
    private KdTwentyFourHoursDao kdTwentyFourHoursDao;
    
    @Autowired
    private SMSService sMSService;
    
    @Autowired
    private KdTfRecordDao kdTfRecordDao;
    
    @Autowired
    private SBasicParaDao sBasicParaDao;
    
    @Autowired
    private MVipDao mVipDao;
    
    @Autowired
    private UnionPayPlugin unionPayPlugin;
    
    /**
     * 
     * Description: 获取订单详情列表
     *
     * @param listDto
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OrderDetailDto> findOrderDetails(OrderDetailSearchDto listDto) {
        StringBuilder sbSql = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();

        sbSql.append(" SELECT o.TYPE,o.ACTUAL_AMOUNT,od.SPEC_INFO,od.PRODUCT_ID,o.STATUS,isnull(kr.RETURN_ID) CAN_RETURN,kr.RETURN_AMOUNT,od.PRODUCT_COUNT,o.SHIP_AMOUNT,kr.RETURN_COUNT,o.ORDER_ID,od.REAL_PRICE ");
        sbSql.append(" FROM kd_order_detail od JOIN kd_order_main o ");
        sbSql.append(" ON od.order_id = o.order_id ");
        sbSql.append(" LEFT JOIN kd_order_return kr on o.order_id = kr.order_main_id AND od.PRODUCT_ID=kr.PRODUCT_ID AND od.SPEC_INFO=kr.SPEC_INFO WHERE 1=1 ");

        if (listDto.getOrderId() != null) {
            sbSql.append(" AND od.ORDER_ID =:orderId ");
            params.put("orderId", listDto.getOrderId());
        }

        Query query = EntityManager.createNativeQuery(sbSql.toString());
        QueryUtil.processParamForQuery(query, params);

        return buildDetailDtoLists(query.getResultList());
    }

    private List<OrderDetailDto> buildDetailDtoLists(List<Object[]> objs) {
        List<OrderDetailDto> dtos = new ArrayList<OrderDetailDto>();

        if (CollectionUtils.isNotEmpty(objs)) {
            OrderDetailDto dto = null;

            for (Object[] obj : objs) {
                dto = new OrderDetailDto();
                dto.setIsCanReturn(BasicUtil.objToString(obj[5]));
                String productName = "";
                String productCode = "";
                String orderId = BasicUtil.objToString(obj[10]);
                KdTfRecordEntity record = kdTfRecordDao.findByOrderID(orderId);
                if (obj[0] != null && obj[3] != null) {
                    Long productId = WRWUtil.objToLong(obj[3]);
                    String orderType = WRWUtil.objToString(obj[0]);
                    if (KdOrderTypeEnum.NORMAL.getCode().equals(orderType)) {
                        KdProductEntity product = kdProductDao.findOne(productId);
                        productName = product.getPname();
                        productCode = product.getPcode();
                    } else if (KdOrderTypeEnum.TEAM_BUY.getCode().equals(orderType)) {
                        KdTeamBuyProductEntity teamBuy = kdTeamBuyProductDao.findOne(productId);
                        productName = teamBuy.getName();
                        productCode = kdProductDao.findOne(teamBuy.getProductId()).getPcode();
                    } else {
                        KdTwentyFourHoursEntity twentyFour = kdTwentyFourHoursDao.findOne(productId);
                        productName = twentyFour.getName();
                        productCode = kdProductDao.findOne(twentyFour.getProductId()).getPcode();
                        String bargainTime = sBasicParaDao.findParaValue1ByTypeName(BARGAIN_TIME, BARGAIN);
                        if (record != null && record.getShareTime() != null) {
                            if ("1".equals(BasicUtil.objToString(obj[5]))) {
                                Long bargainTimeLong = Long.parseLong(bargainTime);
                                Date date = new Date();
                                if (bargainTime == null) {
                                    bargainTimeLong = 1440L;
                                }
                                if (date.getTime() - record.getShareTime().getTime() <= bargainTimeLong * 60 * 1000) {
                                    dto.setIsCanReturn("2");
                                }
                            }
                        }
                    }
                }
                dto.setProudctCode(productCode);
                dto.setProudctName(productName);
                Long vipPrice = 0L;
                List<MVipOrderEntity> vipOrderList = mVipDao.findByOrderMainId(orderId);
                if (!vipOrderList.isEmpty()) {
                    SBasicParaEntity data = sBasicParaDao.findByParaName(VIP_PRICE);
                    if (data != null) {
                        vipPrice = Long.parseLong(data.getParaValue1());
                    }
                }
                Integer sumPrice = kdOrderReturnDao.findSumByOrderId(BasicUtil.objToString(obj[10]));
                Long price = BasicUtil.objToLong(obj[1]) - vipPrice - (sumPrice == null ? 0L : sumPrice.longValue()) - (record == null || record.getReturnMoney() == null ? 0L : record.getReturnMoney());
                dto.setRestPrice(WRWUtil.transFenToYuan2StringSimple(price));
                dto.setSinglePrice(WRWUtil.transFenToYuan2StringSimple(BasicUtil.objToLong(obj[11])));
                dto.setSpecInfo(BasicUtil.objToString(obj[2]));
                dto.setProductId(BasicUtil.objToLong(obj[3]));
                dto.setOrderStatus(BasicUtil.objToString(obj[4]));
                dto.setReturnPrice(WRWUtil.transFenToYuan2String(BasicUtil.objToLong(obj[6])));
                dto.setProductCount(Integer.parseInt(WRWUtil.objToString(obj[7])));
                dto.setShipAmount(BasicUtil.objToLong(obj[8]));
                dto.setReturnCount(obj[9] == null ? "0" : BasicUtil.objToString(obj[9]));
                dtos.add(dto);
            }
        }

        return dtos;
    }

    /**
     * 
     * Description: 获取订单列表
     *
     * @param dto
     */
    @SuppressWarnings("unchecked")
    @Override
    public void search(OrderManagerListSearchDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder conditionSql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");
        StringBuilder sql = new StringBuilder(
                "SELECT * FROM ("
                        + " SELECT O.ORDER_ID,O.CREATE_DATE,O.PHONE, "
                        + " CONCAT(',', GROUP_CONCAT(O.TYPE SEPARATOR ','),',') TYPE_ID,"
                        + " SUM(OD.REAL_PRICE),O.STATUS,O.TYPE,GROUP_CONCAT(OD.PRODUCT_ID SEPARATOR ','),KR.SHARE_TIME,VIP.ID "
                        + " FROM KD_ORDER_MAIN O LEFT JOIN KD_ORDER_DETAIL OD ON O.ORDER_ID = OD.ORDER_ID "
                        + " LEFT JOIN KD_TF_RECORD KR ON O.ORDER_ID = KR.ORDER_ID "
                        + " LEFT JOIN M_VIP_ORDER VIP ON O.ORDER_ID = VIP.ORDER_MAIN_ID "
                        + " WHERE 1=1 ");
        countSql.append("select count(1) from (").append(sql);
        // 创建时间
        if (!WRWUtil.isEmpty(dto.getStartTime())) {
            conditionSql.append("AND O.CREATE_DATE >= :START ");
            param.put("START", DateTimeUtil.getDateBegin(dto.getStartTime()));
        }
        if (!WRWUtil.isEmpty(dto.getEndDate())) {
            conditionSql.append("AND O.CREATE_DATE <= :END ");
            param.put("END", DateTimeUtil.getDateEnd(dto.getEndDate()));
        }
        // 订单号
        if (!WRWUtil.isEmpty(dto.getOrderId())) {
            String msg = "%" + dto.getOrderId() + "%";
            conditionSql.append("AND O.ORDER_ID LIKE :ORDER_ID ");
            param.put("ORDER_ID", msg);
        }
        // 支付状态
        if (!WRWUtil.isEmpty(dto.getStatus())) {
            conditionSql.append("AND O.STATUS = :STATUS ");
            param.put("STATUS", dto.getStatus());
        }
        // 是否开通vip
        if (!WRWUtil.isEmpty(dto.getVip())) {
            conditionSql.append(" AND VIP.ID IS ");
            if ("0".equals(dto.getVip())) {
                conditionSql.append(" NULL ");
            } else {
                conditionSql.append(" NOT NULL ");
            }
        }
        conditionSql.append("GROUP BY O.ORDER_ID ");
        conditionSql.append("ORDER BY O.CREATE_DATE DESC) ORD WHERE 1=1 ");
        // 手机号
        if (!WRWUtil.isEmpty(dto.getPhone())) {
            if (!validateNum(dto.getPhone())) {
                return;
            }
            String msg = "%" + dto.getPhone() + "%";
            conditionSql.append("AND ORD.PHONE LIKE :PHONE ");
            param.put("PHONE", msg);
        }
        // 商品类型
        if (!WRWUtil.isEmpty(dto.getTypeId())) {
            String msg = "%," + dto.getTypeId() + ",%";
            conditionSql.append("AND (ORD.TYPE_ID LIKE :TYPE ");
            if (KdOrderTypeEnum.NORMAL.getCode().equals(dto.getTypeId())) {
                conditionSql.append(" OR (ORD.TYPE_ID LIKE '%3%' ");
                conditionSql.append(" AND ORD.SHARE_TIME IS NULL)) ");
            } else if (KdOrderTypeEnum.TWENTY_FOUR.getCode().equals(dto.getTypeId())) {
                conditionSql.append(" AND ORD.SHARE_TIME IS NOT NULL) ");
            } else {
                conditionSql.append(") ");
            }
            param.put("TYPE", msg);
        }
        Query query = EntityManager.createNativeQuery(sql.append(conditionSql).toString());
        QueryUtil.processParamForQuery(query, param);
        Query countQuery = EntityManager.createNativeQuery(countSql.append(conditionSql).append(" ) A").toString());
        QueryUtil.processParamForQuery(countQuery, param);
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        dto.setTotalRecord(totalRecord.longValue());
        dto.setTotalPages(totalRecord.intValue() % dto.getPageSize() == 0 ? totalRecord.intValue() / dto.getPageSize()
                : ((totalRecord.intValue() / dto.getPageSize()) + 1));
        dto.setList(buildOrderManagerListDtos(query.getResultList()));

    }

    private List<OrderManagerListDto> buildOrderManagerListDtos(List<Object[]> list) {
        List<OrderManagerListDto> dtos = new ArrayList<OrderManagerListDto>();
        for (Object[] array : list) {
            OrderManagerListDto dto = new OrderManagerListDto();
            dto.setOrderId(WRWUtil.objToString(array[0]));
            dto.setCreateDate(DateTimeUtil.parseDate(WRWUtil.objToString(array[1]), DateTimeUtil.SIMPLE_FMT));
            dto.setPhone(WRWUtil.objToString(array[2]));
            
            String types = WRWUtil.objToString(array[3]);
            if (StringUtils.isNotEmpty(types)) {
                String[] typesIds = types.split(",");
                StringBuilder productType = new StringBuilder("");
                for (String typesId : typesIds) {
                    if (!typesId.equals("")) {
                        if (!KdOrderTypeEnum.TWENTY_FOUR.getCode().equals(typesId)) {
                            productType.append(KdOrderTypeEnum.getText(WRWUtil.objToString(typesId)) + "/"); 
                        } else {
                            KdTfRecordEntity record = kdTfRecordDao.findByOrderID(WRWUtil.objToString(array[0]));
                            if (record != null && record.getShareTime() != null) {
                                productType.append(KdOrderTypeEnum.TWENTY_FOUR.getText() + "/");
                            } else {
                                productType.append(KdOrderTypeEnum.NORMAL.getText() + "/");
                            }
                        }
                    }
                }
                dto.setProductType(productType.toString());
            } else {
                dto.setProductType("/");
            }

            dto.setAmount(WRWUtil.transFenToYuan2String(array[4] == null ? 0L : WRWUtil.objToLong(array[4])));

            if (array[5] != null) {
                dto.setStatus(KdOrderStatusEnum.getText(WRWUtil.objToString(array[5])));
            }
            
            if (array[6] != null && array[7] != null) {
                String[] productIds = WRWUtil.objToString(array[7]).split(",");
                String productName = "";
                if (KdOrderTypeEnum.NORMAL.getCode().equals(WRWUtil.objToString(array[6]))) {
                    for (int i = 0; i < productIds.length; i++) {
                        if (i == productIds.length - 1) {
                            productName += kdProductDao.findOne(Long.parseLong(productIds[i])).getPname();
                        } else {
                            productName += kdProductDao.findOne(Long.parseLong(productIds[i])).getPname() + "#&#";
                        }
                    }
                } else if (KdOrderTypeEnum.TEAM_BUY.getCode().equals(WRWUtil.objToString(array[6]))) {
                    for (int i = 0; i < productIds.length; i++) {
                        if (i == productIds.length - 1) {
                            productName += kdTeamBuyProductDao.findOne(Long.parseLong(productIds[i])).getName();
                        } else {
                            productName += kdTeamBuyProductDao.findOne(Long.parseLong(productIds[i])).getName() + "#&#";
                        }
                    }
                } else {
                    for (int i = 0; i < productIds.length; i++) {
                        if (i == productIds.length - 1) {
                            KdTwentyFourHoursEntity tf = kdTwentyFourHoursDao.findOne(Long.parseLong(productIds[i]));
                            if(tf != null){
                                productName += tf.getName();
                            }
                        } else {
                            KdTwentyFourHoursEntity tf = kdTwentyFourHoursDao.findOne(Long.parseLong(productIds[i]));
                            if(tf != null){
                                productName += tf.getName() + "#&#";
                            }
                        }
                    }
                }
                dto.setProductName(productName);
            }
            
            dto.setBuyVip("否");
            if (array[9] != null) {
                dto.setBuyVip("是");
            }
            
            dtos.add(dto);
        }
        return dtos;
    }

    private boolean validateNum(String str) {
        String reg = "^[-+]?[0-9]+(\\.[0-9]+)?$";
        return str.matches(reg);
    }

    /**
     * 
     * Description: 导出列表
     *
     * @param pageDto
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public HSSFWorkbook toExcle(KdOrderManagerExportSearchDto pageDto) {
        ArrayList<String> fieldName = new ArrayList<String>();
        ArrayList<String> sheetName = new ArrayList<String>();
        ArrayList<String> fieldStyle = new ArrayList<String>();
        List<List<Object>> fieldData = new ArrayList<List<Object>>();
        ArrayList<Integer> orderNum = new ArrayList<Integer>();
        fieldName.add(0, "订单编号");
        fieldName.add(1, "订单创建时间");
        fieldName.add(2, "订单状态");
        fieldName.add(3, "买家注册手机号");
        fieldName.add(4, "商品名");
        fieldName.add(5, "单价（元）");
        fieldName.add(6, "数量");
        fieldName.add(7, "规格");
        fieldName.add(8, "订单类型");
        fieldName.add(9, "实付金额（元）");
        fieldName.add(10, "退款金额（元）");
        fieldName.add(11, "收货地址");
        fieldName.add(12, "收货人");
        fieldName.add(13, "是否开通VIP");

        for (int j = 0; j <= fieldName.size(); j++) {
            fieldStyle.add(j, "6000");
        }
        pageDto.setPageSize(65536);
        this.exportSearch(pageDto);
        if (pageDto.getList().size() > 0) {
            for (int i = 0; i < pageDto.getList().size(); i++) {
                KdOrderManagerExportDto dto = pageDto.getList().get(i);
                String[] productName = dto.getProductNames().split("#&#");
                String[] productPrice = dto.getProductPrices().split("#&#");
                String[] productCount = dto.getProductCounts().split("#&#");
                String[] specInfos = dto.getProductSpecs().split("#&#");
                String[] returnAmounts = dto.getReturnAmount().split("#&#");
                Integer rowNumber = productName.length;
                orderNum.add(rowNumber);
                for (int j = 0; j < rowNumber; j++) {
                    List<Object> rowData = new ArrayList<Object>();
                    if (j == 0) {
                        rowData.add(0, dto.getOrderId());
                        rowData.add(1, dto.getCreateDate());
                        rowData.add(2, dto.getStatus());
                        rowData.add(3, dto.getPhone());
                        if (j <productName.length) {
                            if (!productName[j].equals("")) {
                                rowData.add(4, productName[j]);
                            }else {
                                rowData.add(4,"");
                            }
                        }else {
                            rowData.add(4,"");
                        }
                        
                        if (j <productPrice.length) {
                            if (!productPrice[j].equals("")) {
                                rowData.add(5, productPrice[j]);
                            }else {
                                rowData.add(5,"");
                            }
                        }else {
                            rowData.add(5,"");
                        }
                        
                        if (j <productCount.length) {
                            if (!productCount[j].equals("")) {
                                rowData.add(6, productCount[j]);
                            }else {
                                rowData.add(6,"");
                            }
                        }else {
                            rowData.add(6,"");
                        }

                        if (j <specInfos.length) {
                            if (!specInfos[j].equals("")) {
                                JSONObject jsonObject = JSONObject.fromObject(specInfos[j]);
                                Map<String, String> map = (Map) jsonObject;
                                String specInfo = "";
                                for (String key : map.keySet()) {
                                    String mainSpec = key;
                                    String subSpec = map.get(key);
                                    specInfo += mainSpec + ":" + subSpec + ";";
                                }
                                rowData.add(7, specInfo.substring(0, specInfo.length() - 1));
                            }else {
                                rowData.add(7,"");
                            }
                        }else {
                            rowData.add(7,"");
                        }
                        
                        rowData.add(8, dto.getOrderType());
                        rowData.add(9, dto.getActualAmount());
                        
                        if (j < returnAmounts.length) {
                            if (!returnAmounts[j].equals("") && !returnAmounts[j].equals("0")) {
                                rowData.add(10, returnAmounts[j]);
                            } else {
                                rowData.add(10, "");
                            }
                        } else {
                            rowData.add(10, "");
                        }
                        
                        rowData.add(11, dto.getAddressInfo());
                        rowData.add(12, dto.getContact());
                        rowData.add(13, dto.getBuyVip());
                        
                        fieldData.add(rowData);
                    } else {
                        rowData.add(0, "");
                        rowData.add(1, "");
                        rowData.add(2, "");
                        rowData.add(3, "");
                        
                        if (j <productName.length) {
                            if (!productName[j].equals("")) {
                                rowData.add(4, productName[j]);
                            }else {
                                rowData.add(4,"");
                            }
                        }else {
                            rowData.add(4,"");
                        }
                        
                        if (j <productPrice.length) {
                            if (!productPrice[j].equals("")) {
                                rowData.add(5, productPrice[j]);
                            }else {
                                rowData.add(5,"");
                            }
                        }else {
                            rowData.add(5,"");
                        }
                        
                        if (j <productCount.length) {
                            if (!productCount[j].equals("")) {
                                rowData.add(6, productCount[j]);
                            }else {
                                rowData.add(6,"");
                            }
                        }else {
                            rowData.add(6,"");
                        }

                        if (j <specInfos.length) {
                            if (!specInfos[j].equals("")) {
                                JSONObject jsonObject = JSONObject.fromObject(specInfos[j]);
                                Map<String, String> map = (Map) jsonObject;
                                String specInfo = "";
                                for (String key : map.keySet()) {
                                    String mainSpec = key;
                                    String subSpec = map.get(key);
                                    specInfo += mainSpec + ":" + subSpec + ";";
                                }
                                rowData.add(7, specInfo.substring(0, specInfo.length() - 1));
                            }else {
                                rowData.add(7,"");
                            }
                        }else {
                            rowData.add(7,"");
                        }

                        rowData.add(8, "");
                        rowData.add(9, "");
                        
                        if (j < returnAmounts.length) {
                            if (!returnAmounts[j].equals("") && !returnAmounts[j].equals("0")) {
                                rowData.add(10, returnAmounts[j]);
                            } else {
                                rowData.add(10, "");
                            }
                        } else {
                            rowData.add(10, "");
                        }
                        
                        rowData.add(11, "");
                        rowData.add(12, "");
                        rowData.add(13, "");
                        
                        fieldData.add(rowData);
                    }
                }
            }
        } else {
            List<Object> rowData = new ArrayList<Object>();
            fieldData.add(rowData);
        }
        sheetName.add(0, "订单列表");
        ExportExcleUtil ex = new ExportExcleUtil(fieldName, sheetName, fieldData, fieldStyle);
        HSSFWorkbook wb = ex.createWorkbook();
        Integer sum = 1;
        if (CollectionUtils.isNotEmpty(orderNum)) {
            for (Integer num : orderNum) {
                for (int i = 0; i <= 3; i++) {
                    wb.getSheetAt(0).addMergedRegion(
                            new org.apache.poi.ss.util.CellRangeAddress(sum, sum + num - 1, i, i));
                }
                for (int i = 8; i <= 9; i++) {
                    wb.getSheetAt(0).addMergedRegion(
                            new org.apache.poi.ss.util.CellRangeAddress(sum, sum + num - 1, i, i));
                }
                sum += num;
            }
        }
        return wb;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void exportSearch(KdOrderManagerExportSearchDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder conditionSql = new StringBuilder("");
        StringBuilder sql = new StringBuilder(
                "SELECT * FROM "
                + " (SELECT KO.ORDER_ID,KO.CREATE_DATE,KO.STATUS,KO.PHONE,GROUP_CONCAT(KD.PRODUCT_ID SEPARATOR '#&#') PRODUCT_IDS,"
                + " GROUP_CONCAT(KD.REAL_PRICE SEPARATOR '#&#') PRODUCT_PRICE,GROUP_CONCAT(KD.PRODUCT_COUNT SEPARATOR '#&#') PRODUCT_COUNT,"
                + " GROUP_CONCAT(KD.SPEC_INFO SEPARATOR '#&#') PRODUCT_SPEC_INFO,KO.`TYPE`,KO.ACTUAL_AMOUNT,GROUP_CONCAT(IFNULL(KR.RETURN_AMOUNT,0) SEPARATOR '#&#') PRODUCT_RETURN,"
                + " KO.ADDRESS_INFO,KO.CONTACT,VIP.ID FROM "
                + " KD_ORDER_MAIN KO LEFT JOIN KD_ORDER_DETAIL KD ON KO.ORDER_ID = KD.ORDER_ID LEFT JOIN KD_ORDER_RETURN KR ON KO.ORDER_ID = KR.ORDER_MAIN_ID AND KD.PRODUCT_ID = KR.PRODUCT_ID "
                + " LEFT JOIN KD_TF_RECORD KTR ON KO.ORDER_ID = KTR.ORDER_ID "
                + " AND KD.SPEC_INFO = KR.SPEC_INFO "
                + " LEFT JOIN M_VIP_ORDER VIP ON KO.ORDER_ID = VIP.ORDER_MAIN_ID "
                + " WHERE 1=1 ");
        if (WRWUtil.isEmpty(dto.getOrderIds())) {
            // 创建时间
            if (WRWUtil.isNotEmpty(dto.getStartTime())) {
                conditionSql.append(" AND KO.CREATE_DATE >= :START ");
                param.put("START", DateTimeUtil.getDateBegin(dto.getStartTime()));
            }
            if (WRWUtil.isNotEmpty(dto.getEndDate())) {
                conditionSql.append(" AND KO.CREATE_DATE <= :END ");
                param.put("END", DateTimeUtil.getDateEnd(dto.getEndDate()));
            }
            // 订单号
            if (WRWUtil.isNotEmpty(dto.getOrderId())) {
                String msg = dto.getOrderId().trim();
                char escape = '\\';
                msg =msg.replace("\\", escape+"\\");
                msg =msg.replace("%", escape+"%");
                msg =msg.replace("_", escape+"_");
                conditionSql.append(" AND REPLACE(KO.ORDER_ID,' ','') LIKE REPLACE(:ORDER_ID,' ','') ");
                param.put("ORDER_ID", "%"+msg+"%");   
            }
            // 手机号
            if (WRWUtil.isNotEmpty(dto.getPhone())) {
                String msg = dto.getPhone().trim();
                char escape = '\\';
                msg =msg.replace("\\", escape+"\\");
                msg =msg.replace("%", escape+"%");
                msg =msg.replace("_", escape+"_");
                conditionSql.append(" AND REPLACE(KO.PHONE,' ','') LIKE REPLACE(:PHONE,' ','') ");
                param.put("PHONE", "%"+msg+"%"); 
            }
            // 订单类型
            if (WRWUtil.isNotEmpty(dto.getTypeId())) {
                conditionSql.append(" AND (KO.TYPE = :TYPE");
                if (KdOrderTypeEnum.NORMAL.getCode().equals(dto.getTypeId())) {
                    conditionSql.append(" OR (KO.TYPE = '3' ");
                    conditionSql.append(" AND KTR.SHARE_TIME IS NULL)) ");
                } else if (KdOrderTypeEnum.TWENTY_FOUR.getCode().equals(dto.getTypeId())) {
                    conditionSql.append(" AND KTR.SHARE_TIME IS NOT NULL) ");
                } else {
                    conditionSql.append(") ");
                }
                param.put("TYPE", dto.getTypeId());
            }
            // 订单状态
            if (WRWUtil.isNotEmpty(dto.getStatus())) {
                conditionSql.append("AND KO.STATUS = :STATUS ");
                param.put("STATUS", dto.getStatus());
            }
            // 是否开通VIP
            if (WRWUtil.isNotEmpty(dto.getVip())) {
                conditionSql.append(" AND VIP.ID IS ");
                if ("0".equals(dto.getVip())) {
                    conditionSql.append(" NULL ");
                } else {
                    conditionSql.append(" NOT NULL ");
                }
            }
            
        }else {
            String[] orderIds = dto.getOrderIds().split(",");
            List<String> orderIdList = new ArrayList<String>();
            Collections.addAll(orderIdList, orderIds);
            conditionSql.append(" AND KO.ORDER_ID IN :ORDER_IDS ");
            param.put("ORDER_IDS", orderIdList);
        }
        conditionSql.append(" GROUP BY KO.ORDER_ID ");
        conditionSql.append(" ) A ORDER BY A.CREATE_DATE DESC ");
        Query query = EntityManager.createNativeQuery(sql.append(conditionSql).toString());
        QueryUtil.processParamForQuery(query, param);
        dto.setList(buildOrderManagerExportDtos(query.getResultList()));
    }

    private List<KdOrderManagerExportDto> buildOrderManagerExportDtos(List<Object[]> list) {
        List<KdOrderManagerExportDto> dtos = new ArrayList<KdOrderManagerExportDto>();
        for (Object[] array : list) {
            KdOrderManagerExportDto dto = new KdOrderManagerExportDto();
            dto.setOrderId(WRWUtil.objToString(array[0]));
            if (array[1] != null) {
                String createDate = array[1].toString();
                Date date = DateTimeUtil.dateParse(createDate);
                dto.setCreateDate(DateTimeUtil.dateFormat(date));
            }

            if (array[2] != null) {
                dto.setStatus(KdOrderStatusEnum.getText(WRWUtil.objToString(array[2])));
            }

            dto.setPhone(WRWUtil.objToString(array[3]));
            String orderType = WRWUtil.objToString(array[8]);
            
            if (array[4] != null) {
                String productNames = "";
                String[] productIdArray = WRWUtil.objToString(array[4]).split("#&#");
                if (KdOrderTypeEnum.NORMAL.getCode().equals(orderType)) {
                    for (int i = 0; i < productIdArray.length; i++) {
                        if (i == productIdArray.length - 1) {
                            productNames += kdProductDao.findOne(Long.parseLong(productIdArray[i])).getPname();
                        } else {
                            productNames += kdProductDao.findOne(Long.parseLong(productIdArray[i])).getPname() + "#&#";
                        }
                    }
                } else if(KdOrderTypeEnum.TEAM_BUY.getCode().equals(orderType)){
                    for (int i = 0; i < productIdArray.length; i++) {
                        if (i == productIdArray.length - 1) {
                            productNames += kdTeamBuyProductDao.findOne(Long.parseLong(productIdArray[i])).getName();
                        } else {
                            productNames += kdTeamBuyProductDao.findOne(Long.parseLong(productIdArray[i])).getName() + "#&#";
                        }
                    }
                } else {
                    for (int i = 0; i < productIdArray.length; i++) {
                        if (i == productIdArray.length - 1) {
                            productNames += kdTwentyFourHoursDao.findOne(Long.parseLong(productIdArray[i])).getName();
                        } else {
                            productNames += kdTwentyFourHoursDao.findOne(Long.parseLong(productIdArray[i])).getName() + "#&#";
                        }
                    }
                }
                dto.setProductNames(productNames);
            }
            
            if (array[5] != null) {
                String[] productPriceArray = WRWUtil.objToString(array[5]).split("#&#");
                String productPrices = "";
                for (int i = 0; i < productPriceArray.length; i++) {
                    if (i == productPriceArray.length - 1) {
                        productPrices += WRWUtil.transFenToYuan2StringSimple(Long.parseLong(productPriceArray[i]));
                    } else {
                        productPrices += WRWUtil.transFenToYuan2StringSimple(Long.parseLong(productPriceArray[i])) + "#&#";
                    }
                }
                dto.setProductPrices(productPrices);
            }
            dto.setProductCounts(WRWUtil.objToString(array[6]));
            dto.setProductSpecs(WRWUtil.objToString(array[7]));
            
            KdTfRecordEntity record = kdTfRecordDao.findByOrderID(WRWUtil.objToString(array[0]));
            if (record == null) {
                if (KdOrderTypeEnum.TWENTY_FOUR.getCode().equals(orderType)) {
                    dto.setOrderType(KdOrderTypeEnum.NORMAL.getText());
                } else {
                    dto.setOrderType(KdOrderTypeEnum.getText(orderType));
                }
            } else {
                if (record.getShareTime() == null) {
                    dto.setOrderType(KdOrderTypeEnum.NORMAL.getText());
                } else {
                    dto.setOrderType(KdOrderTypeEnum.TWENTY_FOUR.getText());
                }
            }
            
            if (array[9] != null) {
                String actualAmount = WRWUtil.objToString(array[9]);
                dto.setActualAmount(WRWUtil.transFenToYuan2StringSimple(Long.parseLong(actualAmount)));
            }
            
            if (array[10] != null) {
                String[] productReturnAmount = WRWUtil.objToString(array[10]).split("#&#");
                String productReturnAmounts = "";
                for (int i = 0; i < productReturnAmount.length; i++) {
                    if (i == productReturnAmount.length - 1) {
                        productReturnAmounts += WRWUtil.transFenToYuan2StringSimple(Long.parseLong(productReturnAmount[i]));
                    } else {
                        productReturnAmounts += WRWUtil.transFenToYuan2StringSimple(Long.parseLong(productReturnAmount[i])) + "#&#";
                    }
                }
                dto.setReturnAmount(productReturnAmounts);
            }
            
            if (array[11] != null) {
                // 收货地址
                dto.setAddressInfo(WRWUtil.objToString(array[11]));
            }
            
            if (array[12] != null) {
                // 收货人
                dto.setContact(WRWUtil.objToString(array[12]));
            }
            
            dto.setBuyVip("否");
            if (array[13] != null) {
                dto.setBuyVip("是");
            }
            
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public KdOrderReturnDto getOrderDetail(String orderId) {
        List<Object[]> objects = kdOrderMainDao.findInfoByOrderId(orderId);
        KdOrderReturnDto dto = new KdOrderReturnDto();
        if (objects.isEmpty()) {
            throw new WRWException("该订单编号不存在");
        }
        
        Object[] object = objects.get(0);
        dto.setOrderId(orderId);
        dto.setTotalAmount(WRWUtil.transFenToYuan2String(kdOrderDetailDao.findTotalAmountByOrderId(orderId).longValue()));
        dto.setActualAmount(WRWUtil.transFenToYuan2String(WRWUtil.objToLong(object[2])));
        dto.setStatus(KdOrderStatusEnum.getText(WRWUtil.objToString(object[3])));
        dto.setPhone(WRWUtil.objToString(object[4]));
        dto.setShipAmount(WRWUtil.objToLong(object[5]).equals(0L) ? "免运费" : WRWUtil.transFenToYuan2String(WRWUtil.objToLong(object[5])));
        dto.setCreateDate(DateTimeUtil.parseDateToString((Date) object[6], DateTimeUtil.SIMPLE_M_D));
        KdTfRecordEntity record = kdTfRecordDao.findByOrderID(orderId);
        if (record == null) {
            if (KdOrderTypeEnum.TWENTY_FOUR.getCode().equals(WRWUtil.objToString(object[7]))) {
                dto.setOrderType(KdOrderTypeEnum.NORMAL.getText());
                dto.setOrderTypeCode(KdOrderTypeEnum.TWENTY_FOUR.getCode());
            } else {
                dto.setOrderType(KdOrderTypeEnum.getText(WRWUtil.objToString(object[7])));
                dto.setOrderTypeCode(WRWUtil.objToString(object[7]));
            }
        } else {
            if (record.getShareTime() == null) {
                dto.setOrderType(KdOrderTypeEnum.NORMAL.getText());
            } else {
                dto.setOrderType(KdOrderTypeEnum.TWENTY_FOUR.getText());
            }
            dto.setOrderTypeCode(KdOrderTypeEnum.TWENTY_FOUR.getCode());
        }
        dto.setExpressInfo(WRWUtil.objToString(object[8]));
        dto.setAddressInfo(WRWUtil.objToString(object[9]));
        dto.setBuyVip("0");
        if (object[10] != null) {
            dto.setBuyVip("1");
        }
        dto.setRemarks(getRemarks(orderId));
        Integer returnAmount = kdOrderReturnDao.findSumByOrderId(orderId);
        if (returnAmount == null) {
            dto.setShowReturnInfo("0");
        } else {
            dto.setShowReturnInfo("1");
            dto.setReturnInfo("是，" + KdOrderStatusEnum.COMPLETE_RETURNMONEY.getText()
                    + WRWUtil.transFenToYuan2String(returnAmount.longValue()) + "元");
        }
        return dto;
    }

    @Override
    public List<KdOrderRemarkDto> getRemarks(String orderId) {
        List<KdOrderRemarkDto> dtos = new ArrayList<KdOrderRemarkDto>();
        List<KdOrderRemarksEntity> entities = kdOrderRemarksDao.findByOrderIdAndStatusOrderByCreateDateDesc(orderId, StatusEnum.NORMAL.getCode());
        Integer index = 1;
        for (KdOrderRemarksEntity entity : entities) {
            KdOrderRemarkDto dto = new KdOrderRemarkDto();
            dto.setRemark(entity.getRemark());
            dto.setRemarkDate(DateTimeUtil.parseDateToString(entity.getCreateDate(), DateTimeUtil.SIMPLE_M_D));

            SUserEntity userEntity = sUserDao.findOne(Long.valueOf(entity.getCreateId()));
            dto.setName(userEntity.getUserName());
            dto.setIndex(index++);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public KdOrderMainEntity findEntityByOrderId(String orderId) {
        return kdOrderMainDao.findByOrderId(orderId);
    }

    @Override
    @Transactional
    public String changeActualAmount(OrderReturnDto dto) {
        SUserEntity user = (SUserEntity) AuthorityContext.getCurrentUser();
        KdOrderMainEntity entity = kdOrderMainDao.findByOrderId(dto.getOrderId());

        if (null == entity) {
            throw new WRWException("该订单编号不存在");
        }
        if (!KdOrderStatusEnum.WAIT_PAY.getCode().equals(entity.getStatus())) {
            throw new WRWException("该订单状态不可改支付金额");
        }
        if (StringUtils.isEmpty(dto.getActualAmount())) {
            throw new WRWException("请输入订单金额");
        }
        if (Double.valueOf(dto.getActualAmount()) > 99999L) {
            throw new WRWException("订单金额不能大于99999元");
        }
        if (Double.valueOf(dto.getActualAmount()) <= 0D) {
            throw new WRWException("订单金额不能改为0元");
        }
        if (null != entity.getIsPrepay() && entity.getIsPrepay().equals("1")) {
            throw new WRWException("支付请求已提交，无法修改订单信息");
        }
        if (StringUtils.isBlank(dto.getRemark())) {
            throw new WRWException("备注不能为空");
        }
        if (dto.getRemark().length() > 150) {
            throw new WRWException("备注内容不可超过150字");
        }
        addRemarkAndOper(dto, user);
        entity.setActualAmount(Double.valueOf(Double.valueOf(dto.getActualAmount()) * 100L).longValue());
        entity.setModifyId(user.getUserId());
        entity.setModifyDate(new Date());
        kdOrderMainDao.save(entity);
        return "改价成功";
    }

    @Override
    @Transactional
    public String cancelOrder(OrderReturnDto dto) {
        SUserEntity user = (SUserEntity) AuthorityContext.getCurrentUser();
        KdOrderMainEntity entity = kdOrderMainDao.findByOrderId(dto.getOrderId());

        if (null == entity) {
            throw new WRWException("该订单编号不存在");
        }
        if (!OrderStatus.WAIT_PAY.getKey().equals(entity.getStatus())) {
            throw new WRWException("该订单状态不可取消订单");
        }
        if (StringUtils.isBlank(dto.getRemark())) {
            throw new WRWException("备注不能为空");
        }
        if (dto.getRemark().length() > 150) {
            throw new WRWException("备注内容不可超过150字");
        }
        // 释放库存
        List<KdOrderDetailEntity> detailList = kdOrderDetailDao.findByOrderId(dto.getOrderId());
        for (KdOrderDetailEntity detail : detailList) {
            Integer count = detail.getProductCount();
            String specGroup = buildStockSpec(detail.getSpecInfo());
            addStockCount(detail.getProductId(), count, user.getUserId(), specGroup, dto.getOrderType());
        }
        addRemarkAndOper(dto, user);
        entity.setStatus(KdOrderStatusEnum.CANCELED.getCode());
        entity.setModifyId(user.getUserId());
        entity.setModifyDate(new Date());
        kdOrderMainDao.save(entity);
        return "取消订单成功";
    }
    
    

    @Override
    @Transactional
    public String addRemark(OrderReturnDto dto) {
        SUserEntity user = (SUserEntity) AuthorityContext.getCurrentUser();
        KdOrderMainEntity orderEntity = kdOrderMainDao.findByOrderId(dto.getOrderId());
        if (null == orderEntity) {
            throw new WRWException("该订单编号不存在");
        }
        if (StringUtils.isBlank(dto.getRemark())) {
            throw new WRWException("备注不能为空");
        }
        if (dto.getRemark().length() > 150) {
            throw new WRWException("备注内容不可超过150字");
        }
        KdOrderRemarksEntity entity = new KdOrderRemarksEntity();
        entity.setRemark(dto.getRemark());
        entity.setOrderId(dto.getOrderId());
        entity.setCreateDate(new Date());
        entity.setCreateId(user.getUserId());
        entity.setStatus(StatusEnum.NORMAL.getCode());
        kdOrderRemarksDao.save(entity);
        return "追加成功";
    }

    @Override
    public void addRemarkAndOper(OrderReturnDto dto, SUserEntity user) {
        // 保存备注
        KdOrderRemarksEntity remarkEntity = new KdOrderRemarksEntity();
        remarkEntity.setRemark(dto.getRemark());
        remarkEntity.setOrderId(dto.getOrderId());
        remarkEntity.setCreateDate(new Date());
        remarkEntity.setCreateId(user.getUserId());
        remarkEntity.setStatus(StatusEnum.NORMAL.getCode());
        kdOrderRemarksDao.save(remarkEntity);
        
        // 保存操作记录
        KdOrderOperEntity operEntity = new KdOrderOperEntity();
        operEntity.setOrderId(dto.getOrderId());
        operEntity.setOperType(dto.getOperType());
        operEntity.setCreateDate(new Date());
        operEntity.setCreateId(user.getUserId());
        kdOrderOperDao.save(operEntity);
    }

    @Override
    @Transactional
    public String returnAmount(OrderReturnDto dto) {
        SUserEntity user = (SUserEntity) AuthorityContext.getCurrentUser();
        KdOrderMainEntity entity = kdOrderMainDao.findByOrderId(dto.getOrderId());
        if (null == entity) {
            throw new WRWException("该订单编号不存在");
        }
        if (WRWUtil.isEmpty(dto.getReturnOrderAmount())) {
            throw new WRWException("退款金额不能为空");
        }
        Long reqReturnAmount = Double.valueOf(Double.valueOf(dto.getReturnOrderAmount()) * 100L).longValue();
        KdOrderDetailEntity detailEntity = kdOrderDetailDao.findDetail(dto.getOrderId(), dto.getProductId());
        Integer sumPrice = kdOrderReturnDao.findSumByOrderId(dto.getOrderId());
        Long price = entity.getActualAmount() - (sumPrice == null ? 0L : sumPrice.longValue());
        if (reqReturnAmount > price) {
            throw new WRWException("退款金额不能大于实付金额");
        }
        if (Double.valueOf(dto.getReturnOrderAmount()) <= 0D) {
            throw new WRWException("退款金额不能小于等于0元");
        }
        if (!KdOrderStatusEnum.WAIT_PAY.equals(entity.getStatus())) {
            String result = "";
            if (KdPayTypeEnum.WECHAT.getKey().equals(entity.getPaymentType())) {
                result = wechatRefundService.wechatRefund(String.valueOf(entity.getActualAmount()),
                        String.valueOf(reqReturnAmount), entity.getOrderId(), entity.getTsn());
            } else if (KdPayTypeEnum.UNION.getKey().equals(entity.getPaymentType())) {
                String orderId = entity.getOrderId() + DateTimeUtil.getCurrentTime(DateTimeUtil.SIMPLE_SECONDS);
                PaymentParamDto paramDto = new PaymentParamDto(orderId, String.valueOf(reqReturnAmount), entity.getTsn());
                result = unionPayPlugin.refund(paramDto).getCode();
            }
            if (result.equalsIgnoreCase("SUCCESS") || result.equalsIgnoreCase("ACK")) {
                KdOrderReturnEntity returnEntity = new KdOrderReturnEntity();
                returnEntity.setOrderMainId(dto.getOrderId());
                returnEntity.setReturnId(sequenceGenerator.getOrderId(SequenceType.M_KD_RETURN));
                returnEntity.setReturnAmount(reqReturnAmount);
                returnEntity.setOrderStatus(dto.getStatus());
                returnEntity.setProductId(dto.getProductId());
                returnEntity.setCreateDate(new Date());
                returnEntity.setCreateId(user.getUserId());
                returnEntity.setType(dto.getOrderType());
                returnEntity.setReturnCount(dto.getReturnCount().longValue());
                returnEntity.setSpecInfo(dto.getSpecInfo());
                kdOrderReturnDao.save(returnEntity);
                if (isAllReturn(dto.getOrderId())) {
                    entity.setStatus(KdOrderStatusEnum.COMPLETE_RETURNMONEY.getCode());
                    kdOrderMainDao.save(entity);
                }
                addRemarkAndOper(dto, user);
                String specGroup = "";
                if (dto.getReturnCount() > 0) {
                    specGroup = buildStockSpec(dto.getSpecInfo());
                    addStockCount(detailEntity.getProductId(), dto.getReturnCount(), user.getUserId(), specGroup, dto.getOrderType());
                }
                // 发短信 
                HashMap<String, String> dataMap = new HashMap<String, String>();
                dataMap.put("amount", dto.getReturnOrderAmount());
                dataMap.put("day", "5");
                String productName = "";
                if (KdOrderTypeEnum.NORMAL.getCode().equals(detailEntity.getType())) {
                    productName += kdProductDao.findOne(detailEntity.getProductId()).getPname() + " : " + specGroup;
                    dataMap.put("productName", productName);
                    sMSService.sendSMS(entity.getPhone(), MessageModelEnum.sms_C_KdProductRefundNotify, dataMap);
                } else if (KdOrderTypeEnum.TEAM_BUY.getCode().equals(detailEntity.getType())) {
                    productName += kdTeamBuyProductDao.findOne(detailEntity.getProductId()).getName() + " : " + specGroup;
                    dataMap.put("productName", productName);
                    dataMap.put("actType", KdPImageEnum.TEAMBUY.getDesc());
                    sMSService.sendSMS(entity.getPhone(), MessageModelEnum.sms_C_KdActRefundNotify, dataMap);
                } else if (KdOrderTypeEnum.TWENTY_FOUR.getCode().equals(detailEntity.getType())) {
                    productName += kdTwentyFourHoursDao.findOne(detailEntity.getProductId()).getName() + " : " + specGroup;
                    dataMap.put("productName", productName);
                    dataMap.put("actType", KdPImageEnum.BARGAIN_ACT.getDesc());
                    sMSService.sendSMS(entity.getPhone(), MessageModelEnum.sms_C_KdActRefundNotify, dataMap);
                }
                return "退款成功！";
            }
        } else {
            throw new WRWException("该订单状态不可退款");
        }
        throw new WRWException("退款失败");
    }
    
    @SuppressWarnings("rawtypes")
    private String buildStockSpec(String specInfo) {
        String specGroup = "";
        JSONObject jsonObject = JSONObject.fromObject(specInfo);
        Iterator iterator = jsonObject.keys();
        List<String> specs = new ArrayList<String>();
        while(iterator.hasNext()){
            specs.add((String) jsonObject.get(String.valueOf(iterator.next())));
        }
        for (int i = 0; i < specs.size(); i++) {
            if (i == specs.size() - 1) {
                specGroup += specs.get(i);
            } else {
                specGroup += specs.get(i) + "-";
            }
        }
        return specGroup;
    }

    @Override
    public Boolean isAllReturn(String orderId) {
        return kdOrderDetailDao.countByOrderId(orderId) <= kdOrderReturnDao.countByOrderMainId(orderId);
    }

    @Override
    @Transactional
    public ResultDto<?> delivery(String orderId, String expressInfo) {
        SUserEntity user = (SUserEntity) AuthorityContext.getCurrentUser();
        KdOrderMainEntity entity = kdOrderMainDao.findByOrderId(orderId);
        if (entity == null) {
            return ResultDtoFactory.toNack("订单不存在！");
        }
        if (WRWUtil.isEmpty(expressInfo)) {
            return ResultDtoFactory.toNack("快递信息不能为空！");
        }
        if (!entity.getStatus().equals(KdOrderStatusEnum.WAIT_DELIVERY.getCode())) {
            return ResultDtoFactory.toNack("该订单状态不能发货！");
        }
        // 保存发货信息并改变订单状态
        entity.setExpressInfo(expressInfo);
        entity.setStatus(KdOrderStatusEnum.WAIT_RECEIVE.getCode());
        kdOrderMainDao.save(entity);
        // 记录订单发货操作记录
        KdOrderOperEntity operEntity = new KdOrderOperEntity();
        operEntity.setOrderId(entity.getOrderId());
        operEntity.setOperType(KdOrderOperTypeEnum.DELIVERY.getCode());
        operEntity.setCreateDate(new Date());
        operEntity.setCreateId(user.getUserId());
        kdOrderOperDao.save(operEntity);
        return ResultDtoFactory.toAck("发货成功！");
    }

    @Override
    @Transactional
    public void addStockCount(Long productId, Integer count, Long userId, String specGroup, String orderType) {
        Long kdProductId = 0L;
        if (KdOrderTypeEnum.OFFLINE.getCode().equals(orderType) || KdOrderTypeEnum.NORMAL.getCode().equals(orderType)) {
            kdProductId = productId;
        } else if (KdOrderTypeEnum.TEAM_BUY.getCode().equals(orderType)) {
            kdProductId = kdTeamBuyProductDao.findOne(productId).getProductId();
        } else {
            kdProductId = kdTwentyFourHoursDao.findOne(productId).getProductId();
        }
        KdProductStockEntity stock = kdProductStockDao.findByProductId(kdProductId);
        if (stock != null) {
            // 按规格
            if (SPEC.equals(stock.getStockType())) {
                kdProductStockDao.updateRestAmount(kdProductId, count, userId);
                KdProductStockDetailEntity entity = kdProductStockDetailDao.findByStockIdAndSpecGroup(stock.getId(), specGroup);
                if (entity != null) {
                    kdProductStockDetailDao.addRestAmount(count, entity.getId(), userId);
                }
            }
            // 按总库存
            if (STOCK.equals(stock.getStockType())) {
                kdProductStockDao.updateRestAmount(kdProductId, count, userId);
            }
        }
    }
}
