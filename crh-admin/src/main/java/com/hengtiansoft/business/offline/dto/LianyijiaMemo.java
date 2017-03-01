/*
 * Project Name: wrw-admin
 * File Name: LianyijiaMemo.java
 * Class Name: LianyijiaMemo
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
 * Class Name: LianyijiaMemo
 * Description: 
 * @author xiaoluzhou
 *
 */
public class LianyijiaMemo {
    
    private String subMerchant;
    
    private String subTerminal;
    
    private String validThru;

    public String getSubMerchant() {
        return subMerchant;
    }

    public void setSubMerchant(String subMerchant) {
        this.subMerchant = subMerchant;
    }

    public String getSubTerminal() {
        return subTerminal;
    }

    public void setSubTerminal(String subTerminal) {
        this.subTerminal = subTerminal;
    }

    public String getValidThru() {
        return validThru;
    }

    public void setValidThru(String validThru) {
        this.validThru = validThru;
    }
    
}
