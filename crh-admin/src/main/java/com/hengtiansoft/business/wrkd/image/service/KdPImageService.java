/*
 * Project Name: wrw-admin
 * File Name: KdPImageService.java
 * Class Name: KdPImageService
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
package com.hengtiansoft.business.wrkd.image.service;

import java.util.List;

import com.hengtiansoft.business.wrkd.image.dto.KdPImageDto;
import com.hengtiansoft.wrw.entity.KdPImageEntity;

/**
 * Class Name: KdPImageService
 * Description: 酷袋商品、专享图片
 * @author zhongyidong
 *
 */
public interface KdPImageService {
    
    /**
     * Description: 查询图片
     * 
     * @param keyId
     * @param type
     * @return
     */
    List<KdPImageDto> queryImage(Long keyId, String type);
    
    /**
     * Description: 保存图片
     * @param keyId
     * @param type
     * @param imageDtoList
     * @return
     */
    List<KdPImageEntity> saveImage(Long keyId, String type, List<KdPImageDto> imageDtoList);
    
    /**
     * Description: 删除图片
     * 
     * @param keyId
     * @param type
     * @return
     */
    int deleteImage(Long keyId, String type);
    
}
