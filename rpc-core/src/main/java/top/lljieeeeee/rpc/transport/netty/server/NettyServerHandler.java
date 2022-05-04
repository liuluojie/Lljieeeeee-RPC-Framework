package top.lljieeeeee.rpc.transport.netty.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.rpc.handler.RequestHandler;
import top.lljieeeeee.rpc.entity.RpcRequest;
import top.lljieeeeee.rpc.factory.ThreadPoolFactory;

import java.util.concurrent.ExecutorService;


/**
 * @author Lljieeeeee
 * @date 2022/4/21 19:11
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 * Netty中处理从客户端传来的RpcRequest
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private static final String THREAD_NAME_PREFIX = "netty-server-handler";
    private final ExecutorService threadPool;
    private final RequestHandler requestHandler;

    public NettyServerHandler() {
        requestHandler = new RequestHandler();
        //引入异步业务线程池，避免长时间的耗时业务阻塞netty本身的worker工作线程，耽误了同一个Selector中其他任务的执行
        threadPool = ThreadPoolFactory.createDefaultThreadPool(THREAD_NAME_PREFIX);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        try {
            logger.info("服务端接收到请求：{}", msg);
            Object response = requestHandler.handle(msg);
            //这里的通道是workerGroup中的，而NettyServer中创建的是bossGroup的
            ChannelFuture future = ctx.writeAndFlush(response);
            //添加一个监听器到 future 来检测是否所有的数据包都发出，然后关闭通道
            future.addListener(ChannelFutureListener.CLOSE);
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("处理过程调用时有错误发生：");
        cause.printStackTrace();
        ctx.channel();
    }
}
