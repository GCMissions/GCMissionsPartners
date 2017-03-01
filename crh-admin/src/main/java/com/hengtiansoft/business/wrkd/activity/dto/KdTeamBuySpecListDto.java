/*
 * Project Name: kd-wechat
 * File Name: KdTeamBuySpecListDto.java
 * Class Name: KdTeamBuySpecListDto
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
import java.util.List;

/**
 * Class Name: KdTeamBuySpecListDto
 * Description: 规格信息封装返回给前端
 * @author zhisongliu
 *
 */
public class KdTeamBuySpecListDto implements Serializable{

    
    private static final long serialVersionUID = -1646539963243970709L;
    
    private String mainSpec;
    
    private List<String> subSpecs;

    public String getMainSpec() {
        return mainSpec;
    }

    public void setMainSpec(String mainSpec) {
        this.mainSpec = mainSpec;
    }

    public List<String> getSubSpecs() {
        return subSpecs;
    }

    public void setSubSpecs(List<String> subSpecs) {
        this.subSpecs = subSpecs;
    }

    
    
    

}
