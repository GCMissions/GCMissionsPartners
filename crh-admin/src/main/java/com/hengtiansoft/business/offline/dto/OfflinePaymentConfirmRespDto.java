/*
 * Project Name: wrw-admin
 * File Name: OfflinePaymentConfirmRespDto.java
 * Class Name: OfflinePaymentConfirmRespDto
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
 * Class Name: OfflinePaymentConfirmRespDto
 * Description: 
 * @author xiaoluzhou
 *
 */
public class OfflinePaymentConfirmRespDto implements Serializable{
    
    private static final long serialVersionUID = -8702722101566618529L;
    
    private String code = "FAIL";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    
}
