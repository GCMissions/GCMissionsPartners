package com.hengtiansoft.common.sequence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "S_SEQUENCE")
public class SequenceEntity implements Serializable {

    private static final long serialVersionUID = 544868043072690013L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SEQUENCE_NAME")
    private String sequenceName;

    @Column(name = "LAST_NUMBER")
    private Long lastNumber;

    @Column(name = "MIN_VALUE")
    private Long minValue;

    @Column(name = "MAX_VALUE")
    private Long maxValue;

    @Column(name = "CACHE_SIZE")
    private Long cacheSize;

    @Column(name = "CYCLE_FLAG")
    private Boolean cycleFlag;

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public Long getLastNumber() {
        return lastNumber;
    }

    public void setLastNumber(Long lastNumber) {
        this.lastNumber = lastNumber;
    }

    public Long getMinValue() {
        return minValue;
    }

    public void setMinValue(Long minValue) {
        this.minValue = minValue;
    }

    public Long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }

    public Long getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(Long cacheSize) {
        this.cacheSize = cacheSize;
    }

    public Boolean getCycleFlag() {
        return cycleFlag;
    }

    public void setCycleFlag(Boolean cycleFlag) {
        this.cycleFlag = cycleFlag;
    }

}
