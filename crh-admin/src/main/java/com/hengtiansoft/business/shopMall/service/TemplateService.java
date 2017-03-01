/*
* Project Name: ecom
* File Name: TemplateService.java
* Class Name: TemplateService
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
package com.hengtiansoft.business.shopMall.service;

import java.util.List;

import com.hengtiansoft.business.shopMall.service.impl.Template;
import com.hengtiansoft.business.shopMall.service.impl.Template.Type;


/**
 * Service - 模板
 * 
 * @author Hengtiansoft Team
 * @version 1.0_beta
 */
public interface TemplateService {

	/**
	 * 获取所有模板
	 * 
	 * @return 所有模板
	 */
	List<Template> getAll();

	/**
	 * 获取模板
	 * 
	 * @param type
	 *            类型
	 * @return 模板
	 */
	List<Template> getList(Type type);

	/**
	 * 获取模板
	 * 
	 * @param id
	 *            ID
	 * @return 模板
	 */
	Template get(String id);

	/**
	 * 读取模板文件内容
	 * 
	 * @param id
	 *            ID
	 * @return 模板文件内容
	 */
	String read(String id);

	/**
	 * 读取模板文件内容
	 * 
	 * @param template
	 *            模板
	 * @return 模板文件内容
	 */
	String read(Template template);

	/**
	 * 写入模板文件内容
	 * 
	 * @param id
	 *            Id
	 * @param content
	 *            模板文件内容
	 */
	void write(String id, String content);

	/**
	 * 写入模板文件内容
	 * 
	 * @param template
	 *            模板
	 * @param content
	 *            模板文件内容
	 */
	void write(Template template, String content);

}
