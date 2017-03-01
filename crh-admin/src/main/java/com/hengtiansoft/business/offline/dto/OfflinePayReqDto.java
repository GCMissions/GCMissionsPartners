/*
 * Project Name: wrw-admin
 * File Name: OfflinePayReqDto.java
 * Class Name: OfflinePayReqDto
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
import java.util.List;

/**
 * Class Name: OfflinePayReqDto
 * Description: 
 * @author xiaoluzhou
 *
 */
public class OfflinePayReqDto implements Serializable{
    
    private static final long serialVersionUID = -475576243629367001L;

    private List<OfflineProductDto> products;
    
    private Long totalAmount;
    
    private String paymentType;
    
    private String authCode;

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public List<OfflineProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<OfflineProductDto> products) {
        this.products = products;
    }
    
}
