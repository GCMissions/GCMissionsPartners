/*
 * Project Name: wrw-admin
 * File Name: LianyijiaGateWayErrorRespData.java
 * Class Name: LianyijiaGateWayErrorRespData
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
 * Class Name: LianyijiaGateWayErrorRespData
 * Description: 
 * @author xiaoluzhou
 *
 */
public class LianyijiaGateWayErrorRespData implements Serializable{
    
    private static final long serialVersionUID = 276887999821214998L;
    
    private String success;
    
    private String result_code;
    
    private String result_code_msg;
    
    public String getSuccess() {
        return success;
    }
    public void setSuccess(String success) {
        this.success = success;
    }
    public String getResult_code() {
        return result_code;
    }
    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }
    public String getResult_code_msg() {
        return result_code_msg;
    }
    public void setResult_code_msg(String result_code_msg) {
        this.result_code_msg = result_code_msg;
    }
    
}
