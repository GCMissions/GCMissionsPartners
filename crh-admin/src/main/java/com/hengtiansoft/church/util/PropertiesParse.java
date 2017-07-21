package com.hengtiansoft.church.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

/***
 * Descripion: Configuration file parsing code
 * 
 * @author taochen
 *
 */
public class PropertiesParse {
    /**
     * Property file converted to map
     * 
     * @param propertiesFilePath
     *            Property file converted to map
     * @return
     */
    public static <T> Map<String, Object> properties2Map(String propertiesFilePath) {
        Map<String, Object> propertiesMap = new HashMap<String, Object>();

        try {

            Properties properties = new Properties();

            InputStream inputStream = ExcelImportUtil.class.getClassLoader().getResourceAsStream(propertiesFilePath);

            properties.load(inputStream);

            Set<Entry<Object, Object>> entrySets = properties.entrySet();

            for (Entry<Object, Object> entry : entrySets) {
                propertiesMap.put(entry.getKey().toString().trim(), entry.getValue().toString().trim());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return propertiesMap;
    }

}
