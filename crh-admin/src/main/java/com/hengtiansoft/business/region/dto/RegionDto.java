/*
 * Project Name: wrw-admin
 * File Name: RegionDto.java
 * Class Name: RegionDto
 * Copyright 2014 Hengtian Software Inc
 * Licensed under the Hengtiansoft
 * http://www.hengtiansoft.com
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.region.dto;

import java.io.Serializable;

/**
 * Class Name: RegionDto
 * Description: TODO
 * 
 * @author chengminmiao
 */
public class RegionDto implements Serializable {

    private static final long serialVersionUID = -3031342070027939026L;

    private Integer           id;

    private String            name;

    private String            openFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(String openFlag) {
        this.openFlag = openFlag;
    }

}
