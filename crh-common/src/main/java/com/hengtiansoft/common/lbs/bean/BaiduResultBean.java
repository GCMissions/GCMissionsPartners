/*
 * Project Name: zc-collect-common
 * File Name: BaiduResultBean.java
 * Class Name: BaiduResultBean
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
package com.hengtiansoft.common.lbs.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class Name: BaiduResultBean Description: 百度更新数据返回信息
 * 
 * @author jialiangli
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)  
public class BaiduResultBean {

    private String status;

    private String id;

    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
