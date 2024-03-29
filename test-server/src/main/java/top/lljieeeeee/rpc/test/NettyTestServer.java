package top.lljieeeeee.rpc.test;

import top.lljieeeeee.rpc.annotation.ServiceSacn;
import top.lljieeeeee.rpc.api.HelloService;
import top.lljieeeeee.rpc.serializer.CommonSerializer;
import top.lljieeeeee.rpc.transport.netty.server.NettyServer;

/**
 * @author Lljieeeeee
 * @date 2022/4/21 22:19
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
@ServiceSacn
public class NettyTestServer {

    public static void main(String[] args) {
        NettyServer server = new NettyServer("127.0.0.1", 9999, CommonSerializer.PROTOBUF_SERIALIZER);
        server.start();
    }
}
