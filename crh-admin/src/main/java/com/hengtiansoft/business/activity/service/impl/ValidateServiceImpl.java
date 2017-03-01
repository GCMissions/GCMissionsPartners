package com.hengtiansoft.business.activity.service.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengtiansoft.business.activity.dto.ProductValidateDto;
import com.hengtiansoft.business.activity.dto.ValidateSearchDto;
import com.hengtiansoft.business.activity.service.ValidateService;
import com.hengtiansoft.business.common.util.QueryUtil;
import com.hengtiansoft.business.order.dto.OrderDetailDto;
import com.hengtiansoft.business.order.dto.OrderDetailSearchDto;
import com.hengtiansoft.business.order.dto.OrderManagerExportDto;
import com.hengtiansoft.business.order.dto.OrderManagerExportSearchDto;
import com.hengtiansoft.business.order.dto.OrderManagerListDto;
import com.hengtiansoft.business.order.dto.OrderManagerListSearchDto;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.authority.domain.UserInfo;
import com.hengtiansoft.common.dto.ResultDto;
import com.hengtiansoft.common.dto.ResultDtoFactory;
import com.hengtiansoft.common.util.BasicUtil;
import com.hengtiansoft.common.util.CurrencyUtils;
import com.hengtiansoft.common.util.DateTimeUtil;
import com.hengtiansoft.common.util.ExportExcleUtil;
import com.hengtiansoft.common.util.WRWUtil;
import com.hengtiansoft.wrw.dao.ActivityStockDao;
import com.hengtiansoft.wrw.dao.MOrderDetailDao;
import com.hengtiansoft.wrw.dao.MOrderMainDao;
import com.hengtiansoft.wrw.dao.PCategoryDao;
import com.hengtiansoft.wrw.dao.PProductDao;
import com.hengtiansoft.wrw.dao.PShiefDao;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.dao.SUserDao;
import com.hengtiansoft.wrw.entity.ActivityStock;
import com.hengtiansoft.wrw.entity.MOrderDetailEntity;
import com.hengtiansoft.wrw.entity.PCategoryEntity;
import com.hengtiansoft.wrw.entity.SUserEntity;
import com.hengtiansoft.wrw.enums.OrderStatus;
import com.hengtiansoft.wrw.enums.PaymentTypeEnum;
import com.hengtiansoft.wrw.enums.ProductStatusEnum;
import com.hengtiansoft.wrw.enums.ProductTypeEnum;

@Service
public class ValidateServiceImpl implements ValidateService {

    @Autowired
    private PProductDao pProductDao;
    @Autowired
    private SUserDao sUserDao;
    @Autowired
    private PShiefDao pShiefDao;
    @Autowired
    private PCategoryDao pCategoryDao;
    @Autowired
    private SOrgDao sOrgDao;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MOrderDetailDao mOrderDetailDao;
    @Autowired
    private MOrderMainDao mOrderMainDao;
    @Autowired
    private ActivityStockDao activityStockDao;

    private final Long CATE_ID_FINLA = 0L;

