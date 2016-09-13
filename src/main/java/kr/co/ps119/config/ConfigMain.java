package kr.co.ps119.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.context.annotation.ComponentScan;

import java.util.Set;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.ViewResolver;

import org.springframework.web.servlet.view.UrlBasedViewResolver;

import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import kr.co.ps119.controller.*;

import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.dialect.IDialect;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

@Configuration
// @EnableAspectJAutoProxy
public class ConfigMain extends WebMvcConfigurerAdapter {
	
	// Project resources access
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/etc/**").addResourceLocations("classpath:/etc/");
		registry.addResourceHandler("/bower_components/**").addResourceLocations("classpath:/bower_components/");
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
	}

	// Character encoding (UTF-8)
	@Order(Ordered.HIGHEST_PRECEDENCE)
	@Bean
	CharacterEncodingFilter characterEncodingFilter() {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		return filter;
	}

	// Layout dialect for thymeleaf-layout-dialect
	@Bean
	IDialect layoutDialect() {
		return new LayoutDialect();
	}
	
	
	
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