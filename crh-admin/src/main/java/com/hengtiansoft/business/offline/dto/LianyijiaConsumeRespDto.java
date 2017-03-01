/*
 * Project Name: wrw-admin
 * File Name: LianyijiaConsumeRespDto.java
 * Class Name: LianyijiaConsumeRespDto
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
 * Class Name: LianyijiaConsumeRespDto
 * Description: 
 * @author xiaoluzhou
 *
 */
public class LianyijiaConsumeRespDto implements Serializable{

    private static final long serialVersionUID = -2798361235322327119L;

    private LianyijiaConsumeRespData linkea_pays_query_sendConsume_response;

    public LianyijiaConsumeRespData getLinkea_pays_query_sendConsume_response() {
        return linkea_pays_query_sendConsume_response;
    }

    public void setLinkea_pays_query_sendConsume_response(LianyijiaConsumeRespData linkea_pays_query_sendConsume_response) {
        this.linkea_pays_query_sendConsume_response = linkea_pays_query_sendConsume_response;
    }
}
