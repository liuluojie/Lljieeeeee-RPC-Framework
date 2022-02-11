package top.lljieeeeee;

import top.lljieeeeee.core.RpcClientProxy;
import top.lljieeeeee.core.serializer.KryoSerializer;
import top.lljieeeeee.core.socket.client.SocketClient;
import top.lljieeeeeee.api.HelloObject;
import top.lljieeeeeee.api.HelloService;

/**
 * @author Lljieeeeee
 * @date 2022/1/30 14:49
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class SocketTestClient {

    public static void main(String[] args) {
        SocketClient client = new SocketClient("127.0.0.1", 9999);
        client.setSerializer(new KryoSerializer());
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(1, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
