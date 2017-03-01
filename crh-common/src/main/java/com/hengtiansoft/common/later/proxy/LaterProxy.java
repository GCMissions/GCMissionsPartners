package com.hengtiansoft.common.later.proxy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import com.hengtiansoft.common.later.dto.LaterDto;
import com.hengtiansoft.common.later.thread.LaterThread;
import com.hengtiansoft.common.security.SecurityContext;
import com.hengtiansoft.common.util.ApplicationContextUtil;

/**
 * Created by liminghua on 15/12/9.
 */
public class LaterProxy implements MethodInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(LaterProxy.class);

    private static final Map<Class<?>, Object> PROXY_MAP = new HashMap<>();

    private static String KEY;

    private static final Queue<LaterDto> LIST = new ConcurrentLinkedDeque<>();

    private static final ThreadLocal<String> CHANNEL = new ThreadLocal<>();

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Class<?>[] paramTypes = method.getParameterTypes();

        LaterDto laterDto = new LaterDto();

        laterDto.setParamClasses(new byte[0]);
        if (paramTypes != null && paramTypes.length > 0) {
            laterDto.setParamClasses(encode(paramTypes));
        }

        laterDto.setArgs(new byte[0]);
        if (objects != null && objects.length > 0) {
            laterDto.setArgs(encode(objects));
        }

        laterDto.setClassName(method.getDeclaringClass().getName());
        laterDto.setMethodName(method.getName());

        laterDto.setChannel(CHANNEL.get());

        laterDto.setSessionId(SecurityContext.getCurrentSessionId());
        laterDto.setMdcMap(MDC.getCopyOfContextMap());

        LIST.add(laterDto);

        CHANNEL.remove();
        return null;
    }

    /**
     * 获得对应service的一个异步封装.
     * 封装后,调用封装service的方法将会被加入一个执行队列依次执行.
     * @param t
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Class<T> t) {
        return cast(t, "");
    }
    /**
     * 获得对应service的一个异步封装.
     * 封装后,调用封装service的方法将会被加入一个执行队列依次执行.
     * @param t
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Class<T> t, String channel) {
        CHANNEL.set(channel);
        if (!PROXY_MAP.containsKey(t)) {
                Enhancer en = new Enhancer();
                en.setSuperclass(t);
                en.setCallback(new LaterProxy());
                PROXY_MAP.put(t, en.create());
        }
        return (T) PROXY_MAP.get(t);
    }

    /**
     * 在新线程中执行runnable,新建的线程将在spring容器中托管.
     * @param runnable
     */
    public static void run(Runnable runnable) {
        ApplicationContextUtil.getBean(LaterThread.class).run(runnable);
    }

    public static LaterDto pop(){
        return LIST.poll();
    }

    public static byte[] encode(Object obj) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
            oos.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public static Class<?>[] decodeParamClasses(byte[] classes) throws IOException, ClassNotFoundException {
        Object[] objs = decode(classes);
        Class<?>[] cs = new Class[objs.length];
        for (int i = 0; i < objs.length; i++) {
            Object obj = objs[i];
            if (obj instanceof Class<?>) {
                cs[i] = (Class<?>) obj;
            } else {
                throw new ClassCastException(String.valueOf(obj));
            }
        }
        return cs;
    }

    public static Object[] decode(byte[] args) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(args))) {
            Object obj = ois.readObject();
            if (obj == null) {
                return new Object[0];
            }
            if (obj.getClass().isArray()) {
                return (Object[]) obj;
            }
            return new Object[]{obj};
        }
    }

    private static String getLaterDtoKey() {
        if(KEY != null){
            return KEY;
        }
        try {
            KEY = "LATER_DTO_" + InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            LOGGER.error(e.getMessage(), e);
            KEY = "LATER_DTO_";
        }
        LOGGER.error("Redis队列的KEY:{}", KEY);
        return KEY;
    }
}
