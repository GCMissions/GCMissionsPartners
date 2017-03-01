package com.hengtiansoft.wrw.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hengtiansoft.common.domain.BaseEntity;

@Entity
@Table(name="S_FREIGHT_RABATE")
public class SFreightRebateEntity extends BaseEntity{

    /**
    * Variables Name: serialVersionUID
    * Description: TODO
    * Value Description: TODO
    */
    private static final long serialVersionUID = 7385875750881058839L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RABATE_ID")
    private Long              rebateId;
    
    @Column(name = "REGION_ID")
    private Integer              regionId;
    
    @Column(name = "P_REBATE")
    private Double        pRebate;
    
    @Column(name = "Z_REBATE")
    private Double              zRebate;

    public Long getRebateId() {
        return rebateId;
    }

    public void setRebateId(Long rebateId) {
        this.rebateId = rebateId;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Double getpRebate() {
        return pRebate;
    }

    public void setpRebate(Double pRebate) {
        this.pRebate = pRebate;
    }

    public Double getzRebate() {
        return zRebate;
    }

    public void setzRebate(Double zRebate) {
        this.zRebate = zRebate;
    }
    

}
