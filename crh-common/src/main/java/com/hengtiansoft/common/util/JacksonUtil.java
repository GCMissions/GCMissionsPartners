package com.hengtiansoft.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The class JacksonUtil
 *
 * json character and object conversion
 * 
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public final class JacksonUtil {

    public static ObjectMapper objectMapper;

    /**
     * Use the generic method to convert the json string to the corresponding JavaBean object. (1) converted to a normal
     * JavaBean: readValue (json, Student.class)      *   (2) is converted to a set of List, such as List <Student>, the
     * second parameter is passed Student [] .class then Arrays.asList ();. The method of converting an array obtained
     * for a specific type of List
     * 
     * @param jsonStr
     * @param valueType
     * @return
     */
    public static <T> T readValue(String jsonStr, Class<T> valueType) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        try {
            return objectMapper.readValue(jsonStr, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Json array to the List collection
     * 
     * @param jsonStr
     * @param valueTypeRef
     * @return
     */
    public static <T> T readValue(String jsonStr, TypeReference<T> valueTypeRef) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        try {
            return objectMapper.readValue(jsonStr, valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Converts a JavaBean to a json string
     * 
     * @param object
     * @return
     */
    public static String toJSon(Object object) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
