package com.hengtiansoft.business.offline.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePayRequestBuilder;
import com.alipay.demo.trade.model.builder.AlipayTradeQueryRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPayResult;
import com.alipay.demo.trade.model.result.AlipayF2FQueryResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.hengtiansoft.business.common.service.OfflinePayActionListener;
import com.hengtiansoft.business.common.util.ProductUtil;
import com.hengtiansoft.business.common.util.UserUtil;
import com.hengtiansoft.business.offline.dto.AliTradeQueryReqDto;
import com.hengtiansoft.business.offline.dto.AlipayReqDto;
import com.hengtiansoft.business.offline.dto.AlipayRespDto;
import com.hengtiansoft.business.offline.service.AlipayService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.exception.BizServiceException;
import com.hengtiansoft.wrw.dao.MOrderDetailDao;
import com.hengtiansoft.wrw.dao.MOrderMainDao;
import com.hengtiansoft.wrw.entity.MOrderDetailEntity;
import com.hengtiansoft.wrw.entity.MOrderMainEntity;
import com.hengtiansoft.wrw.enums.PaymentType;

/**
 * Class Name: MposAlipaySerivceImpl
 * Description: Mpos支付宝扫码支付service
 * 
 * @author yigesong
 */
@Service
public class AlipaySerivceImpl implements AlipayService {

    private static final Logger       LOGGER = LoggerFactory.getLogger(AlipaySerivceImpl.class);

    private static String             sellerId;

    private static AlipayTradeService tradeService;

    @Autowired
    private MOrderMainDao             orderMainDao;

    @Autowired
    private MOrderDetailDao           mOrderDetailDao;

    @Autowired
    private OfflinePayActionListener  payActionListener;

    static {

        Configs.init("zfbinfo.properties");

        sellerId = Configs.getConfigs().getString("alipay.seller_id");

        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
    }

    @Override
    public AlipayRespDto alipay(AlipayReqDto reqDto) {

        String orderId = reqDto.getOrderId();

        MOrderMainEntity orderMain = orderMainDao.findOne(orderId);
        if (orderMain == null) {
            throw new BizServiceException("未找到对应的订单");
        }

//        orderMain.setPaymentType(PaymentType.ALIPAY.getKey());
        orderMainDao.save(orderMain);
        // 获取订单描述
        List<MOrderDetailEntity> details = mOrderDetailDao.findByOrderId(orderId);

        // (必填) 付款条码，用户支付宝钱包手机app点击“付款”产生的付款条码
        String authCode = reqDto.getAuthCode();

        // 创建条码支付请求builder，设置请求参数
        AlipayTradePayRequestBuilder builder = new AlipayTradePayRequestBuilder().setOutTradeNo(orderId).setSubject(generateProductSummary(details))
                .setAuthCode(authCode).setTotalAmount((new Double(orderMain.getActualAmount()) / 100) + "").setStoreId(UserUtil.getStoreInfo())
                .setBody(generateProductDetail(details)).setOperatorId(AuthorityContext.getCurrentUser().getUserId() + "")
                .setGoodsDetailList(generateProductDetail1(details)).setTimeoutExpress("5m").setSellerId(sellerId);

        AlipayRespDto dto = new AlipayRespDto();
        // 调用tradePay方法获取当面付应答
        AlipayF2FPayResult result = tradeService.tradePay(builder);
        if (result != null) {
            LOGGER.info("支付宝支付返回结果：" + result.getResponse().getBody());
            switch (result.getTradeStatus()) {
            case SUCCESS:
                LOGGER.info("支付宝支付成功: )");
                dto.setCode("SUCCESS");
                dto.setMessage(result.getResponse().getSubMsg());
//                payActionListener.onPaySuccess(ProductUtil.getPrefixCodes(details), orderId, result.getResponse().getTradeNo(),
//                        new Double(Double.parseDouble(result.getResponse().getTotalAmount()) * 100).longValue(), PaymentType.ALIPAY.getKey());
                return dto;

            case FAILED:
                LOGGER.info("支付宝支付失败!!!");
                dto.setCode("FAIL");
                dto.setMessage(result.getResponse().getSubMsg());
                return dto;

            case UNKNOWN:
                LOGGER.error("系统异常，订单状态未知!!!");
                dto.setCode("UNKNOWN");
                dto.setMessage("系统异常，订单状态未知!!!");
                return dto;

            default:
                throw new BizServiceException("不支持的交易状态，交易返回异常!!!");
            }
        } else {
            LOGGER.error("支付接口返回异常，订单状态未知!!!");
            dto.setCode("UNKNOWN");
            dto.setMessage("系统异常，订单状态未知!!!");
        }

        return dto;

    }

    /**
     * Description：生成产品概要描述
     */
    private String generateProductSummary(List<MOrderDetailEntity> details) {
        StringBuilder sb = new StringBuilder();

        if (!details.isEmpty()) {
            sb.append(details.get(0).getProductName());
            if (details.size() > 1) {
                sb.append("等多件");
            }
        }

        return sb.toString();
    }

    /**
     * Description：生成产品详情描述
     */
    private String generateProductDetail(List<MOrderDetailEntity> details) {
        StringBuilder sb = new StringBuilder();

        for (MOrderDetailEntity detail : details) {
            sb.append(detail.getProductName());
            sb.append("x").append(detail.getNum()).append("件");
            sb.append(" ");
        }

        if (sb.length() > 500) {
            return sb.substring(0, 500);
        }
        return sb.toString();
    }

    /**
     * Description：生成产品详情描述
     */
    private List<GoodsDetail> generateProductDetail1(List<MOrderDetailEntity> details) {
        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();

        GoodsDetail goods = null;

        for (MOrderDetailEntity detail : details) {
            goods = GoodsDetail.newInstance(String.valueOf(detail.getProductId()), detail.getProductName(), detail.getAmount(), detail.getNum());
            goodsDetailList.add(goods);
        }

        return goodsDetailList;
    }

    @Override
    public AlipayTradeQueryResponse tradeQuery(AliTradeQueryReqDto reqDto) {
        // 创建查询请求builder，设置请求参数
        AlipayTradeQueryRequestBuilder builder = new AlipayTradeQueryRequestBuilder().setOutTradeNo(reqDto.getOutTradeNo()).setTradeNo(
                reqDto.getTradeNo());

        AlipayF2FQueryResult result = tradeService.queryTradeResult(builder);
        return result.getResponse();

    }

}
