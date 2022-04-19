package top.lljieeeeee.rpc.test;

import top.lljieeeeee.rpc.registry.DefaultServiceRegistry;
import top.lljieeeeee.rpc.server.RpcServer;

/**
 * @author Lljieeeeee
 * @date 2022/4/19 13:31
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class TestServer {

    public static void main(String[] args) {
        HelloServiceImpl helloService = new HelloServiceImpl();
        DefaultServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        RpcServer rpcServer = new RpcServer(serviceRegistry);
        rpcServer.start(9000);
    }
}
