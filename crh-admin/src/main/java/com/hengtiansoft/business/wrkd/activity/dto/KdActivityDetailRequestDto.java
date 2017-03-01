/*
 * Project Name: kd-wechat
 * File Name: KdActivityDetailSearchDto.java
 * Class Name: KdActivityDetailSearchDto
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
 * Class Name: KdActivityDetailRequestDto
 * Description: 酷袋活动详情--请求DTO
 * @author zhisongliu
 *
 */
public class KdActivityDetailRequestDto implements Serializable{

    private static final long serialVersionUID = 571640606338803373L;
    
    private Long actId;                //活动ID
    
    private String actType;            //活动分类,团购，24小时，公益
    
    private String source;             //来源，0--专享列表进入，1---订单详情进入  ，2---商品详情进入   3---分享详情进入
    
    private String orderId;            //订单ID

    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public String getActType() {
        return actType;
    }

    public void setActType(String actType) {
        this.actType = actType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    

    
}
