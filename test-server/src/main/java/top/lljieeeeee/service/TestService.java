package top.lljieeeeee.service;

import top.lljieeeeee.core.registry.DefaultServiceRegistry;
import top.lljieeeeee.core.registry.ServiceRegistry;
import top.lljieeeeee.core.server.RpcServer;
import top.lljieeeeeee.api.HelloService;

/**
 * @author Lljieeeeee
 * @date 2022/1/30 14:46
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class TestService {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        RpcServer rpcServer = new RpcServer(serviceRegistry);
        rpcServer.start(9999);
    }
}
