/*
 * Project Name: wrw-admin
 * File Name: SaveCouponDto.java
 * Class Name: SaveCouponDto
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
package com.hengtiansoft.business.marketing.dto;

import java.util.List;

/**
 * Class Name: SaveCouponDto
 * Description: TODO
 * 
 * @author chengminmiao
 */
public class SaveCouponDto extends CouponDto {

    private static final long serialVersionUID = 8247095973944105058L;

    private List<String>      typeList;

    public List<String> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<String> typeList) {
        this.typeList = typeList;
    }

}
