/*
* Project Name: ecom
* File Name: TemplateServiceImpl.java
* Class Name: TemplateServiceImpl
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
package com.hengtiansoft.business.shopMall.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import com.hengtiansoft.business.shopMall.service.TemplateService;
import com.hengtiansoft.business.shopMall.service.impl.Template.Type;
import com.hengtiansoft.common.util.CommonAttributes;

/**
 * Service - 模板
 * 
 * @author Hengtiansoft Team
 * @version 1.0_beta
 */
@Service("templateServiceImpl")
public class TemplateServiceImpl implements TemplateService, ServletContextAware {
	
	private static final Logger  LOGGER  = LoggerFactory.getLogger(TemplateServiceImpl.class);

	/** servletContext */
	private ServletContext servletContext;

	@Value("${template.loader_path}")
	private String[] templateLoaderPaths;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@SuppressWarnings("unchecked")
	public List<Template> getAll() {
		try {
            File wrwFile = new ClassPathResource(CommonAttributes.getXylXmlPath()).getFile();
			Document document = new SAXReader().read(wrwFile);
			List<Template> templates = new ArrayList<Template>();
			List<Element> elements = document.selectNodes("/wrw/template");
			for (Element element : elements) {
				Template template = getTemplate(element);
				templates.add(template);
			}
			return templates;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Template> getList(Type type) {
		if (type != null) {
			try {
                File wrwFile = new ClassPathResource(CommonAttributes.getXylXmlPath()).getFile();
				Document document = new SAXReader().read(wrwFile);
				List<Template> templates = new ArrayList<Template>();
				List<Element> elements = document.selectNodes("/wrw/template[@type='" + type + "']");
				for (Element element : elements) {
					Template template = getTemplate(element);
					templates.add(template);
				}
				return templates;
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return null;
			}
		} else {
			return getAll();
		}
	}

	public Template get(String id) {
		try {
            File wrwFile = new ClassPathResource(CommonAttributes.getXylXmlPath()).getFile();
			Document document = new SAXReader().read(wrwFile);
			Element element = (Element) document.selectSingleNode("/wrw/template[@id='" + id + "']");
			return getTemplate(element);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return null;
		}
	}

	public String read(String id) {
		Template template = get(id);
		return read(template);
	}

	public String read(Template template) {
		String templatePath = servletContext.getRealPath(templateLoaderPaths[0] + template.getTemplatePath());
		File templateFile = new File(templatePath);
		String templateContent = null;
		try {
			templateContent = FileUtils.readFileToString(templateFile, "UTF-8");
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return templateContent;
	}

	public void write(String id, String content) {
		Template template = get(id);
		write(template, content);
	}

	public void write(Template template, String content) {
		String templatePath = servletContext.getRealPath(templateLoaderPaths[0] + template.getTemplatePath());
		File templateFile = new File(templatePath);
		try {
			FileUtils.writeStringToFile(templateFile, content, "UTF-8");
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取模板
	 * 
	 * @param element
	 *            元素
	 */
	private Template getTemplate(Element element) {
		String id = element.attributeValue("id");
		String type = element.attributeValue("type");
		String name = element.attributeValue("name");
		String templatePath = element.attributeValue("templatePath");
		String staticPath = element.attributeValue("staticPath");
		String description = element.attributeValue("description");

		Template template = new Template();
		template.setId(id);
		template.setType(Type.valueOf(type));
		template.setName(name);
		template.setTemplatePath(templatePath);
		template.setStaticPath(staticPath);
		template.setDescription(description);
		return template;
	}

}
