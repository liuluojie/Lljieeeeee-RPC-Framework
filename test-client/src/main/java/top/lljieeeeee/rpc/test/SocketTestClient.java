package top.lljieeeeee.rpc.test;

import top.lljieeeeee.rpc.api.HelloObject;
import top.lljieeeeee.rpc.api.HelloService;
import top.lljieeeeee.rpc.transport.RpcClientProxy;
import top.lljieeeeee.rpc.serializer.KryoSerializer;
import top.lljieeeeee.rpc.transport.socket.client.SocketClient;

/**
 * @author Lljieeeeee
 * @date 2022/4/19 13:40
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class SocketTestClient {

    public static void main(String[] args) {
        SocketClient client = new SocketClient();
        client.setSerializer(new KryoSerializer());
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is test message");
        String result = helloService.hello(object);
        System.out.println(result);
    }
}
