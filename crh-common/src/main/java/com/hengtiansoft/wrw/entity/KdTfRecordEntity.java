/*
 * Project Name: kd-wechat
 * File Name: KdTfRecord.java
 * Class Name: KdTfRecord
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
package com.hengtiansoft.wrw.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "kd_tf_record")
public class KdTfRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECORD_ID")
    private Long recordId;

    @Column(name = "TF_ID")
    private Long tfId;

    @Column(name = "SHARE_ID")
    private Long shareId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "SHARE_TIME")
    private Date shareTime;// 开始分享时间

    @Column(name = "SHARE_TOTAL")
    private Long shareTotal;// 分享总次数

    @Column(name = "CURRENT_PRICE")
    private Long currentPrice;// 当前价格

    @Column(name = "IS_BASE_PRICE")
    private String isBasePrice;// 是否砍到低价

    @Column(name = "ORDER_ID")
    private String orderId;// 订单ID

    @Column(name = "SPEC_INFO")
    private String specInfo;

    @Column(name = "RETURN_TYPE")
    private String returnType;

    @Column(name = "RETURN_MONEY")
    private Long returnMoney;

    @Column(name = "WELFARE_MONEY")
    private Long welfareMoney;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getTfId() {
        return tfId;
    }

    public void setTfId(Long tfId) {
        this.tfId = tfId;
    }

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getShareTime() {
        return shareTime;
    }

    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }

    public Long getShareTotal() {
        return shareTotal;
    }

    public void setShareTotal(Long shareTotal) {
        this.shareTotal = shareTotal;
    }

    public Long getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Long currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getIsBasePrice() {
        return isBasePrice;
    }

    public void setIsBasePrice(String isBasePrice) {
        this.isBasePrice = isBasePrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSpecInfo() {
        return specInfo;
    }

    public void setSpecInfo(String specInfo) {
        this.specInfo = specInfo;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public Long getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(Long returnMoney) {
        this.returnMoney = returnMoney;
    }

    public Long getWelfareMoney() {
        return welfareMoney;
    }

    public void setWelfareMoney(Long welfareMoney) {
        this.welfareMoney = welfareMoney;
    }

}
