package top.lljieeeeee;

import top.lljieeeeee.core.RpcClientProxy;
import top.lljieeeeee.core.netty.client.NettyClient;
import top.lljieeeeee.core.serializer.HessianSerializer;
import top.lljieeeeee.core.serializer.ProtobufSerializer;
import top.lljieeeeeee.api.HelloObject;
import top.lljieeeeeee.api.HelloService;

/**
 * @author Lljieeeeee
 * @date 2022/2/8 15:19
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class NettyTestClient {

    public static void main(String[] args) {
        NettyClient client = new NettyClient("localhost", 9999);
        client.setSerializer(new ProtobufSerializer());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(123, "test hanqing");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
