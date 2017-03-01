/*
 * Project Name: wrw-admin
 * File Name: ActivityPartakeDto.java
 * Class Name: ActivityPartakeDto
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
package com.hengtiansoft.business.activity.dto;

/**
 * Class Name: ActivityPartakeDto
 * Description: 活动参与人信息
 * @author zhongyidong
 *
 */
public class ActivityPartakeDto {

    // 子规格
    private String subSpec;
    
    //父规格
    private String mainSpec;
    
    // 最小单位量
    private Integer unitNum; 
    
    // 是否启用
    private boolean enabled;

    public String getSubSpec() {
        return subSpec;
    }

    public void setSubSpec(String subSpec) {
        this.subSpec = subSpec;
    }

    public String getMainSpec() {
        return mainSpec;
    }

    public void setMainSpec(String mainSpec) {
        this.mainSpec = mainSpec;
    }

    public Integer getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(Integer unitNum) {
        this.unitNum = unitNum;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
}
