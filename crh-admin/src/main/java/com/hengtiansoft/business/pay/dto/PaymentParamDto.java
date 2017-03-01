/*
 * Project Name: k12-web
 * File Name: PaymentParamDto.java
 * Class Name: PaymentParamDto
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
package com.hengtiansoft.business.pay.dto;

/**
 * Class Name: PaymentParamDto
 * Description: 支付相关参数
 * @author zhongyidong
 *
 */
public class PaymentParamDto {
    
private Long memberId;
    
    // 1-普通商品 ，2-团购商品，3-24小时
    private String buyType;
    
    // 订单编号 
    private String orderNo;
    
    // 团购记录编号
    private Long recordId;
    
    // 原支付订单的queryId
    private String origQryId;
    
    // 账户卡号
    private String accNo;
    
    // 交易金额
    private String txnAmount;
    
    // 手机号码
    private String phoneNo;
    
    // 短信验证码
    private String smsCode;
    
    public PaymentParamDto (String orderNo, String txnAmount, String origQryId) {
        this.orderNo = orderNo;
        this.txnAmount = txnAmount;
        this.origQryId = origQryId;
    }
    
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getBuyType() {
        return buyType;
    }

    public void setBuyType(String buyType) {
        this.buyType = buyType;
    }
    
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }
    
    public String getOrigQryId() {
        return origQryId;
    }

    public void setOrigQryId(String origQryId) {
        this.origQryId = origQryId;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(String txnAmount) {
        this.txnAmount = txnAmount;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    
}
