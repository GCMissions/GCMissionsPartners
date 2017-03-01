/*
 * Project Name: wrw-admin
 * File Name: RefereeRebateDto.java
 * Class Name: RefereeRebateDto
 * Copyright 2014 Hengtian Software Inc
 * Licensed under the Hengtiansoft
 * http://www.hengtiansoft.com
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.business.marketing.dto;

import java.io.Serializable;

/**
 * Class Name: RefereeRebateDto
 * Description: TODO
 * 
 * @author chengminmiao
 */
public class RefereeRebateDto implements Serializable {

    private static final long serialVersionUID = 3920427019004092743L;

    private Double            refereeRebate;

    public Double getRefereeRebate() {
        return refereeRebate;
    }

    public void setRefereeRebate(Double refereeRebate) {
        this.refereeRebate = refereeRebate;
    }

}
