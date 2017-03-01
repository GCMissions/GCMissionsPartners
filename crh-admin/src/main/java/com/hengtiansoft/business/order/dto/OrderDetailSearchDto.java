/*
 * Project Name: wrw-admin
 * File Name: OrderDetailListDto.java
 * Class Name: OrderDetailListDto
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
package com.hengtiansoft.business.order.dto;

import java.io.Serializable;

/**
 * Class Name: OrderDetailListDto Description: TODO
 * 
 * @author kangruan
 *
 */
public class OrderDetailSearchDto implements Serializable {

    private static final long serialVersionUID = 5132628150903017818L;

    private String orderId;

    private Integer currentPage = 1;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

}
