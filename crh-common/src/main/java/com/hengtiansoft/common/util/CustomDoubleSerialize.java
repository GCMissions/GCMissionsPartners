package com.hengtiansoft.common.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Class Name: CustomDoubleSerialize Description: The amount of money in the JSON (yuan) format conversion, to retain two decimal places, 
 * add comments @ JsonSerialize (using =
  * CustomDoubleSerialize.class)
 * 
 * @author taochen
 */
public class CustomDoubleSerialize extends JsonSerializer<BigDecimal> {
    private DecimalFormat df = new DecimalFormat("##0.00");

    @Override
    public void serialize(BigDecimal value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        if (null == value) {
            jgen.writeString("");
        } else {
            jgen.writeString(df.format(value));
        }
    }
}
