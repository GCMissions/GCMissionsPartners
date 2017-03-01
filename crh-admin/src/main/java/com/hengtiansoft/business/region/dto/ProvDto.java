/*
 * Project Name: wrw-admin
 * File Name: ProvDto.java
 * Class Name: ProvDto
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

/**
 * Class Name: ProvDto
 * Description: TODO
 * 
 * @author chengminmiao
 */
public class ProvDto extends RegionDto {

    private static final long serialVersionUID = 3895215155308443745L;

    private Integer           cityNum;

    private Integer           openCityNum;

    public Integer getCityNum() {
        return cityNum;
    }

    public void setCityNum(Integer cityNum) {
        this.cityNum = cityNum;
    }

    public Integer getOpenCityNum() {
        return openCityNum;
    }

    public void setOpenCityNum(Integer openCityNum) {
        this.openCityNum = openCityNum;
    }

}
