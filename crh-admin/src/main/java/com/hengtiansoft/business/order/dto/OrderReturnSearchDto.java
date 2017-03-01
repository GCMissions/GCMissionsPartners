/*
 * Project Name: wrw-admin
 * File Name: OrderReturnSearchDto.java
 * Class Name: OrderReturnSearchDto
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
package com.hengtiansoft.business.order.dto;

import com.hengtiansoft.common.dto.PagingDto;

/**
 * Class Name: OrderReturnSearchDto
 * Description:
 * 
 * @author xianghuang
 */
public class OrderReturnSearchDto extends PagingDto<OrderReturnDto> {
    /**
     * Variables Name: serialVersionUID
     * Description: TODO
     * Value Description: TODO
     */
    private static final long serialVersionUID = 6280161138610999733L;

    // 订单编号
    private String            orderId;

    // 开始时间
    private String            startDate;

    // 结束时间
    private String            endDate;

    // 配送方式
    private String            shipmentType;

    // 联系方式
    private String            phone;

    // 订单状态
    private String            status;

    // 订单金额
    private String            orderAmount;

    // 退款金额
    private String            returnOrderAmount;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(String shipmentType) {
        this.shipmentType = shipmentType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getReturnOrderAmount() {
        return returnOrderAmount;
    }

    public void setReturnOrderAmount(String returnOrderAmount) {
        this.returnOrderAmount = returnOrderAmount;
    }

}
