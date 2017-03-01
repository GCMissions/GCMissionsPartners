/*
 * Project Name: wrw-admin
 * File Name: KdPImageDto.java
 * Class Name: KdPImageDto
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
package com.hengtiansoft.business.wrkd.image.dto;

import java.io.Serializable;

/**
 * Class Name: KdPImageDto
 * Description: 酷袋商品、专享图片
 * @author zhongyidong
 *
 */
public class KdPImageDto implements Serializable{
    
    private static final long serialVersionUID = 2678794821346059664L;
    
    private Long  imageId;

    private String title;
    
    private String imageUrl;
    
    private String imageKey;
    
    private Long sort;

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }
    
    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }
}
