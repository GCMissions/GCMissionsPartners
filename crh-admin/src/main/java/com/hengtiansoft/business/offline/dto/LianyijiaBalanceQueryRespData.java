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

/**
 * Class Name: LianyijiaBalanceQueryRespDto
 * Description: 
 * @author xiaoluzhou
 *
 */
public class LianyijiaBalanceQueryRespData extends LianyijiaRespBaseDto{

    private static final long serialVersionUID = -5936725584723175815L;

    private String balance_cent;
    
    private String balance_yuan;

    public String getBalance_cent() {
        return balance_cent;
    }

    public void setBalance_cent(String balance_cent) {
        this.balance_cent = balance_cent;
    }

    public String getBalance_yuan() {
        return balance_yuan;
    }

    public void setBalance_yuan(String balance_yuan) {
        this.balance_yuan = balance_yuan;
    }
    
}
