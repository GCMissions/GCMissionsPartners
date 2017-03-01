/*
 * Project Name: wrw-admin
 * File Name: Feature.java
 * Class Name: Feature
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
package com.hengtiansoft.business.wrkd.feature.dto;

import java.io.Serializable;

import com.hengtiansoft.common.dto.PagingDto;

/**
 * Class Name: Feature Description: TODO
 * 
 * @author kangruan
 *
 */
public class FeaturePageDto extends PagingDto<FeatureDto> implements Serializable {

    private static final long serialVersionUID = -521487208270280206L;
    
    /**
     * 专辑名称
     */
    private String name;
    
    private String source;
    /**
     * 标签
     */
    private String[] tagNames;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String[] getTagNames() {
        return tagNames;
    }

    public void setTagNames(String[] tagNames) {
        this.tagNames = tagNames;
    }

}
