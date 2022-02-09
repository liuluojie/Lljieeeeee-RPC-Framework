package top.lljieeeeee.service;

import top.lljieeeeee.core.netty.server.NettyServer;
import top.lljieeeeee.core.registry.DefaultServiceRegistry;

/**
 * @author Lljieeeeee
 * @date 2022/2/8 15:15
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class NettyTestServer {

    public static void main(String[] args) {
        HelloServiceImpl helloService = new HelloServiceImpl();
        DefaultServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer server = new NettyServer();
        server.start(9999);
    }
}
