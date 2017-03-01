/*
 * Project Name: wrw-common
 * File Name: DataFilter.java
 * Class Name: DataFilter
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
package com.hengtiansoft.common.util;

import org.apache.commons.lang.StringUtils;

/**
* Class Name: DataFilter
* Description: TODO
* @author xianghuang
*
*/
public class DataFilter{
    public static final String EQUALS="=";
    public static final String LIKE="like";
    public static final String GT=">";
    public static final String LT="<";
    
    int index;//筛选位置，从0开始
    String condition;
    String relation;//like，=
    
    public DataFilter(int index, String condition, String relation) {
         this.index = index;
         this.condition = condition;
         this.relation = relation;
    }
     
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public String getCondition() {
        if(StringUtils.isNotBlank(condition)){
            return condition.trim();
        }
        return condition;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }
    public String getRelation() {
        return relation;
    }
    public void setRelation(String relation) {
        this.relation = relation;
    }
}
