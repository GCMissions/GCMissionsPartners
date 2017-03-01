package com.hengtiansoft.business.order.servicer.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.business.customer.service.MemberService;
import com.hengtiansoft.business.marketing.constant.CouponConstants;
import com.hengtiansoft.business.marketing.service.CouponService;
import com.hengtiansoft.business.order.dto.CouponDto;
import com.hengtiansoft.business.order.dto.CouponSearchDto;
import com.hengtiansoft.business.order.dto.LineOrderDetailDto;
import com.hengtiansoft.business.order.dto.LineOrderDto;
import com.hengtiansoft.business.order.dto.OrderDetailDto;
import com.hengtiansoft.business.order.dto.OrderDetailSearchDto;
import com.hengtiansoft.business.order.dto.OrderManagerExportDto;
import com.hengtiansoft.business.order.dto.OrderManagerExportSearchDto;
import com.hengtiansoft.business.order.dto.OrderManagerListDto;
import com.hengtiansoft.business.order.dto.OrderManagerListSearchDto;
import com.hengtiansoft.business.order.dto.RegisterRequestDto;
import com.hengtiansoft.business.order.dto.SpecInfoDto;
import com.hengtiansoft.business.order.servicer.OrderManagerService;
import com.hengtiansoft.business.order.servicer.RegisterService;
import com.hengtiansoft.business.product.service.ProductService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.authority.domain.UserInfo;
import com.hengtiansoft.common.constant.ResultCode;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.exception.BizServiceException;
import com.hengtiansoft.common.exception.WRWException;
import com.hengtiansoft.common.later.proxy.LaterProxy;
import com.hengtiansoft.common.sequence.SequenceGenerator;
import com.hengtiansoft.common.sequence.SequenceType;
import com.hengtiansoft.common.util.ApplicationContextUtil;
import com.hengtiansoft.common.util.BasicUtil;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.DateUtils;
import com.hengtiansoft.common.util.ExportExcleUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.ActivityStockDao;
import com.hengtiansoft.wrw.dao.MCouponDao;
import com.hengtiansoft.wrw.dao.MOrderDetailDao;
import com.hengtiansoft.wrw.dao.MOrderMainDao;
import com.hengtiansoft.wrw.dao.MOrderOperDao;
import com.hengtiansoft.wrw.dao.MOrderReturnDao;
import com.hengtiansoft.wrw.dao.SCouponConfigDao;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.dao.SUserDao;
import com.hengtiansoft.wrw.entity.ActivitySpec;
import com.hengtiansoft.wrw.entity.ActivityStock;
import com.hengtiansoft.wrw.entity.MCouponEntity;
import com.hengtiansoft.wrw.entity.MMemberEntity;
import com.hengtiansoft.wrw.entity.MOrderDetailEntity;
import com.hengtiansoft.wrw.entity.MOrderMainEntity;
import com.hengtiansoft.wrw.entity.MOrderOperEntity;
import com.hengtiansoft.wrw.entity.PProductEntity;
import com.hengtiansoft.wrw.entity.SCouponConfigEntity;
import com.hengtiansoft.wrw.entity.SOrgEntity;
import com.hengtiansoft.wrw.entity.SUserEntity;
import com.hengtiansoft.wrw.enums.CouponState;
import com.hengtiansoft.wrw.enums.CouponTypeEnum;
import com.hengtiansoft.wrw.enums.OrderDeleteStatus;
import com.hengtiansoft.wrw.enums.OrderExceptionEnum;
import com.hengtiansoft.wrw.enums.OrderPressedStatus;
import com.hengtiansoft.wrw.enums.OrderSourceEnum;
import com.hengtiansoft.wrw.enums.OrderStatus;
import com.hengtiansoft.wrw.enums.PaymentStatus;
import com.hengtiansoft.wrw.enums.PaymentTypeEnum;
import com.hengtiansoft.wrw.enums.ProductTypeEnum;
import com.hengtiansoft.wrw.enums.SOrgStatusEnum;

/**
 * Class Name: OrderManagerServiceImpl Description: 订单管理service 实现类
 * 
 * @author kangruan
 */
@Service
public class OrderManagerServiceImpl implements OrderManagerService {

    private static final Logger logger = LoggerFactory.getLogger(OrderManagerServiceImpl.class);

    @Autowired
    private EntityManager EntityManager;

    @Autowired
    private SOrgDao sOrgDao;

    @Autowired
    private SUserDao sUserDao;

    @Autowired
    private MCouponDao couponDao;

    @Autowired
    private MOrderReturnDao orderReturnDao;

    @Autowired
    private ProductService productService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private ActivityStockDao activityStockDao;

    @Autowired
    private MOrderDetailDao mOrderDetailDao;

    @Autowired
    private MOrderMainDao mOrderMainDao;

    @Autowired
    private MOrderOperDao mOrderOperDao;
    
    @Autowired
    private SCouponConfigDao sCouponConfigDao;
    
    @Autowired
    private MCouponDao mCouponDao;

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

        sbSql.append(" SELECT p.PRODUCT_CODE,p.PRODUCT_NAME,org.ORG_NAME,od.PRICE,od.ACT_DATE,od.SPEC_INFO,od.PERSONAL_INFO,od.REMARK ");
        sbSql.append(" FROM m_order_detail od, p_product p,s_org org ");
        sbSql.append(" WHERE od.PRODUCT_ID = p.PRODUCT_ID AND p.ORG_ID = org.ORG_ID ");

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

        if (!CollectionUtils.isEmpty(objs)) {
            OrderDetailDto dto = null;

            for (Object[] obj : objs) {
                dto = new OrderDetailDto();
                dto.setProudctCode(BasicUtil.objToString(obj[0]));
                dto.setProudctName(BasicUtil.objToString(obj[1]));
                dto.setOrgName(BasicUtil.objToString(obj[2]));
                dto.setPrice(BasicUtil.objToLong(obj[3]));
                dto.setActDate(DateTimeUtil.dateParse(BasicUtil.objToString(obj[4])));
                dto.setSpecInfo(BasicUtil.objToString(obj[5]));
                dto.setPersonalInfo(BasicUtil.objToString(obj[6]));
                dto.setRemark(BasicUtil.objToString(obj[7]));

                dtos.add(dto);
            }
        }

