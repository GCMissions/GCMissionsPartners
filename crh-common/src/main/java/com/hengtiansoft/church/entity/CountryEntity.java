package com.hengtiansoft.church.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hengtiansoft.common.domain.BaseEntity;

@Entity
@Table(name = "country")
public class CountryEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "COUNTRY_SIMPLE_NAME")
    private String countrySimpleName;
    
    @Column(name = "COUNTRY_NAME")
    private String countryName;
    
    @Column(name = "DEL_FLAG")
    private String delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountrySimpleName() {
        return countrySimpleName;
    }

    public void setCountrySimpleName(String countrySimpleName) {
        this.countrySimpleName = countrySimpleName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
