/*
 * Project Name: wrw-admin
 * File Name: CateDot.java
 * Class Name: CateDot
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
 * Class Name: CateDot
 * Description: TODO
 * 
 * @author chengminmiao
 */
public class CateDto implements Serializable {

    private static final long serialVersionUID = 775738219711309692L;

    private Long              cateId;

    private String            cateName;

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }
}
