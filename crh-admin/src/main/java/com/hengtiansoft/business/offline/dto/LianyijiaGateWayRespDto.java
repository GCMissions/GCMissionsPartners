/*
 * Project Name: wrw-admin
 * File Name: LianyijiaGateWayRespDto.java
 * Class Name: LianyijiaGateWayRespDto
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
 * Class Name: LianyijiaGateWayRespDto
 * Description: TODO
 * @author xiaoluzhou
 *
 */
public class LianyijiaGateWayRespDto implements Serializable{
    
    private static final long serialVersionUID = 6832238344471311957L;
    
    private LianyijiaGateWayErrorRespData linkea_pays_query_sendConsume_response;

    public LianyijiaGateWayErrorRespData getLinkea_pays_query_sendConsume_response() {
        return linkea_pays_query_sendConsume_response;
    }

    public void setLinkea_pays_query_sendConsume_response(LianyijiaGateWayErrorRespData linkea_pays_query_sendConsume_response) {
        this.linkea_pays_query_sendConsume_response = linkea_pays_query_sendConsume_response;
    }
    
}
