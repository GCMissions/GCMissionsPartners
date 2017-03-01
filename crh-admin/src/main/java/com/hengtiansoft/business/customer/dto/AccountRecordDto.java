/*
 * Project Name: wrw-admin
 * File Name: AccountRecordDto.java
 * Class Name: AccountRecordDto
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
package com.hengtiansoft.business.customer.dto;

import java.util.Date;

/**
 * Class Name: AccountRecordDto
 * Description: 账户充值记录
 * 
 * @author xianghuang
 */
public class AccountRecordDto implements Comparable<AccountRecordDto> {
    // 金额
    private String amount;

    // 类型（消费、充值）
    private String type;

    // 支付类型
    private String paymentType;

    //
    private Date   date;

    // 订单编号
    private String orderId;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public int compareTo(AccountRecordDto arg0) {
        if (null != this.getDate() && null != arg0.getDate() && this.getDate().before(arg0.getDate())) {
            return -1;
        } else {
            return 1;
        }
    }

}
