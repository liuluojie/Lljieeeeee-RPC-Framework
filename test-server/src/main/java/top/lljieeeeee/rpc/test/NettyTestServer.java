package top.lljieeeeee.rpc.test;

import top.lljieeeeee.rpc.api.HelloService;
import top.lljieeeeee.rpc.netty.server.NettyServer;
import top.lljieeeeee.rpc.registry.DefaultServiceRegistry;

/**
 * @author Lljieeeeee
 * @date 2022/4/21 22:19
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class NettyTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        DefaultServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer server = new NettyServer();
        server.start(9999);
    }
}
