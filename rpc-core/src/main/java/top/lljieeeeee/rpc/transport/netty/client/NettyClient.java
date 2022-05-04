package top.lljieeeeee.rpc.transport.netty.client;

import io.netty.channel.*;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.rpc.register.NacosServiceRegistry;
import top.lljieeeeee.rpc.register.ServiceRegistry;
import top.lljieeeeee.rpc.transport.RpcClient;
import top.lljieeeeee.rpc.entity.RpcRequest;
import top.lljieeeeee.rpc.entity.RpcResponse;
import top.lljieeeeee.rpc.enumeration.RpcError;
import top.lljieeeeee.rpc.exception.RpcException;
import top.lljieeeeee.rpc.serializer.CommonSerializer;
import top.lljieeeeee.rpc.util.RpcMessageChecker;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Lljieeeeee
 * @date 2022/4/21 22:06
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 * Netty方式客户端
 */
public class NettyClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);
    private final ServiceRegistry serviceRegistry;

    private CommonSerializer serializer;

    public NettyClient() {
        serviceRegistry = new NacosServiceRegistry();
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        if (serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        //保证自定义实体类变量的原子性和共享性的线程安全，此处应用于rpcResponse
        AtomicReference<Object> result = new AtomicReference<>(null);
        try {
            //从Nacos获取提供对应的服务的服务端地址
            InetSocketAddress inetSocketAddress = serviceRegistry.lookupService(rpcRequest.getInterfaceName());
            //创建Netty通道地址
            Channel channel = ChannelProvider.get(inetSocketAddress, serializer);
            if (channel.isActive()) {
                //向服务器发请求，并设置监听
                channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                    if (future1.isSuccess()) {
                        logger.info(String.format("客户端发送消息：%s", rpcRequest.toString()));
                    }else {
                        logger.error("发送消息时有错误发生：", future1.cause());
                    }
                });
                channel.closeFuture().sync();
                //AttributeMap<AttributeKey, AttributeValue>是绑定在Channel上的，可以设置用来获取通道对象
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse" + rpcRequest.getRequestId());
                //get()阻塞获取value
                RpcResponse rpcResponse = channel.attr(key).get();
                RpcMessageChecker.check(rpcRequest, rpcResponse);
                result.set(rpcResponse.getData());
            }else {
                //0表示“正常”退出程序，即如果当前程序还有在执行的任务，则等待所有任务执行完成以后再退出
                System.exit(0);
            }
        }catch (InterruptedException e) {
            logger.error("发送消息时有错误发生：", e);
        }
        return result.get();
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }
}
