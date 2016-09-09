package kr.co.ps119;

import kr.co.ps119.config.*;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.hibernate.validator.HibernateValidator;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

@SpringBootApplication
//@Import(value = kr.co.ps119.config.ConfigWebsocket.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

/*	
	@Bean
	public Validator localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}
*/	
	
/*	
	@Bean
	public ServletRegistrationBean mainConfig() {
		DispatcherServlet dispatcherServlet = new DispatcherServlet();
		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
		appContext.register(ConfigMain.class, ConfigDB.class);
		dispatcherServlet.setApplicationContext(appContext);
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(dispatcherServlet, "/*");
		servletRegistrationBean.setName("main");
	    return servletRegistrationBean;
	}
*/	
}
