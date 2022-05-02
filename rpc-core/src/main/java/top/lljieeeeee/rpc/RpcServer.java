package top.lljieeeeee.rpc;

import top.lljieeeeee.rpc.serializer.CommonSerializer;

/**
 * @author Lljieeeeee
 * @date 2022/4/20 21:49
 * @url https://www.lljieeeeee.top/
 * @QQ 2015743127
 * 服务端通用接口
 */
public interface RpcServer {

    void start(int port);

    void setSerializer(CommonSerializer serializer);
}
