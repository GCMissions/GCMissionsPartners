package com.hengtiansoft.common.util;

import java.io.IOException;
import java.text.DecimalFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Class Name: CustomLongSerialize
 * Description: 
 * 
 * @author taochen
 */
public class CustomLongSerialize extends JsonSerializer<Long> {

    private DecimalFormat df = new DecimalFormat("##0.00");

    @Override
    public void serialize(Long value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

        if (null == value) {
            jgen.writeString("0.00");
        } else {
            jgen.writeString(df.format(value.doubleValue() / 100));
        }
    }

}
