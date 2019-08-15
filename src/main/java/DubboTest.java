import com.hjx.dubbo.interfaces.APIHello;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: hjx
 * @Date: 2019/7/30
 * @Version 1.0
 */
public class DubboTest {
    public static void main(String[] args) {

//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo-client.xml");
//        APIHello apiHello = (APIHello)context.getBean("helloService");
//        System.out.println(apiHello.sayHello());

        double a = 3.2d;

        double b = 4.8d;


        Integer c = 5;
        System.out.println(c.doubleValue());

        System.out.println(a+b);

    }
}
