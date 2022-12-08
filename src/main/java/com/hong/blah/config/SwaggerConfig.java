package com.hong.blah.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private static final String API_NAME = "BlahBlah API";
    private static final String API_VERSION = "0.0.1";
    private static final String API_DESCRIPTION = "API 명세서";

    @Bean
    public Docket api() {
        Parameter parameterBuilder = new ParameterBuilder()
            .name(HttpHeaders.AUTHORIZATION)
            .description("Access Tocken")
            .modelRef(new ModelRef("string"))
            .parameterType("header")
            .required(false)
            .build();

        List<Parameter> globalParamters = new ArrayList<>();
        globalParamters.add(parameterBuilder);

        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(globalParamters)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hong.blah.controller")) //Swagger를 적용할 클래스의 package 명
                .paths(PathSelectors.any()) //해당 package 하위에 있는 모든 url에 적용시킨다.
                .build();
    }

    public ApiInfo apiInfo() {
	return new ApiInfoBuilder()
		.title(API_NAME) // api 이름
		.version(API_VERSION) //현재 버전
		.description(API_DESCRIPTION) //설명
		.build();
    }
}
