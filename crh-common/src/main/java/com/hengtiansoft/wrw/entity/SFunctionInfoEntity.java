package com.hengtiansoft.wrw.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hengtiansoft.common.authority.domain.FunctionInfo;

@Entity
@Table(name = "S_FUNCTION_INFO")
public class SFunctionInfoEntity extends FunctionInfo {

    private static final long serialVersionUID = 576156438847300078L;

    @Id
    @Column(name = "FUNCTION_ID")
    private Long           functionId;

    @Column(name = "FUNCTION_NAME")
    private String            functionName;

    @Column(name = "TYPE")
    private String            type;

    @Column(name = "PRIORITY")
    private Long           priority;

    @Column(name = "PARENT_ID")
    private Long           parentId;

    @Column(name = "PARENT_IDS")
    private String            parentIds;

    @Column(name = "PERMISSION")
    private String            permission;

    @Column(name = "URL")
    private String            url;

    @Column(name = "LEVEL")
    private Long           level;

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

}
