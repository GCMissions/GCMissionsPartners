/*
 * Project Name: wrw-admin
 * File Name: OfflineQueryProductReqDto.java
 * Class Name: OfflineQueryProductReqDto
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
package com.hengtiansoft.business.offline.dto;

import java.io.Serializable;

/**
 * Class Name: OfflineQueryProductReqDto
 * Description: TODO
 * @author xiaoluzhou
 *
 */
public class OfflineQueryProductReqDto implements Serializable{

    private static final long serialVersionUID = 2450157815492917410L;
    
    //二维码或者商品名称，二维码以http开头
    private String condition;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}

