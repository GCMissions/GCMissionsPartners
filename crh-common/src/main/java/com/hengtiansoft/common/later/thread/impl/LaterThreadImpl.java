package com.hengtiansoft.common.later.thread.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hengtiansoft.common.later.thread.LaterThread;
import com.hengtiansoft.common.security.SecurityContext;

/**
 *
 * Created by liminghua on 16/1/7.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LaterThreadImpl extends Thread implements LaterThread {
    private static final Logger LOGGER = LoggerFactory.getLogger(LaterThreadImpl.class);

    private Runnable runnable;

    private String currentSessionId = null;

    private Map<?, ?> mdcMap = new HashMap<>();

    public void run() {
        if (runnable != null) {
            try {
                SecurityContext.setCurrentSessionId(currentSessionId);
                MDC.setContextMap(mdcMap);
                runnable.run();
            }catch (Exception e){
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    public void run(Runnable runnable) {
        this.runnable = runnable;
        this.currentSessionId = SecurityContext.getCurrentSessionId();
        Map<?, ?> map = MDC.getCopyOfContextMap();
        if(map != null){
            mdcMap = map;
        }
        this.start();
    }
}