        return dtos;
    }

    /**
     * 
     * Description: 获取优惠券列表
     *
     * @param listDto
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CouponDto> findCoupons(CouponSearchDto listDto) {
        StringBuilder sbSql = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();

        sbSql.append(" SELECT c.COUPON_ID,c.COUPON_NAME,c.STATUS,c.VALUE,c.EFFECT_DATE,c.INVALID_DATE, ");
        sbSql.append("(select  GROUP_CONCAT(cat.CATE_NAME) from p_category cat ");
        sbSql.append(" where cat.CATE_ID in (select cc.USE_TYPE_DETAIL from  s_coupon_config cc where cc.COUPON_ID=c.COUP_CON_ID)) cateName ");
        sbSql.append("from  m_coupons c where 1=1 ");

        if (listDto.getOrderId() != null) {
            sbSql.append(" AND c.SOURCE_ORDER_ID =:orderId ");
            params.put("orderId", listDto.getOrderId());
        }

        Query query = EntityManager.createNativeQuery(sbSql.toString());
        QueryUtil.processParamForQuery(query, params);

        return buildCouponDtoLists(query.getResultList());
    }

    private List<CouponDto> buildCouponDtoLists(List<Object[]> objs) {
        List<CouponDto> dtos = new ArrayList<CouponDto>();

        if (!CollectionUtils.isEmpty(objs)) {
            CouponDto dto = null;

            for (Object[] obj : objs) {
                dto = new CouponDto();
                dto.setCouponId(BasicUtil.objToLong(obj[0]));
                dto.setCouponName(BasicUtil.objToString(obj[1]));
                dto.setStatus(BasicUtil.objToString(obj[2]));
                dto.setValue(BasicUtil.objToLong(obj[3]));
                dto.setEffectDate(DateTimeUtil.dateParse(BasicUtil.objToString(obj[4])));
                dto.setInvalidDate(DateTimeUtil.dateParse(BasicUtil.objToString(obj[5])));
                dto.setCateNames(BasicUtil.objToString(obj[6]));

                dtos.add(dto);
            }
        }

        return dtos;
    }

    /**
     * 
     * Description: 根据优惠券Id 删除用户优惠券
     *
     * @param couponId
     */
    @Override
    public void delCoupon(Long couponId) {
        MCouponEntity coupon = couponDao.findOne(couponId);
        if (coupon != null) {
            // 删除未使用的优惠券
            if (CouponState.INVALID.getKey().equals(coupon.getStatus())) {
                coupon.setStatus(CouponState.DELETE.getKey());
                couponDao.save(coupon);
            } else {
                throw new BizServiceException("优惠券已使用，不能删除");
            }
        }
    }

    /**
     * 
     * Description: 获取服务商列表
     *
     * 
     */
    @Override
    public List<SOrgEntity> findOrgs() {
        return sOrgDao.findByStatus(SOrgStatusEnum.NORMAL.getCode());
    }

    /**
     * 
     * Description: 获取订单列表
     *
     * @param dto
     */
    @Override
    public void search(OrderManagerListSearchDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder conditionSql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");
        StringBuilder sql = new StringBuilder(
                "SELECT * FROM ("
                        + "SELECT O.ORDER_ID,O.CREATE_DATE,M.LOGIN_ID, GROUP_CONCAT(P.PRODUCT_NAME SEPARATOR '#&#') PRODUCT_NAME,"
                        + "GROUP_CONCAT(SO.ORG_NAME SEPARATOR ',/,') ORG_NAME, CONCAT(',', GROUP_CONCAT(P.TYPE_ID SEPARATOR ','),',') TYPE_ID,"
                        + "O.TOTAL_AMOUNT,O.STATUS, CONCAT(',', GROUP_CONCAT(SO.ORG_ID SEPARATOR ','),',') ORG_ID,"
                        + "GROUP_CONCAT(P.PRODUCT_CODE SEPARATOR ',/,') PRODUCT_CODE,VIP.ID "
                        + "FROM M_ORDER_MAIN O LEFT JOIN M_ORDER_DETAIL OD ON O.ORDER_ID = OD.ORDER_ID "
                        + "LEFT JOIN P_PRODUCT P ON P.PRODUCT_ID = OD.PRODUCT_ID LEFT JOIN S_ORG SO ON SO.ORG_ID = P.ORG_ID LEFT JOIN M_MEMBER M ON M.ID = O.MEMBER_ID "
                        + "LEFT JOIN M_VIP_ORDER VIP ON VIP.ORDER_MAIN_ID = O.ORDER_ID "
                        + "LEFT JOIN (SELECT ORDER_MAIN_ID, SUM(RETURN_AMOUNT) AS RETURN_AMOUNT FROM M_ORDER_RETURN GROUP BY ORDER_MAIN_ID) MOR ON MOR.ORDER_MAIN_ID = O.ORDER_ID "
                        + "WHERE 1=1 ");
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
            if (dto.getStatus().startsWith(OrderStatus.COMPLETE_RETURNMONEY.getKey())) {
                if (OrderStatus.RETURN_TOTAL.getKey().equals(dto.getStatus())) {
                    // 全部退款
                    conditionSql.append("AND MOR.RETURN_AMOUNT >= O.ACTUAL_AMOUNT -  IFNULL(VIP.VIP_PRICE, 0) ");
                }
                if (OrderStatus.RETURN_APART.getKey().equals(dto.getStatus())) {
                    // 部分退款
                    conditionSql.append("AND MOR.RETURN_AMOUNT < O.ACTUAL_AMOUNT - IFNULL(VIP.VIP_PRICE, 0) ");
                }
                param.put("STATUS", OrderStatus.COMPLETE_RETURNMONEY.getKey());
            } else {
                param.put("STATUS", dto.getStatus());
            }
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
        conditionSql.append("group by O.ORDER_ID ");
        conditionSql.append("ORDER BY O.CREATE_DATE DESC,O.ORDER_ID desc) ORD WHERE 1=1 ");
        // 手机号
        if (!WRWUtil.isEmpty(dto.getPhone())) {
            if (!validateNum(dto.getPhone())) {
                return;
            }
            String msg = "%" + dto.getPhone() + "%";
            conditionSql.append("AND ord.LOGIN_ID LIKE :PHONE ");
            param.put("PHONE", msg);
        }
        // 商品名称
        if (!WRWUtil.isEmpty(dto.getProductName())) {
            String msg = dto.getProductName();
            char escape = '\\';
            msg = msg.replace("\\", escape + "\\");
            msg = msg.replace("%", escape + "%");
            msg = msg.replace("_", escape + "_");
            conditionSql.append(" AND ord.PRODUCT_NAME LIKE :NAME escape '\\\\' ");
            param.put("NAME", "%" + msg + "%");
        }
        // 商品类型
        if (!WRWUtil.isEmpty(dto.getTypeId())) {
            String msg = "%," + dto.getTypeId() + ",%";
            conditionSql.append("AND ord.TYPE_ID LIKE :TYPE ");
            param.put("TYPE", msg);
        }
        // 服务商
        if (!WRWUtil.isEmpty(dto.getOrgId())) {
            String msg = "%," + dto.getOrgId() + ",%";
            conditionSql.append("AND ord.ORG_ID LIKE :ORG ");
            param.put("ORG", msg);
        } else {
            UserInfo userInfo = AuthorityContext.getCurrentUser();
            if (userInfo != null) {
                SUserEntity user = sUserDao.findOne(userInfo.getUserId());
                if (user != null && !user.getOrgId().equals(0L)) {
                    String msg = "%," + user.getOrgId() + ",%";
                    conditionSql.append("AND ord.ORG_ID LIKE :ORG ");
                    param.put("ORG", msg);
                }
            }
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
            dto.setProductName(WRWUtil.objToString(array[3]));
            dto.setOrgName(WRWUtil.objToString(array[4]));

            String types = WRWUtil.objToString(array[5]);
            if (StringUtils.isNotEmpty(types)) {
                String[] typesIds = types.split(",");
                StringBuilder productType = new StringBuilder("");
                for (String typesId : typesIds) {
                    if (!typesId.equals("")) {
                        productType.append(ProductTypeEnum.getValue(WRWUtil.objToLong(typesId)) + "/");
                    }
                }
                dto.setProductType(productType.toString());
            } else {
                dto.setProductType("/");
            }

            dto.setAmount(WRWUtil.transFenToYuan2String(array[6] == null ? 0L : WRWUtil.objToLong(array[6])));

            if (array[7] != null) {
                dto.setStatus(OrderStatus.getValue(WRWUtil.objToString(array[7])));
            }

            // 检查是否可以退款
            int returnable = 0;
            if (OrderStatus.COMPLETE_RETURNMONEY.getKey().equals(dto.getStatus())) {
                returnable = orderReturnDao.isReturnable(dto.getOrderId());
            }
            dto.setReturnable(returnable > 0 ? "1" : "0");

            dto.setProductCode(WRWUtil.objToString(array[9]));
            
            dto.setBuyVip(array[10] == null ? "否" : "是");

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
    @Override
    public HSSFWorkbook toExcle(OrderManagerExportSearchDto pageDto) {
        ArrayList<String> fieldName = new ArrayList<String>();
        ArrayList<String> sheetName = new ArrayList<String>();
        ArrayList<String> fieldStyle = new ArrayList<String>();
        List<List<Object>> fieldData = new ArrayList<List<Object>>();
        ArrayList<Integer> orderNum = new ArrayList<Integer>();
        fieldName.add(0, "订单编号");
        fieldName.add(1, "订单创建时间");
        fieldName.add(2, "订单状态");
        fieldName.add(3, "买家注册会员名/昵称");
        fieldName.add(4, "买家注册手机号");
        fieldName.add(5, "买家注册省、市");
        fieldName.add(6, "店铺/商家名（服务商）");
        fieldName.add(7, "商品名");
        fieldName.add(8, "SKU（商品规格）");
        fieldName.add(9, "活动日期");
        fieldName.add(10, "商品单价（元）");
        fieldName.add(11, "购买数量");
        fieldName.add(12, "商家勾选的若干留言字段（商品购买人、手机号等）");
        fieldName.add(13, "买家留言");
        fieldName.add(14, "应付金额（元）");
        fieldName.add(15, "优惠金额（元）");
        fieldName.add(16, "实付金额（元）");
        fieldName.add(17, "支付方式（连连、微信）");
        fieldName.add(18, "订单付款时间");
        fieldName.add(19, "订单流水号");
        fieldName.add(20, "退款金额（元）");
        fieldName.add(21, "退款时间");
        fieldName.add(22, "是否开通VIP");

        for (int j = 0, len = fieldName.size(); j <= len; j++) {
            fieldStyle.add(j, "6000");
        }
        pageDto.setPageSize(65536);
        this.exportSearch(pageDto);
        if (!pageDto.getList().isEmpty()) {
            for (int i = 0, len = pageDto.getList().size(); i < len; i++) {
                OrderManagerExportDto dto = pageDto.getList().get(i);
                String[] productName = dto.getProductName().split(",/,");
                String[] orgName = dto.getOrgName().split(",/,");
                String[] specInfos = dto.getSpecInfo().split(",/,");
                String[] productPrice = dto.getProductPrice().split(",/,");
                String[] num = dto.getNum().split(",/,");
                String[] requireFields = dto.getRequireField().split(",/,");
                String[] remark = dto.getRemark().split(",/,");
                String[] actDate = dto.getActDate().split(",/,");
                Integer rowNumber = orgName.length;
                orderNum.add(rowNumber);
                for (int j = 0; j < rowNumber; j++) {
                    List<Object> rowData = new ArrayList<Object>();
                    if (j == 0) {
                        rowData.add(0, dto.getOrderId());
                        rowData.add(1, dto.getCreateDate());
                        rowData.add(2, dto.getStatus());
                        rowData.add(3, dto.getCustName());
                        rowData.add(4, dto.getMenberPhone());
                        rowData.add(5, dto.getMemberRegion());
                        rowData.add(6, orgName[j]);
                        rowData.add(7, productName[j]);

                        JSONArray ja = JSONArray.fromObject(specInfos[j]);
                        StringBuilder specInfo = new StringBuilder("");
                        for (int a = 0; a < ja.size(); a++) {
                            specInfo.append(ja.getJSONObject(a).getString("subSpec") + "  ");
                        }

                        rowData.add(8, specInfo.toString());

                        if (j < actDate.length) {
                            if (!actDate[j].equals("")) {
                                String actDates = actDate[j].toString();
                                Date date = DateTimeUtil.dateParse(actDates);
                                rowData.add(9, DateTimeUtil.parseDateToString(date, DateTimeUtil.SIMPLE_YMD));
                            } else {
                                rowData.add(9, "");
                            }
                        } else {
                            rowData.add(9, "");
                        }

                        rowData.add(10, WRWUtil.transFenToYuan2String(WRWUtil.objToLong(productPrice[j])));
                        rowData.add(11, num[j]);

                        if (j < requireFields.length) {
                            if (!requireFields[j].equals("")) {
                                JSONArray jb = JSONArray.fromObject(requireFields[j]);
                                StringBuilder requireField = new StringBuilder("");
                                for (int a = 0; a < jb.size(); a++) {
                                    requireField.append(jb.getJSONObject(a).getString("key") + ":"
                                            + jb.getJSONObject(a).getString("value") + ";");
                                }
                                rowData.add(12, requireField.toString());
                            } else {
                                rowData.add(12, "");
                            }
                        } else {
                            rowData.add(12, "");
                        }

                        if (j < remark.length) {
                            rowData.add(13, remark[j]);
                        } else {
                            rowData.add(13, "");
                        }
                        rowData.add(14, dto.getTotalAmunt());
                        rowData.add(15, dto.getCouponAmunt());
                        rowData.add(16, dto.getActualAmount());
                        rowData.add(17, dto.getPaymentType());
                        rowData.add(18, dto.getPayDate());
                        rowData.add(19, dto.getTSN());
                        rowData.add(20, dto.getReturnAmunt());
                        rowData.add(21, dto.getReturnDate());
                        if ("0".equals(dto.getBuyVip())) {
                            rowData.add(22, "否");
                        } else {
                            rowData.add(22, "是");
                        }
                        fieldData.add(rowData);
                    } else {
                        rowData.add(0, "");
                        rowData.add(1, "");
                        rowData.add(2, "");
                        rowData.add(3, "");
                        rowData.add(4, "");
                        rowData.add(5, "");
                        rowData.add(6, orgName[j]);
                        rowData.add(7, productName[j]);

                        JSONArray ja = JSONArray.fromObject(specInfos[j]);
                        StringBuilder specInfo = new StringBuilder("");
                        for (int a = 0; a < ja.size(); a++) {
                            specInfo.append(ja.getJSONObject(a).getString("subSpec") + "  ");
                        }

                        rowData.add(8, specInfo.toString());
                        if (j < actDate.length) {
                            if (!actDate[j].equals("")) {
                                String actDates = actDate[j].toString();
                                Date date = DateTimeUtil.dateParse(actDates);
                                rowData.add(9, DateTimeUtil.parseDateToString(date, DateTimeUtil.SIMPLE_YMD));
                            } else {
                                rowData.add(9, "");
                            }
                        } else {
                            rowData.add(9, "");
                        }

                        rowData.add(10, WRWUtil.transFenToYuan2String(WRWUtil.objToLong(productPrice[j])));
                        rowData.add(11, num[j]);

                        if (j < requireFields.length) {
                            if (!requireFields[j].equals("")) {
                                JSONArray jb = JSONArray.fromObject(requireFields[j]);
                                StringBuilder requireField = new StringBuilder("");
                                for (int a = 0; a < jb.size(); a++) {
                                    requireField.append(jb.getJSONObject(a).getString("key") + ":"
                                            + jb.getJSONObject(a).getString("value") + ";");
                                }
                                rowData.add(12, requireField.toString());
                            } else {
                                rowData.add(12, "");
                            }
                        } else {
                            rowData.add(12, "");
                        }

                        if (j < remark.length) {
                            rowData.add(13, remark[j]);
                        } else {
                            rowData.add(13, "");
                        }
                        rowData.add(14, "");
                        rowData.add(15, "");
                        rowData.add(16, "");
                        rowData.add(17, "");
                        rowData.add(18, "");
                        rowData.add(19, "");
                        rowData.add(20, "");
                        rowData.add(21, "");
                        rowData.add(22, "");
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
        if (!CollectionUtils.isEmpty(orderNum)) {
            for (Integer num : orderNum) {
                for (int i = 0; i <= 5; i++) {
                    wb.getSheetAt(0).addMergedRegion(
                            new org.apache.poi.ss.util.CellRangeAddress(sum, sum + num - 1, i, i));
                }
                for (int i = 14; i <= 21; i++) {
                    wb.getSheetAt(0).addMergedRegion(
                            new org.apache.poi.ss.util.CellRangeAddress(sum, sum + num - 1, i, i));
                }
                sum += num;
            }
        }
        return wb;
    }

    @Override
    public void exportSearch(OrderManagerExportSearchDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder conditionSql = new StringBuilder("");
        StringBuilder sql = new StringBuilder(
                "SELECT * FROM (SELECT O.ORDER_ID,O.CREATE_DATE,O.STATUS,"
                        + "M.MEMBER_NAME,M.CUST_NAME,"
                        + "O.TOTAL_AMOUNT,O.COUPON_AMOUNT,O.ACTUAL_AMOUNT,"
                        + "O.PAYMENT_TYPE,O.PAY_DATE,O.TSN,R.RETURN_AMOUNT,"
                        + "M.LOGIN_ID,S.MERGER_NAME,GROUP_CONCAT(P.PRODUCT_NAME SEPARATOR ',/,') PRODUCT_NAME,"
                        + "GROUP_CONCAT(SO.ORG_NAME SEPARATOR ',/,') ORG_NAME,GROUP_CONCAT(OD.SPEC_INFO SEPARATOR ',/,') SPEC_INFO,"
                        + "GROUP_CONCAT(OD.NUM SEPARATOR ',/,') NUM,GROUP_CONCAT(OD.PERSONAL_INFO SEPARATOR ',/,') PERSONAL_INFO,"
                        + "GROUP_CONCAT(OD.PRICE SEPARATOR ',/,') PRICE,GROUP_CONCAT(OD.REMARK SEPARATOR ',/,') REMARK,GROUP_CONCAT(OD.ACT_DATE SEPARATOR ',/,') ACT_DATE,"
                        + "CONCAT(',', GROUP_CONCAT(P.TYPE_ID SEPARATOR ','),',') TYPE_ID,CONCAT(',', GROUP_CONCAT(SO.ORG_ID SEPARATOR ','),',') ORG_ID, "
                        + "R.RETURN_DATE, VIP.ID "
                        + "FROM M_ORDER_MAIN O LEFT JOIN M_ORDER_DETAIL OD ON O.ORDER_ID = OD.ORDER_ID "
                        + "LEFT JOIN P_PRODUCT P ON P.PRODUCT_ID = OD.PRODUCT_ID "
                        + "LEFT JOIN S_ORG SO ON SO.ORG_ID = P.ORG_ID "
                        + "LEFT JOIN M_MEMBER M ON M.ID = O.MEMBER_ID "
                        + "LEFT JOIN M_VIP_ORDER VIP ON VIP.ORDER_MAIN_ID = O.ORDER_ID "
                        + "LEFT JOIN (SELECT ORDER_MAIN_ID, IFNULL(SUM(RETURN_AMOUNT), 0) AS RETURN_AMOUNT,CREATE_DATE as RETURN_DATE FROM M_ORDER_RETURN GROUP BY ORDER_MAIN_ID) R "
                        + "ON O.ORDER_ID = R.ORDER_MAIN_ID " + "LEFT JOIN S_REGION S ON O.REGION_ID = S.ID WHERE 1=1 ");
        if (WRWUtil.isEmpty(dto.getOrderIds())) {
            // 创建时间
            if (WRWUtil.isNotEmpty(dto.getStartTime())) {
                conditionSql.append("AND O.CREATE_DATE >= :START ");
                param.put("START", DateTimeUtil.getDateBegin(dto.getStartTime()));
            }
            if (WRWUtil.isNotEmpty(dto.getEndDate())) {
                conditionSql.append("AND O.CREATE_DATE <= :END ");
                param.put("END", DateTimeUtil.getDateEnd(dto.getEndDate()));
            }
            // 订单号
            if (WRWUtil.isNotEmpty(dto.getOrderId())) {
                String msg = "%" + dto.getOrderId() + "%";
                conditionSql.append("AND O.ORDER_ID LIKE :ORDER_ID ");
                param.put("ORDER_ID", msg);
            }
            // 支付状态
            if (WRWUtil.isNotEmpty(dto.getStatus())) {
                conditionSql.append(" AND O.STATUS = :STATUS ");
                if (dto.getStatus().startsWith(OrderStatus.COMPLETE_RETURNMONEY.getKey())) {
                    if (OrderStatus.RETURN_TOTAL.getKey().equals(dto.getStatus())) {
                        // 全部退款
                        conditionSql.append("AND R.RETURN_AMOUNT >= O.ACTUAL_AMOUNT -  IFNULL(VIP.VIP_PRICE, 0) ");
                    }
                    if (OrderStatus.RETURN_APART.getKey().equals(dto.getStatus())) {
                        // 部分退款
                        conditionSql.append("AND R.RETURN_AMOUNT < O.ACTUAL_AMOUNT - IFNULL(VIP.VIP_PRICE, 0) ");
                    }
                    param.put("STATUS", OrderStatus.COMPLETE_RETURNMONEY.getKey());
                } else {
                    param.put("STATUS", dto.getStatus());
                }
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
            conditionSql.append("group by O.ORDER_ID ");
            conditionSql.append("ORDER BY O.CREATE_DATE DESC) ORD WHERE 1=1 ");
            // 手机号
            if (!WRWUtil.isEmpty(dto.getPhone())) {
                if (!validateNum(dto.getPhone())) {
                    return;
                }
                String msg = "%" + dto.getPhone() + "%";
                conditionSql.append("AND ord.LOGIN_ID LIKE :PHONE ");
                param.put("PHONE", msg);
            }
            // 商品名称
            if (WRWUtil.isNotEmpty(dto.getProductName())) {
                String msg = dto.getProductName();
                char escape = '\\';
                msg = msg.replace("\\", escape + "\\");
                msg = msg.replace("%", escape + "%");
                msg = msg.replace("_", escape + "_");
                conditionSql.append(" AND ord.PRODUCT_NAME LIKE :NAME escape '\\\\' ");
                param.put("NAME", "%" + msg + "%");
            }
            // 商品类型
            if (WRWUtil.isNotEmpty(dto.getTypeId())) {
                String msg = "%," + dto.getTypeId() + ",%";
                conditionSql.append("AND ord.TYPE_ID LIKE :TYPE ");
                param.put("TYPE", msg);
            }
            // 服务商
            if (WRWUtil.isNotEmpty(dto.getOrgId())) {
                String msg = "%," + dto.getOrgId() + ",%";
                conditionSql.append("AND ord.ORG_ID LIKE :ORG ");
                param.put("ORG", msg);
            } else {
                UserInfo userInfo = AuthorityContext.getCurrentUser();
                if (userInfo != null) {
                    SUserEntity user = sUserDao.findOne(userInfo.getUserId());
                    if (user != null && !user.getOrgId().equals(0L)) {
                        String msg = "%," + user.getOrgId() + ",%";
                        conditionSql.append("AND ord.ORG_ID LIKE :ORG ");
                        param.put("ORG", msg);
                    }
                }
            }
        } else {
            String[] orderIds = dto.getOrderIds().split(",");
            List<String> orderIdList = new ArrayList<String>();
            Collections.addAll(orderIdList, orderIds);
            conditionSql.append("AND O.ORDER_ID IN :ORDER_IDS ");
            param.put("ORDER_IDS", orderIdList);
            conditionSql.append("group by O.ORDER_ID ");
            conditionSql.append("ORDER BY O.CREATE_DATE DESC) ORD ");
        }
        Query query = EntityManager.createNativeQuery(sql.append(conditionSql).toString());
        QueryUtil.processParamForQuery(query, param);
        dto.setList(buildOrderManagerExportDtos(query.getResultList(), dto));
    }

    private List<OrderManagerExportDto> buildOrderManagerExportDtos(List<Object[]> list,
            OrderManagerExportSearchDto edto) {
        List<OrderManagerExportDto> dtos = new ArrayList<OrderManagerExportDto>();
        for (Object[] array : list) {
            OrderManagerExportDto dto = new OrderManagerExportDto();
            dto.setOrderId(WRWUtil.objToString(array[0]));
            if (array[1] != null) {
                String createDate = array[1].toString();
                Date date = DateTimeUtil.dateParse(createDate);
                dto.setCreateDate(DateTimeUtil.dateFormat(date));
            }

            if (array[2] != null) {
                dto.setStatus(OrderStatus.getValue(WRWUtil.objToString(array[2])));
            }

            dto.setMenberName(WRWUtil.objToString(array[3]));
            dto.setCustName(WRWUtil.objToString(array[4]));

            if (array[5] != null) {
                dto.setTotalAmunt(WRWUtil.transFenToYuan2String(WRWUtil.objToLong(array[5])));
            }

            if (array[6] != null) {
                dto.setCouponAmunt(WRWUtil.transFenToYuan2String(WRWUtil.objToLong(array[6])));
            }

            if (array[7] != null) {
                dto.setActualAmount(WRWUtil.transFenToYuan2String(WRWUtil.objToLong(array[7])));
            }

            dto.setPaymentType(PaymentTypeEnum.getName(WRWUtil.objToString(array[8])));

            if (array[9] != null) {
                String payDate = array[9].toString();
                Date date = DateTimeUtil.dateParse(payDate);
                dto.setPayDate(DateTimeUtil.dateFormat(date));
            }

            dto.setTSN(WRWUtil.objToString(array[10]));

            if (array[11] != null) {
                dto.setReturnAmunt(WRWUtil.transFenToYuan2String(WRWUtil.objToLong(array[11])));
            }
            dto.setMenberPhone(WRWUtil.objToString(array[12]));

            if (array[13] != null) {
                String region = WRWUtil.objToString(array[13]);
                String[] regions = region.split(",");
                if (regions.length > 2) {
                    dto.setMemberRegion(regions[1] + "," + regions[2]);
                } else if (regions.length == 2) {
                    dto.setMemberRegion(regions[1]);
                } else {
                    dto.setMemberRegion(regions[0]);
                }
            }
            dto.setProductName(WRWUtil.objToString(array[14]));
            dto.setOrgName(WRWUtil.objToString(array[15]));
            dto.setSpecInfo(WRWUtil.objToString(array[16]));
            dto.setNum(WRWUtil.objToString(array[17]));
            dto.setRequireField(WRWUtil.objToString(array[18]));
            dto.setProductPrice(WRWUtil.objToString(array[19]));
            dto.setRemark(WRWUtil.objToString(array[20]));
            dto.setActDate(WRWUtil.objToString(array[21]));
            if (array[24] != null) {
                String returnDate = array[24].toString();
                Date date = DateTimeUtil.dateParse(returnDate);
                dto.setReturnDate(DateTimeUtil.dateFormat(date));
            }
            dto.setBuyVip("0");
            if (array[25] != null) {
                dto.setBuyVip("1");
            }
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public String isOrg() {
        UserInfo userInfo = AuthorityContext.getCurrentUser();
        if (userInfo != null) {
            SUserEntity user = sUserDao.findOne(userInfo.getUserId());
            if (user != null) {
                return user.getOrgId().equals(0L) ? "0" : null;
            }
        }
        return "0";
    }

    @Override
    public ResultDto<String> importExcel(InputStream inputStream) {
        // 开始时间
        long start = System.currentTimeMillis();
        LineOrderDto lineOrderDto = new LineOrderDto();
        ResultDto<String> pResult = new ResultDto<String>();
        List<LineOrderDetailDto> lineOrderDetailDtos = new ArrayList<LineOrderDetailDto>();
        Map<String, String> phoneMap = new HashMap<String, String>();
        try {
            // 创建Excel对象
            Workbook wb = WorkbookFactory.create(inputStream);
            // 取出第一个工作表，索引从0开始
            Sheet sheet = wb.getSheetAt(0);
            // 从第3行开始遍历
            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    // 如果行数据为空，则不处理
                    continue;
                }
                if (i == 2) {
                    String productIdStr = row.getCell(0).toString();
                    if (WRWUtil.isNotEmpty(productIdStr) && productIdStr.toUpperCase().indexOf("P") != -1) {
                        Long productId = Long.valueOf(productIdStr.substring(1));
                        lineOrderDto.setProductId(productId);
                        if (HSSFDateUtil.isCellDateFormatted(row.getCell(2))) {
                            SimpleDateFormat sdf = null;  
                            if (row.getCell(2).getCellStyle().getDataFormat() == HSSFDataFormat
                                    .getBuiltinFormat("h:mm")) {
                                sdf = new SimpleDateFormat("HH:mm");
                            } else {// 日期
                                sdf = new SimpleDateFormat("yyyy-MM-dd");
                            }
                            Date date = row.getCell(2).getDateCellValue();
                            lineOrderDto.setActDate(sdf.format(date));
                        }
                        // 处理商品规格信息
                        String specInfoStr = row.getCell(3).toString();
                        List<SpecInfoDto> specInfoList = new ArrayList<SpecInfoDto>();
                        // 代表有多个子规格组合
                        if (specInfoStr.indexOf(";") != -1) {
                            String[] specInfoArr = specInfoStr.split(";");
                            if (specInfoArr.length > 0) {
                                for (String specInfo : specInfoArr) {
                                    if (specInfo.indexOf("&") != -1) {
                                        SpecInfoDto specInfoDto = new SpecInfoDto();
                                        String mainSubArr[] = specInfo.split("&");
                                        specInfoDto.setMainSpec(mainSubArr[0]);
                                        specInfoDto.setSubSpec(mainSubArr[1]);
                                        specInfoList.add(specInfoDto);
                                    } else {
                                        return ResultDtoFactory.toBusinessError("录入订单中商品规格格式不正确！");
                                    }
                                }
                            } else {
                                return ResultDtoFactory.toBusinessError("录入订单中商品规格格式不正确！");
                            }
                        } else if (specInfoStr.indexOf(";") == -1 && specInfoStr.indexOf("&") != -1) {
                            // 代表只有一个子规格
                            if (specInfoStr.indexOf("&") != -1) {
                                SpecInfoDto specInfoDto = new SpecInfoDto();
                                String mainSubArr[] = specInfoStr.split("&");
                                specInfoDto.setMainSpec(mainSubArr[0]);
                                specInfoDto.setSubSpec(mainSubArr[1]);
                                specInfoList.add(specInfoDto);
                            } else {
                                return ResultDtoFactory.toBusinessError("录入订单中商品规格格式不正确！");
                            }
                        }
                        lineOrderDto.setSpecInfo(specInfoList);
                        pResult = productService.checkStatusForLineOrder(lineOrderDto);
                    } else {
                        return ResultDtoFactory.toBusinessError("录入订单中商品编号不存在！");
                    }
                } else if (i == 3 || i == 4) {
                    // 无数据
                    continue;
                } else if (i > 4) {
                    Row row2 = sheet.getRow(i);
                    if (row2 == null) {
                        // 如果行数据为空，则不处理
                        continue;
                    }
                    DecimalFormat df = new DecimalFormat("0");
                    String phone = null;
                    Cell phoneCell = row.getCell(0);
                    if (phoneCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                        phone = df.format(row.getCell(0).getNumericCellValue());
                        if (phone.length() != 11) {
                            return ResultDtoFactory.toBusinessError("录入订单中用户手机号位数不正确！如：" + phone);
                        }
                    }else{
                        return ResultDtoFactory.toBusinessError("录入订单中用户手机号格式不正确！如："+row.getCell(0).toString());
                    }
                    if (!phoneMap.containsKey(phone)) {
                        LineOrderDetailDto lineOrderDetailDto = new LineOrderDetailDto();
                        lineOrderDetailDto.setPhone(phone);
                        lineOrderDetailDto.setUserName(row.getCell(1).toString());
                        String orderAmount = df.format(row.getCell(2).getNumericCellValue());
                        String productNum = df.format(row.getCell(3).getNumericCellValue());
                        if (!WRWUtil.isNumeric(orderAmount) || !WRWUtil.isNumeric(productNum)) {
                            return ResultDtoFactory.toBusinessError("录入订单中订单金额或者商品数量格式不正确！");
                        }
                        lineOrderDetailDto.setOrderAmount(Long.valueOf(orderAmount));
                        lineOrderDetailDto.setProductNum(Integer.valueOf(productNum));
                        lineOrderDetailDtos.add(lineOrderDetailDto);
                        phoneMap.put(phone, phone);
                    }
                }
            }
            // 商品校验成功
            if (ResultCode.ACK.equals(pResult.getCode())) {
                lineOrderDto.setLineOrderDetailDtos(lineOrderDetailDtos);
                if (!CollectionUtils.isEmpty(lineOrderDto.getLineOrderDetailDtos())) {
                    Map<String, Object> map = ApplicationContextUtil.getBean(CouponService.class).getProductPrice(
                            lineOrderDto.getProductId(), lineOrderDto.getSpecInfo(), lineOrderDto.getActDate());
                    if(map == null || CollectionUtils.isEmpty(map)){
                        return ResultDtoFactory.toBusinessError("录入订单中商品无此商品规格，请核对后再操作！");
                    }
                    LaterProxy.cast(OrderManagerService.class, "lineOrderChannel").lineOrderProcess(lineOrderDto,map);
                } else {
                    return ResultDtoFactory.toBusinessError("录入订单中暂无用户数据！");
                }
            }else{
                return ResultDtoFactory.toBusinessError(pResult.getMessage());
            }
        } catch (IOException | EncryptedDocumentException | InvalidFormatException e) {
            throw new WRWException(e.toString());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new WRWException(e.toString());
                }
            }
        }
        long end = System.currentTimeMillis();
        logger.error("总共执行：" + (end - start));
        return ResultDtoFactory.toAck("处理成功！请稍后查看。");
    }

    @Override
    public ResultDto<String> lineOrderProcess(LineOrderDto lineOrderDto,Map<String, Object> map) {
        // 开始时间
        long start = System.currentTimeMillis();
        if (!CollectionUtils.isEmpty(lineOrderDto.getLineOrderDetailDtos())) {
            for (LineOrderDetailDto lineOrderDetailDto : lineOrderDto.getLineOrderDetailDtos()) {
                MMemberEntity mEntity = memberService.findByLoginId(lineOrderDetailDto.getPhone(), "1");
                // 1. 判断用户是否存在，不存在则创建对应账户信息
                if (mEntity == null) {
                    RegisterRequestDto requestDto = new RegisterRequestDto(lineOrderDetailDto.getPhone(), null, null);
                    mEntity = registerService.register(requestDto);
                }
                // 2. 生成对应订单信息
                createOnlineOrder(mEntity, lineOrderDetailDto, lineOrderDto,map);
            }
        } else {
            return ResultDtoFactory.toBusinessError("录入订单中暂无用户数据！");
        }
        long end = System.currentTimeMillis();
        logger.error("总共执行：" + (end - start));
        return null;
    }

    @Override
    @Transactional
    public ResultDto<Boolean> createOnlineOrder(MMemberEntity mEntity, LineOrderDetailDto lineOrderDetailDto,
            LineOrderDto lineOrderDto,Map<String, Object> map) {
        Long memberId = mEntity.getUserId();
        // 生成订单id
        String orderId = sequenceGenerator.getOrderId(SequenceType.M_ORDER);

        Integer pPrice = 0;
        ActivitySpec activitySpec = null;
        if (map != null && !CollectionUtils.isEmpty(map)) {
            Object object = map.get(CouponConstants.PRODUCT_PRICE);
            if (object == null) {
                return ResultDtoFactory.toBusinessError("录入订单中暂无用户数据！");
            }
            pPrice = Integer.valueOf(object.toString());
            activitySpec = (ActivitySpec) map.get(CouponConstants.ACT_SPEC);
        }

        MOrderDetailEntity detail = new MOrderDetailEntity();
        // 订单id
        detail.setOrderId(orderId);
        //商品id
        detail.setProductId(lineOrderDto.getProductId());
        // 商品数量
        detail.setNum(lineOrderDetailDto.getProductNum());
        // 该规格商品价格
        detail.setPrice(Long.valueOf(pPrice));
        // 商品总金额
        detail.setAmount(Long.valueOf(detail.getNum() * Integer.valueOf(pPrice)));
        // 获取库存信息
        ActivityStock activityStock = activityStockDao.findActByProductIdAndActDate(lineOrderDto.getProductId(),
                lineOrderDto.getActDate());
        // 下单时库存数量
        detail.setGoodNum(activityStock != null ? activityStock.getTotalCount().longValue() : null);
        // 商品规格信息
        detail.setSpecInfo(lineOrderDto.getSpecInfo() == null ? null : JSONArray.fromObject(lineOrderDto.getSpecInfo())
                .toString());
        // 商品库存id
        detail.setActStockId(activityStock != null ? activityStock.getId() : null);
        // 规格id
        detail.setActSpecId(activitySpec.getId());
        // 订单明细备注
        detail.setRemark(null);
        // 商品活动时间
        detail.setActDate(DateUtils.getFormatDateByString(lineOrderDto.getActDate(), DateUtils.STRING_PATTERN_SHORT));
        // 订单自动变更为待点评状态
        detail.setStatus("0");

        // 保存订单商品明细信息
        mOrderDetailDao.save(detail);

        // 生成订单
        MOrderMainEntity order = new MOrderMainEntity();
        order.setOrgId(0L);
        order.setOrderId(orderId);
        order.setMemberId(memberId);
        order.setMemberName(memberService.findById(memberId).getMemberName());
        order.setStatus(OrderStatus.WATI_RATE.getKey());
        order.setIsDelete(OrderDeleteStatus.EXISTING.getKey());
        order.setPaymentStatus(PaymentStatus.paid.getKey());
        order.setPressedFlag(OrderPressedStatus.UNPRESSED.getKey());
        order.setIfException(OrderExceptionEnum.NORMAL.getKey());
        order.setTotalAmount(lineOrderDetailDto.getOrderAmount());
        order.setActualAmount(lineOrderDetailDto.getOrderAmount());
        order.setCouponAmount(0L);
        order.setTotalNum(1);
        order.setCreateId(memberId);
        order.setCreateDate(new Date());
        order.setSource(OrderSourceEnum.OFFLINE.getCode());
        order.setPhone(mEntity.getLoginId());
        order.setPayDate(new Date());
        // 默认为杭州
        order.setRegionId(330100);
        mOrderMainDao.save(order);

        // 订单操作记录
        updateStatus(memberId, OrderStatus.WATI_RATE.getKey(), orderId);
        
        giveCoupon(orderId, lineOrderDetailDto.getOrderAmount(), memberId);
        
        return null;
    }

    @Override
    @Transactional(value = "jpaTransactionManager")
    public void updateStatus(Long memberId, String status, String... orderIds) {
        Date now = new Date();
        List<MOrderOperEntity> orderOpers = new ArrayList<>();

        List<MOrderMainEntity> orders = mOrderMainDao.findAll(Arrays.asList(orderIds));
        for (MOrderMainEntity order : orders) {
            // 订单更新记录
            orderOpers.add(generateOrderStatus(order.getOrderId(), status, memberId));

            // 取消订单
            if (OrderStatus.CANCELED.getKey().equals(status)) {
                order.setCancelReason("用户手动取消");
            }
            // 订单评价
            else if (OrderStatus.COMPLETE.getKey().equals(status)) {
                order.setFinishDate(now);
            }

            order.setStatus(status);
            order.setModifyDate(now);
            order.setModifyId(memberId);
        }
        mOrderMainDao.save(orders);
        mOrderOperDao.save(orderOpers);
    }

    /**
     * Description: 保存订单操作记录
     *
     * @param status
     * @param orderIds
     */
    @Transactional(value = "jpaTransactionManager")
    private MOrderOperEntity generateOrderStatus(String orderId, String operType, Long memberId) {
        MOrderOperEntity orderOper = mOrderOperDao.findByOrderIdAndOpertype(orderId, operType);
        if (null == orderOper) {
            orderOper = new MOrderOperEntity();
            orderOper.setOrderId(orderId);
            orderOper.setOpertype(operType);
        }
        orderOper.setComment("订单状态更新:" + OrderStatus.getValue(operType));
        orderOper.setUserId(memberId);
        orderOper.setOperDate(new Date());
        return orderOper;
    }

    @Override
    public void giveCoupon(String orderId, Long actualAmount, Long memberId) {
        // 订单中有哪些商品
        List<PProductEntity> productList = productService.findProductsByOrderId(orderId);
        if (productList == null || productList.isEmpty()) {
            logger.error("支付回调-赠送优惠券-订单【{}】内商品数据异常", orderId);
            return;
        }
        // 查询商品的品类
        Set<String> cateIds = new HashSet<String>();
        for (PProductEntity product : productList) {
            cateIds.add(product.getCateId() + "");
        }
        // 根据品类ID查询有哪些可获取的优惠券
        SCouponConfigEntity resultCoupon = null;
        for (String cateId : cateIds) {
            SCouponConfigEntity coupon = sCouponConfigDao.findCoupons(cateId, actualAmount);
            if (coupon == null || coupon.getValue() == null) {
                continue;
            }

            // 取面额最大的优惠券
            if (resultCoupon != null) {
                // 将当前优惠券的面额与之前优惠券的面额进行比较，取最大的，并将该优惠券作为最终选择
                if (resultCoupon.getValue().intValue() < coupon.getValue().intValue()) {
                    resultCoupon = coupon;
                }
            } else {
                resultCoupon = coupon;
            }
        }

        // 取面额最大的优惠券奖励给用户
        if (resultCoupon == null) {
            logger.error("支付回调-赠送优惠券-无相应优惠券奖励给用户");
            return;
        }

        MCouponEntity mCoupon = new MCouponEntity();
        mCoupon.setCoupConId(resultCoupon.getCouponId());
        mCoupon.setMemberId(memberId);
        mCoupon.setSourceOrderId(orderId);
        mCoupon.setStatus(CouponState.INVALID.getKey());
        mCoupon.setCouponName(resultCoupon.getCouponName());
        mCoupon.setType(CouponTypeEnum.EXCHANGE.getKey());
        mCoupon.setValue(resultCoupon.getValue());
        mCoupon.setLimitValue(resultCoupon.getUserValueLimit());
        mCoupon.setEffectDate(resultCoupon.getEffectDate());
        mCoupon.setInvalidDate(resultCoupon.getInvalidDate());
        mCoupon.setCreateDate(new Date());
        mCouponDao.save(mCoupon);
        logger.error("支付回调-赠送优惠券-订单【{}】获赠优惠券【{}】", orderId, resultCoupon.getCouponId());
    
    }
}
