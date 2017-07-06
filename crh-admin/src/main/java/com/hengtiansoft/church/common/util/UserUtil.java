package com.hengtiansoft.church.common.util;

import com.hengtiansoft.church.entity.SUserEntity;
import com.hengtiansoft.common.authority.AuthorityContext;

/**
 * Class Name: UserUtil Description:
 * 
 * @author taochen
 */
public class UserUtil {

    public static Long getUserOrgId() {
        return ((SUserEntity) AuthorityContext.getCurrentUser()).getOrgId();
    }

    public static Long getUserId() {
        return AuthorityContext.getCurrentUser().getUserId();
    }
}
