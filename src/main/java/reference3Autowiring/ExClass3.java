package reference3Autowiring;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;

@Component
//@Qualifier("myQualifier1")
//@Qualifier("myQualifier2")  ->  You can't use more @Qualifier even it's annotated by @Repeatable (You should use your customized annotation)
@ExAnnotation1
@ExAnnotation2
@ExAnnotation3
public class ExClass3 implements ExInterface {

}
