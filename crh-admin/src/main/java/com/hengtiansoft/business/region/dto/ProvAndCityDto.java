/*
 * Project Name: wrw-admin
 * File Name: ProvAndCityDto.java
 * Class Name: ProvAndCityDto
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
import java.util.ArrayList;
import java.util.List;

/**
 * Class Name: ProvAndCityDto
 * Description: TODO
 * 
 * @author chengminmiao
 */
public class ProvAndCityDto implements Serializable {

    private static final long serialVersionUID = 555793964632858368L;

    private Integer           provId;

    private String            provName;

    private String            firstLetter;

    List<RegionDto>           cityDtos         = new ArrayList<RegionDto>();

    public Integer getProvId() {
        return provId;
    }

    public void setProvId(Integer provId) {
        this.provId = provId;
    }

    public String getProvName() {
        return provName;
    }

    public void setProvName(String provName) {
        this.provName = provName;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public List<RegionDto> getCityDtos() {
        return cityDtos;
    }

    public void setCityDtos(List<RegionDto> cityDtos) {
        this.cityDtos = cityDtos;
    }

    public void setCityDto(RegionDto cityDto) {
        if (null == this.cityDtos) {
            cityDtos = new ArrayList<RegionDto>();
        }
        this.cityDtos.add(cityDto);
    }

}
