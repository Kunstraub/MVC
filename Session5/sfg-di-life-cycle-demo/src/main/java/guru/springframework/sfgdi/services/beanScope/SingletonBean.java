package guru.springframework.sfgdi.services.beanScope;

import org.springframework.stereotype.Component;

@Component
public class SingletonBean {

    public SingletonBean(){
        System.out.println("Singleton created");
    }
    public String getBeanTime(){
        return "Hi i've been in a singleton";
    }
}
