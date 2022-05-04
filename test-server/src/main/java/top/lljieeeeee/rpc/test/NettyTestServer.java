package top.lljieeeeee.rpc.test;

import top.lljieeeeee.rpc.api.HelloService;
import top.lljieeeeee.rpc.transport.netty.server.NettyServer;
import top.lljieeeeee.rpc.provider.ServiceProviderImpl;
import top.lljieeeeee.rpc.serializer.ProtostuffSerializer;

/**
 * @author Lljieeeeee
 * @date 2022/4/21 22:19
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class NettyTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        NettyServer server = new NettyServer("127.0.0.1", 9999);
        server.setSerializer(new ProtostuffSerializer());
        server.publishService(helloService, HelloService.class);
    }
}
