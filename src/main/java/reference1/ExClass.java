package reference1;

import org.springframework.stereotype.Component;

@Component
public class ExClass implements ExInterface {

	ExInterface ExampleClass;
	
	public ExClass() {
		this.ExampleClass = new ExClass();
	}

	public ExClass(ExInterface exampleClass) {
		this.ExampleClass = exampleClass;
	}

	@Override
	public void method() {
		System.out.println("This is an example method!");
	}

	public ExInterface getExampleClass() {
		return ExampleClass;
	}

	public void setExampleClass(ExInterface exampleClass) {
		ExampleClass = exampleClass;
	}
	
	
}