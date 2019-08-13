package dubbo;

import com.hjx.dubbo.interfaces.APIHello;

/**
 * @Author: hjx
 * @Date: 2019/8/8
 * @Version 1.0
 */
public class MockTest implements APIHello {
    @Override
    public String sayHello() {
        System.out.println("进入到 容错中");
        return "系统繁忙";
    }
}
