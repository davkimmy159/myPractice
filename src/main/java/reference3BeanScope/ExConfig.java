package reference3BeanScope;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.context.annotation.ScopedProxyMode;

@Configuration
public class ExConfig {

	// Prototype scope
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	// @Scope("prototype")
	public ExInterface1 method1() {
		return new ExClass1();
	}

	// Session scope
	@Bean
	@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.INTERFACES)
	// proxyMode = ScopedProxyMode.TARGET_CLASS)  ->  Use class, not interface
	public ExInterface1 method2() {
		return new ExClass1();
	}
}
