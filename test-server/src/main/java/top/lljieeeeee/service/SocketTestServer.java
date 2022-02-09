package top.lljieeeeee.service;

import top.lljieeeeee.core.registry.DefaultServiceRegistry;
import top.lljieeeeee.core.registry.ServiceRegistry;
import top.lljieeeeee.core.socket.server.SocketServer;
import top.lljieeeeeee.api.HelloService;

/**
 * @author Lljieeeeee
 * @date 2022/1/30 14:46
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class SocketTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        SocketServer socketServer = new SocketServer(serviceRegistry);
        socketServer.start(9999);
    }
}
