package top.lljieeeeee.rpc.test;

import top.lljieeeeee.rpc.RpcClientProxy;
import top.lljieeeeee.rpc.api.HelloObject;
import top.lljieeeeee.rpc.api.HelloService;
import top.lljieeeeee.rpc.netty.client.NettyClient;
import top.lljieeeeee.rpc.serializer.HessianSerializer;
import top.lljieeeeee.rpc.serializer.ProtostuffSerializer;

/**
 * @author Lljieeeeee
 * @date 2022/4/21 22:17
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class NettyTestClient {

    public static void main(String[] args) {
        NettyClient client = new NettyClient("127.0.0.1", 9999);
        client.setSerializer(new ProtostuffSerializer());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "this is netty style");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
