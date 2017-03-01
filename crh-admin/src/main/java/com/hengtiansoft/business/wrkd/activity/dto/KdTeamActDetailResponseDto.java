/*
 * Project Name: kd-wechat
 * File Name: KdTeamActDetailResponseDto.java
 * Class Name: KdTeamActDetailResponseDto
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
package com.hengtiansoft.business.wrkd.activity.dto;

import java.util.List;

/**
 * Class Name: KdTeamActDetailResponseDto
 * Description: 团购活动详情信息表
 * @author zhisongliu
 *
 */
public class KdTeamActDetailResponseDto  extends KdTeamBuyProductDetailInfoDto{
   
    private static final long serialVersionUID = -5471919796288308835L;
    
    private List<KdTeamActRecordResponseDto> recordList;    //开团记录List
    
    private Integer surplusCount; //团购商品剩余数量
    
    private Integer actNum;       //参团人数
    
    private List<String>  imageList;   //商品图片集合
    
    private Integer saleCount;

    public List<KdTeamActRecordResponseDto> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<KdTeamActRecordResponseDto> recordList) {
        this.recordList = recordList;
    }

    public Integer getSurplusCount() {
        return surplusCount;
    }

    public void setSurplusCount(Integer surplusCount) {
        this.surplusCount = surplusCount;
    }

    public Integer getActNum() {
        return actNum;
    }

    public void setActNum(Integer actNum) {
        this.actNum = actNum;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }
    
}

