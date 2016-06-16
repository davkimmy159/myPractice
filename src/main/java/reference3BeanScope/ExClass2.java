package reference3BeanScope;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ScopedProxyMode;

@Component
public class ExClass2 {

	ExClass1 exClass1;

	// ExClass1 has session scope and doesn't exist when this singleton bean is created
	// For this, you should use (proxyMode = ScopedProxyMode.INTERFACES) to session scope bean
	@Autowired
	public void setExClass1(ExClass1 exClass1) {
		this.exClass1 = exClass1;
	}
}
