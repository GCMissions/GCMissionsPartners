/*
 * Project Name: wrw-admin
 * File Name: SpecDto.java
 * Class Name: SpecDto
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

import java.util.List;

/**
 * Class Name: SpecDto
 * Description: 活动规格
 * @author zhongyidong
 *
 */
public class SpecDto {
    
    /**
     * 主规格
     */
    private String mainSpec;
    
    /**
     * 子规格
     */
    private List<String> subSpecList;

    public String getMainSpec() {
        return mainSpec;
    }

    public void setMainSpec(String mainSpec) {
        this.mainSpec = mainSpec;
    }

    public List<String> getSubSpecList() {
        return subSpecList;
    }

    public void setSubSpecList(List<String> subSpecList) {
        this.subSpecList = subSpecList;
    }
    
}
