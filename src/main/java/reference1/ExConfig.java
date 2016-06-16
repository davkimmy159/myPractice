package reference1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;

@Configuration
// @ComponentScan(basePackages = "examplePackage1")
// @ComponentScan(basePackages = {"examplePackage1", "examplePackage2"})
// @ComponentScan(basePackageClasses = {exampleCalss1.class, exampleCalss2.class})
public class ExConfig {

	@Bean(name = "exampleAnotherBeanName")
	public ExInterface method1() {
		int choice = (int) Math.floor(Math.random() * 4);

		switch (choice) {
		case 0:
			return new ExClass();
		case 1:
			return new ExClass(); // Another class 1
		case 2:
			return new ExClass(); // Another class 2
		case 3:
			return new ExClass(); // Another class 3
		default : 
			return new ExClass(); // Another class 4
		}

		// return new ExampleClass();
	}
	
	// has same bean instance with next method
	@Bean
	public ExInterface method2() {
		return new ExClass(method1());
	}

	// has same bean instance with previous method
	@Bean
	public ExInterface method3() {
		return new ExClass(method1());
	}
	
	// nicer way to use other config method
	@Bean
	public ExInterface method4(ExInterface exampleClass) {
		return new ExClass(exampleClass);
	}
	
	// nicer way to use other config method
	@Bean
	public ExInterface exampleConfigMethod5(ExInterface exampleClass) {
		ExClass tmpExampleClass = new ExClass();
		tmpExampleClass.setExampleClass(exampleClass);
		return tmpExampleClass;
	}
	 
}