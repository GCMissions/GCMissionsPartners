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
package com.hengtiansoft.church.common.util;

import com.hengtiansoft.church.entity.SUserEntity;
import com.hengtiansoft.common.authority.AuthorityContext;

/**
 * Class Name: UserUtil
 * Description:
 * 
 * @author xiaoluzhou
 */
public class UserUtil {

    public static Long getUserOrgId() {
        return ((SUserEntity) AuthorityContext.getCurrentUser()).getOrgId();
    }

    public static Long getUserId() {
        return AuthorityContext.getCurrentUser().getUserId();
    }
}
