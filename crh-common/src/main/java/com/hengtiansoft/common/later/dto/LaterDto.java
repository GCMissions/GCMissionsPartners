package com.hengtiansoft.common.later.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liminghua on 15/12/9.
 */
public class LaterDto implements Serializable{
    private String className;
    private String methodName;
    private byte[] paramClasses;
    private byte[] args;
    private String channel;

    private Map<?, ?> mdcMap = new HashMap<>();
    private String sessionId;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public byte[] getParamClasses() {
        return paramClasses;
    }

    public void setParamClasses(byte[] paramClasses) {
        this.paramClasses = paramClasses;
    }

    public byte[] getArgs() {
        return args;
    }

    public void setArgs(byte[] args) {
        this.args = args;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Map<?, ?> getMdcMap() {
        return mdcMap;
    }

    public void setMdcMap(Map<?, ?> mdcMap) {
        if (mdcMap != null) {
            this.mdcMap = mdcMap;
        }
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
