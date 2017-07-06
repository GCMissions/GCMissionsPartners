package com.hengtiansoft.common.serialize;

import java.io.IOException;
import java.text.DecimalFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Class Name: CustomMoneySerializer 
 * Description: Currency conversion
 * 
 * @author taochen
 *
 */
public class CustomMoneySerializer extends JsonSerializer<Long> {
    private static DecimalFormat decimalFormat = new DecimalFormat("##0.00");

    @Override
    public void serialize(Long value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException,
            JsonProcessingException {

        if (value == null) {
            value = 0L;
        }
        jsonGenerator.writeString(decimalFormat.format(value.doubleValue() / 100));

    }
}
