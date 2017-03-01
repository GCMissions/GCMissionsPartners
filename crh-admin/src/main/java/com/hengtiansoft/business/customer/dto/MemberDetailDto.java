/*
 * Project Name: wrw-admin
 * File Name: MemberDetailDto.java
 * Class Name: MemberDetailDto
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
package com.hengtiansoft.business.customer.dto;

import java.util.Date;
import java.util.List;

import com.hengtiansoft.wrw.entity.MPointsDetailEntity;

/**
* Class Name: MemberDetailDto
* Description: TODO
* @author xianghuang
*
*/
public class MemberDetailDto {
    
    //消费者编号
    private Long memberNo;
    //消费者姓名
    private String memberName;
    //手机号
    private String phone;
    //注册日期
    private Date registerDate;
    //账户余额
    private String balance;
    //积分
    private Long point;
    
    List<MAddressDto> addresses;
    
    //消费总金额
    private Double consumeNum;
    List<OrderMainDetailDto> orders;
    
    //当前积分
    private Long currentPoint;
    List<MPointsDetailEntity> points;
    
    //当前余额
    private Long currentBalance;
    List<AccountRecordDto> balances;
    
    //当前优惠券
    private Long currentCoupoints;
    List<CouponDetailDto> coupons;
    
    public Long getMemberNo() {
        return memberNo;
    }
    public void setMemberNo(Long memberNo) {
        this.memberNo = memberNo;
    }
    public String getMemberName() {
        return memberName;
    }
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Date getRegisterDate() {
        return registerDate;
    }
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
    public String getBalance() {
        return balance;
    }
    public void setBalance(String balance) {
        this.balance = balance;
    }
    public Long getPoint() {
        return point;
    }
    public void setPoint(Long point) {
        this.point = point;
    }
    public List<MAddressDto> getAddresses() {
        return addresses;
    }
    public void setAddresses(List<MAddressDto> addresses) {
        this.addresses = addresses;
    }
    public Double getConsumeNum() {
        return consumeNum;
    }
    public void setConsumeNum(Double consumeNum) {
        this.consumeNum = consumeNum;
    }
    public List<OrderMainDetailDto> getOrders() {
        return orders;
    }
    public void setOrders(List<OrderMainDetailDto> orders) {
        this.orders = orders;
    }
    public Long getCurrentPoint() {
        return currentPoint;
    }
    public void setCurrentPoint(Long currentPoint) {
        this.currentPoint = currentPoint;
    }
    public List<MPointsDetailEntity> getPoints() {
        return points;
    }
    public void setPoints(List<MPointsDetailEntity> points) {
        this.points = points;
    }
    public Long getCurrentBalance() {
        return currentBalance;
    }
    public void setCurrentBalance(Long currentBalance) {
        this.currentBalance = currentBalance;
    }
    public List<AccountRecordDto> getBalances() {
        return balances;
    }
    public void setBalances(List<AccountRecordDto> balances) {
        this.balances = balances;
    }
    public Long getCurrentCoupoints() {
        return currentCoupoints;
    }
    public void setCurrentCoupoints(Long currentCoupoints) {
        this.currentCoupoints = currentCoupoints;
    }
    public List<CouponDetailDto> getCoupons() {
        return coupons;
    }
    public void setCoupons(List<CouponDetailDto> coupons) {
        this.coupons = coupons;
    }
    
    
    
        
}
