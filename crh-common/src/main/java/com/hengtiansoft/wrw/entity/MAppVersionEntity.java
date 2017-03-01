package com.hengtiansoft.wrw.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "S_APP_VERSION")
public class MAppVersionEntity implements Serializable {

    private static final long serialVersionUID = -733844671809406617L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long              id;

    @Column(name = "APP_TYPE")
    private String            appType;

    @Column(name = "CREATE_DATE")
    private Date              createDate;

    @Column(name = "VERSION_NUMBER")
    private String            newVersion;

    @Column(name = "FILE_NAME")
    private String            fileName;

    @Column(name = "FILE_LENGTH")
    private String            size;

    @Column(name = "URL")
    private String            url;

    @Column(name = "LATEST_VERSION")
    private String            isLatestVersion;                        // 1最新，0不是最新
    
    @Column(name = "FORCE_UPDATE")
    private String              forceUpdate;                         // 1 强制，0不强制

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIsLatestVersion() {
        return isLatestVersion;
    }

    public void setIsLatestVersion(String isLatestVersion) {
        this.isLatestVersion = isLatestVersion;
    }

    public String getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(String forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

}