    @SuppressWarnings("unchecked")
    @Override
    public void findList(final ValidateSearchDto dto) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuilder conditionSql = new StringBuilder("");
        StringBuilder countSql = new StringBuilder("");
        StringBuilder sql = new StringBuilder(
                "SELECT P.PRODUCT_ID,P.CATE_ID,(SELECT C.CATE_NAME FROM P_CATEGORY C WHERE P.CATE_ID = C.CATE_ID) FIRST_CATE_NAME,"
                        + "(SELECT C.CATE_NAME  FROM P_CATEGORY C WHERE C.CATE_ID =(SELECT C.PARENT_ID FROM P_CATEGORY C WHERE P.CATE_ID = C.CATE_ID AND C.PARENT_ID != 0)) SECOND_CATE_NAME,P.ORG_ID,"
                        + "(SELECT O.ORG_NAME FROM S_ORG O WHERE P.ORG_ID = O.ORG_ID) ORG_NAME,"
                        + "P.PRODUCT_NAME,P.PRICE,P.CREATE_DATE,P.PRODUCT_CODE,"
                        + "(SELECT S.SALE_FLAG FROM (SELECT S.PRODUCT_ID,S.SALE_FLAG FROM P_SHIEF S group by S.PRODUCT_ID HAVING COUNT(*) > 0) S WHERE P.PRODUCT_ID = S.PRODUCT_ID) SALE_FLAG,"
                        + "AC.LOW_PRICE,AC.HIGH_PRICE,P.TYPE_ID,P.IS_CAPTCHA "
                        + "FROM P_PRODUCT P  LEFT JOIN ACT_STOCK AC ON P.PRODUCT_ID = AC.PRODUCT_ID WHERE 1=1 ");
        conditionSql.append("AND P.STATUS = " + ProductStatusEnum.USED.getCode());
        countSql.append("select count(1) from (").append(sql);
        // 商品名称
        if (!WRWUtil.isEmpty(dto.getProductName())) {
            String msg = dto.getProductName();
            char escape = '\\';
            msg =msg.replace("\\", escape+"\\");
            msg =msg.replace("%", escape+"%");
            msg =msg.replace("_", escape+"_");
            conditionSql.append(" AND REPLACE(P.PRODUCT_NAME,' ','') LIKE REPLACE(:NAME,' ','') escape '\\\\' ");
            param.put("NAME", "%"+msg+"%");  
        }
        // 商品品类
        if (dto.getSecondCategory() == null) {
            if (dto.getFirstCategory() != null && !(CATE_ID_FINLA.equals(dto.getFirstCategory()))) {
                List<Long> cateIds = new ArrayList<Long>();
                cateIds.add(dto.getFirstCategory());
                List<PCategoryEntity> categorys = pCategoryDao.findByParentId(dto.getFirstCategory());
                for (PCategoryEntity entity : categorys) {
                    cateIds.add(entity.getCateId());
                }
                conditionSql.append(" AND P.CATE_ID IN :IDS");
                param.put("IDS", cateIds);
            }
        } else {

            if (dto.getSecondCategory() != null) {
                conditionSql.append(" AND P.CATE_ID = :ID");
                param.put("ID", dto.getSecondCategory());
            }
        }
        // 服务商
        UserInfo userInfo = AuthorityContext.getCurrentUser();
        if (userInfo != null) {
            SUserEntity user = sUserDao.findOne(userInfo.getUserId());
            if (!user.getOrgId().equals(0L)) {
                conditionSql.append(" AND P.ORG_ID = :ORG");
                param.put("ORG", user.getOrgId());
            }
        }
        // 商品价格
        if (WRWUtil.isNotEmpty(dto.getMinPrice())) {
            if (!validateNum(dto.getMinPrice())) {
                return;
            }
            conditionSql.append(" AND AC.HIGH_PRICE >= :LOWPRICE");
            param.put("LOWPRICE", CurrencyUtils.rmbYuanToFen(Double.parseDouble(dto.getMinPrice())));
        }
        if (WRWUtil.isNotEmpty(dto.getMaxPrice())) {
            if (!validateNum(dto.getMaxPrice())) {
                return;
            }
            conditionSql.append(" AND AC.LOW_PRICE <= :HIGHTPRICE");
            param.put("HIGHTPRICE", CurrencyUtils.rmbYuanToFen(Double.parseDouble(dto.getMaxPrice())));
        }
        if (WRWUtil.isNotEmpty(dto.getProductType())) {
            conditionSql.append(" AND P.TYPE_ID = :TYPE");
            param.put("TYPE", dto.getProductType());
        }
        conditionSql.append("  group by P.PRODUCT_ID");
        conditionSql.append(" ORDER BY P.PRODUCT_ID");
        Query query = entityManager.createNativeQuery(sql.append(conditionSql).toString());
        QueryUtil.processParamForQuery(query, param);
        Query countQuery = entityManager.createNativeQuery(countSql.append(conditionSql).append(" ) A").toString());
        QueryUtil.processParamForQuery(countQuery, param);
        BigInteger totalRecord = (BigInteger) countQuery.getSingleResult();
        query.setFirstResult(dto.getPageSize() * (dto.getCurrentPage() - 1));
        query.setMaxResults(dto.getPageSize());
        dto.setTotalRecord(totalRecord.longValue());
        dto.setTotalPages(totalRecord.intValue() % dto.getPageSize() == 0 ? totalRecord.intValue() / dto.getPageSize()
                : ((totalRecord.intValue() / dto.getPageSize()) + 1));
        dto.setList(buildValidateDto(query.getResultList(), dto));
    }

    private List<ProductValidateDto> buildValidateDto(List<Object[]> objects, ValidateSearchDto dto) {
        List<ProductValidateDto> productValidateDtos = new ArrayList<ProductValidateDto>();
        for (Object[] object : objects) {
            ProductValidateDto productValidateDto = new ProductValidateDto();
            productValidateDto.setProductCode(WRWUtil.objToString(object[9]));
            if (object[3] == null || object[3].equals("")) {
                productValidateDto.setCateName(WRWUtil.objToString(object[2]));
            } else {
                productValidateDto.setCateName(WRWUtil.objToString(object[3]) + "-" + WRWUtil.objToString(object[2]));
            }
            productValidateDto.setOrgName(WRWUtil.objToString(object[5]));
            productValidateDto.setProductType(ProductTypeEnum.getValue(WRWUtil.objToLong(object[13])));
            productValidateDto.setProductName(WRWUtil.objToString(object[6]));
            Long lowPrice = object[11] == null ? 0L : WRWUtil.objToLong(object[11]);
            Long highPrice = object[12] == null ? 0L : WRWUtil.objToLong(object[12]);
            if (!lowPrice.equals(0L) && !lowPrice.equals(highPrice)) {
                productValidateDto.setPrice(WRWUtil.transFenToYuan2String(lowPrice) + "~"
                        + WRWUtil.transFenToYuan2String(highPrice));
            } else if (!lowPrice.equals(0L) && lowPrice.equals(highPrice)) {
                productValidateDto.setPrice(WRWUtil.transFenToYuan2String(lowPrice));
            }
            productValidateDto.setCreateDate(object[8] == null ? "" : new SimpleDateFormat("yyyy-M-d HH:mm")
                    .format((Date) object[8]));
            productValidateDto.setProductId(WRWUtil.objToLong(object[0]));
            productValidateDto.setIsCaptcha(WRWUtil.objToLong(object[0]) + ";" + WRWUtil.objToString(object[14]));
            productValidateDtos.add(productValidateDto);
        }
        return productValidateDtos;
    }

    private boolean validateNum(String str) {
        String reg = "^[-+]?[0-9]+(\\.[0-9]+)?$";
        return str.matches(reg);
    }

    @Override
    @Transactional
    public ResultDto<?> doValidate(Long productId, String code) {
        List<MOrderDetailEntity> orderDetails = mOrderDetailDao.findByProductId(productId);
        if (orderDetails.isEmpty()) {
            return ResultDtoFactory.toNack("商品还未有出售记录!");
        }
        MOrderDetailEntity orderDetail = mOrderDetailDao.getByProductIdAndCode(productId, code);
        if (orderDetail == null) {
            return ResultDtoFactory.toNack("验证码错误！");
        } else {
            ActivityStock activityStock = activityStockDao.findOne(orderDetail.getActStockId());
            Date date = new Date();
            if (activityStock != null) {
                if ((DateTimeUtil.isSameDate(activityStock.getActDate(), date) || activityStock.getActDate().getTime() >= date.getTime())) {
                    if (orderDetail.getCodeUsed() != null) {
                        if (orderDetail.getCodeUsed().equals("1")) {
                            return ResultDtoFactory.toNack("验证码已被使用！");
                        } else {
                            return ResultDtoFactory.toNack("验证码已失效！");
                        }
                    } else {
                        mOrderDetailDao.updateCodeUsed(productId, code);
                        return ResultDtoFactory.toAck("验证成功！");
                    }
                } else {
                    return ResultDtoFactory.toNack("验证码已过期！");
                }
            } else {
                if ((DateTimeUtil.isSameDate(orderDetail.getActDate(), date) || orderDetail.getActDate().getTime() >= date.getTime())) {
                    if (orderDetail.getCodeUsed() != null) {
                        if (orderDetail.getCodeUsed().equals("1")) {
                            return ResultDtoFactory.toNack("验证码已被使用！");
                        } else {
                            return ResultDtoFactory.toNack("验证码已失效！");
                        }
                    } else {
                        mOrderDetailDao.updateCodeUsed(productId, code);
                        return ResultDtoFactory.toAck("验证成功！");
                    }
                } else {
                    return ResultDtoFactory.toNack("验证码已过期！");
                }
            }
        }
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
                "SELECT OM.ORDER_ID, OM.CREATE_DATE, "
                        + " OM.PHONE, GROUP_CONCAT(P.PRODUCT_NAME separator '#&#'),SO.ORG_NAME,GROUP_CONCAT(P.TYPE_ID separator '/'),OM.TOTAL_AMOUNT,OM.STATUS "
                        + " FROM M_ORDER_DETAIL OD JOIN P_PRODUCT P "
                        + " ON OD.PRODUCT_ID = P.PRODUCT_ID JOIN M_ORDER_MAIN OM ON OD.ORDER_ID = OM.ORDER_ID "
                        + " JOIN S_ORG SO ON P.ORG_ID = SO.ORG_ID " + " WHERE 1=1");
        countSql.append("select count(1) from (").append(sql);
        // 创建时间
        if (!WRWUtil.isEmpty(dto.getStartTime())) {
            conditionSql.append(" AND OM.CREATE_DATE >= :START ");
            param.put("START", DateTimeUtil.getDayBegin(dto.getStartTime()));
        }
        if (!WRWUtil.isEmpty(dto.getEndDate())) {
            conditionSql.append(" AND OM.CREATE_DATE <= :END ");
            param.put("END", DateTimeUtil.getDayEnd(dto.getEndDate()));
        }
        // 订单号
        if (!WRWUtil.isEmpty(dto.getOrderId())) {
            String msg = dto.getOrderId();
            char escape = '\\';
            msg =msg.replace("\\", escape+"\\");
            msg =msg.replace("%", escape+"%");
            msg =msg.replace("_", escape+"_");
            conditionSql.append(" AND REPLACE(OM.ORDER_ID,' ','') LIKE REPLACE(:ORDER_ID,' ','') escape '\\\\' ");
            param.put("ORDER_ID", "%"+msg+"%");  
        }
        // 手机号
        if (!WRWUtil.isEmpty(dto.getPhone())) {
            if (!validateNum(dto.getPhone())) {
                return;
            }
            String msg = dto.getPhone();
            char escape = '\\';
            msg =msg.replace("\\", escape+"\\");
            msg =msg.replace("%", escape+"%");
            msg =msg.replace("_", escape+"_");
            conditionSql.append(" AND REPLACE(OM.PHONE,' ','') LIKE REPLACE(:PHONE,' ','') escape '\\\\' ");
            param.put("PHONE", "%"+msg+"%");  
        }
        // 商品名称
        if (!WRWUtil.isEmpty(dto.getProductName())) {
            String msg = dto.getProductName();
            char escape = '\\';
            msg =msg.replace("\\", escape+"\\");
            msg =msg.replace("%", escape+"%");
            msg =msg.replace("_", escape+"_");
            conditionSql.append(" AND REPLACE(P.PRODUCT_NAME,' ','') LIKE REPLACE(:NAME,' ','') escape '\\\\' ");
            param.put("NAME", "%"+msg+"%");  
        }
        // 商品类型
        if (!WRWUtil.isEmpty(dto.getTypeId())) {
            conditionSql.append(" AND P.TYPE_ID = :TYPE ");
            param.put("TYPE", dto.getTypeId());
        }
        UserInfo userInfo = AuthorityContext.getCurrentUser();
        if (userInfo != null) {
            SUserEntity user = sUserDao.findOne(userInfo.getUserId());
            if (user != null && !user.getOrgId().equals(0L)) {
                conditionSql.append(" AND P.ORG_ID = :ORG ");
                param.put("ORG", user.getOrgId());
            }
        }
        // 支付状态
        if (!WRWUtil.isEmpty(dto.getStatus())) {
            conditionSql.append(" AND OM.STATUS = :STATUS ");
            param.put("STATUS", dto.getStatus());
        }
        conditionSql.append(" group by OM.ORDER_ID ");
        conditionSql.append(" ORDER BY OM.CREATE_DATE DESC ");
        Query query = entityManager.createNativeQuery(sql.append(conditionSql).toString());
        QueryUtil.processParamForQuery(query, param);
        Query countQuery = entityManager.createNativeQuery(countSql.append(conditionSql).append(" ) A").toString());
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
                String[] typesIds = types.split("/");
                StringBuilder productType = new StringBuilder("");
                for (String typesId : typesIds) {
                    productType.append(ProductTypeEnum.getValue(WRWUtil.objToLong(typesId)).toString() + "/");
                }
                dto.setProductType(productType.toString());
            } else {
                dto.setProductType("/");
            }
            dto.setAmount(WRWUtil.transFenToYuan2String(array[6] == null ? 0L : WRWUtil.objToLong(array[6])));
            dto.setStatus(OrderStatus.getValue(WRWUtil.objToString(array[7])));
            dtos.add(dto);
        }
        return dtos;
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
        fieldName.add(9, "商品单价（元）");
        fieldName.add(10, "购买数量");
        fieldName.add(11, "商家勾选的若干留言字段（商品购买人、手机号等）");
        fieldName.add(12, "买家留言");
        fieldName.add(13, "应付金额（元）");
        fieldName.add(14, "优惠金额（元）");
        fieldName.add(15, "实付金额（元）");
        fieldName.add(16, "支付方式（连连、微信）");
        fieldName.add(17, "订单付款时间");
        fieldName.add(18, "订单流水号");
        fieldName.add(19, "退款金额（元）");
        
        for (int j = 0; j <= fieldName.size(); j++) {
            fieldStyle.add(j, "6000");
        }
        pageDto.setPageSize(65536);
        this.exportSearch(pageDto);
        if (pageDto.getList().size() > 0) {
            for (int i = 0; i < pageDto.getList().size(); i++) {
                OrderManagerExportDto dto = pageDto.getList().get(i);
                String[] productName = dto.getProductName().split(",/,");
                String[] orgName = dto.getOrgName().split(",/,");
                String[] specInfos = dto.getSpecInfo().split(",/,");
                String[] productPrice = dto.getProductPrice().split(",/,");
                String[] num = dto.getNum().split(",/,");
                String[] requireFields = dto.getRequireField().split(",/,");
                String[] remark = dto.getRemark().split(",/,");
                Integer rowNumber =  productName.length;
                orderNum.add(rowNumber);
                for (int j = 0; j<rowNumber; j++) {
                    List<Object> rowData = new ArrayList<Object>();
                    if (j==0) {
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
                            specInfo.append(ja.getJSONObject(a).getString("subSpec")+"  ");
                        }
                        
                        rowData.add(8, specInfo.toString());
                        rowData.add(9, WRWUtil.transFenToYuan2String(WRWUtil.objToLong(productPrice[j])));
                        rowData.add(10, num[j]);
                        
                        if(j < requireFields.length){
                            if (!requireFields[j].equals("")) {
                                JSONArray jb = JSONArray.fromObject(requireFields[j]);
                                StringBuilder requireField = new StringBuilder("");
                                for (int a = 0; a < jb.size(); a++) {
                                    requireField.append(jb.getJSONObject(a).getString("key")+":"+jb.getJSONObject(a).getString("value")+";");
                                }
                                rowData.add(11, requireField.toString());
                            } else {
                                rowData.add(11, "");
                            }
                           }
                            
                        if(j < remark.length){
                            rowData.add(12, remark[j]);
                        } else {
                            rowData.add(12, "");
                        }
                        rowData.add(13, dto.getTotalAmunt());
                        rowData.add(14, dto.getCouponAmunt());
                        rowData.add(15, dto.getActualAmount());
                        rowData.add(16, dto.getPaymentType());
                        rowData.add(17, dto.getPayDate());
                        rowData.add(18, dto.getTSN());
                        rowData.add(19, dto.getReturnAmunt());
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
                            specInfo.append(ja.getJSONObject(a).getString("subSpec")+"  ");
                        }
                        
                        rowData.add(8, specInfo.toString());
                        rowData.add(9, WRWUtil.transFenToYuan2String(WRWUtil.objToLong(productPrice[j])));
                        rowData.add(10, num[j]);
                        
                        if(j < requireFields.length){
                            JSONArray jb = JSONArray.fromObject(requireFields[j]);
                            StringBuilder requireField = new StringBuilder("");
                            for (int a = 0; a < jb.size(); a++) {
                                requireField.append(jb.getJSONObject(a).getString("key")+":"+jb.getJSONObject(a).getString("value")+";");
                            }
                            rowData.add(11, requireField.toString());
                        } else {
                            rowData.add(11, "");
                        }
                        
                        if(j < remark.length){
                            rowData.add(12, remark[j]);
                        } else {
                            rowData.add(12, "");
                        }
                        rowData.add(13, "");
                        rowData.add(14, "");
                        rowData.add(15, "");
                        rowData.add(16, "");
                        rowData.add(17, "");
                        rowData.add(18, "");
                        rowData.add(19, "");
                        fieldData.add(rowData);
                    }
                }                 
            }
        }else {
            List<Object> rowData = new ArrayList<Object>();
            fieldData.add(rowData);
        }
        sheetName.add(0, "订单列表");
        ExportExcleUtil ex = new ExportExcleUtil(fieldName, sheetName, fieldData, fieldStyle);
        HSSFWorkbook wb = ex.createWorkbook();
        Integer sum = 1;
        if (CollectionUtils.isNotEmpty(orderNum)) {
            for (Integer num : orderNum) {
                for (int i = 0; i <= 5; i++) {
                    wb.getSheetAt(0).addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(sum, sum+num-1, i, i));
                }
                for (int i = 13; i <= 19; i++) {
                    wb.getSheetAt(0).addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(sum, sum+num-1, i, i));
                }
                sum += num;
            }
        } 
        return wb;
    }

    @SuppressWarnings("unchecked")
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
                        + "GROUP_CONCAT(OD.PRICE SEPARATOR ',/,') PRICE,GROUP_CONCAT(OD.REMARK SEPARATOR ',/,') REMARK,"
                        + "CONCAT(',', GROUP_CONCAT(P.TYPE_ID SEPARATOR ','),',') TYPE_ID,CONCAT(',', GROUP_CONCAT(SO.ORG_ID SEPARATOR ','),',') ORG_ID "
                        + "FROM M_ORDER_MAIN O LEFT JOIN M_ORDER_DETAIL OD ON O.ORDER_ID = OD.ORDER_ID "
                        + "LEFT JOIN P_PRODUCT P ON P.PRODUCT_ID = OD.PRODUCT_ID "
                        + "LEFT JOIN S_ORG SO ON SO.ORG_ID = P.ORG_ID " + "LEFT JOIN M_MEMBER M ON M.ID = O.MEMBER_ID "
                        + "LEFT JOIN M_ORDER_RETURN R ON O.ORDER_ID = R.ORDER_MAIN_ID "
                        + "LEFT JOIN S_REGION S ON O.REGION_ID = S.ID WHERE 1=1 ");
        UserInfo userInfo = AuthorityContext.getCurrentUser();
        if (userInfo != null) {
            SUserEntity user = sUserDao.findOne(userInfo.getUserId());
            if (user != null && !user.getOrgId().equals(0L)) {
                conditionSql.append("AND SO.ORG_ID = :ORG ");
                param.put("ORG", user.getOrgId());
            }
        }
        if (dto.getOrderIds().equals("")) {
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
            // 手机号
            if (!WRWUtil.isEmpty(dto.getPhone())) {
                if (!validateNum(dto.getPhone())) {
                    return;
                }
                String msg = "%" + dto.getPhone() + "%";
                conditionSql.append("AND O.PHONE LIKE :PHONE ");
                param.put("PHONE", msg);
            }
            // 支付状态
            if (!WRWUtil.isEmpty(dto.getStatus())) {
                    conditionSql.append(" AND O.STATUS = :STATUS ");
                    param.put("STATUS", dto.getStatus());  
            }
        }
        conditionSql.append("group by O.ORDER_ID ");
        conditionSql.append("ORDER BY O.CREATE_DATE DESC) ORD WHERE 1=1 ");
        
        if (!dto.getOrderIds().equals("")) {
            String[] orderIds = dto.getOrderIds().split(",");
            List<String> orderIdList = new ArrayList<String>();
            Collections.addAll(orderIdList, orderIds);
            conditionSql.append("AND ord.ORDER_ID IN :ORDER_IDS ");
            param.put("ORDER_IDS", orderIdList);
        } else {
            // 商品名称
            if (!WRWUtil.isEmpty(dto.getProductName())) {
                String msg = "%" + StringEscapeUtils.unescapeHtml(dto.getProductName()) + "%";
                conditionSql.append("AND ord.PRODUCT_NAME LIKE :NAME ");
                param.put("NAME", msg);
            }
            // 商品类型
            if (!WRWUtil.isEmpty(dto.getTypeId())) {
                String msg = "%," + dto.getTypeId() + ",%";
                conditionSql.append("AND ord.TYPE_ID LIKE :TYPE ");
                param.put("TYPE", msg);
            }
        }
        
        Query query = entityManager.createNativeQuery(sql.append(conditionSql).toString());
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
            dtos.add(dto);
        }
        return dtos;
    }

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

        sbSql.append(" SELECT p.PRODUCT_CODE,p.PRODUCT_NAME,org.ORG_NAME,od.PRICE,acs.ACT_DATE,od.SPEC_INFO,od.PERSONAL_INFO,od.REMARK ");
        sbSql.append(" FROM m_order_detail od, p_product p,s_org org,act_stock acs ");
        sbSql.append(" WHERE od.PRODUCT_ID = p.PRODUCT_ID AND p.ORG_ID = org.ORG_ID and acs.ID =od.ACT_STOCK_ID");

        if (listDto.getOrderId() != null) {
            sbSql.append(" AND od.ORDER_ID =:orderId ");
            params.put("orderId", listDto.getOrderId());
        }
        UserInfo userInfo = AuthorityContext.getCurrentUser();
        if (userInfo != null) {
            SUserEntity user = sUserDao.findOne(userInfo.getUserId());
            if (user != null && !user.getOrgId().equals(0L)) {
                sbSql.append(" AND p.org_id =:orgId ");
                params.put("orgId", user.getOrgId());
            }
        }

        Query query = entityManager.createNativeQuery(sbSql.toString());
        QueryUtil.processParamForQuery(query, params);

        return buildDetailDtoLists(query.getResultList());
    }

    private List<OrderDetailDto> buildDetailDtoLists(List<Object[]> objs) {
        List<OrderDetailDto> dtos = new ArrayList<OrderDetailDto>();

        if (CollectionUtils.isNotEmpty(objs)) {
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
}
