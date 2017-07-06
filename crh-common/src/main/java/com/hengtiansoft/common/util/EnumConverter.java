package com.hengtiansoft.common.util;

import org.apache.commons.beanutils.converters.AbstractConverter;

/**
 * Enum type conversion
 * 
 * @author taochen
 * @version 1.0_beta
 */
public class EnumConverter extends AbstractConverter {

    /** Enum type */
    private final Class<?> enumClass;

    /**
     * @param enumClass
     *            Enum type
     */
    public EnumConverter(Class<?> enumClass) {
        this(enumClass, null);
    }

    /**
     * @param enumClass
     *            Enum type
     * @param defaultValue
     * 
     */
    public EnumConverter(Class<?> enumClass, Object defaultValue) {
        super(defaultValue);
        this.enumClass = enumClass;
    }

    /**
     * Get the default type
     * 
     * @return the default type
     */
    @Override
    protected Class<?> getDefaultType() {
        return this.enumClass;
    }

    /**
     * Converted to enumerate objects
     * 
     * @param type
     * 
     * @param value
     * 
     * @return Enum object
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected Object convertToType(Class type, Object value) {
        String stringValue = value.toString().trim();
        return Enum.valueOf(type, stringValue);
    }

    /**
     * Convert to a string
     * 
     * @param value
     * 
     * @return string
     */
    protected String convertToString(Object value) {
        return value.toString();
    }

}
