/*
 * Project Name: wrw-common
 * File Name: SBarcodeCycleEntity.java
 * Class Name: SBarcodeCycleEntity
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
package com.hengtiansoft.wrw.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* Class Name: SBarcodeCycleEntity
* Description: 条码流水表
* @author zhisongliu
*
*/
@Table(name = "S_BARCODE_CYCLE")
@Entity
public class SBarcodeCycleEntity implements Serializable {

    private static final long serialVersionUID = -2209005074278736109L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CYCLE_ID")
    private Long              cycleId;
    
    @Column(name = "PREFIX_CODE")
    private String              prefixCode;
    
    @Column(name = "SOURCE")
    private String            source;
    
    @Column(name = "STATUS")
    private String            status;
    
    @Column(name = "FROM_ID")
    private Long              fromId;
    
    @Column(name = "FROM_NAME")
    private String            fromName;
    
    @Column(name = "TO_ID")
    private String              toId;
    
    @Column(name = "TO_NAME")
    private String            toName;
    
    @Column(name = "ORDER_ID")
    private String            orderId;
    
    @Column(name = "CREATE_DATE")
    private Date              createDate;
    
    @Column(name = "CREATE_ID")
    private Long              createId;
    
    @Column(name ="CONTENT")
    private String            content;

    public Long getCycleId() {
        return cycleId;
    }

    public void setCycleId(Long cycleId) {
        this.cycleId = cycleId;
    }

    public String getPrefixCode() {
        return prefixCode;
    }

    public void setPrefixCode(String prefixCode) {
        this.prefixCode = prefixCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
}
