package kr.co.ps119.config.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.dialect.IDialect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

import kr.co.ps119.vo.MemberVO;
import nz.net.ultraq.thymeleaf.LayoutDialect;

@Order(1)
@Configuration
//@PropertySource("classpath:properties/etc.properties")
public class CommonConfigMain extends WebMvcConfigurerAdapter {
	
	@Bean
	public Map<Long, Map<String, MemberVO>> boardStompConnMap() {
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
	@Autowired
	BoardHistoryInterceptor boardHistoryInterceptor;

	// registers interceptors 
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	String basePath1 = "/ajax/memo";
    	String memoAjaxPath1 = basePath1 + "/createMemo";
    	String memoAjaxPath2 = basePath1 + "/updateMemo";
    	String memoAjaxPath3 = basePath1 + "/deleteOneMemo";
    	
        registry.addInterceptor(boardHistoryInterceptor)
        		.addPathPatterns(memoAjaxPath1, memoAjaxPath2, memoAjaxPath3);
    }
	 */
	
	/*
	// Is turned off as spring security configuration sets this instead
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