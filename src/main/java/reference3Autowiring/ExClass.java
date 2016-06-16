package reference3Autowiring;

import org.springframework.beans.factory.annotation.Autowired;

public class ExClass {
	
	ExInterface exInterface;

	public ExInterface getExInterface() {
		return exInterface;
	}

	@Autowired
	// @Qualifier("myQualifier1")
	// @Qualifier("myQualifier2")  ->  You can't use more @Qualifier even it's annotated by @Repeatable (You should use your customized annotation)
	@ExAnnotation1
	@ExAnnotation2
	@ExAnnotation3
	public void setExInterface(ExInterface exInterface) {
		this.exInterface = exInterface;
	}
}
