package top.lljieeeeee.rpc.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.rpc.api.ByeService;
import top.lljieeeeee.rpc.api.HelloObject;
import top.lljieeeeee.rpc.api.HelloService;
import top.lljieeeeee.rpc.loadbalancer.RoundRobinLoadBalancer;
import top.lljieeeeee.rpc.serializer.CommonSerializer;
import top.lljieeeeee.rpc.transport.RpcClientProxy;
import top.lljieeeeee.rpc.transport.socket.client.SocketClient;

/**
 * @author Lljieeeeee
 * @date 2022/4/19 13:40
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class SocketTestClient {

    private static Logger logger = LoggerFactory.getLogger(SocketTestClient.class);

    public static void main(String[] args) {
        SocketClient client = new SocketClient(CommonSerializer.PROTOBUF_SERIALIZER, new RoundRobinLoadBalancer());
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = null;
        object = new HelloObject(1, "This is test message");
        String result = helloService.hello(object);
        System.out.println(result);
        ByeService byeService = proxy.getProxy(ByeService.class);
        String socket = byeService.bye("socket");
        System.out.println(socket);

    }
}
