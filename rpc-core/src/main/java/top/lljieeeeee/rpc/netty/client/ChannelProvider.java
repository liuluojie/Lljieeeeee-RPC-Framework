package top.lljieeeeee.rpc.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.rpc.codec.CommonDecoder;
import top.lljieeeeee.rpc.codec.CommonEncoder;
import top.lljieeeeee.rpc.enumeration.RpcError;
import top.lljieeeeee.rpc.exception.RpcException;
import top.lljieeeeee.rpc.serializer.CommonSerializer;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Lljieeeeee
 * @date 2022/5/3 22:46
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 * 用于获取Channel对象
 */
public class ChannelProvider {

    private static final Logger logger = LoggerFactory.getLogger(ChannelProvider.class);

    private static EventLoopGroup eventLoopGroup;
    private static Bootstrap bootstrap = initializeBootstrap();

    private static final int MAX_RETRY_COUNT = 5;
    private static Channel channel = null;

    private static Bootstrap initializeBootstrap() {
        eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                //连接的超时时间，超过这个时间还是建立不上的话则代表连接失败
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                //启用该功能时，TCP会主动探测空闲连接的有效性，可以将此功能视为TCP的心跳机制，默认的心跳间隔是7200s（2小时）
                .option(ChannelOption.SO_KEEPALIVE, true)
                //配置Channel参数，nodelay没有延迟，true就代表禁用Nagel算法，减小传输延迟
                .option(ChannelOption.TCP_NODELAY, true);
        return bootstrap;
    }

    public static Channel get(InetSocketAddress inetSocketAddress, CommonSerializer serializer) {
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline()
                        .addLast(new CommonEncoder(serializer))
                        .addLast(new CommonDecoder())
                        .addLast(new NettyClientHandler());
            }
        });
        //设置计数器值为1
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            connect(bootstrap, inetSocketAddress, countDownLatch);
            //阻塞当前线程直到计时器的值为0
            countDownLatch.await();
        }catch (InterruptedException e) {
            logger.error("获取Channel时有错误发生", e);
        }
        return channel;
    }

    private static void connect(Bootstrap bootstrap, InetSocketAddress inetSocketAddress, CountDownLatch countDownLatch) {
        connect(bootstrap, inetSocketAddress, MAX_RETRY_COUNT, countDownLatch);
    }

    /**
     * Netty客户端创建通道连接， 实现连接失败重试机制
     * @param bootstrap
     * @param inetSocketAddress
     * @param retry
     * @param countDownLatch
     */
    private static void connect(Bootstrap bootstrap, InetSocketAddress inetSocketAddress, int retry, CountDownLatch countDownLatch) {
        bootstrap.connect(inetSocketAddress).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                logger.info("客户端连接成功！");
                channel = future.channel();
                //计数器减一
                countDownLatch.countDown();
                return;
            }
            if (retry == 0) {
                logger.error("客户端连接失败：重用次数已用完，放弃链接！");
                countDownLatch.countDown();
                throw new RpcException(RpcError.CLIENT_CONNECT_SERVER_FAILURE);
            }
            //第几次重连
            int order = (MAX_RETRY_COUNT - retry) + 1;
            //重连的时间间隔
            int delay = 1 << order;
            logger.error("{}：连接失败，第{}次重连", new Date(), order);
            //利用schedule()在给定的延迟时间后执行connect()重连
            bootstrap.config().group().schedule(() -> connect(bootstrap, inetSocketAddress, retry - 1, countDownLatch), delay, TimeUnit.SECONDS);
        });
    }
}
