package top.lljieeeeee.rpc.test;

import top.lljieeeeee.rpc.registry.DefaultServiceRegistry;
import top.lljieeeeee.rpc.serializer.HessianSerializer;
import top.lljieeeeee.rpc.socket.server.SocketServer;

import java.util.Arrays;
import java.util.List;


/**
 * @author Lljieeeeee
 * @date 2022/4/19 13:31
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class SocketTestServer {

    public static void main(String[] args) {
        HelloServiceImpl helloService = new HelloServiceImpl();
        DefaultServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        SocketServer socketServer = new SocketServer(serviceRegistry);
        socketServer.setSerializer(new HessianSerializer());
        socketServer.start(9000);
    }
}
