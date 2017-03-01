/*
 * Project Name: wrw-admin
 * File Name: ShopStaticDto.java
 * Class Name: ShopStaticDto
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
package com.hengtiansoft.business.shopMall.dto;

import java.io.Serializable;
import java.util.List;

/**
* Class Name: ShopStaticDto
* Description: 静态化选择DTO
* @author zhisongliu
*
*/
public class ShopStaticDto implements Serializable{

    private static final long serialVersionUID = -5922972958465126430L;
    
    private String type;
    
    private List<Integer> list;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }
    
    
    
}
