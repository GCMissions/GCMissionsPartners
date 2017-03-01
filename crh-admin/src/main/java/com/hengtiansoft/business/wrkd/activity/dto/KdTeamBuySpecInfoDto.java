/*
 * Project Name: kd-wechat
 * File Name: KdTeamBuySpecInfoDto.java
 * Class Name: KdTeamBuySpecInfoDto
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
package com.hengtiansoft.business.wrkd.activity.dto;

import java.io.Serializable;

/**
 * Class Name: KdTeamBuySpecInfoDto
 * Description: 团购详情规格信息表
 * @author zhisongliu
 *
 */
public class KdTeamBuySpecInfoDto implements Serializable{

    private static final long serialVersionUID = 4304225373791022931L;
    
    
    private String specName;
    
    private String specValue;

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getSpecValue() {
        return specValue;
    }

    public void setSpecValue(String specValue) {
        this.specValue = specValue;
    }

    
    
}
