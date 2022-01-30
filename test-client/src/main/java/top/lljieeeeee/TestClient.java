package top.lljieeeeee;

import top.lljieeeeee.core.client.RpcClientProxy;
import top.lljieeeeeee.api.HelloObject;
import top.lljieeeeeee.api.HelloService;

/**
 * @author Lljieeeeee
 * @date 2022/1/30 14:49
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class TestClient {

    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy("localhost", 9999);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject helloObject = new HelloObject(1, "测试");
        String hello = helloService.hello(helloObject);
        System.out.println(hello);
    }
}
