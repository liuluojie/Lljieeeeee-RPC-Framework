package top.lljieeeeee.rpc.test;

import top.lljieeeeee.rpc.annotation.ServiceSacn;
import top.lljieeeeee.rpc.serializer.CommonSerializer;
import top.lljieeeeee.rpc.transport.socket.server.SocketServer;



/**
 * @author Lljieeeeee
 * @date 2022/4/19 13:31
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
@ServiceSacn
public class SocketTestServer {

    public static void main(String[] args) {
        SocketServer socketServer = new SocketServer("127.0.0.1", 9000, CommonSerializer.PROTOBUF_SERIALIZER);
        socketServer.start();
    }
}
