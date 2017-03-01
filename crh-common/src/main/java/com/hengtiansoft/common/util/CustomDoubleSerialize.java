/*
 * Project Name: wrw-common
 * File Name: CustomDoubleSerialize.java
 * Class Name: CustomDoubleSerialize
 * Copyright 2014 Hengtian Software Inc
 * Licensed under the Hengtiansoft
 * http://www.hengtiansoft.com
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hengtiansoft.common.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Class Name: CustomDoubleSerialize Description: 对JSON中的金额（元）进行格式转换，保留两位小数 加上注解@JsonSerialize(using =
 * CustomDoubleSerialize.class)
 * 
 * @author chengminmiao
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
