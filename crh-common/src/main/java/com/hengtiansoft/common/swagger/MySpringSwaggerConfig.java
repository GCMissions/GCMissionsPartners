package com.hengtiansoft.common.swagger;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.configuration.SwaggerModelsConfiguration;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.paths.RelativeSwaggerPathProvider;
import com.mangofactory.swagger.paths.SwaggerPathProvider;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

@Configuration
@ComponentScan(basePackages = { "com.mangofactory.swagger.controllers" })
@Import(SwaggerModelsConfiguration.class)
public class MySpringSwaggerConfig extends SpringSwaggerConfig {

    @Autowired
    private ServletContext servletContext;

    @Bean
    public SwaggerPathProvider defaultSwaggerPathProvider() {
        SwaggerPathProvider provider = new RelativeSwaggerPathProvider(servletContext);
        provider.setApiResourcePrefix("web");
        return provider;
    }

    /**
     * 链式编程 来定制API样式 后续会加上分组信息
     *
     * @return
     */
    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this).apiInfo(apiInfo()).includePatterns(".*").apiVersion("0.0.1")
                .swaggerGroup("user");
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("API", "API文档", "index.html", "", "My License", "");
    }

}
