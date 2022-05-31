package top.lljieeeeee.rpc.test;

import top.lljieeeeee.rpc.api.HelloService;
import top.lljieeeeee.rpc.provider.ServiceProviderImpl;
import top.lljieeeeee.rpc.serializer.CommonSerializer;
import top.lljieeeeee.rpc.serializer.HessianSerializer;
import top.lljieeeeee.rpc.transport.socket.server.SocketServer;


/**
 * @author Lljieeeeee
 * @date 2022/4/19 13:31
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class SocketTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl2();
        SocketServer socketServer = new SocketServer("127.0.0.1", 9000, CommonSerializer.PROTOBUF_SERIALIZER);
        socketServer.publishService(helloService, HelloService.class);
    }
}
