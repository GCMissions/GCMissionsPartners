/*
 * Project Name: 3t-common
 * File Name: CustomMoneyDeserializer.java
 * Class Name: CustomMoneyDeserializer
 *
 * Copyright 2014 Hengtian Software Inc
 *
 * Licensed under the Hengtiansoft
 *
 * http://www.hengtiansoft.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.common.serialize;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * Class Name: CustomMoneyDeserializer Description: 元转分
 * 
 * @author kangruan
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
