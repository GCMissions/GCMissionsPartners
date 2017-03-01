/*
 * Project Name: 3t-common
 * File Name: CustomMoneySerializer.java
 * Class Name: CustomMoneySerializer
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
import java.text.DecimalFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Class Name: CustomMoneySerializer Description: 分转元
 * 
 * @author kangruan
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
