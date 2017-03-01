/*
 * Project Name: wrw-common
 * File Name: AppHotAdDao.java
 * Class Name: AppHotAdDao
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
package com.hengtiansoft.wrw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengtiansoft.wrw.entity.AppHotAdEntity;

/**
 * Class Name: AppHotAdDao Description: TODO
 * 
 * @author qianqianzhu
 *
 */
public interface AppHotAdDao extends JpaRepository<AppHotAdEntity, Long>, JpaSpecificationExecutor<AppHotAdEntity> {

    @Modifying
    @Query(value = "update app_hot_ad set title = ?1 where id = ?2 and status = '1'", nativeQuery = true)
    void updateTitle(String title, Long id);

    @Modifying
    @Query(value = "update app_hot_ad set related_url = ?1 where id = ?2 and status = '1'", nativeQuery = true)
    void updateUrl(String relatedUrl, Long id);

    List<AppHotAdEntity> findByTypeAndStatus(String type, String status);

    @Modifying
    @Query(value = "update app_hot_ad set product_id = ?1,upload_image_url = ?2,related_url = ?3,modify_date = now() where id=?4", nativeQuery = true)
    void updateAppHotAdEntity(Long productId, String uploadImageUrl, String relatedUrl, Long id);

    @Query(value = "select * from app_hot_ad where type = ?1 and sort = ?2 and status = '1' limit 1", nativeQuery = true)
    AppHotAdEntity findByTypeAndSort(String type, Integer sort);

    @Modifying
    @Query(value = "update app_hot_ad set product_id = null, upload_image_url = null, related_url = null, modify_date = now() where id = ?1", nativeQuery = true)
    void deleteCarouselAd(Long adId);

    @Modifying
    @Query(value = "update app_hot_ad set status = 0 where id = ?1", nativeQuery = true)
    void deleteHotAd(Long adId);

    @Modifying
    @Query(value = "update app_hot_ad set sort = sort - 1 where sort > ?1 and type = '7' and status = '1'", nativeQuery = true)
    void updateSort(Integer sort);

    @Query(value = "select sort from app_hot_ad where type = '7' and status = '1' order by sort desc limit 1", nativeQuery = true)
    Integer findLastSort();

    @Query(value = "select ad from AppHotAdEntity ad where ad.type = ?1 and ad.status = '1' order by ad.sort")
    List<AppHotAdEntity> findAdvertiseByType(String adType);

    @Query(value = "select * from app_hot_ad where type = '6' and status = '1' and sort = ?1 limit 1", nativeQuery = true)
    AppHotAdEntity findBySort(Integer sort);

    @Modifying
    @Query(value = "update app_hot_ad set sort = sort + ?1 where id = ?2 and type = '6' and status = '1'", nativeQuery = true)
    void updateSortById(Integer sort, Long adId);

}
