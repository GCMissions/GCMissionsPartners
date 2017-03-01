/*
 * Project Name: wrw-admin
 * File Name: LianyijiaReverseRespData.java
 * Class Name: LianyijiaReverseRespData
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
 * Class Name: LianyijiaReverseRespData
 * Description: 
 * @author xiaoluzhou
 *
 */
public class LianyijiaReverseRespData extends LianyijiaRespBaseDto implements Serializable{
    
    private static final long serialVersionUID = 346121679741575425L;

    private String orderNo;
    
    private String referNo;
    
    private String orgCode;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getReferNo() {
        return referNo;
    }

    public void setReferNo(String referNo) {
        this.referNo = referNo;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @Override
    public String toString() {
        return "LianyijiaReverseRespData [orderNo=" + orderNo + ", referNo=" + referNo + ", orgCode=" + orgCode
                + ", getRespMsg()=" + getRespMsg() + ", getRespCode()=" + getRespCode() + "]";
    }
    
}
