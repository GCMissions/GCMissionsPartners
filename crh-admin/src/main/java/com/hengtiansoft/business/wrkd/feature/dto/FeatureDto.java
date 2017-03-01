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
import java.util.Date;

/**
 * Class Name: FeatureDto Description: TODO
 * 
 * @author kangruan
 *
 */
public class FeatureDto implements Serializable {
    private static final long serialVersionUID = -592194903210307019L;

    private Long id;

    private String tag;

    private String name;

    private String description;

    private String image;

    private Date createDate;

    private Integer sort;

    private String buyUrl;

    private String tagName;
    
    private String sheifStatus;
    
    private Integer  maxSortValue;
    
    private Integer  minSortValue;

	

    public FeatureDto() {
    }

    public FeatureDto(Long id, String tag, String name, String description, String image, Date createDate, Integer sort,String buyUrl) {
        super();
        this.id = id;
        this.tag = tag;
        this.name = name;
        this.description = description;
        this.image = image;
        this.createDate = createDate;
        this.sort = sort;
        this.buyUrl = buyUrl;
    }
    
    public FeatureDto(Long id, String tag, String name, String description, String image, Date createDate, Integer sort) {
        super();
        this.id = id;
        this.tag = tag;
        this.name = name;
        this.description = description;
        this.image = image;
        this.createDate = createDate;
        this.sort = sort;
    }

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getBuyUrl() {
        return buyUrl;
    }

    public void setBuyUrl(String buyUrl) {
        this.buyUrl = buyUrl;
    }
    public Integer getMaxSortValue() {
		return maxSortValue;
	}

	public void setMaxSortValue(Integer maxSortValue) {
		this.maxSortValue = maxSortValue;
	}

	public Integer getMinSortValue() {
		return minSortValue;
	}

	public void setMinSortValue(Integer minSortValue) {
		this.minSortValue = minSortValue;
	}

	public String getSheifStatus() {
		return sheifStatus;
	}

	public void setSheifStatus(String sheifStatus) {
		this.sheifStatus = sheifStatus;
	}
	
	
}
