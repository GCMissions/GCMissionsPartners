package com.hengtiansoft.common.serialize;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * Class Name: CustomMoneyDeserializer Description: Currency conversion
 * 
 * @author taochen
 *
 */
public class CustomMoneyDeserializer extends JsonDeserializer<Long> {

    @Override
    public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        if (StringUtils.isNotEmpty(p.getText())) {
            BigDecimal money = BigDecimal.valueOf(Double.valueOf(p.getText())).multiply(BigDecimal.valueOf(100D));
            return money.longValue();
        } else {
            return null;
        }
    }
}
