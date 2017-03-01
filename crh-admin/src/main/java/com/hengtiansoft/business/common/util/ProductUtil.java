/*
 * Project Name: wrw-admin
 * File Name: ProductUtil.java
 * Class Name: ProductUtil
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
package com.hengtiansoft.business.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hengtiansoft.common.util.ApplicationContextUtil;
import com.hengtiansoft.wrw.dao.MOrderDetailDao;
import com.hengtiansoft.wrw.entity.MOrderDetailEntity;

/**
 * Class Name: ProductUtil
 * Description: 
 * @author xiaoluzhou
 *
 */
public class ProductUtil {

    public static List<String> getPrefixCodes(List<MOrderDetailEntity> details) {
        List<String> prefixCodes = new ArrayList<String>();
        for (MOrderDetailEntity mOrderDetailEntity : details) {
            String prefixCode = mOrderDetailEntity.getPrefixCode();
            if(StringUtils.isNotBlank(prefixCode)){
                String [] prefixArray = prefixCode.split(",");
                for (int i = 0; i < prefixArray.length; i++) {
                    if(StringUtils.isNotBlank(prefixArray[i].trim())){
                        prefixCodes.add(prefixArray[i].trim());
                    }
                }
            }
        }
        return prefixCodes;
    }
    
    public static List<String> getPrefixCodes(String orderNo) {
        List<MOrderDetailEntity> details = ApplicationContextUtil.getBean(MOrderDetailDao.class).findByOrderId(orderNo);
        return getPrefixCodes(details);
    }
}
