/*
 * Project Name: wrw-admin
 * File Name: LianyijiaBalanceQueryRespDto.java
 * Class Name: LianyijiaBalanceQueryRespDto
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
 * Class Name: LianyijiaBalanceQueryRespDto
 * Description: 
 * @author xiaoluzhou
 *
 */
public class LianyijiaBalanceQueryRespDto implements Serializable{

    private static final long serialVersionUID = 403223299388035754L;
    
    private LianyijiaBalanceQueryRespData linkea_pays_query_balance_response;

    public LianyijiaBalanceQueryRespData getLinkea_pays_query_balance_response() {
        return linkea_pays_query_balance_response;
    }

    public void setLinkea_pays_query_balance_response(LianyijiaBalanceQueryRespData linkea_pays_query_balance_response) {
        this.linkea_pays_query_balance_response = linkea_pays_query_balance_response;
    }
}
