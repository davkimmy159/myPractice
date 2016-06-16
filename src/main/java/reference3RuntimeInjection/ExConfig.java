package reference3RuntimeInjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:/com/example/package/example.properties")
public class ExConfig {

	@Autowired
	Environment env;

	@Bean
	public ExInterface1 method1() {
		return new ExClass1(env.getProperty("ex.field1", "Default value"), env.getProperty("ex.field2"));
		// env.getProperty("ex.field1", Integer.class, 30);
		// Class<ExClass1> clazz = env.getPropertyAsClass("ex.field", ExClass1.class);
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
