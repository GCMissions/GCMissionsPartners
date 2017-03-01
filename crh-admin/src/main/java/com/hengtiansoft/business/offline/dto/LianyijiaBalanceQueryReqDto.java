/*
 * Project Name: wrw-admin
 * File Name: LianyijiaBalanceQueryReqDto.java
 * Class Name: LianyijiaBalanceQueryReqDto
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
 * Class Name: LianyijiaBalanceQueryReqDto
 * Description: 
 * @author xiaoluzhou
 *
 */
public class LianyijiaBalanceQueryReqDto extends LianyijiaBaseApplicationReqDto{

    private static final long serialVersionUID = 1253282636618276921L;

    private String icData;

    public String getIcData() {
        return icData;
    }

    public void setIcData(String icData) {
        this.icData = icData;
    }
    
}
