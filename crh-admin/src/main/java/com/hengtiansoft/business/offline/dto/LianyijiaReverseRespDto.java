/*
 * Project Name: wrw-admin
 * File Name: LianyijiaReverseRespDto.java
 * Class Name: LianyijiaReverseRespDto
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
 * Class Name: LianyijiaReverseRespDto
 * Description: 
 * @author xiaoluzhou
 *
 */
public class LianyijiaReverseRespDto implements Serializable{
    
    private static final long serialVersionUID = -6845934421975481301L;

    private LianyijiaReverseRespData linkea_pays_query_sendReverse_response;

    public LianyijiaReverseRespData getLinkea_pays_query_sendReverse_response() {
        return linkea_pays_query_sendReverse_response;
    }

    public void setLinkea_pays_query_sendReverse_response(LianyijiaReverseRespData linkea_pays_query_sendReverse_response) {
        this.linkea_pays_query_sendReverse_response = linkea_pays_query_sendReverse_response;
    }
    
}
