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
     * Chain programming Used to customize the API style will be added after the grouping information
     *
     * @return
     */
    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this).apiInfo(apiInfo()).includePatterns(".*").apiVersion("0.0.1")
                .swaggerGroup("user");
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("API", "API Documentation", "index.html", "", "My License", "");
    }

}
