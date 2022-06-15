package top.lljieeeeee.rpc.test.socketserver;

import top.lljieeeeee.rpc.api.HelloService;
import top.lljieeeeee.rpc.serializer.CommonSerializer;
import top.lljieeeeee.rpc.test.socketserver.implmethod.HelloServiceImpl3;
import top.lljieeeeee.rpc.transport.socket.server.SocketServer;

/**
 * @author liuluojie
 * @date 2022/6/7 14:18
 */
public class SocketServerTest3 {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl3();
        SocketServer socketServer = new SocketServer("127.0.0.1", 9003, CommonSerializer.PROTOBUF_SERIALIZER);
        socketServer.publishService(helloService, HelloService.class);
    }
}
