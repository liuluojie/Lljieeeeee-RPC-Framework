package top.lljieeeeee.core.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.common.entity.RpcRequest;
import top.lljieeeeee.common.entity.RpcResponse;
import top.lljieeeeee.common.enumeration.RpcError;
import top.lljieeeeee.common.exception.RpcException;
import top.lljieeeeee.core.RpcClient;
import top.lljieeeeee.core.codec.CommonDecoder;
import top.lljieeeeee.core.codec.CommonEncoder;
import top.lljieeeeee.core.serializer.CommonSerializer;

/**
 * @author Lljieeeeee
 * @date 2022/2/8 15:05
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 */
public class NettyClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private static final Bootstrap bootstrap;

    private CommonSerializer serializer;

    private String host;

    private int port;


    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    static {
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true);
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        if (serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline pipeline = socketChannel.pipeline();
                pipeline.addLast(new CommonDecoder())
                        .addLast(new CommonEncoder(serializer))
                        .addLast(new NettyClientHandler());
            }
        });
        try {
            ChannelFuture future = bootstrap.connect(host, port).sync();
            logger.info("客户端连接到服务器：{}：{}", host, port);
            Channel channel = future.channel();
            if (channel != null) {
                channel.writeAndFlush(rpcRequest)
                        .addListener(future1 -> {
                            if (future1.isSuccess()) {
                                logger.info(String.format("客户端发送消息：%s", rpcRequest.toString()));
                            }else {
                                logger.error("发送消息时有错误发生：", future1.cause());
                            }
                        });
                channel.closeFuture().sync();
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
                RpcResponse rpcResponse = channel.attr(key).get();
                return rpcResponse.getData();
            }
        } catch (InterruptedException e) {
            logger.error("发送消息时有错误发生: ", e);
        }
        return null;
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }
}
