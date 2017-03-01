/*
* Project Name: xinyunlian-ecom
* File Name: EnumConverter.java
* Class Name: EnumConverter
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
package com.hengtiansoft.common.util;

import org.apache.commons.beanutils.converters.AbstractConverter;

/**
 * 枚举类型转换
 * 
 * @author Hengtiansoft Team
 * @version 1.0_beta
 */
public class EnumConverter extends AbstractConverter {

	/** 枚举类型 */
	private final Class<?> enumClass;

	/**
	 * @param enumClass
	 *            枚举类型
	 */
	public EnumConverter(Class<?> enumClass) {
		this(enumClass, null);
	}

	/**
	 * @param enumClass
	 *            枚举类型
	 * @param defaultValue
	 *            默认值
	 */
	public EnumConverter(Class<?> enumClass, Object defaultValue) {
		super(defaultValue);
		this.enumClass = enumClass;
	}

	/**
	 * 获取默认类型
	 * 
	 * @return 默认类型
	 */
	@Override
	protected Class<?> getDefaultType() {
		return this.enumClass;
	}

	/**
	 * 转换为枚举对象
	 * 
	 * @param type
	 *            类型
	 * @param value
	 *            值
	 * @return 枚举对象
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object convertToType(Class type, Object value) {
		String stringValue = value.toString().trim();
		return Enum.valueOf(type, stringValue);
	}

	/**
	 * 转换为字符串
	 * 
	 * @param value
	 *            值
	 * @return 字符串
	 */
	protected String convertToString(Object value) {
		return value.toString();
	}

}
