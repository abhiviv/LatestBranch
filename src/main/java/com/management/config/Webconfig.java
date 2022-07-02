package com.management.config;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
@EnableWebMvc
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableCaching
public class Webconfig implements WebMvcConfigurer{
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
    
	@Bean
    public AuditorAware<Object> auditorProvider() {

    	return () -> {
    		if (SecurityContextHolder.getContext().getAuthentication() != null) {
    			return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    		} else {
    			return Optional.ofNullable("ADMIN");
    		}
    	};
    }
	
	@Bean
	public SpringTemplateEngine templateEngine(){
	    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	    templateEngine.setTemplateResolver(templateResolver());
	    templateEngine.setEnableSpringELCompiler(true);
	    return templateEngine;
	}
	@Bean
	public ClassLoaderTemplateResolver templateResolver(){
	  final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
       templateResolver.setPrefix("/templates/");
       templateResolver.setSuffix(".html");
       templateResolver.setTemplateMode(TemplateMode.HTML);
       templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
       templateResolver.setCacheable(false);
       return templateResolver;
	}
	 @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {

	            registry.addResourceHandler("swagger-ui.html")
	                    .addResourceLocations("classpath:/META-INF/resources/");

	            registry.addResourceHandler("/webjars/**")
	                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
	            
	            registry
		        .addResourceHandler("/files/**")
		        .addResourceLocations("file:c://temp/");
                registry.addResourceHandler("/log/**").addResourceLocations("file:c://Log/");
	    }
	 
	 @Bean
	 public MessageSource messageSource() {
	        ReloadableResourceBundleMessageSource messageSource
	                = new ReloadableResourceBundleMessageSource();
	        messageSource.setBasenames(
	                "classpath:/api_error_messages"
	        );
	        messageSource.setDefaultEncoding("UTF-8");
	        return messageSource;
	    }
	 @Bean
	 public LocalValidatorFactoryBean getValidator() {
	     LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	     bean.setValidationMessageSource(messageSource());
	     return bean;
	 }
}
