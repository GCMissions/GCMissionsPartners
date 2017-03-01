/*
 * Project Name: wrw-admin
 * File Name: UserUtil.java
 * Class Name: UserUtil
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
package com.hengtiansoft.business.common.util;

import com.hengtiansoft.common.authority.AuthorityContext;
import com.hengtiansoft.common.exception.BizServiceException;
import com.hengtiansoft.common.util.ApplicationContextUtil;
import com.hengtiansoft.wrw.dao.SOrgDao;
import com.hengtiansoft.wrw.dao.SRegionDao;
import com.hengtiansoft.wrw.entity.SOrgEntity;
import com.hengtiansoft.wrw.entity.SRegionEntity;
import com.hengtiansoft.wrw.entity.SUserEntity;
import com.hengtiansoft.wrw.enums.RegionLevelType;
import com.hengtiansoft.wrw.enums.StatusEnum;

/**
 * Class Name: UserUtil
 * Description:
 * 
 * @author xiaoluzhou
 */
public class UserUtil {

    public static Integer getUserRegionId() {
        SOrgEntity org = ApplicationContextUtil.getBean(SOrgDao.class).findByOrgIdAndStatus(getUserOrgId(), StatusEnum.NORMAL.getCode());
        if (org == null) {
            throw new BizServiceException("未找到对应的区域信息");
        }
        SRegionDao regionDao = ApplicationContextUtil.getBean(SRegionDao.class);
        SRegionEntity region = regionDao.findOne(org.getRegion());
        if (region == null) {
            throw new BizServiceException("未找到对应的区域信息");
        }
        if (RegionLevelType.CITY.getKey().equals(region.getLevelType())) {
            return region.getId();
        } else {
            return region.getParentId();
        }
    }

    public static String getStoreInfo() {
        Long orgId = ((SUserEntity) AuthorityContext.getCurrentUser()).getOrgId();
        SOrgEntity org = ApplicationContextUtil.getBean(SOrgDao.class).findByOrgIdAndStatus(orgId, StatusEnum.NORMAL.getCode());
        if (org == null) {
            throw new BizServiceException("未找到对应的门店信息");
        }
        return org.getOrgName();
    }

    public static Long getUserOrgId() {
        return ((SUserEntity) AuthorityContext.getCurrentUser()).getOrgId();
    }

    public static Long getUserId() {
        return AuthorityContext.getCurrentUser().getUserId();
    }
}
