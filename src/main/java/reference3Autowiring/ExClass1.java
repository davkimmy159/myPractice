package reference3Autowiring;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
// @Primary  ->  If both Primary classes exist, that's confused
public class ExClass1 implements ExInterface {

}
