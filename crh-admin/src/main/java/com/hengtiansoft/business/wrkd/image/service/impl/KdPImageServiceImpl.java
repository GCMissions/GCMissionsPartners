/*
 * Project Name: wrw-admin
 * File Name: KdPImageServiceImpl.java
 * Class Name: KdPImageServiceImpl
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
package com.hengtiansoft.business.wrkd.image.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hengtiansoft.business.wrkd.image.dto.KdPImageDto;
import com.hengtiansoft.business.wrkd.image.service.KdPImageService;
import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.converter.ConverterService;
import com.hengtiansoft.wrw.dao.KdPImageDao;
import com.hengtiansoft.wrw.entity.KdPImageEntity;

/**
 * Class Name: KdPImageServiceImpl
 * Description: 酷袋商品、专享图片
 * @author zhongyidong
 *
 */
@Service
public class KdPImageServiceImpl implements KdPImageService {

    // 正常使用
    private static final String NORMAL = "0";
    // 已经删除
    private static final String DELETED = "1";
    
    @Autowired
    private KdPImageDao kdPImageDao;
    
    @Override
    public List<KdPImageDto> queryImage(Long keyId, String type) {
        List<KdPImageEntity> imgEntitys = kdPImageDao.queryImage(keyId, type, NORMAL);
        if (!CollectionUtils.isEmpty(imgEntitys)) {
            List<KdPImageDto> imgDtos = new ArrayList<KdPImageDto>(imgEntitys.size());
            KdPImageDto imgDto = null;
            for (KdPImageEntity imgEntity : imgEntitys) {
                imgDto = ConverterService.convert(imgEntity, KdPImageDto.class);
                imgDtos.add(imgDto);
            }
            return imgDtos;
        }
        return null;
    }

    @Override
    @Transactional
    public List<KdPImageEntity> saveImage(Long keyId, String type, List<KdPImageDto> imageDtoList) {
        // 删除已有记录
        kdPImageDao.deleteImage(keyId, type, DELETED);
        Long userId = AuthorityContext.getCurrentUser().getUserId();
        if (!CollectionUtils.isEmpty(imageDtoList)) {
            List<KdPImageEntity> imgEntitys = new ArrayList<KdPImageEntity>(imageDtoList.size());
            KdPImageEntity imgEntity = null;
            Date curDate = new Date();
            for (KdPImageDto imgDto : imageDtoList) {
                imgEntity = ConverterService.convert(imgDto, KdPImageEntity.class);
                imgEntity.setKeyId(keyId);
                imgEntity.setType(type);
                imgEntity.setIsDeleted(NORMAL);
                imgEntity.setCreateId(userId);
                imgEntity.setCreateDate(curDate);
                imgEntitys.add(imgEntity);
            }
            return kdPImageDao.save(imgEntitys);
        }
        
        return null;
    }

    @Override
    @Transactional
    public int deleteImage(Long keyId, String type) {
        return kdPImageDao.deleteImage(keyId, type, DELETED);
    }

}
