package kr.co.ps119.config.dev;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Profile("dev")
@Order(2)
@Configuration
//@PropertySource("classpath:properties/etc.properties")
public class DevConfigMain extends WebMvcConfigurerAdapter {
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:4200");
				/*
				.allowedMethods("PUT", "DELETE")
				.allowedHeaders("header1", "header2", "header3")
				.exposedHeaders("header1", "header2")
				.allowCredentials(false)
				.maxAge(3600);
				*/
	}
}