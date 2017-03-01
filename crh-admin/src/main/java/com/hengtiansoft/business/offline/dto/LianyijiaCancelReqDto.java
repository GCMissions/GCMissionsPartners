/*
 * Project Name: wrw-admin
 * File Name: LianyijiaCancelReqDto.java
 * Class Name: LianyijiaCancelReqDto
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
package com.hengtiansoft.business.offline.dto;

/**
 * Class Name: LianyijiaCancelReqDto
 * Description: 
 * @author xiaoluzhou
 *
 */
public class LianyijiaCancelReqDto extends LianyijiaBaseApplicationReqDto{
    
    private static final long serialVersionUID = 3234267534984312941L;

    //金额（元）
    private String amount;
    
    private String memo;
    
    //撤销交易订单号，系统自己生成
    private String orderNo;
    
    //需要撤销的订单号
    private String origOrderNo;
    
    //原交易参考号
    private String origReferNo;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrigOrderNo() {
        return origOrderNo;
    }

    public void setOrigOrderNo(String origOrderNo) {
        this.origOrderNo = origOrderNo;
    }

    public String getOrigReferNo() {
        return origReferNo;
    }

    public void setOrigReferNo(String origReferNo) {
        this.origReferNo = origReferNo;
    }

}
