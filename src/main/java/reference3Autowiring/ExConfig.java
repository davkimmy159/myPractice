package reference3Autowiring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.beans.factory.annotation.Qualifier;

@Configuration
public class ExConfig {

	@Bean
	// @Primary  ->  If more than two Primary classes exist, that's confused
	public ExInterface method1() {
		return new ExClass1();
	}
	
	@Bean
	// @Primary  ->  If more than two Primary classes exist, that's confused
	public ExInterface method2() {
		return new ExClass2();
	}
	
	@Bean
	//@Qualifier("myQualifier1")
	//@Qualifier("myQualifier2")  ->  You can't use more @Qualifier even it's annotated by @Repeatable (You should use your customized annotation)
	@ExAnnotation1
	@ExAnnotation2
	@ExAnnotation3
	public ExInterface method3() {
		return new ExClass3();
	}
}
