package com.hengtiansoft.common.util;

import org.apache.commons.lang.StringUtils;

/**
* Class Name: DataFilter
* Description: 
* @author taochen
*
*/
public class DataFilter{
    public static final String EQUALS="=";
    public static final String LIKE="like";
    public static final String GT=">";
    public static final String LT="<";
    
    int index;//screening position, starting from 0
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
