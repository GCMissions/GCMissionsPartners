/*
 * Project Name: wrw-admin
 * File Name: AppHotAdDto.java
 * Class Name: AppHotAdDto
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
package com.hengtiansoft.business.app.dto;

import java.io.Serializable;

/**
 * Class Name: AppHotAdDto Description: TODO
 * 
 * @author qianqianzhu
 *
 */
public class AppHotAdDto implements Serializable {

    private static final long serialVersionUID = 1L;

    // 上传图片URL
    private String url;

    private String info;

    private String operation;
    // 关联URL
    private String skipUrl;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getSkipUrl() {
        return skipUrl;
    }

    public void setSkipUrl(String skipUrl) {
        this.skipUrl = skipUrl;
    }

}
