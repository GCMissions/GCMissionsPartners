/*
 * Project Name: wrw-admin
 * File Name: MposPayActionListener.java
 * Class Name: MposPayActionListener
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
package com.hengtiansoft.business.common.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hengtiansoft.business.barcode.dto.BarcodeStorageDto;
import com.hengtiansoft.business.barcode.service.BarcodeService;
import com.hengtiansoft.business.service.OrderPaymentService;
import com.hengtiansoft.wrw.enums.BarcodeSourceEnum;
import com.hengtiansoft.wrw.enums.PaymentType;
import com.hengtiansoft.wrw.enums.SBasicCodeCycleStatusEnum;

/**
 * Class Name: MposPayActionListener
 * Description:
 * 
 * @author xiaoluzhou
 */
@Component
public class OfflinePayActionListener {

    @Autowired
    private OrderPaymentService orderPaymentService;

    @Autowired
    private BarcodeService      barcodeService;

    public void onPaySuccess(List<String> prefixCodes, String orderNo, String tsn, Long amount, String paymentType) {
        // 二维码流水
        if (CollectionUtils.isNotEmpty(prefixCodes)) {
            BarcodeStorageDto sdto = new BarcodeStorageDto();
            sdto.setSource(BarcodeSourceEnum.MPOS_SOURCE.getCode());
            sdto.setOrderId(orderNo);
            sdto.setStatus(SBasicCodeCycleStatusEnum.Z_C_SHIPMENT.getKey());
            barcodeService.saveBarcodeCycle(prefixCodes, sdto);
        }

//        if (PaymentType.ALIPAY.getKey().equals(paymentType)) {
//            orderPaymentService.aliPayPaidConfirm(orderNo, tsn, amount);
//        } else if (PaymentType.WECHAT.getKey().equals(paymentType)) {
//            orderPaymentService.wechatPaidConfirm(orderNo, tsn, amount);
//        } else if (PaymentType.LIANYIJIA.getKey().equals(paymentType)) {
//            orderPaymentService.lianyijiaPaidConfirm(orderNo, tsn, amount);
//        }
    }

}
