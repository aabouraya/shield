package com.knowhow.shield.config;

import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Slf4j
class SwaggerConfiguration {

    private static final String BASE_PACKAGE = "com.knowhow.shield.controller";
    private AppProperties appProperties;

    public SwaggerConfiguration(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any()).build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact(appProperties.getSwagger().getContactName(),
                appProperties.getSwagger().getContactUrl(), appProperties.getSwagger().getContactEmail());

        return new ApiInfo(appProperties.getSwagger().getTitle(), appProperties.getSwagger().getDescription(),
                appProperties.getSwagger().getVersion(), appProperties.getSwagger().getTermsOfServiceUrl(), contact,
                appProperties.getSwagger().getLicense(), appProperties.getSwagger().getLicenseUrl(),
                Collections.emptyList());
    }
}
