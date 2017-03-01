/*
 * Project Name:wuliangye-collect-dao-platform
 * File Name: BasicPara.java
 * Class Name: BasicPara
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
package com.hengtiansoft.wrw.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Class Name: BasicPara
 * Description: BasicPara-doMain
 * 
 * @author yigesong
 */
@Entity
@Table(name = "S_BASIC_PARA")
public class SBasicParaEntity implements Serializable {

    private static final long serialVersionUID = 5539412050363360728L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PARA_ID")
    private Integer           paraId;

    @Column(name = "TYPE_ID")
    private Integer           typeId;

    @Column(name = "PARA_NAME")
    private String            paraName;

    @Column(name = "PARA_VALUE1")
    private String            paraValue1;

    @Column(name = "PARA_VALUE2")
    private String            paraValue2;

    @Column(name = "PARA_VALUE3")
    private String            paraValue3;

    @Column(name = "PARA_VALUE4")
    private String            paraValue4;

    @Column(name = "REMARK")
    private String            remark;

    /**
     * @return return the value of the var paraId
     */
    public Integer getParaId() {
        return paraId;
    }

    /**
     * Description: Set paraId value
     * 
     * @param paraId
     */
    public void setParaId(Integer paraId) {
        this.paraId = paraId;
    }

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
     * @return return the value of the var paraName
     */
    public String getParaName() {
        return paraName;
    }

    /**
     * Description: Set paraName value
     * 
     * @param paraName
     */
    public void setParaName(String paraName) {
        this.paraName = paraName;
    }

    /**
     * @return return the value of the var paraValue1
     */
    public String getParaValue1() {
        return paraValue1;
    }

    /**
     * Description: Set paraValue1 value
     * 
     * @param paraValue1
     */
    public void setParaValue1(String paraValue1) {
        this.paraValue1 = paraValue1;
    }

    /**
     * @return return the value of the var paraValue2
     */
    public String getParaValue2() {
        return paraValue2;
    }

    /**
     * Description: Set paraValue2 value
     * 
     * @param paraValue2
     */
    public void setParaValue2(String paraValue2) {
        this.paraValue2 = paraValue2;
    }

    /**
     * @return return the value of the var paraValue3
     */
    public String getParaValue3() {
        return paraValue3;
    }

    /**
     * Description: Set paraValue3 value
     * 
     * @param paraValue3
     */
    public void setParaValue3(String paraValue3) {
        this.paraValue3 = paraValue3;
    }

    /**
     * @return return the value of the var paraValue4
     */
    public String getParaValue4() {
        return paraValue4;
    }

    /**
     * Description: Set paraValue4 value
     * 
     * @param paraValue4
     */
    public void setParaValue4(String paraValue4) {
        this.paraValue4 = paraValue4;
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
        return new EqualsBuilder().append(paraId, ((SBasicParaEntity) obj).getParaId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(paraId).toHashCode();
    }
}
