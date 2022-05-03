package top.lljieeeeee.rpc.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lljieeeeee.rpc.entity.RpcResponse;
import top.lljieeeeee.rpc.netty.server.NettyServerHandler;

/**
 * @author Lljieeeeee
 * @date 2022/4/21 22:02
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 * 客户端Netty处理器
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    public static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        try {
            logger.info("客户端接收到消息：%s", msg);
            AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse" + msg.getRequestId());
            ctx.channel().attr(key).set(msg);
            //关闭客户端通道
            ctx.channel().close();
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("过程调用中有错误发生：");
        cause.printStackTrace();
        ctx.close();
    }
}
