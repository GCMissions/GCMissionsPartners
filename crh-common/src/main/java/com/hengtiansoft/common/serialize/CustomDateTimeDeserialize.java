/*
 * Project Name: 3t-common
 * File Name: CustomDateTimeDeserialize.java
 * Class Name: CustomDateTimeDeserialize
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
 * Class Name: CustomDateTimeDeserialize Description: TODO
 * 
 * @author kangruan
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
            logger.error("日期转换错误", e);
        }
        return date;
    }
}
