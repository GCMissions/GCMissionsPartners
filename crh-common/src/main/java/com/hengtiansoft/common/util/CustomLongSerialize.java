/*
 * Project Name: wrw-common
 * File Name: CustomLongSerialize.java
 * Class Name: CustomLongSerialize
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
import java.text.DecimalFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Class Name: CustomLongSerialize
 * Description: TODO
 * 
 * @author chengminmiao
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
