package com.hengtiansoft.common.serialize;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * Class Name: CustomDateTimeDeserialize Description:
 * 
 * @author taochen
 *
 */
public class CustomDateTimeDeserialize extends JsonDeserializer<Date> {
    private static final Logger logger = LoggerFactory.getLogger(CustomDateTimeDeserialize.class);

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        Date date = null;
        try {
            if (StringUtils.isNotEmpty(jp.getText())) {
                date = sdf.parse(jp.getText());
            }
        } catch (ParseException e) {
            logger.error("Date conversion error", e);
        }
        return date;
    }
}
