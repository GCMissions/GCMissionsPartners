/*
 * Project Name: wrw-admin
 * File Name: OrgStockSettingDto.java
 * Class Name: OrgStockSettingDto
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
package com.hengtiansoft.business.shopStock.platformStock.dto;

import java.util.List;

/**
* Class Name: OrgStockSettingDto
* Description: TODO
* @author xianghuang
*
*/
public class OrgStockSettingDto {
    
    List<GoodsStockDto> list;

    public List<GoodsStockDto> getList() {
        return list;
    }

    public void setList(List<GoodsStockDto> list) {
        this.list = list;
    }
    
}
