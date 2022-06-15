package top.lljieeeeee.rpc.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        for (int i = 1; i <= 100; i++) {
            object = new HelloObject(i, "This is test message，来自第" + i + "个请求");
            String result = helloService.hello(object);
            logger.info("第 {} 次请求结果：{}", i, result);
        }
    }
}
