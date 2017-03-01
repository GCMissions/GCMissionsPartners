package com.hengtiansoft.common.later.thread.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hengtiansoft.common.later.fork.IForkFunction;
import com.hengtiansoft.common.later.thread.ForkThread;
import com.hengtiansoft.common.security.SecurityContext;

/**
 * Created by liminghua on 16/4/22.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ForkThreadImpl<T> extends Thread implements ForkThread<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ForkThreadImpl.class);

    private List<T> result = new ArrayList<>();
    private IForkFunction<T> forkFunction = null;
    private String currentSessionId = null;

    public void run(IForkFunction<T> forkFunction) {
        this.forkFunction = forkFunction;
        this.currentSessionId = SecurityContext.getCurrentSessionId();
        this.start();
    }

    public void run() {
        SecurityContext.setCurrentSessionId(currentSessionId);
        try {
            result = forkFunction.run();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public List<T> getResult() {
        return result;
    }
}
