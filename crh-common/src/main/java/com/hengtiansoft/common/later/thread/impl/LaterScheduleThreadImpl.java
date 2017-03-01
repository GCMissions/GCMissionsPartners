package com.hengtiansoft.common.later.thread.impl;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.hengtiansoft.common.later.dto.LaterDto;
import com.hengtiansoft.common.later.proxy.LaterProxy;
import com.hengtiansoft.common.later.thread.LaterThread;
import com.hengtiansoft.common.security.SecurityContext;
import com.hengtiansoft.common.util.ApplicationContextUtil;

/**
 * Created by liminghua on 16/1/7.
 */
@SuppressWarnings("unused")
@Lazy(false)
@Component
public class LaterScheduleThreadImpl extends Thread{
    private static final Logger LOGGER = LoggerFactory.getLogger(LaterScheduleThreadImpl.class);

    @Autowired
    private ApplicationContextUtil applicationContextUtil;

    private static final Map<String, ConcurrentLinkedQueue<LaterDto>> map = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        new Thread(this, "异步调用主线程").start();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void run(){
        while (true) {
            LaterDto dto;
            while ((dto = LaterProxy.pop()) != null) {

                final String channel = dto.getChannel().intern();

                boolean n = false; //是否需要新建执行通道
                synchronized (channel.intern()){
                    if(map.containsKey(channel) && !map.get(channel).isEmpty()){
                        map.get(channel).add(dto);
                        n = false;
                        LOGGER.debug("增加异步执行通道:{}, size:{}", channel, map.get(channel).size());
                    }else {
                        map.put(channel, new ConcurrentLinkedQueue<>());
                        map.get(channel).add(dto);
                        n = true;
                        LOGGER.debug("初始化异步执行通道:{}, size:{}", channel, map.get(channel).size());
                    }
                }
                if(n){
                    ApplicationContextUtil.getBean(LaterThread.class).run(()->{
                        while(true) {
                            LaterDto laterDto = null;
                            synchronized (channel.intern()) {
                                laterDto = map.get(channel).poll();
                            }
                            if (laterDto != null) {
                                try {
                                    MDC.clear();
                                    MDC.setContextMap(laterDto.getMdcMap());
                                    SecurityContext.setCurrentSessionId(laterDto.getSessionId());
                                    LOGGER.debug("异步执行方法:{}.{}(...)", laterDto.getClassName(), laterDto.getMethodName());
                                    Object target = ApplicationContextUtil.getBean(Class.forName(laterDto.getClassName()));
                                    Method m;
                                    if (laterDto.getParamClasses() == null || laterDto.getParamClasses().length == 0) {
                                        m = target.getClass().getMethod(laterDto.getMethodName());
                                        m.invoke(target);
                                    } else {
                                        Object[] arguments = LaterProxy.decode(laterDto.getArgs());
                                        m = target.getClass().getMethod(laterDto.getMethodName(),
                                                LaterProxy.decodeParamClasses(laterDto.getParamClasses()));

                                        m.invoke(target, arguments);
                                    }
                                } catch (Exception e) {
                                    LOGGER.error(e.getMessage(), e);
                                }
                            }else{
                                LOGGER.debug("关闭异步执行通道:{}", channel);
                                break;
                            }
                        }
                    });
                }
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }
}
