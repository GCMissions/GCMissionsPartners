/*
 * Project Name:wuliangye-collect-dao-platform
 * File Name: BasicType.java
 * Class Name: BasicType
 * Copyright 2015 Hengtian Software Inc
 * Licensed under the Hengtiansoft
 * http://www.hengtiansoft.com
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.church.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Class Name: BasicType
 * Description: BasicType-doMain
 * 
 * @author yigesong
 */
@Entity
@Table(name = "BASIC_TYPE")
public class SBasicTypeEntity implements Serializable {

    private static final long serialVersionUID = 2520657043127683443L;

    @Id
    @Column(name = "TYPE_ID")
    private Integer           typeId;

    @Column(name = "TYPE_NAME")
    private String            typeName;

    @Column(name = "CONFIG_FLAG")
    private Integer           configFlag;
    
    @Column(name = "MODIFY_ID")
    private Integer           modifyId;

    @Column(name = "MODIFY_DATE")
    private Date              modifyDate;

    @Column(name = "REMARK")
    private String            remark;

    /**
     * @return return the value of the var typeId
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * Description: Set typeId value
     * 
     * @param typeId
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    /**
     * @return return the value of the var typeName
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * Description: Set typeName value
     * 
     * @param typeName
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
	 * @return the configFlag
	 */
	public Integer getConfigFlag() {
		return configFlag;
	}

	/**
	 * @param configFlag the configFlag to set
	 */
	public void setConfigFlag(Integer configFlag) {
		this.configFlag = configFlag;
	}

	/**
     * @return return the value of the var modifyId
     */
    public Integer getModifyId() {
        return modifyId;
    }

    /**
     * Description: Set modifyId value
     * 
     * @param modifyId
     */
    public void setModifyId(Integer modifyId) {
        this.modifyId = modifyId;
    }

    /**
     * @return return the value of the var modifyDate
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * Description: Set modifyDate value
     * 
     * @param modifyDate
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * @return return the value of the var remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Description: Set remark value
     * 
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (!this.getClass().isInstance(obj)) {
            return false;
        }
        return new EqualsBuilder().append(typeId, ((SBasicTypeEntity) obj).getTypeId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(typeId).toHashCode();
    }
}
