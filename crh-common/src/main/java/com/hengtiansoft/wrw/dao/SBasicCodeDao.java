/*
 * Project Name: wrw-common
 * File Name: SBasicCodeDao.java
 * Class Name: SBasicCodeDao
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

import com.hengtiansoft.wrw.entity.SBasicCodeEntity;

/**
 * Class Name: SBasicCodeDao Description: 条码Dao
 * 
 * @author zhisongliu
 *
 */
public interface SBasicCodeDao extends JpaRepository<SBasicCodeEntity, Integer>, JpaSpecificationExecutor<SBasicCodeEntity> {

      /**
       * 
       * Description: 通过URLS来查询出所有二维码的信息
       *
       * @param url
       * @return
       */
      @Query(value = "select r from SBasicCodeEntity r where r.url in(?1)")
      List<SBasicCodeEntity> findByUrls(List<String> url);

      SBasicCodeEntity findByPrefixCode(String prefixCode);

      /**
       * 
       * Description: 查询出当前码是母码的所有子码 LIST
       *
       * @param codeList
       * @return
       */
      @Query(value = "select r from SBasicCodeEntity r where r.prefixCode != r.packCode and r.packCode in (?1) ")
      List<SBasicCodeEntity> findByPackCode(List<String> codeList);

      /**
       * 
       * Description: 通过URL来查询出二维码的信息
       *
       * @param url
       * @return
       */
      SBasicCodeEntity findByUrl(String url);

      /**
       * 
       * Description: 通过二维码明码来查询出所有二维码的信息
       *
       * @param prefixCodes
       * @return
       */
      @Query(value = "select r from SBasicCodeEntity r where r.prefixCode in(?1)")
      List<SBasicCodeEntity> findByPrefixCodes(List<String> prefixCodes);

      /**
       * 
       * Description: 查询出当前码是母码的所有子码
       *
       * @param codeList
       * @return
       */
      @Query(value = "select r from SBasicCodeEntity r where r.prefixCode != r.packCode and r.packCode =?1 ")
      List<SBasicCodeEntity> findbyParentIdEqualPrefixCode(String prefixCode);

      @Query(value = "select r.prefixCode from SBasicCodeEntity r where r.prefixCode != r.packCode and r.packCode =?1 ")
      List<String> findPrefixCodebyPackCode(String prefixCode);

      @Query(value = "update SBasicCodeEntity r set r.status=?1 where r.goodId =?2 and r.packCode =?3 ")
      @Modifying
      void updateStatusByGoodIdAndPackCode(String status, Long goodsId, String packCode);

      @Query(value = "update SBasicCodeEntity r set r.status=?1 where r.goodId =?2 and r.prefixCode =?3 ")
      @Modifying
      void updateStatusByGoodIdAndPrefixCode(String status, Long goodsId, String prefixCode);

      @Query("from SBasicCodeEntity s where s.packCode in ?1 and s.prefixCode not in ?1")
      List<SBasicCodeEntity> findAllPrefixCodeByPackCode(List<String> packCodes);

      /**
       * 
       * Description: 通过二维码暗码进行查询
       *
       * @param url
       * @return
       */
      @Query("from SBasicCodeEntity r where r.codeUrl =?1 ")
      SBasicCodeEntity findByCodeUrl(String url);

      SBasicCodeEntity findByQrCode(String qrCode);
      
      @Query("select r.packCode , count(prefixCode) from SBasicCodeEntity r where r.prefixCode in(?1) group by r.packCode")
      List<Object[]> findAllPrefixCodes(List<String> childCodes);
}
