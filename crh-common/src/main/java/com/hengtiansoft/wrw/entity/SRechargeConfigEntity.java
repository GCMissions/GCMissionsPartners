/*
 * Project Name: wrw-common
 * File Name: SRechargeConfigEntity.java
 * Class Name: SRechargeConfigEntity
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
package com.hengtiansoft.wrw.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hengtiansoft.common.domain.BaseEntity;

/**
 * Class Name: SRechargeConfigEntity
 * Description: TODO
 * 
 * @author chengminmiao
 */
@Entity
@Table(name = "S_RECHARGE_CONFIG")
public class SRechargeConfigEntity extends BaseEntity {

    private static final long serialVersionUID = -6796037350130413082L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONFIG_ID")
    private Long              configId;

    @Column(name = "AMOUNT")
    private Long              amount;

    @Column(name = "STATUS")
    private String            status;

    @Column(name = "NOTE")
    private String            note;

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "SRechargeConfigEntity [configId=" + configId + ", amount=" + amount + ", status=" + status + ", note=" + note + "]";
    }
}
