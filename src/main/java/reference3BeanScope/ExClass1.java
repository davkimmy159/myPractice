package reference3BeanScope;

import org.springframework.stereotype.Component;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
// @Scope("prototype")
public class ExClass1 implements ExInterface1 {

	@Override
	public void method1() {
		System.out.println("Method implementation!");
	}
}
