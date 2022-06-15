package top.lljieeeeee.rpc.test.socketserver;

import top.lljieeeeee.rpc.api.HelloService;
import top.lljieeeeee.rpc.serializer.CommonSerializer;
import top.lljieeeeee.rpc.test.socketserver.implmethod.HelloServiceImpl2;
import top.lljieeeeee.rpc.transport.socket.server.SocketServer;

/**
 * @author liuluojie
 * @date 2022/6/7 14:18
 */
public class SocketServerTest2 {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl2();
        SocketServer socketServer = new SocketServer("127.0.0.1", 9002, CommonSerializer.PROTOBUF_SERIALIZER);
        socketServer.publishService(helloService, HelloService.class);
    }
}
