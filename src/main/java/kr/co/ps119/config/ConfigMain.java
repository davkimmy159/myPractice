package kr.co.ps119.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.context.annotation.ComponentScan;

import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.ViewResolver;

import org.springframework.web.servlet.view.UrlBasedViewResolver;

import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

import kr.co.ps119.controller.*;
import kr.co.ps119.entity.Member;

import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

@Configuration
//@PropertySource("classpath:properties/etc.properties")
public class ConfigMain extends WebMvcConfigurerAdapter {
	
	@Bean
	public Map<Long, List<Member>> memberListMap() {
		return new HashMap<>();
	}
	
	/*
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	    return new PropertySourcesPlaceholderConfigurer();
	}
	*/
	
	// Project resources access
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/bower_components/**").addResourceLocations("classpath:/bower_components/");
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/css/");
		registry.addResourceHandler("/image/**").addResourceLocations("classpath:/image/");
	}

	/*
	// Turned off as spring security configuration sets this instead
	// Character encoding (UTF-8)
	@Order(Ordered.HIGHEST_PRECEDENCE)
	@Bean
	CharacterEncodingFilter characterEncodingFilter() {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		return filter;
	}
	*/

	// Layout dialect for thymeleaf-layout-dialect
	@Bean
	IDialect layoutDialect() {
		return new LayoutDialect();
	}
	
	public MappingJackson2HttpMessageConverter jacksonMessageConverter(){
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        ObjectMapper mapper = new ObjectMapper();
        //Registering Hibernate4Module to support lazy objects
        mapper.registerModule(new Hibernate5Module());

        messageConverter.setObjectMapper(mapper);
        return messageConverter;
    }

	@Bean
	public HttpMessageConverters customConverters() {
		HttpMessageConverter<?> hibernate5ModuleJacksonMessageConverter = jacksonMessageConverter();
		return new HttpMessageConverters(hibernate5ModuleJacksonMessageConverter);
	}
    
	/*
	@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //Here we add our custom-configured HttpMessageConverter
        converters.add(jacksonMessageConverter());
        super.configureMessageConverters(converters);
    }
    */
	
/*
	@Bean
	ServletContextTemplateResolver templateResolver() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		return templateResolver;
	}

	@Bean
	SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		
		Set<IDialect> dialectSet = new HashSet<>();
		dialectSet.add(new SpringSecurityDialect());
		
		templateEngine.setAdditionalDialects(dialectSet);
		templateEngine.addDialect(new LayoutDialect());
		return templateEngine;
	}
	
	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setOrder(1);
		return viewResolver;
	}
	
*/
	
}