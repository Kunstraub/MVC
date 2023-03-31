package guru.springframework.sfgdi.services.beanScope;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PrototypeBean {
    public PrototypeBean(){
        System.out.println("Prototype created");
    }
    public String getBeanTime(){
        return "Hi i've been in a prototype";
    }
}
