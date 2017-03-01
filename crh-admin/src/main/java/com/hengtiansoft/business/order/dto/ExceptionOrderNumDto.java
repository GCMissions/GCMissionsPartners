/*
 * Project Name: wrw-admin
 * File Name: ExceptionOrderNumDto.java
 * Class Name: ExceptionOrderNumDto
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
* Class Name: ExceptionOrderNumDto
* 
* Description: 异常订单数量dto
* 
* @author huizhuang
*/
public class ExceptionOrderNumDto implements Serializable {

    private static final long serialVersionUID = 6403408336045670665L;
    
    private Long exceptionOrderNum;
    
    private Long resendOrderNum;

    public Long getExceptionOrderNum() {
        return exceptionOrderNum;
    }

    public void setExceptionOrderNum(Long exceptionOrderNum) {
        this.exceptionOrderNum = exceptionOrderNum;
    }

    public Long getResendOrderNum() {
        return resendOrderNum;
    }

    public void setResendOrderNum(Long resendOrderNum) {
        this.resendOrderNum = resendOrderNum;
    }

    @Override
    public String toString() {
        return "ExceptionOrderNumDto [exceptionOrderNum=" + exceptionOrderNum + ", resendOrderNum=" + resendOrderNum
                + "]";
    }
}
