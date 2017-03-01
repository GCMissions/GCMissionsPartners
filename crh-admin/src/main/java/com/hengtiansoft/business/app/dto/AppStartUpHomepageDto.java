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

import org.apache.commons.lang.StringUtils;

import com.hengtiansoft.wrw.enums.AppSource;

/**
 * Class Name: AppHotAdDto Description: TODO
 * 
 * @author qianqianzhu
 *
 */
public class AppStartUpHomepageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String status;

    private String uploadImageUrl;

    private String height;

    private String width;

    private String version;

    private String mobileType;

    private String mobileTypeStr;

    public String getMobileTypeStr() {
        if (StringUtils.isNotBlank(mobileType)) {
            if (AppSource.android.getCode().equals(mobileType)) {
                return AppSource.android.getSource();
            }
            return AppSource.ios.getSource();
        }
        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUploadImageUrl() {
        return uploadImageUrl;
    }

    public void setUploadImageUrl(String uploadImageUrl) {
        this.uploadImageUrl = uploadImageUrl;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMobileType() {
        return mobileType;
    }

    public void setMobileType(String mobileType) {
        this.mobileType = mobileType;
    }

}
