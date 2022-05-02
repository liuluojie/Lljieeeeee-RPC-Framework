package top.lljieeeeee.rpc.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.rpc.RpcServer;
import top.lljieeeeee.rpc.codec.CommonDecoder;
import top.lljieeeeee.rpc.codec.CommonEncoder;
import top.lljieeeeee.rpc.enumeration.RpcError;
import top.lljieeeeee.rpc.exception.RpcException;
import top.lljieeeeee.rpc.serializer.CommonSerializer;
import top.lljieeeeee.rpc.serializer.HessianSerializer;
import top.lljieeeeee.rpc.serializer.JsonSerializer;
import top.lljieeeeee.rpc.serializer.KryoSerializer;

/**
 * @author Lljieeeeee
 * @date 2022/4/21 19:03
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 * Netty方式服务端
 */
public class NettyServer implements RpcServer {

    public static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    private CommonSerializer serializer;

    @Override
    public void start(int port) {
        if (serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        //用于处理客户端连接的主线程池
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //用于连接后处理IO事件的从线程池
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //初始化Netty服务端启动器
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //将主从线程池初始化到启动器中
            serverBootstrap.group(bossGroup, workerGroup)
                    //设置服务端通道类型
                    .channel(NioServerSocketChannel.class)
                    //日志打印方式
                    .handler(new LoggingHandler(LogLevel.INFO))
                    //配置ServerChannel参数，服务端接受连接的最大队列长度，如果队列已满，客户端连接将被拒绝
                    .option(ChannelOption.SO_BACKLOG, 256)
                    //启动该功能时，TCP会主动探测空闲连接的有效性。可以将此功能视为TCP的心跳机制，默认的心跳间隔是7200s即2小时
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    //配置Channel参数，nodelay没有延迟，true就代表禁用Nagle算法，减小传输延迟
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    //初始化Handler，设置Handler操作
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //初始化管道
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //往管道中添加Handler，注意入站Handler与出站Handler都必须按实际执行顺序添加，比如先解码再Server处理，Decoder()就要放在前面。
                            //但入站和出站Handler之间则互不影响，这里就是先添加的出站Handler再添加的入站
                            pipeline.addLast(new CommonEncoder(serializer))
                                    .addLast(new CommonDecoder())
                                    .addLast(new NettyServerHandler());
                        }
                    });
            //绑定端口，启动Netty，sync()代表阻塞主Server线程，以执行Netty线程，如果不阻塞Netty就直接被下面shutdown了
            ChannelFuture future = serverBootstrap.bind(port).sync();
            //等确认通道关闭了，关闭future回到主Server线程
            future.channel().closeFuture().sync();
        }catch (InterruptedException e) {
            logger.error("启动服务器时有错误发生；{}", e);
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }
}
