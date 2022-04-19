package top.lljieeeeee.rpc.test;

import top.lljieeeeee.rpc.api.HelloObject;
import top.lljieeeeee.rpc.api.HelloService;
import top.lljieeeeee.rpc.client.RpcClientProxy;

/**
 * @author Lljieeeeee
 * @date 2022/4/19 13:40
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class TestClient {

    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 9000);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is test message");
        String result = helloService.hello(object);
        System.out.println(result);
    }
}
