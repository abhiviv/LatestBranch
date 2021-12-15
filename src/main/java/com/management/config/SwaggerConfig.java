package com.management.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
@Configuration
@EnableSwagger2
public class SwaggerConfig {
   @Bean
   public Docket api() {
	   ParameterBuilder aParameterBuilder = new ParameterBuilder();
       aParameterBuilder.name("Authorization").modelRef(new ModelRef("string")).parameterType("header").build();
       List<Parameter> aParameters = new ArrayList<Parameter>();
       aParameters.add(aParameterBuilder.build());
       return new Docket(DocumentationType.SWAGGER_2).select()
               .apis(RequestHandlerSelectors.basePackage("com.management.controller")).paths(PathSelectors.ant("/web/*")).build()
               .apiInfo(apiDetails())
               .globalOperationParameters(aParameters);
   }
   
   
   private ApiInfo apiDetails() {
	return new ApiInfo("Service A API Doc", "Service A API Doc", "1.0", "https://github.com/", new Contact("Abhishek", "https://github.com/", "awesomesingh007p@gmail.com"), null, null);
	   
   }  
}
