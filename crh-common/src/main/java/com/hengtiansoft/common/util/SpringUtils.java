package com.hengtiansoft.common.util;

import java.util.Locale;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.LocaleResolver;

/**
 * Utils - Spring.
 * 
 * @author taochen
 * @version 1.0_beta
 */
@Component("springUtils")
@Lazy(false)
public final class SpringUtils implements ApplicationContextAware, DisposableBean {

    /** applicationContext. */
    private static ApplicationContext applicationContext;

    /**
     * Can not be instantiated.
     */
    private SpringUtils() {
    }

    /**
     * @param applicationContext
     */
    protected static void initApplicationContext(ApplicationContext applicationContext) {
        SpringUtils.applicationContext = applicationContext;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.
     * ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtils.initApplicationContext(applicationContext);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    public void destroy() {
    }

    /**
     * get applicationContext.
     * 
     * @return applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * Get the instance.
     * 
     * @param name
     *            Bean name
     * @return instance
     */
    public static Object getBean(String name) {
        Assert.hasText(name);
        return applicationContext.getBean(name);
    }

    /**
     * Get the instance.
     * 
     * @param name
     *            Bean name
     * @param type
     *            Bean type
     * @return instance
     */
    public static <T> T getBean(String name, Class<T> type) {
        Assert.hasText(name);
        Assert.notNull(type);
        return applicationContext.getBean(name, type);
    }

    /**
     * Get international news.
     * 
     * @param code
     * 
     * @param args
     * 
     * @return Internationalized news
     */
    public static String getMessage(String code, Object... args) {
        LocaleResolver localeResolver = getBean("localeResolver", LocaleResolver.class);
        Locale locale = localeResolver.resolveLocale(null);
        return applicationContext.getMessage(code, args, locale);
    }

}
