/*
 * Project Name: wrw-admin
 * File Name: FeatureDto.java
 * Class Name: FeatureDto
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
import java.util.List;


/**
 * Class Name: FeatureDto Description: TODO
 * 
 * @author kangruan
 *
 */
public class FeatureInfoDto implements Serializable {
    private static final long serialVersionUID = -592194903210307019L;

    private Long id;

    private String tag;

    private String name;

    private String description;

    private String image;

    private Integer sort;

    private String details;

    private String buyUrl;

    private List<Long> tagIds;
    
    /**
     * 所属标签
     */
    private String tagName;
    
    private String ifHomeshow;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Long> tagIds) {
        this.tagIds = tagIds;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getBuyUrl() {
        return buyUrl;
    }

    public void setBuyUrl(String buyUrl) {
        this.buyUrl = buyUrl;
    }

	public String getIfHomeshow() {
		return ifHomeshow;
	}

	public void setIfHomeshow(String ifHomeshow) {
		this.ifHomeshow = ifHomeshow;
	}
    
}
