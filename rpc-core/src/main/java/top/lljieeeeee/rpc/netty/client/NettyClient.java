package top.lljieeeeee.rpc.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.rpc.RpcClient;
import top.lljieeeeee.rpc.codec.CommonDecoder;
import top.lljieeeeee.rpc.codec.CommonEncoder;
import top.lljieeeeee.rpc.entity.RpcRequest;
import top.lljieeeeee.rpc.entity.RpcResponse;
import top.lljieeeeee.rpc.serializer.JsonSerializer;
import top.lljieeeeee.rpc.serializer.KryoSerializer;

/**
 * @author Lljieeeeee
 * @date 2022/4/21 22:06
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 * Netty方式客户端
 */
public class NettyClient implements RpcClient {

    public static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private String host;
    private int port;
    private static final Bootstrap bootstrap;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    static {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new CommonDecoder())
                                .addLast(new CommonEncoder(new KryoSerializer()))
                                .addLast(new NettyClientHandler());
                    }
                });
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        try {
            ChannelFuture future = bootstrap.connect(host, port).sync();
            logger.info("客户端连接到服务端{}:{}", host, port);
            Channel channel = future.channel();
            if (channel != null) {
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
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
                //get()阻塞获取value
                RpcResponse rpcResponse = channel.attr(key).get();
                return rpcResponse.getData();
            }
        }catch (InterruptedException e) {
            logger.error("发送消息时有错误发生：", e);
        }
        return null;
    }
}
