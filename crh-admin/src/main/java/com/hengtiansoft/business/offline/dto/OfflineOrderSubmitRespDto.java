/*
 * Project Name: wrw-admin
 * File Name: MposOrderSubmitRespDto.java
 * Class Name: MposOrderSubmitRespDto
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

import java.io.Serializable;

/**
 * Class Name: MposOrderSubmitRespDto
 * Description: 
 * @author xiaoluzhou
 *
 */
public class OfflineOrderSubmitRespDto implements Serializable{
    
    private static final long serialVersionUID = 5895375935246205582L;

    private String            orderId;

    private Double              actualAmount;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Double actualAmount) {
        this.actualAmount = actualAmount;
    }
    
}
