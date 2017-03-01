package com.hengtiansoft.wrw.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hengtiansoft.common.domain.BaseEntity;

/**
 * The persistent class for the coolbag_feature_tag_ref database table.
 * 
 */
@Entity
@Table(name = "coolbag_feature_tag_ref")
public class CoolbagFeatureTagRefEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -6648371397984718399L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FEATURE_ID")
    private Long featureId;

    @Column(name = "TAG_ID")
    private Long tagId;

    public CoolbagFeatureTagRefEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFeatureId() {
        return this.featureId;
    }

    public void setFeatureId(Long featureId) {
        this.featureId = featureId;
    }

    public Long getTagId() {
        return this.tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

}
