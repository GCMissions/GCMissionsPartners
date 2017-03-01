/*
 * Project Name: wrw-admin
 * File Name: OfflinePaymentConfirmReqDto.java
 * Class Name: OfflinePaymentConfirmReqDto
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
 * Class Name: OfflinePaymentConfirmReqDto
 * Description: 
 * @author xiaoluzhou
 *
 */
public class OfflinePaymentConfirmReqDto implements Serializable{
    
    private static final long serialVersionUID = 4285427084848310474L;

    private String orderNo;
    
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    
}
