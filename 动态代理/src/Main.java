import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Main {

    public static void main(String[] args) {
        DynamicProxy proxy = new DynamicProxy();

        // 代理Sub1
        System.out.println("代理Sub1接口");
        Sub1 obj1 = (Sub1) proxy.newProxyInstance(new RealSub1());
        System.out.println(obj1.getClass().getName());
        obj1.sub1Act("参数 for sub1");

        System.out.println();

        // 代理 Sub2
        System.out.println("代理Sub2接口");
        Sub2 obj2 = (Sub2) proxy.newProxyInstance(new RealSub2());
        System.out.println(obj2.getClass().getName());
        obj2.sub2Act("参数 for sub2");

    }
}