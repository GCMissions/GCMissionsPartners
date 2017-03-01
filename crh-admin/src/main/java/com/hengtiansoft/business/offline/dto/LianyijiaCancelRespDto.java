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
public class LianyijiaCancelRespDto  implements Serializable{

    private static final long serialVersionUID = -7223773958017410167L;

    private LianyijiaCancelRespData linkea_pays_query_sendCancel_response;

    public LianyijiaCancelRespData getLinkea_pays_query_sendCancel_response() {
        return linkea_pays_query_sendCancel_response;
    }

    public void setLinkea_pays_query_sendCancel_response(LianyijiaCancelRespData linkea_pays_query_sendCancel_response) {
        this.linkea_pays_query_sendCancel_response = linkea_pays_query_sendCancel_response;
    }

}
